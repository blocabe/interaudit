package com.interaudit.domain.dao.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.interaudit.domain.dao.ITimesheetDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.data.TimesheetData;
import com.interaudit.domain.model.data.TimesheetOption;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchTimesheetParam;
import com.interaudit.util.Constants;

public class TimesheetDao  implements ITimesheetDao {
	Log log = LogFactory.getLog(TimesheetDao.class);
	
	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(TimesheetDao.class);
	
	
	public void deleteOne(Long id) {
		
		try{			
			Timesheet timesheet = (Timesheet)em.find(Timesheet.class, id);
			if (timesheet == null) {
				throw new DaoException(2);
			}
			em.remove(timesheet);	       
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao :  deleteOne..."));
		}finally{
			em.close();
		}

		
	}

	// generic
	@SuppressWarnings("unchecked")
	
	public List<Timesheet> getAll() {
		
		try{
			return em.createQuery("select t from Timesheet t").getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao :  getAll..."));
	        
		}finally{
			em.close();
		}
		
		
	}


	
	public Timesheet getOne(Long id) {
		
		try{		
			Timesheet t = (Timesheet)em.find(Timesheet.class, id);	        
	        return t;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao :  getOne..."));
		}finally{
			em.close();
		}

		
		
	}

	
	public Timesheet saveOne(Timesheet timesheet) {
		
		try{			
			em.persist(timesheet);			
	        return timesheet;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao :  saveOne..."));
	        
		}finally{
			em.close();
		}

		
		
	}

	
	public Timesheet updateOne(Timesheet timesheet) {
		
		try{			
			Timesheet t = (Timesheet)em.merge(timesheet);	        
	        return t;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao :  updateOne..."));
	        
		}finally{
			em.close();
		}

		
		
	}

	@SuppressWarnings("unchecked")
	
