package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.InHouse;
import models.inventory;
import models.Outsourced;
import models.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;



public class modifyPartController implements Initializable {
    private Stage stage;
    private Scene scene;

    @FXML
    TextField modifyPartID;
    @FXML
    TextField modifyPartName;
    @FXML
    TextField modifyPartInv;
    @FXML
    TextField modifyPartMax;
    @FXML
    TextField modifyPartMin;
    @FXML
    TextField modifyPartPrice;
    @FXML
    TextField addPartMachineID;
    @FXML
     Button savebtnpartmod;
    @FXML
    Button modifyPartCancelButton;
    @FXML
    Label MachineIDorCompany;
    @FXML
    RadioButton ModifyPartInHouseRadio;
    @FXML
    RadioButton ModifyPartOutsourcedRadio;

    private int existingIndex = 0;

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
                alert.setContentText("minimum can't be larger than the maximum");
                alert.showAndWait();
                break;
            case 3: alertwarn.setTitle("WARNING");
                alert.setHeaderText("Input error");
                alert.setContentText("Incorrect Value");
                alert.showAndWait();
                break;

                }}



    /**
     * takes local data from main screen, sets modified text from parts and modifypartID set as disabled.
     * @param part setting selected parts
     * @param selectedIndex setting existing index of 0 to selected index
     */
    public void sendPart(int selectedIndex, Part part) {

        existingIndex = selectedIndex;
        if (part instanceof InHouse) {
            ModifyPartInHouseRadio.setSelected(true);
            addPartMachineID.setText(String.valueOf(((InHouse) part).getMachineID()));
        } else {
            ModifyPartOutsourcedRadio.setSelected(true);
            addPartMachineID.setText(((Outsourced) part).getCompanyName());
        }

        modifyPartID.setText(String.valueOf(part.getId()));
        modifyPartName.setText(String.valueOf(part.getName()));
        modifyPartInv.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMax.setText(String.valueOf(part.getMax()));
        modifyPartMin.setText(String.valueOf(part.getMin()));
        modifyPartID.setDisable(true);
    }

    /**
     * setting machineid text when radio button is selected
     * @param event representation of the action when a button is pressed.
     */
    @FXML
    public void addPartOutsourced(ActionEvent event) {


        MachineIDorCompany.setText("Company Name");
        ModifyPartInHouseRadio.setSelected(false);
        autoGenValue();
    }

    /**
     * setting machineid text when radio button is selected
     * @param event representation of the action when a button is pressed.
     */

    @FXML
    public void addPartInHouse(ActionEvent event) {

        MachineIDorCompany.setText("Machine ID");
        ModifyPartOutsourcedRadio.setSelected(false);
        autoGenValue();
    }



    /**
     * modifypart ID text set to disabled
     */
    private void autoGenValue() {
        if (modifyPartID.getText().isBlank() || modifyPartID.getText().isEmpty())
            modifyPartID.setText("Auto Gen - Disabled");
    }

    /**
     *  This handles the save button on the modify part screen, entailing new double or int initialized
     *  to the value represented by the specified string, getters, inventory checks,
     *  and loading it to the main screen.
     * @param event representation of the action when a button is pressed.
     * @throws IOException constructs an IOException with null as its error detail message produced by a failed or interrupted I/O operation.
     */
    @FXML
    void modifyPartSaveButton(ActionEvent event) throws IOException {
        try {
            int partID = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            int inStock = Integer.parseInt(modifyPartInv.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            String companyName;
            int machineID;



            if (ModifyPartOutsourcedRadio.isSelected()) {
                companyName = addPartMachineID.getText();
                Outsourced updatedPart = new Outsourced(partID, name, price, inStock, min, max, companyName);
                inventory.updatePart(existingIndex, updatedPart);
            }

            if (ModifyPartInHouseRadio.isSelected()) {
                machineID = Integer.parseInt(addPartMachineID.getText());
                InHouse updatedPart = new InHouse(partID, name, price, inStock, min, max, machineID);
                inventory.updatePart(existingIndex, updatedPart);
            }

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
     * This handles the modify part cancel button press loading back to the main scene.
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

