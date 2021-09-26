package DAO;

import java.sql.*;

import DAO.DBConnection;

/**
 * The type Query.
 */
public class Query {

    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    /**
     * Make query.
     *
     * @param connection the connection
     * @param q          the q
     */
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

    /**
     * Gets result.
     *
     * @return the result
     */
    public static ResultSet getResult() { return result; }
}
