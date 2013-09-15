package com.interaudit.domain.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.interaudit.domain.dao.ITimesheetCellDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.TimesheetCell;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;

public class TimesheetCellDao implements ITimesheetCellDao {
	Log log = LogFactory.getLog(TimesheetCellDao.class);
	
	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(TimesheetCellDao.class);
	
	
	public void deleteOne(Long id) {
				
		try{			
			TimesheetCell timesheetCell = (TimesheetCell)em.find(TimesheetCell.class, id);
			if (timesheetCell == null) {
				throw new DaoException(2);
			}
			em.remove(timesheetCell);	       
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetCellDao :  deleteOne..."));
		}finally{
			em.close();
		}

		
	}

	// generic
	@SuppressWarnings("unchecked")
	
	public List<TimesheetCell> getAll() {
		
		try{
			return em.createQuery("select t from TimesheetCell t").getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetCellDao :  getAll..."));

		}finally{
			em.close();
		}
		
	}


	
	public TimesheetCell getOne(Long id) {
		
		try{
			return (TimesheetCell) em.find(TimesheetCell.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetCellDao :  getOne..."));

		}finally{
			em.close();
		}
		
	}

	
	public TimesheetCell saveOne(TimesheetCell timesheetCell) {		
		try{			
			em.persist(timesheetCell);	     
	        return timesheetCell;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetCellDao :  saveOne..."));
	        
		}finally{
			em.close();
		}

		
		
	}

	
	public TimesheetCell updateOne(TimesheetCell timesheetCell) {		
		try{		
			TimesheetCell ret = (TimesheetCell) em.merge(timesheetCell);	      
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetCellDao :  updateOne..."));
	        
		}finally{
			em.close();
		}

		
	}
}
