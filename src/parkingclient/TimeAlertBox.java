import java.util.Calendar;

public class TimeAlertBox {
    public static boolean display() {
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
        amOrPMBox.setPromptText("AM");

        upperAlertLayout.getChildren().addAll(hoursBox, colonLabel, minutesBox, amOrPMBox);



        HBox lowerAlertLayout = new HBox();
        Label reservationLengthLabel = new Label("For How Long?: ");
        lowerAlertLayout.getChildren().add(reservationLengthLabel);

        HBox lowerLowerAlertLayout = new HBox();
        ObservableList<String> possibleLengths = FXCollections.observableArrayList();
        for(int i = 1; i < 13; i++) {
            if(i < 10) {
                possibleHours.add("0" + i);
            } else {
                possibleHours.add("" + i);
            }
        }


        alertLayout.getChildren().addAll(upperUpperAlertLayout, upperAlertLayout, lowerAlertLayout, lowerLowerAlertLayout);

        Scene alertScene = new Scene(alertLayout, 400, 280);




        ObservableList<String> options = FXCollections.observableArrayList("", "Option 2", "Option 3");
        final ComboBox comboBox = new ComboBox(options);





        alertWindow.setScene(alertScene);
        alertWindow.showAndWait();
        return true;

    }
}
