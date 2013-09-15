package com.interaudit.domain.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.ICustomerDao;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.data.CustomerData;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchCustomerParam;

public class CustomerDao implements ICustomerDao {

	    
	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(CustomerDao.class);
	
	
    public int deleteOne(Long id) {
		
		try{			
			Object customer = null;
	    	try {
	    		customer = em.find(Customer.class, id);
	    	} catch(EntityNotFoundException e) {}
	    	if(customer == null) {
	    		return 0;
	    	} else {	    		
	    		em.remove(customer);	    		
	            return 1;
	    	}			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  deleteOne..."));
	        
		}finally{
			em.close();
		}
		
    	
    }

    
    @SuppressWarnings("unchecked")
    
    public List<Customer> getAll() {
    	
    	try{
    		return em.createQuery("select c from Customer c order by c.companyName")
            .getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  getAll..."));

		}finally{
			em.close();
		}
		
        
    }
    
    @SuppressWarnings("unchecked")
    
    public List<Customer> getAllByUserManagerId(Long userManagerId){
    	try{

    		return em
        	.createQuery("select c from Customer c order by c.companyName where c.customerManager.id = idManager")
        	.setParameter("idManager", userManagerId)
            .getResultList();	
		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new RuntimeException(e);

		}finally{
			em.close();
		}
		
    	
    }

    
    public Customer getOne(Long id) {
    	
    	try{
    		return (Customer) em.find(Customer.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);

		}finally{
			em.close();
		}
		
        
    }
    
