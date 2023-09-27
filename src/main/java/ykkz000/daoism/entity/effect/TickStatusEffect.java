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

public abstract class TickStatusEffect extends StatusEffect {
    protected int tickTime;
    protected TickStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
        this.tickTime = 0;
    }

    public abstract boolean canUpdateEffect(int tick, LivingEntity entity, int amplifier);
    public abstract void updateEffect(int tick, LivingEntity entity, int amplifier);

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        tickTime++;
        if (canUpdateEffect(tickTime, entity, amplifier)) {
            updateEffect(tickTime, entity, amplifier);
        }
    }
}
