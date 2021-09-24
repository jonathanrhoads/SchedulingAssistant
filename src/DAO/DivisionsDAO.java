package DAO;

import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionsDAO {

    public static ObservableList<Division> getDivisions () throws SQLException {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        String stmt = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions;";
        Query.makeQuery(DBConnection.openConnection(), stmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int divisionId = result.getInt("Division_ID");
            String division = result.getString("Division");
            int countryId = result.getInt("Country_ID");
            divisions.add(new Division(divisionId, division, countryId));
        }
        DBConnection.closeConnection();
        return divisions;
    }

}
