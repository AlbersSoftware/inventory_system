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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



import models.Part;
import models.Products;
import models.inventory;

import static javafx.fxml.FXMLLoader.load;


public class modifyProdController implements Initializable {

    public Stage stage;
    public Scene scene;
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();






    @FXML
    private TextField modifyProductID;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductInv;
    @FXML
    private TextField modifyProductMin;
    @FXML
    private TextField modifyProductMax;

    @FXML
    private TableView<Part> modifyProductTable;
    @FXML
    private TableColumn<?, ?> modifyPartNameCol;
    @FXML
    private TableColumn<?, ?> modifyProductPartIDCol;
    @FXML
    private TableColumn<?, ?> modifyProductPriceCol;
    @FXML
    private TableColumn<?, ?> modifyProductInventoryCol;



    @FXML
    private TableView<Part> associatedProductTable;
    @FXML
    private TableColumn<?, ?> associatedProductIDCol;
    @FXML
    private TableColumn<?, ?> associatedInventoryCol;
    @FXML
    private TableColumn<?, ?> associatedPartNameCol;

    @FXML
    private TableColumn<?, ?> associatedPriceCol;

    @FXML
    Button modifyProductmodifyButton;
    @FXML
    Button modifyProductSaveButton;
    @FXML
    Button removeAssociatedPartButton;
    @FXML
    TextField modifyProductSearchBox;

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
                alert.setContentText("Select the part from the list");
                alert.showAndWait();
                break;
            case 4: alertwarn.setTitle("WARNING");
                alert.setHeaderText("Input error");
                alert.setContentText("Incorrect Value");
                alert.showAndWait();
                break;
            case 5: alert.setTitle("Error");
                alert.setHeaderText("Invalid");
                alert.setContentText("part not found");
                alert.showAndWait();
                break;


        }}
    /**
     * takes local data from main screen, sets modified text from products
     * @param product setting selected products
     * @param selectedIndex setting existing index of 0 to selected index
     */

    @FXML
    public void sendProduct(int selectedIndex, Products product) {

        existingIndex = selectedIndex;
        modifyProductID.setText(String.valueOf(product.getId()));
        modifyProductName.setText(String.valueOf(product.getName()));
        modifyProductInv.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));

        for (Part part : product.getAllAssociatedParts()) {
            associatedPartsList.add(part);
        }


    }
    /**
     * Controller initialization interface. Called to initialize a controller after its root element has been completely processed
     * Initialize and set the modified products and associated parts
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized
     */

    public void initialize(URL url, ResourceBundle resourceBundle) {

        modifyProductTable.setItems(inventory.getAllParts());
        modifyProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));



        associatedProductTable.setItems(associatedPartsList);
        associatedProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     *  add part to associated modify product table
     * @param event representation of the action when a button is pressed.
     */
    @FXML
    void handleAddPartProductAssocModify(ActionEvent event) {
        Part selectedPart = modifyProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(3);
            return;
        } else if (!associatedPartsList.contains(selectedPart)) {
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }
    /**
     * Remove part from the associated list in the  modify products table
     * @param event representation of the action when a button is pressed.
     */

    @FXML
    void handleButtonRemoveAssocPartButton(ActionEvent event) {
        Part selectedPart = associatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(3);
            return;
        } else if (associatedPartsList.contains(selectedPart)) {
            Products.deleteAssocdPart (selectedPart);
            associatedPartsList.remove(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
            autoGenValue();
        }
    }

    /**
     *  This handles the save button on the modify product screen, entailing new double or int initialized to the value represented by the specified string, getters, inventory checks, and loading it to the main screen.
     * @param event representation of the action when a button is pressed.
     * @throws IOException constructs an IOException with null as its error detail message produced by a failed or interrupted I/O operation.
     */
    @FXML
        void handleProductSaved(ActionEvent event) throws IOException {
            try {
                int id = Integer.parseInt(modifyProductID.getText());
                String name = modifyProductName.getText();
                int stock = Integer.parseInt(modifyProductInv.getText());
                double price = Double.parseDouble(modifyProductPrice.getText());
                int max = Integer.parseInt(modifyProductMax.getText());
                int min = Integer.parseInt(modifyProductMin.getText());

                if (stock > max || stock < min) {
                    displayAlert(1);
                    return;
                } else if (min >= max) {
                    displayAlert(2);

                    return;
                }
                Products updatedProduct = new Products(id, name, price, stock, min, max);
                if (updatedProduct != associatedPartsList) {
                    inventory.updateProduct(existingIndex, updatedProduct);
                }


                for (Part part: associatedPartsList) {
                    if (part != associatedPartsList)
                        updatedProduct.addAssociatedParts(part);
                }

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("../views/main1.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();

           } catch (NumberFormatException e) {
                displayAlert(4);
                return;
            }
        }

    /**
     * This handles the modify product cancel button press loading back to the main scene.
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
    /**
     * this search is for modifyparts to add to the associated parts table.
     * @param event representation of the action when enter key is pressed.
     */

    @FXML
    void modifyProductPartSearch(ActionEvent event) {
        String searchText = modifyProductSearchBox.getText();
        ObservableList<Part> results = inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(inventory.lookupPart(partID));
            }
            modifyProductTable.setItems(results);
        } catch (NumberFormatException e) {
            displayAlert(5);
        }
    }

    /**
     * This is to set text for modifyID to disabled
     */
    @FXML
    private void autoGenValue() {
        if (modifyProductID.getText().isEmpty() || modifyProductID.getText().isBlank())
            modifyProductID.setText("Auto Gen - Disabled");
}}
