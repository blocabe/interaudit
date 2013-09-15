package com.interaudit.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.service.IContactService;
import com.interaudit.service.IProjectService;
import com.interaudit.service.IRoleService;
import com.interaudit.service.ISearchService;
import com.interaudit.service.IUserService;
import com.interaudit.service.param.SearchContactParam;
import com.interaudit.service.param.SearchParam;
import com.interaudit.service.view.SearchView;

@Transactional
public class SearchService implements ISearchService {
   
    private IUserService jaspersUserService;    
    private IRoleService roleService;
    private IProjectService projectService;    
    private IContactService contactService;

    // private Log log = LogFactory.getLog(ISearchService.class);

    public SearchView getProjectSearchView() {
        SearchView view = new SearchView();
//        view.setYears(projectService.getAllDistinctProjectYears());
        /*
        view.setYears(actionPlanService.getAllDistinctActionPlanYears());
        view.setCountries(projectService.getAllDistinctProjectCountries());
        view.setSectors(projectService.getAllDistinctProjectSectors());
        view.setSubsectors(projectService.getAllDistinctProjectSubSectors());
        view.setStatuses(projectService.getAllDistinctProjectStatuses());
        view.setRegionalManagers(jaspersUserService
                .getAllByRoleName(Constants.ROLE_NAME_OPERATIONAL_MANAGER));
        view.setSectorManagers(jaspersUserService
                .getAllByRoleName(Constants.ROLE_NAME_SECTOR_MANAGER));
        view.setTaskManagers(jaspersUserService
                .getAllByRoleName(Constants.ROLE_NAME_TASK_MANAGER));
        view.setOffices(officeService.getAll());
        view.setProjectStatus(projectStatusService
                .getOneByCode(Constants.PROJECT_STATUS_CODE_REJECTED));
                */
        return view;
    }

    public SearchView getActionPlanSearchView() {
        SearchView view = new SearchView();
        /*
        view.setYears(actionPlanService.getAllDistinctActionPlanYears());
        view
                .setCountries(actionPlanService
                        .getAllDistinctActionPlanCountries());
                        */

        return view;
    }


    public SearchView getContactSearchView() {
        SearchView view = new SearchView();
        /*
        view.setCountries(contactService.getAllDistinctContactCountries());
        view.setContactTypes(contactService.getAllDistinctContactTypes());
        */
        return view;
    }
    public SearchView searchProjects(SearchParam searchParam) {
        SearchView view = getProjectSearchView();
        view.setProjects(projectService.findProjects(searchParam));
        return view;
    }
    public SearchView searchActionPlans(SearchParam searchParam) {
        SearchView view = getActionPlanSearchView();
        //view.setActionPlans(actionPlanService.findActionPlans(searchParam));
        return view;
    }

    public SearchView searchContact(SearchContactParam searchContactParam) {
    	/*
        SearchView view = getContactSearchView();
        view.setContacts(contactService.findContacts(searchContactParam));
        return view;
        */
    	return null;
    }
    
    public IUserService getJaspersUserService() {
        return jaspersUserService;
    }

    public void setJaspersUserService(IUserService jaspersUserService) {
        this.jaspersUserService = jaspersUserService;
    }

    

    public IRoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    public IProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(IProjectService projectService) {
        this.projectService = projectService;
    }

    

    public IContactService getContactService() {
        return contactService;
    }

    public void setContactService(IContactService contactService) {
        this.contactService = contactService;
    }

}
