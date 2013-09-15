package com.interaudit.service.view;

import java.util.ArrayList;
import java.util.List;

import com.interaudit.domain.model.TimesheetRow;



public class TimesheetRowSelectionView {

	private List<String> years;
	
	
	private List<TimesheetRow> usedGeneralItems;
	
	private List<TimesheetRow> usedActionPlanItems;
	private List<TimesheetRow> usedProjectItems;
	
	public TimesheetRowSelectionView( List<TimesheetRow> usedGeneralRows, List<TimesheetRow> usedActionPlanItems ) {		
		this.usedGeneralItems = usedGeneralRows;
		this.usedActionPlanItems = usedActionPlanItems;
		this.usedProjectItems = null;
	}
	
	public boolean isAlreadyUsed(TimesheetRow type) {
		boolean used = false;
		for (TimesheetRow row : usedGeneralItems) {
			if (row.getId().equals(type.getId())) {
				used = true;
				break;
			}
		}
		return used;
	}
	
	public List<TimesheetRow> getExisitingChildrenForActionPlanCategory() {
		ArrayList<TimesheetRow> result = new ArrayList<TimesheetRow>();
		for (TimesheetRow type: usedActionPlanItems) {
				result.add(type);
		}
		return result;
	}
	
	public List<Long> getUsedGeneralItemTypeIds() {
		List<Long> ids = new ArrayList<Long>();
		for (TimesheetRow row : usedGeneralItems) {
			ids.add(row.getId());
		}
		return ids;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	

	public List<TimesheetRow> getUsedGeneralItems() {
		return usedGeneralItems;
	}

	public void setUsedGeneralItems(List<TimesheetRow> usedGeneralItems) {
		this.usedGeneralItems = usedGeneralItems;
	}

	public List<TimesheetRow> getUsedActionPlanItems() {
		return usedActionPlanItems;
	}

	public void setUsedActionPlanItems(List<TimesheetRow> usedActionPlanItems) {
		this.usedActionPlanItems = usedActionPlanItems;
	}
	
	public List<TimesheetRow> getUsedProjectItems() {
		return usedProjectItems;
	}
	public void setUsedProjectItems(List<TimesheetRow> usedProjectItems) {
		this.usedProjectItems = usedProjectItems;
	}
	
}
