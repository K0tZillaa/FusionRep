package org.fusion.fusionRep.util;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.entity.Player;
import org.fusion.fusionRep.FusionRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseController {
    private final FusionRep plugin;
    private HikariDataSource dataSource;

    public DatabaseController(FusionRep plugin) {
        this.plugin = plugin;
        dataSource = plugin.getDatabaseManager().getDataSource();
    }

    public void recordVote(String voterUuid, String targetUuid) {
        String query = "INSERT INTO FUSION_VOTES (voter_uuid, target_uuid) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, voterUuid);
            statement.setString(2, targetUuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Voice recording error: " + e.getMessage());
        }
    }

    public boolean hasVoted(String voterUuid, String targetUuid) {
        String query = "SELECT COUNT(*) FROM FUSION_VOTES WHERE voter_uuid = ? AND target_uuid = ?";
        try (Connection connection = dataSource.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, voterUuid);
            statement.setString(2, targetUuid);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Voice verification error: " + e.getMessage());
        }
        return false;
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

        } catch (SQLException e) {
            plugin.getLogger().severe("Error when updating a player's reputation: " + e.getMessage());
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


        } catch (SQLException e) {
            plugin.getLogger().severe("Error when getting player's reputation: " + e.getMessage());
            e.printStackTrace();
        }

        return reputation;
    }

    public List<String> getTopReputationPlayers() {
        updateDataSource();

        String query = "SELECT player_name, player_reputation FROM FUSION_REPUTATION ORDER BY player_reputation DESC LIMIT 10";
        List<String> topPlayers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String playerName = resultSet.getString("player_name");
                int reputation = resultSet.getInt("player_reputation");
                topPlayers.add(playerName + ":" + reputation);
            }

        } catch (SQLException e) {
            plugin.getLogger().severe("Error in getting top 10 players: " + e.getMessage());
            e.printStackTrace();
        }

        return topPlayers;
    }

    public int getPlayerRank(Player player) {
        updateDataSource();

        String query = "SELECT COUNT(*) + 1 AS rank FROM FUSION_REPUTATION WHERE player_reputation > " +
                "(SELECT player_reputation FROM FUSION_REPUTATION WHERE uuid = ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            UUID playerUUID = player.getUniqueId();
            statement.setString(1, playerUUID.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("rank");
                }
            }

        } catch (SQLException e) {
            plugin.getLogger().severe("Error in determining a player's position: " + e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }

    private void updateDataSource() {
        dataSource = plugin.getDatabaseManager().getDataSource();
    }
}
