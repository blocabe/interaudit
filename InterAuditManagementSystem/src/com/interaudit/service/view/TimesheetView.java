package com.interaudit.service.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.interaudit.domain.model.Timesheet;
import com.interaudit.util.Constants;

public class TimesheetView {

	//private List<String> years;
	private String exercise;
	
	private String weekNumber;
	
	private Long timesheetId;
	
	private String timesheetStatus;
	
	private String userName;
	
	private boolean isOperationalManager;
	
	private Calendar calendar;
	
	private Date date;
	
	private Date weekStartDate;
	
	private Date weekEndDate;

	private ArrayList<TimesheetRowView> rows;
	
	private TimesheetRowView columnTotals;
	
	public TimesheetView() {
		calendar = Calendar.getInstance();
		this.rows = new ArrayList<TimesheetRowView>();
		this.columnTotals = new TimesheetRowView("Total");
	}
	
	public TimesheetView(Timesheet timesheet) {
		this();
		this.timesheetId = timesheet.getId();
		this.timesheetStatus = timesheet.getStatus();
		this.userName = timesheet.getUser().getFullName();
		this.isOperationalManager = false;
		this.date = timesheet.getDate();
		calendar.setTime(date);
	}
	
	public void updateCell(int row, int col, double value) {
		double oldValue = rows.get(row).getCellValue(col); 
		
		rows.get(row).setCellValue(col, value);
		
		//updateColumn Total
		columnTotals.setCellValue(col, columnTotals.getCellValue(col) - oldValue + value);
	}
	
	
	public double getRowTotal(int row) {
		return rows.get(row).getHoursTotal();
	}
	
	public double getColumnTotal(int col) {
		return columnTotals.getCellValue(col);
	}
	
	public double getGlobalTotal() {
		return columnTotals.getHoursTotal();
	}
	
	public void addRow(TimesheetRowView rowView) {
		this.rows.add(rowView);
	}
	
	public void initializeColumnTotals(double[] totals) {
		columnTotals.setCells(totals);
	}
	
	public void removeRow(Long id) {
		for (TimesheetRowView rowView : rows) {
			if (rowView.getRow().getId().equals(id)) {
				rows.remove(rowView);
				break;
			}
		}
	}
	
	
	
	public void removeProjectRow(Long projectId) {
		for (TimesheetRowView rowView : rows) {
			/*
			if (rowView.getRow().getProject() != null) {
				if (rowView.getRow().getProject().getId().equals(projectId)) {
					rows.remove(rowView);
					break;
				}
			}
			*/
		}
	}
	
	public boolean isEmptyRow(Long id) {
		boolean empty = false;
		for (TimesheetRowView rowView : rows) {
			if (rowView.getRow().getId().equals(id)) {
				if (rowView.getHoursTotal() == 0) {
					empty = true;
				}
				break;
			}
		}
		return empty;
	}
	
	public boolean isEmptyProjectRow(Long projectId) {
		boolean empty = false;
		for (TimesheetRowView rowView : rows) {
			if (rowView.getRow().getId().equals(Constants.TIMESHEET_ROW_TYPE_ID_PROJECT)) {
				/*
				Mission p = rowView.getRow().getProject();
				if (p.getId().equals(projectId)) {
					if (rowView.getHoursTotal() == 0) {
						empty = true;
					}
					break;
				}
				*/
			}
		}
		return empty;
	}
	
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<TimesheetRowView> getRows() {
		return rows;
	}

	public void setRows(ArrayList<TimesheetRowView> rows) {
		this.rows = rows;
	}
	
	public TimesheetRowView getColumnTotals() {
		return columnTotals;
	}

	public void setColumnTotals(TimesheetRowView columnTotals) {
		this.columnTotals = columnTotals;
	}

	public String getFormattedDate() {
		SimpleDateFormat sdf = new SimpleDateFormat( "MMMMMMM yyyy",Locale.ENGLISH );
		Date dating = calendar.getTime();
		return sdf.format(dating);
	}
	
	public int getNumberOfDays() {
		//return calendar.getActualMaximum(Calendar.DATE);
		return TimesheetRowView.TIMESHEET_ROW_MAX_SIZE;
	}
	
