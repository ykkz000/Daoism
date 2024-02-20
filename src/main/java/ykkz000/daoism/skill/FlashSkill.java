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

package ykkz000.daoism.skill;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class FlashSkill extends DaoismSkill {
    private static final double MAX_DISTANCE = 4.0;

    public FlashSkill() {
        super(20, 10);
    }


    @Override
    public void use(PlayerEntity player, World world) {
        Vec3d direct = Vec3d.fromPolar(0.0f, player.getHeadYaw());
        Vec3d start = player.getPos();
        Vec3d end = start.add(direct.multiply(MAX_DISTANCE));
        BlockHitResult result = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));
        double distance = result != null && result.getType() != HitResult.Type.MISS ? Math.sqrt(result.getPos().squaredDistanceTo(start)) : MAX_DISTANCE;
        player.teleport(player.getPos().getX() + direct.getX() * distance, player.getPos().getY(), player.getPos().getZ() + direct.getZ() * distance);
    }
}
