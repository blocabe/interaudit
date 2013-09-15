package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.UserAction;



/**
 * Service methods handling Role entities.
 * 
 * @author Valentin Carnu
 * 
 */
public interface IUserActionService {
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
