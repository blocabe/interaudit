package com.interaudit.domain.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IDocumentDao;
import com.interaudit.domain.model.Document;

public class DocumentDao  implements IDocumentDao {

   
	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(DocumentDao.class);
    
	
    public Document getOne(Long documentId) {
		
		try{
			
			return (Document)em.find(Document.class, documentId);
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
	        
		}finally{
			em.close();
		}

        
    }

    // 
    // public Document getOneByFileNameAndRelatedJaspersEntityId(String
    // fileName,
    // Long jaspersEntityId) {
    //
    // return (Document) em
    // .createQuery(
    // "SELECT d FROM Document d WHERE
    // d.relatedJaspersEntity.id=:jaspersEntityId AND
    // LOWER(d.fileName)=LOWER(:fileName)")
    // .setParameter("jaspersEntityId", jaspersEntityId).setParameter(
    // "fileName", fileName).getSingleResult();
    // }

   
	
    public Document updateOne(Document document) {
		
		try{
			
			return (Document)em.merge(document);
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
	        
		}finally{
			em.close();
		}

        
    }

	
    public Document saveOne(Document document) {
		
		try{
			
			em.persist(document);
	        return document;
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
	        
		}finally{
			em.close();
		}
		
    	
    }

	
    public void deleteOne(Long documentId) {
		
		try{
			em.remove(em.find(Document.class, documentId));
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
	        
		}finally{
			em.close();
		}
		
    	
    }
	
	@SuppressWarnings("unchecked")
	 
	public  Document  getOneDetached(Long id){
		 try{
	        	String queryString = "select d from Document d  where d.id = :id";
	            List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
	            if (resultList.size() > 0) {
	                return (Document) resultList.get(0);
	            }
	            return null;
	        	}catch(Exception e){
	    			logger.error(e.getMessage());
	    			throw new RuntimeException(e);
	    	        
	    		}finally{
	        		em.close();
	        	}
	 }

    /*
    @SuppressWarnings("unchecked")
    public List<Document> getAllByRelatedEntityCode(String jaspersEntityCode) {

        return em
                .createQuery(
                        "SELECT d FROM Document d WHERE d.relatedJaspersEntity.code=:jaspersEntityCode ORDER BY LOWER(d.title)")
                .setParameter("jaspersEntityCode", jaspersEntityCode)
                .list();
    }
    */

   
    @SuppressWarnings("unchecked")
    
    public List<Document> getAllForUserId(Long userId) {
    	
    	try{
    		
    		return em
            .createQuery(
                    "SELECT d from Document d WHERE d.createdUser.id=:userId")
            .setParameter("userId", userId).getResultList();
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
	        
		}finally{
			em.close();
		}

        
    }

   
    
    public Document getOneForUserIdByFileNameAndJaspersEntityId(Long userId,
            String fileName) {
    	
    	try{
    		
    		return (Document) em
            .createQuery(
                    "SELECT d FROM Document d WHERE d.createdUser.id=:userId AND d.fileName=:fileName ")
            .setParameter("userId", userId)
            .setParameter("fileName",fileName)
            .getSingleResult();
			
		}
    	catch(javax.persistence.NoResultException e){	
    		return null; 
    	}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
	        
		}finally{
			em.close();
		}

        
    }

    
    
    public Document getOneByFileNameAndJaspersEntityId(String fileName) {
    	
    	try{
    		
    		return (Document) em
            .createQuery("SELECT d FROM Document d WHERE d.fileName=:fileName")
            .setParameter("fileName", fileName)
            .getSingleResult();
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
	        
		}finally{
			em.close();
		}
        
        
    }

    // 
    // public Document getDocumentByActioPlanIdAndFileName(Long actionPlanId,
    // String fileName) {
    //
    // return (Document) em
    // .createQuery(
    // "SELECT d FROM ActionPlan ap JOIN ap.documents d WHERE
    // ap.id=:actionPlanId AND LOWER(d.fileName)=LOWER(:fileName)")
    // .setParameter("actionPlanId", actionPlanId).setParameter(
    // "fileName", fileName).getSingleResult();
    // }
    //
    // 
    // @SuppressWarnings("unchecked")
    // public List<Document> getDocumentsByCreatorIdAndEntityCode(Long
    // creatorId, String entityCode) {
    // return em.createQuery(
    // "SELECT d FROM Document d WHERE d.createdUser.id=:creatorId AND
    // d.relatedJaspersEntity.code=:entityCode")
    // .setParameter("creatorId", creatorId)
    // .setParameter("entityCode", entityCode)
    // .getResultList();
    // }
    //
    // 
    // @SuppressWarnings("unchecked")
    // public List<DocumentEntityPair> getActionPlanDocumentsByCreatorId(Long
    // creatorId) {
    // return em.createQuery(
    // "SELECT DISTINCT NEW lu.intrasoft.jaspers.entities.DocumentEntityPair(d,
    // ap.id, CONCAT(ap.year, ap.beneficiaryCountry.isoCode)) FROM ActionPlan ap
    // JOIN ap.documents d WHERE d.createdUser.id=:creatorId")
    // .setParameter("creatorId", creatorId)
    // .getResultList();
    // }
    //
    // 
    // @SuppressWarnings("unchecked")
    // public List<DocumentEntityPair> getProjectDocumentsByCreatorId(Long
    // creatorId) {
    // return em.createQuery(
    // "SELECT DISTINCT NEW lu.intrasoft.jaspers.entities.DocumentEntityPair(d,
    // p.id, COALESCE(p.temporaryReference, p.projectReference)) FROM Project p
    // JOIN p.documents d WHERE d.createdUser.id=:creatorId")
    // .setParameter("creatorId", creatorId)
    // .getResultList();
    // }

}
