package com.interaudit.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IAnnualPlanningDao;
import com.interaudit.domain.dao.IProjectDao;
import com.interaudit.domain.dao.IWeekAssignmentPerEmployeDao;
import com.interaudit.domain.dao.IWeekPlanningDao;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.data.AnnualPlanning;
import com.interaudit.domain.model.data.WeekAssignmentPerEmployee;
import com.interaudit.domain.model.data.WeekPlanning;
import com.interaudit.service.IPlanningService;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.util.BusinessMessagesConstants;
@Transactional
public class PlanningService implements IPlanningService{
	
	private IAnnualPlanningDao annualPlanningDao;
	private IWeekPlanningDao weekPlanningDao;
	private IWeekAssignmentPerEmployeDao weekAssignmentPerEmployeDao;
	private IProjectDao projectDao;
	private static final Logger  logger  = Logger.getLogger(PlanningService.class);
	
	/**
	 * @param responsable
	 * @param exercise
	 * @return
	 */
	public AnnualPlanning createAnnualPlanning( Employee responsable,String exercise){
		
		AnnualPlanning annualPlanning = new AnnualPlanning( exercise, responsable);
		return this.getAnnualPlanningDao().saveOne(annualPlanning);
		
	}
	
	/**
	 * @param exercise
	 * @param weekNumber
	 * @return 
	 */
	public WeekPlanning getWeekPlanning(String exercise, int weekNumber){
		
		WeekPlanning weekPlanning  = this.getWeekPlanningDao().getOneFromWeekNumber(exercise,weekNumber);
		
		if( weekPlanning == null){
			
			AnnualPlanning planning = this.getAnnualPlanningDao().getOneFromExercise(exercise);
			int year = Integer.parseInt(exercise);
			Calendar c = Calendar.getInstance();			
			c.set(Calendar.YEAR,year);
			c.set(Calendar.WEEK_OF_YEAR,weekNumber);
			c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
			Date startDateOfWeek = c.getTime();
			
			c.set(Calendar.DAY_OF_WEEK , Calendar.FRIDAY);
			c.set(Calendar.YEAR,year);
			
			Date endDateOfWeek = c.getTime();
			
			weekPlanning  = new WeekPlanning( planning,					 
					  startDateOfWeek,
					  endDateOfWeek,
					  weekNumber);
			
			weekPlanning = this.getWeekPlanningDao().saveOne(weekPlanning);
			
			
		}
		
		return weekPlanning;
		
	}
	
	
	
	/**
	 * @param startDate
	 * @param endDate
	 * @param weekNumber
	 * @param exercise
	 * @param mission
	 * @param teamMember
	 * @param code
	 * @param description
	 * @return
	 * @throws BusinessException
	 */
	public WeekAssignmentPerEmployee createAssignmentForMember( Date startDate,
			int weekNumber,String exercise, long missionIdentifier, long teamMemberIdentifier,
			String code,String description) throws BusinessException{
		
		Mission mission = this.getProjectDao().getOne(missionIdentifier);
		if(mission == null){
			 throw new BusinessException(BusinessMessagesConstants.ERROR_MISSION_NOT_FOUND);
		}
		
		
		
		WeekPlanning weekPlanning  = this.getWeekPlanningDao().getOneFromWeekNumber(exercise,weekNumber);		
		if(weekPlanning == null){
			 throw new BusinessException(BusinessMessagesConstants.ERROR_WEEKPLANNING_NOT_FOUND);
		}
		
		
		Calendar c1 = Calendar.getInstance();			
		Calendar c2 = Calendar.getInstance();
		Calendar c3 = Calendar.getInstance();
		
		c1.setTime(weekPlanning.getStartDateOfWeek());
		c2.setTime(startDate);
		c3.setTime(weekPlanning.getEndDateOfWeek());
		if(c1.after(c2) == true){
			logger.error("c1 = " + c1.getTime().toString());
			logger.error("c2 = " + c2.getTime().toString());
			throw new BusinessException(BusinessMessagesConstants.ERROR_INVALID_START_DATE_FOR_ASSIGNMENT);
		}
		
		if(c3.before(c2) == true){
			logger.error("c3 = " + c3.getTime().toString());
			logger.error("c2 = " + c2.getTime().toString());
			throw new BusinessException(BusinessMessagesConstants.ERROR_INVALID_START_DATE_FOR_ASSIGNMENT);
		}
		
		return null;
	}
	
	

	public IAnnualPlanningDao getAnnualPlanningDao() {
		return annualPlanningDao;
	}

	public void setAnnualPlanningDao(IAnnualPlanningDao annualPlanningDao) {
		this.annualPlanningDao = annualPlanningDao;
	}

	public IWeekPlanningDao getWeekPlanningDao() {
		return weekPlanningDao;
	}

	public void setWeekPlanningDao(IWeekPlanningDao weekPlanningDao) {
		this.weekPlanningDao = weekPlanningDao;
	}

	public IWeekAssignmentPerEmployeDao getWeekAssignmentPerEmployeDao() {
		return weekAssignmentPerEmployeDao;
	}

	public void setWeekAssignmentPerEmployeDao(
			IWeekAssignmentPerEmployeDao weekAssignmentPerEmployeDao) {
		this.weekAssignmentPerEmployeDao = weekAssignmentPerEmployeDao;
	}

	public IProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(IProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	

}
