package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IRoleDao;
import com.interaudit.domain.model.Position;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IRoleService;

@Transactional
public class RoleService implements IRoleService {
	
	private IRoleDao roleDao;

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<Position> getAll() {
		return roleDao.getAll();
	}
	
	 public List<Option> getPositionsAsOptions(){
	    	List<Option> options= new ArrayList<Option>();
	    	List<Position> positions = this.getAll();
	    	for(Position position : positions){
	    		 options.add(new Option(position.getId().toString(),position.getName()));
	    	}
	    	return options;
	    }

	public Position getOne(Long id) {
		return roleDao.getOne(id);
	}

	public Position getRoleByName(String roleName) {
		return roleDao.getRoleByName(roleName);
	}

	public Map<String, Position> getRolesMappedByName() {
		List<Position> roles = this.getAll();
		Map<String, Position> rolesMap = new HashMap<String, Position>();
		for ( Position role : roles) {
			rolesMap.put(role.getName(), role);
		}
		return rolesMap;
	}
	
	/**
	 * @param userRole
	 * @return
	 */
	public Position saveOne(Position userRole){
		 return roleDao.saveOne(userRole);
	}

	
	/**
	 * @param userRole
	 * @return
	 */
	public Position updateOne(Position userRole){
		return roleDao.updateOne(userRole);
	}

	
	/**
	 * @param id
	 */
	public void deleteOne(Long id){
		roleDao.deleteOne(id);
	}

	// get all user roles where their description is like the model
	/**
	 * @param model
	 * @return
	 */
	public List<Position> getAllLike(String model){
		return roleDao.getAllLike(model);
	}

   
    
    /**
     * @param roleCode
     * @return
     */
    public Position getRoleByCode(String roleCode){
    	return roleDao.getRoleByCode(roleCode);
    }
}
