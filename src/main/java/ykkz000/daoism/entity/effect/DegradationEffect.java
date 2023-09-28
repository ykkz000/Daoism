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
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import ykkz000.daoism.entity.damage.DaoismDamageTypes;
import ykkz000.daoism.entity.mob.ChineseZombieEntity;

public class DegradationEffect extends TickStatusEffect {
    public DegradationEffect() {
        super(StatusEffectCategory.HARMFUL, 0x666666);
    }

    @Override
    public boolean canUpdateEffect(int tick, LivingEntity entity, int amplifier) {
        return tickTime % (80 >> amplifier) == 0 || entity.isDead();
    }

    @Override
    public void updateEffect(int tick, LivingEntity entity, int amplifier) {
        if (entity.getWorld() instanceof ServerWorld) {
            if (entity instanceof PlayerEntity playerEntity) {
                playerEntity.damage(playerEntity.getDamageSources().create(DaoismDamageTypes.DEGRADATION), playerEntity.getHealth() / 2f);
            } else {
                ChineseZombieEntity.infect(entity);
            }
        }
    }
}
