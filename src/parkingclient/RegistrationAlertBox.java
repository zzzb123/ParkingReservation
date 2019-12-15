package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegistrationAlertBox {
    public static void display() {
        Lot lotObject = null;
        try {
            lotObject = Main.server.getLotData();
        } catch(Exception e) {
            System.out.println("Warning: This should never happen");
        }

        Stage alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle("Reserve a Spot");
        alertWindow.setWidth(500);
        alertWindow.setMinWidth(250);

        VBox overallLayout = new VBox(5);
        overallLayout.setStyle("-fx-padding: 0 50 0 50");


        Label nameAndTimeHeader = new Label(lotObject.name + " from " + lotObject.readytimes);
        nameAndTimeHeader.setUnderline(true);
        overallLayout.getChildren().add(nameAndTimeHeader);

        Label capacityLabel = new Label("Capacity of Lot: " + lotObject.getTotal());
        overallLayout.getChildren().add(capacityLabel);

        Label availableSpotsLabel = new Label("Available Spots: " + lotObject.getNormal());
        overallLayout.getChildren().add(availableSpotsLabel);

        Label availableHandicapLabel = new Label("Available Handicap Spots: " + lotObject.getHandicap());
        overallLayout.getChildren().add(availableHandicapLabel);

        CheckBox isHandicapSpot = new CheckBox("Handicap Spot");
        overallLayout.getChildren().add(isHandicapSpot);

        HBox lowerLayout = new HBox(10);

        Button reserveButton = new Button("Reserve");
        Button cancelButton = new Button("Cancel");

        lowerLayout.getChildren().addAll(reserveButton, cancelButton);
        overallLayout.getChildren().add(lowerLayout);

        Scene reservationScene = new Scene(overallLayout, 750, 500);
        alertWindow.setScene(reservationScene);

        alertWindow.showAndWait();
    }
}
