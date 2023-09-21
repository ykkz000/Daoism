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

package ykkz000.daoism.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ykkz000.daoism.Daoism;
import ykkz000.daoism.block.DaoismBlocks;

public class DaoismBlockEntityTypes {
    public static final BlockEntityType<AltarBlockEntity> ALTAR = create("altar", AltarBlockEntity::new, DaoismBlocks.ALTAR);

    public static <T extends BlockEntity> BlockEntityType<T> create(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Daoism.MOD_ID, id), FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }
}