    @SuppressWarnings("unchecked")
	public Customer getOneDetached(Long id){
    	try{
        	String queryString = "select distinct(c) from Customer c  left join fetch c.contacts contacts left join fetch c.contracts contracts where c.id = :id";
            List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
            if (resultList.size() > 0) {
                return (Customer) resultList.get(0);
            }
            return null;
        	}catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  getOneDetached..."));

    		}finally{
        		em.close();
        	}
    }
    
    /*
    @SuppressWarnings("unchecked")
	public Customer getOneCustomerDetachedFromMissionId(Long idMission){
    	try{
        	String queryString = "select c from Mission m join fetch m.annualBudget.contract.customer c where upper(m.id) = upper(:id)";
            List resultList = em.createQuery(queryString).setParameter("id",idMission).getResultList();
            if (resultList.size() > 0) {
                return (Customer) resultList.get(0);
            }
            return null;
            
        	}
    		catch(Exception e){
    			System.out.print(e);
    			return null;
    		}
    		finally{
        		em.close();
        	}
    }
    */
    
    @SuppressWarnings("unchecked")
	public Customer getOneCustomerDetachedFromMissionId(Long idMission){
    	
    	try{
    		String queryString = "select * from interaudit.customers where id = (select FK_CUSTOMER from interaudit.contracts where id = (select FK_CONTRACT from interaudit.BUDGETS where id = (select ANNUALBUDGET_ID from interaudit.MISSIONS where ID = :id)))";
    		List<Customer> resultList = (List<Customer>)em.createNativeQuery(queryString, com.interaudit.domain.model.Customer.class).setParameter("id",idMission).getResultList();
            if (resultList.size() > 0) {
                return (Customer) resultList.get(0);
            }
            return null;
            
        	}
    		catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  getOneCustomerDetachedFromMissionId..."));
    		}
    		finally{
        		em.close();
        	}
    }
    
    //select ID from customers where id = (select FK_CUSTOMER from contracts where id = (select FK_CONTRACT from BUDGETS where id = (select ANNUALBUDGET_ID from MISSIONS where ID = 43)))
    
    
    
    @SuppressWarnings("unchecked")
	public Customer getOneDetachedFromCompanyName(String companyName){
    	
    	try{
        	String queryString = "select c from Customer c  where upper(c.companyName) = upper(:companyName)";
            List resultList = em.createQuery(queryString).setParameter("companyName",companyName).getResultList();
            if (resultList.size() > 0) {
                return (Customer) resultList.get(0);
            }
            return null;
        	}catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  getOneDetachedFromCompanyName..."));
    		}finally{
        		em.close();
        	}
    }
    
    @SuppressWarnings("unchecked")
	public Customer getOneDetachedFromCompanyCode(String companyCode){
    	try{
        	String queryString = "select c from Customer c  where upper(c.code) = upper(:companyCode)";
            List resultList = em.createQuery(queryString).setParameter("companyCode",companyCode).getResultList();
            if (resultList.size() > 0) {
                return (Customer) resultList.get(0);
            }
            return null;
        	}catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  getOneDetachedFromCompanyCode..."));
    		}finally{
        		em.close();
        	}
    }
    
    // Get a customer by its code
    @SuppressWarnings("unchecked")
	
    public Customer getOneFromCode(String code){
    	
    	try{
    		
    		String queryString = "select c from Customer c  where upper(c.code) = upper(:code)";
            List resultList = em.createQuery(queryString)
            .setParameter("code",
                    code).getResultList();
            if (resultList.size() > 0) {
                return (Customer) resultList.get(0);
            }
            return null;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  getOneFromCode..."));

		}finally{
			em.close();
		}
		
    	
    }
    
    // Get a customer by its code
    @SuppressWarnings("unchecked")
	
    public List<Customer> getFromCode(String code){
    	
    	try{
    		
    		String queryString = "select c from Customer c  where upper(c.code) like upper(:code)";
            List resultList = em.createQuery(queryString)
            .setParameter("code",
            		"%" + code + "%" ).getResultList();
           
            return resultList;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  getFromCode..."));

		}finally{
			em.close();
		}
		
    	
    }
    
    public List<CustomerData> getCustomerDataFromCode(String code){
    	
    	
    	try{
    		
			StringBuffer queryStringBuffer = new StringBuffer("select new com.interaudit.domain.model.data.CustomerData(c.id,c.companyName,c.code,c.origin.name, c.associeSignataire.lastName,c.customerManager.lastName,c.phone,c.email,c.active,c.mainAddress, c.mainActivity)  from Customer c left join c.customerManager m  left join c.associeSignataire a where upper(c.code) like upper(:code)");
            List resultList = em.createQuery(queryStringBuffer.toString())
            .setParameter("code",
            		"%" + code + "%" ).getResultList();
           
            return resultList;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  getFromCode..."));

		}finally{
			em.close();
		}
    }


    
    public Customer saveOne(Customer customer) {    	
		try{			
			em.persist(customer);	     
	        return customer;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  saveOne..."));
	        
		}finally{
			em.close();
		}
    	
    }

    
    public Customer updateOne(Customer customer) {
    
		try{			
			Customer ret = (Customer) em.merge(customer);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  updateOne..."));
	        
		}finally{
			em.close();
		}
        
    }
    
 
    
    public long getTotalCount(SearchCustomerParam param){
    	
    	try{
    		
    		Map<String, Object> parameters = new HashMap<String, Object>();
        	StringBuffer queryStringBuffer = new StringBuffer("select count(*) from Customer c join c.customerManager m");
        	StringBuilder whereClause = new StringBuilder("");
        	
        	 //Manager id
            if (param.getManagerName()!= null && param.getManagerName().trim().length() > 0) {
                parameters.put("managerName", Long.parseLong(param.getManagerName()));
                whereClause.append("( m.id = :managerName)");
            }
            
            //name like
            if (param.getNameLike() != null && param.getNameLike().trim().length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                parameters.put("searchField", "%" + param.getNameLike() +"%");
                whereClause.append(" ( upper(c.companyName) like upper(:searchField) ) ");
            }
            
          //startingLetterName
            if (param.getStartingLetterName() != null && param.getStartingLetterName().trim().length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                parameters.put("startingLetterName", param.getNameLike() +"%");
                whereClause.append(" upper( c.companyName) like upper(:startingLetterName) ");
            }
            
            if (whereClause.length() > 0) {
            	queryStringBuffer.append(" WHERE ").append(whereClause);
            }
            //queryStringBuffer.append(" order by c.companyName asc");
            Query q = em.createQuery(queryStringBuffer.toString());
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
			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  getTotalCount..."));

		}finally{
			em.close();
		}
		
    	
    }
    
    @SuppressWarnings("unchecked")
    
    public List<Customer> searchCustomers(SearchCustomerParam param,int firstPos,int LINEPERPAGE){
    	
    	try{
    		
    		Map<String, Object> parameters = new HashMap<String, Object>();
        	StringBuffer queryStringBuffer = new StringBuffer("select c from Customer c left join c.customerManager m  left join c.associeSignataire a");
        	StringBuilder whereClause = new StringBuilder("");
        	
        	//Manager id
            if (param.getManagerName()!= null && param.getManagerName().trim().length() > 0) {
                parameters.put("managerName", Long.parseLong(param.getManagerName()));
                whereClause.append("( m.id = :managerName)");
            }
            
          //Associe id
            if (param.getAssocieName()!= null && param.getAssocieName().trim().length() > 0) {
            	 if (whereClause.length() > 0) {
                     whereClause.append(" AND ");
                 }
                parameters.put("associeName", Long.parseLong(param.getAssocieName()));
                whereClause.append("( a.id = :associeName)");
            }
            
            //name like
            if (param.getNameLike() != null && param.getNameLike().trim().length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                parameters.put("searchField", "%" + param.getNameLike() +"%");
                whereClause.append(" ( upper(c.companyName) like upper(:searchField) ) ");
            }
            
          //startingLetterName
            if (param.getStartingLetterName() != null && param.getStartingLetterName().trim().length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                parameters.put("startingLetterName", param.getStartingLetterName() +"%");
                whereClause.append(" upper( c.companyName) like upper(:startingLetterName) ");
            }
            
            if (whereClause.length() > 0) {
            	queryStringBuffer.append(" WHERE ").append(whereClause);
            }
            queryStringBuffer.append(" order by c.companyName asc");
            Query q = em.createQuery(queryStringBuffer.toString());
            for (Map.Entry<String, Object> me : parameters.entrySet()) {
                q.setParameter(me.getKey(), me.getValue());
               // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
            }
            
            q.setFirstResult(firstPos);
            q.setMaxResults(LINEPERPAGE);
        
            return q.getResultList();

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  searchCustomers..."));

		}finally{
			em.close();
		}
		
    	
    	
    }
    
    
    public List<CustomerData> searchCustomersData(SearchCustomerParam param,int firstPos,int LINEPERPAGE){
    	
    	try{
    		
    		Map<String, Object> parameters = new HashMap<String, Object>();
        	StringBuffer queryStringBuffer = new StringBuffer("select new com.interaudit.domain.model.data.CustomerData(c.id,c.companyName,c.code,c.origin.name, c.associeSignataire.lastName,c.customerManager.lastName,c.phone,c.email,c.active,c.mainAddress, c.mainActivity)  from Customer c left join c.customerManager m  left join c.associeSignataire a");
        	StringBuilder whereClause = new StringBuilder("");
        	
        	//Manager id
            if (param.getManagerName()!= null && param.getManagerName().trim().length() > 0) {
                parameters.put("managerName", Long.parseLong(param.getManagerName()));
                whereClause.append("( m.id = :managerName)");
            }
            
          //Associe id
            if (param.getAssocieName()!= null && param.getAssocieName().trim().length() > 0) {
            	 if (whereClause.length() > 0) {
                     whereClause.append(" AND ");
                 }
                parameters.put("associeName", Long.parseLong(param.getAssocieName()));
                whereClause.append("( a.id = :associeName)");
            }
            
            //name like
            if (param.getNameLike() != null && param.getNameLike().trim().length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                parameters.put("searchField", "%" + param.getNameLike() +"%");
                whereClause.append(" ( upper(c.companyName) like upper(:searchField) ) ");
            }
            
          //startingLetterName
            if (param.getStartingLetterName() != null && param.getStartingLetterName().trim().length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                parameters.put("startingLetterName", param.getStartingLetterName() +"%");
                whereClause.append(" upper( c.companyName) like upper(:startingLetterName) ");
            }
            
            if (whereClause.length() > 0) {
            	queryStringBuffer.append(" WHERE ").append(whereClause);
            }
            queryStringBuffer.append(" order by c.companyName asc");
            Query q = em.createQuery(queryStringBuffer.toString());
            for (Map.Entry<String, Object> me : parameters.entrySet()) {
                q.setParameter(me.getKey(), me.getValue());
               // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
            }
            
            q.setFirstResult(firstPos);
            q.setMaxResults(LINEPERPAGE);
        
            return q.getResultList();

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  searchCustomers..."));

		}finally{
			em.close();
		}
		
    	
    	
    }
