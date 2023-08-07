package models;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  Products following the UML diagram
 */

public class Products {
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Products(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id set id
     */
    public void setId(int id) { this.id = id; }

    /**
     * @param name sets name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @param price sets price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock sets stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min sets min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max sets max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @return gets the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return gets the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return  gets the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return gets the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return gets the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @return  gets the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds a part to the associated parts list
     * @param part provided class
     */
    public void addAssociatedParts(Part part) {
        associatedParts.add(part);
    }

    /**
     * This returns all associated parts from a selected product
     * @return associatedParts gets all the associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * This removes a part from the associated parts list.
     * @param selectedAssociatedPart the part that is selected in the associated parts list
     * @return true boolean logic
     */
    public static boolean deleteAssocdPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
}




