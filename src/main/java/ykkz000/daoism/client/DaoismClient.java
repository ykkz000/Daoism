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

package ykkz000.daoism.client;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import ykkz000.daoism.Daoism;
import ykkz000.daoism.skill.DaoismSkills;
import ykkz000.skill.api.client.SkillClientAPI;

@Environment(EnvType.CLIENT)
public class DaoismClient implements ClientModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String SKILL_CATEGORY = Util.createTranslationKey("category", new Identifier(Daoism.MOD_ID, "skill"));
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        try {
            Class.forName("ykkz000.daoism.client.gui.screen.ingame.DaoismHandledScreens");
            Class.forName("ykkz000.daoism.client.render.block.entity.DaoismBlockEntityRendererFactories");
            Class.forName("ykkz000.daoism.client.render.entity.DaoismEntityRenderers");
            Class.forName("ykkz000.daoism.client.render.trinket.DaoismTrinketRenderers");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Cannot Initialize this MOD, caused by", e);
        }
        SkillClientAPI.bindSkill(DaoismSkills.FLASH.getId(), KeyBindingHelper.registerKeyBinding(new KeyBinding(
                DaoismSkills.FLASH.getTranslationKey(), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, SKILL_CATEGORY
        )));
    }
}
