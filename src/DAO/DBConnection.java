package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String databaseName = "";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/"+databaseName;
    private static final String username = "sqlUser";
    private static final String password = "Pa55w.rd";
    static Connection connection;
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
        connection = (Connection) DriverManager.getConnection(DB_URL,username,password);
    }

    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        connection.close();
    }
}
