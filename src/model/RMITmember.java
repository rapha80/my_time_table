package model;

public class RMITmember {
	/*Attributes*/
    private String firstname;
    private String lastname;

    /*Constructors*/
    public RMITmember(){
        this.firstname="";
        this.lastname="";
    }
    
    public RMITmember(String f,String l){
    	this.firstname=f;
    	this.lastname=l;
    }

    /*Getters*/
    public String getFirstName(){
        return this.firstname;
    }
    
    public String getLastName() {
    	return this.lastname;
    }
    
    public void setFirstName(String fn) {
    	this.firstname=fn;
    }
    
    public void setLastName(String ln) {
    	this.lastname=ln;
    }

}