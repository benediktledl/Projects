package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class BLDatabaseConnector {
    private static final Logger logger = LogManager.getLogger(BLDatabaseConnector.class);
    private Connection connection = null;

    public BLDatabaseConnector(String dbhost, String dbname, String dbuser, String password) throws SQLException {
        String dburl = "jdbc:mysql://" + dbhost + "/" + dbname + "?useSSL=false";
        this.connection = DriverManager.getConnection(dburl, dbuser, password);
        logger.info("Datenbankverbindung erfolgreich hergestellt.");
    }

    public void closeConnection() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
            logger.info("Datenbankverbindung geschlossen.");
        }
    }

    public void createDatabase(String dbName) throws SQLException {
        String sql = "CREATE DATABASE " + dbName;
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.info("Datenbank " + dbName + " erfolgreich erstellt.");
            System.out.println("Datenbank " + dbName + " erfolgreich erstellt.");
        } catch (SQLException e) {
            logger.error("Fehler beim Erstellen der Datenbank " + dbName + ": " + e.getMessage());
            System.out.println("Fehler beim Erstellen der Datenbank " + dbName + ": " + e.getMessage());
            throw e;
        }
    }

    public void dropDatabase(String dbName) throws SQLException {
        String sql = "DROP DATABASE IF EXISTS " + dbName;
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.info("Datenbank " + dbName + " erfolgreich gelöscht.");
            System.out.println("Datenbank " + dbName + " erfolgreich gelöscht.");
        } catch (SQLException e) {
            logger.error("Fehler beim Löschen der Datenbank " + dbName + ": " + e.getMessage());
            System.out.println("Fehler beim Löschen der Datenbank " + dbName + ": " + e.getMessage());
            throw e;
        }
    }

    public void createTable(String tableName, String columns, String types) throws SQLException {
        String sql = "CREATE TABLE ? (?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            statement.setString(2, columns + " " + types);
            statement.executeUpdate();
            logger.info("Tabelle " + tableName + " erfolgreich erstellt.");
            System.out.println("Tabelle " + tableName + " erfolgreich erstellt.");
        } catch (SQLException e) {
            logger.error("Fehler beim Erstellen der Tabelle " + tableName + ": " + e.getMessage());
            System.out.println("Fehler beim Erstellen der Tabelle " + tableName + ": " + e.getMessage());
            throw e;
        }
    }

    public void dropTable(String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.info("Tabelle " + tableName + " erfolgreich gelöscht.");
            System.out.println("Tabelle " + tableName + " erfolgreich gelöscht.");
        } catch (SQLException e) {
            logger.error("Fehler beim Löschen der Tabelle " + tableName + ": " + e.getMessage());
            System.out.println("Fehler beim Löschen der Tabelle " + tableName + ": " + e.getMessage());
            throw e;
        }
    }

    public void insertData(String tableName, String data) {
        String[] values = data.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(tableName).append(" VALUES (");
        for (int i = 0; i < values.length; i++) {
            String value = values[i].trim();
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            } else {
                try {
                    Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    value = "\"" + value + "\"";
                }
            }
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("?");
            values[i] = value;
        }
        sb.append(")");
        String sql = sb.toString();
        logger.info("SQL statement: " + sql); // log the SQL statement

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                String value = values[i].trim();
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                    statement.setString(i + 1, value);
                } else {
                    statement.setInt(i + 1, Integer.parseInt(value));
                }
            }
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully");
                logger.info("Data inserted successfully");
            } else {
                System.out.println("Error inserting data: no rows affected");
                logger.error("Error inserting data: no rows affected");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
            logger.error("Error inserting data: " + e.getMessage());
        }
    }

    public void showTableData(String tableName) {
        String sql = "SELECT * FROM `" + tableName + "`";
        logger.info("SQL statement: " + sql); // log the SQL statement

        try (
                PreparedStatement statement = this.connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + " ");
            }
            System.out.println();
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            logger.error("Error showing table data: " + e.getMessage());
        }
    }
}