package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IDeclarationDao;
import com.interaudit.domain.model.Declaration;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IDeclarationService;

@Transactional
public class DeclarationService implements IDeclarationService {
	//private Log log = LogFactory.getLog(DeclarationService.class);
    private IDeclarationDao declarationDao;
    
    /**
     * 
     * @return Declaration objects list representing all Declarations known by IAMS
     *         application.
     */
    public List<Declaration> getAll(){
    	return declarationDao.getAll();
    }
    
    /**
     * @return the list of Declaration as option
     */
    public List<Option> getDeclarationAsOptions(){
    	List<Option> options= new ArrayList<Option>();
    	List<Declaration> allDeclarations = this.getAll();
    	for(Declaration declaration : allDeclarations){
    		 options.add(new Option(declaration.getId().toString(),declaration.getCustomer()));
    	}
    	return options;
    }
    
    public Declaration getOneForExercise(String customer,String exercise){
    	return declarationDao.getOneForExercise(customer, exercise);
    }

    /**
     * Returns a Declaration object identified by given id.
     * 
     * @param id
     * @return Returns a Declaration object identified by given id.
     */
    public Declaration getOne(Long id){
    	return declarationDao.getOne(id);
    }
    
   public  Declaration  getOneDetached(Long id){
    	return declarationDao.getOneDetached(id);
    }
   
  
  
	 
    
    
 
    /**
     * Delete a Declaration object identified by given id.
     * 
     * @param id
     */
    public void deleteOne(Long id){
    	declarationDao.deleteOne(id);
    }
    
 
    /**
     * Returns a Declaration object identified by given id.
     * 
     * @param id
     * @return Returns a Declaration object identified by given id.
     */
   public Declaration saveOne(Declaration Declaration){
	   return declarationDao.saveOne(Declaration);
   }

    /**
     * Returns a Declaration object identified by given id.
     * 
     * @param id
     * @return Returns a Declaration object identified by given id.
     */
   public Declaration updateOne(Declaration Declaration){
    	return declarationDao.updateOne(Declaration);
    }

	public IDeclarationDao getDeclarationDao() {
		return declarationDao;
	}

	public void setDeclarationDao(IDeclarationDao declarationDao) {
		this.declarationDao = declarationDao;
	}
    
    

	
    
	
}
