package org.fusion.fusionRep.cmd;

import net.kyori.adventure.audience.Audience;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.fusion.fusionRep.FusionRep;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    private final FusionRep plugin;
    private String reloadMessage;

    public ReloadCommand(FusionRep plugin) {
        this.plugin = plugin;
        updateVariables();
    }

    public void updateVariables() {
        this.reloadMessage = plugin.getCfg().getString("localization.reload_command.reload_message", "The plugin has been successfully reloaded");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Audience sender = (Audience) commandSender;
        plugin.reloadCfg();
        sender.sendMessage(plugin.getFormatMessage().parse(reloadMessage));
        return true;
    }
}
