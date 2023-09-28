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

package ykkz000.daoism.entity.projectile.thrown;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import ykkz000.daoism.entity.DaoismEntityTypes;
import ykkz000.daoism.entity.effect.DaoismStatusEffects;
import ykkz000.daoism.item.DaoismItems;

public class ImmobilizationTalismanEntity extends ThrownItemEntity {
    public ImmobilizationTalismanEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ImmobilizationTalismanEntity(World world, LivingEntity owner) {
        super(DaoismEntityTypes.IMMOBILIZATION_TALISMAN, owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return DaoismItems.IMMOBILIZATION_TALISMAN;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(DaoismStatusEffects.IMMOBILIZATION, 100, 0), this.getOwner());
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 20, 0.0, 0.0, 0.0, 1.0);
            this.discard();
        }
    }
}
