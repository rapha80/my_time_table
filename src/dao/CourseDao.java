package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Course;

	/**
	 * A data access object (DAO) is a pattern that provides an abstract interface 
	 * to a database or other persistence mechanism. 
	 * the DAO maps application calls to the persistence layer and provides some specific data operations 
	 * without exposing details of the database. 
	 */
	public interface CourseDao {
		void setupCourse() throws SQLException;
		Course getCourse(String course) throws SQLException;
		ArrayList<Course> searchCourses(String kword) throws SQLException;
		void changeCapacity(String name, String newCapacity) throws SQLException;
	}
