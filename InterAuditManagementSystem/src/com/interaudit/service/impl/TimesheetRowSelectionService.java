package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.TimesheetRow;
import com.interaudit.service.IProjectService;
import com.interaudit.service.ITimesheetRowSelectionService;
import com.interaudit.service.ITimesheetRowService;
import com.interaudit.service.ITimesheetService;
import com.interaudit.service.view.TimesheetRowSelectionView;
import com.interaudit.service.view.TimesheetRowView;
import com.interaudit.service.view.TimesheetView;
import com.interaudit.service.view.TimesheetViewManager;
import com.interaudit.util.Constants;

//All methods of this class take place in a transaction
@Transactional
public class TimesheetRowSelectionService implements ITimesheetRowSelectionService {

	private IProjectService projectService;
	private ITimesheetService timesheetService;
	private ITimesheetRowService timesheetRowService;

	public TimesheetRowSelectionView getTimesheetRowSelectionView(Long id, Long userId) {

		//TimesheetRowSelectionView view = new TimesheetRowSelectionView(timesheetRowTypeService.getAllGeneralTypes(),timesheetRowService.getAllGeneralRowsForOneTimesheetId(id),timesheetRowService.getAllAPRowsForOneTimesheetId(id)); 
		TimesheetRowSelectionView view = null/*new TimesheetRowSelectionView(timesheetRowTypeService.getAllGeneralTypes(),null,null)*/;
		List<Mission> teamProjects = projectService.findProjectsForUserId(userId);
		TimesheetViewManager.getInstance().setTimesheetRowSelectionView(view, id, teamProjects);
		return view;
	}

	public String addRowToView(TimesheetView view, Long id) {
		
		TimesheetRow row = new TimesheetRow();
		//row.setType(rowType);
		TimesheetRowView rowView = null/*new TimesheetRowView(rowType.getDescription())*/;
		rowView.setRow(row);
		view.addRow(rowView);
		return rowView.getTitle();
	}

	public String removeRowFromView(TimesheetView view, Long id) {
		//TimesheetRowType rowType = timesheetRowTypeService.getOne(id);
		view.removeRow(id);
		//return rowType.getDescription();
		return null;
	}

		
	public TimesheetRowView addProjectRowToView(TimesheetView view, Long projectId) {
		Mission project = projectService.getOne(projectId);
		if (project == null) {
			//Project doesn't exist
			return null;
		} else {
			//TimesheetRowType rowType = timesheetRowTypeService.getOne(Constants.TIMESHEET_ROW_TYPE_ID_PROJECT);
			TimesheetRow row = new TimesheetRow();
			//row.setType(null);
			//row.setProject(project);
			TimesheetRowView rowView = new TimesheetRowView(project.getReference());
			rowView.setRow(row);
			view.addRow(rowView);
			return rowView;
		}
	}
	
	public String removeProjectRowFromView(TimesheetView view, Long projectId) {
		Mission project = projectService.getOne(projectId);
		if (project == null) {
			//Project doesn't exist
			return null;
		} else {
			view.removeProjectRow(projectId);
			return project.getReference() + project.getTitle();
		}
	}

	public List<TimesheetRowView> synchronizeView(TimesheetView view) {
		ArrayList<TimesheetRowView> newRows = new ArrayList<TimesheetRowView>();

		Timesheet t = timesheetService.getOne(view.getTimesheetId());
		List<Mission> allProjects = projectService.findProjectsForUserId(t.getUser().getId());
		//Remove projects that are already in timesheet
		for(TimesheetRowView rowView : view.getRows()) {
			/*
			if (rowView.getRow().getProject() != null) {
				allProjects.remove(rowView.getRow().getProject());
			}
			*/
		}
		
		//The remaining projects needs to be added to the view
		for(Mission p : allProjects) {
			//Skip projects that are Cancelled, completed or rejected
			if ((p.getStatus().equals(Constants.PROJECT_STATUS_CODE_CANCELLED))
				|| (p.getStatus().equals(Constants.PROJECT_STATUS_CODE_COMPLETED))
				|| (p.getStatus().equals(Constants.PROJECT_STATUS_CODE_REJECTED))) {
				//SKIPPED
			} else {
				//Create a new row view
				//System.out.println("Create row view for " + p.getId());
				//TimesheetRowType rowType = timesheetRowTypeService.getOne(Constants.TIMESHEET_ROW_TYPE_ID_PROJECT);
				TimesheetRow row = new TimesheetRow();
				//row.setType(rowType);
				//row.setProject(p);
				TimesheetRowView newRowView = new TimesheetRowView(p.getReference());
				newRowView.setRow(row);
				newRows.add(newRowView);
			}
		}
		return newRows;
	}

	
	//	Services injection	
	public ITimesheetRowService getTimesheetRowService() {
		return timesheetRowService;
	}

	public void setTimesheetRowService(ITimesheetRowService timesheetRowService) {
		this.timesheetRowService = timesheetRowService;
	}
	
	public ITimesheetService getTimesheetService() {
		return timesheetService;
	}

	public void setTimesheetService(ITimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}

	public IProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	
}
