package com.interaudit.domain.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IUserDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.AccessRight;
import com.interaudit.domain.model.Document;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Position;
import com.interaudit.domain.model.Right;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchEmployeeParam;

/**
 * @author bernard
 *
 */
public class UserDao implements IUserDao {

	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(UserDao.class);
	
	
	
	
	@SuppressWarnings("unchecked")
    public List<Position> getAllDistinctPositions() {
		try{
			return em.createQuery("select  distinct e.position from Employee e").getResultList(); 
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : getAllDistinctPositions ..."));
		}finally{
			em.close();
		}
    }
	
	
    public void deleteOne(Long id) {
		
		try{			
				Employee user =em.find(Employee.class, id);
		        if (user == null) {
		            throw new DaoException(2);
		        }
		        em.remove(user);	       
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : deleteOne ..."));
		}finally{
			em.close();
		}
                
    }

	
    @SuppressWarnings("unchecked")
    public List<Employee> getAll() {
		try{
			
			Calendar calendar = Calendar.getInstance();
		    int year = calendar.get(Calendar.YEAR);
		    calendar.set(Calendar.YEAR, year);
		    calendar.set(Calendar.MONTH, 0);
		    calendar.set(Calendar.DAY_OF_MONTH, 1);
		    Date secondDate = calendar.getTime();
			return em.createQuery("select distinct e from Employee e where  e.dateEndOfHiring >= :d2 order by  e.lastName , e.firstName asc").setParameter("d2", secondDate).getResultList(); 
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : getAll ..."));       
		}
		finally{
			em.close();
		}
    }
	
    @SuppressWarnings("unchecked")
	public List<Right> getAllRights(){
		
		try{			
				return em.createQuery("select distinct r from Right r order by r.id asc").getResultList();			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : getAllRights ..."));            
		}
		finally{
			em.close();
		}
		
	}
    
    public int createDefaultAccessRightsForEmployee(Employee employee){
    	try{
			
    		List<Right> allRights = getAllRights();
    		int count = 0;
    		for(Right right : allRights){
    			AccessRight accessRight = new AccessRight( employee,  right, true);
    			em.persist(accessRight);			
    			count++;
    		}
    		return count;
		
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : createDefaultAccessRightsForEmployee ..."));     
		}
		finally{
			em.close();
		}
    }
	
