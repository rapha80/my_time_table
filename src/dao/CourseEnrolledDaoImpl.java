package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Course;
import model.User;

public class CourseEnrolledDaoImpl implements CourseEnrolledDao {
    private final String TABLE_NAME = "courseEnrolled";
    private final String TABLE_NAME_2 = "courses";

    public CourseEnrolledDaoImpl() {
    }

    /**
     * Creates the "courseEnrolled" table if it doesn't exist.
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void setupCourseEnrolled() throws SQLException {
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();) {

            String checkTableSql = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_NAME + "'";
            ResultSet resultSet = stmt.executeQuery(checkTableSql);
            boolean tableExists = resultSet.next();

            if (!tableExists) {
                String createTableSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(course VARCHAR(30) NOT NULL,"
                        + "user VARCHAR(30) NOT NULL, " + " PRIMARY KEY (course, user),"
                        + "FOREIGN KEY (course) REFERENCES courses (coursename), "
                        + "FOREIGN KEY (user) REFERENCES users (username)); ";
                stmt.executeUpdate(createTableSql);
            }
        }
    }

    /**
     * Creates a row in the "courseEnrolled" table with the provided course name and username.
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void createRow(String coursename, String username) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, coursename);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a row from the "courseEnrolled" table with the provided course name and username.
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void deleteRow(String coursename, String username) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE user = ? AND course = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, coursename);
            stmt.executeUpdate();
        }
    }

    /**
     * Loads the courses enrolled by a user.
     *
     * @return an ArrayList of Course objects representing the enrolled courses
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ArrayList<Course> loadCoursesEnrolled(String username) throws SQLException {
        ArrayList<Course> result = new ArrayList<Course>();
        String sql = "SELECT c.* FROM " + TABLE_NAME_2 + " c JOIN "
                + TABLE_NAME + " ce ON c.coursename = ce.course WHERE ce.user = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Course crs = new Course();
                    crs.setName(rs.getString("coursename"));
                    crs.setCapacity(rs.getString("capacity"));
                    crs.setYear(rs.getString("year"));
                    crs.setMode(rs.getString("deliverymode"));
                    crs.setDay(rs.getString("dayoflecture"));
                    crs.setTime(rs.getString("timeoflecture"));
                    crs.setDuration(rs.getString("durationoflecture"));
                    result.add(crs);
                }
            }
        }
        return result;
    }
}


