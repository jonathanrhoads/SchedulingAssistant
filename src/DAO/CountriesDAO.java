package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Countries dao.
 */
public class CountriesDAO {

    /**
     * Gets countries.
     *
     * @return the countries
     * @throws SQLException the sql exception
     */
    public static ObservableList<Country> getCountries() throws SQLException {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        String stmt = "SELECT Country_ID, Country FROM countries;";
        Query.makeQuery(DBConnection.openConnection(), stmt);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int countryId = result.getInt("Country_ID");
            String country = result.getString("Country");
            countries.add(new Country(countryId, country));
        }
        DBConnection.closeConnection();
        return countries;
    }
}
