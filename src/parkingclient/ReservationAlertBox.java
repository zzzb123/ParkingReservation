import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReservationAlertBox {
    public static void display() {
        Stage alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle("Current Reservations");
        alertWindow.setWidth(500);
        alertWindow.setMinWidth(250);

        VBox overallLayout = new VBox();
        Label titleLabel = new Label("Current Reservations");

        ListView<HBox> reservationList = new ListView<HBox>();
        ObservableList listViewItems = FXCollections.observableArrayList();
        reservationList.setItems(listViewItems);

        for(String s : Main.server.getReservations()){
            String[] ss = s.split(":");
            String[] timedata1 = ss[1].split(",");
            String[] timedata2 = ss[2].split(",");
            LocalDateTime time1 = LocalDateTime.of(Integer.parseInt(timedata1[0]),Month.of(Integer.parseInt(timedata1[1])),Integer.parseInt(timedata1[2]),Integer.parseInt(timedata1[3]), Integer.parseInt(timedata1[4]));
            LocalDateTime time2 = LocalDateTime.of(Integer.parseInt(timedata2[0]),Month.of(Integer.parseInt(timedata2[1])),Integer.parseInt(timedata2[2]),Integer.parseInt(timedata2[3]), Integer.parseInt(timedata2[4]));
            String timeString = time1.getMonthValue() + "/" + time1.getDayOfMonth() + " at " + time1.format(DateTimeFormatter.ofPattern("hh:mm a"))
             + " to " + time2.getMonthValue() + "/" + time2.getDayOfMonth() + " at " + time2.format(DateTimeFormatter.ofPattern("hh:mm a"));
            String result = ss[0] + " from " + timeString;
        }





        overallLayout.getChildren().addAll(titleLabel, reservationList);
    }
}
