package org.fusion.fusionRep.util;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.fusion.fusionRep.FusionRep;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {
    private final FusionRep plugin;
    public Placeholders(FusionRep plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "fusion";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null || !player.isOnline()) {
            return "";
        }
        Player onlinePlayer = player.getPlayer();
        if (onlinePlayer == null) {
            return "";
        }
        if (params.startsWith("reputation_colored")) {
            return formatReputation(plugin.getDatabaseController().getReputation(onlinePlayer));
        } else if (params.startsWith("reputation")) {
            return "" + plugin.getDatabaseController().getReputation(onlinePlayer);
        }
        return "";
    }

    private String formatReputation(int reputation) {
        if (reputation < 0)
            return "§c" + reputation;
        else if (reputation == 0)
            return "§e" + reputation;
        else
            return "§a" + reputation;
    }
}