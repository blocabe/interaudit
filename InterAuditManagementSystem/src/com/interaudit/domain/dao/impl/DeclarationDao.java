package com.interaudit.domain.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IDeclarationDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Declaration;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;

/**
 * @author bernard
 *
 */
public class DeclarationDao implements IDeclarationDao {

	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(DeclarationDao.class);
	
	
    public void deleteOne(Long id) {	
		try{			
			Declaration declaration =em.find(Declaration.class, id);
	        if (declaration == null) {
	            throw new DaoException(2);
	        }
	        em.remove(declaration);     					       
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in DeclarationDao :  deleteOne..."));
	        
		}finally{
			em.close();
		}
   
    }

	
    @SuppressWarnings("unchecked")
    public List<Declaration> getAll() {
		
		try{
			return em.createQuery("select distinct b from Declaration b order by b.exercise, b.customer asc").getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in DeclarationDao :  getAll..."));

		}finally{
			em.close();
		}
		
        
    }
	
	 @SuppressWarnings("unchecked")
	 
	public  Declaration  getOneDetached(Long id){
			
		 try{
	        	String queryString = "select b from Declaration b  where b.id = :id";
	            List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
	            if (resultList.size() > 0) {
	                return (Declaration) resultList.get(0);
	            }
	            return null;
	        	}catch(Exception e){
	    			logger.error(e.getMessage());
	    			throw new BusinessException(new ExceptionMessage("Failed in DeclarationDao :  getOneDetached..."));

	    		}finally{
	        		em.close();
	        	}
	 }
	 
	 
	 
	 @SuppressWarnings("unchecked")
	public Declaration getOneFromCode(String code){
		 
		 try{
			 
			 String queryString = "select b from Declaration b  where  b.code = :code ";
		       
		        List<Declaration> resultList = em.createQuery(queryString).setParameter("code",code).getResultList();
		        if (resultList.size() > 0) {
		            return resultList.get(0);
		        }
		        return null;

			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in DeclarationDao :  getOneFromCode..."));

			}finally{
				em.close();
			}
			
			
		}
	 
	 @SuppressWarnings("unchecked")
	  public Declaration getOneForExercise(String customer,String exercise){
		 
		 try{
			 
			 String queryString = "select b from Declaration b  where  b.customer = :customer and b.exercise = :exercise";
		       
		        List<Declaration> resultList = em.createQuery(queryString).setParameter("customer",customer).setParameter("exercise",exercise).getResultList();
		        if (resultList.size() > 0) {
		            return resultList.get(0);
		        }
		        return null;

			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in DeclarationDao :  getOneFromCode..."));

			}finally{
				em.close();
			}
			
			
		}

    
    
    public Declaration getOne(Long id) {
    	
    	try{
    		return em.find(Declaration.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in DeclarationDao :  getOne..."));

		}finally{
			em.close();
		}
        
    }

    
    

    
    public Declaration saveOne(Declaration declaration) {
    	    	
		try{			
			em.persist(declaration);	        
	        return declaration;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in DeclarationDao :  saveOne..."));
	        
		}finally{
			em.close();
		}

		
    	
        
    }

    
    public Declaration updateOne(Declaration declaration) {
    	    	
		try{			
			Declaration ret = em.merge(declaration);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in DeclarationDao :  updateOne..."));
	        
		}finally{
			em.close();
		} 
    }
    
    

    
}
