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

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

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
        if (world instanceof ServerWorld serverWorld) {
            MinecraftServer minecraftServer = serverWorld.getServer();
            if (TransmissionTalismanItem.hasTarget(itemStack)) {
                return TransmissionTalismanItem.getTarget(itemStack).map(target -> {
                    ServerWorld targetWorld = minecraftServer.getWorld(RegistryKey.of(RegistryKeys.WORLD, target.destination()));
                    if (targetWorld == null) {
                        return TypedActionResult.fail(itemStack);
                    }
                    user.teleport(targetWorld, target.x(), target.y(), target.z(), PositionFlag.ROT, target.yaw(), target.pitch());
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    user.getItemCooldownManager().set(this, 20);
                    if (!user.getAbilities().creativeMode) {
                        itemStack.damage(1, user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                    }
                    return TypedActionResult.success(itemStack);
                }).orElse(TypedActionResult.fail(itemStack));
            } else {
                Target target = Target.from(user, world);
                itemStack.setSubNbt("target", target.toNbt());
            }
        }
        return TypedActionResult.success(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        TransmissionTalismanItem.getTarget(stack).ifPresent(target -> tooltip.add(Text.literal(target.toString()).formatted(Formatting.GREEN)));
    }

    public static boolean hasTarget(ItemStack itemStack) {
        if (itemStack.isEmpty() || !(itemStack.getItem() instanceof TransmissionTalismanItem)) {
            return false;
        }
        NbtCompound target = itemStack.getSubNbt("target");
        return target != null && !target.isEmpty();
    }

    public static Optional<Target> getTarget(ItemStack itemStack) {
        if (!TransmissionTalismanItem.hasTarget(itemStack)) {
            return Optional.empty();
        }
        NbtCompound targetNbt = itemStack.getSubNbt("target");
        if (targetNbt == null) {
            return Optional.empty();
        }
        return Optional.of(Target.fromNbt(targetNbt));
    }

    public record Target(Identifier destination, double x, double y, double z, float yaw, float pitch){
        @Override
        public String toString() {
            return String.format("%s (%.2f, %.2f, %.2f) %.2f %.2f", destination, x, y, z, yaw, pitch);
        }

        public NbtCompound toNbt() {
            NbtCompound targetNbt = new NbtCompound();
            targetNbt.putString("world", destination.toString());
            targetNbt.putDouble("x", x);
            targetNbt.putDouble("y", y);
            targetNbt.putDouble("z", z);
            targetNbt.putFloat("yaw",yaw);
            targetNbt.putFloat("pitch", pitch);
            return targetNbt;
        }

        public static Target fromNbt(NbtCompound targetNbt) {
            return new Target(new Identifier(targetNbt.getString("world")), targetNbt.getDouble("x"), targetNbt.getDouble("y"), targetNbt.getDouble("z"), targetNbt.getFloat("yaw"), targetNbt.getFloat("pitch"));
        }

        public static Target from(PlayerEntity user, World world) {
            return new Target(world.getRegistryKey().getValue(), user.getX(), user.getY(), user.getZ(), user.getYaw(), user.getPitch());
        }
    }
}
