package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Outsourced;
import models.Part;
import models.Products;
import models.inventory;
//Chris Albers WGU-Software1 C482

public class Main extends Application {
    /**
     * "FUTURE ENHANCEMENT"
     * Though I feel this project could benefit from a more modern UI, aesthetics is not extended functionality.
     * A great functionality would be maybe a order parts section or a comments section for in-source and out-sourced parts for suppliers.
     * Possibly A unique signature stamp/number for each time an employee modifies a part or product? That would be super cool!
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/main1.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();



    }


    public static void main(String[] args) {
        Part Thruster = new Outsourced(7, "Thruster", 15.00, 4, 1, 12, "lockheed");
        inventory.addPart(Thruster);

        Products Plane = new Products (48, "Plane", 48000.0, 1, 1, 10);
        inventory.addProduct(Plane);


        launch(args);

    }



}
