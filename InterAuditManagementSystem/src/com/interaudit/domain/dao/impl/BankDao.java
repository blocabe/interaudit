package com.interaudit.domain.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IBankDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Bank;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;

/**
 * @author bernard
 *
 */
public class BankDao implements IBankDao {

	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(BankDao.class);
	
	
    public void deleteOne(Long id) {
		
		try{
			
			Bank Bank =em.find(Bank.class, id);
	        if (Bank == null) {
	            throw new DaoException(2);
	        }
	        em.remove(Bank);   
	        
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BankDao : deleteOne..."));		
	        
		}finally{
			em.close();
		}
		
		     
    }

	
    @SuppressWarnings("unchecked")
    public List<Bank> getAll() {
		

		try{
			
			return em.createQuery("select distinct b from Bank b order by b.name asc").getResultList();

		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in BankDao :  List<Bank> getAll()..."));				

		}finally{
			em.close();
		}
		
        
    }
	
	 @SuppressWarnings("unchecked")
	 
	public  Bank  getOneDetached(Long id){
		 try{
	        	String queryString = "select b from Bank b  where b.id = :id";
	            List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
	            if (resultList.size() > 0) {
	                return (Bank) resultList.get(0);
	            }
	            return null;
	        	}catch(Exception e){
					logger.error(e.getMessage());
					throw new BusinessException(new ExceptionMessage("Failed in BankDao :  Bank  getOneDetached(Long id)..."));			

	        	}finally{
	        		em.close();
	        	}
	 }
	 
	 
	 
	 @SuppressWarnings("unchecked")
	public Bank getOneFromCode(String code){
		 

			try{
				
				String queryString = "select b from Bank b  where  b.code = :code ";
			       
		        List<Bank> resultList = em.createQuery(queryString).setParameter("code",code).getResultList();
		        if (resultList.size() > 0) {
		            return resultList.get(0);
		        }
		        return null;

			}
			catch(Exception e){
					logger.error(e.getMessage());
					throw new BusinessException(new ExceptionMessage("Failed in BankDao :  Bank getOneFromCode(String code)..."));	

			}finally{
				em.close();
			}
			
			
		}
	 
	 @SuppressWarnings("unchecked")
	public Bank getOneFromAccountNumber(String accountNumber){
		 

			try{
				
				String queryString = "select b from Bank b  where  b.accountNumber = :accountNumber ";
			       
		        List<Bank> resultList = em.createQuery(queryString).setParameter("accountNumber",accountNumber).getResultList();
		        if (resultList.size() > 0) {
		            return resultList.get(0);
		        }
		        return null;

			}
			catch(Exception e){
					logger.error(e.getMessage());
					throw new BusinessException(new ExceptionMessage("Failed in BankDao :  Bank getOneFromAccountNumber(String accountNumber)..."));

			}finally{
				em.close();
			}
			
			
		}

    
    
    public Bank getOne(Long id) {
    	

		try{
			 return em.find(Bank.class, id);
		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in BankDao :  Bank getOne(Long id)..."));

		}finally{
			em.close();
		}
       
    }

    
    

    
    public Bank saveOne(Bank bank) {    	
		try{			
			em.persist(bank);	        
	        return bank;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BankDao :  Bank saveOne(Bank bank)..."));
	        
		}finally{
			em.close();
		}
		
    	
        
    }

    
    public Bank updateOne(Bank bank) {    	
		try{			
			Bank ret = em.merge(bank);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BankDao :  Bank updateOne(Bank bank)..."));
	        
		}finally{
			em.close();
		}
    }
    
    /**
     * Returns a Bank object identified by given BankName.
     * 
     * @param BankName
     * @return Returns a Bank object identified by given BankName.
     */
	@SuppressWarnings("unchecked")
	
	public  List<Bank> getBankByName(String bankName){
		try{
			
			return em
	        .createQuery(
	                "SELECT distinct b FROM Bank b  WHERE b.name like upper(:name) ORDER BY b.name ")
	        .setParameter("name", bankName).getResultList();

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BankDao :  List<Bank> getBankByName(String bankName)..."));

		}finally{
			em.close();
		}
		
		
	}

    
}
