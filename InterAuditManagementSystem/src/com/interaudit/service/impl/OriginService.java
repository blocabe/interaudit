package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IOriginDao;
import com.interaudit.domain.model.Origin;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IOriginService;

@Transactional
public class OriginService implements IOriginService {
	//private Log log = LogFactory.getLog(TaskService.class);
    private IOriginDao originDao;
    
  
    /**
     * 
     * @return Task objects list representing all tasks known by IAMS
     *         application.
     */
    public List<Origin> getAll(){
    	return originDao.getAll();
    }
    
    public  List<Option> getAllAsOptions(){
    	List<Option> options= new ArrayList<Option>();
    	List<Origin> origins = this.getAll();
    	for(Origin origin : origins){
    		 options.add(new Option(origin.getId().toString(),origin.getName()));
    	}
    	return options;
    }
    
    
  public  boolean createNewOrigine(String newOriginName){
    	
    	//Build a code for the employee
    	int index=2;
		String code = newOriginName.substring(0, index).toUpperCase();
		List<Origin> origins = getAll();
		boolean found=false;
		do{
			found=false;
			for(Origin origin: origins){
				if(origin.getCode().equalsIgnoreCase(code)){
					found = true;
					break;
				}
			}
			
			if(found){
				 index++;
				 code = newOriginName.substring(0, index).toUpperCase();
			}
			
			
		}while(found);
		
		Origin newOrigin = saveOne( new  Origin(code,newOriginName));
		
		return (newOrigin != null);
		
    }
    

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    public Origin getOne(Long id){
    	return originDao.getOne(id);
    }
    
    public Origin getOneDetached(Long id){
    	return originDao.getOneDetached(id);
    }
    
 
    /**
     * Delete a Task object identified by given id.
     * 
     * @param id
     */
    public void deleteOne(Long id){
    	originDao.deleteOne(id);
    }
    
 
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
   public Origin saveOne(Origin origin){
	   return originDao.saveOne(origin);
   }
   
  

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
   public Origin updateOne(Origin origin){
    	return originDao.updateOne(origin);
    }

	public IOriginDao getOriginDao() {
		return originDao;
	}

	public void setOriginDao(IOriginDao originDao) {
		this.originDao = originDao;
	}
   
   
   

	
    
   
    
    
	
}
