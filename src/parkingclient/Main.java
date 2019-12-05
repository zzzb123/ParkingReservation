package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    static ServerInterconnect server = new ServerInterconnect();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Parking Reservation");
        loginMenu(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void loginMenu(Stage primaryStage) {

        VBox overallDisplay = new VBox();
        VBox topDisplay = new VBox();

        Label invalidUserNameWarning = new Label(" ");
        topDisplay.getChildren().add(invalidUserNameWarning);

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

        Scene loginScene = new Scene(overallDisplay, 500, 350);

        primaryStage.setScene(loginScene);
        primaryStage.show();

        registerButton.setOnAction(e -> {
            registrationMenu(primaryStage);
        });

        loginButton.setOnAction(e -> {
            if(server.setUser(usernameField.getText(), passwordField.getText())) {
                mainMenu(primaryStage);
            } else {
                invalidUserNameWarning.setText("Warning: Incorrect Username or Password");
            }
        });
    }

    public void registrationMenu(Stage primaryStage) {

        VBox overallDisplay = new VBox();
        VBox topDisplay = new VBox();

        Label invalidWarning = new Label(" ");
        topDisplay.getChildren().add(invalidWarning);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        topDisplay.getChildren().add(usernameField);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        topDisplay.getChildren().add(emailField);

        TextField idField = new TextField();
        idField.setPromptText("License Plate");
        topDisplay.getChildren().add(idField);

        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        topDisplay.getChildren().add(passwordField);

        TextField repasswordField = new TextField();
        repasswordField.setPromptText("Re-Type Password");
        topDisplay.getChildren().add(repasswordField);

        HBox bottomDisplay = new HBox();

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        bottomDisplay.getChildren().addAll(submitButton, cancelButton);

        overallDisplay.getChildren().addAll(topDisplay, bottomDisplay);

        Scene registrationScene = new Scene(overallDisplay, 500, 350);
        primaryStage.setScene(registrationScene);

        submitButton.setOnAction(e -> {
            if(passwordField.getText().equals(repasswordField.getText())) {
                if(server.registerUser(usernameField.getText(), emailField.getText(), idField.getText(), passwordField.getText())) {
                    mainMenu(primaryStage);
                }
                invalidWarning.setText("Warning: Username already exists");
            } else {
                invalidWarning.setText("Warning: Passwords do not Match");
            }
        });

    }

    public void mainMenu(Stage primaryStage) {
        BorderPane overallLayout = new BorderPane();

        VBox rightPane = new VBox();

        HBox upperRight = new HBox();

        TextField searchField = new TextField();
        searchField.setPromptText("Closest lots to: ");

        Button searchButton = new Button("Search");
        upperRight.getChildren().addAll(searchButton, searchButton);

        
        overallLayout.setRight(rightPane);

        Scene mainScene = new Scene(overallLayout);

    }
}
