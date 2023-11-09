package Controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Model;
import model.User;

public class loginController {
	@FXML
	private TextField name;
	@FXML
	private PasswordField password;
	@FXML
	private Label message;
	@FXML
	private Button login;
	@FXML
	private Button signin;

	private Model model;
	private Stage stage;
	
	public loginController(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
	}
	
	/**
	 * Initialize the login controller.
	 * This method is automatically called after the FXML file is loaded.
	 * It sets up event handlers for login and signin buttons.
	 */
	@FXML
	public void initialize() {		
		login.setOnAction(event -> {
			if (!name.getText().isEmpty() && !password.getText().isEmpty()) {
				User user;
				try {
					// Try to retrieve the user from the model's UserDao
					user = model.getUserDao().getUser(name.getText(), password.getText());
					if (user != null) {
						// If the user exists, set it as the current user in the model
						model.setCurrentUser(user);
						try {
							// Load the Dashboard.fxml file
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
							dashBoardController homeController = new dashBoardController(stage, model, user);
							
							// Set the controller for the loaded file
							loader.setController(homeController);
							BorderPane root = loader.load();
	
							// Show the dashboard stage and close the current login stage
							homeController.showStage(root);
							stage.close();
						} catch (IOException e) {
							message.setText(e.getMessage());
						}
						
					} else {
						// Display an error message for wrong username or password
						message.setText("Wrong username or password");
						message.setTextFill(Color.RED);
					}
				} catch (SQLException e) {
					// Display an error message for database errors
					message.setText(e.getMessage());
					message.setTextFill(Color.RED);
				}
				
			} else {
				// Display an error message for empty username or password
				message.setText("Empty username or password");
				message.setTextFill(Color.RED);
			}
			// Clear the input fields
			name.clear();
			password.clear();
		});
		
		signin.setOnAction(event -> {
			try {
				// Load the createProfile.fxml file
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/createProfile.fxml"));
				
				// Create an instance of createProfileController and customize it
				createProfileController signupController =  new createProfileController(stage, model);

				// Set the controller for the loaded file
				loader.setController(signupController);
				BorderPane root = loader.load();
				
				// Show the create profile stage
				signupController.showStage(root);
				
				// Reset the message label and input fields
				message.setText("");
				name.clear();
				password.clear();
				
				// Close the current login stage
				stage.close();
			} catch (IOException e) {
				message.setText(e.getMessage());
			}
		});
	}
	
	/**
	 * Show the login stage with the provided root pane.
	 */
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 900, 650);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Welcome");
		stage.show();
	}
}
