package com.interaudit.service;

import java.util.List;

import com.interaudit.service.view.TimesheetRowSelectionView;
import com.interaudit.service.view.TimesheetRowView;
import com.interaudit.service.view.TimesheetView;



public interface ITimesheetRowSelectionService {

	public TimesheetRowSelectionView getTimesheetRowSelectionView(Long id, Long userId);
	
	public String addRowToView(TimesheetView view, Long id);
	
	public String removeRowFromView(TimesheetView view, Long id);
	
	public TimesheetRowView addProjectRowToView(TimesheetView view, Long projectId);
	
	public String removeProjectRowFromView(TimesheetView view, Long projectId);
	
	public List<TimesheetRowView> synchronizeView(TimesheetView view);

	
}
