package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Products;
import models.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;


public class AddProductController implements Initializable {

    private Stage stage;
    private Scene scene;

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductMin;

    @FXML
    private TableView<Part> associatedProductTable;
    @FXML
    private TableView<Part> addProductTable;
    @FXML
    private TableColumn<?, ?> addProductPartIDCol;

    @FXML
    private TableColumn<?, ?> addPartNameCol;
    @FXML
    private TableColumn<?, ?> associatedProductIDCol;
    @FXML
    private TableColumn<?, ?> addProductPriceCol;
    @FXML
    private TableColumn<?, ?> addProductInventoryCol;

    @FXML
    private TableColumn<?, ?> associatedPartNameCol;

    @FXML
    private TableColumn<?, ?> associatedPriceCol;
    @FXML
    private TableColumn<?, ?> associatedInventoryCol;
    @FXML
    private TextField addProductSearchBox;
    @FXML
    TextField addProductID;


    @FXML
    Button addProductSaveButton;

    @FXML
    Button addProductAddButton;

    @FXML
    Button removeAssocPartButton;
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
            case 4: alertwarn.setTitle("WARNING");
                alert.setHeaderText("Input error");
                alert.setContentText("Select the part from the list");
                alert.showAndWait();
                break;
            case 5: alert.setTitle("Error");
                alert.setHeaderText("Invalid");
                alert.setContentText("part not found");
                alert.showAndWait();
                break;
        }
    }

    /**
     *  This handles the save button on the add product screen, entailing new double or int initialized to the value represented by the specified string, getters, inventory checks, and loading it to the main screen.
     * @param event representation of the action when a button is pressed.
     * @throws IOException constructs an IOException with null as its error detail message produced by a failed or interrupted I/O operation.
     */
    @FXML
    void handleButtonProductSaved(ActionEvent event) throws IOException {
        try {

            String name = addProductName.getText();
            int randomID= (int)(Math.random() * 10000 );
            int stock = Integer.parseInt(addProductInv.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());
            double price = Double.parseDouble(addProductPrice.getText());


            if (min > stock || stock > max) {
                displayAlert(1);
                return;
            }

           else if (min >= max) {
              displayAlert(2);
                return;
            }
            Products product = new Products(randomID, name, price, stock, min, max);


            for (Part part : associatedPartsList) {
                if (part != associatedPartsList)
                    product.addAssociatedParts(part);
            }
            inventory.getAllProducts().add(product);


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
     * Add part to the associated list in the  products table
     * @param event representation of the action when a button is pressed.
     */

    @FXML
    void handleButtonAddProductAssoc(ActionEvent event) {
        Part selectedPart = addProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(4);
            return;
        } else if (!associatedPartsList.contains(selectedPart)) {
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * Remove part from the associated list in the  products table
     * @param event representation of the action when a button is pressed.
     */
    @FXML
     void handleButtonRemoveAssocPart(ActionEvent event) {
        Part selectedPart = associatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(4);
            return;
        } else if (associatedPartsList.contains(selectedPart)) {
            associatedPartsList.remove(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * Controller initialization interface. Called to initialize a controller after its root element has been completely processed
     * Initialize and set the products table and associated parts
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addProductTable.setItems(inventory.getAllParts());
        addProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        associatedProductTable.setItems(associatedPartsList);
        associatedProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * filters parts in search box based off of text and error if part not found.
     * @param event representation of the action when enter key is pressed.
     */
    @FXML
    void addProductPartSearch(ActionEvent event) {
        String searchText = addProductSearchBox.getText();
        ObservableList<Part> results = inventory.lookupPart(searchText);
        try {
            while (results.size() == 0) {
                int partID = Integer.parseInt(searchText);
                results.add(inventory.lookupPart(partID));
            }
            addProductTable.setItems(results);
        } catch (NumberFormatException e) {
            displayAlert(5);
        }
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
        autoGenValue();
    }

    /**
     * disabling product ID, made with math.random
     */
    @FXML
    private void autoGenValue() {
        addProductID.setText("Auto Gen - Disabled");
    }
}