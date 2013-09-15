package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.MissionTypeTaskLink;
import com.interaudit.domain.model.data.ContractData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchContractParam;

public interface IContractDao {

    // get a Contract by its id
    public Contract getOne(Long id);
    
    public Contract getOneDetached(Long id);
    
    public MissionTypeTaskLink getOneMissionTypeTaskLink(Long id);
    
    public Contract getOneFromCode(String reference);
    
    public int getNextContractSequence(Long idCustomer);

    // get all Contracts
    public List<Contract> getAll();
    
    public List<Contract> findActiveContracts();
    
    public List<Option> getMissionTypeOptions();
    
    public List<Option> getMissionTypeOptions2();

    // save a Contract
    public Contract saveOne(Contract Contract);

    // update a Contract
    public Contract updateOne(Contract Contract);

    // delete a Contract by its id
    public int deleteOne(Long id);
    
    public boolean existContractForCustomerAndType(Long customerId, String type);
    
    public void updateInvalideContracts();

    /**
     * 
     * @param userId
     * @return
     */
   // public List<Contract> getAllByUserId(Long userId);

    /**
     * 
     * @return
     */
   // public List<String> getAllDistinctContractCountries();

    /**
     * 
     * @return
     */
    //public List<String> getAllDistinctContractTypes();

    /**
     * 
     * @param searchContractParam
     * @return
     */
    public List<Contract> findContracts(SearchContractParam searchContractParam,int firstPos,int LINEPERPAGE);
    
    public List<ContractData> findContractsData(SearchContractParam param,int firstPos,int LINEPERPAGE);
    
    public long getTotalCount(SearchContractParam searchContractParam);
    
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
     * Dedeactivate all given Contracts.
     * @param Contracts the list of Contracts to be deactivated.
     */
    public void deactivateAll(List<Contract> Contracts);

}
