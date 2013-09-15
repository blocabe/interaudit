package com.interaudit.service.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.interaudit.domain.model.Timesheet;

public class TimesheetValidationSummaryView {

	private List<String> validatedTimesheetSummary;
	private List<String> rejectedTimesheetSummary;
	
	public TimesheetValidationSummaryView() {
		validatedTimesheetSummary = new ArrayList<String>();
		rejectedTimesheetSummary = new ArrayList<String>();
	}

	public void addToValidatedTimesheetSummary(Timesheet t) {
		validatedTimesheetSummary.add(computeSummary(t));
	}
	
	public void addToRejectedTimesheetSummary(Timesheet t) {
		rejectedTimesheetSummary.add(computeSummary(t));
	}
	
	private String computeSummary(Timesheet t) {
		SimpleDateFormat sdf = new SimpleDateFormat( "MMMMMMM yyyy",Locale.ENGLISH );
		return t.getUser().getFullName() + " for " + sdf.format(t.getDate());
	}
	
	public List<String> getValidatedTimesheetSummary() {
		return validatedTimesheetSummary;
	}
	
	public List<String> getRejectedTimesheetSummary() {
		return rejectedTimesheetSummary;
	}
}
