package com.interaudit.domain.dao;

import java.util.Date;
import java.util.List;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.data.WeekAssignmentPerEmployee;

public interface IWeekAssignmentPerEmployeDao {

	//generic
	// get a timesheetCell from its id
	public WeekAssignmentPerEmployee getOne(Long id);

	// get all timesheetCells
	public List<WeekAssignmentPerEmployee> getAll();

	// save a timesheetCell
	public WeekAssignmentPerEmployee saveOne(WeekAssignmentPerEmployee timesheetCell);

	// update a timesheetCell
	public WeekAssignmentPerEmployee updateOne(WeekAssignmentPerEmployee timesheetCell);

	// delete a timesheetCell from its id
	public void deleteOne(Long id);
	
	/**
	 * @param userId
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public List<WeekAssignmentPerEmployee> getAllAssignmentForUserIdInPeriod(Long userId,Long projectId,Date firstDate/*,Date secondDate*/);
	
	/**
	 * @param user
	 * @param weekNumber
	 * @param exercise
	 * @return
	 */
	public List<Mission> getAllScheduledProjectForUserInWeek(Employee user,int weekNumber, String exercise );
}
