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

import com.google.gson.JsonObject;
import lombok.Getter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;
import ykkz000.daoism.item.DaoismItems;

public class TalismanRecipe implements Recipe<Inventory> {
    protected final Identifier id;
    @Getter
    protected final int experience;
    protected final ItemStack output;

    public TalismanRecipe(Identifier id, int experience, ItemStack output) {
        this.id = id;
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
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DaoismRecipeSerializers.TALISMAN;
    }

    @Override
    public RecipeType<?> getType() {
        return DaoismRecipeTypes.TALISMAN;
    }

    public static class Serializer implements RecipeSerializer<TalismanRecipe> {
        final RecipeFactory factory;
        protected Serializer(RecipeFactory factory) {
            this.factory = factory;
        }
        @Override
        public TalismanRecipe read(Identifier id, JsonObject json) {
            int experience = JsonHelper.getInt(json, "experience");
            String result = JsonHelper.getString(json, "result");
            return factory.create(id, experience, new ItemStack(Registries.ITEM.get(new Identifier(result)), 1));
        }

        @Override
        public TalismanRecipe read(Identifier id, PacketByteBuf buf) {
            int experience = buf.readVarInt();
            String result = buf.readString();
            return factory.create(id, experience, new ItemStack(Registries.ITEM.get(new Identifier(result)), 1));
        }

        @Override
        public void write(PacketByteBuf buf, TalismanRecipe recipe) {
            buf.writeVarInt(recipe.experience);
            buf.writeString(Registries.ITEM.getId(recipe.output.getItem()).toString());
        }

        public interface RecipeFactory {
            TalismanRecipe create(Identifier id, int experience, ItemStack output);
        }
    }
}
