package application;
	
import Controller.loginController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private Model model;
	
	@Override
	public void init() {
		model = new Model();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			model.setup();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
			loginController loginController = new loginController(primaryStage, model);
			loader.setController(loginController);
			BorderPane root = loader.load();
			loginController.showStage(root);
		} catch(Exception e) {
			e.printStackTrace();
			Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
			primaryStage.setTitle("Error");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
