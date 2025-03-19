package org.fusion.fusionRep.util;

import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.fusion.fusionRep.FusionRep;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.UUID;

@Getter
public class MenuProviders {
    @Setter
    private Player target;
    private Material backgroundMaterial;
    private final FusionRep plugin;

    public MenuProviders(FusionRep plugin) {
        this.plugin = plugin;
    }

    private final InventoryProvider inventoryStyleOne = new InventoryProvider() {
        @Override
        public void init(Player player, InventoryContents contents) {
            for (int i = 0; i < 27; i++) {
                contents.set(i, createItem(backgroundMaterial, "", "Закрыть меню"));
            }
            contents.set(0, createItem(Material.LIME_STAINED_GLASS_PANE, "+РЕП", "+1 к репутации игрока"));
            contents.set(1, createItem(Material.LIME_STAINED_GLASS_PANE, "+РЕП", "+1 к репутации игрока"));
            contents.set(2, createItem(Material.LIME_STAINED_GLASS_PANE, "+РЕП", "+1 к репутации игрока"));
            contents.set(6, createItem(Material.RED_STAINED_GLASS_PANE, "-РЕП", "-1 к репутации игрока"));
            contents.set(7, createItem(Material.RED_STAINED_GLASS_PANE, "-РЕП", "-1 к репутации игрока"));
            contents.set(8, createItem(Material.RED_STAINED_GLASS_PANE, "-РЕП", "-1 к репутации игрока"));

            contents.set(9, createItem(Material.LIME_STAINED_GLASS_PANE, "+РЕП", "+1 к репутации игрока"));
            contents.set(10, createItem(Material.LIME_STAINED_GLASS_PANE, "+РЕП", "+1 к репутации игрока"));
            contents.set(11, createItem(Material.LIME_STAINED_GLASS_PANE, "+РЕП", "+1 к репутации игрока"));
            contents.set(13, createClickedPlayerHead());
            contents.set(15, createItem(Material.RED_STAINED_GLASS_PANE, "-РЕП", "-1 к репутации игрока"));
            contents.set(16, createItem(Material.RED_STAINED_GLASS_PANE, "-РЕП", "-1 к репутации игрока"));
            contents.set(17, createItem(Material.RED_STAINED_GLASS_PANE, "-РЕП", "-1 к репутации игрока"));

            contents.set(18, createItem(Material.LIME_STAINED_GLASS_PANE, "+РЕП", "+1 к репутации игрока"));
            contents.set(19, createItem(Material.LIME_STAINED_GLASS_PANE, "+РЕП", "+1 к репутации игрока"));
            contents.set(20, createItem(Material.LIME_STAINED_GLASS_PANE, "+РЕП", "+1 к репутации игрока"));
            contents.set(24, createItem(Material.RED_STAINED_GLASS_PANE, "-РЕП", "-1 к репутации игрока"));
            contents.set(25, createItem(Material.RED_STAINED_GLASS_PANE, "-РЕП", "-1 к репутации игрока"));
            contents.set(26, createItem(Material.RED_STAINED_GLASS_PANE, "-РЕП", "-1 к репутации игрока"));
        }
    };

    private final InventoryProvider inventoryStyleTwo = new InventoryProvider() {
        @Override
        public void init(Player player, InventoryContents contents) {
            for (int i = 0; i < 27; i++) {
                contents.set(i, createItem(backgroundMaterial, "", "Закрыть меню"));
            }
            contents.set(10, createItem(Material.EMERALD_BLOCK, "+РЕП", "+1 к репутации игрока"));
            contents.set(13, createClickedPlayerHead());
            contents.set(16, createItem(Material.REDSTONE_BLOCK, "-РЕП", "-1 к репутации игрока"));
            contents.set(26, createItem(Material.ARROW, "Выйти", "Закрыть меню"));
        }
    };

    private final InventoryProvider inventoryStyleThree = new InventoryProvider() {
        @SneakyThrows
        @Override
        public void init(Player player, InventoryContents contents) {
            for (int i = 0; i < 27; i++) {
                contents.set(i, createItem(backgroundMaterial, "", "Закрыть меню"));
            }
            contents.set(10, createHead(plugin.getCfg().getString("settings.menu.plus_head_url", "http://textures.minecraft.net/texture/6c48ddfdcd6d98a1b0aa3c71e8dad4edde732a68b2b0a5ab142600dca7587c32"), "+РЕП", "+1 к репутации игрока"));
            contents.set(13, createClickedPlayerHead());
            contents.set(16, createHead(plugin.getCfg().getString("settings.menu.minus_head_url", "http://textures.minecraft.net/texture/6f05afec2a6ec675cd5505a8f44bb6a4d556935689528321ead4edef685f2d10"), "-РЕП", "-1 к репутации игрока"));
            contents.set(26, createItem(Material.ARROW, "Выйти", "Закрыть меню"));
        }
    };

    private ItemStack createClickedPlayerHead() {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
            skullMeta.setDisplayName(ChatColor.GOLD + target.getName());
            head.setItemMeta(skullMeta);
        }
        return head;
    }

    private ItemStack createHead(String url, String name, String lore) throws MalformedURLException {
        PlayerProfile profile = getProfile(new URL(url));
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta != null) {
            if (!name.isEmpty()) meta.setDisplayName(ChatColor.RESET + name);
            if (!lore.isEmpty()) meta.setLore(Collections.singletonList(ChatColor.RESET + "" + ChatColor.GRAY + lore));
            meta.setOwnerProfile(profile);
            head.setItemMeta(meta);
        }
        return head;
    }

    private PlayerProfile getProfile(URL url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.fromString("92864445-51c5-4c3b-9039-517c9927d1b4"));
        PlayerTextures textures = profile.getTextures();
        textures.setSkin(url);
        profile.setTextures(textures);
        return profile;
    }

    private ItemStack createItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.RESET + name);
            if (!lore.isEmpty()) meta.setLore(Collections.singletonList(ChatColor.RESET + "" + ChatColor.GRAY + lore));
            item.setItemMeta(meta);
        }
        return item;
    }

    public void setBackgroundMaterial() {
        backgroundMaterial = Material.getMaterial(plugin.getCfg().getString("settings.menu.background_material", "air").toUpperCase());
    }
}
