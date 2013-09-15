package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Document;



/**
 * Service methods for handling documents.
 * 
 * @author Valentin Carnu
 * 
 */
public interface IDocumentService {
    /**
     * 
     * @param documentId
     *            Document identifier.
     * @return Returns the document identified by given documentId.
     */
    Document getOne(Long documentId);
    
    Document  getOneDetached(Long id);

    /**
     * Updates a document.
     * 
     * @param document
     * @return Newly updated document.
     */
    Document updateOne(Document document);

    /**
     * Save a new document.
     * 
     * @param document
     * @return Returns newly saved document.
     */
    Document saveOne(Document document);

    /**
     * Deletes a document identified by given documentId.
     * 
     * @param documentId
     */
    void deleteOne(Long documentId);

    /**
     * 
     * @param jaspersEntityCode
     * @return Document objects list linked to a jaspers entity identified by
     *         the given jaspersEntityCode.
     */
    List<Document> getAllByRelatedEntityCode(String jaspersEntityCode);

    /**
     * 
     * @param userId
     * @return Document objects list linked to the user identified by given
     *         userId
     */
    List<Document> getAllForUserId(Long userId);

    /**
     * 
     * @param userId
     * @param fileName
     * @param jaspersEntityId
     * @return
     */
    Document getOneForUserIdByFileNameAndJaspersEntityId(Long userId,
            String fileName, Long jaspersEntityId);

    /**
     * 
     * @param fileName
     * @param jaspersEntityId
     * @return
     */
    Document getOneByFileNameAndJaspersEntityId(String fileName,
            Long jaspersEntityId);
}