/*
    @SuppressWarnings("unchecked")
    public List<Contact> getAllByUserId(Long userId) {
        return em
                .createQuery(
                        "SELECT c FROM Contact c JOIN c.jaspersUsers usr WHERE usr.id=:userId ORDER BY c.contactName")
                .setParameter("userId", userId).list();
    }


    @SuppressWarnings("unchecked")
    public List<String> getAllDistinctContactCountries() {
        return em.createQuery("SELECT DISTINCT ctry FROM Contact c JOIN c.country ctry ORDER BY ctry.description").list();
    }

 
    @SuppressWarnings("unchecked")
    public List<String> getAllDistinctContactTypes() {
        
        return em.createQuery("SELECT DISTINCT ct FROM Contact c JOIN c.contactType ct ORDER BY ct.description").list();
    }

   
    @SuppressWarnings("unchecked")
    public List<Contact> findContacts(SearchContactParam searchContactParam) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        StringBuilder hql = new StringBuilder("SELECT c FROM Contact c");
        StringBuilder whereClause = new StringBuilder("");
        
        if (searchContactParam.getExpression() != null) {
            parameters.put("expresion", searchContactParam.getExpression() + "%");
            whereClause.append("(c.contactName LIKE :expresion OR c.lastName LIKE :expresion OR c.firstName LIKE :expresion OR c.company LIKE :expresion)");
        }
        if (searchContactParam.getContactTypeId() != null) {
            if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
            parameters.put("contactTypeId", Long.parseLong(searchContactParam.getContactTypeId()));
            whereClause.append("c.contactType.id=:contactTypeId");
        }
        if (searchContactParam.getCountryId() != null) {
            if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
            parameters.put("countryId", searchContactParam.getCountryId());
            whereClause.append("c.country.id=:countryId");
        }
        if (searchContactParam.isForActiveOnly()) {
            if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
            whereClause.append("c.active IS true");
        }
        if (whereClause.length() > 0) {
            hql.append(" WHERE ").append(whereClause);
        }
        hql.append(" ORDER BY c.contactName ");
        Query q = em.createQuery(hql.toString());
        for (Map.Entry<String, Object> me : parameters.entrySet()) {
            q.setParameter(me.getKey(), me.getValue());
            log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
        }
        
        return q.list();
    }
    */
