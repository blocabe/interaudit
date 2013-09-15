package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.Mission;

public interface IProjectDao {

	
	
	/**
	 * @param year
	 * @return
	 */
	//public int getMaxYearSequence(String year);
	
	
	/**
	 * @param id
	 * @return
	 */
	public Mission getOne(Long id);

	
	/**
	 * @return
	 */
	public List<Mission> getAll();

	
	/**
	 * @param actionPlan
	 * @return
	 */
	public Mission saveOne(Mission actionPlan);

	
	/**
	 * @param actionPlan
	 * @return
	 */
	public Mission updateOne(Mission actionPlan);
	
	
	/**
	 * @param project
	 * @return
	 */
	public int updateProjectStatus(Mission project);

	
	/**
	 * @param id
	 */
	public void deleteOne(Long id);

	
	/**
	 * @param model
	 * @return
	 */
	public List<Mission> getAllTitleLike(String model);
	
	/**
	 * @param model
	 * @return
	 */
	public List<Mission> getAllReferenceLike(String model);
	
	/**
	 * Get all projects for the given year and country and which have their statuses
	 * not residing within the given statusesToAvoid collection.
	 * @param year
	 * @param country
	 * @param statusesToAvoid
	 * @return
	 */
	//public List<Project> getAllByCountryAndYearAndStatusNotIn(String year, String country, Collection<String> statusesToAvoid);
	
	/**
	 * 
	 * @param searchParam
	 * @return
	 */
	//public List<Project> findProjects(SearchParam searchParam);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<Mission> findProjectsForUserId(Long userId);
	
	/**
	 * @return
	 */
	//public List<String> getAllDistinctProjectYears();
	
	/**
	 * @return
	 */
	//public List<String> getAllDistinctProjectCountries();
	
	
	/**
	 * @return
	 */
	//public List<String> getAllDistinctProjectStatuses();
	
	/**
	 * @param projectId
	 * @param fileName
	 * @return
	 */
	//public Document getDocumentByEntityIdAndFileName(Long projectId, String fileName);
}
