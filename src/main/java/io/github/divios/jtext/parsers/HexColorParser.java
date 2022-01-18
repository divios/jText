package io.github.divios.jtext.parsers;

import io.github.divios.jtext.wrappers.Template;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexColorParser {

    private static final Pattern HEX_PATTERN = Pattern.compile("#([A-Fa-f0-9]){6}");
    private static final char COLOR_CHAR = ChatColor.COLOR_CHAR;

    public String parse(String s) {
        Matcher matcher = HEX_PATTERN.matcher(s);
        StringBuilder buffer = new StringBuilder(s.length() + 4 * 8);

        int pos = 0;
        while (matcher.find()) {
            String group = matcher.group();
            buffer.append(s, pos, matcher.start());
            pos = matcher.end();
            buffer.append(COLOR_CHAR + "x" + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
                    + COLOR_CHAR + group.charAt(6));
        }
        return buffer.append(s, pos, s.length()).toString();
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

    public @Nullable Template parseAsTemplate(final String s) {
        if (!(s.startsWith("#") && s.length() == 7)) return null;

        return Template.of(s, parse(s));
    }

}
