package io.github.divios.jtext;

import io.github.divios.jtext.wrappers.Template;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JText {

    private static final Map<String, JTextBuilder> cache = new HashMap<>();

    static {
        cache.put("default", JTextBuilder.getDefault());
    }

    public static String parse(String s, Template... templates) {
        return cache.get("default")
                .withTemplate(templates)
                .parse(s, null);
    }

    public static JTextBuilder builder() {
        return cache.get("default");
    }

    public static void register(String key, JTextBuilder builder) {
        cache.put(key, builder);
    }

    public static JTextBuilder get(String key) {
        return cache.get(key);
    }

    public static int getVersion() {
        Pattern pattern = Pattern.compile("1\\.([0-9]+)");
        Matcher matcher = pattern.matcher(Bukkit.getBukkitVersion());
        matcher.find();
        return Integer.parseInt(matcher.group(1));
    }

}
