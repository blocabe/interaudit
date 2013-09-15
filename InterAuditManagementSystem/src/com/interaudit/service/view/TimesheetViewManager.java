package com.interaudit.service.view;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.TimesheetRow;
import com.interaudit.util.Constants;



public final class TimesheetViewManager {

	private static TimesheetViewManager manager;

	private HashMap<String,TimesheetView> views;

	private TimesheetViewManager() {
		views = new HashMap<String,TimesheetView>();
	}

	public static TimesheetViewManager getInstance() {
		if (manager == null)
			manager = new TimesheetViewManager();
		return manager;
	}

	public void register(TimesheetView view) {
		views.put(String.valueOf(view.getTimesheetId()), view);
	}

	public TimesheetView get(String code) {
		return views.get(code);
	}

	
	
	public void setTimesheetRowSelectionView(TimesheetRowSelectionView selectionView,Long id, List<Mission> teamProjects) {
		TimesheetView view = get(id.toString());
		//System.out.println("Update selectionView from view : " + view);
		selectionView.setUsedGeneralItems(getUsedGeneralItemsForView(view));
		selectionView.setUsedActionPlanItems(getUsedAPItemsForView(view));
		//selectionView.setUsedProjectItems(getUsedProjectItemsForView(view, teamProjects));
	}
	
	private ArrayList<TimesheetRow> getUsedGeneralItemsForView(TimesheetView view) {
		ArrayList<TimesheetRow> result = new ArrayList<TimesheetRow>();
		for(TimesheetRowView rowView : view.getRows()) {
			TimesheetRow row = rowView.getRow();
			if (!(row.getId().equals(Constants.TIMESHEET_ROW_TYPE_ID_ACTION_PLAN))
					&& !(row.getId().equals(Constants.TIMESHEET_ROW_TYPE_ID_PROJECT)))
				result.add(row);
		}
		return result;
	}
	
	private ArrayList<TimesheetRow> getUsedAPItemsForView(TimesheetView view) {
		ArrayList<TimesheetRow> result = new ArrayList<TimesheetRow>();
		for(TimesheetRowView rowView : view.getRows()) {
			TimesheetRow row = rowView.getRow();
			if (row.getId().equals(Constants.TIMESHEET_ROW_TYPE_ID_ACTION_PLAN))
				result.add(row);
		}
		return result;
	}
	
	public ArrayList<TimesheetRow> getUsedProjectItemsForView(TimesheetView view, List<Mission> teamProjects) {
		ArrayList<TimesheetRow> result = new ArrayList<TimesheetRow>();
		for(TimesheetRowView rowView : view.getRows()) {
			TimesheetRow row = rowView.getRow();
			if (row.getId().equals(Constants.TIMESHEET_ROW_TYPE_ID_PROJECT)) {
				//Add cancelled or completed or rejected projects
				/*
				if ((row.getProject().getStatus().equals(Constants.PROJECT_STATUS_CODE_CANCELLED))
					|| (row.getProject().getStatus().equals(Constants.PROJECT_STATUS_CODE_COMPLETED))
					|| (row.getProject().getStatus().equals(Constants.PROJECT_STATUS_CODE_REJECTED))) {
					result.add(row);
				} else if (!teamProjects.contains(row.getProject())){
					//Add projects external to the team
					result.add(row);
				}
				*/
				
			}
		}
		return result;
	}
	
	public static float stringToDouble(String s) {
		int indexOfSep = s.indexOf(":");
		float result = 0;
		try {
			result = Float.parseFloat(s.substring(0, indexOfSep));
			if("30".equals(s.substring(indexOfSep +1))) {
				result += 0.5;
			}
		} catch (NumberFormatException e) {
			result = 0;
		}
		return result;
	}

	public static String doubleToString(double d) {
		if (Double.compare(d, 0) == 0) {
			return "";
		} else {
			NumberFormat nb = NumberFormat.getInstance();
			nb.setMaximumFractionDigits(0);
			nb.setMinimumIntegerDigits(2);
			
			String frac = "00";
			double d1 = d - Math.floor(d);
			if (Double.compare(d1, 0) == 1) {
				frac = "30";
			}
			return nb.format(Math.floor(d)) + ":" + frac;
		}
	}
}
