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
        } catch (SQLException e) {
            logger.error("Fehler beim Erstellen der Datenbank " + dbName + ": " + e.getMessage());
            throw e;
        }
    }

    public void dropDatabase(String dbName) throws SQLException {
        String sql = "DROP DATABASE IF EXISTS " + dbName;
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.info("Datenbank " + dbName + " erfolgreich geloescht.");
        } catch (SQLException e) {
            logger.error("Fehler beim Loeschen der Datenbank " + dbName + ": " + e.getMessage());
            throw e;
        }
    }

    public void createTable(String tableName, String columns, String types) throws SQLException {
        String sql = "CREATE TABLE " + tableName + " (" + columns + ")";
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.info("Tabelle " + tableName + " erfolgreich erstellt.");
        } catch (SQLException e) {
            logger.error("Fehler beim Erstellen der Tabelle " + tableName + ": " + e.getMessage());
            throw e;
        }
    }

    public void dropTable(String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.info("Tabelle " + tableName + " erfolgreich geloescht.");
        } catch (SQLException e) {
            logger.error("Fehler beim Loeschen der Tabelle " + tableName + ": " + e.getMessage());
            throw e;
        }
    }

    public void insertData(String tableName, String data) {
        String[] values = data.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(tableName).append(" VALUES (");
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(values[i]);
        }
        sb.append(")");

        String sql = sb.toString();
        logger.info("SQL statement: " + sql); // log the SQL statement

        try (PreparedStatement statement = this.connection.prepareStatement(sql)){
            statement.executeUpdate();
            logger.info("Data inserted successfully");
        } catch (SQLException e) {
            logger.error("Error inserting data: " + e.getMessage());
        }
    }

    public void showTableData(String tableName) {
        String sql = "SELECT * FROM " + tableName;
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