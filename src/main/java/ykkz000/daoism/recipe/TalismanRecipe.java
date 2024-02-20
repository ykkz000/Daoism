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

package ykkz000.daoism.recipe;

import lombok.Getter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;
import ykkz000.daoism.item.DaoismItems;

public class TalismanRecipe implements Recipe<Inventory> {
    @Getter
    protected final int experience;
    protected final ItemStack output;

    public TalismanRecipe(int experience, ItemStack output) {
        this.experience = experience;
        this.output = output;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return inventory.getStack(0).isOf(DaoismItems.EMPTY_TALISMAN);
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return this.output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DaoismRecipeSerializers.TALISMAN;
    }

    @Override
    public RecipeType<?> getType() {
        return DaoismRecipeTypes.TALISMAN;
    }

}
