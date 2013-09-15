package com.interaudit.domain.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EVENTS",schema="interaudit")
public class Event implements java.io.Serializable{
	
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_employee" , nullable = false)
	private Employee employee;
	
	//Chargeable event has activity set
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_activity" , nullable = true)
	private Activity activity;
    
    //non Chargeable event has task set
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="TASK_ID" , nullable = true)
    private Task task;
      
    private int year;
    private int month;
    //The day number within the year
    private int day;
    private int startHour;
    private int endHour;
    private String type;
    private String title;
    private Date dateOfEvent;
   
	private boolean valid = false;
	
    public  int getDayNumber(Calendar calendar){
    	
    	int ret = 0;
    	calendar.set(Calendar.YEAR, year);
	    calendar.set(Calendar.MONTH, month);
	    calendar.set(Calendar.DAY_OF_YEAR, day);
    	
    	int value = calendar.get(Calendar.DAY_OF_WEEK) ;
    	
    	switch(value){
    	case Calendar.MONDAY :
    		ret = 1;
    		break;
    	case Calendar.TUESDAY :
    		ret = 2;
    		break;
    		
    	case Calendar.WEDNESDAY :
    		ret = 3;
    		break;
    		
    	case Calendar.THURSDAY :
    		ret = 4;
    		break;
    		
    	case Calendar.FRIDAY :
    		ret = 5;
    		break;
    	
    	case Calendar.SATURDAY :
    		ret = 6;
    		break;
    		
    	case Calendar.SUNDAY :
    		ret = 7;
    		break;
    	}
    	
    	return ret;
    }
    
public  int getDayNumber(Date dateOfEvent){
    	
    	int ret = 0;
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOfEvent);
    	
    	int value = calendar.get(Calendar.DAY_OF_WEEK) ;
    	
    	switch(value){
    	case Calendar.MONDAY :
    		ret = 1;
    		break;
    	case Calendar.TUESDAY :
    		ret = 2;
    		break;
    		
    	case Calendar.WEDNESDAY :
    		ret = 3;
    		break;
    		
    	case Calendar.THURSDAY :
    		ret = 4;
    		break;
    		
    	case Calendar.FRIDAY :
    		ret = 5;
    		break;
    	
    	case Calendar.SATURDAY :
    		ret = 6;
    		break;
    		
    	case Calendar.SUNDAY :
    		ret = 7;
    		break;
    	}
    	
    	return ret;
    }
    
public  String getMonthAsString(){
    	
    	String ret = null;
    	
    	switch(this.month){
    	case 0:
    		ret = "Janvier";
    		break;
    		
    	case 1:
    		ret = "Fevrier";
    		break;
    		
    	case 2:
    		ret = "Mars";
    		break;
    		
    	case 3:
    		ret = "Avril";
    		break;
    		
    	case 4 :
    		ret = "Mai";
    		break;
    	
    	case 5 :
    		ret = "Juin";
    		break;
    		
    	case 6 :
    		ret = "Juillet";
    		break;
    		
    		
    	case 7:
    		ret = "Août";
    		break;
    		
    	case 8:
    		ret = "Septembre";
    		break;
    		
    	case 9:
    		ret = "Octobre";
    		break;
    		
    	case 10 :
    		ret = "Novembre";
    		break;
    	
    	case 11 :
    		ret = "Decembre";
    		break;
    		
 
    	}
    	
    	return ret;
    }
    
    

    
    
	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Event(Employee employee,Activity activity, /*Mission mission,*/Task task, int year, int month,
			int day, int startHour, int endHour, String type, String title) {
		super();
		this.task = task;
		this.employee = employee;		
		this.activity = activity;
		
		//In case the activity is null
		if(this.activity != null){
			this.activity.getEvents().add(this);
		}
		
		this.year = year;
		this.month = month;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.type = type;
		this.title = title;
		
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.YEAR, year);
	    calendar.set(Calendar.MONTH, month);
	    calendar.set(Calendar.DAY_OF_YEAR, day);
		this.dateOfEvent = calendar.getTime();
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Event)  )){
			return false;
		}
		else{
			return this.getId().equals(((Event)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getStartHour() {
		return startHour;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public int getEndHour() {
		return endHour;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}



	public Date getDateOfEvent() {
		return dateOfEvent;
	}



	public void setDateOfEvent(Date dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}
	
	public String getStartHourAsString() {
		int part1 = (this.startHour / 4) + 8;
		String key = part1 +"";
		int reste = this.startHour % 4;
		switch(reste){
		case 0:
			key += "h00";
			break;
		case 1:
			key += "h15";
			break;
		case 2:
			key += "h30";
			break;
		case 3:
			key += "h45";
			break;
		}
		return key;
	}
	
	public String getEndHourAsString() {
		int part1 = (this.endHour / 4) + 8;
		String key = part1 +"";
		int reste = this.endHour % 4;
		switch(reste){
		case 0:
			key += "h00";
			break;
		case 1:
			key += "h15";
			break;
		case 2:
			key += "h30";
			break;
		case 3:
			key += "h45";
			break;
		}
		return key;
	}
	
	public int getMidHour(){
		return (int) ((this.startHour + this.endHour)/2);
	}


	
	public Task getTask() {
		return task;
	}



	public void setTask(Task task) {
		this.task = task;
	}
	


	public boolean isValid() {
		return valid;
	}



	public void setValid(boolean valid) {
		this.valid = valid;
	}



	public Activity getActivity() {
		return activity;
	}



	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	
    
    

}
