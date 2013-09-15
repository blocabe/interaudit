package com.interaudit.domain.model.data;

import java.io.Serializable;
import java.util.Date;

public class TimesheetOption  implements Serializable , java.lang.Comparable<TimesheetOption> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	private int weekNumber;
	private Date lastRejectedDate;
	
	
	
	public TimesheetOption(String status, int weekNumber, Date lastRejectedDate) {
		super();
		this.status = status;
		this.weekNumber = weekNumber;
		this.lastRejectedDate = lastRejectedDate;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	public Date getLastRejectedDate() {
		return lastRejectedDate;
	}

	public void setLastRejectedDate(Date lastRejectedDate) {
		this.lastRejectedDate = lastRejectedDate;
	}
	
	 public int compareTo(TimesheetOption other) { 
	      int nombre1 = ((TimesheetOption) other).getWeekNumber(); 
	      int nombre2 = this.getWeekNumber(); 
	      if (nombre1 > nombre2)  return -1; 
	      else if(nombre1 == nombre2) return 0; 
	      else return 1; 
	   } 

	

	
}
