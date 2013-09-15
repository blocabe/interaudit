package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.AnnualResult;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.data.AlerteData;
import com.interaudit.domain.model.data.MissionToCloseData;
import com.interaudit.service.exc.BusinessException;

public interface IExerciseDao {
	
	public boolean existFinalBillForBudget(Long idBudget,Integer currentYear);
	public List<MissionToCloseData> findBudgetsWithFinalBillToClose(Integer currentYear);
	
	/**
     * 
     * @return Exercise objects list representing all Exercise known by IAMS
     *         application.
     */
    List<Exercise> getAll();
    
    public List<String> generateReminderForMissionsWithoutInvoiceAndAlreadyCharged();
    
    public void markexErciseForUpdate(Long idExercise, boolean flag);
    
    public boolean deletePendingExercise(Long idExercise);
    
    public int countExercises();
    
    List<Exercise> getExercises(List<Integer> years);
    
    public List<Integer> getExercisesAsInteger();
    
    public Exercise buildExercise(java.io.OutputStream out) throws BusinessException;
    
    public Exercise approveExercise(Long idExercise) throws BusinessException;
    
    public Exercise recalculateExercise(int year) throws BusinessException;
	public Exercise applyInflation( float inflationPercentage,Long idExercise) throws BusinessException;
	
	public Exercise closeExercise(Long idExercise) throws BusinessException;
	
	public Integer getFirstOnGoingExercise();
	
	 public Integer getLastClosedExercise();
	
	public com.interaudit.domain.model.Exercise referenceBudgetPage(Long idExercise) throws BusinessException;
	
	
	public double calculateReportedAmountForBudget(Long idBudget,Integer currentYear);
	
	public List<AlerteData> checkMissionsAlertes();
    
    /**
     * 
     * @return AnnualBudget objects list representing all AnnualBudget known by IAMS
     *         application.
     
    List<AnnualBudgetView> getBudgetsForSelectedExercises(  SearchBudgetParam param);
    */
    
    /**
     * @param param
     * @return
     
    List<AnnualBudgetView> findBudgets(SearchBudgetParam param);
    */

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Exercise getOne(Long id);
    
    public Exercise getOneDetached(Long id);
    
    
    Exercise getOneFromYear(int year);
    
    /**
     * @return the Integer having the max year
     */
    public Integer getMaxYear();
    
    public int getCountOfOngoingExercises();
    
    public int getCountOfPendingExercises();
    

    /**
     * Delete a Exercise object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    
 
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Exercise saveOne(Exercise exercise); 


    
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Exercise updateOne(Exercise exercise);
    
    public AnnualResult updateOne(AnnualResult result);
    public AnnualResult getOneDetached(Long exerciseId, Long responsableIdentifier);
    
    public int transferMissingBudgetsFromPreviousExercise( Exercise currentExercise);

}
