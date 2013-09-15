package com.interaudit.domain.dao;

import java.util.Date;
import java.util.List;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Event;
import com.interaudit.domain.model.EventPlanning;
import com.interaudit.domain.model.Message;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.MissionMember;
import com.interaudit.domain.model.data.MessageData;
import com.interaudit.domain.model.data.MissionBudgetData;
import com.interaudit.domain.model.data.MissionData;
import com.interaudit.domain.model.data.MissionHeurePresteeData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.ProfitabilityPerCustomerData;
import com.interaudit.service.param.SearchMissionParam;
import com.interaudit.service.param.SearchProfitabilityPerCustomerParam;
import com.interaudit.service.view.AgendaPlanningView;
import com.interaudit.service.view.AnnualPlanningView;
import com.interaudit.service.view.AnnualTimesheetReportView;


public interface IMissionDao {
	
	public  List<Option> getAllOpenMissionsWithoutFinalBillForYearAsOptions(String  exercise);
	public AnnualPlanningView updateAnnualPlanningView(AnnualPlanningView annualPlanningView);
	public long  countMissionBudget(Long missionId);
	
	public Mission markMissionAsClosed(Long missionId);
	
	public ProfitabilityPerCustomerData calculateProfitabilityPerMission(Long idMission);
	
	 public List<ProfitabilityPerCustomerData> calculateProfitabilityPerCustomer(SearchProfitabilityPerCustomerParam param,boolean usePagination,int firstPos,int LINEPERPAGE);
	 
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Mission object identified by given id.
     */
    Mission getOne(Long id);
    
    public int addMemberToMission(Long idMission,Long employeId);
    public int removeMemberToMission(Long idMission,Long employeId);
    public int updateMissionMemberstring(Long idMission);
    
    public List<Employee> getMissionMembers(Long idMission);
    
    /**
     * Returns a detached Mission object identified by given id.
     * 
     * @param id
     * @return Returns a detached Mission object identified by given id.
     */
    Mission getOneDetached(Long id);
    
    public  List<Option> getAllMissionsExercicesOptions();
    
    
    public EventPlanning getEventPlanningFromIdentifier(Long planningId);
    
    public EventPlanning getEventPlanning( int year,int weekNumber,Long employeId);
    
    public EventPlanning updateEventPlanning(EventPlanning eventPlanning);
    
    public void  deleteItemEventPlanning(Long itemEventPlanning);
    
    public void  deleteItemEventPlannings(List<Long> itemIds);
    
    public EventPlanning createPlanningEvent(Long userId,int year, Integer weekNumber );
    
    
    Mission getOneDetachedFromBudgetId(Long id);
    
    Mission getOneDetachedForContractAndExercise(Long contractId, String exercise, int year );
    
    public Mission  getOneDetachedFromReference(String reference);
    
    public int createListOfPlanningEvents(List<Long> userIds,Long missionId, int year, List<Integer> dayList,String startdate,String enddate,String durationType,float duration,Date expectedStartDate,Date expectedEndDate,double hours );
    
    public int deleteListOfPlanningEvents(List<Long> userIds,int year,List<Integer> dayList );
    
    public int updatePlanningFromTimeSheets(List<Long> userIds,int year,List<Integer> dayList);
    
    public AnnualPlanningView buildAnnualPlanningView(Employee caller,String year,int startMonth,int endMonth,/* String month,String employeeType,*/String role,int startWeekNumber,int endWeekNumber);
    
    public AnnualTimesheetReportView buildAnnualTimesheetReportView(String year,int startMonth,int endMonth,int startWeekNumber,int endWeekNumber);
    
    public AgendaPlanningView buildAgendaPlanningView(Employee caller,String year,String week,String employeeIdentifier);
    
    public Event createEvent(Long userId, Long missionId, int year,  int month,
			int dayOfYear, int startHour,  int endHour, String object,  String title);
    
    public Event updateEvent(Long eventId,Long userId, Long missionId, int year,  int month,
			int dayOfYear, int startHour,  int endHour, String object,  String title,Date dateOfEvent);
    

    /**
     * Delete a Mission object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    public void  deleteOneCost(Long id);
    public void deleteOneAlerte(Long id);
    
 
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
    public List<MissionData> searchMissions(  Employee caller,SearchMissionParam param, boolean sortedByName, boolean isforOption);
    public List<MissionData> findMissionsForEmployee(Employee caller,SearchMissionParam param, boolean sortedByName, boolean isforOption);
    
    public MissionData findMissionDataForIdenifier(Long id);
    
    public List<MissionBudgetData>  calculateMissionBudget(Long missionId);
    public List<MissionBudgetData>  calculateMissionBudget2(Long missionId);
    public List<MissionBudgetData>  calculateMissionBudgetForNonValidatedTimeSheet(Long missionId);
    
    public List<MissionHeurePresteeData>  calculateMissionHeuresPrestees(Long missionId);
    
    public List<Employee> getTeamMembers(Long missionId);
    
    public List<MessageData> searchMessages(Employee employee,Integer year,Long missionId,boolean received,boolean usePagination ,int firstPos,int LINEPERPAGE);
    public Message getMessageOne(Long id);
    public  Message  getOneMessageDetached(Long id);
    
    public Message updateOneMessage(Message message);
    

}
