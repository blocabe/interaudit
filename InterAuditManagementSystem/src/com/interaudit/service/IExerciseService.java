package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.view.ExerciseGeneralView;



/**
 * Service methods handling Role entities.
 */
/**
 * @author blocabe
 *
 */
/**
 * @author bernard
 *
 */
/**
 * @author bernard
 *
 */
public interface IExerciseService {
	
	public boolean deletePendingExercise(Long idExercise);
    /**
     * 
     * @return Exercise objects list representing all Exercise known by IAMS
     *         application.
     */
    List<Exercise> getAll();
    
    int countExercises();
    
    public Integer getFirstOnGoingExercise();
    
    public Integer getLastClosedExercise();
    

    /**
     * Returns a Exercise object identified by given id.
     * 
     * @return Returns a new Exercise object built from the current valid contracts .
     */
    Exercise buildExercise(java.io.OutputStream out) throws BusinessException;
    
    /**
     * Returns a Exercise object identified by given id.
     * 
     * @return Returns  Exercise object  .
     */
    public Exercise approveExercise(Long idExercise) throws BusinessException;
    
    public Exercise applyInflation( float inflation,Long idExercise) throws BusinessException;

    /**
     * Returns a Exercise object identified by given id.
     * 
     * @return Returns a  Exercise object .
     */
    public Exercise closeExercise(Long idExercise) throws BusinessException;
    
    public com.interaudit.domain.model.Exercise referenceBudgetPage(Long idExercise) throws BusinessException;
    
    
    /**
     * @return true if there is at least one exercise not in status CLOSED
     */
    public boolean hasOnGoingExercise();

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Exercise getOne(Long id);
    
    Exercise getOneFromYear(int year);
    
    public Exercise getOneDetached(Long id);
    
    public Integer getMaxYear();
    

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
    
    
    List<Option> getAllExercicesOptions();
    
    
    ExerciseGeneralView getExerciseGeneralView(int year);
    
    Exercise recalculateExercise(int year) throws BusinessException;
    
    
    /**
     * 
     * @return AnnualBudget objects list representing all AnnualBudget known by IAMS
     *         application.
     
    BudgetGeneralView getBudgetsForSelectedExercises(  SearchBudgetParam param);
    */
    
    public void updateExerciceAndBudget(Long idExercise);
    //public void updateExerciceAndBudget();  
	public abstract ExerciseGeneralView getExerciseGeneralViewLight(int year);
}
