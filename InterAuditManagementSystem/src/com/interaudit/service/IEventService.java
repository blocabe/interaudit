package com.interaudit.service;

import java.util.Date;
import java.util.List;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Event;
import com.interaudit.service.param.SearchEventParam;



/**
 * Service methods handling Mission entities.
 */
/**
 * @author blocabe
 *
 */
public interface IEventService {
    
    
	 /**
     * Returns a event object identified by given id.
     * 
     * @param id
     * @return Returns a event object identified by given id.
     */
	Event getOne(Long id);
	
	
	
	public Event getOneEventDetached(Long id); 
	
	public Event findEvent( Long employeId, int year,int month,int day,int startHour, int endHour);
	
	int getNextAvailableStartHour(Long employeId, int year, int month, int dayOfYear);

    /**
     * Delete a event object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    
  
 
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Event saveOne(Event event); 
    
   
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Event updateOne(Event event);
    
    
    /**
     * 
     * @return Mission objects list matching the criterias
     */
    List<Event> searchEvents(  SearchEventParam param);
    
    public List<Event> getAllEventsForPeriod(Date firstDate, Date secondDate,Long employeeId  );
    
    public int markHoursAsValid(List<Long> resultList);
    public int getCountUnvalidateHoursForMission(Long missionId);
    
    
    
}
