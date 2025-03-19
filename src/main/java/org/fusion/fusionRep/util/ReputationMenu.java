package org.fusion.fusionRep.util;

import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.other.EventCreator;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.fusion.fusionRep.FusionRep;

import java.util.Objects;

public class ReputationMenu {
    private FusionRep plugin;
    private static Material backgroundMaterial = Material.BARRIER;
    private Player target;
    private String plusReputationSender;
    private String plusReputationTarget;
    private String minusReputationSender;
    private String minusReputationTarget;
    private String itemPlusReputationButtonTitle;
    private String itemMinusReputationButtonTitle;
    private String itemExitButtonTitle;
    private final String menuTitle;


    public ReputationMenu(FusionRep plugin) {
        this.plugin = plugin;
        this.menuTitle = plugin.getConfig().getString("localization.reputation_menu.title", "Player reputation %player%");
        this.plusReputationSender = plugin.getConfig().getString("localization.reputation_menu.plus_reputation_sender", "You have increased the player's reputation, his current reputation: %reputation%");
        this.plusReputationTarget = plugin.getConfig().getString("localization.reputation_menu.plus_reputation_target", "Player %player% has increased your reputation, your current reputation is: %reputation%");
        this.minusReputationSender = plugin.getConfig().getString("localization.reputation_menu.minus_reputation_sender", "You have lowered the player's reputation, his current reputation: %reputation%");
        this.minusReputationTarget = plugin.getConfig().getString("localization.reputation_menu.minus_reputation_target", "Player %player% has lowered your reputation, your current reputation is: %reputation%");
        this.itemPlusReputationButtonTitle = plugin.getConfig().getString("localization.reputation_menu.plus_reputation_button.title", "+REP");
        this.itemMinusReputationButtonTitle = plugin.getConfig().getString("localization.reputation_menu.minus_reputation_button.title", "-REP");
        this.itemExitButtonTitle = plugin.getConfig().getString("localization.reputation_menu.exit_button.title", "Exit");
    }

    EventCreator<InventoryClickEvent> event = new EventCreator<>(InventoryClickEvent.class, event -> {
        if(event.getClickedInventory() == null) return;
        Player player = (Player) event.getWhoClicked();
        Audience sender = (Audience) player;
        Audience targetAudience = (Audience) target;
        if (Objects.requireNonNull(event.getCurrentItem()).getType() == backgroundMaterial || Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(itemExitButtonTitle)) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
            player.closeInventory();
        } else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(itemPlusReputationButtonTitle)) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
            plusReputation();
            sender.sendMessage(plugin.getFormatMessage().parse(player, plusReputationSender.replace("%reputation%", String.valueOf(getReputation())).replace("%player%", target.getName())));
            targetAudience.sendMessage(plugin.getFormatMessage().parse(player, plusReputationTarget.replace("%reputation%", String.valueOf(getReputation())).replace("%player%", player.getName())));
            player.closeInventory();
        } else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(itemMinusReputationButtonTitle)) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
            minusReputation();
            sender.sendMessage(plugin.getFormatMessage().parse(player, minusReputationSender.replace("%reputation%", String.valueOf(getReputation())).replace("%player%", target.getName())));
            targetAudience.sendMessage(plugin.getFormatMessage().parse(player, minusReputationTarget.replace("%reputation%", String.valueOf(getReputation())).replace("%player%", player.getName())));
            player.closeInventory();
        }
    });

    public void openMenu(Player player, Player target) {
        this.target = target;
        RyseInventory.builder()
                .title(menuTitle.replace("%player%", player.getName()))
                .rows(3)
                .clearAndSafe()
                .listener(event)
                .provider(getInventoryStyle(target))
                .build(plugin)
                .open(player);
    }

    private void plusReputation() {
        int rep = plugin.getDatabaseController().getReputation(target);
        plugin.getDatabaseController().setReputation(target, rep + 1);
    }

    private void minusReputation() {
        int rep = plugin.getDatabaseController().getReputation(target);
        plugin.getDatabaseController().setReputation(target, rep - 1);
    }

    private int getReputation() {
        return plugin.getDatabaseController().getReputation(target);
    }

    private InventoryProvider getInventoryStyle(Player target) {
        plugin.getMenuProviders().setTarget(target);
        backgroundMaterial = Material.getMaterial(plugin.getCfg().getString("settings.menu.background_material", "air").toUpperCase());

        return switch (plugin.getCfg().getInt("settings.menu.style")) {
            case 2 -> plugin.getMenuProviders().getInventoryStyleTwo();
            case 3 -> plugin.getMenuProviders().getInventoryStyleThree();
            default -> plugin.getMenuProviders().getInventoryStyleOne();
        };
    }
}
