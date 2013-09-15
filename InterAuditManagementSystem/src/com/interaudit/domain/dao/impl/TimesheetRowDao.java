package com.interaudit.domain.dao.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.interaudit.domain.dao.ITimesheetRowDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.TimesheetCell;
import com.interaudit.domain.model.TimesheetRow;
import com.interaudit.domain.model.data.TimesheetData;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;

public class TimesheetRowDao implements ITimesheetRowDao {
	//Log log = LogFactory.getLog(TimesheetRowDao.class);
	private static final Logger  logger      = Logger.getLogger(TimesheetRowDao.class);
	
	@PersistenceContext
	private EntityManager em;

	
	public void deleteOne(Long id) {
				
		try{			
			TimesheetRow timesheetRow = (TimesheetRow) em.find(TimesheetRow.class, id);
			if (timesheetRow == null) {
				throw new DaoException(2);
			}
			em.remove(timesheetRow);	        
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : deleteOne ..."));
	        
		}finally{
			em.close();
		}	
	}
	
	public TimesheetRow copyRowFromTimesheet(Long id) {		
		try{			
			TimesheetRow timesheetRow = (TimesheetRow) em.find(TimesheetRow.class, id);
			if (timesheetRow == null) {
				throw new DaoException(2);
			}
			
			Timesheet timesheet = timesheetRow.getTimesheet();
			Activity activity = timesheetRow.getActivity();			
			String label= timesheetRow.getLabel();
			
			TimesheetRow timesheetRowCopy = new TimesheetRow( timesheet, activity,null, label,timesheetRow.getCodePrestation(),timesheetRow.getYear());
			if(activity == null){
				timesheetRowCopy.setCustNumber("99900");				
				timesheetRowCopy.setTaskIdentifier(timesheetRow.getTaskIdentifier());
			}
			
			Set<TimesheetCell> cells = timesheetRow.getCells();
			for(TimesheetCell cell : cells){
				timesheetRowCopy.addCell(new TimesheetCell(cell.getDayNumber(), cell.getValue()));
			}
			
			
			em.persist(timesheetRow);			
	        return timesheetRowCopy;
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : copyRowFromTimesheet ..."));
	        
		}finally{
			em.close();
		}	
	}

	// generic
	@SuppressWarnings("unchecked")
	
	public List<TimesheetRow> getAll() {
		try{
			return em.createQuery("select t from TimesheetRow t").getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : getAll ..."));
		}finally{
			em.close();
		}
		
	}

	
	
	public TimesheetRow getOne(Long id) {		
		try{			
			TimesheetRow timesheetRow = (TimesheetRow) em.find(TimesheetRow.class, id);	        
	        return timesheetRow;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : getOne ..."));
	        
		}finally{
			em.close();
		}

		
	}

	
	public TimesheetRow saveOne(TimesheetRow timesheetRow) {		
		try{			
			 em.persist(timesheetRow);			
	        return timesheetRow;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : saveOne ..."));
	        
		}finally{
			em.close();
		}

		
		
	}

	
	public TimesheetRow updateOne(TimesheetRow timesheetRow) {
		
		try{			
			TimesheetRow ret = (TimesheetRow) em.merge(timesheetRow);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : updateOne ..."));
	        
		}finally{
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<TimesheetRow> findTimesheetRowsForUserId(Long userId) {

		try{
			return  em
			.createQuery(
					"SELECT DISTINCT NEW com.interaudit.domain.model.TimesheetRow(t.id, t.date, t.status, t.user) FROM TimesheetRow t WHERE t.user.id=:userId")
			.setParameter("userId", userId)
			.getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : findTimesheetRowsForUserId ..."));
	        
		}finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	
	public List<TimesheetRow> getAllRowsForOneTimesheetId(Long id) {
		
		try{
			return  em.createQuery("select t from TimesheetRow t WHERE t.timesheet.id=:timesheetId")
			.setParameter("timesheetId", id)
			.getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : getAllRowsForOneTimesheetId ..."));
		}finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")	
	public List<TimesheetRow> getAllRowsToPayForOneProject(Long projectId){
		try{

			return  em.createQuery("select t.timesheet from TimesheetRow t WHERE t.project.id=:projectId and t.facture.id = null")
			.setParameter("projectId", projectId)
			.getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : getAllRowsToPayForOneProject ..."));
	        
		}finally{
			em.close();
		}
		
		
	}
	/*
	@SuppressWarnings("unchecked")
	public List<TimesheetRow> getAllAPRowsForOneTimesheetId(Long id) {
		return  em.createQuery("select t from TimesheetRow t WHERE t.timesheet.id=:timesheetId AND t.type.id=:apTypeId")
		.setParameter("timesheetId", id).setParameter("apTypeId", Constants.TIMESHEET_ROW_TYPE_ID_ACTION_PLAN).list();
	}
	@SuppressWarnings("unchecked")
	public List<TimesheetRow> getAllGeneralRowsForOneTimesheetId(Long id) {
		return  em.createQuery("select t from TimesheetRow t WHERE t.timesheet.id=:timesheetId AND t.type.id!=:apTypeId AND t.type.id!=:projectTypeId" )
		.setParameter("timesheetId", id).setParameter("apTypeId", Constants.TIMESHEET_ROW_TYPE_ID_ACTION_PLAN)
		.setParameter("projectTypeId", Constants.TIMESHEET_ROW_TYPE_ID_PROJECT).list();
	}
	*/
	
	@SuppressWarnings("unchecked")
	
	public TimesheetRow getOneActivityRowForTimesheetId(Long timesheetId, Long activityId) {
		try{

			List<TimesheetRow> result =  em.createQuery("select t from TimesheetRow t WHERE t.timesheet.id=:timesheetId AND t.activity.id=:activityId")
			.setParameter("timesheetId", timesheetId)
			.setParameter("activityId", activityId)
			.getResultList();		
			return result.size() > 0 ? result.iterator().next() : null;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : getOneActivityRowForTimesheetId ..."));
	        
		}finally{
			em.close();
		}
		
		
	}
	
	
	@SuppressWarnings("unchecked")	
	public List<TimesheetData> getAllTimesheetToValidateForProject(Long projectId){
		try{			
			return  em.createQuery("select DISTINCT new com.interaudit.domain.model.data.TimesheetData(t.timesheet.id,t.timesheet.user.lastName,t.timesheet.user.id, t.timesheet.exercise,t.timesheet.status,t.timesheet.weekNumber,t.timesheet.validationDate,t.timesheet.startDateOfWeek,t.timesheet.endDateOfWeek,t.timesheet.lastRejectedDate)  from TimesheetRow t WHERE t.activity.mission.id=:projectId and t.timesheet.status != 'VALIDATED'  ORDER BY t.timesheet.weekNumber")
			.setParameter("projectId", projectId)
			.getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : getAllTimesheetToValidateForProject ..."));
	        
		}finally{
			em.close();
		}
	}
	
	
	@Override
	@SuppressWarnings("unchecked")	
	public int getCountTimesheetToValidateForProject(Long projectId){
		 
		try{			
			Number result = (Number) em.createQuery("select count(t)  from TimesheetRow t WHERE t.activity.mission.id=:projectId and t.timesheet.status = 'SUBMITTED'")
			.setParameter("projectId", projectId)
			.getSingleResult();
			
			 if(result == null ){
				 return 0;
			 }
			 else{
				return  result.intValue();
			 }
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetRowDao : getAllTimesheetToValidateForProject ..."));
	        
		}finally{
			em.close();
		}
	}
	
}
