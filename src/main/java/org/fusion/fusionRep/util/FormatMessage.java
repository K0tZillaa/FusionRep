package org.fusion.fusionRep.util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FormatMessage {

    public Component parse(Player sender, Player receiver, String message) {
        return getComponent(sender, receiver, message);
    }

    public Component parse(Player sender, String message) {
        return getComponent(sender, sender, message);
    }

    public Component parse(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    @NotNull
    private Component getComponent(Player sender, Player receiver, String message) {
        message = message.replace("&", "§");

        StringBuilder builder = new StringBuilder(message);

        for (int i = 0; i < builder.length() - 1; i++) {
            if ((builder.charAt(i) == '&' || builder.charAt(i) == '§') && Character.isLetterOrDigit(builder.charAt(i + 1))) {
                char code = builder.charAt(i + 1);
                String colorTag = getColorTag(code);
                if (!colorTag.isEmpty()) {
                    builder.replace(i, i + 2, "<" + colorTag + ">");
                }
            }
        }

        message = builder.toString();

        if (message.contains("%fusionchat_name_color%") || message.contains("%fusionchat_text_color%")) {
            String receiverParsedNameColor = PlaceholderAPI.setPlaceholders(receiver, "%fusionchat_name_color%");
            String receiverParsedTextColor = PlaceholderAPI.setPlaceholders(receiver, "%fusionchat_text_color%");

            message = message.replace("%fusionchat_name_color%", receiverParsedNameColor)
                    .replace("%fusionchat_text_color%", receiverParsedTextColor);
        }

        String parsedMessage = PlaceholderAPI.setPlaceholders(sender, message);
        return MiniMessage.miniMessage().deserialize(parsedMessage);
    }

    private static String getColorTag(char code) {
        return switch (code) {
            case '0' -> "black";
            case '1' -> "dark_blue";
            case '2' -> "dark_green";
            case '3' -> "dark_aqua";
            case '4' -> "dark_red";
            case '5' -> "dark_purple";
            case '6' -> "gold";
            case '7' -> "gray";
            case '8' -> "dark_gray";
            case '9' -> "blue";
            case 'a' -> "green";
            case 'b' -> "aqua";
            case 'c' -> "red";
            case 'd' -> "light_purple";
            case 'e' -> "yellow";
            case 'f' -> "white";
            case 'k' -> "obfuscated";
            case 'l' -> "bold";
            case 'm' -> "strikethrough";
            case 'n' -> "underlined";
            case 'o' -> "italic";
            case 'r' -> "reset";
            default -> "";
        };
    }

    public String formatReputation(int reputation) {
        if (reputation < 0)
            return "<red>" + reputation;
        else if (reputation == 0)
            return "<yellow>" + reputation;
        else
            return "<green>" + reputation;
    }
}
