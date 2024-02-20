/*
 * Daoism
 * Copyright (C) 2024  ykkz000
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

package ykkz000.daoism.skill;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import ykkz000.daoism.item.DaoismItems;
import ykkz000.skill.api.skill.Skill;

public abstract class DaoismSkill extends Skill {
    protected int expiredDamage;

    public DaoismSkill(int cooldownTime, int expiredDamage) {
        super(cooldownTime);
        this.expiredDamage = expiredDamage;
    }

    @Override
    public boolean canUse(PlayerEntity player, World world) {
        return TrinketsApi.getTrinketComponent(player)
                .map(component -> component.getEquipped(DaoismItems.PRIEST_FROCK).stream().findFirst()
                        .map(pair -> pair.getRight().getMaxDamage() - pair.getRight().getDamage() >= expiredDamage)
                        .orElse(false)).orElse(false);
    }

    @Override
    public void use(PlayerEntity player, World world) {
        TrinketsApi.getTrinketComponent(player)
                .map(component -> component.getEquipped(DaoismItems.PRIEST_FROCK).stream().findFirst()
                        .map(pair -> {
                            pair.getRight().damage(expiredDamage, player, e -> e.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
                            return true;
                        }));
    }
}
