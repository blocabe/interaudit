package com.interaudit.domain.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IBudgetDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.AnnualBudget;
import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.MissionTypeTaskLink;
import com.interaudit.domain.model.ObjectifPerExercise;
import com.interaudit.domain.model.Task;
import com.interaudit.domain.model.data.AnnualBudgetData;
import com.interaudit.domain.model.data.AnnualBudgetSumData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchBudgetParam;
import com.interaudit.service.param.SearchProfitabilityPerCustomerParam;

/**
 * @author bernard
 *
 */
public class BudgetDao implements IBudgetDao {

	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(BudgetDao.class);
	
	/**
     * @param budget
     * @return the mission created for the budget
     */
    public  Mission createMission(Long budgetId, Mission  parent,String mandat){
    	
    	try{	
    		//Load the annual budget
			AnnualBudget budget =em.find(AnnualBudget.class, budgetId);
	        if (budget == null) {
	            throw new DaoException(2);
	        }
	        
	        //Get the contract
	        Contract contract = budget.getContract();
	        if (contract == null) {
	            throw new DaoException(2);
	        }
	        
	        MissionTypeTaskLink missionTypeTaskLink = null;
	        List<MissionTypeTaskLink> resultList = em.createQuery("select c from MissionTypeTaskLink c where c.missionTypeCode = :code").setParameter("code", contract.getMissionType()).getResultList();
	        if(( resultList != null) && (resultList.size()>0)){
	        	missionTypeTaskLink = resultList.get(0);
	        }else{
	            throw new DaoException(2);
	        }
	        
	        Task task =  null;
	        List<Task> resultList2 =em.createQuery("SELECT distinct t FROM Task t  WHERE t.code = :taskCode").setParameter("taskCode", missionTypeTaskLink.getTaskCode()).getResultList();
	       
	        if(( resultList2 != null) && (resultList2.size()>0)){
	        	task = resultList2.get(0);
	        }else{
	            throw new DaoException(3);
	        }
	      
	        
	        //Create the new mission
	        Mission mission = new Mission(budget,parent,mandat);
	       
	        //Save the mission
	        em.persist(mission);
	        
	        //Create the activity
	        Activity activity = new Activity( mission,  task, new Date(),1) ;
	        
	        //Save the activity
	        em.persist(activity);
	        
	        return mission;
	       
	        
	        
		}
		catch(Exception e){	
			logger.error(e.getMessage());
			//throw new BusinessException(e);
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : createMission..."));
			//return null;
	        
		}finally{
			em.close();
		}
    	
  
    }
	
	
    public void deleteOne(Long id) {
		
		try{			
			AnnualBudget budget = em.find(AnnualBudget.class, id);
			if (budget == null) {
	            throw new DaoException(2);
	        }
			
			
			
			List<AnnualBudget> resultList = em
		        .createQuery(
		                "select b  from AnnualBudget b  where b.parent.id = :parentId")
		        .setParameter("parentId", id).getResultList();
			
			if(( resultList != null) && (resultList.size()>0)){
				AnnualBudget budgetChild  = resultList.get(0);
				deleteOne(budgetChild.getId());
	        }
			
			
			
	        Mission mission = budget.getMission();
	        
	        mission.setAnnualBudget(null);
	        
	        budget.setMission(null);
	        
	        em.remove(budget); 
	        
	        em.remove(mission);  
		}
		catch(Exception e){
			logger.error(e.getMessage());			
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : deleteOne..."));
			//return null;
	        
		}finally{
			em.close();
		}
		
		   
    }
    
    
public void updateReportedAmountInChildBudget(Long idParent,double reportedAmount) {
		
		try{			
			AnnualBudget budget = em.find(AnnualBudget.class,idParent);
			if (budget == null) {
	            throw new DaoException(2);
	        }
			
			
			
			List<AnnualBudget> resultList = em
		        .createQuery(
		                "select b  from AnnualBudget b  where b.parent.id = :parentId")
		        .setParameter("parentId", idParent).getResultList();
			
			if(( resultList != null) && (resultList.size()>0)){
				AnnualBudget budgetChild  = resultList.get(0);
				budgetChild.setReportedAmount(reportedAmount);
				budgetChild = em.merge(budgetChild);
				//Mark this exercise for update
				Exercise exerciseToUpdate=budgetChild.getExercise();
				exerciseToUpdate.setNeedUpdate(true);
				em.merge(exerciseToUpdate);
				updateReportedAmountInChildBudget(budgetChild.getId(),reportedAmount);
	        }
			
			
		}
		catch(Exception e){
			logger.error(e.getMessage());			
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : deleteOne..."));
			//return null;
	        
		}finally{
			em.close();
		}
		
		   
    }

	
	 /**
     * 
     * @return AnnualBudget objects list representing all AnnualBudget known by IAMS
     *         application.
     */
	
