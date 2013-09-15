package com.interaudit.service;

import com.interaudit.service.param.SearchContactParam;
import com.interaudit.service.param.SearchParam;
import com.interaudit.service.view.SearchView;

/**
 * Service methods for searching.
 * 
 * @author Valentin Carnu
 * 
 */
public interface ISearchService {
    /**
     * Returns an object encapsulating all information that must be displayed on
     * the Search Project screen.
     * 
     * @return Object encapsulating information displayed on Search Project
     *         screen.
     */
    SearchView getProjectSearchView();

    /**
     * Returns an object encapsulating all information that must be displayed on
     * the Search Action Plan screen.
     * 
     * @return Object encapsulating information displayed on Search Action Plan
     *         screen.
     */
    SearchView getActionPlanSearchView();

    /**
     * Returns an object encapsulating all information that must be displayed on
     * the Search Contact screen.
     * 
     * @return Object encapsulating information displayed on Search Contact
     *         screen.
     */
    SearchView getContactSearchView();

    /**
     * 
     * @param searchParam
     * @return
     */
    SearchView searchProjects(SearchParam searchParam);

    /**
     * 
     * @param searchParam
     * @return
     */
    SearchView searchActionPlans(SearchParam searchParam);

    /**
     * 
     * @param searchContactParam
     * @return
     */
    SearchView searchContact(SearchContactParam searchContactParam);
}