	public List<Integer> getWeekEndDays() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		//Backup current day of calendar
		int tempDate = calendar.get(Calendar.DATE);
		for (int i = 1; i <= calendar.getActualMaximum(Calendar.DATE);i++) {
			calendar.set(Calendar.DATE, i);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); 
	        if ((dayOfWeek == Calendar.SATURDAY) || (dayOfWeek == Calendar.SUNDAY)) {
	            res.add(i);
	        } 
		}
		//Restore current day
		calendar.set(Calendar.DATE, tempDate);
		
		return res;
	}
	
	public String getInitialDataSource() {
		//StringBuffer buf = new StringBuffer();
		/*
		JSONArray dataSource = new JSONArray();
		try {
		//Add normal rows
		for(Iterator<TimesheetRowView> it = rows.iterator(); it.hasNext();) {
			TimesheetRowView row = it.next();
			JSONObject dataSourceEntry = new JSONObject();

			dataSourceEntry.put("type", row.getTitle());
			for (int i=1; i <=getNumberOfDays();i++) {
				dataSourceEntry.put("day"+ String.valueOf(i),TimesheetViewManager.doubleToString(row.getCellValue(i)));
			}
			dataSourceEntry.put("total",TimesheetViewManager.doubleToString(row.getHoursTotal()));
			dataSourceEntry.put("typeF",row.getTitle());
			//Adding titles for additional information display
			if (row.getRow().getProject() != null) {
				dataSourceEntry.put("title", row.getRow().getProject().getTitle());
			} else if (row.getRow().getActionPlan() != null) {
				dataSourceEntry.put("title", row.getRow().getActionPlan().getBeneficiaryCountry().getDescription() + " " + row.getRow().getActionPlan().getYear());
			} else {
				dataSourceEntry.put("title", row.getTitle());
				dataSourceEntry.put("type", row.getRow().getType().getParent().getDescription());
			}
			dataSource.put(dataSourceEntry);
		}
		//Add columnTotals row
		JSONObject columnTotalsRow = new JSONObject();
		columnTotalsRow.put("type",columnTotals.getTitle());
		for (int i=1; i <=getNumberOfDays();i++) {
			columnTotalsRow.put("day"+String.valueOf(i), TimesheetViewManager.doubleToString(columnTotals.getCellValue(i)));
		}
		columnTotalsRow.put("total", TimesheetViewManager.doubleToString(columnTotals.getHoursTotal()));
		columnTotalsRow.put("typeF", columnTotals.getTitle());
		columnTotalsRow.put("title", "Day Total");
		dataSource.put(columnTotalsRow);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataSource.toString();
		*/
		return null;
		
		
	}

	public String getInitialRowMapping() {
		/*
		StringBuffer buf = new StringBuffer();
		for(Iterator<TimesheetRowView> it = rows.iterator(); it.hasNext();) {
			TimesheetRowView row = it.next();
			
			buf.append("{rowTypeId:" + row.getRow().getType().getId());
			buf.append(",recordIndex:" + rows.indexOf(row));
			if (row.getRow().getActionPlan() != null) {
				buf.append(",year:" + row.getRow().getActionPlan().getYear());
				buf.append(",countryId:" +  row.getRow().getActionPlan().getBeneficiaryCountry().getId());
			} else if (row.getRow().getProject() != null) {
				buf.append(",projectId:" +  row.getRow().getProject().getId());
			}
			
			buf.append(",title:\"" + row.getTitle() +"\"}");
			if (it.hasNext()) buf.append(",");
		}
		return buf.toString();
		*/
		return null;
	}
	
	public Long getTimesheetId() {
		return timesheetId;
	}

	public void setTimesheetId(Long timesheetId) {
		this.timesheetId = timesheetId;
	}

	

	

	public String getTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(String timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean getIsOperationalManager() {
		return isOperationalManager;
	}

	public void setOperationalManager(boolean isOperationalManager) {
		this.isOperationalManager = isOperationalManager;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public String getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(String weekNumber) {
		this.weekNumber = weekNumber;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public Date getWeekStartDate() {
		return weekStartDate;
	}

	public void setWeekStartDate(Date weekStartDate) {
		this.weekStartDate = weekStartDate;
	}

	public Date getWeekEndDate() {
		return weekEndDate;
	}

	public void setWeekEndDate(Date weekEndDate) {
		this.weekEndDate = weekEndDate;
	}
	
}
