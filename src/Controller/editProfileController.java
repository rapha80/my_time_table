package Controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class editProfileController {
    private dashBoardController dashboardController;
    private Model model;
    private Stage stage;
    private Stage parentStage;
    private User userOnline;
    @FXML
    private TextField newpassword;
    @FXML
    private TextField newfirstname;
    @FXML
    private TextField newlastname;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label message;

    public editProfileController(Stage parentStage, Model model, User userOnline) {
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;
        this.userOnline = userOnline;
    }

    public void setDashboardController(dashBoardController controller) {
        this.dashboardController = controller;
    }

    @FXML
    public void initialize() {
        // Event handler for the Edit button
        editButton.setOnAction(event -> {
            if (!newpassword.getText().isEmpty() && !newfirstname.getText().isEmpty() && !newlastname.getText().isEmpty()) {
                try {
                    // Call the editUser method to update the user's information in the database
                    userOnline = model.getUserDao().editUser(newpassword.getText(), newfirstname.getText(), newlastname.getText(), userOnline.getUsername(), userOnline.getId());
                    if (userOnline != null) {
                        message.setText("Edited, re  " + userOnline.getUsername());
                        message.setTextFill(Color.GREEN);
                        dashboardController.updateLabels(userOnline.getFirstName(), userOnline.getLastName());
                    } else {
                        message.setText("Cannot create user");
                        message.setTextFill(Color.RED);
                    }
                    stage.close();
                    parentStage.show();
                } catch (Exception e) {
                    message.setText(e.getMessage());
                    message.setTextFill(Color.RED);
                }
            } else {
                message.setText("Empty firstname, lastname or password");
                message.setTextFill(Color.RED);
            }
        });

        // Event handler for the Cancel button
        cancelButton.setOnAction(event -> {
            stage.close();
            parentStage.show();
        });
    }

    public void showStage(Pane root) {
        Scene scene = new Scene(root, 900, 650);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Welcome");
        stage.show();
    }
}

