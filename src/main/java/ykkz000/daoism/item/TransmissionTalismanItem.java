/*
 * Daoism
 * Copyright (C) 2023  ykkz000
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ykkz000.daoism.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Objects;

public class TransmissionTalismanItem extends AbstractTalismanItem {
    public TransmissionTalismanItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public int getEnchantability() {
        return 20;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(DaoismItems.CINNABAR) || super.canRepair(stack, ingredient);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack != null && world instanceof ServerWorld serverWorld) {
            MinecraftServer minecraftServer = serverWorld.getServer();
            if (!TransmissionTalismanItem.hasTarget(itemStack)) {
                NbtCompound target = new NbtCompound();
                target.putString("world", world.getRegistryKey().getValue().toString());
                target.putDouble("x", user.getX());
                target.putDouble("y", user.getY());
                target.putDouble("z", user.getZ());
                target.putFloat("yaw", user.getYaw());
                target.putFloat("pitch", user.getPitch());
                itemStack.setSubNbt("target", target);
            } else {
                NbtCompound target = itemStack.getSubNbt("target");
                assert target != null;
                ServerWorld targetWorld = minecraftServer.getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(target.getString("world"))));
                if (targetWorld == null) {
                    return TypedActionResult.fail(itemStack);
                }
                if (!Objects.equals(user.getWorld().getRegistryKey().getValue().toString(), targetWorld.getRegistryKey().getValue().toString())) {
                    user.moveToWorld(targetWorld);
                }
                ((ServerPlayerEntity) user).networkHandler.requestTeleport(target.getDouble("x"), target.getDouble("y"), target.getDouble("z"), target.getFloat("yaw"), target.getFloat("pitch"), PositionFlag.ROT);
                user.getItemCooldownManager().set(this, 20);
                user.incrementStat(Stats.USED.getOrCreateStat(this));
                itemStack.damage(1, user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            }
            return TypedActionResult.success(itemStack);
        }
        return super.use(world, user, hand);
    }

    public static boolean hasTarget(ItemStack itemStack) {
        if (itemStack.isEmpty() || !(itemStack.getItem() instanceof TransmissionTalismanItem)) {
            return false;
        }
        NbtCompound target = itemStack.getSubNbt("target");
        return target != null && !target.isEmpty();
    }
}
