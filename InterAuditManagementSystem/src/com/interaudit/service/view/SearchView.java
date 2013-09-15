package com.interaudit.service.view;

import java.util.List;

import com.interaudit.domain.model.Contact;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Mission;



/**
 * Class to encapsulate all the values that must be displayed on the Jasper
 * Search screen.
 * 
 * @author Valentin Carnu
 * 
 */
public class SearchView {
    
    private List<String> years;
    private List<String> statuses;
    private List<Employee> regionalManagers;
    private List<Employee> sectorManagers;
    private List<Employee> taskManagers;
    private List<Mission> projects;

    private String projectStatus;
    private List<Contact> contacts;
  

   

    public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    public List<Employee> getRegionalManagers() {
        return regionalManagers;
    }

    public void setRegionalManagers(List<Employee> regionalManagers) {
        this.regionalManagers = regionalManagers;
    }

    public List<Employee> getSectorManagers() {
        return sectorManagers;
    }

    public void setSectorManagers(List<Employee> sectorManagers) {
        this.sectorManagers = sectorManagers;
    }

    public List<Employee> getTaskManagers() {
        return taskManagers;
    }

    public void setTaskManagers(List<Employee> taskManagers) {
        this.taskManagers = taskManagers;
    }

    public List<Mission> getProjects() {
        return projects;
    }

    public void setProjects(List<Mission> projects) {
        this.projects = projects;
    }

   

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

   
}
