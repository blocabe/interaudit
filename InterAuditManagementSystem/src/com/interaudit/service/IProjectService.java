package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Document;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Mission;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.param.SearchParam;
import com.interaudit.service.view.MyProjectsView;

/**
 * Model describing service methods that must be provided in order to handle
 * details of a Project.
 * 
 * @author Valentin Carnu
 * 
 */
public interface IProjectService {

    public String getProjectReference(Mission project);

    /**
     * 
     * @param userId
     * @return Project objects list where user identified by given userId is
     *         team member.
     */
    public List<Mission> findProjectsForUserId(Long userId);

    /**
     * 
     * @param tempReference
     * @param year
     * @param relatedSector
     * @param relatedSubSector
     * @param mainCountry
     * @param title
     * @param evolution
     * @param state
     * @param issuesAffectingProjectPreparation
     * @param issuedAffectionJaspersAssignement
     * @param objectives
     * @param objectivesInput
     * @param scopeWork
     * @return
    
    public Mission createOne(String tempReference, String year,
            Sector relatedSector, SubSector relatedSubSector,
            Country mainCountry, String title, ProjectEvolution evolution,
            ProjectCEState state, String issuesAffectingProjectPreparation,
            String issuedAffectionJaspersAssignement, String objectives,
            String objectivesInput, String scopeWork);
             */

    /**
     * get all projects
     * 
     * @return Project objects list.
     */
    public List<Mission> getAll();

    /**
     * get a project from its id
     * 
     * @param id
     * @return
     */
    public Mission getOne(Long id);

    /**
     * save a project
     * 
     * @param project
     * @return
     */
    public Mission saveOne(Mission project);

    /**
     * update a project
     * 
     * @param project
     * @return
     */
    public Mission updateOne(Mission project);

    /**
     * delete a project from its id
     * 
     * @param id
     */
    public void deleteOne(Long id);

    /**
     * Validates the project, which means: if the project should be rejected,
     * then the status is set to REJECTED and rejectedDate to current date if
     * actual status is REQUESTED or the status is set to CANCELLED and
     * cancelledDate to current date if actual status is not REQUESTED, if the
     * project should be accepted, then setS the acceptedDate to current date
     * and sets the status to "ACCEPTED" if the actual status is requested, or
     * it preserves the actual status if it is different from requested.
     * 
     * @param project
     */
    public void validateProject(Mission project);

    /**
     * get all project where their title is like the model
     * 
     * @param model
     * @return
     */
    public List<Mission> getAllLike(String model);

    /**
     * get all project where their reference is like the model
     * 
     * @param model
     * @return
     */
    public List<Mission> getAllReferenceLike(String model);

    /**
     * delete several project at a time
     * 
     * @param projects
     */
    public void deleteArray(Mission[] projects);

    /**
     * save several project at a time
     * 
     * @param projects
     * @return
     */
    public Mission[] saveArray(Mission[] projects);

    /**
     * update several project together
     * 
     * @param projects
     * @return
     */
    public Mission[] updateArray(Mission[] projects);

    /**
     * 
     * @param searchParam
     * @return
     */
    public List<Mission> findProjects(SearchParam searchParam);

    /**
     * 
     * @param userId
     * @return
     */
    public MyProjectsView getMyProjectsView(Long userId);

    /**
     * 
     * @return String objects list encapsulating years for which projects
     *         exists.
     */
    public List<String> getAllDistinctProjectYears();

    

    

    /**
     * 
     * @return ProjectStatus objects list representing all ProjectStatus for
     *         which projects exists.
     */
    public List<String> getAllDistinctProjectStatuses();

    

    /**
     * 
     * @return Returns an object encapsulating information displayed on project
     *         details screen.
     
    public ProjectView getProjectView();
    */

    /**
     * 
     * @param project
     * @param jaspersUser
     * @return
     * @throws BusinessException
     *             Throws BusinessException encapsulating error messages for
     *             each property of given project which has not a proper value.
     */
    public Mission saveProject(Mission project, Employee user)
            throws BusinessException;

    /**
     * 
     * @param year
     * @return
     */
    public Integer getMaxYearSequence(String year);

    /**
     * 
     * @param projectId
     * @param fileName
     * @return
     */
    public Document getDocumentByEntityIdAndFileName(Long projectId,
            String fileName);
}
