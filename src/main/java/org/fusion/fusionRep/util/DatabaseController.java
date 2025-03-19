package org.fusion.fusionRep.util;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.entity.Player;
import org.fusion.fusionRep.FusionRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseController {
    private final FusionRep plugin;
    private HikariDataSource dataSource;

    public DatabaseController(FusionRep plugin) {
        this.plugin = plugin;
        dataSource = plugin.getDatabaseManager().getDataSource();
    }

    public void setReputation(Player player, int reputation) {
        updateDataSource();

        String databaseType = plugin.getConfig().getString("database.type", "H2");
        String query;

        if (databaseType.equalsIgnoreCase("MySQL")) {
            query = "INSERT INTO FUSION_REPUTATION (uuid, player_name, player_reputation) " +
                    "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE player_reputation = ?";
        } else {
            query = "MERGE INTO FUSION_REPUTATION (uuid, player_name, player_reputation) " +
                    "KEY (uuid) VALUES (?, ?, ?)";
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            UUID playerUUID = player.getUniqueId();
            String playerName = player.getName();

            statement.setString(1, playerUUID.toString());
            statement.setString(2, playerName);
            statement.setInt(3, reputation);

            if (databaseType.equalsIgnoreCase("MySQL")) {
                statement.setInt(4, reputation);
            }

            statement.executeUpdate();
            plugin.getLogger().info("Репутация игрока " + playerName + " успешно обновлена: " + reputation);

        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка при обновлении репутации игрока: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getReputation(Player player) {
        updateDataSource();

        String selectQuery = "SELECT player_reputation FROM FUSION_REPUTATION WHERE uuid = ?";
        int reputation = 0;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {

            UUID playerUUID = player.getUniqueId();
            statement.setString(1, playerUUID.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    reputation = resultSet.getInt("player_reputation");
                }
            }

            plugin.getLogger().info("Получена репутация игрока " + player.getName() + ": " + reputation);

        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка при получении репутации игрока: " + e.getMessage());
            e.printStackTrace();
        }

        return reputation;
    }

    private void updateDataSource() {
        dataSource = plugin.getDatabaseManager().getDataSource();
    }
}
