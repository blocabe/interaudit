package com.interaudit.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IDocumentDao;
import com.interaudit.domain.model.Document;
import com.interaudit.service.IDocumentService;

@Transactional
public class DocumentService implements IDocumentService {
    private Log log = LogFactory.getLog(IDocumentService.class);
    private IDocumentDao documentDao;

    public IDocumentDao getDocumentDao() {
        return documentDao;
    }

    public void setDocumentDao(IDocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    
    public void deleteOne(Long documentId) {
        documentDao.deleteOne(documentId);
    }

    
    public Document getOne(Long documentId) {

        return documentDao.getOne(documentId);
    }
    
    public  Document  getOneDetached(Long id){
    	return documentDao.getOneDetached(id);
    }

    
    public Document updateOne(Document document) {

        return documentDao.updateOne(document);
    }

    
    public Document saveOne(Document document) {

        return documentDao.saveOne(document);
    }

    
    public List<Document> getAllByRelatedEntityCode(String jaspersEntityCode) {
        //return documentDao.getAllByRelatedEntityCode(jaspersEntityCode);
    	return null;
    }

    
    public List<Document> getAllForUserId(Long userId) {

        return documentDao.getAllForUserId(userId);
    }

    
    public Document getOneForUserIdByFileNameAndJaspersEntityId(Long userId,
            String fileName, Long jaspersEntityId) {
        Document storedDocument = null;
        try {
            storedDocument = null;
            	/*
            	documentDao
                    .getOneForUserIdByFileNameAndJaspersEntityId(userId,
                            fileName, jaspersEntityId);
                            */
        } catch (NoResultException e) {
            log.debug("No document found for userId=" + userId + "; fileName="
                    + fileName + "; jaspersEntityId=" + jaspersEntityId);
        }
        return storedDocument;
    }

    
    public Document getOneByFileNameAndJaspersEntityId(String fileName,
            Long jaspersEntityId) {
        Document storedDocument = null;
        try {
        	/*storedDocument = documentDao.getOneByFileNameAndJaspersEntityId(
                    fileName, jaspersEntityId);*/
        } catch (NoResultException e) {
            log.debug("No document found for fileName=" + fileName
                    + "; jaspersEntityId=" + jaspersEntityId);
        }
        return storedDocument;
    }

    // @Override
    // public Document getDocumentByActioPlanIdAndFileName(Long actionPlanId,
    // String fileName) {
    //
    // Document storedDocument = null;
    // try {
    // storedDocument = documentDao.getDocumentByActioPlanIdAndFileName(
    // actionPlanId, fileName);
    // } catch (NoResultException e) {
    // log.debug(e.getMessage(), e);
    // // don't do anything, just return null to the caller.
    // }
    //
    // return storedDocument;
    // }

    // @Override
    // public List<Document> getDocumentsByCreatorIdAndEntityCode(Long
    // creatorId, String entityCode) {
    // return documentDao.getDocumentsByCreatorIdAndEntityCode(creatorId,
    // entityCode);
    // }
    //    
    // @Override
    // public List<DocumentEntityPair> getActionPlanDocumentsByCreatorId(Long
    // creatorId) {
    // return documentDao.getActionPlanDocumentsByCreatorId(creatorId);
    // }
    //    
    // @Override
    // public List<DocumentEntityPair> getProjectDocumentsByCreatorId(Long
    // creatorId) {
    // return documentDao.getProjectDocumentsByCreatorId(creatorId);
    // }

}
