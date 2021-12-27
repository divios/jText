package io.github.divios.jtext;

import io.github.divios.jtext.wrappers.Template;

import java.util.HashMap;
import java.util.Map;

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

}
