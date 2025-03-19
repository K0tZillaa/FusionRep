package org.fusion.fusionRep.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.fusion.fusionRep.FusionRep;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private final FusionRep plugin;
    @Getter
    private HikariDataSource dataSource;

    public DatabaseManager(FusionRep plugin) {
        this.plugin = plugin;
        setupDataSource();

        if (dataSource != null) {
            initializeDatabase();
        } else {
            plugin.getLogger().severe("Не удалось инициализировать базу данных, так как пул соединений не был создан.");
        }
    }

    private void setupDataSource() {
        HikariConfig config = new HikariConfig();

        String databaseType = plugin.getConfig().getString("database.type", "H2");
        String username = plugin.getConfig().getString("database.user");
        String password = plugin.getConfig().getString("database.password");

        String jdbcUrl;
        if (databaseType.equalsIgnoreCase("MySQL")) {
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                plugin.getLogger().severe("Некорректная конфигурация базы данных!");
                return;
            }
            jdbcUrl = plugin.getConfig().getString("database.url");
        } else if (databaseType.equalsIgnoreCase("H2")) {
            jdbcUrl = "jdbc:h2:./fusion_rep";
            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                plugin.getLogger().severe("Не удалось загрузить драйвер H2: " + e.getMessage());
                return;
            }
        } else {
            plugin.getLogger().severe("Неизвестный тип базы данных: " + databaseType);
            return;
        }

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        try {
            dataSource = new HikariDataSource(config);
            plugin.getLogger().info("Пул соединений успешно создан для " + databaseType);
        } catch (Exception e) {
            plugin.getLogger().severe("Ошибка при создании пула соединений: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void initializeDatabase() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS FUSION_REPUTATION (" +
                "uuid VARCHAR(36) PRIMARY KEY," +
                "player_name  VARCHAR(36)," +
                "player_reputation INTEGER)";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableQuery);
            plugin.getLogger().info("Таблица FUSION_REPUTATION успешно создана или уже существует.");
        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка при создании таблицы игрока: " + e.getMessage());
        }
    }

    public void disconnect() {
        if (dataSource != null) {
            try {
                dataSource.close();
                plugin.getLogger().info("Пул соединений успешно закрыт.");
            } catch (Exception e) {
                plugin.getLogger().severe("Ошибка при закрытии пула соединений: " + e.getMessage());
            }
        }
    }
}