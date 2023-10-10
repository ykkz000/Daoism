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

package ykkz000.daoism;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.gen.GenerationStep;
import org.slf4j.Logger;
import ykkz000.daoism.entity.DaoismEntityTypes;
import ykkz000.daoism.util.I18nUtil;
import ykkz000.daoism.world.gen.feature.DaoismPlacedFeatures;

public class Daoism implements ModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "daoism";
    public static final I18nUtil I18N = new I18nUtil(Daoism.MOD_ID);
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        try {
            Class.forName("ykkz000.daoism.block.DaoismBlocks");
            Class.forName("ykkz000.daoism.block.entity.DaoismBlockEntityTypes");
            Class.forName("ykkz000.daoism.item.DaoismItems");
            Class.forName("ykkz000.daoism.item.DaoismItemGroups");
            Class.forName("ykkz000.daoism.entity.DaoismEntityTypes");
            Class.forName("ykkz000.daoism.entity.effect.DaoismStatusEffects");
            Class.forName("ykkz000.daoism.enchantment.DaoismEnchantments");
            Class.forName("ykkz000.daoism.recipe.DaoismRecipeTypes");
            Class.forName("ykkz000.daoism.recipe.DaoismRecipeSerializers");
            Class.forName("ykkz000.daoism.screen.DaoismScreenHandlerTypes");
            Class.forName("ykkz000.daoism.world.gen.feature.DaoismPlacedFeatures");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Cannot Initialize this MOD, caused by", e);
        }
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_DECORATION, DaoismPlacedFeatures.ORE_CINNABAR_LOWER);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_DECORATION, DaoismPlacedFeatures.ORE_CINNABAR_UPPER);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.ZOMBIE), SpawnGroup.MONSTER, DaoismEntityTypes.CHINESE_ZOMBIE, 100, 2, 4);
    }
}
