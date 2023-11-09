package Controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class createProfileController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField studentnumber;
    @FXML
    private Button createUser;
    @FXML
    private Button close;
    @FXML
    private Label status;

    private Stage stage;
    private Stage parentStage;
    private Model model;

    public createProfileController(Stage parentStage, Model model) {
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;
    }

    @FXML
    public void initialize() {
        // Event handler for the Create User button
        createUser.setOnAction(event -> {
            if (!username.getText().isEmpty() && !password.getText().isEmpty() && !firstname.getText().isEmpty() && !lastname.getText().isEmpty() && !studentnumber.getText().isEmpty()) {
                try {
                    // Call the createUser method to create a new user in the database
                    User user = model.getUserDao().createUser(username.getText(), password.getText(), firstname.getText(), lastname.getText(), Integer.parseInt(studentnumber.getText()));
                    if (user != null) {
                        status.setText("Created " + user.getUsername());
                        status.setTextFill(Color.GREEN);
                    } else {
                        status.setText("Cannot create user");
                        status.setTextFill(Color.RED);
                    }
                } catch (SQLException e) {
                    status.setText(e.getMessage());
                    status.setTextFill(Color.RED);
                } catch (NumberFormatException e) {
                    status.setText("Enter an int for studentNB");
                    status.setTextFill(Color.RED);
                }
            } else {
                status.setText("Empty username or password");
                status.setTextFill(Color.RED);
            }
        });

        // Event handler for the Close button
        close.setOnAction(event -> {
            stage.close();
            parentStage.show();
        });
    }

    public void showStage(Pane root) {
        Scene scene = new Scene(root, 900, 650);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sign up");
        stage.show();
    }
}
