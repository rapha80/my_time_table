package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Course;
import model.Model;
import model.User;

public class searchCourseController {
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
    private TextField kwordField;
    @FXML
    private Button selectButton;
    @FXML
    private Button enrollButton;
    @FXML
    private ChoiceBox<String> searchBox;
    @FXML
    private Label status;

    public searchCourseController(Stage parentStage, Model model, User userOnline) {
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
        selectButton.setOnAction(event -> {
            status.setText("Now select a course just below");
            status.setTextFill(Color.GREEN);
            if (!kwordField.getText().isEmpty()) {
                ArrayList<Course> courses = new ArrayList<Course>();
                try {
                    // Call the searchCourses method to retrieve a list of courses matching the keyword
                    courses = model.getCourseDao().searchCourses(kwordField.getText());
                    ArrayList<String> courseNames = new ArrayList<String>();
                    for (int i = 0; i < courses.size(); i++) {
                        courseNames.add(courses.get(i).getName());
                    }
                    searchBox.getItems().addAll(courseNames);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        enrollButton.setOnAction(event -> {
            String choice = searchBox.getValue();
            try {
                // Check if the selected course has available capacity
                Integer actualCapa = Integer.parseInt(model.getCourseDao().getCourse(choice).getCapacity());
                if (actualCapa > 0) {
                    // Enroll the user in the selected course
                    model.getCourseEnrolledDao().createRow(choice, userOnline.getUsername());
                    message.setText("You enrolled successfully");
                    message.setTextFill(Color.GREEN);
                    dashboardController.addListView(choice);
                    // Decrease the course capacity by 1
                    model.getCourseDao().changeCapacity(choice, String.valueOf(actualCapa - 1));
                } else {
                    message.setText("Sorry, this course is full");
                    message.setTextFill(Color.RED);
                }
            } catch (SQLException e) {
                if (!(choice == null)) {
                    message.setText("You are already enrolled in this course");
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

