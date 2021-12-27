package io.github.divios.jtext.wrappers;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Class to hold the mather and replacer strings.
 */
public record Template(String matcher, Supplier<String> replacer) {

    public static Template of(String matcher, String replacer) {
        return new Template(matcher, () -> replacer);
    }

    public static Template of(String matcher, Supplier<String> replacer) {
        return new Template(matcher, replacer);
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
