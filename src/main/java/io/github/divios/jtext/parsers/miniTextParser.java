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
        if (!adventureIsInstalled()) return s;
        return LegacyComponentSerializer.legacyAmpersand().serialize(
                builder.parse(s));
    }

    public String unParse(final String s) {
        if (!adventureIsInstalled()) return null;
        return null;
    }

    private boolean adventureIsInstalled() {
        try {
            Class.forName(" net.kyori.adventure");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

}