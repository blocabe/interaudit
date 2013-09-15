package com.interaudit.domain.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IContractDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.MissionTypeTaskLink;
import com.interaudit.domain.model.data.ContractData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchContractParam;

public class ContractDao  implements IContractDao {

    private static final String UNCHECKED = "unchecked";
	private static final String STRING = UNCHECKED;
	
	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(ContractDao.class);
	
	 public void updateInvalideContracts(){
		 try{		
			 List<Contract> invalidContracts = em.createQuery("select c from Contract c  where c.valid= :valid and  c.toDate < :referenceDate ").setParameter("valid",Boolean.TRUE).setParameter("referenceDate",new Date()).getResultList();	        
			  	if( invalidContracts != null &&  invalidContracts.size()>0){
			  		
			  		for(Contract contract : invalidContracts){
			  			contract.setValid(false);
			  			em.merge(contract);
			  		}
			  		 
			 	}	 
			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  deleteOne..."));
		        
			}finally{
				em.close();
			}
			 
	 }
	
	
	
    public int deleteOne(Long id) {
		
		try{		
			Object Contract = null;
	    	try {
	    		Contract = em.find(Contract.class, id);
	    	} catch(EntityNotFoundException e) {}
	    	if(Contract == null) {
	    		return 0;
	    	} else {	    		
	    		em.remove(Contract);	    		
	            return 1;
	    	}		 
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  deleteOne..."));
	        
		}finally{
			em.close();
		}
		
    	
    }
	
