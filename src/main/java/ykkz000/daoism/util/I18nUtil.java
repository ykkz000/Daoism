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

package ykkz000.daoism.util;

import lombok.Getter;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Getter
public class I18nUtil {
    private final String namespace;

    public I18nUtil(String namespace) {
        this.namespace = namespace;
    }

    public String translationKey(String prefix, String id) {
        return I18nUtil.translationKey(prefix, namespace, id);
    }
    public MutableText translate(String prefix, String id, Object... args) {
        return I18nUtil.translate(prefix, namespace, id, args);
    }

    public static String translationKey(String prefix, String namespace, String id) {
        return new Identifier(namespace, id).toTranslationKey(prefix);
    }
    public static MutableText translate(String prefix, String namespace, String id, Object... args) {
        return Text.translatable(I18nUtil.translationKey(prefix, namespace, id), args);
    }
}
