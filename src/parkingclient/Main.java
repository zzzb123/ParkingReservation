package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Parking Reservation");

        VBox overallDisplay = new VBox();
        VBox topDisplay = new VBox();

        Label loginText = new Label("Login");
        topDisplay.getChildren().add(loginText);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        topDisplay.getChildren().add(usernameField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        topDisplay.getChildren().add(passwordField);

        HBox bottomDisplay = new HBox();

        Button loginButton = new Button("Login");
        bottomDisplay.getChildren().add(loginButton);

        Button registerButton = new Button("Register");
        bottomDisplay.getChildren().add(registerButton);

        overallDisplay.getChildren().addAll(topDisplay, bottomDisplay);

        Scene loginScene = new Scene(overallDisplay);


        primaryStage.setScene(loginScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
