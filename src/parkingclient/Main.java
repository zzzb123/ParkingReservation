package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main extends Application {

    public static boolean isTimeCorrectlySet = false;

    public static ServerInterconnect server = new ServerInterconnect();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Parking Reservation");
        loginMenu(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
        server.disconnect();
    }

    public void loginMenu(Stage primaryStage) {

        VBox overallDisplay = new VBox(10);
        overallDisplay.setPrefWidth(250);
        VBox topDisplay = new VBox(5);

        Label invalidUserNameWarning = new Label(" ");
        invalidUserNameWarning.setTextFill(Color.web("#dc3545"));
        topDisplay.getChildren().add(invalidUserNameWarning);

        Label loginText = new Label("Login");
        topDisplay.getChildren().add(loginText);
        loginText.setStyle("-fx-font-size: 24");


        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        topDisplay.getChildren().add(usernameField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        topDisplay.getChildren().add(passwordField);

        HBox bottomDisplay = new HBox(10);
        bottomDisplay.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        bottomDisplay.getChildren().add(loginButton);

        Button registerButton = new Button("Register");
        bottomDisplay.getChildren().add(registerButton);

        overallDisplay.getChildren().addAll(topDisplay, bottomDisplay);
        overallDisplay.setStyle("-fx-padding: 0 50 0 50");

        Scene loginScene = new Scene(overallDisplay, 500, 350);
        loginScene.getStylesheets().add("sample/styles.css");

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

        VBox overallDisplay = new VBox(8);
        VBox topDisplay = new VBox(8);

        Label invalidWarning = new Label(" ");
        invalidWarning.setTextFill(Color.web("#dc3545"));
        topDisplay.getChildren().add(invalidWarning);

        Label signUpLabel = new Label("Sign Up");
        signUpLabel.setStyle("-fx-font-size: 24");
        signUpLabel.setAlignment(Pos.CENTER);
        topDisplay.getChildren().add(signUpLabel);

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

        HBox bottomDisplay = new HBox(10);
        bottomDisplay.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        bottomDisplay.getChildren().addAll(submitButton, cancelButton);

        overallDisplay.getChildren().addAll(topDisplay, bottomDisplay);
        overallDisplay.setStyle("-fx-padding: 0 50 0 50");

        Scene registrationScene = new Scene(overallDisplay, 750, 500);
        registrationScene.getStylesheets().add("sample/styles.css");
        primaryStage.setScene(registrationScene);

        cancelButton.setOnAction(e -> {
            loginMenu(primaryStage);
        });

        submitButton.setOnAction(e -> {
            if(passwordField.getText().equals(repasswordField.getText()) && emailField.getText().contains("@") && emailField.getText().contains(".") && emailField.getText().lastIndexOf(".") > emailField.getText().indexOf("@")) {
                if(server.registerUser(usernameField.getText(), emailField.getText(), idField.getText(), passwordField.getText())) {
                    mainMenu(primaryStage);
                }
                invalidWarning.setText("Warning: Username already exists");
            } else if(!passwordField.getText().equals(repasswordField.getText())) {
                invalidWarning.setText("Warning: Passwords do not Match");
            } else {
                invalidWarning.setText("Warning: Email formatted incorrectly");
            }
        });

    }

    public void mainMenu(Stage primaryStage) {
        BorderPane overallLayout = new BorderPane();

        VBox rightPane = new VBox(5);

        HBox upperRight = new HBox(5);
        upperRight.setAlignment(Pos.CENTER);

        TextField searchField = new TextField();
        searchField.setPromptText("Closest lots (in order) to: ");

        Button searchButton = new Button("Search");
        upperRight.getChildren().addAll(searchField, searchButton);
        rightPane.getChildren().add(upperRight);

        Button timeButton = new Button("Set Time");
        timeButton.setStyle("-fx-text-fill: #dc3545");
        rightPane.getChildren().add(timeButton);
        timeButton.setPrefWidth(250);

        timeButton.setOnAction(e -> {
            TimeAlertBox.display();
            if(isTimeCorrectlySet) {
                timeButton.setText("Change Time");
                timeButton.setStyle("-fx-text-fill: #000000");
            }
        });

        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("GoogleMap.html").toString());
        overallLayout.setCenter(webView);

        ListView<Button> middleRight = new ListView<Button>();
        ObservableList<Button> listViewItems = FXCollections.observableArrayList();
        middleRight.setItems(listViewItems);

        searchButton.setOnAction(e -> {
            listViewItems.clear();
            server.fixReservationSystem();
            if(!searchField.getText().isEmpty()) {
                if(timeButton.getText().equals("Change Time")) {
                    try {
                        String[] lotArray;
                        String searchText = searchField.getText().toLowerCase();
                        if(!searchText.contains("gmu")) {
                            searchText += " gmu";
                        }
                        server.setPosition(FindCoordinates.getCoordinates(searchText));
                        lotArray = server.listLots(8);
                        for(String s : lotArray){
                            System.out.println(s);
                        }
                        for(int i = 0; i < Math.min(6, lotArray.length); i++) {
                            listViewItems.add(
                                    new Button(
                                            lotArray[i].split("\t")[0] +
                                             "\n" + "Spots Available:" + lotArray[i].split(",")[0].substring(lotArray[i].indexOf("\t")) +
                                            "\n" + "Handicap Spots Available: " + lotArray[i].substring(lotArray[i].indexOf(",") + 1)));
                        }
                        for(Button b : listViewItems) {
                            b.setPrefWidth(210);
                            b.setOnAction(ee -> {
                                server.setTargetLot(b.getText().split("\t")[0]);
                                RegistrationAlertBox.display();
                            });
                        }
                    } catch(Exception ex) {
                        System.out.println("Google servers are down; there is no more internet, there is no more world.");
                        ex.printStackTrace();
                    }
                } else {
                    Stage alertWindow = new Stage();

                    alertWindow.initModality(Modality.APPLICATION_MODAL);
                    alertWindow.setTitle("Pick a Time");
                    alertWindow.setWidth(150);

                    Label setTime = new Label("Warning: Please set a specified time before searching");
                    VBox layout = new VBox();
                    layout.getChildren().add(setTime);

                    Scene alertSetTimeScene = new Scene(layout, 150, 100);
                    alertWindow.setScene(alertSetTimeScene);
                    alertWindow.showAndWait();
                }
            } else {
                Stage alertWindow = new Stage();

                alertWindow.initModality(Modality.APPLICATION_MODAL);
                alertWindow.setTitle("Pick a Location");
                alertWindow.setWidth(150);

                Label setTime = new Label("Warning: Please Set a Desired Location");
                VBox layout = new VBox();
                layout.getChildren().add(setTime);

                Scene alertSetTimeScene = new Scene(layout, 150, 100);
                alertWindow.setScene(alertSetTimeScene);
                alertWindow.showAndWait();
            }
        });

        rightPane.getChildren().add(middleRight);

        Button bottomRight = new Button("Display Current Reservations");
        bottomRight.setPrefWidth(250);

        rightPane.getChildren().add(bottomRight);
        overallLayout.setRight(rightPane);

        Scene mainScene = new Scene(overallLayout, 750, 500);
        primaryStage.setScene(mainScene);

    }
}
