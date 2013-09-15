package com.interaudit.service.view;

import java.util.Date;
import java.util.List;

import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.TimesheetWeekReport;

public class AnnualTimesheetReportView {

	public static final int ECART_MOIS = 2;
	
	private String year;
	private int startMonth;
	private int endMonth;
	private List<Option> yearOptions;
	private List<TimesheetWeekReport> timesheetWeekReports;
	private int startWeekNumber;
	private int endWeekNumber;

	
	public AnnualTimesheetReportView() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AnnualTimesheetReportView(String year, int startMonth, int endMonth,
			List<Option> yearOptions,
			List<TimesheetWeekReport> timesheetWeekReports,
			int startWeekNumber, int endWeekNumber) {
		super();
		this.year = year;
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.yearOptions = yearOptions;
		this.timesheetWeekReports = timesheetWeekReports;
		this.startWeekNumber = startWeekNumber;
		this.endWeekNumber = endWeekNumber;		
	}


	







	private  String getMonthAsString(int month){
    	
    	String ret = null;
    	
    	switch(month){
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


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public int getStartMonth() {
		return startMonth;
	}


	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}


	public int getEndMonth() {
		return endMonth;
	}


	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}


	public List<Option> getYearOptions() {
		return yearOptions;
	}


	public void setYearOptions(List<Option> yearOptions) {
		this.yearOptions = yearOptions;
	}


	public List<TimesheetWeekReport> getTimesheetWeekReports() {
		return timesheetWeekReports;
	}


	public void setTimesheetWeekReports(
			List<TimesheetWeekReport> timesheetWeekReports) {
		this.timesheetWeekReports = timesheetWeekReports;
	}


	public int getStartWeekNumber() {
		return startWeekNumber;
	}


	public void setStartWeekNumber(int startWeekNumber) {
		this.startWeekNumber = startWeekNumber;
	}


	public int getEndWeekNumber() {
		return endWeekNumber;
	}


	public void setEndWeekNumber(int endWeekNumber) {
		this.endWeekNumber = endWeekNumber;
	}


	
	public String getStartMonthAsString() {
		return getMonthAsString(this.startMonth);
	}
	
	public String getEndMonthAsString() {
		int index = this.startMonth + AnnualPlanningView.ECART_MOIS;
		if(index > 11)index = 11;
		return getMonthAsString(index);
	}
	
	
	
}
