package com.interaudit.domain.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IBudgetDao;
import com.interaudit.domain.dao.IExerciseDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.AnnualBudget;
import com.interaudit.domain.model.AnnualResult;
import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.PlanningAnnuel;
import com.interaudit.domain.model.data.AlerteData;
import com.interaudit.domain.model.data.MissionToCloseData;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.impl.ExerciseService;
import com.interaudit.util.Constants;

/**
 * @author bernard
 *
 */
public class ExerciseDao implements IExerciseDao {
	
	
	private static final Logger  logger      = Logger.getLogger(ExerciseService.class);
	
	@PersistenceContext
	private EntityManager em;
	
	private IBudgetDao budgetDao;
	
	public static double formatDouble(double value){
		
		double res = Math.rint((value*100 )+ 0.5 ) / 100 ;
		return res;
	}
	
	public void markexErciseForUpdate(Long idExercise, boolean flag){
		try{
			Exercise exerciseToUpdate = getOne(idExercise);
			if(exerciseToUpdate == null){
				throw new BusinessException(new ExceptionMessage("Cannot find this exercise...") ) ;
			}
			exerciseToUpdate.setNeedUpdate(flag);
			em.merge(exerciseToUpdate);
		
		}catch(DaoException daoe){	
			  logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to build the exercise...") ) ;		 
		  }	catch(BusinessException e){
		  		throw e;
		  	}
		catch(Exception e){	 
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to build the exercise...") ) ;		 
		  }
		  finally{
				em.close();
		  }  
	}
	
	public boolean deletePendingExercise(Long idExercise){
		try{
			
			Exercise exerciseToDelete = getOneDetached(idExercise);
			if(!exerciseToDelete.getStatus().equalsIgnoreCase(Exercise.STATUS_PENDING)){
				throw new BusinessException(new ExceptionMessage("Cannot delete this exercise. Only PENDING Exercise can be deleted...") ) ;
			}
			
			//Deleting the timesheet cells for the target exercise
			StringBuffer deleteTimesheetCellsQuery = new StringBuffer("delete from interaudit.timesheet_cells where fk_row in (select id from interaudit.timesheet_rows where fk_timesheet in (select id from interaudit.timesheets where exercise = '"+ exerciseToDelete.getYear() +"'))");		  	
			int result =  em.createNativeQuery(deleteTimesheetCellsQuery.toString()).executeUpdate();
			
			
			//Updating the missions budget to null for the target exercise
			StringBuffer updateTimesheetRowsQuery = new StringBuffer("update interaudit.timesheet_rows set fk_activity = null where fk_activity in (select id from interaudit.activities where mission_id in  (  select id from interaudit.missions where annualbudget_id in  ( select id from interaudit.budgets where fk_exercise ="+idExercise+")))");		  	
			result =  em.createNativeQuery(updateTimesheetRowsQuery.toString()).executeUpdate();
			
			//Deleting the timesheet rows for the target exercise
			StringBuffer deleteTimesheetRowsQuery = new StringBuffer("delete from interaudit.timesheet_rows where fk_timesheet in (select id from interaudit.timesheets where exercise = '"+ exerciseToDelete.getYear() +"')");		  	
			result =  em.createNativeQuery(deleteTimesheetRowsQuery.toString()).executeUpdate();
			
			//Deleting the timesheets  for the target exercise
			StringBuffer deleteTimesheetsQuery = new StringBuffer("delete from interaudit.timesheets where exercise = '"+ exerciseToDelete.getYear() +"'");		  	
			result =  em.createNativeQuery(deleteTimesheetsQuery.toString()).executeUpdate();
			
			//Deleting the activities for the target exercise
			StringBuffer deleteActivitiesQuery = new StringBuffer("delete from interaudit.activities where mission_id in  (  select id from interaudit.missions where annualbudget_id in  ( select id from interaudit.budgets where fk_exercise ="+ idExercise +"))");		  	
			result =  em.createNativeQuery(deleteActivitiesQuery.toString()).executeUpdate();
			
			//Updating the missions budget to null for the target exercise
			StringBuffer updateMissionsQuery = new StringBuffer("update interaudit.budgets set fk_mission = null where fk_exercise ="+ idExercise);		  	
			result =  em.createNativeQuery(updateMissionsQuery.toString()).executeUpdate();
			
			//Deleting the missions budget for the target exercise
			StringBuffer deleteMissionsQuery = new StringBuffer("delete  from interaudit.missions where annualbudget_id in  ( select id from interaudit.budgets where fk_exercise ="+ idExercise +")");		  	
			result =  em.createNativeQuery(deleteMissionsQuery.toString()).executeUpdate();
			
			//Deleting the budgets budget for the target exercise
			StringBuffer deleteBudgetsQuery = new StringBuffer("delete from interaudit.budgets where fk_exercise ="+ idExercise);		  	
			result =  em.createNativeQuery(deleteBudgetsQuery.toString()).executeUpdate();
			
			//Deleting the target exercise
			StringBuffer deleteExerciseQuery = new StringBuffer("delete from interaudit.exercises where id ="+ idExercise);		  	
			result =  em.createNativeQuery(deleteExerciseQuery.toString()).executeUpdate();
			
			 return true;
			}catch(DaoException daoe){	
			  logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to build the exercise...") ) ;		 
		  }	catch(BusinessException e){
		  		throw e;
		  }catch(Exception e){	 
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to build the exercise...") ) ;		 
		  }
		  finally{
				em.close();
		  }  
	}
	
public int transferMissingBudgetsFromPreviousExercise( Exercise currentExercise){
		int count=0;
		try{
			if( currentExercise == null) return 0;
			//Try to find a previous exercise
			Exercise previousExercise =  getOneFromYear(currentExercise.getYear()-1);
			if( previousExercise == null) return 0;
		
			StringBuffer searchQuery = new StringBuffer( "select id from interaudit.budgets where fk_exercise= "+ previousExercise.getId());
			searchQuery.append(" and status='TRANSFERED' and id not in (select fk_parent from interaudit.budgets where fk_exercise=" + currentExercise.getId() +" and fk_parent is not null)");
			Query q = em.createNativeQuery(searchQuery.toString());
			@SuppressWarnings("unchecked")
			List<Object> resultList = (List<Object>) q.getResultList();			
			for(int i = 0;i<resultList.size();i++){
				java.math.BigInteger bigInt =   (java.math.BigInteger)resultList.get(i);
				Long idBudget = bigInt.longValue();
				AnnualBudget previousBudget = em.find(AnnualBudget.class, idBudget);
			  	if(previousBudget == null)  throw new BusinessException(new ExceptionMessage("No matching budget found for identifier : " + idBudget));
			  	String mandat = previousBudget.getMission().getExercise();
		  		String info="Processing customer :  " + previousBudget.getContract().getCustomer().getCompanyName() +" Exercice : "+previousExercise.getYear()+ " Mandat : "+ mandat;
		  		logger.info(info);
		  		
		  		double totalExpectedAmount = 0.0 ;
		  		double totalReportedAmount =0.0;
		  		
		  		boolean resultTransfer = transferAnnualBudget ( previousBudget, currentExercise, totalExpectedAmount, totalReportedAmount);
		  		
		  		if(!resultTransfer){
		  			 String error = "Fail transferring customer :  " + previousBudget.getContract().getCustomer().getCompanyName() +"for  exercice : "+previousExercise.getYear()+ " Mandat : "+ mandat;
		  			 logger.error(error);
			 		 throw new BusinessException(new ExceptionMessage(error) ) ;	
		  		}
		  		else{
		  			 String success = "Success transferring customer :  " + previousBudget.getContract().getCustomer().getCompanyName() +"for  exercice : "+previousExercise.getYear()+ " Mandat : "+ mandat;
		  			 logger.error(success);
		  			count++;
		  		}	
			  	
			}
			
			return count;
				
				
		}
		catch(Exception e){
			return 0; 
		}
		  finally{
				em.close();
		  } 
		
	}
	
