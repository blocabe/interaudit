package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.MissionTypeTaskLink;
import com.interaudit.domain.model.data.ContractData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchContractParam;



/**
 * @author bernard
 *
 */
/**
 * @author bernard
 *
 */
public interface IContractService {

    /**
     * Retrieves the Contact instance identified by the given id
     * from the persistence support.
     * @param id the id of the entity to be retrieved
     * @return the Contact instance identified by the given id
     * if it exists, or null otherwise.
     */
    public Contract getOne(Long id);
    
    public Contract getOneDetached(Long id);
    
	public boolean existContractForCustomerAndType(Long customerId, String type);
        
    public Contract createContract(Contract contract);
    
    public Contract createContract(com.interaudit.domain.model.Customer customer);
    
    public Contract updateContract(Contract contract);
    
    public Contract getOneFromCode(String reference);
    
    public List<String> getAllValidContractReference();
    
    public List<Option> getMissionTypeOptions();
    
    public List<Option> getMissionTypeOptions2();
    
    public MissionTypeTaskLink getOneMissionTypeTaskLink(Long id);

    /**
     * Retrieves all existing Contract instances.
     * @return a list containing all existing Contract instances.
     */
    public List<Contract> getAll();
    
    public List<Contract> findActiveContracts();
    
    
    public List<Option> getAllContractAsOptions();

    /**
     * Retrieves all Contract objects which reside within the
     * "my Contracts" collection of the JaspersUser identified by
     * the given id.
     * @param userId the id identifying the JaspersUser who's
     * Contracts we are looking for.
     * @return a list containing all Contracts which reside within 
     * the "my Contracts" collection of the given JaspersUser
     */
    public List<Contract> getAllByUserId(Long userId);

    /**
     * Adds a new created Contract to the persistence support.
     * @param Contract the Contract to be saved.
     * @return the given Contract after being persisted.
     */
    public Contract saveOne(Contract Contract);

    /**
     * Merges the given Contract object with its existing (persisted)
     * version.
     * @param Contract the Contract object to be updated
     * @return the given Contract after it is updated.
     */
    public Contract updateOne(Contract Contract);

    /**
     * Deletes the Contract object identified by the given id
     * (if it exists).
     * @param id the id of the object to be deleted.
     */
    public void deleteOne(Long id);

    
    /**
     * Search for Contract objects according to the filtering criteria
     * encapsulated by the given parameter.
     * @param searchContractParam
     * @return a list containing all the Contract objects which match
     * the filtering criteria
     */
    public List<ContractData> findContracts(SearchContractParam searchContractParam,int firstPos,int LINEPERPAGE);

    long getTotalCount(SearchContractParam searchContractParam);
    
    /**
     * Delete all given Contracts.
     * @param Contracts the list of Contracts to be deleted.
     */
    public void deleteAll(List<Contract> Contracts);
    
    /**
     * Activate all given Contracts.
     * @param Contracts the list of Contracts to be activated.
     */
    public void activateAll(List<Contract> Contracts);
    
    /**
     * Deactivate all given Contracts.
     * @param Contracts the list of Contracts to be deactivated.
     */
    public void deactivateAll(List<Contract> Contracts);
    
    public void updateInvalideContracts();
}
