package io.github.diegoalegil.garageos.repositories.sqlite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLiteConnectionManager {

    protected static final String DEFAULT_DATABASE_PATH = "data/garageos.db";
    private static final String LEGACY_DATABASE_PATH = "src/main/resources/data/sqlite/garageos.db";

    protected Connection getConnection() throws SQLException {
        ensureDatabaseFileExists();
        return getConnection(DEFAULT_DATABASE_PATH);
    }

    protected Connection getConnection(String databasePath) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }

    private void ensureDatabaseFileExists() throws SQLException {
        Path databasePath = Path.of(DEFAULT_DATABASE_PATH);
        Path legacyDatabasePath = Path.of(LEGACY_DATABASE_PATH);

        try {
            Path parentDirectory = databasePath.getParent();
            if (parentDirectory != null) {
                Files.createDirectories(parentDirectory);
            }

            if (Files.notExists(databasePath) && Files.exists(legacyDatabasePath)) {
                Files.copy(legacyDatabasePath, databasePath);
            }
        } catch (IOException e) {
            throw new SQLException("No se pudo preparar la base de datos local", e);
        }
    }
}