	public List<AlerteData> checkMissionsAlertes(){
		
		try{
			List<AlerteData> resultats = new ArrayList<AlerteData>();
			Integer exerciseNumber = getFirstOnGoingExercise();
			if(null == exerciseNumber){
				throw new BusinessException(new ExceptionMessage("No ongoing exercise found...") ) ;
			}
			
			//Building the queryString
			//StringBuffer searchQuery = new StringBuffer("select fk_customer,description from interaudit.contracts where id in");	
			//searchQuery.append("(select fk_contract from interaudit.budgets where fk_mission in (");
			StringBuffer searchQuery = new StringBuffer("select mission_id ,amount,id,numero from interaudit.mission_alertes where status = 'ACTIVE' and mission_id in (");
			searchQuery.append("select fk_mission from interaudit.budgets where fk_exercise = (select id from interaudit.exercises where year =" + exerciseNumber +"))");
			Query q = em.createNativeQuery(searchQuery.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = (List<Object[]>) q.getResultList();			
			for(int i = 0;i<resultList.size();i++){
				Object[] resultat = resultList.get(i);
				Long idMission = ((java.math.BigInteger)resultat[0]).longValue();
				Double amountAlerte = (Double)resultat[1];
				Long idAlerte = ((java.math.BigInteger)resultat[2]).longValue();
				Integer numero = (Integer)resultat[3];
				double prixRevient =0.0d;
				//Calcul du prix de revient
				Number result = null;			               			       
				try{						
					 result = (Number) em
				       .createQuery(
				               "select sum(c.value * t.prixRevient)  from TimesheetCell c join c.row.activity.mission m join c.row.timesheet t where t.status = :status and m.id = :idMission ").setParameter("status", Constants.TIMESHEET_STATUS_STRING_VALIDATED).setParameter("idMission", idMission).getSingleResult();				               
				}
				catch(javax.persistence.NoResultException e){	
					result = null;
				}
				if ( result == null) {
					prixRevient = 0;
				} else {
					prixRevient = result.doubleValue();
				}
				
				//Verifier si on atteint le montant indiqué dans l'alerte
				if( prixRevient >= amountAlerte){
					
					//Update the database mission accordingly
			
					StringBuffer updateAlerteQuery = new StringBuffer("update interaudit.mission_alertes set status = 'SENT' where id ="+ idAlerte);		  	
					result =  em.createNativeQuery(updateAlerteQuery.toString()).executeUpdate();
					
					StringBuffer searchMissionDetailsQuery = new StringBuffer("select title,exercise,typ from interaudit.missions where id = "+idMission);
					q = em.createNativeQuery(searchMissionDetailsQuery.toString());
					@SuppressWarnings("unchecked")
					List<Object[]> resultList2 = (List<Object[]>) q.getResultList();					
					for(int x = 0;x<resultList2.size();x++){
						
						Object[] resultat2 = resultList2.get(i);
						String titleMission = (String)resultat2[0];
						String mandat = (String)resultat2[1];
						String typeMission = (String)resultat2[2];	
						
						AlerteData data = new  AlerteData( exerciseNumber,  titleMission,
								 mandat,  typeMission,  amountAlerte,
								 prixRevient,numero);
						
						resultats.add(data);						
					}				
				}
				
				
				
			}
			 return resultats;
			}catch(DaoException daoe){	
			  logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed in checkMissionsAlertes...") ) ;		 
		  }catch(Exception e){	 
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed in checkMissionsAlertes...") ) ;		 
		  }
		  finally{
				em.close();
		  } 
		
	}
	
	
	
	
	/**
	 * @return a list of mission with the mandat that need to be invoice as the employers have started to charge on them alraedy
	 */
	public List<String> generateReminderForMissionsWithoutInvoiceAndAlreadyCharged(){
		try{
			List<String> resultats = new ArrayList<String>();
			Integer exerciseNumber = getFirstOnGoingExercise();
			if(null == exerciseNumber){
				throw new BusinessException(new ExceptionMessage("No ongoing exercise found...") ) ;
			}
			
			//Building the queryString
			StringBuffer searchQuery = new StringBuffer("select * from interaudit.missions where id in ( select  mission_id  from interaudit.activities where ");	
			searchQuery.append("id in (select fk_activity from interaudit.timesheet_rows where fk_timesheet in ( select id from interaudit.timesheets where exercise ='" +exerciseNumber.toString()+"')) and ");
			searchQuery.append("mission_id not in(select distinct (project_id) from interaudit.invoices where ( project_id  in (select fk_mission from interaudit.budgets where fk_exercise = (select id from interaudit.exercises where year ="+ exerciseNumber +")))))");
			Query q = em.createNativeQuery(searchQuery.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = (List<Object[]>) q.getResultList();
			
			for(int i = 0;i<resultList.size();i++){
				Object[] resultat = resultList.get(i);
				String mandat = (String)resultat[1];
				String title = (String)resultat[2];
				
				resultats.add(title.toUpperCase() + "[ " + mandat.toUpperCase() + " ]");
			}
			 return resultats;
			}catch(DaoException daoe){	
			  logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed in generateReminderForMissionsWithoutInvoiceAndAlreadyCharged...") ) ;		 
		  }
			catch(BusinessException e){
		  		throw e;
		  	}catch(Exception e){	 
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed in generateReminderForMissionsWithoutInvoiceAndAlreadyCharged...") ) ;		 
		  }
		  finally{
				em.close();
		  }  
	}
	
	
	/**
	 * @param idBudget
	 * @param currentYear
	 * @return
	 */
	public boolean existFinalBillForBudget(Long idBudget,Integer currentYear){
		try{
			boolean hasFinalBill = false;		
			AnnualBudget budget = em.find(AnnualBudget.class, idBudget);
		  	if(budget == null)  throw new BusinessException(new ExceptionMessage("No matching budget found for identifier : " + idBudget));
		  	
		  	String mandat = budget.getMission().getExercise();
	  		//Check if the budget has an associated final bill
			Number result = (Number) em
		       .createQuery(
		               "select count(f) from Invoice f where f.type='FB' and f.project.annualBudget.contract.id = :idContract and f.exercise = :year and f.exerciseMandat =:mandat")
		        .setParameter("idContract", budget.getContract().getId()).setParameter("year", Integer.toString(currentYear)).setParameter("mandat", mandat).getSingleResult();
			if ( result == null) {
				hasFinalBill = false;
			} else {
				hasFinalBill = result.intValue() > 0;
			}
			
			return hasFinalBill;
		}catch(DaoException daoe){	
			  logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to execute method existFinalBillForBudget...") ) ;		 
		  }catch(Exception e){	 
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to execute method existFinalBillForBudget...") ) ;		 
		  }
		  finally{
				em.close();
		  }  
	}
	
	/**
	 * @param idBudget
	 * @param currentYear
	 * @return
	 */
	public boolean existOngoingBudgetWithFinalBillForExercise(Integer currentYear){
		try{
		
			List<MissionToCloseData> result = findBudgetsWithFinalBillToClose(currentYear);
			
			return (result != null) && (!result.isEmpty());
		}catch(DaoException daoe){	
			  logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to execute method existFinalBillForBudget...") ) ;		 
		  }catch(Exception e){	 
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to execute method existFinalBillForBudget...") ) ;		 
		  }
		  finally{
				em.close();
		  }  
	}
	
	/**
	 * @param currentYear
	 * @return the list of ONGOING budget having a final bill and that therefore needs to be closed
	 */
	@SuppressWarnings("unchecked")
	public List<MissionToCloseData> findBudgetsWithFinalBillToClose(Integer currentYear){
		try{		
	  		//Check the invoice FB having  budget in status ONGOING			
			String queryString = "select new com.interaudit.domain.model.data.MissionToCloseData(c.companyName,f.exerciseMandat,b.associeSignataire.lastName, b.associeSignataire.email,b.budgetManager.lastName, b.budgetManager.email, b.status, f.exercise) from Invoice f join f.project.annualBudget b join f.project.annualBudget.contract.customer c where f.type='FB' and f.project.annualBudget.status = :status and f.exercise = :year";
			List<MissionToCloseData> resultList  = (List<MissionToCloseData>) em.createQuery(queryString).setParameter("year", Integer.toString(currentYear)).setParameter("status", "ONGOING").getResultList();
			return resultList;
		}catch(DaoException daoe){	
			  logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to execute method findBudgetsWithFinalBillToClose...") ) ;		 
		  }catch(Exception e){	 
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to execute method findBudgetsWithFinalBillToClose...") ) ;		 
		  }
		  finally{
				em.close();
		  }  
	}
	
	public double calculateReportedAmountForBudget(Long idBudget,Integer currentYear){
		
		try{
		double reportedAmount = 0d;		  				  			
		double totalInvoiced = 0;
		Number result = null;
		AnnualBudget budget = em.find(AnnualBudget.class, idBudget);
		if(budget == null)  throw new BusinessException(new ExceptionMessage("No matching budget found for identifier : " + idBudget));
		String mandat = budget.getMission().getExercise();  	
  		try{	
  			 result = (Number) em
		       .createQuery(
		               "select sum(f.amountNetEuro) from Invoice f where f.project.annualBudget.contract.id = :idContract and f.exercise = :year and f.exerciseMandat =:mandat")
		       .setParameter("idContract", budget.getContract().getId()).setParameter("year", Integer.toString(currentYear)).setParameter("mandat", mandat).getSingleResult();
  		}
		 catch(javax.persistence.NoResultException e){	
  			result = null;			 
		 }
 
		if ( result == null) {
			totalInvoiced = 0;
		} else {
			totalInvoiced = result.doubleValue();
		}
			
		double totalAmount = formatDouble(budget.getExpectedAmount() + budget.getReportedAmount());
			
		if( totalAmount <= totalInvoiced){
			reportedAmount = 0d;
		}
		else{
			reportedAmount = formatDouble(totalAmount - totalInvoiced);
		}
		
		return reportedAmount;
		
	}catch(DaoException daoe){	
		  logger.error(daoe.getMessage());
	 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to execute method existFinalBillForBudget...") ) ;		 
	 }catch(Exception e){	 
		  logger.error(e.getMessage());
	 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to execute method existFinalBillForBudget...") ) ;		 
	 }
	 finally{
			em.close();
	 }  
			
	}
	
	
	protected boolean transferAnnualBudget (AnnualBudget previousBudget,Exercise exercise,double totalExpectedAmount,double totalReportedAmount){
		
		try{
			String mandat = previousBudget.getMission().getExercise();
			int year = exercise.getYear()-1;
	  	
	  		boolean isInterim = previousBudget.isInterim();
	  		//Check the status of the budget
	  		boolean valid = !( previousBudget.getStatus().equalsIgnoreCase(AnnualBudget.STATUS_CANCELLED) || previousBudget.getStatus().equalsIgnoreCase(AnnualBudget.STATUS_CLOSED) );
	  		
	  		
	  		//We only take into account the valid ones
				if(valid){
					Contract contract = previousBudget.getContract();	
					Contract contractLive = (Contract) em.find(Contract.class, contract.getId());
					
					if(isInterim){
						
						//Creation du budget de continuation de la mission d'interim
						AnnualBudget budget1 = new AnnualBudget();
						budget1.setExercise(exercise);				  		
						budget1.setParent(previousBudget);
						double reportedAmount = contract.getAmount()- previousBudget.getExpectedAmount();
						budget1.setStatus(AnnualBudget.STATUS_TRANSFERED);
						budget1.setReportedAmount(reportedAmount);	  		
						budget1.setContract(contract);
						//Set the associe and manager
						budget1.setBudgetManager(contractLive.getCustomer().getCustomerManager());
						budget1.setAssocieSignataire(contractLive.getCustomer().getAssocieSignataire());
						totalExpectedAmount += budget1.getExpectedAmount();
				  		totalReportedAmount += budget1.getReportedAmount();
				  		//Save the budget		  	
					  	em.persist(budget1);					  	
					  	Mission parentMission = previousBudget.getMission();
				  		Mission mission = budgetDao.createMission(budget1.getId(), parentMission,mandat);
				  		mission.setStatus(Mission.STATUS_TRANSFERED);
				  		mission = em.merge(mission);				  		
				  		budget1.setMission(mission);
				  		budget1 = em.merge(budget1);
						
						//Creation du nouveau budget d'interim pour l'année suivante
						AnnualBudget budget2 = new AnnualBudget();
						budget2.setInterim(true);
						budget2.setExercise(exercise);				  		
						budget2.setParent(null);
						budget2.setStatus(AnnualBudget.STATUS_PENDING);
						budget2.setReportedAmount(0.0);	 
						budget2.setExpectedAmount(previousBudget.getExpectedAmount());
						budget2.setContract(contract);
						//Set the associe and manager
						budget2.setBudgetManager(contractLive.getCustomer().getCustomerManager());
						budget2.setAssocieSignataire(contractLive.getCustomer().getAssocieSignataire());
						totalExpectedAmount += budget2.getExpectedAmount();
						totalReportedAmount += budget2.getReportedAmount();
						//Save the budget		  	
						em.persist(budget2);					  	
				  
				  	
				  	Integer resultat = null;
					
					try{
						resultat = Integer.parseInt(mandat);
					}
					catch(NumberFormatException e){
						resultat = exercise.getYear();
					}
				
					mandat = Integer.toString(resultat+1);
			  		mission = budgetDao.createMission(budget2.getId(), null,mandat);
			  		mission = em.merge(mission);				  		
			  		budget2.setMission(mission);
			  		budget2 = em.merge(budget2);
						
					}
					else{
		  				
		  			    //Calculate for each contract the total amount invoiced for same contract id, exercice - 1 and mandat -1
		  				double reportedAmount = calculateReportedAmountForBudget(previousBudget.getId(),year);
		  				
		  				AnnualBudget budget = new AnnualBudget();
				  		budget.setExercise(exercise);
				  		
				  		budget.setParent(previousBudget);			  		
				  		budget.setContract(contractLive);
				  		
				  		//Set the budget properties and mark the budget as transfered
				  		budget.setStatus(AnnualBudget.STATUS_TRANSFERED);
				  		budget.setReportedAmount(reportedAmount);	  		
				  		budget.setContract(contract);
				  		
				  		//Set the associe and manager
				  		budget.setBudgetManager(contractLive.getCustomer().getCustomerManager());
				  		budget.setAssocieSignataire(contractLive.getCustomer().getAssocieSignataire());
				  		
				  		//Add a comment
				  		if(reportedAmount > 0){
				  			budget.setComments(reportedAmount +" from previous " + ( exercise.getYear()-1) );
				  		}
				  		
				  		totalExpectedAmount += budget.getExpectedAmount();
				  		totalReportedAmount += budget.getReportedAmount();
				    			
				  		//Add the budget to the list of budgets for the new exercise
				  		//exercise.getBudgets().add(budget);
				  		//Save the budget		  	
					  	em.persist(budget);
					  	
					  	Mission parentMission = previousBudget.getMission();
				  		Mission mission = budgetDao.createMission(budget.getId(), parentMission,mandat);
				  		mission.setStatus(Mission.STATUS_TRANSFERED);
				  		mission = em.merge(mission);
				  		
				  		budget.setMission(mission);
				  		budget = em.merge(budget);
		  		
					}
				}
				
				return true;
			
		}
		catch(Exception e){
			return false;
		}
		
		
		
	}
		
	
	
	
	
	  /**
	   * Returns a Exercise object identified by given id.
	   * 
	   * @return Returns a new Exercise object built from the current valid contracts and existing exercice if any.
	   */
	  public Exercise buildExercise(java.io.OutputStream out) throws BusinessException{
		try{
			
			 Number result = null;
		  	 int year = 0;
		  	 int nextYear = 0;
		  	 Exercise exercise  = null; 
		  	 int countExercise = 0;
		  	 
		  	 try{	 
		  		result = (Number) em.createQuery("select count(*) from Exercise e").getSingleResult();
		  	 }
		  	 catch(javax.persistence.NoResultException e){	
		  		result = null; 
		  	 }
		  	 
			 if ( result == null) {
				 countExercise = 0;
			 } else {
				 countExercise = result.intValue();
			 }
		  	 
		  	 if(countExercise == 0){
		  		 Calendar c = Calendar.getInstance();		  		 		  		
		  		nextYear = c.get(Calendar.YEAR); 
		  		exercise  = new Exercise(nextYear);
		  	 }else{
		  		//Create a new exercise from an existing one		      			      	 
		         List<Integer> resultList = em.createQuery("select max(e.year) from Exercise e").getResultList();
		         if (resultList.size() > 0) {
		        	 year = resultList.get(0);
		         }
		         
		      	 nextYear = year + 1;
		      	 exercise  = new Exercise(nextYear);
		  	 }
		  	 
		  	 logger.info("Save exercise " + nextYear);
		  	 //Save the exercise		  	
			 em.persist(exercise);
		  	 
			 logger.info("Starting building exercise " + nextYear);		  	 
		  	 
		  	 //List of annual budget of the previous Exercise
		  	@SuppressWarnings("unchecked")
			List<AnnualBudget> previousBudgets = em.createQuery("select b from AnnualBudget b  where  b.exercise.year = :year").setParameter("year", year).getResultList();
		  	if( previousBudgets == null ||  previousBudgets.size()==0){
		  		 logger.error("No budgets found for previous exercise "+ year);
		 		 throw new BusinessException(new ExceptionMessage("No budgets found for previous exercise "+ year) ) ;
		 	}
		  	
		  	double totalExpectedAmount =0;
		  	double totalReportedAmount =0;

		  	//Treat each valid budget within previous exercise   
		  	for(AnnualBudget previousBudget : previousBudgets){
		  		String mandat = previousBudget.getMission().getExercise();
		  		String info="Processing customer :  " + previousBudget.getContract().getCustomer().getCompanyName() +" Exercice : "+year+ " Mandat : "+ mandat;
		  		logger.info(info);
		  		
		  		boolean resultTransfer = transferAnnualBudget ( previousBudget, exercise, totalExpectedAmount, totalReportedAmount);
		  		
		  		if(!resultTransfer){
		  			 String error = "Fail transferring customer :  " + previousBudget.getContract().getCustomer().getCompanyName() +"for  exercice : "+year+ " Mandat : "+ mandat;
		  			 logger.error(error);
			 		 throw new BusinessException(new ExceptionMessage(error) ) ;	
		  		}
		  		else{
		  			 String success = "Success transferring customer :  " + previousBudget.getContract().getCustomer().getCompanyName() +"for  exercice : "+year+ " Mandat : "+ mandat;
		  			 logger.error(success);
		  		}	
		  	}
		  	
		  //Treat invalid contracts
		  	//Retrieve the past contracts   
	        List<Contract> invalidContracts = em.createQuery("select c from Contract c  where c.valid= :valid and  c.toDate < :referenceDate ").setParameter("valid",Boolean.TRUE).setParameter("referenceDate",new Date()).getResultList();	        
		  	if( invalidContracts != null &&  invalidContracts.size()>0){
		  		
		  		for(Contract contract : invalidContracts){
		  			contract.setValid(false);
		  			em.merge(contract);
		  		}
		  		 
		 	}
		  	
		  	//Treat each valid contract
		  	//Retrieve the valid contract   
	        List<Contract> validContracts = em.createQuery("select c from Contract c  where c.valid= :valid and c.interim =:interim and  c.toDate >= :referenceDate ").setParameter("interim",Boolean.FALSE).setParameter("valid",Boolean.TRUE).setParameter("referenceDate",new Date()).getResultList();	        
		  	if( validContracts == null ||  validContracts.size()==0){
		  		 logger.error("No active contracts found while building exercise "+ nextYear);
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : No active contracts found...") ) ;
		 	}
		  	
		  	for(Contract contract : validContracts){
		  		
		  		Customer customer = contract.getCustomer();
		  		
		  		if(customer.isActive()){
		  			logger.info("Processing contract " + contract.getReference());
			  		
			  		AnnualBudget budget = new AnnualBudget();
			  		budget.setExercise(exercise);
			  		Contract contractLive = (Contract) em.find(Contract.class, contract.getId());
			  		budget.setContract(contractLive);
			  		double reportedAmount = 0d;
			  		
			  		//Set the budget properties
			  		budget.setParent(null);	
			  		budget.setStatus(AnnualBudget.STATUS_PENDING);
			  		budget.setReportedAmount(reportedAmount);		  		
			  		budget.setExpectedAmount(formatDouble(contractLive.getAmount()));
			  		budget.setContract(contract);
			  		
			  		//Set the associe and manager
			  		budget.setBudgetManager(contractLive.getCustomer().getCustomerManager());
			  		budget.setAssocieSignataire(contractLive.getCustomer().getAssocieSignataire());
			  		
			  		//Add a comment
			  		if(reportedAmount > 0){
			  			budget.setComments(reportedAmount +" from previous " + year );
			  		}
			  		
			  		totalExpectedAmount += budget.getExpectedAmount();
			  		totalReportedAmount += budget.getReportedAmount();
			    					  		
			  		//Save the budget		  	
				  	em.persist(budget);
				  	//String mandat = Integer.toString(nextYear);
				  	String mandat=null;
				  	
				  	StringBuffer bufferQuery = new StringBuffer("select max(m.exercise) from interaudit.missions m, interaudit.budgets b, interaudit.contracts c ,interaudit.exercises e");
				  	bufferQuery.append(" where m.annualbudget_id = b.id and ");
					bufferQuery.append(" b.fk_exercise = e.id and b.fk_contract = c.id and ");
					bufferQuery.append(" e.year = " + year + " and c.id =" + contract.getId());
				  	
					Query query =  em.createNativeQuery(bufferQuery.toString());
					
					String temp = (String)query.getSingleResult();
					
					Integer resultat = null;
					
					try{
						resultat = Integer.parseInt(temp);
					}
					catch(NumberFormatException e){
						resultat = nextYear;
					}
				
					mandat = Integer.toString(resultat+1);
				  	
				  	Mission mission = budgetDao.createMission(budget.getId(), null,mandat);
			  		budget.setMission(mission);
			  		budget = em.merge(budget);
		  		}
		  		
		  		
		  	}
		  	
		  	//Update the exercise total expected amount
		  	exercise.setTotalExpectedAmount(formatDouble(totalExpectedAmount));
		  	exercise.setTotalReportedAmount(formatDouble(totalReportedAmount));
		  
		  	
		  	//Save the exercise		  			  
		  	em.merge(exercise);
		  	
		  	//Create a new planning 
		  	PlanningAnnuel planning = new PlanningAnnuel(exercise.getYear());
		  	em.persist(planning);
		  	
		  	logger.info("Finished building exercise " + nextYear);
		  	
		  	//Return a detached version 
		  	String queryString = "select e from Exercise e where e.year = :year";
 	       
            Exercise resultingExercise = (Exercise)em.createQuery(queryString).setParameter("year",exercise.getYear()).getSingleResult();
            return resultingExercise;
           
		  }catch(DaoException daoe){	
			  logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to build the exercise...") ) ;		 
		  }	catch(BusinessException e){
		  		throw e;
		  	}catch(Exception e){	 
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to build the exercise...") ) ;		 
		  }
		  finally{
				em.close();
		  }  	
	  }
	
	
	 /**
	   * Returns a Exercise object identified by given id.
	   * This method is supposed to create a mission object for each budget line
	   * All non terminated activities will be reported in the next exercise and set to TRANSFERED state in the previous 
	   * @return Returns  Exercise object  .
	   */
	  public Exercise approveExercise(Long idExercise) throws BusinessException{
		 
		  try{
			  
			  
			//Check if any exercise are still in status ongoing--This must be done at approbation time
			 boolean hasOngoingExercise = false;
			 Number result = null;
			 try
			 {
			  //result = (Number) em.createQuery("select count(*) from Exercise e where e.status = :pending or e.status = :ongoing").setParameter("pending","PENDING").setParameter("ongoing","ONGOING").getSingleResult();			 
			  result = (Number) em.createQuery("select count(*) from Exercise e where e.status = :ongoing").setParameter("ongoing","ONGOING").getSingleResult();
			 }
			 catch(javax.persistence.NoResultException e){	
	  			result = null;			 
			 }
  		
		     if ( result == null) {
		    	hasOngoingExercise = false;
		     } else {
		    	hasOngoingExercise = result.intValue() > 0;
		     }
			    
		  	 if( hasOngoingExercise ){
		  		 logger.error("Please close any ongoing exercise before going further...");
		  		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Please close any ongoing exercise before going further...") ) ;
		  	 }
			  	 
			  
			  //Retrieve the current exercise
			  Exercise currentExercise = em.find(Exercise.class, idExercise); 
			  if(currentExercise == null)  throw new BusinessException(new ExceptionMessage("ExerciseDao : No matching exercise found for identifier : " + idExercise));
			  
			  for(AnnualBudget budget : currentExercise.getBudgets()){
				  //We skip the cancelled,closed and non-fiable budgets
				  if( budget.isFiable() && !budget.getStatus().equalsIgnoreCase(AnnualBudget.STATUS_CANCELLED ) &&! budget.getStatus().equalsIgnoreCase(AnnualBudget.STATUS_CLOSED )){
					  
					   //Set the status of the budget at ONGOING
					   budget.setStatus(AnnualBudget.STATUS_ONGOING);
					   em.merge(budget);
					   
					   //Set the status of the mission at ONGOING
					   Mission mission = budget.getMission();
					   mission.setStatus(Mission.STATUS_ONGOING);
					   em.merge(mission);
				  }
			  }
			
			  //Update the current exercise
			  currentExercise.setAppproved(true);
			  currentExercise.setStatus(Exercise.STATUS_ONGOING);
			  currentExercise = em.merge(currentExercise);
			  return currentExercise;
			  
		  }catch(DaoException daoe){	
			     logger.error("Failed to approve the budget..." + daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to approve the budget...") ) ;
			 
		  }	catch(BusinessException e){
		  		throw e;
		  	}
		  catch(Exception e){	
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to approve the exercise...") ) ;		 
		  }finally{
				em.close();
		  }
		  
	  }
	  
	  
	  /**
	   * @param year
	   * @return
	   * @throws BusinessException
	   */
	  public Exercise recalculateExercise(int year) throws BusinessException{
		  try{
			  
		  	   //To do : Add pre-conditions here regarding the state of the exercise		  	  
		  	   Exercise exercise = null;
		  	 
	   	       
	            List<Exercise> resultList = em.createQuery("select e from Exercise e where e.year = :year" ).setParameter("year",year).getResultList();
	            if (resultList.size() > 0) {
	            	exercise = resultList.get(0);
	            }
	            
	            if(exercise == null){
	            	throw new BusinessException(new ExceptionMessage("ExerciseDao :  Failed to apply Inflation on the budget...") ) ;	
	            }
		  	  	
		  	   //Calculate the amount for the exercise
		  	   double totalExpectedAmount = 0.0d;
		  	   double totalReportedAmount = 0.0d;
		  	   double totalInactifAmount = 0.0d;
		  	   double totalEffectiveAmount = 0.0d;
		  	 
		  	   for(AnnualBudget budget : exercise.getBudgets()){		   
		  		   if(budget.isFiable() == true){
		  			   totalExpectedAmount += budget.getExpectedAmount();
		  			   totalReportedAmount += budget.getReportedAmount();
		  			   totalEffectiveAmount += budget.getEffectiveAmount();
		  		   }else{
		  			   totalInactifAmount += budget.getExpectedAmount() + budget.getReportedAmount();
		  		   }
		  	   }
		  	   
		  	   exercise.setTotalExpectedAmount(formatDouble(totalExpectedAmount));
		  	   exercise.setTotalReportedAmount(formatDouble(totalReportedAmount));
		  	   exercise.setTotalInactifAmount(formatDouble(totalInactifAmount));
		  	   exercise.setTotalEffectiveAmount(formatDouble(totalEffectiveAmount));
		  	 		  	  
		  	   //Update the exercise		  	  
		  	   exercise = em.merge(exercise);
		  	  
		  	    //Return a detached version of the exercise		  
               resultList = em.createQuery("select e from Exercise e where e.year = :year ").setParameter("year",year).getResultList();
	            if (resultList.size() > 0) {
	                return resultList.get(0);
	            }else{
	            	return null;
	            }	
		  	  
		  }	catch(BusinessException e){
		  		throw e;
		  	}catch(Exception daoe){		
			  		logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to approve the budget...") ) ;		 
		  }finally{
				em.close();
		  } 	  	  
	    }

	  	public Exercise applyInflation( float inflationPercentage,Long idExercise) throws BusinessException{
	  		
	  		try{
	  			Integer year = null;
	  			Exercise exercise = null;
		  		//To do : Add pre-conditions here regarding the state of the exercise
	  			if(idExercise == null){
	  				year = this.getFirstOnGoingExercise();		  		
			        if(year == null){
			            	throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to apply Inflation on the budget...") ) ;	
			        }
			        
			        exercise = (Exercise) em.createQuery("select e from Exercise e where e.year = :year" ).setParameter("year",year).getSingleResult();
		            if(exercise == null){
		            	throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to apply Inflation on the budget...") ) ;	
		            }
	  			}
	  			else{	  				
	  				exercise = em.find(Exercise.class, idExercise);
	  				year = exercise.getYear();	  				
	  			}
		  		
		        
   	       
		  	  	
		  	  	
		  	  	//Calculate the amount for the exercise
		  	   double totalExpectedAmount = 0.0d;
		  	   double totalReportedAmount = 0.0d;
		  	   double totalInactifAmount = 0.0d;
		  	   for(AnnualBudget budget : exercise.getBudgets()){		   
		  		   if(budget.isFiable() == true){
		  			   budget.setExpectedAmount(budget.getExpectedAmount()  * ( 1 + (inflationPercentage/100)));
		  			   budget.setReportedAmount(budget.getReportedAmount()  * ( 1 + (inflationPercentage/100)));
		  			   totalExpectedAmount += budget.getExpectedAmount();
		  			   totalReportedAmount += budget.getReportedAmount();			   
		  		   }
		  		   else{
		  			   totalInactifAmount += budget.getExpectedAmount() + budget.getReportedAmount();
		  		   }
		  	   }
		  	   
		  	   exercise.setTotalExpectedAmount(formatDouble(totalExpectedAmount));
		  	   exercise.setTotalReportedAmount(formatDouble(totalReportedAmount));
		  	   exercise.setTotalInactifAmount(formatDouble(totalInactifAmount));
		  	  
		  	   //Update the exercise		  	  
		  	   exercise = em.merge(exercise);
		  	  
		  	    //Return a detached version of the exercise		  
		  	   exercise = (Exercise)  em.createQuery("select e from Exercise e where e.year = :year ").setParameter("year",year).getSingleResult();
	           return 	exercise;  	  
				  
			  }	catch(BusinessException e){
			  		throw e;
			  	}catch(Exception daoe){	
				  logger.error(daoe.getMessage());
			 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to apply Inflation on the budget...") ) ;		 
			  }
			  finally{
					em.close();
			  }
	  		
	  	}
	  	
	  	
	  	 /**
	     * Returns a Exercise object identified by given id.
	     * This method will set all non terminated activities to BE TRANSFERED state , close all related mission and budget line and finally close the root exercise object
	     * @return Returns a  Exercise object .
	     */
	    public Exercise closeExercise(Long idExercise) throws BusinessException{
	  	  try{
	  	 
		  	 //Retrieve the current exercise
		  	 Exercise exercise = em.find(Exercise.class, idExercise);
		  	 if(exercise == null)  throw new BusinessException(new ExceptionMessage("No matching exercise found for identifier : " + idExercise));
		  	 
		  	 //We check that the current exercise is active
		  	 if(!exercise.getStatus().equalsIgnoreCase(Exercise.STATUS_ONGOING)){
				throw new BusinessException(new ExceptionMessage("Cannot close this exercise. Only ONGOING Exercise can be closed...") ) ;
			 }
		  	 
		  	 //We must check that the next exercise is already generated before going further
		  	 Exercise nextExercise = getOneFromYear(exercise.getYear()+1);
		  	 int nextYearValue = exercise.getYear()+1;
		  	 if(nextExercise == null)  throw new BusinessException(new ExceptionMessage("No exercise found for  year :: [  " + nextYearValue +"] ...Please generate a new exercise in order to proceed further..."));
		  	 
		  	//We must check that no mission in status ONGOING and having a final bill exist in the current exercise
		  	 /*
		  	 boolean result = existOngoingBudgetWithFinalBillForExercise(exercise.getYear());
		  	if(result == true)  throw new BusinessException(new ExceptionMessage("There are still missions in status ONGOING having a final bill for the exercise : [ " + exercise.getYear()+" ] ...Please close them in order to proceed further..."));
			*/
		  	  //We found an exercise, let's close all related budget , missions and activities 		  	  
		  	  for(AnnualBudget budget : exercise.getBudgets()){
		  		 
		  		if( budget.isFiable() && !budget.getStatus().equalsIgnoreCase(AnnualBudget.STATUS_CANCELLED ) &&! budget.getStatus().equalsIgnoreCase(AnnualBudget.STATUS_CLOSED )){
		  				
			  		
			  		//Set the status of the mission as CLOSED
					Mission mission = em.find(Mission.class, budget.getMission().getId());
					mission.setStatus(Mission.STATUS_TRANSFERED);
					mission = em.merge(mission); 	
					
					//Close the budget
			  		budget.setStatus(AnnualBudget.STATUS_TRANSFERED);
			  		budget = em.merge(budget); 
		  		}
		  				  
		  	  }
		  	  
		  	  //Finally we close the exercise
		  	  exercise.setStatus(Exercise.STATUS_CLOSED);
		  	  return em.merge(exercise);
	  	  
	    }
	  	catch(BusinessException e){
	  		throw e;
	  	}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to close the exercise...") ) ;	
	        
		}finally{
			em.close();
		}
	  	   
	  	 
	 }
	    
	    public com.interaudit.domain.model.Exercise referenceBudgetPage(Long idExercise) throws BusinessException{
	    	
	    	try{
	   	  	 
			  	  //Retrieve the current exercise
			  	  Exercise exercise = em.find(Exercise.class, idExercise);
			  	
			  	  if(exercise == null)  throw new BusinessException(new ExceptionMessage("ExerciseDao : No matching exercise found for identifier : " + idExercise));
		
			  	  //We found an exercise, let's close all related budget , missions and activities 			  	  
			  	  for(AnnualBudget budget : exercise.getBudgets()){
			  		  
			  		  	budget.setExpectedAmountRef(budget.getExpectedAmount());
			  		 	budget.setReportedAmountRef(budget.getReportedAmount());
				  		budget = em.merge(budget); 	  		  
			  	  }
			  	  
			  	  //Finally we close the exercise
			  	  exercise.setTotalExpectedAmountRef(exercise.getTotalExpectedAmount());
			  	  exercise.setTotalReportedAmountRef(exercise.getTotalReportedAmount());
			  	  exercise.setTaggedDate(new Date());
			  	  return em.merge(exercise);
		  	  
		    }
	    	catch(BusinessException e){
		  		throw e;
		  	}
			catch(Exception e){
				logger.error(e.getMessage());	
				throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : referenceBudgetPage..."));
		        
			}finally{
				em.close();
			}
	    	
	    }
	    
	
	
    public void deleteOne(Long id) {
				
		try{
			
			Exercise exercise =em.find(Exercise.class, id);
	        if (exercise == null) {
	            throw new DaoException(2);
	        }
	        em.remove(exercise);     
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : deleteOne..."));
	        
		}finally{
			em.close();
		}

		
		   
    }
	
	
    @SuppressWarnings("unchecked")
    public List<Exercise> getExercises(List<Integer> years){
		
		try{
			return em.createQuery("select  e from Exercise e where e.year in (:years) order by e.year asc").setParameter("years",years).getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : getExercises..."));

		}finally{
			em.close();
		}
		
		
	}
	
	
    @SuppressWarnings("unchecked")
    public List<Integer> getExercisesAsInteger(){
		
		try{
			return em.createQuery("select  e.year from Exercise e order by e.year asc").getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : getExercisesAsInteger..."));

		}finally{
			em.close();
		}
		
		
	}

	
    @SuppressWarnings("unchecked")
    public List<Exercise> getAll() {
		
		try{
			return em.createQuery("select distinct e from Exercise e order by e.year asc").getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : getAll..."));

		}finally{
			em.close();
		}
		
        
    }
	
	
	public int getCountOfOngoingExercises() {
		
		try{
			
			Number result = (Number) em
            .createQuery("select count(*) from Exercise e where e.status = :ongoing").setParameter("ongoing","ONGOING").getSingleResult();
		    if ( result == null) {
		        return 0;
		    } else {
		        return result.intValue();
		    }

		}
		catch(javax.persistence.NoResultException e){	
  			return 0;			 
		 }
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : getCountOfOngoingExercises..."));

		}finally{
			em.close();
		}
   
    }
	
	public int getCountOfPendingExercises() {
		
		try{
			
			Number result = (Number) em
            .createQuery("select count(*) from Exercise e where e.status = :pending ").setParameter("pending","PENDING").getSingleResult();
		    if ( result == null) {
		        return 0;
		    } else {
		        return result.intValue();
		    }

		}
		catch(javax.persistence.NoResultException e){	
  			return 0;			 
		 }
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : getCountOfOngoingExercises..."));

		}finally{
			em.close();
		}
   
    }
	
