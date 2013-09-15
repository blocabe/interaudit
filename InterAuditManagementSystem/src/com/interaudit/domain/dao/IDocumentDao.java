package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.Document;

public interface IDocumentDao {

    Document getOne(Long documentId);
    
    public  Document  getOneDetached(Long id);
    
    Document updateOne(Document document);
    
    Document saveOne(Document document);

    void deleteOne(Long documentId);
    
   // List<Document> getAllByRelatedEntityCode(String jaspersEntityCode);

    List<Document> getAllForUserId(Long userId);

    Document getOneForUserIdByFileNameAndJaspersEntityId(Long userId, String fileName);

    Document getOneByFileNameAndJaspersEntityId(String fileName);

}
