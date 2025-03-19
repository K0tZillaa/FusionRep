package org.fusion.fusionRep.util;

import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.other.EventCreator;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.fusion.fusionRep.FusionRep;

import java.util.Objects;

public class ReputationMenu {
    private final FusionRep plugin;
    private static Material backgroundMaterial = Material.BARRIER;
    private Player target;

    public ReputationMenu(FusionRep plugin) {
        this.plugin = plugin;
    }

    EventCreator<InventoryClickEvent> event = new EventCreator<>(InventoryClickEvent.class, event -> {
        if(event.getClickedInventory() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (Objects.requireNonNull(event.getCurrentItem()).getType() == backgroundMaterial || Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase("Выйти")) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
            player.closeInventory();
        } else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase("+РЕП")) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
            plusReputation();
            player.sendMessage(ChatColor.GREEN + "Вы повысили репутацию игрока, его текущая репутация " + getReputation());
            player.closeInventory();
        } else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase("-РЕП")) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
            minusReputation();
            player.sendMessage(ChatColor.RED + "Вы понизили репутацию игрока, его текущая репутация " + getReputation());
            player.closeInventory();
        }
    });

    public void openMenu(Player player, Player target) {
        this.target = target;
        RyseInventory.builder()
                .title("Репутация игрока " + target.getName())
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
