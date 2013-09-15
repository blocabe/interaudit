package com.interaudit.service;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Mission;
import com.interaudit.service.exc.BusinessException;



/**
 * Service methods for handling project details.
 * 
 * @author Daniel Doboga
 * 
 */
public interface IProjectDetailsService {
    /**
     * 
     * @param projectId
     * @param currentUser
     * @return Returns an object encapsulating information that must be
     *         displayed within project details screen.
     
    public ProjectDetailsView getProjectDetailsView(Long projectId,
            User currentUser);
            */

    /**
     * 
     * @param id
     *            Project identifier.
     * @return Returns a Project object identified by given id.
     */
    public Mission getProject(Long id);

    // /**
    // *
    // * @return
    // */
    // public List<ProjectRiskLevel> getAllRiskLevels();

    /**
     * Updates a project.
     * 
     * @param project
     *            project to be updated.
     * @param loggedUser
     *            user who modifies given project.
     * @return Updated Project
     * @throws BusinessException
     *             Throws BusinessException in case of concurrent update.
     */
    public Mission updateProject(Mission project, Employee loggedUser)
            throws BusinessException;
}
