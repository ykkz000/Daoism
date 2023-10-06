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
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import ykkz000.daoism.entity.damage.DaoismDamageTypes;

public class DegradationEffect extends StatusEffect {
    public DegradationEffect() {
        super(StatusEffectCategory.HARMFUL, 0x666666);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % (10 >> amplifier) == 1;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        float amount = (entity instanceof PlayerEntity && entity.getHealth() <= entity.getMaxHealth() * 0.2f) ? 0f : entity.getMaxHealth() * 0.2f;
        entity.damage(entity.getDamageSources().create(DaoismDamageTypes.DEGRADATION), amount);
    }
}