	public List<AnnualBudgetData> searchBudgets( SearchBudgetParam param,boolean usePagination,int firstPos,int LINEPERPAGE){
		return findBudgets( param,usePagination, firstPos, LINEPERPAGE);
	}
	
	    
	    
	public long getTotalCount(SearchBudgetParam  param){
		
		try{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
	        StringBuilder hql = new StringBuilder("select count(*)  from AnnualBudget b  join b.exercise e  join b.contract.customer c   ");
	        StringBuilder whereClause = new StringBuilder("");
	        
	        //Rechercher les années
	        if (param.getYears()!= null) {
	            parameters.put("years", param.getYears());
	            whereClause.append("(e.year in (:years))");
	        }
	        
	        //Rechercher le status
	      //Rechercher le type
	        if (param.getType() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("type", param.getType() +"%");
	            whereClause.append("b.contract.missionType like :type");
	        }
	        
	      //Rechercher l'origin
	        if (param.getOrigin() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("originId", param.getOrigin());
	            whereClause.append("c.origin.id = :originId");
	        }
	        
	      //Rechercher le manager
	        if (param.getManager() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("managerId", param.getManager());
	            whereClause.append("b.budgetManager.id = :managerId");
	        }
	        
	        
	        //Rechercher le associe
	        if (param.getAssocie() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("associeId", param.getAssocie());
	            whereClause.append("b.associeSignataire.id = :associeId");
	        }
	        
	        
	      //Filtrer lesclients commencant par customerStartString
	        if (param.getCustomerStartString() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerNameLike",  param.getCustomerStartString() +"%");
	            whereClause.append("c.companyName like :customerNameLike");
	        }
	        
	        //Filtrer les missions à cloturer
	        if (param.isToClose() == true) {
	        	logger.info("Mission to close only : "+param.isToClose());
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            //parameters.put("toCloseOnly",  param.getCustomerStartString() +"%");
	            whereClause.append(" b.finalBill = true AND  b.status !="+ "'CLOSED'");
	        }
	        
	        
	        
	        
	        
	        if (whereClause.length() > 0) {
	            hql.append(" WHERE ").append(whereClause);
	        }
	        //hql.append(" ORDER BY c.companyName,e.year ");
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
			
			 Number result = null;
			 
			 try{	 
				 result = (Number) q.getSingleResult();
			 }
			 catch(javax.persistence.NoResultException e){	
				 result = null;
			 }
			 
			 if ( result == null) {
			     return 0;
			 } else {
			     return result.longValue();
			 }

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : getTotalCount..."));
			//return null;
	        	//return -1L;

		}finally{
			em.close();
		}
		
		
	}
	
	
public long getTotalCountProfitabilityPerCustomer(SearchProfitabilityPerCustomerParam  param){
		
		try{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
	        StringBuilder hql = new StringBuilder("select count(*) from Mission m   ");
	        StringBuilder whereClause = new StringBuilder("");
	        
	      //Rechercher les années
	        if (param.getYears()!= null  ) {
	           // parameters.put("years", param.getYearsAsString());
	            //whereClause.append("(m.exercise in (:years))");
	        	parameters.put("years", param.getYears());	            
	            whereClause.append("(m.annualBudget.exercise.year in (:years))");
	        }
	      
	        //Rechercher l'associe
	        if (param.getAssocie() != null && param.getAssocie() != -1L) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("associeId", param.getAssocie());
	            whereClause.append(" m.annualBudget.associeSignataire.id = :associeId");
	        }
	        
	        //Rechercher l'associe
	        if (param.getManager() != null && param.getManager() != -1L) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("managerId", param.getManager());
	            whereClause.append("m.annualBudget.budgetManager.id = :managerId");
	        }
	        
