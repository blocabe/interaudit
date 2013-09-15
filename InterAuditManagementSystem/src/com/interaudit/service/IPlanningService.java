package com.interaudit.service;

import java.util.Date;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.data.AnnualPlanning;
import com.interaudit.domain.model.data.WeekAssignmentPerEmployee;
import com.interaudit.domain.model.data.WeekPlanning;
import com.interaudit.service.exc.BusinessException;

public interface IPlanningService {
	
	/**
	 * @param responsable
	 * @param exercise
	 * @return
	 */
	public AnnualPlanning createAnnualPlanning( Employee responsable,String exercise);
	
	/**
	 * @param exercise
	 * @param weekNumber
	 * @return 
	 */
	public WeekPlanning getWeekPlanning(String exercise, int weekNumber);
	
	
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
			/*Date endDate,*/int weekNumber,String exercise, long missionIdentifier, long teamMemberIdentifier,
			String code,String description) throws BusinessException;

}
