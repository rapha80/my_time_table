package Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Course;
import model.Model;
import model.User;

public class dashBoardController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	private User userOnline;
	@FXML
	private Label numberLabel;
	@FXML
	private Label firstLabel;
	@FXML
	private Label messageEdit;
	@FXML
	private Label lastLabel;
	@FXML
	private Button editButton;
	@FXML
	private Button viewAllButton;
	@FXML
	private Button withdrawButton;
	@FXML
	private Button searchButton;
	@FXML
	private Button exportButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Label status;
	@FXML
	private Button timetableButton;
	@FXML
	private ListView<String> courseListview;
	
	public dashBoardController(Stage parentStage, Model model, User userOnline) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.userOnline = userOnline;
	}
	
	@FXML
	public void initialize() {
		
		ArrayList<Course> courses = new ArrayList<Course>();
		try {
			// Load the enrolled courses for the current user
			courses = model.getCourseEnrolledDao().loadCoursesEnrolled(userOnline.getUsername());
			ArrayList<String> courseNames = new ArrayList<String>();
			for(int i=0; i < courses.size(); i++) {
				courseNames.add(courses.get(i).getName().toUpperCase());
			}
			courseListview.getItems().addAll(courseNames);
		} catch (SQLException e1) {
			status.setText("An error occurred");
			status.setTextFill(Color.RED);
		}
		
		numberLabel.setText(String.valueOf(userOnline.getId()));
		firstLabel.setText(userOnline.getFirstName());
		lastLabel.setText(userOnline.getLastName());
		
		logoutButton.setOnAction(event -> {
			// Close the current dashboard stage and show the login stage
			stage.close();
			parentStage.show();
		});
		
		editButton.setOnAction(event -> {
		    try {
		        // Load the editProfile.fxml file
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editProfile.fxml"));
		        editProfileController editController = new editProfileController(stage, model, userOnline);
		        editController.setDashboardController(this); 

		        loader.setController(editController);
		        BorderPane root = loader.load();

		        editController.showStage(root);

		        stage.close();
		    } catch (IOException e) {
		        messageEdit.setText(e.getMessage());
		    }
		});
		
		viewAllButton.setOnAction(event -> {
		    try {
		        // Load the viewAllCourses.fxml file
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewAllCourses.fxml"));
		        viewAllCoursesController viewAllController = new viewAllCoursesController(stage, model, userOnline);

		        loader.setController(viewAllController);
		        BorderPane root = loader.load();

		        viewAllController.showStage(root);

		        stage.close();
		    } catch (IOException e) {
		        messageEdit.setText(e.getMessage());
		    }
		});
		
		searchButton.setOnAction(event -> {
			try {
		        // Load the searchCourse.fxml file
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/searchCourse.fxml"));
		        searchCourseController searchController = new searchCourseController(stage, model, userOnline);
		        searchController.setDashboardController(this);

		        loader.setController(searchController);
		        BorderPane root = loader.load();

		        searchController.showStage(root);

		        stage.close();
		    } catch (IOException e) {
		        messageEdit.setText(e.getMessage());
		    }
		});
		
		withdrawButton.setOnAction(event -> {
			try {
		        // Load the withdrawCourse.fxml file
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/withdrawCourse.fxml"));
		        withdrawCourseController withdrawController = new withdrawCourseController(stage, model, userOnline);
		        withdrawController.setDashboardController(this);
		        
		        loader.setController(withdrawController);
		        BorderPane root = loader.load();

		        withdrawController.showStage(root);

		        stage.close();
		    } catch (IOException e) {
		        messageEdit.setText(e.getMessage());
		    }
		});
		
		timetableButton.setOnAction(event -> {
			try {
		        // Load the timetable.fxml file
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/timetable.fxml"));
		        timeTableController timetableController = new timeTableController(stage, model, userOnline);
		        
		        loader.setController(timetableController);
		        BorderPane root = loader.load();

		        timetableController.showStage(root);

		        stage.close();
		    } catch (IOException e) {
		        messageEdit.setText(e.getMessage());
		    }
		});
		
		exportButton.setOnAction(event -> {
			try {
				// Export the course list to a text file
				exportCourseList();
				status.setText("File exported successfully!");
				status.setTextFill(Color.GREEN);
			} catch (SQLException e) {
				status.setText("An error occurred");
				status.setTextFill(Color.RED);
			}
		});
	}
	
	/*
	 * This method update the first name and last name labels
	 */
	public void updateLabels(String newFirstName, String newLastName) {
	    firstLabel.setText(newFirstName);
	    lastLabel.setText(newLastName);
	}
	
	/*
	 * This method add an item to the course list view
	 */
	public void addListView(String Course) {
		courseListview.getItems().add(Course.toUpperCase());
	}
	
	/*
	 * This method remove an item from the course list view
	 */
	public void removeListView(String Course) {
		courseListview.getItems().remove(Course.toUpperCase());
	}
	
	/*
	 * This method handles the creation and the edition of a file containing the course informations of a student
	 */
	public void exportCourseList() throws SQLException {
	    ArrayList<Course> courseList = model.getCourseEnrolledDao().loadCoursesEnrolled(userOnline.getUsername());

	    try {
	        FileWriter fileWriter = new FileWriter("course_list_"+userOnline.getUsername()+".txt");
	        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	        
	        // Write the user information to the file
	        bufferedWriter.write(userOnline.getFirstName() + " " + userOnline.getLastName()+ ", student number : " + userOnline.getId() );
	        bufferedWriter.newLine();
	        
	        // Write each course's information to the file
	        for (Course course : courseList) {
	            bufferedWriter.write(course.getName() + ", " + course.getDay() + ", " + course.getTime() + ", " + course.getMode() + ", " + course.getDuration()+"H" + ", " + course.getYear());
	            bufferedWriter.newLine();
	        }

	        bufferedWriter.close();
	        fileWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 900, 650);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("DashBoard");
		stage.show();
	}
}


