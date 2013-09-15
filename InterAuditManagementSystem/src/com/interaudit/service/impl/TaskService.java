package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.ITaskDao;
import com.interaudit.domain.model.Task;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.ITaskService;

@Transactional
public class TaskService implements ITaskService {

	private static final Logger  logger      = Logger.getLogger(TaskService.class);
    private ITaskDao taskDao;
    
    /**
     * 
     * @return Task objects list representing all tasks known by IAMS
     *         application.
     */
    public List<Task> getAll(){
    	return taskDao.getAll();
    }
    
    /**
     * @return the list of task as option
     */
    public List<Option> getTaskAsOptions(){
    	List<Option> options= new ArrayList<Option>();
    	List<Task> allTasks = this.getAll();
    	for(Task task : allTasks){
    		 options.add(new Option(task.getId().toString(),task.getDescription()));
    	}
    	return options;
    }
    
    
    public List<Option> getTaskAsOptions2(){
    	List<Option> options= new ArrayList<Option>();
    	List<Task> allTasks = taskDao.getAll2();
    	for(Task task : allTasks){    		 
    		 options.add(new Option(task.getCodePrestation(),task.getDescription()));
    		 
    	}
    	return options;
    }
    
    public List<Option> getTaskAsOptions3(){
    	
    	List<Option> options= new ArrayList<Option>();
    	List<Task> allTasks = taskDao.getAll3();
    	for(Task task : allTasks){    		 
    		 options.add(new Option(task.getId().toString(),task.getDescription()));
    		 
    	}
    	return options;
    }

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    public Task getOne(Long id){
    	return taskDao.getOne(id);
    }
    
    
    /**
     * @return the default of task
     */
   public Task getDefaultTask(){
	   return taskDao.getTaskByCode("TODO");	   
   }
    
    
 
    /**
     * Delete a Task object identified by given id.
     * 
     * @param id
     */
    public void deleteOne(Long id){
    	taskDao.deleteOne(id);
    }
    
 
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
   public Task saveOne(Task task){
	   return taskDao.saveOne(task);
   }

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
   public Task updateOne(Task task){
    	return taskDao.updateOne(task);
    }
    
    /**
     * Returns a list of Task object identified by given taskName.
     * 
     * @param taskName
     * @return Returns a list of Task object identified by given taskName.
     */
   public List<Task> getTaskByName(String taskName){
    	return taskDao.getTaskByName(taskName);
    }

	public ITaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(ITaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	
	public  Task getTaskByCodePrestation(String codePrestation){
		return taskDao.getTaskByCodePrestation(codePrestation);
	}
    
    
	
}
