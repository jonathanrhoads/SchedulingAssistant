package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import DAO.DBConnection;

public class Query {

    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    public static void makeQuery(Connection connection, String q) {
        query = q;
        try {
            stmt = connection.createStatement();

            if(query.toUpperCase().startsWith("SELECT"))
                result = stmt.executeQuery(q);
            if(query.toUpperCase().startsWith("DELETE") || query.toUpperCase().startsWith("INSERT") || query.toUpperCase().startsWith("UPDATE"))
                stmt.executeUpdate(q);
        }
        catch (Exception e) {
            System.out.println("Error: "  + e.getMessage());
        }
    }

    public static ResultSet getResult() { return result; }
}
