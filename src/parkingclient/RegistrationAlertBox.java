package sample;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

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

        String[] unparsedTimes = lotObject.readytimes.split(":");
        String[] timedata1 = unparsedTimes[0].split(",");
        String[] timedata2 = unparsedTimes[1].split(",");
        LocalDateTime time1 = LocalDateTime.of(Integer.parseInt(timedata1[0]),Month.of(Integer.parseInt(timedata1[1])),Integer.parseInt(timedata1[2]),Integer.parseInt(timedata1[3]), Integer.parseInt(timedata1[4]));
        LocalDateTime time2 = LocalDateTime.of(Integer.parseInt(timedata2[0]),Month.of(Integer.parseInt(timedata2[1])),Integer.parseInt(timedata2[2]),Integer.parseInt(timedata2[3]), Integer.parseInt(timedata2[4]));
        String timeString = time1.getMonthValue() + "/" + time1.getDayOfMonth() + " at " + time1.format(DateTimeFormatter.ofPattern("hh:mm a"))
             + " to " + time2.getMonthValue() + "/" + time2.getDayOfMonth() + " at " + time2.format(DateTimeFormatter.ofPattern("hh:mm a"));

        Label nameAndTimeHeader = new Label(lotObject.name + " from " + timeString);
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

        reserveButton.setOnAction(e -> {
            if(!Main.server.reserveSpot(!isHandicapSpot.isSelected())){
                Stage warningWindow = new Stage();

                warningWindow.initModality(Modality.APPLICATION_MODAL);
                warningWindow.setTitle("Pick a Location");
                warningWindow.setWidth(150);

                Label setTime = new Label("Warning: No spots available!  Please try another lot.");
                VBox layout = new VBox();
                layout.getChildren().add(setTime);

                Scene warningScene = new Scene(layout, 150, 100);
                warningWindow.setScene(warningScene);
                warningWindow.showAndWait();
            }
            alertWindow.close();
        });

        cancelButton.setOnAction(e -> {
            alertWindow.close();
        });

        lowerLayout.getChildren().addAll(reserveButton, cancelButton);
        overallLayout.getChildren().add(lowerLayout);

        Scene reservationScene = new Scene(overallLayout, 750, 500);
        alertWindow.setScene(reservationScene);

        alertWindow.showAndWait();
    }
}
