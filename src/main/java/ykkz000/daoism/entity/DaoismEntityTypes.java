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

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import ykkz000.daoism.Daoism;
import ykkz000.daoism.entity.mob.ChineseZombieEntity;
import ykkz000.daoism.entity.projectile.thrown.ImmobilizationTalismanEntity;

public class DaoismEntityTypes {
    public static final EntityType<ChineseZombieEntity> CHINESE_ZOMBIE = register("chinese_zombie", mobBuilder(ChineseZombieEntity::new, SpawnGroup.MONSTER, ChineseZombieEntity.class).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).defaultAttributes(ChineseZombieEntity::createChineseZombieAttributes).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark).build());
    public static final EntityType<ImmobilizationTalismanEntity> IMMOBILIZATION_TALISMAN = register("immobilization_talisman", builder(ImmobilizationTalismanEntity::new, SpawnGroup.MISC, ImmobilizationTalismanEntity.class).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4).trackedUpdateRate(10).build());
    public static <T extends Entity> EntityType<T> register(String id, EntityType<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(Daoism.MOD_ID, id), type);
    }
    public static <T extends MobEntity> FabricEntityTypeBuilder.Mob<T> mobBuilder(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup, Class<T> clazz) {
        return FabricEntityTypeBuilder.createMob().spawnGroup(spawnGroup).entityFactory(factory);
    }
    public static <T extends Entity> FabricEntityTypeBuilder<T> builder(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup, Class<T> clazz) {
        return FabricEntityTypeBuilder.create(spawnGroup, factory);
    }
}