	@SuppressWarnings(STRING)
	public Contract getOneDetached(Long id){
    	try{
        	String queryString = "select c from Contract c  where c.id = :id";
            List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
            if (resultList.size() > 0) {
                return (Contract) resultList.get(0);
            }
            return null;
        	}catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  getOneDetached..."));
    	        
    		}finally{
        		em.close();
        	}
    }
	
	public int getNextContractSequence(Long idCustomer) {
		
		try{
			Number result = null;			 
			try{	 
				result = (Number) em
	            .createQuery(
	                    "select count(*) from Contract c where c.customer.id = :idCustomer")
	            .setParameter("idCustomer", idCustomer).getSingleResult();
			}
			catch(javax.persistence.NoResultException e){	
				result = null;
			}
			
		    if ( result.intValue() == 0) {
		        return 1;
		    } else {
		        return result.intValue() + 1;
		    }

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  getNextContractSequence..."));

		}finally{
			em.close();
		}
		
		
        
    }
	
    public boolean existContractForCustomerAndType(Long customerId, String type){
    	try{
			Number result = null;			 
			try{	 
				result = (Number) em
	            .createQuery(
	                    "select count(*) from Contract c where c.customer.id = :idCustomer and c.missionType = :type")
	            .setParameter("idCustomer", customerId).setParameter("type", type).getSingleResult();
			}
			catch(javax.persistence.NoResultException e){	
				result = null;
			}
			
		    if ( result.intValue() == 0) {
		        return false;
		    } else {
		        return true;
		    }

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  existContractForCustomerAndType..."));

		}finally{
			em.close();
		}
    }
	
	 public List<Option> getMissionTypeOptions(){
		 try{
				 List<Option> missionOptions = new ArrayList<Option>();
				 List<MissionTypeTaskLink> resultList = em.createQuery("select c from MissionTypeTaskLink c order by c.missionTypeCode").getResultList();
				 if (resultList.size() > 0) {
			            
					 for(MissionTypeTaskLink missionTypeTaskLink : resultList){
						 missionOptions.add(new Option(missionTypeTaskLink.getMissionTypeCode(),missionTypeTaskLink.getLibelle())); 
					 }
			     }
			     return missionOptions;
			}
			catch(Exception e){		
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  getMissionTypeOptions..."));
			}finally{
				 em.close();
			} 
	 }
	 
	 public List<Option> getMissionTypeOptions2(){
		 try{
				 List<Option> missionOptions = new ArrayList<Option>();
				 List<MissionTypeTaskLink> resultList = em.createQuery("select c from MissionTypeTaskLink c order by c.missionTypeCode").getResultList();
				 if (resultList.size() > 0) {
			            
					 for(MissionTypeTaskLink missionTypeTaskLink : resultList){
						 missionOptions.add(new Option(missionTypeTaskLink.getId().toString(),missionTypeTaskLink.getLibelle())); 
					 }
			     }
			     return missionOptions;
			}
			catch(Exception e){		
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  getMissionTypeOptions2..."));
			}finally{
				 em.close();
			} 
	 }
	
	@SuppressWarnings(STRING)
	public Contract getOneFromCode(String reference){
		
		try{
			
			String queryString = "select c from Contract c  where  UPPER(c.reference) = UPPER(:reference) ";
		       
	        List<Contract> resultList = em.createQuery(queryString).setParameter("reference",reference).getResultList();
	        if (resultList.size() > 0) {
	            return resultList.get(0);
	        }
	        return null;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  getOneFromCode..."));

		}finally{
			em.close();
		}
		
		
	}

    
    @SuppressWarnings(STRING)
    
    public List<Contract> getAll() {
    	
    	try{
    		return em.createQuery("select c from Contract c order by c.reference").getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  getAll..."));

		}finally{
			em.close();
		}
		
        
    }


    
    public Contract getOne(Long id) {
    	
    	try{
    		return (Contract) em.find(Contract.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  getOne..."));

		}finally{
			em.close();
		}
		
        
    }
    
    
    public MissionTypeTaskLink getOneMissionTypeTaskLink(Long id){
    	try{
    		return (MissionTypeTaskLink) em.find(MissionTypeTaskLink.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  getOneMissionTypeTaskLink..."));

		}finally{
			em.close();
		}
    }
    
    
    @SuppressWarnings(STRING)
    
    public List<Contract> findActiveContracts(){
    	
    	try{

    		String queryString = "select c from Contract c  where  c.toDate >= :referenceDate ";
 	       
            List<Contract> resultList = em.createQuery(queryString).setParameter("referenceDate",new Date()).getResultList();
            if (resultList.size() > 0) {
                return resultList;
            }
            return null;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  findActiveContracts..."));

		}finally{
			em.close();
		}
		
    	
    }

    
    public Contract saveOne(Contract contract) {    	
		try{			
			em.persist(contract);	        
	        return contract;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  saveOne..."));
	        
		}finally{
			em.close();
		}
		
    	
        
    }

    
    public Contract updateOne(Contract contract) {
    	
		try{
			 boolean result = updateRelatedMissionType(contract.getId(),contract.getMissionType());
			 if(result == true){
				 Contract ret = (Contract) em.merge(contract);	    
			     return ret;
			 }
			 else{
				 return null;
			 }
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  updateOne..."));
	        
		}finally{
			em.close();
		}
		
        
    }
    
    public boolean updateRelatedMissionType(Long idContrat,String missionType){
		try{
			
			//Deleting the activities for the target exercise
			StringBuffer updateRelatedMissionTypeQuery = new StringBuffer("update interaudit.missions set typ = '"+ missionType + "' where id in  ( select fk_mission from interaudit.budgets where fk_contract = "+ idContrat +")");		  	
			int result =  em.createNativeQuery(updateRelatedMissionTypeQuery.toString()).executeUpdate();
		
			 return true;
			}catch(DaoException daoe){	
			  logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to build the exercise...") ) ;		 
		  }catch(Exception e){	 
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("ExerciseDao : Failed to build the exercise...") ) ;		 
		  }
		  finally{
				em.close();
		  }  
	}


    
    public long getTotalCount(SearchContractParam param){
    	
    	try{
    		
    		Map<String, Object> parameters = new HashMap<String, Object>();
        	StringBuffer queryStringBuffer = new StringBuffer("select count(*) from Contract c join c.customer cust");
        	StringBuilder whereClause = new StringBuilder("");
        	

            //customer name like
            if (param.getExpression() != null && param.getExpression().trim().length() > 0) {
                
                parameters.put("searchField", "%" + param.getExpression() +"%");
                whereClause.append(" ( upper(cust.companyName) like upper(:searchField) ) ");
            }
            
          //Filtrer les clients commencant par customerStartString
	        if (param.getStartingLetterName() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerNameLike",   param.getStartingLetterName() +"%");
	            whereClause.append("( upper(c.customer.companyName) like upper(:customerNameLike) )");
	        }
            
            if (whereClause.length() > 0) {
            	queryStringBuffer.append(" WHERE ").append(whereClause);
            }
           // queryStringBuffer.append(" order by cust.companyName asc");
            Query q = em.createQuery(queryStringBuffer.toString());
            for (Map.Entry<String, Object> me : parameters.entrySet()) {
                q.setParameter(me.getKey(), me.getValue());
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
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  getTotalCount..."));

		}finally{
			em.close();
		}
		
    	
    }
    
    
    @SuppressWarnings(STRING)
    public List<Contract> findContracts(SearchContractParam param,int firstPos,int LINEPERPAGE) {
    	
    	try{
    		
    		Map<String, Object> parameters = new HashMap<String, Object>();
        	StringBuffer queryStringBuffer = new StringBuffer("select c from Contract c join c.customer cust");
        	StringBuilder whereClause = new StringBuilder("");
        	

            //customer name like
            if (param.getExpression() != null && param.getExpression().trim().length() > 0) {
                
                parameters.put("searchField", "%" + param.getExpression() +"%");
                whereClause.append(" ( upper(cust.companyName) like upper(:searchField) ) ");
            }
            
            //Filtrer les clients commencant par customerStartString
	        if (param.getStartingLetterName() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerNameLike",   param.getStartingLetterName() +"%");
	            whereClause.append("( upper(c.customer.companyName) like upper(:customerNameLike) )");
	        }
            
            if (whereClause.length() > 0) {
            	queryStringBuffer.append(" WHERE ").append(whereClause);
            }
            queryStringBuffer.append(" order by cust.companyName asc");
            Query q = em.createQuery(queryStringBuffer.toString());
            for (Map.Entry<String, Object> me : parameters.entrySet()) {
                q.setParameter(me.getKey(), me.getValue());
            }
            
            q.setFirstResult(firstPos);
            q.setMaxResults(LINEPERPAGE);
        
            return q.getResultList();

		}
		catch(Exception e){
			    logger.error(e.getMessage());
			    throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  findContracts..."));

		}finally{
			em.close();
		}
    	
    	
    }
    
    
    @SuppressWarnings(STRING)
    public List<ContractData> findContractsData(SearchContractParam param,int firstPos,int LINEPERPAGE) {
    	
    	try{
    		
    		
    		Map<String, Object> parameters = new HashMap<String, Object>();
        	StringBuffer queryStringBuffer = new StringBuffer("select new com.interaudit.domain.model.data.ContractData(c.id, c.reference, c.description,c.fromDate, c.toDate, c.language,c.amount,c.missionType, cust.companyName, c.agreed,c.valid,cust.active) from Contract c join c.customer cust");
        	StringBuilder whereClause = new StringBuilder("");
        	

            //customer name like
            if (param.getExpression() != null && param.getExpression().trim().length() > 0) {
                
                parameters.put("searchField", "%" + param.getExpression() +"%");
                whereClause.append(" ( upper(cust.companyName) like upper(:searchField) ) ");
            }
            
            //Filtrer les clients commencant par customerStartString
	        if (param.getStartingLetterName() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerNameLike",   param.getStartingLetterName() +"%");
	            whereClause.append("( upper(c.customer.companyName) like upper(:customerNameLike) )");
	        }
            
            if (whereClause.length() > 0) {
            	queryStringBuffer.append(" WHERE ").append(whereClause);
            }
            queryStringBuffer.append(" order by cust.companyName asc");
            Query q = em.createQuery(queryStringBuffer.toString());
            for (Map.Entry<String, Object> me : parameters.entrySet()) {
                q.setParameter(me.getKey(), me.getValue());
            }
            
            q.setFirstResult(firstPos);
            q.setMaxResults(LINEPERPAGE);
        
            return q.getResultList();

		}
		catch(Exception e){
			    logger.error(e.getMessage());
			    throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  findContracts..."));

		}finally{
			em.close();
		}
    	
    	
    }
    
//    
//    public void deleteAll(List<Contract> Contracts) {
//    	if(Contracts == null || Contracts.size() == 0) {
//    		return;
//    	}
//    	StringBuilder sb = new StringBuilder("delete from Contract c where c.id in (");
////    	StringBuilder sb1 = new StringBuilder("DELETE Contract c FROM JaspersUser ju JOIN ju.Contracts c WHERE c.id in (");
//    	int count = 0;
//    	for(Contract Contract : Contracts) {
//    		if(count++ > 0) {
//    			sb.append(",");
//    		}
//    		sb.append(":id_" + Contract.getId());
//    	}
//    	sb.append(")");
//        Query query = em.createQuery(sb.toString());
//    	for(Contract Contract : Contracts) {
//    		query.setParameter("id_" + Contract.getId(), Contract.getId());
//    	}
//    	
//        query.executeUpdate();
//    }
    
    
	public void deleteAll(List<Contract> Contracts) {
		for(int i=Contracts.size()-1; i>=0; i--) {
			Contract Contract = Contracts.get(i);
			if(Contract != null) {
				deleteOne(Contract.getId());
			}
		}
	}
    
    
    public void activateAll(List<Contract> Contracts) {
    	updateActivation(Contracts, true);
    }
    
    
    public void deactivateAll(List<Contract> Contracts) {
    	updateActivation(Contracts, false);
    }
    

    private void updateActivation(List<Contract> Contracts, boolean activation) {
    	
    	//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			
			if(Contracts == null || Contracts.size() == 0) {
	    		return;
	    	}
	    	StringBuilder sb = new StringBuilder("update Contract c set c.active=:activation where c.id in (");
	    	int count = 0;
	    	for(Contract Contract : Contracts) {
	    		if(count++ > 0) {
	    			sb.append(",");
	    		}
	    		sb.append(":id_" + Contract.getId());
	    	}
	    	sb.append(")");
	        Query query = em.createQuery(sb.toString());
	        query.setParameter("activation", activation);
	    	for(Contract Contract : Contracts) {
	    		query.setParameter("id_" + Contract.getId(), Contract.getId());
	    	}
	    	
	        query.executeUpdate();
				
	        //tx.commit();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContractDao :  updateActivation..."));
	        
		}finally{
			em.close();
		}
		
		
    	
    }
}
