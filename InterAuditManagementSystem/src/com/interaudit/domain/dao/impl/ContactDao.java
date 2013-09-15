package com.interaudit.domain.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IContactDao;
import com.interaudit.domain.model.Contact;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchContactParam;

public class ContactDao  implements IContactDao {

    //private Log log = LogFactory.getLog(IContactDao.class);
	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(ContactDao.class);
	
	
    public int deleteOne(Long id) {
		
		try{
			Object contact = null;
	    	try {
	    		contact = em.find(Contact.class, id);
	    	} catch(EntityNotFoundException e) {}
	    	if(contact == null) {
	    		return 0;
	    	} else {	    		
	    		em.remove(contact);	    		
	            return 1;
	    	}  
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContactDao :  int deleteOne(Long id)..."));
	        
		}finally{
			em.close();
		}
		
		
    	
    }

    
    @SuppressWarnings("unchecked")
    
    public List<Contact> getAll() {
    	
    	try{
    		return em.createQuery("select c from Contact c where  c.active = true order by c.contactName").getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContactDao :  List<Contact> getAll()..."));
		}finally{
			em.close();
		}
		
        
    }


    
    public Contact getOne(Long id) {
    	
    	try{
    		return (Contact) em.find(Contact.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContactDao :  Contact getOne(Long id)..."));

		}finally{
			em.close();
		}
		
        
    }
    
    @SuppressWarnings("unchecked")
	public Contact getOneDetached(Long id){
    	try{
        	String queryString = "select c from Contact c  where c.id = :id";
            List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
            if (resultList.size() > 0) {
                return (Contact) resultList.get(0);
            }
            return null;
        	}catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in ContactDao :  Contact getOneDetached(Long id)..."));

    		}finally{
        		em.close();
        	}
    }
    

    
    public Contact saveOne(Contact contact) {
    	
		try{			
			em.persist(contact);	        
	        return contact;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContactDao :  Contact saveOne(Contact contact)..."));
	        
		}finally{
			em.close();
		}
		
    	
        
    }

    
    public Contact updateOne(Contact contact) {    	
		try{			
			Contact ret = (Contact) em.merge(contact);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContactDao :  Contact updateOne(Contact contact)..."));
	        
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

   */
    @SuppressWarnings("unchecked")
    
    /*
    private String managerName;
	private String nameLike;
	private String startingLetterName;
	*/
    
   
    
    public long getTotalCount(SearchContactParam searchContactParam){
    	
    	try{
    		
    		Map<String, Object> parameters = new HashMap<String, Object>();
            StringBuilder hql = new StringBuilder("select count(*) FROM Contact c join c.customer cust");
            StringBuilder whereClause = new StringBuilder("");
            
            //Contact name
            if (searchContactParam.getNameLike()!= null && searchContactParam.getNameLike().trim().length() > 0) {
                parameters.put("expresion", "%" + searchContactParam.getNameLike()+ "%");
                whereClause.append("( upper( c.contactName ) LIKE upper( :expresion ) OR upper( c.lastName ) LIKE upper( :expresion ) OR upper( c.firstName ) LIKE upper( :expresion ) OR upper( c.company ) LIKE upper( :expresion ) )");
            }
            //Customer name
            if (searchContactParam.getCustomerName()!= null && searchContactParam.getCustomerName().trim().length() > 0) {
            	
            	if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
            	
            	parameters.put("customerName", "%" + searchContactParam.getCustomerName()+ "%");
                whereClause.append("( upper( cust.companyName ) LIKE upper( :customerName ) )");
            }
            
          //Filtrer les clients commencant par customerStartString
	        if (searchContactParam.getStartingLetterName() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("nameLike",   searchContactParam.getStartingLetterName() +"%");
	            whereClause.append("upper( c.contactName ) like upper( :nameLike )");
	        }
            
            
            if (whereClause.length() > 0) {
                hql.append(" WHERE  c.active = true AND  ").append(whereClause);
            }
            //hql.append(" ORDER BY c.contactName ");
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
			throw new BusinessException(new ExceptionMessage("Failed in ContactDao :  long getTotalCount(SearchContactParam searchContactParam)..."));

		}finally{
			em.close();
		}
    	
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Contact> findContacts(SearchContactParam searchContactParam,int firstPos,int LINEPERPAGE) {
    	
    	try{
    		
    		Map<String, Object> parameters = new HashMap<String, Object>();
            StringBuilder hql = new StringBuilder("SELECT c FROM Contact c join c.customer cust");
            StringBuilder whereClause = new StringBuilder("");
            
            //Contact name
            if (searchContactParam.getNameLike()!= null && searchContactParam.getNameLike().trim().length() > 0) {
                parameters.put("expresion", "%" + searchContactParam.getNameLike()+ "%");
                whereClause.append("( upper( c.contactName ) LIKE upper( :expresion ) OR upper( c.lastName ) LIKE upper( :expresion ) OR upper( c.firstName ) LIKE upper( :expresion ) OR upper( c.company ) LIKE upper( :expresion ) )");
            }
            //Customer name
            if (searchContactParam.getCustomerName()!= null && searchContactParam.getCustomerName().trim().length() > 0) {
            	
            	if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
            	
            	parameters.put("customerName", "%" + searchContactParam.getCustomerName()+ "%");
                whereClause.append("( upper( cust.companyName ) LIKE upper( :customerName ) )");
            }
            
            
          //Filtrer les clients commencant par customerStartString
	        if (searchContactParam.getStartingLetterName() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("nameLike",   searchContactParam.getStartingLetterName() +"%");
	            whereClause.append("upper( c.contactName ) like upper( :nameLike )");
	        }
            
            
            if (whereClause.length() > 0) {
                hql.append(" WHERE c.active = true AND  ").append(whereClause);
            }
            hql.append(" ORDER BY c.contactName ");
            Query q = em.createQuery(hql.toString());
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
			throw new BusinessException(new ExceptionMessage("Failed in ContactDao :  findContacts..."));

		}finally{
			em.close();
		}
		
        
    }
    
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
    
    
	public void deleteAll(List<Contact> contacts) {
		for(int i=contacts.size()-1; i>=0; i--) {
			Contact contact = contacts.get(i);
			if(contact != null) {
				deleteOne(contact.getId());
			}
		}
	}
    
    
    public void activateAll(List<Contact> contacts) {
    	updateActivation(contacts, true);
    }
    
    
    public void deactivateAll(List<Contact> contacts) {
    	updateActivation(contacts, false);
    }
    

    private void updateActivation(List<Contact> contacts, boolean activation) {
    	
    	//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			if(contacts == null || contacts.size() == 0) {
	    		return;
	    	}
	    	StringBuilder sb = new StringBuilder("update Contact c set c.active=:activation where c.id in (");
	    	int count = 0;
	    	for(Contact contact : contacts) {
	    		if(count++ > 0) {
	    			sb.append(",");
	    		}
	    		sb.append(":id_" + contact.getId());
	    	}
	    	sb.append(")");
	        Query query = em.createQuery(sb.toString());
	        query.setParameter("activation", activation);
	    	for(Contact contact : contacts) {
	    		query.setParameter("id_" + contact.getId(), contact.getId());
	    	}
	    	
	        query.executeUpdate();
	        //tx.commit();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ContactDao :  updateActivation..."));
	        
		}finally{
			em.close();
		}
		
    	
    }
}
