package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.TimesheetRow;



public interface ITimesheetRowService {

	//Generic services
	
	// get a timesheetRow from its id
	public TimesheetRow getOne(Long id);

	// get all timesheetRows
	public List<TimesheetRow> getAll();
	
	// get all timesheetRows for one timesheet
	public List<TimesheetRow> getAllForOneTimesheetId(Long id);
	
	//get all ActionPlan timesheetRows for one timesheet
	public List<TimesheetRow> getAllAPRowsForOneTimesheetId(Long id);
	
	//get all ActionPlan timesheetRows for one timesheet
	public List<TimesheetRow> getAllGeneralRowsForOneTimesheetId(Long id);

	// save a timesheetRow
	public TimesheetRow saveOne(TimesheetRow timesheetRow);

	// update a timesheetRow
	public TimesheetRow updateOne(TimesheetRow timesheetRow);

	// delete a timesheetRow from its id
	public void deleteOne(Long id);

	// delete several timesheetRow at a time
	public void deleteArray(TimesheetRow[] timesheetRows);

	// save several timesheetRow at a time
	public TimesheetRow[] saveArray(TimesheetRow[] timesheetRows);

	// update several timesheetRow together
	public TimesheetRow[] updateArray(TimesheetRow[] timesheetRows);
	
	/**
	 * @param timesheet
	 * @param project
	 * @return
	 */
	public boolean synchronizeRowFromActivity(Timesheet timesheet, Activity activity);
	
	/**
	 * @param timesheet
	 * @param project
	 */
	public void removeActivityFromTimesheet(Timesheet timesheet, Activity activity);
}
