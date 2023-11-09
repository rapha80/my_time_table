package model;

public class Course {
	/*Attributes*/
    private String name;
    private String capacity;
    private String year;
    private String mode;
    private String day;
    private String time;
    private String duration;

    /*Constructors*/
    public Course(){
        this.name="";
        this.capacity="";
        this.year="";
        this.mode="";
        this.day="";
        this.time="";
        this.duration="";
    }
    
    public Course(String a,String b,String c,String d,String e,String f,String g) {
    	this.name=a;
        this.capacity=b;
        this.year=c;
        this.mode=d;
        this.day=e;
        this.time=f;
        this.duration=g;
    }

    /*Getters and Setters*/
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getCapacity(){
        return this.capacity;
    }

    public void setCapacity(String nb){
        this.capacity=nb;
    }

    public String getYear(){
        return this.year;
    }

    public void setYear(String y){
        this.year=y;
    }

    public String getMode(){
        return this.mode;
    }

    public void setMode(String d){
        this.mode=d;
    }

    public String getDay(){
        return this.day;
    }

    public void setDay(String dol){
        this.day=dol;
    }

    public String getTime(){
        return this.time;
    }

    public void setTime(String t){
        this.time=t;
    }

    public String getDuration(){
        return this.duration;
    }

    public void setDuration(String dur){
        this.duration=dur;
    }
}
