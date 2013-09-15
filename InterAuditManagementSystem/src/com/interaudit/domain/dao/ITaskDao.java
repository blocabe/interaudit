package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.Task;

public interface ITaskDao {
	
	/**
     * 
     * @return Task objects list representing all tasks known by IAMS
     *         application.
     */
	public  List<Task> getAll();
	
	 public List<Task> getAll2();
	 
	 public List<Task> getAll3();

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
	public Task getOne(Long id);
    
    
 
    /**
     * Delete a Task object identified by given id.
     * 
     * @param id
     */
	public void deleteOne(Long id); 
    
 
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
	public Task saveOne(Task task); 

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
	public Task updateOne(Task task);
    
    /**
     * Returns a Task object identified by given taskName.
     * 
     * @param taskName
     * @return Returns a Task object identified by given taskName.
     */
	public  List<Task> getTaskByName(String taskName);
	
	public  Task getTaskByCode(String taskCode);
	
	public  Task getTaskByCodePrestation(String codePrestation);

}
