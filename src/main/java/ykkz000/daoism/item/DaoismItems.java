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

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ykkz000.daoism.Daoism;
import ykkz000.daoism.block.DaoismBlocks;

public class DaoismItems {
    public static final Item ALTAR = register("altar", new BlockItem(DaoismBlocks.ALTAR, new Item.Settings()));
    public static final Item CINNABAR = register("cinnabar", new Item(new Item.Settings()));
    public static final Item CINNABAR_ORE = register("cinnabar_ore", new BlockItem(DaoismBlocks.CINNABAR_ORE, new Item.Settings()));
    public static final Item DEEPSLATE_CINNABAR_ORE = register("deepslate_cinnabar_ore", new BlockItem(DaoismBlocks.DEEPSLATE_CINNABAR_ORE, new Item.Settings()));
    public static final Item CINNABAR_BLOCK = register("cinnabar_block", new BlockItem(DaoismBlocks.CINNABAR_BLOCK, new Item.Settings()));
    public static final Item EMPTY_TALISMAN = register("empty_talisman", new EmptyTalismanItem(new Item.Settings().maxCount(16).fireproof()));
    public static final Item TRANSMISSION_TALISMAN = register("transmission_talisman", new TransmissionTalismanItem(new Item.Settings().maxCount(1).maxDamage(32).fireproof()));
    public static final Item RAIN_TALISMAN = register("rain_talisman", new RainTalismanItem(new Item.Settings().maxCount(16).fireproof()));
    public static final Item LIGHTNING_TALISMAN = register("lightning_talisman", new LightningTalismanItem(new Item.Settings().maxCount(16).fireproof()));
    public static final Item IMMOBILIZATION_TALISMAN = register("immobilization_talisman", new ImmobilizationTalismanItem(new Item.Settings().maxCount(16).fireproof()));
    public static final Item MERCURY_WOODEN_SWORD = register("mercury_wooden_sword", new MercurySwordItem(ToolMaterials.WOOD, 3, -2.4f, new Item.Settings()));
    public static final Item MERCURY_STONE_SWORD = register("mercury_stone_sword", new MercurySwordItem(ToolMaterials.STONE, 3, -2.4f, new Item.Settings()));
    public static final Item MERCURY_IRON_SWORD = register("mercury_iron_sword", new MercurySwordItem(ToolMaterials.IRON, 3, -2.4f, new Item.Settings()));
    public static final Item MERCURY_GOLDEN_SWORD = register("mercury_golden_sword", new MercurySwordItem(ToolMaterials.GOLD, 3, -2.4f, new Item.Settings()));
    public static final Item MERCURY_DIAMOND_SWORD = register("mercury_diamond_sword", new MercurySwordItem(ToolMaterials.DIAMOND, 3, -2.4f, new Item.Settings()));
    public static final Item MERCURY_NETHERITE_SWORD = register("mercury_netherite_sword", new MercurySwordItem(ToolMaterials.NETHERITE, 3, -2.4f, new Item.Settings()));
    public static <T extends Item> T register(String id, T item) {
        return Registry.register(Registries.ITEM, new Identifier(Daoism.MOD_ID, id), item);
    }
}
