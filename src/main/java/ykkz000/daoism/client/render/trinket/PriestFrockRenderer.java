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

package ykkz000.daoism.client.render.trinket;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import ykkz000.daoism.Daoism;
import ykkz000.daoism.client.render.ExtendedRenderLayers;
import ykkz000.daoism.item.DaoismItems;

@Environment(EnvType.CLIENT)
public class PriestFrockRenderer implements TrinketRenderer {
    private static final Identifier TEXTURE = new Identifier(Daoism.MOD_ID, "textures/trinket/priest_frock.png");
    private static final float MIN_X = 0.0f;
    private static final float MIN_Y = 0.0f;
    private static final float MIN_Z = 0.0f;
    private static final float MAX_X = 1.0f;
    private static final float MAX_Y = 1.2f;
    private static final float MAX_Z = 0.03125f;
    @Override
    @SuppressWarnings("unchecked")
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
            if (!TrinketsApi.getTrinketComponent(entity).map(component -> component.isEquipped(DaoismItems.PRIEST_FROCK)).orElse(true)) {
                return;
            }
            matrices.push();
            TrinketRenderer.translateToChest(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, abstractClientPlayerEntity);
            matrices.translate(-0.5, -0.375, 0.25);
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(MathHelper.PI / 6.0f));
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ExtendedRenderLayers.FROCK.apply(TEXTURE));

            vertexConsumer.vertex(matrix4f, MIN_X, MIN_Y, MIN_Z).texture(0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MIN_X, MAX_Y, MIN_Z).texture(0.0f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MAX_X, MAX_Y, MIN_Z).texture(0.5f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MAX_X, MIN_Y, MIN_Z).texture(0.5f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();

            vertexConsumer.vertex(matrix4f, MIN_X, MIN_Y, MAX_Z).texture(0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MAX_X, MIN_Y, MAX_Z).texture(0.5f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MAX_X, MAX_Y, MAX_Z).texture(0.5f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MIN_X, MAX_Y, MAX_Z).texture(0.0f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();

            vertexConsumer.vertex(matrix4f, MIN_X, MIN_Y, MIN_Z).texture(0.5f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MAX_X, MIN_Y, MIN_Z).texture(1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MAX_X, MIN_Y, MAX_Z).texture(1.0f, 0.03125f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MIN_X, MIN_Y, MAX_Z).texture(0.0f, 0.03125f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();

            vertexConsumer.vertex(matrix4f, MIN_X, MAX_Y, MIN_Z).texture(0.5f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MIN_X, MAX_Y, MAX_Z).texture(0.0f, 0.03125f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MAX_X, MAX_Y, MAX_Z).texture(1.0f, 0.03125f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MAX_X, MAX_Y, MIN_Z).texture(1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();

            vertexConsumer.vertex(matrix4f, MAX_X, MIN_Y, MIN_Z).texture(0.5f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MAX_X, MAX_Y, MIN_Z).texture(0.5f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MAX_X, MAX_Y, MAX_Z).texture(0.53125f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MAX_X, MIN_Y, MAX_Z).texture(0.53125f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();

            vertexConsumer.vertex(matrix4f, MIN_X, MIN_Y, MIN_Z).texture(0.5f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MIN_X, MIN_Y, MAX_Z).texture(0.53125f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MIN_X, MAX_Y, MAX_Z).texture(0.53125f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
            vertexConsumer.vertex(matrix4f, MIN_X, MAX_Y, MIN_Z).texture(0.5f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();

            matrices.pop();
        }
    }
}
