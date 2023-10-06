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

package ykkz000.daoism.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ykkz000.daoism.entity.damage.DaoismDamageTypes;
import ykkz000.daoism.entity.effect.DaoismStatusEffects;
import ykkz000.daoism.entity.mob.ChineseZombieEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "onDeath", at = @At("RETURN"))
    public void onDeath(DamageSource cause, CallbackInfo ci) {
        if (!getThis().isPlayer() && cause.getTypeRegistryEntry().getKey().map(DaoismDamageTypes.DEGRADATION::equals).orElse(false) || cause.getAttacker() instanceof ChineseZombieEntity || getThis().hasStatusEffect(DaoismStatusEffects.DEGRADATION)) {
            ChineseZombieEntity.infect(getThis());
        }
    }

    @Unique
    private LivingEntity getThis() {
        return (LivingEntity) (Object)this;
    }
}
