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

package ykkz000.daoism.world.gen.feature;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;
import ykkz000.daoism.Daoism;

public class DaoismPlacedFeatures {
    public static final RegistryKey<PlacedFeature> ORE_CINNABAR_LOWER = DaoismPlacedFeatures.of("ore_cinnabar_lower");
    public static final RegistryKey<PlacedFeature> ORE_CINNABAR_UPPER = DaoismPlacedFeatures.of("ore_cinnabar_upper");
    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Daoism.MOD_ID, id));
    }
}
