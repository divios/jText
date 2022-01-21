package io.github.divios.jtext.parsers;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class gradientColorParser {

    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<gradient:((#[A-Fa-f0-9]{6}):(#[A-Fa-f0-9]{6})>(.*?))</gradient>");

    public String parse(String s) {
        Matcher matcher = GRADIENT_PATTERN.matcher(s);
        StringBuilder buffer = new StringBuilder(s.length() + 4 * 8);

        int pos = 0;
        while (matcher.find()) {
            String startHex = matcher.group(2);
            String endHex = matcher.group(3);
            String text = matcher.group(4);

            buffer.append(s, pos, matcher.start());
            pos = matcher.end();

            buffer.append(rgbGradient(text, Color.decode(startHex), Color.decode(endHex), this::linear));
        }
        return buffer.append(s, pos, s.length()).toString();
    }

    public String unparse(String s) {
        return s; // Is not possible to unParse, not by normal means
    }


    /* Utils */

    @FunctionalInterface
    private interface Interpolator {
        double[] interpolate(double from, double to, int max);
    }

    private double[] linear(double from, double to, int max) {
        final double[] res = new double[max];
        for (int i = 0; i < max; i++) {
            res[i] = from + i * ((to - from) / (max - 1));
        }
        return res;
    }

    private String rgbGradient(String str, Color from, Color to, Interpolator interpolator) {

        // interpolate each component separately
        final double[] red = interpolator.interpolate(from.getRed(), to.getRed(), str.length());
        final double[] green = interpolator.interpolate(from.getGreen(), to.getGreen(), str.length());
        final double[] blue = interpolator.interpolate(from.getBlue(), to.getBlue(), str.length());

        final StringBuilder builder = new StringBuilder();

        // create a string that matches the input-string but has
        // the different color applied to each char
        for (int i = 0; i < str.length(); i++) {
            builder.append(ChatColor.of(new Color(
                            (int) Math.round(red[i]),
                            (int) Math.round(green[i]),
                            (int) Math.round(blue[i]))))
                    .append(str.charAt(i));
        }

        return builder.toString();
    }
}
