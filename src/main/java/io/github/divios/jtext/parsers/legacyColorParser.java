package io.github.divios.jtext.parsers;

import net.md_5.bungee.api.ChatColor;

public class legacyColorParser {

    public String parse(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String unparse(String s) {
        char[] array = s.toCharArray();
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == ChatColor.COLOR_CHAR && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(array[i + 1]) != -1) {
                array[i] = '&';
                array[i + 1] = Character.toLowerCase(array[i + 1]);
            }
        }
        return new String(array);
    }

}
