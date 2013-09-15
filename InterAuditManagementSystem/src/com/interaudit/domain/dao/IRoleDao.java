package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.Position;

public interface IRoleDao {

	
	/**
	 * @param id
	 * @return
	 */
	public Position getOne(Long id);

	
	/**
	 * @return
	 */
	public List<Position> getAll();

	
	/**
	 * @param userRole
	 * @return
	 */
	public Position saveOne(Position userRole);

	
	/**
	 * @param userRole
	 * @return
	 */
	public Position updateOne(Position userRole);

	
	/**
	 * @param id
	 */
	public void deleteOne(Long id);

	// get all user roles where their description is like the model
	/**
	 * @param model
	 * @return
	 */
	public List<Position> getAllLike(String model);

    /**
     * @param roleName
     * @return
     */
   public Position getRoleByName(String roleName);
    
    /**
     * @param roleCode
     * @return
     */
    public Position getRoleByCode(String roleCode);
    
    
}
