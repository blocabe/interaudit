package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.TimesheetCell;

public interface ITimesheetCellDao {

	//generic
	// get a timesheetCell from its id
	public TimesheetCell getOne(Long id);

	// get all timesheetCells
	public List<TimesheetCell> getAll();

	// save a timesheetCell
	public TimesheetCell saveOne(TimesheetCell timesheetCell);

	// update a timesheetCell
	public TimesheetCell updateOne(TimesheetCell timesheetCell);

	// delete a timesheetCell from its id
	public void deleteOne(Long id);
}
