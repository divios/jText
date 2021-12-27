package io.github.divios.jtext.wrappers;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Class to hold the mather and replacer strings.
 */
public class Template {

    private final String matcher;
    private final Supplier<String> replacer;

    public static Template of(String matcher, String replacer) {
        return new Template(matcher, () -> replacer);
    }

    public static Template of(String matcher, char replacer) {
        return new Template(matcher, () -> String.valueOf(replacer));
    }

    public static Template of(String mather, double replacer) {
        return new Template(mather, () -> String.valueOf(replacer));
    }

    public static Template of(String mather, int replacer) {
        return new Template(mather, () -> String.valueOf(replacer));
    }

    public static Template of(String mather, long replacer) {
        return new Template(mather, () -> String.valueOf(replacer));
    }

    public static Template of(String mather, float replacer) {
        return new Template(mather, () -> String.valueOf(replacer));
    }

    public static Template of(String mather, boolean replacer) {
        return new Template(mather, () -> String.valueOf(replacer));
    }

    public static Template of(String matcher, Supplier<String> replacer) {
        return new Template(matcher, replacer);
    }

    private Template(String matcher, Supplier<String> replacer) {
        this.matcher = matcher;
        this.replacer = replacer;
    }

    public String getMatcher() {
        return matcher;
    }

    public String getReplacer() {
        return replacer.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return Objects.equals(matcher, template.matcher) && Objects.equals(replacer, template.replacer);
    }

    @Override
    public int hashCode() {
        return matcher.hashCode();
    }

    @Override
    public String toString() {
        return "Template{" +
                "mather='" + matcher + '\'' +
                ", replacer='" + replacer + '\'' +
                '}';
    }
}
