package Controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;
import model.Course;

public class viewAllCoursesController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	private User userOnline;
	@FXML
	private TableView<Course> tableView;
	@FXML
	private TableColumn<Course,String> courseColumn;
	@FXML
	private TableColumn<Course,String> capacityColumn;
	@FXML
	private TableColumn<Course,String> yearColumn;
	@FXML
	private TableColumn<Course,String> deliveryColumn;
	@FXML
	private TableColumn<Course,String> dayColumn;
	@FXML
	private TableColumn<Course,String> timeColumn;
	@FXML
	private TableColumn<Course,String> durationColumn;
	@FXML
	private Button closeButton;
	@FXML
	private Label message;
	
	public viewAllCoursesController(Stage parentStage, Model model, User userOnline) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.userOnline = userOnline;
	}
	
	@FXML
	public void initialize() {
		// Retrieve a list of all courses
		ArrayList<Course> courses = new ArrayList<>();
		try {
			courses.add(model.getCourseDao().getCourse("java programming"));
			courses.add(model.getCourseDao().getCourse("programming skills"));
			courses.add(model.getCourseDao().getCourse("advanced python programming"));
			courses.add(model.getCourseDao().getCourse("math"));
			courses.add(model.getCourseDao().getCourse("data mining"));
			courses.add(model.getCourseDao().getCourse("knowledge technologies"));
			courses.add(model.getCourseDao().getCourse("algorithms and complexity"));
		} catch (SQLException e) {
			message.setText(e.getMessage());
			message.setTextFill(Color.RED);
		}
		
		// Set up the table columns
		courseColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
		capacityColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("capacity"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("year"));
		deliveryColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("mode"));
		dayColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("day"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("time"));
		durationColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("duration"));
		
		// Add courses to the table
		for (Course course: courses) {
			tableView.getItems().add(course);
		}

		closeButton.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 900, 650);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("DashBoard");
		stage.show();
	}
}

