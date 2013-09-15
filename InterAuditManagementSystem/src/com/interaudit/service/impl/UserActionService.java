package com.interaudit.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IUserActionDao;
import com.interaudit.domain.model.UserAction;
import com.interaudit.service.IUserActionService;

@Transactional
public class UserActionService implements IUserActionService {
	
	private IUserActionDao userActionDao;
	
	/**
	 * @param id
	 * @return
	 */
	public UserAction getOne(Long id){
		return userActionDao.getOne(id);
	}

	
	/**
	 * @return
	 */
	public List<UserAction> getAll(String className,Long idEntity){
		return userActionDao.getAll(className, idEntity);
	}

	
	/**
	 * @param userRole
	 * @return
	 */
	public UserAction saveOne(UserAction userAction){
		return userActionDao.saveOne(userAction);
	}

	public IUserActionDao getUserActionDao() {
		return userActionDao;
	}

	public void setUserActionDao(IUserActionDao userActionDao) {
		this.userActionDao = userActionDao;
	}

	
}
