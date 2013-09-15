package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Declaration;
import com.interaudit.domain.model.data.Option;



/**
 * Service methods handling ank entities.
 */
/**
 * @author blocabe
 *
 */
public interface IDeclarationService {
    /**
     * 
     * @return Declaration objects list representing all Declarations known by IAMS
     *         application.
     */
    List<Declaration> getAll();
    
    /**
     * @return the list of Declaration as option
     */
    List<Option> getDeclarationAsOptions();

    /**
     * Returns a Declaration object identified by given id.
     * 
     * @param id
     * @return Returns a Declaration object identified by given id.
     */
    Declaration getOne(Long id);
    
    
    Declaration  getOneDetached(Long id);
  
    public Declaration getOneForExercise(String customer,String exercise);
    
    
 
    /**
     * Delete a Declaration object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    
 
    /**
     * Returns a Declaration object identified by given id.
     * 
     * @param id
     * @return Returns a Declaration object identified by given id.
     */
    Declaration saveOne(Declaration Declaration); 
    
    public Declaration updateOne(Declaration Declaration);

    
    
    

    
}
