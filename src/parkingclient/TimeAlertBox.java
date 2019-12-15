package sample;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

public class TimeAlertBox {
    public static void display() {
        Stage alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle("Pick a Time");
        alertWindow.setWidth(500);
        alertWindow.setMinWidth(250);

        VBox alertLayout = new VBox(10);

        HBox upperUpperAlertLayout = new HBox();

        Label timeFromLabel = new Label("What Time Would you like to Reserve?: ");
        upperUpperAlertLayout.getChildren().add(timeFromLabel);

        HBox upperAlertLayout = new HBox();

        DatePicker date = new DatePicker();
        date.setValue(LocalDate.now());
        upperAlertLayout.getChildren().add(date);

        ObservableList<String> possibleHours = FXCollections.observableArrayList();
        for(int i = 1; i < 13; i++) {
            if(i < 10) {
                possibleHours.add("0" + i);
            } else {
                possibleHours.add("" + i);
            }
        }

        ComboBox<String> hoursBox = new ComboBox<>(possibleHours);
        hoursBox.setValue("12");

        Label colonLabel = new Label(" : ");

        ObservableList<String> possibleMinutes = FXCollections.observableArrayList("00", "30");
        ComboBox<String> minutesBox = new ComboBox<>(possibleMinutes);
        minutesBox.setValue("00");

        ObservableList<String> amOrPM = FXCollections.observableArrayList("AM", "PM");
        ComboBox<String> amOrPMBox = new ComboBox<>(amOrPM);
        amOrPMBox.setValue("PM");

        upperAlertLayout.getChildren().addAll(hoursBox, colonLabel, minutesBox, amOrPMBox);



        HBox lowerAlertLayout = new HBox();
        Label reservationLengthLabel = new Label("For How Long?: ");
        lowerAlertLayout.getChildren().add(reservationLengthLabel);

        HBox lowerLowerAlertLayout = new HBox();
        ObservableList<String> hoursLength = FXCollections.observableArrayList();
        for(int i = 0; i < 9; i++) {
            hoursLength.add("0" + i);
        }

        ComboBox<String> hoursLengthBox = new ComboBox<>(hoursLength);
        hoursLengthBox.setValue("01");
        Label hoursLengthLabel = new Label(" Hours\t");

        ObservableList<String> minutesLength = FXCollections.observableArrayList("00", "30");
        ComboBox<String> minutesLengthBox = new ComboBox<>(minutesLength);
        minutesLengthBox.setValue("00");
        Label minutesLengthLabel = new Label(" Minutes\t");


        lowerLowerAlertLayout.getChildren().addAll(hoursLengthBox, hoursLengthLabel, minutesLengthBox, minutesLengthLabel);

        HBox lowestAlertLayout = new HBox(10);
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        lowestAlertLayout.getChildren().addAll(submitButton, cancelButton);
        lowestAlertLayout.setAlignment(Pos.CENTER);

        alertLayout.getChildren().addAll(upperUpperAlertLayout, upperAlertLayout, lowerAlertLayout, lowerLowerAlertLayout, lowestAlertLayout);

        Scene alertScene = new Scene(alertLayout, 400, 280);


        submitButton.setOnAction(e -> {

            if(minutesLengthBox.getValue().equals("00") && hoursLengthBox.getValue().equals("00")) {
                //TODO: Tell them that they made an error
            } else {
                Main.isTimeCorrectlySet = true;
                int hours = Integer.parseInt((String)hoursBox.getValue());
                if(hours == 12)
                    hours = 0;
                if(amOrPMBox.getValue().equals("PM"))
                    hours += 12;

                LocalDateTime startTime = LocalDateTime.of(date.getValue(), LocalTime.of(hours, Integer.parseInt((String)minutesBox.getValue())));
                String start = startTime.format(DateTimeFormatter.ofPattern("uuuu,MM,dd,HH,mm"));
                LocalDateTime endTime = startTime.plusMinutes(Integer.parseInt((String)minutesLengthBox.getValue())).plusHours(Integer.parseInt((String)hoursLengthBox.getValue()));
                String end = endTime.format(DateTimeFormatter.ofPattern("uuuu,MM,dd,HH,mm"));
                Main.server.setReservation(start, end);


                alertWindow.close();
            }

        });

        cancelButton.setOnAction(e -> {

            alertWindow.close();

        });

        alertLayout.setStyle("-fx-padding: 0 50 0 50");
        alertWindow.setScene(alertScene);
        alertWindow.showAndWait();

    }
}
