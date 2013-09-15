package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Task;
import com.interaudit.domain.model.data.Option;



/**
 * Service methods handling Role entities.
 */
/**
 * @author blocabe
 *
 */
public interface ITaskService {
    /**
     * 
     * @return Task objects list representing all tasks known by IAMS
     *         application.
     */
    List<Task> getAll();
    
    /**
     * @return the list of task as option
     */
    List<Option> getTaskAsOptions();
    
    List<Option> getTaskAsOptions2();
    
    List<Option> getTaskAsOptions3();
    
    

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Task getOne(Long id);
    
    /**
     * @return the default of task
     */
    Task getDefaultTask();
    
    
 
    /**
     * Delete a Task object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    
 
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Task saveOne(Task task); 

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Task updateOne(Task task);
    
    /**
     * Returns a list of Task object identified by given taskName.
     * 
     * @param taskName
     * @return Returns a list of Task object identified by given taskName.
     */
    List<Task> getTaskByName(String taskName);
    
    public  Task getTaskByCodePrestation(String codePrestation);

    
}
