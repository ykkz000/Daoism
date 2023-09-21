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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import ykkz000.daoism.util.WorldUtil;

public class ThunderTalismanItem extends TalismanItem {
    public ThunderTalismanItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack != null && world instanceof ServerWorld serverWorld) {
            WorldUtil.filterEntitySurroundEntity(user, world, 5, Entity::isLiving)
                    .stream()
                    .map(entity -> (LivingEntity) entity)
                    .filter(livingEntity -> livingEntity instanceof MobEntity).forEach(livingEntity -> {
                        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                        if (lightning == null) {
                            return;
                        }
                        lightning.setPosition(livingEntity.getPos());
                        serverWorld.spawnEntity(lightning);
                    });
            user.getItemCooldownManager().set(this, 1200);
            itemStack.decrement(1);
            return TypedActionResult.success(itemStack);
        }
        return super.use(world, user, hand);
    }
}
