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

package ykkz000.daoism.entity.ai.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

import java.util.function.Supplier;

public class JumpMoveControl extends MoveControl {
    private int ticksUntilJump;
    private final Supplier<Integer> tickSupplier;

    public JumpMoveControl(MobEntity entity, Supplier<Integer> tickSupplier) {
        super(entity);
        this.tickSupplier = tickSupplier;
        this.ticksUntilJump = 0;
    }

    @Override
    public void tick() {
        if (this.state != MoveControl.State.MOVE_TO) {
            this.entity.setForwardSpeed(0.0f);
            return;
        }
        this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
        this.state = MoveControl.State.WAIT;
        double dx = this.targetX - this.entity.getX();
        double dy = this.targetZ - this.entity.getZ();
        double dz = this.targetY - this.entity.getY();
        double sqrDist = dx * dx + dz * dz + dy * dy;
        if (sqrDist < 2.500000277905201E-7) {
            this.entity.setForwardSpeed(0.0f);
            return;
        }
        float targetYaw = (float)(MathHelper.atan2(dy, dx) * 57.2957763671875) - 90.0f;
        this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), targetYaw, 90.0f));
        if (this.entity.isOnGround()) {
            if (this.ticksUntilJump-- <= 0) {
                this.ticksUntilJump = this.tickSupplier.get();
                this.entity.getJumpControl().setActive();
            } else {
                this.entity.setSidewaysSpeed(0.0f);
                this.entity.setForwardSpeed(0.0f);
                this.entity.setMovementSpeed(0.0f);
            }
        }
    }
}
