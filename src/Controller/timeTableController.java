package Controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Course;
import model.Model;
import model.User;

public class timeTableController {
    private Model model;
    private Stage stage;
    private Stage parentStage;
    private User userOnline;
    @FXML
    private Button closeButton;
    @FXML
    private GridPane gridTable;
    
    public timeTableController(Stage parentStage, Model model, User userOnline) {
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;
        this.userOnline = userOnline;
    }
    
    @FXML
    public void initialize() {
        // Define the days of the week and hours for the timetable
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] hours = {"8h-10h", "10h-12h","12h-14h", "14h-16h", "16h-18h"};
        Integer[] daoHours = {8,10,12,14,16,18};
        
        // Add day labels to the grid
        for (int col = 1; col <= days.length; col++) {
            Label dayLabel = new Label(days[col-1]);
            GridPane.setHalignment(dayLabel, HPos.CENTER);
            GridPane.setValignment(dayLabel, VPos.CENTER);
            gridTable.add(dayLabel, col, 0);
        }

        // Add hour labels to the grid
        for (int row = 1; row <= hours.length; row++) {
            Label hoursLabel = new Label(hours[row-1]);
            GridPane.setHalignment(hoursLabel, HPos.CENTER);
            GridPane.setValignment(hoursLabel, VPos.CENTER);
            gridTable.add(hoursLabel, 0, row);
        }
        
        // Fill the grid with course labels
        for (int col = 1; col <= days.length; col++) {
            for (int row = 1; row <= hours.length; row++) {
                String courseName = getCourse(days[col-1], daoHours[row-1], daoHours[row]);
                Label courseLabel = new Label(courseName.toUpperCase());
                GridPane.setHalignment(courseLabel, HPos.CENTER);
                GridPane.setValignment(courseLabel, VPos.CENTER);
                gridTable.add(courseLabel, col, row);
            }
        }

        closeButton.setOnAction(event -> {
            stage.close();
            parentStage.show();
        });
    }
    
    // Retrieve the course for a specific day and hour range
    private String getCourse(String day, Integer startHour, Integer endHour) {
        String result = "";
        ArrayList<Course> studentCourse = new ArrayList<Course>();
        try {
            studentCourse = model.getCourseEnrolledDao().loadCoursesEnrolled(this.userOnline.getUsername());
            for(Course crs : studentCourse) {
                String[] leftTime = crs.getTime().split(":");
                String hour = leftTime[0];
                Integer intHour = Integer.parseInt(hour);
                if((intHour >= startHour) && (intHour < endHour) && (crs.getDay().equals(day))) {
                    result = crs.getName();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public void showStage(Pane root) {
        Scene scene = new Scene(root, 900, 650);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("DashBoard");
        stage.show();
    }
}
