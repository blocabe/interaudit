package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.data.AnnualPlanning;

public interface IAnnualPlanningDao {

	//generic
	// get a timesheet from its id
	/**
	 * @param id
	 * @return
	 */
	public AnnualPlanning getOne(Long id);

	// get all timesheets
	/**
	 * @return
	 */
	public List<AnnualPlanning> getAll();

	// save a timesheet
	public AnnualPlanning saveOne(AnnualPlanning planning);

	// update a timesheet
	public AnnualPlanning updateOne(AnnualPlanning timesheet);

	// delete a timesheet from its id
	public void deleteOne(Long id);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	/*
	public List<AnnualPlanning> findPlanningsForUserId(Long userId);
	
	public AnnualPlanning findOnePlanningsForUserId(Long userId,int month,int year);
	
	public List<String> getAllDistinctPlanningStatuses();
	
	public List<AnnualPlanning> findPlannings(SearchTimesheetParam searchTimesheetParam);
	*/
	
	public AnnualPlanning getOneFromExercise(String exercise);
}
