package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.AnnualBudget;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.ObjectifPerExercise;
import com.interaudit.domain.model.data.AnnualBudgetData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.ProfitabilityPerCustomerData;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.param.SearchBudgetParam;
import com.interaudit.service.param.SearchProfitabilityPerCustomerParam;
import com.interaudit.service.view.BudgetGeneralView;
import com.interaudit.service.view.ProfitabilityPerCustomerView;



/**
 * Service methods handling AnnualBudget entities.
 */
/**
 * @author blocabe
 *
 */
/**
 * @author bernard
 *
 */
public interface IBudgetService {
    

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a AnnualBudget object identified by given id.
     */
    AnnualBudget getOne(Long id);
    
    public  List<Option> getAllExercicesOptions();
    
   
    
    /**
     * @param id
     * @return adetached annual budget from a contract identifier
     */
    AnnualBudget getOneDetachedFromContract(Long id);
    
    public AnnualBudgetData getOneAnnualBudgetDataFromId(Long id);
    
    

    /**
     * Delete a AnnualBudget object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    
    public void cancelOne(Long id);
    
 
    /**
     * Returns a AnnualBudget object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    AnnualBudget saveOne(AnnualBudget budget); 
    

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    AnnualBudget updateOne(AnnualBudget budget);
    
    /**
     * @param data
     * @return the id of the create / updated budget
     */
    AnnualBudgetData saveOrUpdateBudget(AnnualBudgetData data)throws BusinessException;
    
    
    /**
     * 
     * @return AnnualBudget objects list representing all AnnualBudget known by IAMS
     *         application.
     */
    BudgetGeneralView searchBudgets(SearchBudgetParam param,boolean usePagination,int firstPos,int LINEPERPAGE);
    
    long getTotalCount(SearchBudgetParam  param);
    
    public long getTotalCountProfitabilityPerCustomer(SearchProfitabilityPerCustomerParam  param);
    
    public List<AnnualBudgetData> findBudgetsForExpression(String expression,List<Integer> years);
    
    public List<AnnualBudgetData> findBudgetsForCustomerCode(String code,List<Integer> years);
    
    
    /**
     * @param budget
     * @return the mission created for the budget
     */
  //  Mission createMission(Long budgetId);
    
    /**
     * @param budget
     * @return the mission created for the budget
     */
   // public Mission createMission(AnnualBudget budgetSaved);
    
    public ProfitabilityPerCustomerView calculateProfitabilityPerCustomer(SearchProfitabilityPerCustomerParam param,boolean usePagination,int firstPos,int LINEPERPAGE);
    
    public ObjectifPerExercise computeObjectifPerExerciseForAssocie(Employee responsable ,Exercise exercise);
    public ObjectifPerExercise computeObjectifPerExerciseForManager(Employee responsable ,Exercise exercise);
    
}
