package model;

import java.sql.SQLException;

import dao.CourseDao;
import dao.CourseDaoImpl;
import dao.CourseEnrolledDao;
import dao.CourseEnrolledDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;

public class Model {
	private UserDao userDao;
	private User currentUser; 
	private CourseDao courseDao;
	private CourseEnrolledDao courseEnrolledDao;
	
	public Model() {
		// Initialize the data access objects
		userDao = new UserDaoImpl();
		courseDao = new CourseDaoImpl();
		courseEnrolledDao = new CourseEnrolledDaoImpl();
	}
	
	/**
	 * Set up the database by calling the setup methods of data access objects.
	 *
	 * @throws SQLException if there's an error during the setup process.
	 */
	public void setup() throws SQLException {
		userDao.setup();
		courseDao.setupCourse();
		courseEnrolledDao.setupCourseEnrolled();
	}
	
	/**
	 * Get the UserDao object.
	 *
	 * @return the UserDao object.
	 */
	public UserDao getUserDao() {
		return userDao;
	}
	
	/**
	 * Get the CourseDao object.
	 *
	 * @return the CourseDao object.
	 */
	public CourseDao getCourseDao() {
		return courseDao;
	}
	
	/**
	 * Get the CourseEnrolledDao object.
	 *
	 * @return the CourseEnrolledDao object.
	 */
	public CourseEnrolledDao getCourseEnrolledDao() {
		return courseEnrolledDao;
	}
	
	/**
	 * Get the current user.
	 *
	 * @return the current User object.
	 */
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	/**
	 * Set the current user.
	 */
	public void setCurrentUser(User user) {
		currentUser = user;
	}
}
