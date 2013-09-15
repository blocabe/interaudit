package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.data.WeekPlanning;

public interface IWeekPlanningDao {

	
	
	//generic
	// get a timesheetRow from its id
	public WeekPlanning getOne(Long id);

	// get all timesheetRows
	public List<WeekPlanning> getAll();

	// save a timesheetRow
	public WeekPlanning saveOne(WeekPlanning weekPlanning);

	// update a timesheetRow
	public WeekPlanning updateOne(WeekPlanning weekPlanning);

	// delete a timesheetRow from its id
	public void deleteOne(Long id);

	public List<WeekPlanning> getAllForOnePlanningId(Long id);
	//public List<TimesheetRow> getAllAPRowsForOneTimesheetId(Long id);
	//public List<TimesheetRow> getAllGeneralRowsForOneTimesheetId(Long id);
	
	//public WeekPlanning getOneProjectRowForTimesheetId(Long timesheetId, Long projectId);
	
	public WeekPlanning getOneFromWeekNumber(String exercise, int weekNumber);
}
