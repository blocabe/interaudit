package com.interaudit.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.ITimesheetRowDao;
import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.TimesheetRow;
import com.interaudit.service.ITimesheetRowService;


//All methods of this class take place in a transaction
@Transactional
public class TimesheetRowService implements ITimesheetRowService {

	
	private ITimesheetRowDao timesheetRowDao;
	

	// Pure Business services
	public boolean synchronizeRowFromActivity(Timesheet timesheet, Activity activity) {
		//Test if row already exists.
		boolean needUpdate = false;
		if (getOneActivityRowForTimesheetId(timesheet, activity.getId()) == null) {
			//Create new row from activity
			String codePrestation = activity.getTask().getCodePrestation();
			TimesheetRow row = new TimesheetRow(timesheet, activity,null,activity.getMission().getAnnualBudget().getContract().getCustomer().getCompanyName(),
					codePrestation, timesheet.getExercise());
			timesheet.addRow(row);
			timesheetRowDao.saveOne(row);
			needUpdate = true;
		}
		return needUpdate;
	}
	
	// Custom services
	public TimesheetRow getOneActivityRowForTimesheetId(Timesheet t, Long activityId) {
		return timesheetRowDao.getOneActivityRowForTimesheetId(t.getId(), activityId);
	}
	
	/**
	 * @param timesheet
	 * @param project
	 */
	public  void removeActivityFromTimesheet(Timesheet timesheet, Activity activity){
		TimesheetRow row = getOneActivityRowForTimesheetId(timesheet, activity.getId());
		if(row != null){
			timesheetRowDao.deleteOne(row.getId());
		}
	}
	
	
	public List<TimesheetRow> getAllForOneTimesheetId(Long id) {
		//return timesheetRowDao.getAllForOneTimesheetId(id);
		return null;
	}
	public List<TimesheetRow> getAllAPRowsForOneTimesheetId(Long id) {
		//return timesheetRowDao.getAllAPRowsForOneTimesheetId(id);
		return null;
	}
	public List<TimesheetRow> getAllGeneralRowsForOneTimesheetId(Long id) {
		//return timesheetRowDao.getAllGeneralRowsForOneTimesheetId(id);
		return null;
	}
	
	
	// Services
	public void deleteArray(TimesheetRow[] timesheetRows) {
		for (TimesheetRow p : timesheetRows) {
			timesheetRowDao.deleteOne(p.getId());
		}
	}

	public void deleteOne(Long id) {
		timesheetRowDao.deleteOne(id);
	}

	public List<TimesheetRow> getAll() {
		return timesheetRowDao.getAll();
	}

	public TimesheetRow getOne(Long id) {
		return timesheetRowDao.getOne(id);
	}

	public TimesheetRow[] saveArray(TimesheetRow[] timesheetRows) {
		TimesheetRow[] timesheetRows2 = new TimesheetRow[timesheetRows.length];
		for (int i = 0; i < timesheetRows.length; i++) {
			timesheetRows2[i] = timesheetRowDao.saveOne(timesheetRows[i]);
		}
		return timesheetRows2;
	}

	public TimesheetRow saveOne(TimesheetRow timesheetRow) {
		return timesheetRowDao.saveOne(timesheetRow);
	}

	public TimesheetRow[] updateArray(TimesheetRow[] timesheetRows) {
		TimesheetRow[] timesheetRows2 = new TimesheetRow[timesheetRows.length];
		for (int i = 0; i < timesheetRows.length; i++) {
			timesheetRows2[i] = timesheetRowDao.updateOne(timesheetRows[i]);
		}
		return timesheetRows2;
	}

	public TimesheetRow updateOne(TimesheetRow timesheetRow) {
		return timesheetRowDao.updateOne(timesheetRow);
	}
	
	
	
	// Inject dao layer
	public ITimesheetRowDao getTimesheetRowDao() {
		return timesheetRowDao;
	}

	public void setTimesheetRowDao(ITimesheetRowDao timesheetRowDao) {
		this.timesheetRowDao = timesheetRowDao;
	}
	
	
}
