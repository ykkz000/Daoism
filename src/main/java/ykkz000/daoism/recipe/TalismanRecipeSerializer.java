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

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class TalismanRecipeSerializer implements RecipeSerializer<TalismanRecipe> {
    final RecipeFactory factory;
    final Codec<TalismanRecipe> codec;

    protected TalismanRecipeSerializer(RecipeFactory factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("experience").forGetter(recipe -> recipe.experience),
                ItemStack.CUTTING_RECIPE_RESULT_CODEC.forGetter(recipe -> recipe.output)
        ).apply(instance, factory::create));
    }

    @Override
    public TalismanRecipe read(PacketByteBuf buf) {
        int experience = buf.readVarInt();
        String result = buf.readString();
        return factory.create(experience, new ItemStack(Registries.ITEM.get(new Identifier(result)), 1));
    }

    @Override
    public Codec<TalismanRecipe> codec() {
        return codec;
    }

    @Override
    public void write(PacketByteBuf buf, TalismanRecipe recipe) {
        buf.writeVarInt(recipe.experience);
        buf.writeString(Registries.ITEM.getId(recipe.output.getItem()).toString());
    }

    public interface RecipeFactory {
        TalismanRecipe create(int experience, ItemStack output);
    }
}
