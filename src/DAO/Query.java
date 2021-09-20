package DAO;

import java.sql.ResultSet;
import java.sql.Statement;

public class Query {
    private static String query;
    private static Statement statement;
    private static ResultSet result;

    public static void makeQuery(String q){
        query = q;

    }
}
