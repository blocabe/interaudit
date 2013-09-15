package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.AnnualBudget;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.ObjectifPerExercise;
import com.interaudit.domain.model.data.AnnualBudgetData;
import com.interaudit.domain.model.data.AnnualBudgetSumData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchBudgetParam;
import com.interaudit.service.param.SearchProfitabilityPerCustomerParam;


public interface IBudgetDao {
	
	 /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a AnnualBudget object identified by given id.
     */
	public AnnualBudget getOne(Long id);
    
	public AnnualBudget getOneDetached(Long id);
    
	public AnnualBudget getOneDetachedFromContract(Long id);
    
	public AnnualBudgetData getOneAnnualBudgetDataFromId(Long id);
	
	public Mission createMission(Long budgetId, Mission  parent,String mandat);
	
	public AnnualBudget findBudgetForContractAndExercise(Long idExecise, Long idContract);
	
	public List<AnnualBudgetData> findBudgetsForExpression(String expression,List<Integer> years);
	
	public List<AnnualBudgetData> findBudgetsForCustomerCode(String code,List<Integer> years);
	
	public void updateReportedAmountInChildBudget(Long idParent,double reportedAmount);
	
	
	

    

    /**
     * Delete a AnnualBudget object identified by given id.
     * 
     * @param id
     */
	public void deleteOne(Long id); 
    
 
    /**
     * Returns a AnnualBudget object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
	public AnnualBudget saveOne(AnnualBudget budget); 
    

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
	public AnnualBudget updateOne(AnnualBudget budget);
    
    
    /**
     * 
     * @return AnnualBudget objects list representing all AnnualBudget known by IAMS
     *         application.
     */
    public List<AnnualBudgetData>  searchBudgets(  SearchBudgetParam param,boolean usePagination,int firstPos,int LINEPERPAGE);
    
    public List<Option> findCustomerOptionsForLetter(String letter, List<Integer> years) ;
    
    
    long getTotalCount(SearchBudgetParam  param);
    public long getTotalCountProfitabilityPerCustomer(SearchProfitabilityPerCustomerParam  param);
    
   
    
    public ObjectifPerExercise computeObjectifPerExerciseForAssocie(Employee responsable ,Exercise exercise);
    public ObjectifPerExercise computeObjectifPerExerciseForManager(Employee responsable ,Exercise exercise);

	public abstract AnnualBudgetSumData findBudgetSums(SearchBudgetParam param);

}
