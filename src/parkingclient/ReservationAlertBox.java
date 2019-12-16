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





        overallLayout.getChildren().addAll(titleLabel, reservationList);
    }
}
