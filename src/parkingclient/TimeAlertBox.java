package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TimeAlertBox {
    public static void display() {
        Stage alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle("Pick a Time");
        alertWindow.setWidth(500);
        alertWindow.setMinWidth(250);

        VBox alertLayout = new VBox();

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

        ComboBox hoursBox = new ComboBox(possibleHours);
        hoursBox.setPromptText("12");

        Label colonLabel = new Label(" : ");

        ObservableList<String> possibleMinutes = FXCollections.observableArrayList("00", "30");
        ComboBox minutesBox = new ComboBox(possibleMinutes);
        minutesBox.setPromptText("00");

        ObservableList<String> amOrPM = FXCollections.observableArrayList("AM", "PM");
        ComboBox amOrPMBox = new ComboBox(amOrPM);
        amOrPMBox.setPromptText("PM");

        upperAlertLayout.getChildren().addAll(hoursBox, colonLabel, minutesBox, amOrPMBox);



        HBox lowerAlertLayout = new HBox();
        Label reservationLengthLabel = new Label("For How Long?: ");
        lowerAlertLayout.getChildren().add(reservationLengthLabel);

        HBox lowerLowerAlertLayout = new HBox();
        ObservableList<String> hoursLength = FXCollections.observableArrayList();
        for(int i = 0; i < 9; i++) {
            hoursLength.add("0" + i);
        }

        ComboBox hoursLengthBox = new ComboBox(hoursLength);
        hoursLengthBox.setPromptText("01");
        Label hoursLengthLabel = new Label(" Hours\t");

        ObservableList<String> minutesLength = FXCollections.observableArrayList("00", "30");
        ComboBox minutesLengthBox = new ComboBox(minutesLength);
        minutesLengthBox.setPromptText("00");
        Label minutesLengthLabel = new Label(" Minutes\t");

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        lowerLowerAlertLayout.getChildren().addAll(submitButton, cancelButton);

        lowerLowerAlertLayout.getChildren().addAll(hoursLengthBox, hoursLengthLabel, minutesLengthBox, minutesLengthLabel);


        alertLayout.getChildren().addAll(upperUpperAlertLayout, upperAlertLayout, lowerAlertLayout, lowerLowerAlertLayout);

        Scene alertScene = new Scene(alertLayout, 400, 280);


        submitButton.setOnAction(e -> {

            if(minutesLengthBox.getValue().equals("00") && hoursLengthBox.getValue().equals("00")) {
                //TODO: Tell them that they made an error
            } else {
                Main.isTimeCorrectlySet = true;


                LocalDateTime startTime = LocalDateTime.of(date.getValue(), LocalTime.of(Integer.parseInt((String)hoursBox.getValue()), Integer.parseInt((String)minutesBox.getValue())));
                String start = startTime.format(DateTimeFormatter.ofPattern("uuuu,MM,dd,HH,mm"));
                startTime.plusMinutes(Integer.parseInt((String)minutesLengthBox.getValue()));
                startTime.plusHours(Integer.parseInt((String)hoursLengthBox.getValue()));
                String end = startTime.format(DateTimeFormatter.ofPattern("uuuu,MM,dd,HH,mm"));
                Main.server.setReservation(start, end);


                alertWindow.close();
            }

        });

        cancelButton.setOnAction(e -> {

            alertWindow.close();

        });


        alertWindow.setScene(alertScene);
        alertWindow.showAndWait();

    }
}
