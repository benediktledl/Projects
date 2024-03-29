package org.example;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws SQLException, IOException {
        String dbhost = "localhost";
        String dbname = "website";
        String dbuser = "website";
        String password = "website";
        BLDatabaseConnector connector = new BLDatabaseConnector(dbhost, dbname, dbuser, password);

        while (true) {
            System.out.println("Was moechtest du tun?");
            System.out.println("1. Datenbank erstellen");
            System.out.println("2. Datenbank loeschen");
            System.out.println("3. Tabelle bearbeiten");
            System.out.println("4. Beenden");

            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();

            if (option == 1) {
                System.out.println("Gib den Namen der Datenbank ein:");
                String dbName = scanner.next();
                connector.createDatabase(dbName);
            } else if (option == 2) {
                System.out.println("Gib den Namen der Datenbank ein:");
                String dbName = scanner.next();
                connector.dropDatabase(dbName);
            } else if (option == 3) {
                // TODO: create table
                System.out.println("Gib den Namen der Tabelle ein:");
                String tableName = scanner.next();

                while (true) {
                    System.out.println("Was moechtest du tun?");
                    System.out.println("1. Tabelle loeschen");
                    System.out.println("2. Daten einfuegen");
                    System.out.println("3. Tabelle anzeigen");
                    System.out.println("4. Daten suchen");;
                    System.out.println("5. Zurueck");

                    int tableOption = scanner.nextInt();
                    if (tableOption == 1) {
                        connector.dropTable(tableName);
                        break;
                    } else if (tableOption == 2) {
                        System.out.println("Gib die Daten ein (kommagetrennt):");
                        String data = scanner.next();
                        connector.insertData(tableName, data);
                    } else if (tableOption == 3) {
                        connector.showTableData(tableName);
                    } else if (tableOption == 4) {
                        System.out.println("Gib den Tabellennamen ein:");
                        String columnName = scanner.next();
                        System.out.println("Gib den Suchwert ein:");
                        String username = scanner.next();
                        connector.searchData(tableName, columnName, username);
                    } else if (tableOption == 5) {
                        break;
                    } else {
                        System.out.println("Ungueltige Option");
                    }
                }
            } else if (option == 4) {
                System.out.println("Auf Wiedersehen!");
                System.exit(0);
            } else {
                System.out.println("Ungueltige Option");
            }
        }
    }
}
