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

package ykkz000.daoism.skill;

import net.minecraft.util.Identifier;
import ykkz000.daoism.Daoism;
import ykkz000.skill.api.SkillMainAPI;
import ykkz000.skill.api.skill.Skill;

public class DaoismSkills {
    public static final Skill FLASH = SkillMainAPI.registerSkill(new Identifier(Daoism.MOD_ID, "flash"), new FlashSkill());
}
