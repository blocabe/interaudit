package com.interaudit.service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.data.TimesheetData;
import com.interaudit.service.impl.RepositoryService;
import com.interaudit.service.param.SearchTimesheetParam;
import com.interaudit.service.view.ListTimesheetsView;
import com.interaudit.service.view.TimesheetView;
import com.interaudit.service.view.WeeklyTimeSheetView;



/**
 * @author bernard
 *
 */
public interface ITimesheetService {

	public void generateRawDataCsvFile(Integer exerciseNumber,String status,OutputStream os);
	public int getCountTimesheetToValidateForProject(Long projectId);
	public void createMissingTimesheets();
	public void sendTimesheetReminderEmails();
	public void sendInvoicesReminderEmails();
	public List<TimesheetData> getAllTimesheetToValidateForProject(Long projectId);
	public ListTimesheetsView buildTimeSheetListView(SearchTimesheetParam param);
	public ListTimesheetsView searchdMyTimeSheetListView(Long userId,int year,SearchTimesheetParam param);
	public WeeklyTimeSheetView buildWeeklyTimeSheetViewFromAgenda(Employee caller,Long employeeIdentifier,int week,int year);	
	public WeeklyTimeSheetView synchronizeWeeklyTimeSheetViewFromAgenda(Employee caller,Long employeeIdentifier,int week,int year);
	public void deleleteEvent(Long eventId,int year,int week,Long userId);
	public void removeRowFromTimesheet(Long idRow);
	public void copyRowFromTimesheet(Long idRow);
	/**
	 * @param t
	 * @return
	 */
	public Timesheet submitTimesheet(Timesheet t);
	
	/**
	 * @param t
	 * @return
	 */
	public Timesheet unsubmitTimesheet(Timesheet t);

	/**
	 * @param id
	 * @return
	 */
	public Timesheet validateTimesheet(Long id);
	
	/**
	 * @param id
	 * @return
	 */
	public Timesheet rejectTimesheet(Long id);
	
	/**
	 * @param t
	 * @return
	 */
	public Timesheet getPreviousTimesheet(Timesheet t);
	
	/**
	 * @param month
	 * @param year
	 * @return
	 */
	public boolean authorizedTimesheetCreation(int month, int year);
	
	/**
	 * @param timesheet
	 * @return
	 */
	public TimesheetView getTimesheetView(Timesheet timesheet);

	/**
	 * @param searchTimesheetParam
	 * @return
	 */
	public List<Timesheet> findTimesheets(SearchTimesheetParam searchTimesheetParam);
	
	public List<TimesheetData> searchTimesheets(SearchTimesheetParam searchTimesheetParam);
	
	/**
	 * @param user
	 * @param weekNumber
	 * @param year
	 * @return
	 */
	//public Timesheet createOne(Employee user,int weekNumber, String year);
	
	/**
	 * @param timesheet
	 * @param project
	 * @param libelle
	 */
	public void addRowToTimesheet(Long  timesheetId,Long projectId, Map<Integer,Float>  hours,String codePrestation/*,String year*/);
	
	
	/**
	 * @param id
	 * @return
	 */
	public Timesheet getOne(Long id);

	
	/**
	 * @return
	 */
	public List<Timesheet> getAll();

	
	/**
	 * @param timesheet
	 * @return
	 */
	public Timesheet saveOne(Timesheet timesheet);

	
	/**
	 * @param timesheet
	 * @return
	 */
	public Timesheet updateOne(Timesheet timesheet);

	
	/**
	 * @param id
	 */
	public void deleteOne(Long id);

	/**
	 * @param view
	 * @return
	 */
	public Timesheet updateTimesheetFromTimesheetView(TimesheetView view);	
	
	/**
	 * @param userId
	 * @param month
	 * @param year
	 * @return
	 */
	public Timesheet getOneTimesheetForUser(Long userId,int week, int year);
	
	
	/**
	 * @return
	 */
	public List<String> getAllDistinctTimesheetStatuses();
	
	/**
	 * @param timesheet
	 * @param project
	 */
	public void addActivityToTimesheet(Timesheet timesheet, Activity project);
	
	/**
	 * @param timesheet
	 * @param project
	 */
	public void removeActivityFromTimesheet(Timesheet timesheet, Activity project);
	
	public void addScheduledProjectForWeek(Timesheet timesheet,Date firstDate,Date secondDate);
	
	public RepositoryService getRepositoryService();
	
	
}
