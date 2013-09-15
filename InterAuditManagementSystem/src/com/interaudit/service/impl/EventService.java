package com.interaudit.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IEventDao;
import com.interaudit.domain.model.Event;
import com.interaudit.service.IEventService;
import com.interaudit.service.param.SearchEventParam;

@Transactional
public class EventService implements IEventService {
	//private Log log = LogFactory.getLog(TaskService.class);
    private IEventDao eventDao;
    
    /**
     * Returns a event object identified by given id.
     * 
     * @param id
     * @return Returns a event object identified by given id.
     */
    public Event getOne(Long id){
    	return eventDao.getOne(id);
    }
	
	
	
	public Event getOneEventDetached(Long id){
    	return eventDao.getOneEventDetached(id);
    } 
	
	
	public Event findEvent( Long employeId, int year,int month,int day,int startHour, int endHour){
		return eventDao.findEvent(  employeId,  year, month, day, startHour,  endHour);
	}
	
	public int getNextAvailableStartHour(Long employeId, int year, int month, int dayOfYear){
		return eventDao.getNextAvailableStartHour( employeId,  year,  month,  dayOfYear);
	}

    /**
     * Delete a event object identified by given id.
     * 
     * @param id
     */
    public void deleteOne(Long id){
    	 eventDao.deleteOne(id);
    } 
    
  
 
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    public Event saveOne(Event event){
    	return eventDao.saveOne(event);
    }  
    
   
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    public Event updateOne(Event event){
    	return eventDao.updateOne(event);
    } 
    
    public List<Event> getAllEventsForPeriod(Date firstDate, Date secondDate,Long employeeId  ){
    	return eventDao.getAllEventsForPeriod(   firstDate,  secondDate, employeeId  );
    }
    
    
    /**
     * 
     * @return Mission objects list matching the criterias
     */
    public List<Event> searchEvents(  SearchEventParam param){
    	return eventDao.searchEvents(param);
    }

	public IEventDao getEventDao() {
		return eventDao;
	}

	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}
    
	
    
	
	 public int markHoursAsValid(List<Long> resultList){
		  return eventDao.markHoursAsValid(resultList);
	 }
	 
	 public int getCountUnvalidateHoursForMission(Long missionId){
		 return eventDao.getCountUnvalidateHoursForMission(missionId);
	 }


	
}
