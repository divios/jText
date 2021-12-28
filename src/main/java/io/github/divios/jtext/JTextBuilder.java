package io.github.divios.jtext;

import io.github.divios.jtext.parsers.HexColorParser;
import io.github.divios.jtext.parsers.PlaceholderApiParser;
import io.github.divios.jtext.parsers.legacyColorsParser;
import io.github.divios.jtext.wrappers.Template;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class JTextBuilder {

    private final Set<Tag> tags = new HashSet<>();
    private final Set<Template> templates = new HashSet<>();
    private boolean parseLegacyColors = true;
    private boolean parseHexColors = false;
    private boolean parsePlaceholdersAPI = false;
    private boolean parseWithMiniText = false;

    static JTextBuilder getDefault() {
        return new JTextBuilder();
    }

    JTextBuilder() {
        tags.add(Tag.of("<", ">"));    // Default tag
    }

    public JTextBuilder withTag(String startTag, String endTag) {
        JTextBuilder clone = copy();
        clone.tags.add(Tag.of(startTag, endTag));
        return clone;
    }

    public JTextBuilder withTemplate(String key, String replacer) {
        JTextBuilder clone = copy();
        clone.templates.add(Template.of(key, replacer));
        return clone;
    }

    public JTextBuilder withTemplate(Template... templates) {
        return withTemplates(Arrays.asList(templates));
    }

    public JTextBuilder withTemplates(Collection<Template> templates) {
        JTextBuilder clone = copy();
        clone.templates.addAll(templates);
        return clone;
    }

    public JTextBuilder parseChatColors() {
        JTextBuilder clone = copy();
        clone.parseLegacyColors = true;
        return clone;
    }

    public JTextBuilder parseHexColors() {
        JTextBuilder clone = copy();
        clone.parseHexColors = true;
        return clone;
    }

    public JTextBuilder parsePlaceholderAPI() {
        JTextBuilder clone = copy();
        clone.parsePlaceholdersAPI = true;
        return clone;
    }

    public JTextBuilder parseWithMiniText() {
        JTextBuilder clone = copy();
        clone.parseWithMiniText = true;
        return clone;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<Template> getTemplates() {
        return Collections.unmodifiableSet(templates);
    }

    public boolean isParseLegacyColors() {
        return parseLegacyColors;
    }

    public boolean isParseHexColors() {
        return parseHexColors;
    }

    public boolean isParsePlaceholdersAPI() {
        return parsePlaceholdersAPI;
    }

    public JTextBuilder setParseLegacyColors(boolean parseLegacyColors) {
        JTextBuilder clone = copy();
        clone.parseLegacyColors = parseLegacyColors;
        return clone;
    }

    public JTextBuilder setParseHexColors(boolean parseHexColors) {
        JTextBuilder clone = copy();
        clone.parseHexColors = parseHexColors;
        return clone;
    }

    public JTextBuilder setParsePlaceholdersAPI(boolean parsePlaceholdersAPI) {
        JTextBuilder clone = copy();
        clone.parsePlaceholdersAPI = parsePlaceholdersAPI;
        return clone;
    }

    public JTextBuilder setParseWithMiniText(boolean parseWithMiniText) {
        JTextBuilder clone = copy();
        clone.parseWithMiniText = parseWithMiniText;
        return clone;
    }

    public String parse(String s) {
        return parse(s, null);
    }

    public String parse(String s, Player p) {
        String bufferStr = s;
        for (Tag tag : tags) {
            StringBuilder buffer = new StringBuilder(s.length() + 4 * 8);
            Pattern pattern = Pattern.compile(tag.startTag + "(.*?)" + tag.endtag);
            Matcher matcher = pattern.matcher(bufferStr);

            int pos = 0;
            while (matcher.find()) {
                String match = matcher.group(1);

                for (Template template : templates) {
                    if (template.getMatcher().equalsIgnoreCase(match)) {
                        buffer.append(bufferStr, pos, matcher.start());
                        pos = matcher.end();
                        buffer.append(template.getReplacer());
                        break;
                    }
                }
            }
            bufferStr = buffer.append(bufferStr, pos, bufferStr.length()).toString();
        }

        if (parseLegacyColors) bufferStr = new legacyColorsParser().parse(bufferStr);
        if (parseHexColors) bufferStr = new HexColorParser().parse(bufferStr);
        if (parsePlaceholdersAPI) bufferStr = new PlaceholderApiParser().parse(s, p);
        if (parseWithMiniText) bufferStr = MiniMessage.miniMessage().parse(bufferStr).toString();

        return bufferStr;
    }

    public List<String> parse(List<String> collection) {
        return parse(collection, null);
    }

    public List<String> parse(List<String> collection, Player p) {
        List<String> parsedList = new ArrayList<>();
        for (String s : collection) {
            parsedList.add(parse(s, p));
        }

        return parsedList;
    }

    private JTextBuilder copy() {
        JTextBuilder clone = new JTextBuilder();

        clone.tags.addAll(tags);
        clone.templates.addAll(templates);

        clone.parseLegacyColors = parseLegacyColors;
        clone.parseHexColors = parseHexColors;
        clone.parsePlaceholdersAPI = parsePlaceholdersAPI;

        return clone;
    }


    static class Tag {

        private final String startTag;
        private final String endtag;

        public static Tag of(String startTag, String endtag) {
            return new Tag(startTag, endtag);
        }

        private Tag(String startTag, String endtag) {
            this.startTag = startTag;
            this.endtag = endtag;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tag tag = (Tag) o;
            return Objects.equals(startTag, tag.startTag) && Objects.equals(endtag, tag.endtag);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startTag, endtag);
        }

        @Override
        public String toString() {
            return "Tag{" +
                    "startTag='" + startTag + '\'' +
                    ", endtag='" + endtag + '\'' +
                    '}';
        }
    }

    private enum defaultTemplates {
        black(toColor(java.awt.Color.black)),
        blue(toColor(java.awt.Color.blue)),
        cyan(toColor(java.awt.Color.cyan)),
        darkGray(toColor(java.awt.Color.darkGray)),
        green(toColor(java.awt.Color.green)),
        lightGray(toColor(java.awt.Color.lightGray)),
        magenta(toColor(java.awt.Color.magenta)),
        pink(toColor(java.awt.Color.pink)),
        red(toColor(java.awt.Color.red)),
        white(toColor(java.awt.Color.white)),
        yellow(toColor(java.awt.Color.yellow)),
        aqua(Color.AQUA),
        fuchsia(Color.FUCHSIA),
        lime(Color.LIME),
        maroon(Color.MAROON),
        navy(Color.NAVY),
        olive(Color.OLIVE),
        orange(Color.ORANGE),
        purple(Color.PURPLE),
        silver(Color.SILVER),
        teal(Color.TEAL);

        private final Color color;

        private static Color toColor(java.awt.Color color) {
            return Color.fromRGB(color.getRGB());
        }

        defaultTemplates(Color color) {
            this.color = color;
        }

    }

}
