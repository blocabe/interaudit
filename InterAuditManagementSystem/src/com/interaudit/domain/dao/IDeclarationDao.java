package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.Declaration;

public interface IDeclarationDao {
	
	/**
     * 
     * @return Declaration objects list representing all Declarations known by IAMS
     *         application.
     */
	public  List<Declaration> getAll();

    /**
     * Returns a Declaration object identified by given id.
     * 
     * @param id
     * @return Returns a Declaration object identified by given id.
     */
	public Declaration getOne(Long id);
	
	 public  Declaration  getOneDetached(Long id);
	 
	 public Declaration getOneForExercise(String customer,String exercise);
	 
    
 
    /**
     * Delete a Declaration object identified by given id.
     * 
     * @param id
     */
	public void deleteOne(Long id); 
    
 
    /**
     * Returns a Declaration object identified by given id.
     * 
     * @param id
     * @return Returns a Declaration object identified by given id.
     */
	public Declaration saveOne(Declaration Declaration); 

    /**
     * Returns a Declaration object identified by given id.
     * 
     * @param id
     * @return Returns a Declaration object identified by given id.
     */
	public Declaration updateOne(Declaration Declaration);
    
    

}