    @SuppressWarnings("unchecked")
	public List<Right> getAllRights(String type){
		
		try{
			if(type.equalsIgnoreCase("All"))
				return em.createQuery("select distinct r from Right r order by r.id asc").getResultList();
			else{
				return em.createQuery("select distinct r from Right r where upper(r.type) = upper(:type) order by r.id asc").setParameter("type",type).getResultList();
			}
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : getAllRights ..."));         
		}
		finally{
			em.close();
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean getUserHasAccessRight(Long id,String right){
		try{
			String queryString = "select ac from AccessRight ac where ac.employee.id = :idemployee AND upper(ac.right.code) = upper(:right)";
	        List<AccessRight> resultList = em.createQuery(queryString).setParameter("idemployee",id).setParameter("right",right).getResultList();
	        if (resultList.size() > 0) {
	        	AccessRight ac = (AccessRight) resultList.get(0);
	        	return ac.isActive();
	        }
	        return false;
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : getUserHasAccessRight ..."));        
		}finally{
			em.close();
		}
	}
	
public boolean updateUserAccessRights(Long rightId,Set<String> codeList,String type,String role){
try{	
			//Set the active user to 1
			Query q= null;
			if(codeList.size() > 0){
				List<Long> activeRightList = null;
				List<Long> inactiveRightList = null;
				
				q = em.createQuery("select ar.id from AccessRight ar JOIN ar.right r JOIN ar.employee p WHERE p.code in (:codeList) and r.id = :rightId").setParameter("rightId", rightId).setParameter("codeList", codeList);
				activeRightList = q.getResultList();
				
				q = em.createQuery("select ar.id from AccessRight ar JOIN ar.right r JOIN ar.employee p WHERE p.code not in (:codeList) and r.id = :rightId").setParameter("rightId", rightId).setParameter("codeList", codeList);
				inactiveRightList = q.getResultList();
				
				if(activeRightList !=null && activeRightList.size()>0){
					q = em.createQuery("update AccessRight ar set ar.active = true  where ar.id in (:identifiers) ").setParameter("identifiers", activeRightList);
					int result = q.executeUpdate();
				}
				
				if(inactiveRightList !=null && inactiveRightList.size()>0){
					q = em.createQuery("update AccessRight ar set ar.active = false  where ar.id in (:identifiers) ").setParameter("identifiers", inactiveRightList);
				    int result = q.executeUpdate();
				}
				
			}
			else{
				q = em.createQuery("update AccessRight ar set ar.active = false  where ar.id  = :rightId").setParameter("rightId", rightId);
			    int result = q.executeUpdate();
			}			
	        return true;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e) ;
	        
		}finally{
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateAccessRights(String rights,String type,String employeeId){
		
		try{	
			
			List<Long> allRightIds = null;//em.createQuery("select distinct r.id from Right r order by r.id asc").getResultList();
			if(type.equalsIgnoreCase("All")){
				allRightIds = em.createQuery("select distinct r.id from Right r order by r.id asc").getResultList();
			}
			else{
				allRightIds = em.createQuery("select distinct r.id from Right r WHERE r.type = :type order by r.id asc").setParameter("type", type).getResultList();
			}
			
			List<Long> activeRightList = new ArrayList<Long>();
			List<Long> inactiveRightList = new ArrayList<Long>();
			
			//Recuperer les droits actifs
			if(rights != null && rights.length()>0){				
				StringTokenizer stmr = new StringTokenizer(rights,",",false);
				while(stmr.hasMoreTokens())
				{
					String userRight = stmr.nextToken();
					StringTokenizer st = new StringTokenizer(userRight,"##",false);
					if(st.countTokens() == 2)
					{
						Long rightId = Long.valueOf(st.nextToken());
						activeRightList.add(rightId);
					}
				}
			}
				
				//Recuperer les droits inactifs
				for(Long rightId : allRightIds ){
					boolean found = false;
					for(Long activeRight : activeRightList){
						if(activeRight.longValue() == rightId.longValue()){
							found = true;
						}
					}
					
					if(found == false){
						inactiveRightList.add(rightId);
					}
				}
				
				Query q= null;
				
				if(activeRightList.isEmpty() == false){
					List<Long> activeRightList1 = null;
					q = em.createQuery("select ar.id from AccessRight ar JOIN ar.right r JOIN ar.employee p WHERE p.id = :employeeId and   r.id in (:codeList)").setParameter("employeeId",Long.parseLong(employeeId) ).setParameter("codeList", activeRightList);
					activeRightList1 = q.getResultList();
					if(activeRightList1 !=null && activeRightList1.size()>0){
						q = em.createQuery("update AccessRight ar set ar.active = true  where ar.id in (:identifiers) ").setParameter("identifiers", activeRightList1);
						int result = q.executeUpdate();
					}
				}
				
				
				if(inactiveRightList.isEmpty() == false){
					List<Long> inactiveRightList1 = null;
					q = em.createQuery("select ar.id from AccessRight ar JOIN ar.right r JOIN ar.employee p WHERE p.id = :employeeId and   r.id in (:codeList)").setParameter("employeeId",Long.parseLong(employeeId) ).setParameter("codeList", inactiveRightList);
					inactiveRightList1 = q.getResultList();
					if(inactiveRightList1 !=null && inactiveRightList1.size()>0){
						q = em.createQuery("update AccessRight ar set ar.active = false where ar.id in (:identifiers) ").setParameter("identifiers", inactiveRightList1);
						int result = q.executeUpdate();
					}
				}
				
	        return true;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : updateAccessRights ..."));  
	        
		}finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Boolean> accessRightsMap(String roleName){
		
		
		Map<String, Boolean> accessRights = new HashMap<String, Boolean>();
		
		try{
			String queryString = null;
			List<AccessRight> resultList = null;
			if(roleName.equalsIgnoreCase("All")){
				 queryString = "select ac from AccessRight ac";
				 resultList = em.createQuery(queryString).getResultList();
			}
			else{
				queryString = "select ac from AccessRight ac join ac.employee.position p WHERE p.name = :roleName";
				resultList = em.createQuery(queryString).setParameter("roleName", roleName).getResultList();
			}
		
	        if (resultList.size() > 0) {
	        	
	        	for( AccessRight ac  :  resultList){
	        		accessRights.put(ac.getRight().getCode() +"##" + ac.getEmployee().getId(),  Boolean.valueOf((ac.isActive())) );
	        	}
	        	
	        }
	        return accessRights;
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : accessRightsMap ..."));  
	        
		}finally{
			em.close();
		}
		
	}

    
    
    public Employee getOne(Long id) {
    	try{
    			return em.find(Employee.class, id);	
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : getOne ..."));  
	        
		}finally{
			em.close();
		}
       
    }
    
    @SuppressWarnings("unchecked")
	public Employee getOneDetached(Long id){
		
    	try{
        	String queryString = "select e from Employee e  where e.id = :id";
            List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
            if (resultList.size() > 0) {
                return (Employee) resultList.get(0);
            }
            return null;
        	}catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in UserDao : getOneDetached ..."));  
    	        
    		}finally{
        		em.close();
        	}
    }

    @SuppressWarnings("unchecked")
    
    public Employee getOne(String userName, String password,
            boolean onlyActive) {
    	
    	try{
    		
    		String queryString = "select e from Employee e where upper(e.login) = upper(:login) and upper(e.password) = upper(:password)";
            if (onlyActive) {
                queryString = queryString.concat(" and userActive = true");
            }

            List<Employee> resultList = em.createQuery(queryString).setParameter("login",
                    userName).setParameter("password", password).getResultList();
            if (resultList.size() > 0) {
                return resultList.get(0);
            }
            return null;
			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao :  getOne(String userName, String password,boolean onlyActive) ...")); 
	        
		}finally{
			em.close();
		}

        
    }
    
    /**
     * @param code
     * @return
     */
    @SuppressWarnings({ "unchecked" })
	
    public Employee getOneFromCode(String code){
    	
    	try{
			String queryString = "select e from Employee e where upper(e.code) = upper(:code)";
		    List<Employee> resultList = em.createQuery(queryString)
		    .setParameter("code",
		            code).getResultList();
		    if (resultList.size() > 0) {
		        return resultList.get(0);
		    }
		    return null;
    
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : getOneFromCode ..."));  
	        
		}finally{
			em.close();
		}
    	 
    }
    
    /**
     * @param code
     * @return
     */
    @SuppressWarnings({ "unchecked" })
	
    public List<Employee> getFromCode(String code){
    	
    	try{
    		
    		String queryString = "select e from Employee e where upper(e.code) like upper(:code)";
            List<Employee> resultList = em.createQuery(queryString)
            .setParameter("code",
           		 "%" + code + "%" ).getResultList();
            
            return resultList;
			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : getFromCode(String code) ..."));
	        
		}finally{
			em.close();
		}
		
    	 
    }
    
    public int getMaxEmployeeCodeSequence(String code) {
    	
    	try{
			Number result = null;
			
			try{	 
				result = (Number) em.createQuery("select count(*) from Employee e where upper(e.code) like upper(:code)").setParameter("code", code + "%").getSingleResult();
			}
			catch(javax.persistence.NoResultException e){	
				result = null; 
			}
		    
			if ( result == null) {
				return 0;
			} else {
				return result.intValue();
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : getMaxEmployeeCodeSequence(String code) ..."));
	        
		}finally{
			em.close();
		}
        
    }
    
    public int getMaxEmployeeLoginSequence(String login) {
    	
    	try{
				Number result = null;				
				try{	 
					result= (Number) em.createQuery("select count(*) from Employee e where upper(e.login) = upper(:login)").setParameter("login", login).getSingleResult();
				}
				catch(javax.persistence.NoResultException e){	
					result = null; 
				}
				
				if ( result == null) {
					return 0;
				} else {
					return result.intValue();
				}	
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : getMaxEmployeeLoginSequence(String login) ..."));
	        
		}finally{
			em.close();
		}
    }
    
    
   

    
    public Employee saveOne(Employee user) {    	    	
		try{			
			em.persist(user);			
	        return user;	        
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : saveOne ..."));
	        
		}finally{
			em.close();
		}  	
    }

    
    public Employee updateOne(Employee jaspersUser) {    	
		try{			
			Employee employee = em.merge(jaspersUser);		        
	        return employee;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e) ;
	        
		}finally{
			em.close();
		}
        
    }

    @SuppressWarnings("unchecked")    
    public List<Employee> getAllByRoleName(List<String> roleNames) {
		try{
			
			Calendar calendar = Calendar.getInstance();
		    int year = calendar.get(Calendar.YEAR);
		    calendar.set(Calendar.YEAR, year);
		    calendar.set(Calendar.MONTH, 0);
		    calendar.set(Calendar.DAY_OF_MONTH, 1);
		    Date secondDate = calendar.getTime();
			
			return em
            .createQuery(
                    "SELECT distinct e FROM Employee e JOIN e.position p WHERE e.dateEndOfHiring >= :d2 and p.name in (:roleNames) ORDER BY e.lastName, e.firstName ")
            .setParameter("d2", secondDate).setParameter("roleNames", roleNames).getResultList();

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e) ;
	        
		}finally{
			em.close();
		}
        
    }
    
    
    @SuppressWarnings("unchecked")    
    public List<Employee> getAllByRoleName(List<String> roleNames,int year) {
		try{
			
			Calendar calendar = Calendar.getInstance();
		    /*
		    calendar.set(Calendar.MONTH, 1);
		    calendar.set(Calendar.DAY_OF_MONTH, 1);
		    Date firstDate = calendar.getTime();
		    */
		    
		    calendar.set(Calendar.YEAR, year);
		    calendar.set(Calendar.MONTH, 0);
		    calendar.set(Calendar.DAY_OF_MONTH, 1);
		    Date secondDate = calendar.getTime();
			
			return em
            .createQuery(
                    "SELECT distinct e FROM Employee e JOIN e.position p WHERE p.name in (:roleNames) and e.dateEndOfHiring >= :d2 ORDER BY e.lastName, e.firstName ")
            .setParameter("roleNames", roleNames).setParameter("d2", secondDate).getResultList();

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e) ;
	        
		}finally{
			em.close();
		}
        
    }

    @SuppressWarnings("unchecked")
    
    public List<Employee> searchUser(String searchField, boolean onlyActive,int firstPos,int LINEPERPAGE) {
    	

		try{

			StringBuffer queryStringBuffer = new StringBuffer(
            "select e from Employee e where ( upper(e.login) like upper(:searchField) ");
		    queryStringBuffer
		            .append("or upper(e.firstName) like upper(:searchField) ");
		    queryStringBuffer
		            .append("or upper(e.lastName) like upper(:searchField) ");
		    queryStringBuffer
		            .append("or upper(e.email) like upper(:searchField) ) ");
		
		    if (onlyActive) {
		        queryStringBuffer.append("and e.userActive = true");
		    }
		
		    return em.createQuery(queryStringBuffer.toString()).setParameter(
		            "searchField", "%" + searchField + "%").setFirstResult(firstPos).setMaxResults(LINEPERPAGE).getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : searchUser ..."));
		}finally{
			em.close();
		}

        
    }
    
public long getTotalCount(SearchEmployeeParam param){
	

	try{
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuffer queryStringBuffer = new StringBuffer("select count(*) from Employee e join e.position p");
		StringBuilder whereClause = new StringBuilder("");
		
		 //Rolename
	    if (param.getRoleName()!= null && param.getRoleName().trim().length() > 0) {
	        parameters.put("roleName", Long.parseLong(param.getRoleName()) );
	        whereClause.append("(p.id = :roleName)");
	    }
	    
	    //name like
	    if (param.getNameLike() != null  && param.getNameLike().trim().length() > 0) {
	        if (whereClause.length() > 0) {
	            whereClause.append(" AND ");
	        }
	        parameters.put("searchField",  "%" + param.getNameLike() +"%");
	        whereClause.append(" ( upper(e.firstName) like upper(:searchField) or upper(e.lastName) like upper(:searchField) or upper(e.email) like upper(:searchField) ) ) ");
	    }
	    
	  //startingLetterName
	    if (param.getStartingLetterName() != null && param.getStartingLetterName().trim().length() > 0) {
	        if (whereClause.length() > 0) {
	            whereClause.append(" AND ");
	        }
	        parameters.put("startingLetterName", param.getStartingLetterName() +"%");
	        whereClause.append(" upper( e.lastName) like upper(:startingLetterName) ");
	    }
	    
	    if (whereClause.length() > 0) {
	    	queryStringBuffer.append(" WHERE ").append(whereClause);
	    }
	    //queryStringBuffer.append(" order by e.firstName, e.lastName, e.login asc");
	    Query q = em.createQuery(queryStringBuffer.toString());
	    for (Map.Entry<String, Object> me : parameters.entrySet()) {
	        q.setParameter(me.getKey(), me.getValue());
	       // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	    }
			
		 Number result = null;
		 try{	 
			 result = (Number) q.getSingleResult();
		 }
		 catch(javax.persistence.NoResultException e){	
			 result = null; 
		 }
		 
		 if ( result == null) {
		     return 0;
		 } else {
		     return result.longValue();
		 }

	}
	catch(Exception e){
		logger.error(e.getMessage());
		throw new BusinessException(new ExceptionMessage("Failed in UserDao : getTotalCount ..."));
	}finally{
		em.close();
	}
		
	
}
    
    @SuppressWarnings("unchecked")
    
    public List<Employee> searchUsers(SearchEmployeeParam param,int firstPos,int LINEPERPAGE){
    	

		try{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
	    	StringBuffer queryStringBuffer = new StringBuffer("select distinct e from Employee e join e.position p");
	    	StringBuilder whereClause = new StringBuilder("");
	    	
	    	 //Rolename
	        if (param.getRoleName()!= null && param.getRoleName().trim().length() > 0) {
	            parameters.put("roleName", Long.parseLong(param.getRoleName()) );
	            whereClause.append("(p.id = :roleName)");
	        }
	        
	        //name like
	        if (param.getNameLike() != null  && param.getNameLike().trim().length() > 0) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("searchField",  "%" + param.getNameLike() +"%");
	            whereClause.append(" ( upper(e.firstName) like upper(:searchField) or upper(e.lastName) like upper(:searchField) or upper(e.email) like upper(:searchField) ) ) ");
	        }
	        
	      //startingLetterName
	        if (param.getStartingLetterName() != null && param.getStartingLetterName().trim().length() > 0) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("startingLetterName", param.getStartingLetterName() +"%");
	            whereClause.append(" upper( e.lastName) like upper(:startingLetterName) ");
	        }
	        
	        if (whereClause.length() > 0) {
	        	queryStringBuffer.append(" WHERE ").append(whereClause);
	        }
	        queryStringBuffer.append(" order by  e.lastName ,e.firstName asc");
	        Query q = em.createQuery(queryStringBuffer.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	        
	        q.setFirstResult(firstPos);
	        q.setMaxResults(LINEPERPAGE);
	    
	        List<Employee> employees = null;
	        try{
	        	employees = q.getResultList();
	        }catch(java.lang.Exception e){
	        	logger.error(e);
	        }
	        
	        return employees;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserDao : searchUsers(SearchEmployeeParam param,int firstPos,int LINEPERPAGE) ..."));
		}finally{
			em.close();
		}
    	
    	
        
    }

    
    public Document getDocumentByEntityIdAndFileName(Long userId,
            String fileName) {
    	return null;
    	/*
        return (Document) em
                .createQuery(
                        "SELECT d FROM Document d WHERE d.createdUser.id=:userId AND d.fileName=:fileName AND d.relatedJaspersEntity.code=:jaspersEntityCode")
                .setParameter("userId", userId).setParameter("fileName",
                        fileName).setParameter("jaspersEntityCode",
                        Constants.JASPERS_ENTITY_CODE_PERSONAL)
                .uniqueResult();
                */
    }
}
