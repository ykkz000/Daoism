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

package ykkz000.daoism.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.MobEntity;

public class ImmobilizationEffect extends StatusEffect {
    protected ImmobilizationEffect() {
        super(StatusEffectCategory.HARMFUL, 0xffffff);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "B40BA377-8488-05CC-AFED-A1F538D9D3BD", -1.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "B586C32D-C2A2-A7B2-4A03-900BECE9A22F", -1.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "648BD1BB-4DC5-3C69-6C32-6FAE789E2FD1", -1.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.HORSE_JUMP_STRENGTH, "EDA87A89-495B-3149-2B9D-FD37D45C71C4", -1.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        if (entity instanceof MobEntity mobTarget) {
            mobTarget.setAiDisabled(true);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        if (entity instanceof MobEntity mobTarget) {
            mobTarget.setAiDisabled(false);
        }
    }
}
