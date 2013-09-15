package com.interaudit.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.TimesheetRow;
import com.interaudit.service.ISearchTimesheetService;
import com.interaudit.service.ITimesheetService;
import com.interaudit.service.param.SearchTimesheetParam;
import com.interaudit.service.view.SearchTimesheetView;

@Transactional
public class SearchTimesheetService implements ISearchTimesheetService {
   
	private ITimesheetService timesheetService;
	
    public SearchTimesheetView getTimesheetSearchView() {
        SearchTimesheetView view = new SearchTimesheetView();
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat( "MMMMMMM yyyy",Locale.ENGLISH );
        List<String> startDates = new ArrayList<String>();
        List<String> endDates = new ArrayList<String>();
        
        int oldestMonth = 9;
        int oldestYear = 2007;
        Date currentDate = cal.getTime();
        //TODO Go back to last submitted timesheet date
        //Populate start dates
        while(!((cal.get(Calendar.YEAR) == oldestYear) && (cal.get(Calendar.MONTH) == oldestMonth))) {
        	//Add date to selection
        	startDates.add(sdf.format(cal.getTime()));
        	//decrement date
        	if (cal.get(Calendar.MONTH) == 0) {
        		cal.set(Calendar.MONTH, 11);
        		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-1);
        	} else
        		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
        }

        
        cal.setTime(currentDate);
        //Populate end dates
        for(int i = 0;i <3;i++) {
        	endDates.add(sdf.format(cal.getTime()));
        	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
        }
        view.setStartPeriodDates(startDates);
        view.setEndPeriodDates(endDates);
        return view;
    }

    public SearchTimesheetView searchTimesheet(SearchTimesheetParam searchTimesheetParam) {
        SearchTimesheetView view = getTimesheetSearchView();
        List<Timesheet> timesheets = timesheetService.findTimesheets(searchTimesheetParam);
        //Sort timesheet by period,user lastName, user firstName
        Collections.sort(timesheets,new Comparator<Timesheet>() {
			public int compare(Timesheet t1, Timesheet t2) {
				int compareResult = -1;
				
				Calendar c = Calendar.getInstance();
				c.setTime(t1.getDate());
				int m1 = c.get(Calendar.MONTH) + 1 ;
				int y1 = c.get(Calendar.YEAR); 
				c.setTime(t2.getDate());
				int m2 = c.get(Calendar.MONTH) + 1 ;
				int y2 = c.get(Calendar.YEAR);
				if (y1 > y2) {
					compareResult = 1;
				} else if (y1 < y2) {
					compareResult = -1;
				} else {
					if (m1 > m2) {
						compareResult = 1;
					} else if (m1 < m2) {
						compareResult = -1;
					} else {
						//Same period timesheet, sort by user name
						int lastNameCompare = t1.getUser().getLastName().compareTo(t2.getUser().getLastName());
						if (lastNameCompare == 0) {
							//Same last name, compare first name
							compareResult = t1.getUser().getFirstName().compareTo(t2.getUser().getFirstName());
						} else {
							//Nothing more to do
							compareResult = lastNameCompare;
						}
					}
				}
				return compareResult;	
			}
        });
        
        view.setTimesheets(timesheets);
        
        view.setTreeJSonObject(computeTreeJSonObject(timesheets));
        return view;
    }
    
    private String computeTreeJSonObject(List<Timesheet> timesheets) {
    	/*
    	SimpleDateFormat sdf = new SimpleDateFormat( "MMMMMMM yyyy",Locale.ENGLISH );
		
    	JSONObject validationTree  = new JSONObject();
    	JSONArray treeNodes = new JSONArray();
    	
		int currMonth = -1;
		int currYear = -1;
		
		JSONObject treeNode = null;
		JSONArray timesheetList = null;
		try {
		for(ListIterator<Timesheet> itTime = timesheets.listIterator() ; itTime.hasNext();) {
			Timesheet t = null;
			if (!itTime.hasPrevious()) {
				//First
				t = itTime.next();
				//Init currMonth and currYear
				Calendar c = Calendar.getInstance();
				c.setTime(t.getDate());
				currMonth = c.get(Calendar.MONTH);
				currYear = c.get(Calendar.YEAR);
				
				treeNode = new JSONObject();
				treeNode.put("month", sdf.format(c.getTime()));
				timesheetList = new JSONArray();
//				buf.append("{month:\"" + sdf.format(c.getTime()) + "\"");
//				buf.append(",timesheets:[");
				
			} else {
				//Test if we change month
				t = itTime.next();
				Calendar c = Calendar.getInstance();
				c.setTime(t.getDate());
				if ((c.get(Calendar.MONTH)==currMonth) && (c.get(Calendar.YEAR)==currYear)) {
					//Same month
				} else {
					//Close last month and open new one
					currMonth = c.get(Calendar.MONTH);
					currYear = c.get(Calendar.YEAR);
					
					treeNode.put("timesheets", timesheetList);
					treeNodes.put(treeNode);
					
					treeNode = new JSONObject();
					treeNode.put("month", sdf.format(c.getTime()));
					timesheetList = new JSONArray();
					
					//buf.append("]},{month:\"" + sdf.format(c.getTime()) + "\",timesheets:[");
					
				}
			}
			JSONObject timesheet = new JSONObject();
//			buf.append("{username:\"" + t.getUser().getLastName() + ", " + t.getUser().getFirstName() + "\"");
//			buf.append(",total:" + computeTimesheetTotal(t));
//			buf.append(",id:" + t.getId());
//			buf.append(",rows:[");
			timesheet.put("username", t.getUser().getLastName() + ", " + t.getUser().getFirstName());
			timesheet.put("total",computeTimesheetTotal(t));
			timesheet.put("id", t.getId());
			
			JSONArray timesheetRows = new JSONArray();
			
			SortedSet<TimesheetRow> sortedSet = new TreeSet<TimesheetRow>(new Comparator<TimesheetRow>() {
				public int compare(TimesheetRow o1, TimesheetRow row) {
					int result = -1;
					int typeCompare = o1.getId().compareTo(row.getId());
					if (typeCompare > 0 ) {
						result =  1;
					} else if (typeCompare < 0 ) {
						result =  -1;
					} else {
						//Same type
						if (o1.getType().getId().equals(Constants.TIMESHEET_ROW_TYPE_ID_PROJECT)) {
							//Compare Project Type
							String ref = o1.getProject().getReference();
							if (ref == null) ref = o1.getProject().getTemporaryReference(); 
							String compare = row.getProject().getProjectReference();
							if (compare == null) compare = row.getProject().getTemporaryReference();
							result =  ref.compareTo(compare);
						} else 	if (o1.getType().getId().equals(Constants.TIMESHEET_ROW_TYPE_ID_ACTION_PLAN)) {
							//Compare AP Type
							return (("AP"+ o1.getActionPlan().getYear() + o1.getActionPlan().getBeneficiaryCountry().getIsoCode()).compareTo("AP"+ row.getActionPlan().getYear() + row.getActionPlan().getBeneficiaryCountry().getIsoCode()));
						}
					}
					return result;
				}
				
			});
			sortedSet.addAll(t.getRows());
			
			for(Iterator<TimesheetRow> itRow = sortedSet.iterator(); itRow.hasNext();) {
				TimesheetRow row = itRow.next(); 
				JSONObject tRow = new JSONObject();
				tRow.put("title", computeTimesheetRowTitle(row));
				tRow.put("total",computeTimesheetRowTotal(row));
				timesheetRows.put(tRow);
				//buf.append("{title:\"" + computeTimesheetRowTitle(row) + "\"");
				//buf.append(",total:" + computeTimesheetRowTotal(row) + "}");
				//if (itRow.hasNext()) buf.append(",");
			}
			//buf.append("]}");
			timesheet.put("rows",timesheetRows);
//			if (itTime.hasNext()) buf.append(",");
//			else {
//				//Last
//				buf.append("]}");
//			}
			timesheetList.put(timesheet);
		}
		//buf.append("]}");
		//Add last node, if exist
		if (treeNode != null) {
			treeNode.put("timesheets", timesheetList);
			treeNodes.put(treeNode);
		}
		//Populate tree
    	validationTree.put("tree", treeNodes);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return validationTree.toString().replaceAll("\"", "\\\\\"");
		*/
    	return null;
    }
    
    private float computeTimesheetTotal(Timesheet t) {
    	float total = 0;
    	for (TimesheetRow row : t.getRows()) {
    		if (row.getTotalOfHours() != null)
    			total += row.getTotalOfHours();
    	}
    	return total;
    }
    
    private double computeTimesheetRowTotal(TimesheetRow row) {
    	double total = 0;
   		if (row.getTotalOfHours() != null)
   			total = row.getTotalOfHours();
    	return total;
    }
    
    private String computeTimesheetRowTitle(TimesheetRow row) {
    	String title = null;
    	/*
    	if (row.getActionPlan() != null) {
    		title = "AP" + row.getActionPlan().getYear() + row.getActionPlan().getBeneficiaryCountry().getIsoCode()
    		+ " (" + row.getActionPlan().getBeneficiaryCountry().getDescription() + ")"; 
    	} else if (row.getProject() != null) {
    		title = row.getProject().getReference() + " : " + row.getProject().getTitle();
    	} else {
    		//Generic type
    		title = row.getType().getParent().getDescription() + " : " + row.getType().getDescription();
    	}
    	*/
    	return title;
    	
    }
    
    //injection

	public ITimesheetService getTimesheetService() {
		return timesheetService;
	}

	public void setTimesheetService(ITimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}
}
