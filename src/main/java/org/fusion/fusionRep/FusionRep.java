package org.fusion.fusionRep;

import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusion.fusionRep.cmd.MyReputationCommand;
import org.fusion.fusionRep.cmd.ReputationCommand;
import org.fusion.fusionRep.event.ReputationMenuOnClick;
import org.fusion.fusionRep.util.*;

import java.util.Objects;

@Getter
public final class FusionRep extends JavaPlugin {
    FileConfiguration cfg;
    MyReputationCommand myReputationCommand;
    ReputationCommand reputationCommand;
    ReputationMenuOnClick reputationMenuOnClick;
    ReputationMenu reputationMenu;
    MenuProviders menuProviders;
    DatabaseManager databaseManager;
    DatabaseController databaseController;
    FormatMessage formatMessage;

    @Getter
    private final InventoryManager manager = new InventoryManager(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        cfg = getConfig();
        manager.invoke();

        databaseManager = new DatabaseManager(this);
        databaseController = new DatabaseController(this);
        reputationMenu = new ReputationMenu(this);
        menuProviders = new MenuProviders(this);
        menuProviders.setBackgroundMaterial();
        formatMessage = new FormatMessage();

        Objects.requireNonNull(this.getCommand("myreputation")).setExecutor(myReputationCommand = new MyReputationCommand(this));
        Objects.requireNonNull(this.getCommand("reputation")).setExecutor(reputationCommand = new ReputationCommand(this));

        Bukkit.getPluginManager().registerEvents(reputationMenuOnClick = new ReputationMenuOnClick(this), this);

        new Placeholders(this).register();
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.disconnect();
        }
    }
}
