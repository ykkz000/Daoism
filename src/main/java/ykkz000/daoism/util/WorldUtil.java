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

package ykkz000.daoism.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class WorldUtil {
    public static List<Entity> filterEntity(World world, Entity entity, double minX, double maxX, double minY, double maxY, double minZ, double maxZ, Predicate<Entity> filter) {
        return world.getOtherEntities(entity, new Box(minX, minY, minZ, maxX, maxY, maxZ))
                .stream().filter(filter).toList();
    }

    public static List<Entity> filterEntitySurroundEntity(Entity entity, World world, double r, Predicate<Entity> filter) {
        return WorldUtil.filterEntity(world, entity, entity.getX() -r, entity.getX() + r, entity.getY() - r, entity.getY() + r, entity.getZ() - r, entity.getZ() + r, filter)
                .stream().filter(entity1 -> entity1.squaredDistanceTo(entity) <= r * r).toList();
    }
}
