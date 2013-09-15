package com.interaudit.service;

import java.util.Date;
import java.util.List;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Event;
import com.interaudit.domain.model.EventPlanning;
import com.interaudit.domain.model.Message;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.MissionMember;
import com.interaudit.domain.model.data.MissionBudgetData;
import com.interaudit.domain.model.data.MissionHeurePresteeData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.ProfitabilityPerCustomerData;
import com.interaudit.service.param.SearchMissionParam;
import com.interaudit.service.view.AgendaPlanningView;
import com.interaudit.service.view.AnnualPlanningView;
import com.interaudit.service.view.AnnualTimesheetReportView;
import com.interaudit.service.view.MessagerieView;
import com.interaudit.service.view.MissionDetailsView;
import com.interaudit.service.view.MissionView;



/**
 * Service methods handling Mission entities.
 */
/**
 * @author blocabe
 *
 */
/**
 * @author bernard
 *
 */
public interface IMissionService {
	
	public AnnualPlanningView updateAnnualPlanningView(AnnualPlanningView annualPlanningView);
	
	public long  countMissionBudget(Long missionId);
	
	public int getCountUnvalidateHoursForMission(Long missionId);
	public void  deleteOneAlerte(Long id);
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Mission object identified by given id.
     */
    Mission getOne(Long id);
    
    public List<Employee> getMissionMembers(Long idMission);
    
    public ProfitabilityPerCustomerData calculateProfitabilityPerMission(Long idMission);
    
    Mission markMissionAsClosed(Long missionId); 
    
    Mission getOneDetachedForContractAndExercise(Long contractId, String exercise, int year );
    
    public Event createEvent(Long userId, Long missionId, int year,  int month,
			int dayOfYear, int startHour,  int endHour, String object,  String title);
   
    public Event updateEvent(Long eventId,Long userId, Long missionId, int year,  int month,
			int dayOfYear, int startHour,  int endHour, String object,  String title,Date dateOfEvent);
    
    
    //public void deleleteEvent(Long eventId,int year,int week,Long userId);
    
    
    public int createListOfPlanningEvents(List<Long> userIds,Long missionId, int year, List<Integer> dayList,String startdate,String enddate,String durationType,float duration,Date expectedStartDate,Date expectedEndDate ,double nbhours);
    
    public int deleteListOfPlanningEvents(List<Long> userIds,int year,List<Integer> dayList );
    
    public int updatePlanningFromTimeSheets(List<Long> userIds,int year,List<Integer> dayList);
    
    public EventPlanning getEventPlanningFromIdentifier(Long planningId);
    
    public EventPlanning getEventPlanning( int year,int weekNumber,Long employeId);
    
    public EventPlanning updateEventPlanning(EventPlanning eventPlanning);
    
    public void  deleteItemEventPlanning(Long identifier);
    
    public void  deleteItemEventPlannings(List<Long> itemIds);
    
    public EventPlanning createPlanningEvent(Long userId,int year, Integer weekNumber );
    
    public int addMemberToMission(Long idMission,Long employeId);
    
    public int removeMemberToMission(Long idMission,Long employeId);
    
    
    
    /**
     * Returns a Mission detailsview objct fro a given identifier object identified by given id.
     * 
     * @param id for a mission
     * @return Returns a MissionDetailsView object identified by given id.
     */
    MissionDetailsView buildMissionDetailsViewForId(Employee caller , Long id);
    
    MissionDetailsView buildMissionDetailsViewForBudgetId(Employee caller ,Long id);
    
    AnnualPlanningView buildAnnualPlanningView(Employee caller,String year,int startMonth,int endMonth, /*String month,String employeeType,*/String role,int startWeekNumber,int endWeekNumber);
    AgendaPlanningView buildAgendaPlanningView(Employee caller,String year,String week,String employeeIdentifier);	
    
    
    AnnualTimesheetReportView buildAnnualTimesheetReportView(String year,int startMonth,int endMonth,int startWeekNumber,int endWeekNumber);
    
    /**
     * Returns a detached Mission object identified by given id.
     * 
     * @param id
     * @return Returns a detached Mission object identified by given id.
     */
    public Mission getOneDetached(Long id);
    
    /**
     * Returns a detached Mission object identified by given id.
     * 
     * @param id
     * @return Returns a detached Mission object for an AnnualBudget object identified by its id.
     */
    public Mission getOneDetachedForBudgetIdentifier(Long id);
    
    public Mission  getOneDetachedFromReference(String reference);
    

    /**
     * Delete a Mission object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    
    void  deleteOneCost(Long id);
    
 
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Mission saveOne(Mission mission); 
    
   
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Mission updateOne(Mission mission);
    
    
    /**
     * 
     * @return Mission objects list matching the criterias
     */
    public MissionView searchMissions(  Employee caller, SearchMissionParam param);
    public MissionView findMissionsForEmployee(Employee caller,SearchMissionParam param,boolean sortedByName);
    
    
    public  List<Option> getAllMissionForYearAsOptions(Employee caller, String exercise);
    
    public List<MissionHeurePresteeData> calculateMissionHeuresPrestees(Mission mission );
    public List<MissionBudgetData> calculateMissionBudget(Mission mission);
    public  List<Option> getAllOpenMissionForYearAsOptions(Employee caller, String exercise);
    
    public MessagerieView searchMessages(Employee employee,Integer year,Long missionId,boolean received,boolean usePagination ,int firstPos,int LINEPERPAGE);
    public Message getMessageOne(Long id);
    public  Message  getOneMessageDetached(Long id);
    public Message updateOneMessage(Message message);
    
    public List<Option> getTaskOptions();
    
    public List<Option> getAllOpenMissionsWithoutFinalBillForYearAsOptions(String  exercise);
    
}
