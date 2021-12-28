package io.github.divios.jtext.parsers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class miniTextParser {

    private static final MiniMessage builder = MiniMessage.builder()
            .markdown()
            .build();

    public String parse(final String s) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(
                builder.parse(s));
    }

    public Component unParse(final String s) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(s);
    }

}