//    
//    public void deleteAll(List<Contact> contacts) {
//    	if(contacts == null || contacts.size() == 0) {
//    		return;
//    	}
//    	StringBuilder sb = new StringBuilder("delete from Contact c where c.id in (");
////    	StringBuilder sb1 = new StringBuilder("DELETE Contact c FROM JaspersUser ju JOIN ju.contacts c WHERE c.id in (");
//    	int count = 0;
//    	for(Contact contact : contacts) {
//    		if(count++ > 0) {
//    			sb.append(",");
//    		}
//    		sb.append(":id_" + contact.getId());
//    	}
//    	sb.append(")");
//        Query query = em.createQuery(sb.toString());
//    	for(Contact contact : contacts) {
//    		query.setParameter("id_" + contact.getId(), contact.getId());
//    	}
//    	
//        query.executeUpdate();
//    }
    
    
	public void deleteAll(List<Customer> customers) {
		for(int i=customers.size()-1; i>=0; i--) {
			Customer customer = customers.get(i);
			if(customer != null) {
				deleteOne(customer.getId());
			}
		}
	}
    
    
    public void activateAll(List<Customer> customers) {
    	updateActivation(customers, true);
    }
    
    
    public void deactivateAll(List<Customer> customers) {
    	updateActivation(customers, false);
    }

    private void updateActivation(List<Customer> customers, boolean activation) {
    	//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			if(customers == null || customers.size() == 0) {
	    		return;
	    	}
	    	StringBuilder sb = new StringBuilder("update Customer c set c.active=:activation where c.id in (");
	    	int count = 0;
	    	for(Customer customer : customers) {
	    		if(count++ > 0) {
	    			sb.append(",");
	    		}
	    		sb.append(":id_" + customer.getId());
	    	}
	    	sb.append(")");
	        Query query = em.createQuery(sb.toString());
	        query.setParameter("activation", activation);
	    	for(Customer customer : customers) {
	    		query.setParameter("id_" + customer.getId(), customer.getId());
	    	}
	    	
	        query.executeUpdate();
				
	       // tx.commit();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in CustomerDao :  updateActivation..."));
	        
		}finally{
			em.close();
		}
		
    	
    }
}
