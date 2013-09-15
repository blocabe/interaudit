package com.interaudit.service;

import com.interaudit.service.param.SearchTimesheetParam;
import com.interaudit.service.view.SearchTimesheetView;



public interface ISearchTimesheetService {
    /**
     * Returns an object encapsulating all information that must be dispalyed on
     * the Jaspers Search Timesheet screen.
     * 
     * @return Object encapsulatin information dispalyed on Search
     *         Timesheetscreen.
     */
    SearchTimesheetView getTimesheetSearchView();

    /**
     * Search for a timesheet.
     * 
     * @param searchTimesheetParam
     * @return
     */
    SearchTimesheetView searchTimesheet(
            SearchTimesheetParam searchTimesheetParam);

}
