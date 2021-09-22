package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static User getUser(String username) throws SQLException {

        String stmt = "SELECT * FROM users WHERE User_Name = '" + username + "'";
        Query.makeQuery(DBConnection.openConnection(), stmt);
        User userResult;
        ResultSet result = Query.getResult();
        while(result.next()) {
            int userId = result.getInt("User_ID");
            String name = result.getString("User_Name");
            String password = result.getString("Password");
            return new User(userId, name, password);
        }
        DBConnection.closeConnection();
        return null;
    }

    public static ObservableList<User> getUsers() throws SQLException {

        ObservableList<User> users = FXCollections.observableArrayList();
        String stmt = "SELECT * FROM users";
        Query.makeQuery(DBConnection.openConnection(), stmt);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int userId = result.getInt("User_ID");
            String name = result.getString("User_Name");
            String password = result.getString("Password");
            users.add(new User(userId, name, password));
        }
        DBConnection.closeConnection();
        return users;
    }
}
