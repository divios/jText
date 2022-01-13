package io.github.divios.jtext.parsers;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class miniTextParser {

    private static MiniMessage builder;

    public miniTextParser() {
        if (adventureIsInstalled())
            builder = MiniMessage.miniMessage();
    }

    public String parse(final String s) {
        if (builder == null) return s;
        return LegacyComponentSerializer.legacyAmpersand().serialize(
                builder.parse(s));
    }

    public String unParse(final String s) {
        if (builder == null) return null;
        return null;
    }

    private boolean adventureIsInstalled() {
        try {
            Class.forName("net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

}