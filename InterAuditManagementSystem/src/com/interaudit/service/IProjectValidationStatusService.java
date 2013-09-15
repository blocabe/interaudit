package com.interaudit.service;

import java.util.List;



/**
 * TODO delete this class
 * @author Philippe WEBER 
 * 
 */
public interface IProjectValidationStatusService {

    // get a validationStatus from its id
    public String getOne(Long id);

    // get all validationStatuss
    public List<String> getAll();

    // save a validationStatus
    public String saveOne(String validationStatus);

    // update a validationStatus
    public String updateOne(String validationStatus);

    // delete a validationStatus from its id
    public void deleteOne(Long id);

    // get all validationStatus where their title is like the model
    public List<String> getAllLike(String model);

    // delete several validationStatus at a time
    public void deleteArray(String[] validationStatuss);

    // save several validationStatus at a time
    public String[] saveArray(String[] validationStatuss);

    // update several validationStatus together
    public String[] updateArray(String[] validationStatuss);
}
