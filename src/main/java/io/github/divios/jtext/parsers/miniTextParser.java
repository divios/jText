package io.github.divios.jtext.parsers;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class miniTextParser {

    private static MiniMessage builder;

    public miniTextParser() {
        if (builder == null && adventureIsInstalled())
            builder = MiniMessage.builder()
                    .markdown()
                    .build();
    }

    public String parse(final String s) {
        System.out.println("Trying to parse with miniText");
        if (builder == null) return s;
        System.out.println("Parsed with miniText");
        return LegacyComponentSerializer.legacyAmpersand().serialize(
                builder.parse(s));
    }

    public String unParse(final String s) {
        if (builder == null) return null;
        return null;
    }

    private boolean adventureIsInstalled() {
        try {
            Class.forName("net.kyori.adventure");
        } catch (ClassNotFoundException e) {
            System.out.println("Adventure not found");
            return false;
        }
        System.out.println("Adventure found");
        return true;
    }

}