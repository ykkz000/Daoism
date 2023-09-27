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

package ykkz000.daoism.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import ykkz000.daoism.entity.DaoismEntityTypes;
import ykkz000.daoism.entity.ai.control.JumpMoveControl;
import ykkz000.daoism.entity.effect.DaoismStatusEffects;

public class ChineseZombieEntity extends ZombieEntity {
    public ChineseZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new JumpMoveControl(this, () -> this.getRandom().nextInt(5) + 2);
    }

    public ChineseZombieEntity(World world) {
        this(DaoismEntityTypes.CHINESE_ZOMBIE, world);
    }

    public static DefaultAttributeContainer.Builder createChineseZombieAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0).add(EntityAttributes.GENERIC_ARMOR, 2.0).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5f);
    }

    @Override
    protected void jump() {
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x, this.getJumpVelocity(), vec3d.z);
        this.velocityDirty = true;
    }

    @Override
    protected float getJumpVelocityMultiplier() {
        return super.getJumpVelocityMultiplier() * 1.5f;
    }

    @Override
    public boolean onKilledOther(ServerWorld world, LivingEntity other) {
        ChineseZombieEntity.infect(other);
        return true;
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean ret = super.tryAttack(target);
        if (ret && target instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(DaoismStatusEffects.DEGRADATION, 100, 0), this);
        }
        return ret;
    }

    @Override
    public double squaredAttackRange(LivingEntity target) {
        return super.squaredAttackRange(target) * 1.44;
    }

    public static void infect(LivingEntity entity) {
        if (!entity.isRemoved() && entity.getWorld() instanceof ServerWorld serverWorld && !entity.isPlayer()) {
            ChineseZombieEntity zombieEntity = new ChineseZombieEntity(serverWorld);
            zombieEntity.setPosition(entity.getPos());
            zombieEntity.setYaw(entity.getYaw());
            zombieEntity.setPitch(entity.getPitch());
            serverWorld.spawnEntity(zombieEntity);
            entity.discard();
        }
    }

}
