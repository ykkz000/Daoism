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
import org.slf4j.Logger;

@Environment(EnvType.CLIENT)
public class DaoismClient implements ClientModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();
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
    }
}