	        //Rechercher pour une mission spécifique
	        if (param.getCustomer() != null && param.getCustomer() != -1L) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerId", param.getCustomer());
	            whereClause.append("m.annualBudget.contract.customer.id = :customerId");
	        }
	        
	        
	        
	        
	        
	        if (whereClause.length() > 0) {
	            hql.append(" WHERE ").append(whereClause);
	        }
	        //hql.append(" ORDER BY c.companyName,e.year ");
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
			
			 Number result = null;
			 
			 try{	 
				 result = (Number) q.getSingleResult();
			 }
			 catch(javax.persistence.NoResultException e){	
				 result = null;
			 }
			 
			 if ( result == null) {
			     return 0;
			 } else {
			     return result.longValue();
			 }

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : getTotalCountProfitabilityPerCustomer..."));
			//return null;
	        	//return -1L;

		}finally{
			em.close();
		}
		
		
	}
	
	
	
	
	

	
	@SuppressWarnings("unchecked")
	public List<AnnualBudgetData> findBudgets(SearchBudgetParam param,boolean usePagination ,int firstPos,int LINEPERPAGE) {
		logger.info("Getting into BudgetDao:findBudgets...");
		try{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
	        StringBuilder hql = new StringBuilder("select new com.interaudit.domain.model.data.AnnualBudgetData(b.id,e.year, c.companyName, c.code, b.expectedAmount,b.reportedAmount,b.effectiveAmount,b.status, c.origin.name, b.associeSignataire.code, b.budgetManager.code,c.origin.id, b.associeSignataire.id, b.budgetManager.id, b.contract.reference,b.comments,b.contract.missionType,b.fiable,b.expectedAmountRef,b.reportedAmountRef, b.mission.exercise,b.mission.id,b.finalBill,b.interim,e.status,e.isAppproved)  from AnnualBudget b  join b.exercise e  join b.contract.customer c   ");
	        StringBuilder whereClause = new StringBuilder("");
	        
	        //Rechercher les années
	        if (param.getYears()!= null) {
	            parameters.put("years", param.getYears());
	            whereClause.append("(e.year in (:years))");
	        }
	        
	        
	        //Rechercher le type
	        if (param.getType() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("type", param.getType() +"%");
	            whereClause.append("b.contract.missionType like :type");
	        }
	        
	      //Rechercher l'origine
	        if (param.getOrigin() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("originId", param.getOrigin());
	            whereClause.append("c.origin.id = :originId");
	        }
	        
	      //Rechercher le manager
	        if (param.getManager() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("managerId", param.getManager());
	            whereClause.append("b.budgetManager.id = :managerId");
	        }
	        
	        
	        //Rechercher le associe
	        if (param.getAssocie() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("associeId", param.getAssocie());
	            whereClause.append("b.associeSignataire.id = :associeId");
	        }
	        
	      
	        //Filtrer lesclients commencant par customerStartString
	        if (param.getCustomerStartString() != null) {
	        	logger.info("customerNameLike : "+param.getCustomerStartString());
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerNameLike",  param.getCustomerStartString() +"%");
	            whereClause.append("upper(c.companyName) like upper(:customerNameLike)");
	        }
	        
	        
	        //Filtrer les missions à cloturer
	        if (param.isToClose() == true) {
	        	logger.info("Mission to close only : "+param.isToClose());
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            //parameters.put("toCloseOnly",  param.getCustomerStartString() +"%");
	            whereClause.append(" b.finalBill = true AND  b.status !="+ "'CLOSED'");
	        }
	        
	        
	        
	        if (whereClause.length() > 0) {
	            hql.append(" WHERE ").append(whereClause);
	        }
	        hql.append(" ORDER BY c.companyName,b.contract.missionType,b.mission.exercise,e.year ");
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	        //Gestion de la pagination
	        if(usePagination == true){
	        	q.setFirstResult(firstPos);
		        q.setMaxResults(LINEPERPAGE);
	        }
	        
	       
	    
	        return q.getResultList();

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : findBudgets..."));
			//return null;

		}finally{
			em.close();
		}
	        
		 	
	    }
	
	
	@Override
	@SuppressWarnings("unchecked")
	public AnnualBudgetSumData findBudgetSums(SearchBudgetParam param) {
		logger.info("Getting into findBudgetSums...");
		try{
			/*
			AnnualBudgetSumData(double expectedAmount, double effectiveAmount,
					double reportedAmount, double expectedAmountRef,
					double reportedAmountRef)
					*/ 
			
			Map<String, Object> parameters = new HashMap<String, Object>();
	        StringBuilder hql = new StringBuilder("select new com.interaudit.domain.model.data.AnnualBudgetSumData(sum(b.expectedAmount),sum(b.effectiveAmount),sum(b.reportedAmount) ,sum(b.expectedAmountRef),sum(b.reportedAmountRef))  from AnnualBudget b  join b.exercise e  join b.contract.customer c   ");
	        StringBuilder whereClause = new StringBuilder("");
	        
	        //Rechercher les années
	        if (param.getYears()!= null) {
	            parameters.put("years", param.getYears());
	            whereClause.append("(e.year in (:years))");
	        }
	        
	        //Rechercher le type
	        if (param.getType() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("type", param.getType() +"%");
	            whereClause.append("b.contract.missionType like :type");
	        }
	        
	      //Rechercher l'origine
	        if (param.getOrigin() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("originId", param.getOrigin());
	            whereClause.append("c.origin.id = :originId");
	        }
	        
	      //Rechercher le manager
	        if (param.getManager() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("managerId", param.getManager());
	            whereClause.append("b.budgetManager.id = :managerId");
	        }
	        
	        
	        //Rechercher le associe
	        if (param.getAssocie() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("associeId", param.getAssocie());
	            whereClause.append("b.associeSignataire.id = :associeId");
	        }
	        
	      
	        //Filtrer lesclients commencant par customerStartString
	        if (param.getCustomerStartString() != null) {
	        	logger.info("customerNameLike : "+param.getCustomerStartString());
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerNameLike",  param.getCustomerStartString() +"%");
	            whereClause.append("upper(c.companyName) like upper(:customerNameLike)");
	        }
	        
	        
	        //Filtrer les missions à cloturer
	        if (param.isToClose() == true) {
	        	logger.info("Mission to close only : "+param.isToClose());
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            //parameters.put("toCloseOnly",  param.getCustomerStartString() +"%");
	            whereClause.append(" b.finalBill = true AND  b.status !="+ "'CLOSED'");
	        }
	        
	        
	        
	        if (whereClause.length() > 0) {
	            hql.append(" WHERE ").append(whereClause);
	        }
	       // hql.append(" ORDER BY c.companyName,b.contract.missionType,b.mission.exercise,e.year ");
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	       
	        
	       
	    
	        return (AnnualBudgetSumData )q.getResultList().get(0);

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : findBudgets..."));
			//return null;

		}finally{
			em.close();
		}
	        
		 	
	    }
	
	
	
	@SuppressWarnings("unchecked")
	public List<Option> findCustomerOptionsForLetter(String letter, List<Integer> years) {
		logger.info("Getting into BudgetDao:findBudgets...");
		try{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
	        StringBuilder hql = new StringBuilder("select  new com.interaudit.domain.model.data.Option(  c.code, c.companyName)  from AnnualBudget b  join b.exercise e  join b.contract.customer c   ");
	        StringBuilder whereClause = new StringBuilder("");
	        
	        //Rechercher les années
	      //Rechercher les années
	        if (years!= null) {
	            parameters.put("years",years);
	            whereClause.append("(e.year in (:years))");
	        }
	        
	      
	        //Filtrer lesclients commencant par customerStartString
	        if (letter != null) {
	        	logger.info("customerNameLike : "+letter);
	        	 if (whereClause.length() > 0) {
		                whereClause.append(" AND ");
		            }
	            parameters.put("customerNameLike",  letter +"%");
	            whereClause.append(" upper(c.companyName) like upper(:customerNameLike)");
	        }
	        
	       
	        
	        
	        if (whereClause.length() > 0) {
	            hql.append(" WHERE ").append(whereClause);
	        }
	        hql.append(" ORDER BY c.companyName");
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	       
	       
	    
	        return q.getResultList();

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : findCustomerOptionsForLetter..."));
			//return null;

		}finally{
			em.close();
		}
	        
		 	
	    }
	
	
	@SuppressWarnings("unchecked")
	public List<AnnualBudgetData> findBudgetsForExpression(String expression,List<Integer> years) {
		logger.info("Getting into BudgetDao:findBudgets...");
		try{		
	        StringBuilder hql = new StringBuilder("select new com.interaudit.domain.model.data.AnnualBudgetData(b.id,e.year, c.companyName, c.code, b.expectedAmount,b.reportedAmount,b.effectiveAmount,b.status, c.origin.name, b.associeSignataire.code, b.budgetManager.code,c.origin.id, b.associeSignataire.id, b.budgetManager.id, b.contract.reference,b.comments,b.contract.missionType,b.fiable,b.expectedAmountRef,b.reportedAmountRef, b.mission.exercise, b.mission.id,b.finalBill,b.interim,e.status,e.isAppproved)  from AnnualBudget b  join b.exercise e  join b.contract.customer c   ");
	        hql.append(" WHERE e.year in (:years) AND upper(c.companyName) like upper(:customerNameLike)  ORDER BY c.companyName,b.contract.missionType,b.mission.exercise,e.year ");	        	       
	        Query q = em.createQuery(hql.toString()).setParameter("years", years).setParameter("customerNameLike", "%"+expression+"%");	    
	        List<AnnualBudgetData> results = q.getResultList();
	        return results;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : findBudgetsForExpression..."));
			//return null;

		}finally{
			em.close();
		}
    }
	
	
	@SuppressWarnings("unchecked")
	public List<AnnualBudgetData> findBudgetsForCustomerCode(String code,List<Integer> years) {
		logger.info("Getting into BudgetDao:findBudgets...");
		try{		
	        StringBuilder hql = new StringBuilder("select new com.interaudit.domain.model.data.AnnualBudgetData(b.id,e.year, c.companyName, c.code, b.expectedAmount,b.reportedAmount,b.effectiveAmount,b.status, c.origin.name, b.associeSignataire.code, b.budgetManager.code,c.origin.id, b.associeSignataire.id, b.budgetManager.id, b.contract.reference,b.comments,b.contract.missionType,b.fiable,b.expectedAmountRef,b.reportedAmountRef, b.mission.exercise, b.mission.id,b.finalBill,b.interim,e.status,e.isAppproved)  from AnnualBudget b  join b.exercise e  join b.contract.customer c   ");
	        hql.append(" WHERE e.year in (:years) AND upper(c.code) like upper(:customerCodeLike)  ORDER BY c.companyName,b.contract.missionType,b.mission.exercise,e.year ");	        	       
	        Query q = em.createQuery(hql.toString()).setParameter("years", years).setParameter("customerCodeLike", "%"+code+"%");	    
	        List<AnnualBudgetData> results = q.getResultList();
	        return results;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : findBudgetsForCustomerCode..."));
			//return null;

		}finally{
			em.close();
		}
    }


    
    
    public AnnualBudget getOne(Long id) {
    	
    	try{
    		 return em.find(AnnualBudget.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : getOne..."));
			//return null;

		}finally{
			em.close();
		}
		
       
    }
    
    @SuppressWarnings("unchecked")
	
    public AnnualBudget getOneDetachedFromContract(Long id){
    	try{
        	String queryString = "select b from AnnualBudget b  where b.contract.id = :id";
            List resultList = em.createQuery(queryString)
            .setParameter("id",
                    id).getResultList();
            if (resultList.size() > 0) {
                return (AnnualBudget) resultList.get(0);
            }
            return null;
        	}catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : getOneDetachedFromContract..."));
    			//return null;

    		}finally{
        		em.close();
        	}
    }
    
    
    public AnnualBudget findBudgetForContractAndExercise(Long idExercise, Long idContract){
    	try{
        	String queryString = "select b from AnnualBudget b  where b.contract.id = :contractId and b.exercise.id = :idExercise";
            List resultList = em.createQuery(queryString)
            .setParameter("contractId", idContract).setParameter("idExercise", idExercise).getResultList();
            if (resultList.size() > 0) {
                return (AnnualBudget) resultList.get(0);
            }
            return null;
        	}catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : findBudgetForContractAndExercise..."));
    			//return null;

    		}finally{
        		em.close();
        	}
    }
    @SuppressWarnings("unchecked")
    
	public AnnualBudgetData getOneAnnualBudgetDataFromId(Long id){
    	try{

    		StringBuilder queryString = new StringBuilder("select new com.interaudit.domain.model.data.AnnualBudgetData(b.id,e.year, c.companyName, c.code, b.expectedAmount,b.reportedAmount,b.effectiveAmount,b.status, c.origin.name, b.associeSignataire.code, b.budgetManager.code,c.origin.id, b.associeSignataire.id, b.budgetManager.id, b.contract.reference,b.comments,b.contract.missionType,b.fiable,b.expectedAmountRef,b.reportedAmountRef,b.mission.exercise,b.mission.id,b.finalBill,b.interim,e.status,e.isAppproved)  from AnnualBudget b  join b.exercise e  join b.contract.customer c  where b.id = :id ");

	        List resultList = em.createQuery(queryString.toString())
	        .setParameter("id",
	                id).getResultList();
	        if (resultList.size() > 0) {
	            return (AnnualBudgetData) resultList.get(0);
	        }
	        return null;
    	}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : getOneAnnualBudgetDataFromId..."));
			//return null;

		}finally{
    		em.close();
    	}
    }
    
    
    @SuppressWarnings("unchecked")
	public AnnualBudget getOneDetached(Long id){
    	try{
    	String queryString = "select b from AnnualBudget b  where b.id = :id";
        List resultList = em.createQuery(queryString)
        .setParameter("id",
                id).getResultList();
        if (resultList.size() > 0) {
            return (AnnualBudget) resultList.get(0);
        }
        return null;
    	}catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
			//return null;

		}finally{
    		em.close();
    	}
    }
    
    
    
    

    
    public AnnualBudget saveOne(AnnualBudget budget) {
    	//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			em.persist(budget);
	        //tx.commit();
	        return budget;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
			//return null;
	        
		}finally{
			em.close();
		}
		
    	
        
    }

    
    public AnnualBudget updateOne(AnnualBudget budget) {    	
		try{		
			AnnualBudget ret = em.merge(budget);	       
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
			//return null;	        
		}finally{
			em.close();
		}
         
    }
    
    
    
    @SuppressWarnings("unchecked")
	public ObjectifPerExercise computeObjectifPerExerciseForManager(Employee responsable ,Exercise exercise){
    	
    	
		try{
			StringBuilder hql = new StringBuilder("select sum(b.expectedAmount + b.reportedAmount)  from AnnualBudget b join b.budgetManager m join b.exercise e  where b.fiable = true and  m.id = :managerId and e.id = :exerciceId");
			Query q = em.createQuery(hql.toString());
			q.setParameter("managerId", responsable.getId());
			q.setParameter("exerciceId", exercise.getId());
			
			ObjectifPerExercise objectif = null;
					
			List<Object> resultList = (List<Object>) q.getResultList();
			if (resultList.size() > 0) {
				Double resultat = (Double)resultList.get(0);
				if(resultat == null)resultat=0.0d;
				double totalExercice = exercise.getTotalExpectedAmount() +exercise.getTotalReportedAmount();
				double annualAmount = (double)resultat;
				float percentage = (float) (annualAmount/totalExercice);
				objectif = new ObjectifPerExercise( responsable, annualAmount,
						annualAmount, percentage,"MANAGER");
				}
					
			return	objectif;
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
			//return null;
	        
		}finally{
			em.close();
		}
    	
    }
    
    
 @SuppressWarnings("unchecked")
public ObjectifPerExercise computeObjectifPerExerciseForAssocie(Employee responsable ,Exercise exercise){
	 try{
			StringBuilder hql = new StringBuilder("select sum(b.expectedAmount + b.reportedAmount) from AnnualBudget b join b.associeSignataire m join b.exercise e  where b.fiable = true and  m.id = :associeId and e.id = :exerciceId");
			Query q = em.createQuery(hql.toString());
			q.setParameter("associeId", responsable.getId());
			q.setParameter("exerciceId", exercise.getId());
			
			ObjectifPerExercise objectif = null;
					
			List<Object> resultList = (List<Object>) q.getResultList();
			if (resultList.size() > 0) {
				Double resultat = (Double)resultList.get(0);
				if(resultat == null)resultat=0.0d;
				double totalExercice = exercise.getTotalExpectedAmount() +exercise.getTotalReportedAmount();
				double annualAmount = (double)resultat;
				float percentage = (float) (annualAmount/totalExercice);
				objectif = new ObjectifPerExercise( responsable, annualAmount,
						annualAmount, percentage,"PARTNER");
				}
					
			return	objectif;
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
			//return null;
	        
		}finally{
			em.close();
		}
    }
    
   

    
}
