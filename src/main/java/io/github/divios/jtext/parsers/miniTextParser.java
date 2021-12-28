package io.github.divios.jtext.parsers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class miniTextParser {

    public String parse(String s) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(
                MiniMessage.miniMessage().parse(s));
    }

    public Component unParse(String s) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(s);
    }

}
