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

public class CourseDaoImpl implements CourseDao {
    private final String TABLE_NAME = "courses";

    public CourseDaoImpl() {
    }

    /**
     * Creates the "courses" table and inserts data from a CSV file if the table doesn't exist.
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void setupCourse() throws SQLException {
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();) {

            String checkTableSql = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_NAME + "'";
            ResultSet resultSet = stmt.executeQuery(checkTableSql);
            boolean tableExists = resultSet.next();

            if (!tableExists) {
                String createTableSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (coursename VARCHAR(30) NOT NULL,"
                        + "capacity VARCHAR(30) NOT NULL," + "year VARCHAR(30) NOT NULL," + "deliverymode VARCHAR(30) NOT NULL,"
                        + "dayoflecture VARCHAR(30) NOT NULL," + "timeoflecture VARCHAR(30) NOT NULL,"
                        + "durationoflecture VARCHAR(30) NOT NULL," + "PRIMARY KEY (coursename))";
                stmt.executeUpdate(createTableSql);

                String csvFilePath = "src/dao/course.csv";

                String insertDataSql = "INSERT INTO " + TABLE_NAME + " (coursename, capacity, year, deliverymode, dayoflecture, timeoflecture, durationoflecture) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement pstmt = connection.prepareStatement(insertDataSql);
                     BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
                    String line;
                    boolean isFirstLine = true;
                    while ((line = reader.readLine()) != null) {
                        if (isFirstLine) {
                            isFirstLine = false;
                            continue;
                        }
                        String[] data = line.split(",");
                        if (data.length == 7) {
                            pstmt.setString(1, data[0].toLowerCase());
                            pstmt.setString(2, data[1]);
                            pstmt.setString(3, data[2]);
                            pstmt.setString(4, data[3]);
                            pstmt.setString(5, data[4]);
                            pstmt.setString(6, data[5]);
                            pstmt.setString(7, data[6]);
                            pstmt.executeUpdate();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Retrieves a course from the database based on the provided course name.
     *
     * @return the Course object if found, null otherwise
     * @throws SQLException if a database access error occurs
     */
    @Override
    public Course getCourse(String course) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE coursename = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, course);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Course crs = new Course();
                    crs.setName(rs.getString("coursename"));
                    crs.setCapacity(rs.getString("capacity"));
                    crs.setYear(rs.getString("year"));
                    crs.setMode(rs.getString("deliverymode"));
                    crs.setDay(rs.getString("dayoflecture"));
                    crs.setTime(rs.getString("timeoflecture"));
                    crs.setDuration(rs.getString("durationoflecture"));
                    return crs;
                }
                return null;
            }
        }
    }

    /**
     * Searches for courses based on a keyword.
     *
     * @return an ArrayList of Course objects matching the keyword
     * @throws SQLException if a database access error occurs
     */
    public ArrayList<Course> searchCourses(String kword) throws SQLException {
        ArrayList<Course> result = new ArrayList<Course>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE coursename LIKE '%" + kword + "%'";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {

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

    /**
     * Changes the capacity of a course.
     *
     * @throws SQLException if a database access error occurs
     */
    public void changeCapacity(String name, String newCapacity) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET capacity = " + newCapacity + " WHERE coursename = '" + name + "'";
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();) {
            stmt.executeUpdate(sql);
        }
    }
}
