/**
 * 
 */
package com.interaudit.domain.model.data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bernard
 *
 */
public class TimesheetDataForPlanning implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Date   startDate;
	private Date   endDate;
	private Double totalHours;
	
	
	
	public TimesheetDataForPlanning() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TimesheetDataForPlanning(Date startDate, Date endDate,
			Double totalHours) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalHours = totalHours;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}
	
	
}
