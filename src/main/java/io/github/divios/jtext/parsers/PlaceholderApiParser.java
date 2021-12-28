package io.github.divios.jtext.parsers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderApiParser {

    public String parse(String s, Player p) {
        if (p != null
                && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            try {
                return PlaceholderAPI.setPlaceholders(p, s);
            } catch (Exception | Error ignored) {
            }
        return s;
    }

}
