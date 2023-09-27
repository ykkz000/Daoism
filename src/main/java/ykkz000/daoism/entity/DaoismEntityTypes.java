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

package ykkz000.daoism.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ykkz000.daoism.Daoism;
import ykkz000.daoism.entity.mob.ChineseZombieEntity;

public class DaoismEntityTypes {
    public static final EntityType<ChineseZombieEntity> CHINESE_ZOMBIE = register("chinese_zombie", builder(ChineseZombieEntity::new, SpawnGroup.MONSTER, ChineseZombieEntity.class).setDimensions(0.6f, 1.95f).maxTrackingRange(8));
    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(Daoism.MOD_ID, id), type.build(id));
    }
    public static <T extends Entity> EntityType.Builder<T> builder(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup, Class<T> clazz) {
        return EntityType.Builder.create(factory, spawnGroup);
    }
}
