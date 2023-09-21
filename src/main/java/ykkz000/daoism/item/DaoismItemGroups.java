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

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import ykkz000.daoism.Daoism;

import java.util.function.Supplier;

public class DaoismItemGroups {
    public static final ItemGroup DAOISM = register("daoism",
            () -> new ItemStack(DaoismItems.TRANSMISSION_TALISMAN),
            (displayContext, entries) -> {
                entries.add(DaoismItems.ALTAR);

                entries.add(DaoismItems.CINNABAR);
                entries.add(DaoismItems.CINNABAR_ORE);
                entries.add(DaoismItems.DEEPSLATE_CINNABAR_ORE);

                entries.add(DaoismItems.TRANSMISSION_TALISMAN);
                entries.add(DaoismItems.RAIN_TALISMAN);
                entries.add(DaoismItems.LIGHTNING_TALISMAN);

                entries.add(DaoismItems.MERCURY_WOODEN_SWORD);
                entries.add(DaoismItems.MERCURY_STONE_SWORD);
                entries.add(DaoismItems.MERCURY_IRON_SWORD);
                entries.add(DaoismItems.MERCURY_GOLDEN_SWORD);
                entries.add(DaoismItems.MERCURY_DIAMOND_SWORD);
                entries.add(DaoismItems.MERCURY_NETHERITE_SWORD);
            });

    private static ItemGroup register(String id, Supplier<ItemStack> icon, ItemGroup.EntryCollector entryCollector) {
        Identifier identifier = new Identifier(Daoism.MOD_ID, id);
        return Registry.register(Registries.ITEM_GROUP, identifier, FabricItemGroup.builder()
                .icon(icon)
                .displayName(Text.translatable(identifier.toTranslationKey("itemGroup")))
                .entries(entryCollector)
                .build());
    }
}
