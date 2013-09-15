package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.UserAction;

public interface IUserActionDao {

	
	/**
	 * @param id
	 * @return
	 */
	public UserAction getOne(Long id);

	
	/**
	 * @return
	 */
	public List<UserAction> getAll(String className,Long idEntity);

	
	/**
	 * @param userRole
	 * @return
	 */
	public UserAction saveOne(UserAction userAction);

	
	
    
    
}
