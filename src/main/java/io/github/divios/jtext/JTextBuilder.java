package io.github.divios.jtext;

import io.github.divios.jtext.parsers.HexColorParser;
import io.github.divios.jtext.parsers.PlaceholderApiParser;
import io.github.divios.jtext.parsers.legacyColorsParser;
import io.github.divios.jtext.parsers.miniTextParser;
import io.github.divios.jtext.wrappers.Template;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class JTextBuilder {

    private static final Map<String, Template> defaultValues;

    private static final legacyColorsParser legacyParser = new legacyColorsParser();
    private static final HexColorParser hexParser = new HexColorParser();
    private static final PlaceholderApiParser papiParser = new PlaceholderApiParser();
    private static final miniTextParser adventureParser = new miniTextParser();

    static {
        defaultValues = new HashMap<>();

        for (defaultTemplates value : defaultTemplates.values()) {
            defaultValues.put(value.name(), Template.of(value.name(), value.getValue()));
            defaultValues.put(value.name().toLowerCase(), Template.of(value.name().toLowerCase(), value.getValue()));
        }
    }

    private final Set<Tag> tags = new HashSet<>();
    private final Map<String, Template> templates = new HashMap<>();
    private boolean parseLegacyColors = true;
    private boolean parseHexColors = false;
    private boolean parsePlaceholdersAPI = false;
    private boolean parseAdventure = false;

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
        clone.templates.put(key, Template.of(key, replacer));
        return clone;
    }

    public JTextBuilder withTemplate(Template... templates) {
        return withTemplates(Arrays.asList(templates));
    }

    public JTextBuilder withTemplates(Collection<Template> templates) {
        JTextBuilder clone = copy();
        templates.forEach(template -> clone.templates.put(template.getMatcher(), template));
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

    public JTextBuilder parseWithAdventure() {
        JTextBuilder clone = copy();
        clone.parseAdventure = true;
        return clone;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Collection<Template> getTemplates() {
        return Collections.unmodifiableCollection(templates.values());
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

    public boolean isParseAdventure() {
        return parseAdventure;
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

    public JTextBuilder setParseAdventure(boolean parseAdventure) {
        JTextBuilder clone = copy();
        clone.parseAdventure = parseAdventure;
        return clone;
    }

    public String parse(String s) {
        return parse(s, null);
    }

    public String parse(String s, Player p) {
        String bufferStr = s;
        for (Tag tag : tags) {
            final StringBuilder buffer = new StringBuilder(s.length() + 4 * 8);
            final Pattern pattern = Pattern.compile(tag.startTag + "(.*?)" + tag.endtag);
            final Matcher matcher = pattern.matcher(bufferStr);

            int pos = 0;
            while (matcher.find()) {
                final String match = matcher.group(1);

                Template template;
                if ((template = defaultValues.get(match)) != null ||
                        (template = templates.get(match)) != null ||
                        (parseHexColors && (template = hexParser.parseAsTemplate(match)) != null)) {
                    buffer.append(bufferStr, pos, matcher.start());
                    pos = matcher.end();
                    buffer.append(template.getReplacer());
                }
            }
            bufferStr = buffer.append(bufferStr, pos, bufferStr.length()).toString();
        }

        if (parsePlaceholdersAPI) bufferStr = papiParser.parse(bufferStr, p);
        if (parseAdventure) bufferStr = adventureParser.parse(bufferStr);
        if (parseLegacyColors) bufferStr = legacyParser.parse(bufferStr);

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
        clone.templates.putAll(templates);

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

        black(rgbToValue(java.awt.Color.black.getRGB())),
        blue(rgbToValue(java.awt.Color.blue.getRGB())),
        cyan(rgbToValue(java.awt.Color.cyan.getRGB())),
        darkGray(rgbToValue(java.awt.Color.darkGray.getRGB())),
        green(rgbToValue(java.awt.Color.green.getRGB())),
        lightGray(rgbToValue(java.awt.Color.lightGray.getRGB())),
        magenta(rgbToValue(java.awt.Color.magenta.getRGB())),
        pink(rgbToValue(java.awt.Color.pink.getRGB())),
        red(rgbToValue(java.awt.Color.red.getRGB())),
        white(rgbToValue(java.awt.Color.white.getRGB())),
        yellow(rgbToValue(java.awt.Color.yellow.getRGB())),
        aqua(rgbToValue(Color.AQUA.asRGB())),
        fuchsia(rgbToValue(Color.FUCHSIA.asRGB())),
        lime(rgbToValue(Color.LIME.asRGB())),
        maroon(rgbToValue(Color.MAROON.asRGB())),
        navy(rgbToValue(Color.NAVY.asRGB())),
        olive(rgbToValue(Color.OLIVE.asRGB())),
        orange(rgbToValue(Color.ORANGE.asRGB())),
        purple(rgbToValue(Color.PURPLE.asRGB())),
        silver(rgbToValue(Color.SILVER.asRGB())),
        teal(rgbToValue(Color.TEAL.asRGB())),
        dark_aqua(ChatColor.DARK_AQUA.toString()),
        dark_blue(ChatColor.DARK_BLUE.toString()),
        dark_gray(ChatColor.DARK_GRAY.toString()),
        dark_green(ChatColor.DARK_GREEN.toString()),
        dark_purple(ChatColor.DARK_PURPLE.toString()),
        dark_red(ChatColor.DARK_RED.toString()),
        light_purple(ChatColor.LIGHT_PURPLE.toString()),
        gold(ChatColor.GOLD.toString()),
        magic(ChatColor.MAGIC.toString()),
        bold(ChatColor.BOLD.toString()),
        italic(ChatColor.ITALIC.toString()),
        underline(ChatColor.UNDERLINE.toString()),
        strikethrough(ChatColor.STRIKETHROUGH.toString()),
        reset(ChatColor.RESET.toString());

        private final String value;

        private static String rgbToValue(int rgb) {
            return hexParser.parse("#" + Integer.toHexString(rgb).substring(2));
        }

        defaultTemplates(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

}
