package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IProjectDao;
import com.interaudit.domain.model.Document;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Mission;
import com.interaudit.service.IDocumentService;
import com.interaudit.service.IProjectService;
import com.interaudit.service.IRoleService;
import com.interaudit.service.IUserService;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchParam;
import com.interaudit.service.view.MyProjectsView;

//All methods of this class take place in a transaction
@Transactional
public class ProjectService implements IProjectService {

    private Log log = LogFactory.getLog(ProjectService.class);
    private IProjectDao projectDao;
    private IUserService jaspersUserService;
    private IRoleService roleService;
    private IDocumentService documentService;
    private Integer numberOfYears;
    private int maxYearSequence;

    public IProjectDao getProjectDao() {
        return projectDao;
    }

    public void setProjectDao(IProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    // Pure Business services
    public String getProjectReference(Mission project) {
        // FIXME unused service can be removed
        return project.getReference();
    }

    public List<Mission> findProjectsForUserId(Long userId) {
       // return projectDao.findProjectsForUserId(userId);
    	return null;
    }

    // Custom services
    /*
    public Project createOne(String tempReference, String year,
            Sector relatedSector, SubSector relatedSubSector,
            Country mainCountry, String title, ProjectEvolution evolution,
            ProjectCEState state, String issuesAffectingProjectPreparation,
            String issuedAffectionJaspersAssignement, String objectives,
            String objectivesInput, String scopeWork) {

        int yearSequence = this.getMaxYearSequence(year) + 1;

        Project project = new Project(tempReference, year, yearSequence,
                relatedSector, relatedSubSector, mainCountry, title, evolution,
                state, issuesAffectingProjectPreparation,
                issuedAffectionJaspersAssignement, objectives, objectivesInput,
                scopeWork);

        this.saveOne(project);
        return project;
    }
    */

    // Services
    public void deleteArray(Mission[] projects) {
        for (Mission p : projects) {
            this.deleteOne(p.getId());
        }
    }

    public void deleteOne(Long id) {
        projectDao.deleteOne(id);
    }

    public List<Mission> getAll() {
        return projectDao.getAll();
    }

    public List<Mission> getAllLike(String model) {
       // return projectDao.getAllLike(model);
    	return null;
    }

    public List<Mission> getAllReferenceLike(String model) {
        return projectDao.getAllReferenceLike(model);
    }

    public Mission getOne(Long id) {
        return projectDao.getOne(id);
    }

    public Mission[] saveArray(Mission[] projects) {
    	Mission[] projects2 = new Mission[projects.length];
        for (int i = 0; i < projects.length; i++) {
            projects2[i] = this.saveOne(projects[i]);
        }
        return projects2;
    }

    public Mission saveOne(Mission project) {
        return projectDao.saveOne(project);
    }

    public Mission[] updateArray(Mission[] projects) {
        Mission[] projects2 = new Mission[projects.length];
        for (int i = 0; i < projects.length; i++) {
            projects2[i] = this.updateOne(projects[i]);
        }
        return projects2;
    }

    public Mission updateOne(Mission project) {
        project.setUpdateDate(Calendar.getInstance().getTime());
        return projectDao.updateOne(project);
    }

    public List<Mission> findProjects(SearchParam searchParam) {

       // return projectDao.findProjects(searchParam);
    	return null;
    }

    public MyProjectsView getMyProjectsView(Long userId) {
        MyProjectsView view = new MyProjectsView();
       // view.setProjects(projectDao.findProjectsForUserId(userId));
        return view;
    }

    /*
    public ProjectView getProjectView() {
        ProjectView view = new ProjectView();
        List<String> years = new ArrayList<String>();
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < this.numberOfYears; i++) {
            years.add("" + (currentYear + i));
        }
        view.setYears(years);
        view.setCountries(countryService.getAllInEU());
        view.setSectors(sectorService.getAll());
        view.setSubsectors(subSectorService.getAll());
        view.setJaspersUsers(jaspersUserService.getAll());
        view.setOffices(officeService.getAll());
        view.setProjectTargetFunds(projectTargetFundService.getAll());
        view.setProjectTypes(projectTypeService.getAll());
        return view;
    }
    */

    public Mission saveProject(Mission project, Employee jaspersUser)
            throws BusinessException {
        List<ExceptionMessage> exceptionMessages = new ArrayList<ExceptionMessage>();
        
        /*
        // populate the team members with Role objects containing the value for
        // id property.
        Map<String, Profile> roleMap = roleService.getRolesMappedByName();
        Set<TeamMember> newTeamMembers = new HashSet<TeamMember>();
        for (TeamMember teamMember : project.getTeamMembers()) {
            if (teamMember != null) {
                teamMember.setRoleInTeam(roleMap.get(teamMember.getRoleInTeam()
                        .getName()));
                newTeamMembers.add(teamMember);
            }
        }
        project.setTeamMembers(newTeamMembers);

        Date now = Calendar.getInstance().getTime();
        project.setStatus(
                Constants.PROJECT_STATUS_CODE_REQUESTED);
        project.setCreateDate(now);
        project.setUpdatedUser(jaspersUser.getUserName());
        project.setUpdateDate(now);
        //project.setVersion(Constants.VERSION_NUMBER_FIRST_VALUE);
        
        // set year sequence
        int yearSequence = 0;this.getMaxYearSequence(project.getYear());
        if (yearSequence >= maxYearSequence) {
            yearSequence = 0;
        } else {
            yearSequence++;
        }
       // project.setYearSequence(yearSequence);

        // build reference number
        StringBuilder sb = new StringBuilder();
        
        sb.append(project.getYear()).append(
                Constants.PROJECT_REFERENCE_SEPARATOR).append(
                Constants.YEAR_SEQUENCE_FORMATTER.format(new Object[] { project
                        .getYearSequence() }, new StringBuffer(""),
                        new FieldPosition(0))).append(
                Constants.PROJECT_REFERENCE_SEPARATOR).append(
                project.getMainCountry().getIsoCode()).append(
                Constants.PROJECT_REFERENCE_SEPARATOR).append(
                project.getRelatedSector().getShortDescription()).append(
                Constants.PROJECT_REFERENCE_SEPARATOR).append(
                project.getRelatedSubSector().getShortDescription());
        project.setTemporaryReference(sb.toString());
        
        project.setReference(sb.toString());

        // validate the project.
        if (project.getTitle() == null) {
            exceptionMessages.add(new ExceptionMessage(
                    BusinessMessagesConstants.ERROR_PROJECT_EMPTY_TITLE));
        }
        /*
        if (project.getYear() == null) {
            exceptionMessages.add(new ExceptionMessage(
                    BusinessMessagesConstants.ERROR_PROJECT_EMPTY_YEAR));
        }
        if (project.getMainCountry() == null) {
            exceptionMessages.add(new ExceptionMessage(
                    BusinessMessagesConstants.ERROR_PROJECT_EMPTY_COUNTRY));
        }
        if (project.getRelatedSector() == null) {
            exceptionMessages.add(new ExceptionMessage(
                    BusinessMessagesConstants.ERROR_PROJECT_EMPTY_SECTOR));
        }
        if (project.getRelatedSubSector() == null) {
            exceptionMessages.add(new ExceptionMessage(
                    BusinessMessagesConstants.ERROR_PROJECT_EMPTY_SUBSECTOR));
        }
        if (project.getOffice() == null) {
            exceptionMessages.add(new ExceptionMessage(
                    BusinessMessagesConstants.ERROR_PROJECT_EMPTY_OFFICE));
        }
        if (project.getProjectType() == null) {
            exceptionMessages.add(new ExceptionMessage(
                    BusinessMessagesConstants.ERROR_PROJECT_EMPTY_PROJECT_TYPE));
        }

        if (project.getRegionalManager() == null) {
            exceptionMessages
                    .add(new ExceptionMessage(
                            BusinessMessagesConstants.ERROR_PROJECT_EMPTY_REGIONAL_MANAGER));
        }
        if (project.getSectorManager() == null) {
            exceptionMessages
                    .add(new ExceptionMessage(
                            BusinessMessagesConstants.ERROR_PROJECT_EMPTY_SECTOR_MANAGER));
        }
        if (project.getActionPlans() == null
                || project.getActionPlans().size() == 0) {
            exceptionMessages.add(new ExceptionMessage(
                    BusinessMessagesConstants.ERROR_PROJECT_EMPTY_ACTION_PLAN));
        }
        if (exceptionMessages.size() > 0) {
            throw new BusinessException(exceptionMessages);
        }
        */

        return this.saveOne(project);
    }

    public void validateProject(Mission project) {
        Date today = Calendar.getInstance().getTime();
        Mission persistedInstance = projectDao.getOne(project.getId());
        /*
        String newProjectStatus = projectStatusService.getOne(project
                .getStatus().getId());
        ProjectStatus oldProjectStatus = persistedInstance.getStatus();

        persistedInstance
                .setProjectReference(getProjectReferenceFromTempReference(persistedInstance));

        if (Constants.PROJECT_STATUS_CODE_ACCEPTED.equals(newProjectStatus
                .getCode())) {
            if (Constants.PROJECT_STATUS_CODE_REQUESTED.equals(oldProjectStatus
                    .getCode())) {
                persistedInstance.setStatus(projectStatusService
                        .getOneByCode(Constants.PROJECT_STATUS_CODE_ACCEPTED));
            }

            persistedInstance.setAcceptedDate(today);

        } else if (Constants.PROJECT_STATUS_CODE_REJECTED.equals(newProjectStatus.getCode())) {
            if (Constants.PROJECT_STATUS_CODE_REQUESTED.equals(oldProjectStatus.getCode())) {

                persistedInstance.setStatus(projectStatusService
                        .getOneByCode(Constants.PROJECT_STATUS_CODE_REJECTED));
                persistedInstance.setRejectedDate(today);

            } else {

                persistedInstance.setStatus(projectStatusService
                        .getOneByCode(Constants.PROJECT_STATUS_CODE_CANCELLED));
                persistedInstance.setCancelledDate(today);

            }
        } else if (Constants.PROJECT_STATUS_CODE_HOLD.equals(newProjectStatus.getCode())) {
            persistedInstance.setStatus(newProjectStatus);
        } else {
            throw new IllegalStateException("Project with status "
                    + newProjectStatus.getCode()
                    + " not allowed to be validated!");
        }
        */
        this.saveOne(persistedInstance);
    }

    private String getProjectReferenceFromTempReference(Mission project) {
    	/*
        return project
                .getTemporaryReference()
                .replace(
                        (Constants.PROJECT_TEMPORARY_REFERENCE_PREFIX + Constants.PROJECT_REFERENCE_SEPARATOR),
                        "");
                        */
    	return null;
    }

    public Integer getMaxYearSequence(String year) {

        //return projectDao.getMaxYearSequence(year);
    	return null;
    }

    public List<String> getAllDistinctProjectYears() {
        //return projectDao.getAllDistinctProjectYears();
    	return null;
    }

    

    public List<String> getAllDistinctProjectStatuses() {

       // return projectDao.getAllDistinctProjectStatuses();
    	return null;
    }

    

    public IUserService getJaspersUserService() {
        return jaspersUserService;
    }

    public void setJaspersUserService(IUserService jaspersUserService) {
        this.jaspersUserService = jaspersUserService;
    }

    
    

    public IRoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    public Integer getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(Integer numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    

    public int getMaxYearSequence() {
        return maxYearSequence;
    }

    public void setMaxYearSequence(int maxYearSequence) {
        this.maxYearSequence = maxYearSequence;
    }

   
    
    public IDocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(IDocumentService documentService) {
        this.documentService = documentService;
    }

   

	
    public Document getDocumentByEntityIdAndFileName(Long entityId,
            String fileName) {
        Document document = null;
        try {
        	/*
            document = projectDao.getDocumentByEntityIdAndFileName(entityId,
                    fileName);
                    */
        } catch (NoResultException e) {
            log.debug("No document found for project with id = " + entityId
                    + " and fileName = " + fileName);
        }
        return document;
    }
//
//    @Override
//    public JaspersDocumentsView getDocumentsView(Long projectId,
//            Document document, JaspersUser jaspersUser) {
//        Project project = this.getOne(projectId);
//        JaspersDocumentsView view = new JaspersDocumentsView();
//        view.setJaspersEntity(jaspersEntityService.getOneByCode(Constants.JASPERS_ENTITY_CODE_PROJECT));
//        view.setDocumentedEntity(project);
//        Map<DocumentListMetaInf, List<Document>> documentsMap = new HashMap<DocumentListMetaInf, List<Document>>();
//        DocumentListMetaInf key = view.new DocumentListMetaInf();
//        List<Document> documents = new ArrayList<Document>();
//        boolean isExternalUser = (false == Constants.USER_TYPE_JASPERS.equals((jaspersUser.getUserType().getUserType())));
////        boolean isCurrentUserTeamMember = false;
////        Long currentUserId = jaspersUser.getId();
////        Set<TeamMember> teamMembers = project.getTeamMembers();
////        for (TeamMember t : teamMembers) {
////            if (currentUserId.equals(t.getUser().getId())) {
////                isCurrentUserTeamMember = true;
////                break;
////            }
////        }
//        for (Document d : project.getDocuments()) {
//            if (isExternalUser) {
//                if (d.isAllowExternalAccess()) {
//                    documents.add(d);
//                }
//            } else {
//                documents.add(d);
//            }
//        }
//        documentsMap.put(key, documents);
//        view.setDocuments(documentsMap);
//        if (document.getId() != null) {
//            view.setDocument(documentService.getOne(document.getId()));
//        }
//        return view;
//    }
}