	 public Integer getFirstOnGoingExercise() {
	    	
	    	try{
	    		
	    		String queryString = "select e.year from Exercise e where e.status = :ongoing ";
	            
	            List<Integer> resultList = em.createQuery(queryString).setParameter("ongoing","ONGOING").getResultList();
	            if (resultList.size() > 0) {
	                return resultList.get(0);
	            }
	            return null;
	    		
			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : getOne..."));

			}finally{
				em.close();
			}
			
	        
	    }
	 
	 public Integer getLastClosedExercise(){
		 try{
	    		
	    		String queryString = "select max (e.year) from Exercise e where e.status = :closed ";
	            
	            List<Integer> resultList = em.createQuery(queryString).setParameter("closed","CLOSED").getResultList();
	            if (resultList.size() > 0) {
	                return resultList.get(0);
	            }
	            return null;
	    		
			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : getOne..."));

			}finally{
				em.close();
			}
	 }
	
	 public int countExercises(){
		 
		 try{
			 
			 Number result = (Number) em
	         .createQuery("select count(*) from Exercise e").getSingleResult();
			 if ( result == null) {
			     return 0;
			 } else {
			     return result.intValue();
			 }

			}
		 	catch(javax.persistence.NoResultException e){	
	  			return 0;			 
			 }
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : countExercises..."));

			}finally{
				em.close();
			}
			
		 
	 }
	
	 /**
     * 
     * @return AnnualBudget objects list representing all AnnualBudget known by IAMS
     *         application.
     
	
	public List<AnnualBudgetView> getBudgetsForSelectedExercises( SearchBudgetParam param){
		return findBudgets( param);
	}
	
	
	
	public List<AnnualBudgetView> findBudgets(SearchBudgetParam param) {
	        
		 	Map<String, Object> parameters = new HashMap<String, Object>();
	        StringBuilder hql = new StringBuilder("select new com.interaudit.domain.model.data.AnnualBudgetView(b.id,e.year, c.companyName, c.code, b.expectedAmount,b.effectiveAmount,m.reference,b.status, c.origin.name, c.associeSignataire.code, c.customerManager.code)  from AnnualBudget b join b.exercise e  join b.contract.customer c left join b.mission m  ");
	        StringBuilder whereClause = new StringBuilder("");
	        
	        //Rechercher les années
	        if (param.getYears()!= null) {
	            parameters.put("years", param.getYears());
	            whereClause.append("(e.year in (:years))");
	        }
	        
	        //Rechercher le status
	        if (param.getStatus() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("status", param.getStatus() +"%");
	            whereClause.append("b.status like :status");
	        }
	        
	      //Rechercher l'origin
	        if (param.getStatus() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("status", param.getStatus() +"%");
	            whereClause.append("b.status like :status");
	        }
	        
	      //Rechercher le manager
	        if (param.getManager() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("manager", "%" + param.getManager() +"%");
	            whereClause.append("c.customerManager.lastName like :manager");
	        }
	        
	        
	        
	        if (whereClause.length() > 0) {
	            hql.append(" WHERE ").append(whereClause);
	        }
	        hql.append(" ORDER BY c.code,e.year ");
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	    
	        return q.getResultList();
	    }
	    */

	 /**
     * @return the exercise having the max year
     */
	 @SuppressWarnings("unchecked")
	
	 public Integer getMaxYear(){
		 
		 try{
			 
			 String queryString = "select max(e.year) from Exercise e";
		       
	         List<Integer> resultList = em.createQuery(queryString).getResultList();
	         if (resultList.size() > 0) {
	             return resultList.get(0);
	         }
	         return null;

			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : getMaxYear..."));

			}finally{
				em.close();
			}
			
		 
	 }

    
    
    public Exercise getOne(Long id) {
    	
    	try{
    		return em.find(Exercise.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : getOne..."));

		}finally{
			em.close();
		}
		
        
    }
    
    @SuppressWarnings("unchecked")
	
    public Exercise getOneDetached(Long id){
    	
    	try{
    		

       	 String queryString = "select e from Exercise e where e.id = :id ";
            
            List<Exercise> resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
            if (resultList.size() > 0) {
                return resultList.get(0);
            }
            return null;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : getOneDetached..."));

		}finally{
			em.close();
		}
		
    }
    
    @SuppressWarnings("unchecked")
	public Exercise getOneFromYear(int year){
    	
    	try{
    		
    		String queryString = "select e from Exercise e where e.year = :year ";
    	       
            List<Exercise> resultList = em.createQuery(queryString).setParameter("year",year).getResultList();
            if (resultList.size() > 0) {
                return resultList.get(0);
            }
            return null;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : getOneFromYear..."));

		}finally{
			em.close();
		}
    	
    	 
    }

   
    
    public Exercise saveOne(Exercise exercise) {
    	//EntityTransaction tx = em.getTransaction();
    	try{
    		//tx.begin();
    		em.persist(exercise);
    		//tx.commit();
            return exercise;
    	}catch(EntityExistsException ex){
    		logger.error(ex.getMessage());
    		throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : saveOne..."));
    	}
    	catch(IllegalStateException ilx){
    		logger.error(ilx.getMessage());
    		throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : saveOne..."));
    	}
		catch(IllegalArgumentException iax){
			logger.error(iax.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : saveOne..."));
		}
		catch(TransactionRequiredException tx2){
			logger.error(tx2.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ExerciseDao : saveOne..."));
		}finally{
			em.close();
		}
	
    	
    }

    
    public Exercise updateOne(Exercise exercise) {    	
		try{			
			Exercise ret = em.merge(exercise);	 
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return null;	        
		}finally{
			em.close();
		}
        
    }
    
    public AnnualResult updateOne(AnnualResult result){
    	try{			
    		AnnualResult ret = em.merge(result);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return null;
	        
		}finally{
			em.close();
		}
    }
    
    
    
    @SuppressWarnings("unchecked")
	public AnnualResult getOneDetached(Long exerciseId, Long responsableIdentifier){
    	
    	try{    	
          	   String queryString = "select r from AnnualResult r where r.exercise.id = :exerciseId and r.responsable.id = :responsableId ";               
               List<AnnualResult> resultList = em.createQuery(queryString).setParameter("exerciseId",exerciseId).setParameter("responsableId",responsableIdentifier).getResultList();
               if (resultList.size() > 0) {
                   return resultList.get(0);
               }
               return null;

   		}
   		catch(Exception e){
   			logger.error(e.getMessage());
   	        	return null;

   		}finally{
   			em.close();
   		}
    }


	public IBudgetDao getBudgetDao() {
		return budgetDao;
	}


	public void setBudgetDao(IBudgetDao budgetDao) {
		this.budgetDao = budgetDao;
	}
    
   

    
}