	public List<Timesheet> findTimesheetsForUserId(Long userId) {
		
		try{

			return em
			.createQuery(
					"SELECT DISTINCT NEW com.interaudit.domain.model.Timesheet(t.id, t.date, t.status, t.user) FROM Timesheet t WHERE t.user.id=:userId order by t.weekNumber asc")
			.setParameter("userId", userId).getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao :  findTimesheetsForUserId..."));
	        
		}finally{
			em.close();
		}

		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	
	public Timesheet findOneTimesheetsForUserId(Long userId,int weekNumber,int year) {
		/*
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, year);		
		c.set(Calendar.WEEK_OF_YEAR,weekNumber);
		c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
		Date firstDate = c.getTime();
		
		System.out.println("firstDate : " + firstDate);
		
		//c.set(Calendar.DATE,c.getActualMaximum(Calendar.DATE)+1);
		c.set(Calendar.WEEK_OF_YEAR,weekNumber);
		c.set(Calendar.DAY_OF_WEEK , Calendar.FRIDAY);
		c.set(Calendar.YEAR,year);
		Date secondDate = c.getTime();
		System.out.println("secondDate : " + secondDate);
		List<Timesheet> result = em.createQuery(
				"SELECT t FROM Timesheet t WHERE t.user.id=:userId AND t.date BETWEEN :d1 AND :d2")
		.setParameter("userId", userId).setParameter("d1", firstDate).setParameter("d2", secondDate).list();
		*/
		
		
		try{

			List<Timesheet> result = em.createQuery(
			"SELECT t FROM Timesheet t WHERE t.user.id=:userId AND t.weekNumber= :weekNumber AND UPPER(t.exercise) = UPPER(:exercise)")
			.setParameter("userId", userId).setParameter("weekNumber", weekNumber).setParameter("exercise", Integer.toString(year)).getResultList();
			
			return result.size() > 0 ? result.iterator().next() : null;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao :   findOneTimesheetsForUserId(Long userId,int weekNumber,int year) ..."));
	        
		}finally{
			em.close();
		}
		
	}
	
	public List<TimesheetData>  findTimesheetsForUserId(Long userId,int year) {
		/*
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, year);		
		c.set(Calendar.WEEK_OF_YEAR,weekNumber);
		c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
		Date firstDate = c.getTime();
		
		System.out.println("firstDate : " + firstDate);
		
		//c.set(Calendar.DATE,c.getActualMaximum(Calendar.DATE)+1);
		c.set(Calendar.WEEK_OF_YEAR,weekNumber);
		c.set(Calendar.DAY_OF_WEEK , Calendar.FRIDAY);
		c.set(Calendar.YEAR,year);
		Date secondDate = c.getTime();
		System.out.println("secondDate : " + secondDate);
		List<Timesheet> result = em.createQuery(
				"SELECT t FROM Timesheet t WHERE t.user.id=:userId AND t.date BETWEEN :d1 AND :d2")
		.setParameter("userId", userId).setParameter("d1", firstDate).setParameter("d2", secondDate).list();
		*/
		
		
		try{

			List<TimesheetData> result = em.createQuery(
			"select new com.interaudit.domain.model.data.TimesheetData(t.id,u.lastName,u.id, t.exercise,t.status,t.weekNumber,t.validationDate,t.startDateOfWeek,t.endDateOfWeek,t.lastRejectedDate)  from Timesheet t join t.user u WHERE t.user.id=:userId AND UPPER(t.exercise) = UPPER(:exercise) order by t.weekNumber asc")
			.setParameter("userId", userId).setParameter("exercise", Integer.toString(year)).getResultList();
			
			return result;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao :   findTimesheetsForUserId(Long userId,int year) ..."));
	        
		}finally{
			em.close();
		}
		
	}
	
	@Override
	public List<TimesheetOption>  findTimesheetOptionsForUserId(Long userId,int year) {
		
		
		try{

			List<TimesheetOption> result = em.createQuery(
			"select new com.interaudit.domain.model.data.TimesheetOption(t.status,t.weekNumber,t.lastRejectedDate)  from Timesheet t join t.user u WHERE t.user.id=:userId AND UPPER(t.exercise) = UPPER(:exercise) order by t.weekNumber asc")
			.setParameter("userId", userId).setParameter("exercise", Integer.toString(year)).getResultList();
			
			return result;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao :   findTimesheetOptionsForUserId(Long userId,int year) ..."));
	        
		}finally{
			em.close();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	
	public List<String> getAllDistinctTimesheetStatuses() {		
		
		try{

			return (List<String>)em.createQuery("SELECT DISTINCT ts FROM Timesheet t JOIN t.status ts ORDER BY ts.description").getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao : getAllDistinctTimesheetStatuses ..."));
		}finally{
			em.close();
		}		
	}
	
	
	
	public List<Timesheet> findTimesheets(SearchTimesheetParam searchTimesheetParam) {
		
		try{
			
			SimpleDateFormat sdf = new SimpleDateFormat( "MMMMMMM yyyy",Locale.ENGLISH );
			List<Timesheet> globalResult = new ArrayList<Timesheet>();
			
			try {
				Date firstDate = null;//sdf.parse(searchTimesheetParam.getStartPeriod());
				Date endDate = null;//sdf.parse(searchTimesheetParam.getEndPeriod());
				Calendar c = Calendar.getInstance();
				c.setTime(firstDate);
				int startMonth = c.get(Calendar.MONTH) + 1;
				int startYear = c.get(Calendar.YEAR);
				c.setTime(endDate);
				int endMonth = c.get(Calendar.MONTH) + 1;
				int endYear = c.get(Calendar.YEAR);
				
				while(!(startYear == endYear && startMonth == endMonth)) {
					//Add timesheet for current period
					globalResult.addAll(tempGetSubmittedTimesheetsForOnePeriod(startMonth,startYear));
					//Increase period
					if (startMonth == 12) {
						startYear++;
						startMonth = 1;
					} else {
						startMonth++;
					}
				}
				//Finally add last period
				globalResult.addAll(tempGetSubmittedTimesheetsForOnePeriod(startMonth,startYear));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return globalResult;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao : findTimesheets ..."));
		}finally{
			em.close();
		}
		
		
	}
	@SuppressWarnings("unchecked")
	private List<Timesheet> tempGetSubmittedTimesheetsForOnePeriod(int month,int year) {
		
		try{
			
			Calendar c = Calendar.getInstance();
			c.clear();
			c.set(year,month-1,1);
			Date firstDate = c.getTime();
			c.set(Calendar.DATE,c.getActualMaximum(Calendar.DATE)+1);
			Date secondDate = c.getTime();
			List<Timesheet> result = em.createQuery(
					"SELECT t FROM Timesheet t WHERE t.date BETWEEN :d1 AND :d2 AND t.status=:status")
			.setParameter("d1", firstDate)
			.setParameter("d2", secondDate)
			.setParameter("status", Constants.TIMESHEET_STATUS_STRING_SUBMITTED)
			.getResultList();
			return result;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao : tempGetSubmittedTimesheetsForOnePeriod ..."));
	        
		}finally{
			em.close();
		}
	}
	
	
	

	
	
	public List<TimesheetData> searchTimesheets(SearchTimesheetParam param) {
		try{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			
		    StringBuilder whereClause = new StringBuilder("");
		        
		     StringBuilder hql  = new StringBuilder("select new com.interaudit.domain.model.data.TimesheetData(t.id,u.lastName,u.id, t.exercise,t.status,t.weekNumber,t.validationDate,t.startDateOfWeek,t.endDateOfWeek,t.lastRejectedDate)  from Timesheet t join t.user u ");
	        //Rechercher les années
	        if (param.getYear()!= null) {
	            parameters.put("year", param.getYear());
	            whereClause.append("( upper(t.exercise) = upper(:year))");
	        }
	        
	        //Rechercher le status
	        if ((param.getListOfStatus() != null) && (param.getListOfStatus().isEmpty() == false)) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("status", param.getListOfStatus());
	            whereClause.append("( t.status in (:status)) ");
	        }
	        
	      //Rechercher l'employée
	        if (param.getEmployeeId() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("employeeId", param.getEmployeeId());
	           
	            
	            
	            whereClause.append("(u.id = :employeeId ) ");
	        }
	        
	      //Rechercher le week
	        if (param.getWeek() > 0) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("week",  param.getWeek() );
	            whereClause.append("(t.weekNumber = :week)");
	        }
	        
	        if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
	        parameters.put("userActive", Boolean.TRUE);
	        whereClause.append(" ( u.userActive = :userActive) ");
	        
	        if (whereClause.length() > 0) {	        		        	
	        		hql.append(" WHERE ").append(whereClause);				 
	        }
	        hql.append(" ORDER BY t.weekNumber, u.lastName asc ");
	        
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	    
	        return q.getResultList();

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TimesheetDao : searchTimesheets ..."));

		}finally{
			em.close();
		}
	}
	
	
	public void generateRawDataCsvFile(Integer exerciseNumber,String status,OutputStream os){
		
		try{
			
			if(null == exerciseNumber){
				throw new BusinessException(new ExceptionMessage("No ongoing exercise found...") ) ;
			}
			
			if(status == null){
				status="VALIDATED";
			}			
			
			StringBuffer searchQuery = new StringBuffer("select ts.exercise as TS_EXERCISE,ts.weeknumber as TS_WEEKNUM,tc.daynumber as TS_DAYNUM,(select code from interaudit.employees e where e.id=ts.userid) as C_CODEEMP,tr.label as NOMCLIENT,tr.codeprestation as C_PRESTAT,tr.taskidentifier as C_PRESTAT2,tr.custnumber as C_NOCLI,tr.year as C_ANNEE,tc.value as C_HRS from interaudit.timesheets ts, interaudit.timesheet_rows tr,interaudit.timesheet_cells tc ");
			//searchQuery.append("where tr.fk_timesheet = ts.id and tc.fk_row = tr.id and ts.exercise='" + exerciseNumber +"' and  ts.status='" + status +"'");
			searchQuery.append("where tr.fk_timesheet = ts.id and tc.fk_row = tr.id and ts.exercise='" + exerciseNumber +"'");
			
			Query q = em.createNativeQuery(searchQuery.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = (List<Object[]>) q.getResultList();		
			Calendar c = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy", new Locale("fr","FR"));
			String [] data = new String [10];
			
			//1.Writing the headers
			data[0]="C_NUM";
			data[1]="C_DATE";
			data[2]="C_HRS";
			data[3]="C_MIN";
			data[4]="C_PRESTAT";
			data[5]="C_NATURE";
			data[6]="C_ANNEE";
			data[7]="C_MOIS";
			data[8]="C_NOCLI";
			data[9]="NomClient";
			boolean ret = writeDataToStream(os,data);
			assert(ret);
			
			//1.Writing the data
			for(int i = 0;i<resultList.size();i++){
				Object[] resultat = resultList.get(i);
				
				String  exerciseAstring = (String)resultat[0];
				Integer weeknumber = (Integer)resultat[1];
				Integer daynumber = (Integer)resultat[2];
				String  codeEmployee = (String)resultat[3];
				String  nomClient = (String)resultat[4];
				String  codePrestation = (String)resultat[5];
				String  codePrestation2 = (String)resultat[6];
				String  numClient = (String)resultat[7];
				String  mandat = (String)resultat[8];
				Double heures = (Double)resultat[9];
				
				Integer exercise = Integer.parseInt(exerciseAstring);
				
				c.set(Calendar.YEAR,exercise);
				c.set(Calendar.WEEK_OF_YEAR,weeknumber);
				c.set(Calendar.DAY_OF_WEEK,daynumber+1);
				
				Date date = c.getTime();
				String dayDate = dateFormat.format(date);
				
				data[0]=codeEmployee;
				data[1]=dayDate;
				data[2]= heures==null?"0.0":heures.toString();
				data[3]="";
				data[4]=codePrestation;
				data[5]="";
				data[6]=mandat;
				data[7]="";
				data[8]=numClient;
				data[9]=nomClient;
				
				ret = writeDataToStream(os,data);
				assert(ret);
				
			}
			
			}catch(DaoException daoe){	
			  logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed in generateRawDataCsvFile...") ) ;		 
		  }catch(Exception e){	 
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed in generateRawDataCsvFile...") ) ;		 
		  }
		  finally{
				em.close();
		  } 
		
	}
	
	public boolean writeDataToStream(OutputStream os,String [] data)
	{
		try
		{
			StringBuffer buffer = new StringBuffer();
			for (String info : data)
			{
				if (info == null)
				{
					info = "";
				}
				buffer.append(info);
				buffer.append(";");
			}
			buffer.deleteCharAt(buffer.length()-1);
			buffer.append("\n");
			os.write(buffer.toString().getBytes());
			os.flush();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
