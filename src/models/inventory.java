package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * The inventory following the UML diagram.
 */
public class inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Products> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the allParts list.
     * @param newPart
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * Adds a new product to the mainscreen products list
     * @param newProduct new product to create
     */
    public static void addProduct(Products newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * This loops through parts and returns part
     * @param partID the ID for the part
     * @return part the part is found or null if no item found
     */
    public static Part lookupPart(int partID) {
        for(Part part: inventory.getAllParts()) {
            while (part.getId() == partID) {
                return part;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No item found");
        alert.show();
        return null;
    }

    /**
     * This loops through product and returns product
     * @param productID the ID for the product
     * @return part the part is found or null if no item found
     */
    public static Products lookupProduct(int productID) {
        for(Products product: inventory.getAllProducts()){
            while (product.getId() == productID)
                return product;
        }
        return null;
    }

    /**
     * This makes an observablelist of filtered parts and if found returns the part.
     * @param partName the part name
     * @return part name that has been filtered
     */

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> PartName = FXCollections.observableArrayList();

        for (Part part: allParts) {
            if (part.getName().contains(partName)) {
                PartName.add(part);
            }
        }
        return PartName;
    }

    /**
     * This makes an observablelist of filtered parts and if found returns the part.
     * @param productName the part name
     * @return product name that has been filtered
     */
    public static ObservableList<Products> lookupProduct(String productName) {
        ObservableList<Products> ProductName = FXCollections.observableArrayList();

        for (Products product: allProducts) {
            if (product.getName().contains(productName)) {
                ProductName.add(product);
            }
        }
        return ProductName;
    }

    /**
     * this updates the list with modified part
     * @param index passes in selected part
     * @param selectedPart passes index of selected part to be changed
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * this updates the list with modified product
     * @param index passes in selected part
     * @param selectedProduct passes index of selected product to be changed
     */

    public static void updateProduct(int index, Products selectedProduct) {

        allProducts.set(index, selectedProduct);
    }


    /**
     * This deletes a part from the list.
     * @param selectedPart passes index of selected part to be removed
     */

    public static boolean deletePart (Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This deletes a product from the list.
     * @param selectedProduct passes index of selected part to be removed
     */
    public static boolean deleteProduct (Products selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * this gets the entire list of products
     * @return allProducts in the list
     */
    public static ObservableList<Products> getAllProducts(){

        return allProducts;
    }

    /**
     * this gets the entire list of parts
     * @return allParts in the list
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }
}