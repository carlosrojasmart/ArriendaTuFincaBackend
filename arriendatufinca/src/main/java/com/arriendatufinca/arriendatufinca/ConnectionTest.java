package com.arriendatufinca.arriendatufinca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.arriendatufinca.arriendatufinca.Conections.SshTunnelStarter;

public class ConnectionTest {

    public static void main(String[] args) {

        SshTunnelStarter sshTunnelStarter = new SshTunnelStarter();

        try {
            sshTunnelStarter.init();
            System.out.println("SSH Tunnel initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/wikigroup", "root", "12345");
             Statement statement = connection.createStatement()) {

            System.out.println("Database connection established successfully.");

            String createTableSQL = "CREATE TABLE IF NOT EXISTS test ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "PRIMARY KEY (id))";

            statement.execute(createTableSQL);
            System.out.println("Table created successfully.");

            String insertSQL = "INSERT INTO test (name) VALUES ('John')";
            statement.execute(insertSQL);

            /* 
            String dropTableSQL = "DROP TABLE test";
            statement.execute(dropTableSQL);
            System.out.println("Table dropped successfully.");
            */

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
