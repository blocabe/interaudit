package com.interaudit.service.view;

import com.interaudit.domain.model.TimesheetRow;



public class TimesheetRowView implements Comparable<TimesheetRowView> {

	public static final int TIMESHEET_ROW_MAX_SIZE = 6;/*31;*/
	public static final double MAX_LEGAL_DAY_HOURS = 8.0;

	private String title;
	private String codePrestation;
	private String year;
	private String month;
	private String codeCustomer;
	private String codeAssocie;
	private String codeManager;

	private TimesheetRow row;
	
	

	private double[] cells;
	private boolean[] update;

	public TimesheetRowView() {
		this.title="";
		this.codePrestation = "";
		this.year = "";
		this.month = "";
		this.codeCustomer = "";
		this.codeAssocie = "";
		this.codeManager = "";
		this.cells = new double[TIMESHEET_ROW_MAX_SIZE];
		this.update= new boolean[TIMESHEET_ROW_MAX_SIZE];
	}
	
	public TimesheetRowView(String title) {
		this();
		this.title = title;
	}
	
	public TimesheetRowView( String title, 
							 String codePrestation,
							 String year,
							 String month,
							 String codeCustomer,
							 String codeAssocie,
							 String codeManager) {
		this();
		this.title= title;
		this.codePrestation = codePrestation;
		this.year = year;
		this.month = month;
		this.codeCustomer = codeCustomer;
		this.codeAssocie = codeAssocie;
		this.codeManager = codeManager;
	}
	
	public TimesheetRowView( String title, 
					 String codePrestation,
					 String year,
					 String month,
					 String codeCustomer,
					 String codeAssocie,
					 String codeManager,TimesheetRow row) {
		this(title,codePrestation,year,month,codeCustomer,codeAssocie,codeManager);
		this.row = row;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getHoursTotal() {
		double res = 0;
		for (double d : cells) {
			res += d;
		}
		return res;
	}
	
	
	public double getTotalExtraHours(){
		double res = 0;
		for (double d : cells) {
			double extraHours = d - TimesheetRowView.MAX_LEGAL_DAY_HOURS;
			if( extraHours > 0)res += extraHours;
		}
		return res;
	}

	public double[] getCells() {
		return cells;
	}

	public void setCells(double[] cells) {
		this.cells = cells;
	}

	public double getCellValue(int col) {
		return this.cells[col] ;
	}

	public void setCellValue(int col, double val) {
		this.cells[col] = val;
		//Mark for update
		this.update[col] = true;
	}
	
	public boolean cellNeedUpdate(int col) {
		return this.update[col] ;
	}

	public TimesheetRow getRow() {
		return row;
	}
	public void setRow(TimesheetRow row) {
		this.row = row;
	}
	
	public int compareTo(TimesheetRowView o) {
		int result = -1;
		
		if (this.equals(o)) {
			result = 0;
		} else {
			if (o instanceof TimesheetRowView) {
				TimesheetRowView rowView = (TimesheetRowView) o ;
				TimesheetRow row = rowView.getRow();
				int typeCompare = this.getRow().getId().compareTo(row.getId());
				if (typeCompare > 0 ) {
					result =  1;
				} else if (typeCompare < 0 ) {
					result =  -1;
				} 
			}
				/*else {
					//Same type
					if (this.getRow().getType().getId().equals(Constants.TIMESHEET_ROW_TYPE_ID_PROJECT)) {
						//Compare Project Type
						String ref = this.getRow().getProject().getProjectReference();
						if (ref == null) ref = this.getRow().getProject().getTemporaryReference(); 
						String compare = row.getProject().getProjectReference();
						if (compare == null) compare = row.getProject().getTemporaryReference();
						result =  ref.compareTo(compare);
					} else 	if (this.getRow().getType().getId().equals(Constants.TIMESHEET_ROW_TYPE_ID_ACTION_PLAN)) {
						//Compare AP Type
						return this.getTitle().compareTo(rowView.getTitle());
					}
				}
			} else {
				result = -1;
			}
			*/
			
		}
		
		return result;
	}
	public boolean[] getUpdate() {
		return update;
	}
	public void setUpdate(boolean[] update) {
		this.update = update;
	}
	public String getCodePrestation() {
		return codePrestation;
	}
	public void setCodePrestation(String codePrestation) {
		this.codePrestation = codePrestation;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getCodeCustomer() {
		return codeCustomer;
	}
	public void setCodeCustomer(String codeCustomer) {
		this.codeCustomer = codeCustomer;
	}
	public String getCodeAssocie() {
		return codeAssocie;
	}
	public void setCodeAssocie(String codeAssocie) {
		this.codeAssocie = codeAssocie;
	}
	public String getCodeManager() {
		return codeManager;
	}
	public void setCodeManager(String codeManager) {
		this.codeManager = codeManager;
	}
	
}
