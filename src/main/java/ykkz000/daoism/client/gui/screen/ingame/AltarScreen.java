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

package ykkz000.daoism.client.gui.screen.ingame;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import ykkz000.daoism.Daoism;
import ykkz000.daoism.recipe.TalismanRecipe;
import ykkz000.daoism.screen.AltarScreenHandler;

import java.util.List;

@Environment(EnvType.CLIENT)
public class AltarScreen extends HandledScreen<AltarScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Daoism.MOD_ID, "textures/gui/container/altar.png");
    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;

    public AltarScreen(AltarScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        --this.titleY;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        this.renderBackground(context);
        int i = this.x;
        int j = this.y;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int k = (int) (41.0f * this.scrollAmount);
        context.drawTexture(TEXTURE, i + 119, j + 15 + k, 176 + (this.shouldScroll() ? 0 : 12), 0, 12, 15);
        int l = this.x + 52;
        int m = this.y + 14;
        int n = this.scrollOffset + 12;
        this.renderRecipeBackground(context, mouseX, mouseY, l, m, n);
        this.renderRecipeIcons(context, l, m, n);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
        int i = this.x + 52;
        int j = this.y + 14;
        int k = this.scrollOffset + 12;
        List<TalismanRecipe> list = this.handler.getRecipes();
        for (int l = this.scrollOffset; l < k && l < this.handler.getRecipes().size(); ++l) {
            int m = l - this.scrollOffset;
            int n = i + m % 4 * 16;
            int o = j + m / 4 * 18 + 2;
            if (x < n || x >= n + 16 || y < o || y >= o + 18) continue;
            TalismanRecipe recipe = list.get(l);
            if (this.client != null && this.client.world != null && this.client.player != null) {
                ItemStack stack = recipe.getOutput(this.client.world.getRegistryManager());
                List<Text> tooltips = Screen.getTooltipFromItem(this.client, stack);
                tooltips.add(Daoism.I18N.translate("text", "experience_consume", recipe.getExperience())
                        .formatted(Formatting.BLUE));
                if (recipe.getExperience() > this.client.player.experienceLevel) {
                    tooltips.add(Daoism.I18N.translate("text", "experience_not_enough").formatted(Formatting.RED));
                }
                context.drawTooltip(this.textRenderer, tooltips, stack.getTooltipData(), x, y);
            }
        }
    }

    private void renderRecipeBackground(DrawContext context, int mouseX, int mouseY, int x, int y, int scrollOffset) {
        List<TalismanRecipe> list = this.handler.getRecipes();
        for (int i = this.scrollOffset; i < scrollOffset && i < this.handler.getRecipes().size(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            int n = this.backgroundHeight;
            if (this.client != null && this.client.player != null && this.client.player.experienceLevel < list.get(i).getExperience()) {
                n += 54;
            } else if (i == this.handler.getCurrentIndex()) {
                n += 18;
            } else if (mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18) {
                n += 36;
            }
            context.drawTexture(TEXTURE, k, m - 1, 0, n, 16, 18);
        }
    }

    private void renderRecipeIcons(DrawContext context, int x, int y, int scrollOffset) {
        List<TalismanRecipe> list = this.handler.getRecipes();
        for (int i = this.scrollOffset; i < scrollOffset && i < this.handler.getRecipes().size(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            if (this.client != null && this.client.world != null) {
                context.drawItem(list.get(i).getOutput(this.client.world.getRegistryManager()), k, m);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.mouseClicked = false;
        int i = this.x + 52;
        int j = this.y + 14;
        int k = this.scrollOffset + 12;
        for (int l = this.scrollOffset; l < k; ++l) {
            int m = l - this.scrollOffset;
            double d = mouseX - (double) (i + m % 4 * 16);
            double e = mouseY - (double) (j + m / 4 * 18);
            if (this.client != null && (!(d >= 0.0) || !(e >= 0.0) || !(d < 16.0) || !(e < 18.0) || !this.handler.onButtonClick(this.client.player, l)))
                continue;
            if (this.client != null && this.client.interactionManager != null) {
                this.client.interactionManager.clickButton(this.handler.syncId, l);
            }
            return true;
        }
        i = this.x + 119;
        j = this.y + 9;
        if (mouseX >= (double) i && mouseX < (double) (i + 12) && mouseY >= (double) j && mouseY < (double) (j + 54)) {
            this.mouseClicked = true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.mouseClicked && this.shouldScroll()) {
            int i = this.y + 14;
            int j = i + 54;
            this.scrollAmount = ((float) mouseY - (float) i - 7.5f) / ((float) (j - i) - 15.0f);
            this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0f, 1.0f);
            this.scrollOffset = (int) ((double) (this.scrollAmount * (float) this.getMaxScroll()) + 0.5) * 4;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (this.shouldScroll()) {
            int i = this.getMaxScroll();
            float f = (float) amount / (float) i;
            this.scrollAmount = MathHelper.clamp(this.scrollAmount - f, 0.0f, 1.0f);
            this.scrollOffset = (int) ((double) (this.scrollAmount * (float) i) + 0.5) * 4;
        }
        return true;
    }

    private boolean shouldScroll() {
        return this.handler.getRecipes().size() > 12;
    }

    protected int getMaxScroll() {
        return (this.handler.getRecipes().size() + 4 - 1) / 4 - 3;
    }
}
