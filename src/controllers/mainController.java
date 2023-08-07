package controllers;

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
import javafx.collections.ObservableList;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import models.inventory;
import static javafx.fxml.FXMLLoader.load;
import models.*;




public class mainController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button btnScene1, btnScene2, btnScene3;

    @FXML
    private TableView<Part> mainScreenPartsTable;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Products, Integer> productIDCol;
    @FXML
    private TableColumn<Products, Double> productPriceCol;
    @FXML
    private TableColumn<Products, String> productNameCol;
    @FXML
    private TableColumn<Products, Integer> productInventoryCol;
    @FXML
    TextField partSearch;
    @FXML
     TextField productSearch;

    @FXML
    private TableView<Products> mainScreenProductsTable;

    /**
     *  This is used to display pop up-messages quickly without having to rewrite .setTitle, .setHeader, etc. multiple times. I made the most popular alerts used for a specific window to save time writing more code.
     * @param alertType This constructs different errors, warnings, and confirmation messages to the user.
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertconfirm = new Alert(Alert.AlertType.CONFIRMATION);

        switch (alertType) {
            case 1: alert.setTitle("Error");
                alert.setHeaderText("Invalid");
                alert.setContentText("Select a part to modify");
                alert.showAndWait();
                break;
            case 2: alert.setTitle("Error");
                alert.setHeaderText("Invalid");
                alert.setContentText("Select a product to modify");
                alert.showAndWait();
                break;
            case 3: alertconfirm.setTitle("Exit Confirmation");
                alert.setHeaderText("Are you sure?");
                alert.setContentText("This will exit the program, do you want to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK)
                    System.exit(0);
                break;
            case 4:  alert.setTitle("Error");
                alert.setHeaderText("Error Message");
                alert.setContentText("Part not found");
                alert.showAndWait();
                break;
            case 5:  alert.setTitle("Error");
                alert.setHeaderText("Error Message");
                alert.setContentText("Remove associated parts before you delete the product.");
                alert.showAndWait();
                break;
            case 6:  alert.setTitle("Error");
                alert.setHeaderText("Error Message");
                alert.setContentText("Product not found.");
                alert.showAndWait();
                break;
            case 7: alert.setTitle("Error");
                alert.setHeaderText("Invalid");
                alert.setContentText("Select a product to delete");
                alert.showAndWait();
                break;
            case 8: alert.setTitle("Error");
                alert.setHeaderText("Invalid");
                alert.setContentText("Select a part to delete");
                alert.showAndWait();
                break;



        }}


    //Methods Switch scenes
    public void handleButtonCancel(ActionEvent event) throws IOException {
        Parent root = load(getClass().getResource("../views/main1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This handles the main screen add part button press.
     * @param event representation of the action when a button is pressed.
     * @throws IOException Constructs an IOException with null as its error detail message produced by a failed or interrupted I/O operation.
     */
    public void handleButtonAddPart(ActionEvent event) throws IOException {
        Parent root = load(getClass().getResource("../views/main2.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    /**
     * This handles the main screen modify part button press.
     * @param event representation of the action when a button is pressed.
     * @throws IOException Constructs an IOException with null as its error detail message produced by a failed or interrupted I/O operation.
     */
    public void handleButtonModPart(ActionEvent event) throws IOException {
try{


        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/modify3.fxml"));

   loader.load();
       modifyPartController modParController = loader.getController();
       modParController.sendPart(mainScreenPartsTable.getSelectionModel().getSelectedIndex(), mainScreenPartsTable.getSelectionModel().getSelectedItem());
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(loader.getRoot());
    stage.setScene(scene);
    stage.show();}

        catch (NullPointerException e) {
            displayAlert(1);
        }}

    /**
     * This handles the main screen add product button press.
     * @param event representation of the action when a button is pressed.
     * @throws IOException Constructs an IOException with null as its error detail message produced by a failed or interrupted I/O operation.
     */

    public void handleButtonAddProd(ActionEvent event) throws IOException {
        Parent root = load(getClass().getResource("../views/Addproduct4.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * "RUNTIME ERROR"
     * A runtime error occurs when a program is syntactically correct but contains an issue that is only detected during program execution.
     * My runtime error here was: Exception in thread "JavaFX Application Thread" java.lang.RuntimeException Caused by: java.lang.NullPointerException: modifyProdController.java:139 and mainController.java:179
     * This was corrected with try catch blocks. Ideally would want to prevent null from being passed. It was unable to get the Product data.
     * Also had difficulties with the loader.load() and FXMLloader getting root. Ultimately it fixed the issue.
     * This handles the main screen modify product button press.
     * @param event representation of the action when a button is pressed.
     * @throws IOException Constructs an IOException with null as its error detail message produced by a failed or interrupted I/O operation.
     */
    public void handleButtonModProd(ActionEvent event) throws IOException {

try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/modifyproduct5.fxml"));
        loader.load();
        modifyProdController modProdController = loader.getController();
        modProdController.sendProduct(mainScreenProductsTable.getSelectionModel().getSelectedIndex(), mainScreenProductsTable.getSelectionModel().getSelectedItem());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.show();
    }

     catch (NullPointerException e) {
         displayAlert(2);}
}


    /**
     * This handles the main screen exit button press.
     * @param ExitButton Verifies and closes the program
     */
    public void handleMainScreenExitButton(ActionEvent ExitButton) {

       displayAlert(3);

    }

    /**
     * Controller initialization interface. Called to initialize a controller after its root element has been completely processed
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

{    }

        try {
            mainScreenPartsTable.setItems(inventory.getAllParts());
            partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            mainScreenProductsTable.setItems(inventory.getAllProducts());
            productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        } catch (NullPointerException e) {

        }

    }

    /**
     *  This handles the main screen delete part button press.
     * @param event representation of the action when a button is pressed.
     */
    @FXML
    void handleMainScreenDeletePartButton(ActionEvent event) {
        Part selectedPart = mainScreenPartsTable.getSelectionModel().getSelectedItem();
if (selectedPart == null)
{ displayAlert(8);}
else{


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();



        if (result.isPresent() && result.get() == ButtonType.OK) {
            inventory.deletePart(selectedPart);}



    }}







    /**
     *  This handles the main screen delete product button press.
     * @param event representation of the action when a button is pressed.
     */

@FXML
    void handleMainScreenDeleteProductButton(ActionEvent event) {

        Products selectedProduct = mainScreenProductsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            displayAlert(7);
        } else {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Products selectedDeleteProduct = mainScreenProductsTable.getSelectionModel().getSelectedItem();


            if (selectedDeleteProduct.getAllAssociatedParts().size() > 0) {
                displayAlert(5);
                return;
            }
            inventory.deleteProduct(selectedProduct);
        }
    }}

    /**
     *  Handles the mainscreen part search function.
     * @param event representation of the action when enter key is pressed.
     */

    @FXML
    void handleMainScreenPartSearch(ActionEvent event) {
        String searchText = partSearch.getText();
        ObservableList<Part> results = inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(inventory.lookupPart(partID));
            }
            mainScreenPartsTable.setItems(results);
        } catch (NumberFormatException e) {
            displayAlert(4);
        }
    }

    /**
     *  Handles the mainscreen product search function.
     * @param event representation of the action when enter key is pressed.
     */
    @FXML
    void handleMainScreenProductSearch(ActionEvent event) {
        String searchText = productSearch.getText();
        ObservableList<Products> results = inventory.lookupProduct(searchText);
        try {
            while (results.size() == 0 ) {
                int productID = Integer.parseInt(searchText);
                results.add(inventory.lookupProduct(productID));
            }
            mainScreenProductsTable.setItems(results);
        } catch (NumberFormatException e) {
            displayAlert(6);
        }
    }


}




