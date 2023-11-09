package Controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Course;
import model.Model;
import model.User;

public class withdrawCourseController {
    private dashBoardController dashboardController;
    private Model model;
    private Stage stage;
    private Stage parentStage;
    private User userOnline;
    @FXML
    private Button closeButton;
    @FXML
    private Label message;
    @FXML
    private Button withdrawButton;
    @FXML
    private ChoiceBox<String> searchBox;

    public withdrawCourseController(Stage parentStage, Model model, User userOnline) {
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
        ArrayList<Course> courses = new ArrayList<Course>();
        try {
            // Load the courses in which the user is enrolled
            courses = model.getCourseEnrolledDao().loadCoursesEnrolled(userOnline.getUsername());
            ArrayList<String> courseNames = new ArrayList<String>();
            for (int i = 0; i < courses.size(); i++) {
                courseNames.add(courses.get(i).getName());
            }
            searchBox.getItems().addAll(courseNames);
        } catch (SQLException e) {
            message.setText("An error occurred");
            message.setTextFill(Color.RED);
        }

        withdrawButton.setOnAction(event -> {
            String choice = searchBox.getValue();
            try {
                // Check if the course has available capacity
                Integer actualCapa = Integer.parseInt(model.getCourseDao().getCourse(choice).getCapacity());
                // Remove the user's enrollment in the selected course
                model.getCourseEnrolledDao().deleteRow(choice, userOnline.getUsername());
                message.setText("You withdrew successfully");
                message.setTextFill(Color.GREEN);
                dashboardController.removeListView(choice);
                // Increase the course capacity by 1
                model.getCourseDao().changeCapacity(choice, String.valueOf(actualCapa + 1));
            } catch (SQLException e) {
                if (!(choice == null)) {
                    message.setText("An SQL error occurred");
                    message.setTextFill(Color.RED);
                } else {
                    message.setText("Please make sure to enter a choice");
                }
            }
        });

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
