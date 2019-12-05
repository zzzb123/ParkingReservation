package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Scene scene1, scene2;
    Button button1, button2;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Label label1 = new Label("Welcome to the first scene!");
        Label label2 = new Label("Welcome to the second scene!");

        button1 = new Button("Click to go to Scene2");
        button2 = new Button("Click to go to Scene1");

        button1.setOnAction(e -> {
            primaryStage.setScene(scene2);
        });
        button2.setOnAction(e -> {
            primaryStage.setScene(scene1);
        });

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);

        VBox layout2 = new VBox();
        layout2.getChildren().addAll(label2, button2);

        scene1 = new Scene(layout1, 200, 200);
        scene2 = new Scene(layout2, 200, 200);

        primaryStage.setScene(scene1);
        primaryStage.setTitle("This is the Title");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

