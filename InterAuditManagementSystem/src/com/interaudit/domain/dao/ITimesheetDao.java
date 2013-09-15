package com.interaudit.domain.dao;

import java.io.OutputStream;
import java.util.List;

import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.data.TimesheetData;
import com.interaudit.domain.model.data.TimesheetOption;
import com.interaudit.service.param.SearchTimesheetParam;

public interface ITimesheetDao {

	//generic
	// get a timesheet from its id
	/**
	 * @param id
	 * @return
	 */
	public Timesheet getOne(Long id);

	// get all timesheets
	/**
	 * @return
	 */
	public List<Timesheet> getAll();

	// save a timesheet
	public Timesheet saveOne(Timesheet timesheet);

	// update a timesheet
	public Timesheet updateOne(Timesheet timesheet);

	// delete a timesheet from its id
	public void deleteOne(Long id);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<Timesheet> findTimesheetsForUserId(Long userId);
	
	public List<TimesheetData>  findTimesheetsForUserId(Long userId,int year);
	
	public Timesheet findOneTimesheetsForUserId(Long userId,int week,int year);
	
	public List<String> getAllDistinctTimesheetStatuses();
	
	public List<Timesheet> findTimesheets(SearchTimesheetParam searchTimesheetParam);
	
	public List<TimesheetData> searchTimesheets(SearchTimesheetParam searchTimesheetParam);

	public abstract List<TimesheetOption> findTimesheetOptionsForUserId(Long userId,
			int year);
	
	public void generateRawDataCsvFile(Integer exerciseNumber,String status,OutputStream os);
}
