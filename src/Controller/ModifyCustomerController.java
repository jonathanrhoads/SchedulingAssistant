package Controller;

import DAO.CountriesDAO;
import DAO.CustomerDAO;
import DAO.DivisionsDAO;
import Model.Country;
import Model.Customer;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The type Modify customer controller.
 */
public class ModifyCustomerController implements Initializable {
    /**
     * The Country combo box.
     */
    public ComboBox<String> countryComboBox;
    /**
     * The Division combo box.
     */
    public ComboBox<String> divisionComboBox;
    /**
     * The Customer id text field.
     */
    public TextField customerIdTextField;
    /**
     * The Name text field.
     */
    public TextField nameTextField;
    /**
     * The Address text field.
     */
    public TextField addressTextField;
    /**
     * The Phone text field.
     */
    public TextField phoneTextField;
    /**
     * The Postal code text field.
     */
    public TextField postalCodeTextField;
    /**
     * The Save button.
     */
    public Button saveButton;
    /**
     * The Cancel button.
     */
    public Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Country> countries = CountriesDAO.getCountries();
            ObservableList<String> countryNames = FXCollections.observableArrayList();
            Customer customer = MainMenuController.customerToModify;

            for(Country country : countries){
                countryNames.add(country.getCountry());
            }
            countryComboBox.setItems(countryNames);

            customerIdTextField.setText(String.valueOf(customer.getCustomerId()));
            nameTextField.setText(customer.getCustomerName());
            addressTextField.setText(customer.getAddress());
            phoneTextField.setText(customer.getPhone());
            postalCodeTextField.setText(customer.getPostalCode());
            countryComboBox.setValue(customer.getCountry());
            divisionComboBox.setValue(customer.getDivision());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This determines the divisions to be put into the combo box with respect to the country.
     * @param actionEvent country combo box selection
     * @throws SQLException
     */
    @FXML
    private void onActionCountryComboBox(ActionEvent actionEvent) throws SQLException {
        try{
            ObservableList<Division> divisions = DivisionsDAO.getDivisions();
            ObservableList<String> countryMatches = FXCollections.observableArrayList();
            ObservableList<Country> countries = CountriesDAO.getCountries();
            String selectedCountry = String.valueOf(countryComboBox.getSelectionModel().getSelectedItem());
            int countryId = 0;
            for(Country country : countries){
                if(selectedCountry.equals(country.getCountry())){
                    countryId = country.getCountryId();
                }
            }

            for(Division division : divisions) {
                if(division.getCountryId() == countryId){
                    countryMatches.add(division.getDivision());
                }
            }
            divisionComboBox.setItems(countryMatches);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Attempts to update the customer in the database.
     * @param actionEvent actionEvent
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    private void onActionSave(ActionEvent actionEvent) {
        try {
            int customerId = Integer.parseInt(customerIdTextField.getText());
            String customerName = nameTextField.getText();
            String address = addressTextField.getText();
            String country = String.valueOf(countryComboBox.getSelectionModel().getSelectedItem());
            String division = String.valueOf(divisionComboBox.getSelectionModel().getSelectedItem());
            String postalCode = postalCodeTextField.getText();
            String phone = phoneTextField.getText();
            int divisionId = getDivisionId();

            Customer customer = new Customer(customerId, customerName, address, postalCode,
                    phone, division, country, divisionId);

            if(isValidCustomer(customer)){
                CustomerDAO.updateCustomer(customer);

                Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 880, 850);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please ensure all fields are filled out and correct.");
            alert.showAndWait();
        }
    }

    /**
     * Checks to see if the customer is valid
     * @param customer customer to check
     * @return
     */
    private boolean isValidCustomer(Customer customer) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect Input");

        if(customer.getCustomerName().isEmpty() || customer.getCustomerName().isBlank()){
            alert.setContentText("The name cannot be blank or empty.");
            alert.showAndWait();
            return false;
        } else if (customer.getAddress().isEmpty() || customer.getAddress().isBlank()){
            alert.setContentText("The address cannot be blank or empty.");
            alert.showAndWait();
            return false;
        } else if (customer.getPostalCode().isEmpty() || customer.getPostalCode().isBlank()){
            alert.setContentText("The postal code cannot be blank or empty.");
            alert.showAndWait();
            return false;
        } else if (customer.getPhone().isEmpty() || customer.getPhone().isBlank()){
            alert.setContentText("The phone number field cannot be blank or empty.");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    /**
     * Gets the division ID.
     * @return int
     * @throws SQLException SQLException
     */
    private int getDivisionId() throws SQLException {
        ObservableList<Division> divisions = DivisionsDAO.getDivisions();
        String divisionName = String.valueOf(divisionComboBox.getSelectionModel().getSelectedItem());
        for(Division division : divisions){
            if(division.getDivision().equals(divisionName)){
                return division.getDivisionId();
            }
        }
        return 0;
    }

    /**
     * Takes user back to main menu.
     * @param actionEvent actionEvent
     * @throws IOException
     */
    @FXML
    private void onActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 880, 850);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
