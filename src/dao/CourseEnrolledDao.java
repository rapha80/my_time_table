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
	public interface CourseEnrolledDao {
		void setupCourseEnrolled() throws SQLException;
		void createRow(String coursename, String username) throws SQLException;
		void deleteRow(String coursename, String username) throws SQLException;
		ArrayList<Course> loadCoursesEnrolled(String username) throws SQLException;
	}


