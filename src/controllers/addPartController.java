package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class  addPartController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public Button addPartSaveButton;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartInventory;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartMin;
    @FXML
    private TextField addPartMachineID;
    @FXML
    public TextField addPartID;
    @FXML
    private RadioButton PartInHouseRadio;
    @FXML
    private RadioButton PartOutsourcedRadio;
    @FXML
    private Label MachineIdOrCompanyName;
    /**
     *  This is used to display pop up-messages quickly without having to rewrite .setTitle, .setHeader, etc. multiple times. I made the most popular alerts used for a specific window to save time writing more code.
     * @param alertType This constructs different errors, warnings, and confirmation messages to the user.
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        Alert alertwarn = new Alert(Alert.AlertType.WARNING);

        switch (alertType) {
            case 1: alert.setTitle("Error");
                alert.setHeaderText("Invalid Inventory");
                alert.setContentText("Inventory must be between the min and the max");
                alert.showAndWait();
                break;
            case 2: alert.setTitle("Error");
                alert.setHeaderText("Invalid Volume");
                alert.setContentText("Minimum can't be more than the maximum");
                alert.showAndWait();
                break;
            case 3: alertwarn.setTitle("Input Error");
                alert.setHeaderText("Invalid");
                alert.setContentText("Incorrect value");
                alert.showAndWait();
                break;


        }}

    /**
     *  This is used to set the textfeild to campany name when the corresponding radio button is clicked and so that only one of two radio buttons can be selected at a time.
     * @param event representation of the action when a button is pressed.
     */
    public void AddPartOutsourced(ActionEvent event) {
        PartInHouseRadio.setSelected(false);
        MachineIdOrCompanyName.setText("Company Name");
        autoGenValue();
    }
    /**
     *  This is used to set the textfeild to machine id  when the corresponding radio button is clicked and so that only one of two radio buttons can be selected at a time.
     * @param event representation of the action when a button is pressed.
     */
    public void AddPartInHouse(ActionEvent event) {
        PartOutsourcedRadio.setSelected(false);
        MachineIdOrCompanyName.setText("Machine ID");
        autoGenValue();
    }

    /**
     * sets text to disabled for part ID. A random part ID gets generated with math.random
     */
    private void autoGenValue() {
        addPartID.setText("Auto Gen - Disabled");
    }

    /**
     *  This handles the save button on the add part screen, entailing new double or int initialized to the value represented by the specified string, getters, inventory checks, and loading it to the main screen.
     * @param event representation of the action when a button is pressed.
     * @throws IOException constructs an IOException with null as its error detail message produced by a failed or interrupted I/O operation.
     */
    public void AddPartSaveButton(ActionEvent event) throws IOException {
        try {

            int randomID = (int) (Math.random() * 100);
            String name = addPartName.getText();
            double price = Double.parseDouble(addPartPrice.getText());
            int inStock = Integer.parseInt(addPartInventory.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(addPartMax.getText());
            String companyName;
            int machineID = 0;



            //Inventory should be between the min and max values.
            if (inStock < min || max < inStock) {
                displayAlert(1);
                return;
            }
            //Min should be less than max.
            else if (max < min) {
                displayAlert(2);
                return;
            }


            if (PartInHouseRadio.isSelected()) {
                machineID = Integer.parseInt(addPartMachineID.getText());
                models.InHouse addPart = new models.InHouse(randomID, name, price, inStock, min, max, machineID);
                models.inventory.addPart(addPart);
            }

            if (PartOutsourcedRadio.isSelected()) {
                companyName = addPartMachineID.getText();
                models.Outsourced addPart = new models.Outsourced(randomID, name, price, inStock, min, max, companyName);
                models.inventory.addPart(addPart);
            }



            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("../views/main1.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            displayAlert(3);
            return;
        }
    }

    /**
     * Controller initialization interface. Called to initialize a controller after its root element has been completely processed
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /**
     * This handles the add part cancel button press loading back to the main scene.
     * @param event representation of the action when a button is pressed.
     * @throws IOException Constructs an IOException with null as its error detail message produced by a failed or interrupted I/O operation.
     */
    public void handleButtonCancel(ActionEvent event) throws IOException {
        Parent root = load(getClass().getResource("../views/main1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}