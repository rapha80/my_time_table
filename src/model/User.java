package model;

import java.util.ArrayList;
import java.util.List;

public class User extends RMITmember{
	/*Attributes*/
    private Integer id;
    private String username;
    private String password;
    private ArrayList<Course> stdCourses;
    

    /*Constructors*/
    public User(){
        super();
        this.id=3990214;
        this.stdCourses = new ArrayList<Course>();
    }
    
    public User(String u,String p, String fn, String ln, Integer i) {
    	super(fn,ln);
    	this.stdCourses = new ArrayList<Course>();
    	this.username=u;
    	this.password=p;
    }

    /*Getters*/
    public Integer getId(){
        return this.id;
    }

    public ArrayList<Course> getStdCourses(){
        return this.stdCourses;
    }

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setID(Integer i) {
		this.id=i;
	}
    
    /*Methods*/
    /**
     * This method add a Course to the student's ArrayList of Course thanks to the name of the course
     */
    public void addCourses(Course course){
        this.stdCourses.add(course);
    }

    /**
     * This method delete a course from the student's ArrayList of Course thanks to the index 
     */
    public void dropCourses(int index){
        this.stdCourses.remove(index);
    }
    
    public void changePwd(String newPWD) {
    	this.password=newPWD;
    }
}
