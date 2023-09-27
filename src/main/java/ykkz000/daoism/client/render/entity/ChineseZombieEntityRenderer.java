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

package ykkz000.daoism.client.render.entity;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import ykkz000.daoism.Daoism;
import ykkz000.daoism.client.render.entity.model.ChineseZombieEntityModel;
import ykkz000.daoism.entity.mob.ChineseZombieEntity;

public class ChineseZombieEntityRenderer extends BipedEntityRenderer<ChineseZombieEntity, ChineseZombieEntityModel<ChineseZombieEntity>> {
    private static final Identifier TEXTURE = new Identifier(Daoism.MOD_ID, "textures/entity/chinese_zombie.png");
    public ChineseZombieEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ChineseZombieEntityModel<>(context.getPart(EntityModelLayers.ZOMBIE)), 0.5f);
    }

    @Override
    public Identifier getTexture(ChineseZombieEntity entity) {
        return TEXTURE;
    }
}
