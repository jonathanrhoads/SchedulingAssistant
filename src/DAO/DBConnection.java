package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Db connection.
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    /**
     * The constant connection.
     */
    public static Connection connection;  // Connection Interface

    /**
     * Open connection connection.
     *
     * @return the connection
     */
    public static Connection openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            return connection;
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return null;
    }

    /**
     * Close connection.
     */
    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}

