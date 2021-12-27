package io.github.divios.jtext.parsers;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexColorParser {

    private static final Pattern HEX_PATTERN = Pattern.compile("#([A-Fa-f0-9]){6}");
    private static final char COLOR_CHAR = ChatColor.COLOR_CHAR;

    public String parse(String s) {
        Matcher matcher = HEX_PATTERN.matcher(s);
        StringBuilder buffer = new StringBuilder(s.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group();
            matcher.appendReplacement(buffer, "" + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
                    + COLOR_CHAR + group.charAt(6)
            );
        }
        return ChatColor.translateAlternateColorCodes(COLOR_CHAR, matcher.appendTail(buffer).toString());
    }

    public String unparse(String s) {
        return "#" + ChatColor.stripColor(s);
    }
}
