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

package ykkz000.daoism.screen;

import dev.emi.trinkets.api.TrinketsApi;
import lombok.Getter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.apache.commons.compress.utils.Lists;
import ykkz000.daoism.block.DaoismBlocks;
import ykkz000.daoism.item.DaoismItems;
import ykkz000.daoism.recipe.DaoismRecipeTypes;
import ykkz000.daoism.recipe.TalismanRecipe;

import java.util.List;

public class AltarScreenHandler extends ScreenHandler {
    public static final int EXP_TO_DAMAGE = 10;
    private final Inventory input = new SimpleInventory(1) {
        @Override
        public void markDirty() {
            super.markDirty();
            AltarScreenHandler.this.onContentChanged(this);
        }
    };
    private ItemStack inputStack = ItemStack.EMPTY;
    private final Inventory output = new SimpleInventory(1);
    private final ScreenHandlerContext context;
    private final Slot inputSlot;
    private final Slot outputSlot;
    private final World world;
    @Getter
    private List<RecipeEntry<TalismanRecipe>> recipes = Lists.newArrayList();
    @Getter
    private int currentIndex = -1;

    public AltarScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public AltarScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(DaoismScreenHandlerTypes.ALTAR, syncId);
        int i;
        this.context = context;
        this.inputSlot = this.addSlot(new Slot(this.input, 0, 20, 33) {

            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(DaoismItems.EMPTY_TALISMAN);
            }
        });
        this.outputSlot = this.addSlot(new Slot(this.output, 0, 143, 33) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                int cost = player.getAbilities().creativeMode ? 0 : AltarScreenHandler.this.recipes.get(currentIndex).value().getExperience();
                stack.onCraftByPlayer(player.getWorld(), player, stack.getCount());
                if (AltarScreenHandler.canPlayerFreeFromExp(player, cost * EXP_TO_DAMAGE)) {
                    TrinketsApi.getTrinketComponent(player)
                            .map(component -> component.getEquipped(DaoismItems.PRIEST_FROCK).stream().findFirst()
                                    .map(pair -> {
                                        pair.getRight().damage(cost * EXP_TO_DAMAGE, player, e -> e.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
                                        return true;
                                    }).orElse(false));
                } else {
                    player.experienceLevel -= cost;
                }
                ItemStack itemStack = AltarScreenHandler.this.inputSlot.takeStack(1);
                if (!itemStack.isEmpty()) {
                    AltarScreenHandler.this.populateResult();
                }
                super.onTakeItem(player, stack);
            }
        });
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.world = playerInventory.player.getWorld();
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = this.inputSlot.getStack();
        if (!itemStack.isOf(this.inputStack.getItem())) {
            this.inputStack = itemStack.copy();
            this.recipes.clear();
            this.currentIndex = -1;
            this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
            if (!itemStack.isEmpty()) {
                this.recipes = this.world.getRecipeManager().getAllMatches(DaoismRecipeTypes.TALISMAN, this.input, this.world);
            }
        }
        this.sendContentUpdates();
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return ScreenHandler.canUse(context, player, DaoismBlocks.ALTAR);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (this.isInBound(id)) {
            TalismanRecipe recipe = this.recipes.get(id).value();
            if (!this.input.getStack(0).isEmpty() && (player.experienceLevel >= recipe.getExperience() || player.getAbilities().creativeMode || AltarScreenHandler.canPlayerFreeFromExp(player, recipe.getExperience() * EXP_TO_DAMAGE))) {
                this.currentIndex = id;
                this.populateResult();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.output.removeStack(0);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (slot == 1) {
                item.onCraft(itemStack2, player.getWorld());
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (slot == 0 ?
                    !this.insertItem(itemStack2, 2, 38, false) :
                    this.recipes
                            .stream()
                            .map(RecipeEntry::value)
                            .anyMatch(recipe -> recipe.craft(this.input, this.world.getRegistryManager()).getItem().getTranslationKey().equals(itemStack2.getItem().getTranslationKey())) ?
                            !this.insertItem(itemStack2, 0, 1, false) :
                            (slot >= 2 && slot < 29 ?
                                    !this.insertItem(itemStack2, 29, 38, false) :
                                    slot >= 29 && slot < 38 && !this.insertItem(itemStack2, 2, 29, false))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            }
            slot2.markDirty();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot2.onTakeItem(player, itemStack2);
            this.sendContentUpdates();
        }
        return itemStack;
    }

    public boolean isInBound(int id) {
        return id >= 0 && id < this.recipes.size();
    }

    public void populateResult() {
        if (!this.recipes.isEmpty() && this.isInBound(this.currentIndex)) {
            TalismanRecipe recipe = this.recipes.get(this.currentIndex).value();
            ItemStack itemStack = recipe.craft(this.input, this.world.getRegistryManager());
            if (itemStack.isItemEnabled(this.world.getEnabledFeatures())) {
                this.outputSlot.setStackNoCallbacks(itemStack);
            } else {
                this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
            }
        } else {
            this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
        }
        this.sendContentUpdates();
    }


    public static boolean canPlayerFreeFromExp(PlayerEntity player, int expiredDamage) {
        return TrinketsApi.getTrinketComponent(player)
                .map(component -> component.getEquipped(DaoismItems.PRIEST_FROCK).stream().findFirst()
                        .map(pair -> pair.getRight().getMaxDamage() - pair.getRight().getDamage() >= expiredDamage)
                        .orElse(false)).orElse(false);
    }
}
