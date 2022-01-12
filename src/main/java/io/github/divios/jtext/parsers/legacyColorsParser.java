package io.github.divios.jtext.parsers;

import net.md_5.bungee.api.ChatColor;

public class legacyColorsParser {

    public String parse(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String unparse(String s) {
        return ChatColor.stripColor(s);
    }

}
