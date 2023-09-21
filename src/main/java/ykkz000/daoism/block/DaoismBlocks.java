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

package ykkz000.daoism.block;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import ykkz000.daoism.Daoism;

public class DaoismBlocks {
    public static final Block ALTAR = register("altar", new AltarBlock(FabricBlockSettings.create().strength(20f, 1200f).requiresTool()));
    public static final Block CINNABAR_ORE = register("cinnabar_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(Instrument.BASEDRUM).requiresTool().strength(3.0f, 3.0f)));
    public static final Block DEEPSLATE_CINNABAR_ORE = register("deepslate_cinnabar_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(CINNABAR_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 3.0f).sounds(BlockSoundGroup.DEEPSLATE)));
    public static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Daoism.MOD_ID, id), block);
    }
}
