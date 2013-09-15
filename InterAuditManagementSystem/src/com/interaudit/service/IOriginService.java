package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Origin;
import com.interaudit.domain.model.data.Option;



/**
 * Service methods handling Role entities.
 */
/**
 * @author blocabe
 *
 */
public interface IOriginService {
    /**
     * 
     * @return Exercise objects list representing all Exercise known by IAMS
     *         application.
     */
    List<Origin> getAll();

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Origin getOne(Long id);
    
    Origin getOneDetached(Long id);
    
    
    boolean createNewOrigine(String newOrigin);
    

    /**
     * Delete a Exercise object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    
 
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Origin saveOne(Origin origin); 
    
   

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Origin updateOne(Origin origin);
    
    
    List<Option> getAllAsOptions();
    
    
    
    
}
