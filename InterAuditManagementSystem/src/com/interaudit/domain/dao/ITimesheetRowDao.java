package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.TimesheetRow;
import com.interaudit.domain.model.data.TimesheetData;

public interface ITimesheetRowDao {	
	//generic
	// get a timesheetRow from its id
	public TimesheetRow getOne(Long id);

	// get all timesheetRows
	public List<TimesheetRow> getAll();

	// save a timesheetRow
	public TimesheetRow saveOne(TimesheetRow timesheetRow);

	// update a timesheetRow
	public TimesheetRow updateOne(TimesheetRow timesheetRow);

	// delete a timesheetRow from its id
	public void deleteOne(Long id);
	
	public TimesheetRow copyRowFromTimesheet(Long id);

	public List<TimesheetRow> getAllRowsForOneTimesheetId(Long id);
	
	public List<TimesheetRow> getAllRowsToPayForOneProject(Long projectId);
	/*
	public List<TimesheetRow> getAllAPRowsForOneTimesheetId(Long id);
	public List<TimesheetRow> getAllGeneralRowsForOneTimesheetId(Long id);
	*/
	
	public TimesheetRow getOneActivityRowForTimesheetId(Long timesheetId, Long activityId);
	
	public List<TimesheetData> getAllTimesheetToValidateForProject(Long projectId);

	public abstract int getCountTimesheetToValidateForProject(Long projectId);
}
