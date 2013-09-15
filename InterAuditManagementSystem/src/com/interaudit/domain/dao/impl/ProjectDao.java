package com.interaudit.domain.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.interaudit.domain.dao.IProjectDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Mission;

public class ProjectDao  implements IProjectDao {
    
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * @param id identifier of theproject to delete
	 * @return nothing an exception is thrown incase of problems
	 */
	
    public void deleteOne(Long id) {
        Mission project = (Mission)em.find(Mission.class, id);
        if (project == null) {
            throw new DaoException(2);
        }
        em.remove(project);
    }

    // customs
    /*
    public int getMaxYearSequence(String year) {
        Number result = (Number) em
                .createQuery(
                        "select max(p.yearSequence) from Project p where p.year = :year")
                .setParameter("year", year).uniqueResult();
        if ( result == null) {
            return 0;
        } else {
            return result.intValue();
        }
    }
    */

    // generic
    /**
	 * @return all registered projects
	 */
    @SuppressWarnings("unchecked")
    
    public List<Mission> getAll() {
        return em.createQuery("select p from Mission p").getResultList();
    }

    /**
	 * @param model Search all project having their title matching the model
	 * @return a list of matching projects
	 */
    @SuppressWarnings("unchecked")
    
    public List<Mission> getAllTitleLike(String model) {
        return em.createQuery(
                "select p from Mission p where p.title like :model")
                .setParameter("model", model).getResultList();
    }
    
    /**
	 * @param model Search all project having their reference matching the model
	 * @return a list of matching projects
	 */
    @SuppressWarnings("unchecked")
    
    public List<Mission> getAllReferenceLike(String model) {
        return em.createQuery(
                "select p from Mission p where p.reference like :model order by p.projectReference")
                .setParameter("model", model).getResultList();
    }
    
    /*
    @SuppressWarnings("unchecked")    
    public List<Project> getAllByCountryAndYearAndStatusNotIn(String year, String country, Collection<String> statusesToAvoid) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = statusesToAvoid.size();
        for(int i=0; i<size; i++) {
            if(i != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(":status_" + i);
        }
        Query query = em.createQuery("select p from Project p where p.mainCountry.id = :countryId and p.year = :year and p.status.id not in (" 
                + stringBuilder.toString() + ")");
        query.setParameter("countryId", country);
        query.setParameter("year", year);
        int count = 0;
        for(String status : statusesToAvoid) {
            query.setParameter("status_" + count++, status);
        }
        
        return query.list();
    }
    */


    
    public Mission getOne(Long id) {
        return (Mission) em.find(Mission.class, id);
    }

    
    public Mission saveOne(Mission project) {
    	em.persist(project);
        return project;
    }

    
    public Mission updateOne(Mission project) {
        return (Mission) em.merge(project);
    }
    
    
    public int updateProjectStatus(Mission project) {
        Query query = em.createQuery("update Mission p set p.status = :newStatus where p.id = :id");
        query.setParameter("newStatus", project.getStatus());
        query.setParameter("id", project.getId());
        return query.executeUpdate();
    }
/*
    @SuppressWarnings("unchecked")
    public List<Project> findProjects(SearchParam searchParam) {
        StringBuilder where = new StringBuilder();
        StringBuilder from = new StringBuilder("Project p");
        Map<String, Object> parameters = new HashMap<String, Object>(10);
        Long[] ids = null;
        StringBuilder managersFilter = new StringBuilder();

        if (searchParam.getTaskManagerIds() != null) {
            from
                    .append(" JOIN p.teamMembers taskManager JOIN taskManager.roleInTeam rtaskManager");
            managersFilter
                    .append("rtaskManager.name=:taskManagerRoleName AND taskManager.user.id IN (");
            ids = searchParam.getTaskManagerIds();
            for (int i = 0; i < ids.length; i++) {
                if (i > 0) {
                    managersFilter.append(",");
                }
                managersFilter.append(":taskManagerId" + i);
                parameters.put("taskManagerId" + i, ids[i]);
            }
            managersFilter.append(")");
            
            parameters.put("taskManagerRoleName",
                    Constants.ROLE_NAME_TASK_MANAGER);
                   
        }

        if (searchParam.getSectorManagerIds() != null) {
            from
                    .append(" JOIN p.teamMembers sectorManager JOIN sectorManager.roleInTeam rsectorManager");
            if (managersFilter.length() > 0) {
                managersFilter.append(" AND ");
            }
            managersFilter
                    .append("rsectorManager.name=:sectorManagerRoleName AND sectorManager.user.id IN (");
            ids = searchParam.getSectorManagerIds();
            for (int i = 0; i < ids.length; i++) {
                if (i > 0) {
                    managersFilter.append(",");
                }
                managersFilter.append(":sectorManagerId" + i);
                parameters.put("sectorManagerId" + i, ids[i]);
            }
            managersFilter.append(")");
            
            parameters.put("sectorManagerRoleName",
                    Constants.ROLE_NAME_SECTOR_MANAGER);
                    
        }
        if (searchParam.getRegionalManagerIds() != null) {
            from
                    .append(" JOIN p.teamMembers regionalManager JOIN regionalManager.roleInTeam rregionalManager");
            if (managersFilter.length() > 0) {
                managersFilter.append(" AND ");
            }
            managersFilter
                    .append("rregionalManager.name=:regionalManagerRoleName AND regionalManager.user.id IN (");
            ids = searchParam.getRegionalManagerIds();
            for (int i = 0; i < ids.length; i++) {
                if (i > 0) {
                    managersFilter.append(",");
                }
                managersFilter.append(":regionalManagerId" + i);
                parameters.put("regionalManagerId" + i, ids[i]);
            }
            managersFilter.append(")");
            
            parameters.put("regionalManagerRoleName",
                    Constants.ROLE_NAME_OPERATIONAL_MANAGER);
                    
        }

        if (searchParam.getExpression() != null) {
            if (where.length() > 0) {
                where.append(" AND ");
            }
            where
                    .append("(UPPER(p.title) LIKE upper(:expression)")
                    .append(" OR UPPER(p.temporaryReference) LIKE upper(:expression)")
                    .append(" OR UPPER(p.projectReference) LIKE upper(:expression))");
            parameters.put("expression", "%" + searchParam.getExpression()
                    + "%");
        }
        if (searchParam.getYear() != null) {
            from.append(" JOIN p.actionPlans ap");
            if (where.length() > 0) {
                where.append(" AND");
            }
            where.append(" ap.year=:year");
            parameters.put("year", searchParam.getYear());
        }
        if (searchParam.getCountryId() != null) {
            if (where.length() > 0) {
                where.append(" AND");
            }
            where.append(" p.mainCountry.id=:countryId");
            parameters.put("countryId", searchParam.getCountryId());
        }
        if (searchParam.getSectorId() != null) {
            if (where.length() > 0) {
                where.append(" AND");
            }
            where.append(" p.relatedSector.id=:sectorId");
            parameters.put("sectorId", searchParam.getSectorId());
        }
        if (searchParam.getSubsectorId() != null) {
            if (where.length() > 0) {
                where.append(" AND");
            }
            where.append(" p.relatedSubSector.id=:subsectorId");
            parameters.put("subsectorId", searchParam.getSubsectorId());
        }
        if (searchParam.getProjectStatusId() != null) {
            if (where.length() > 0) {
                where.append(" AND");
            }
            where.append(" p.status.id=:projectStatusId");
            parameters.put("projectStatusId", searchParam.getProjectStatusId());
        }
        if (searchParam.getProjectOfficeId() != null) {
            if (where.length() > 0) {
                where.append(" AND");
            }
            where.append(" p.office.id=:projectOfficeId");
            parameters.put("projectOfficeId", searchParam.getProjectOfficeId());
        }
        if (searchParam.getExcludedProjectStatusId() != null) {
            if (where.length() > 0) {
                where.append(" AND");
            }
            where.append(" p.status.id <> :exlcuededProjectStatusId");
//            where.append(" p.status.id not in (SELECT prjSts.id FROM ProjectStatus prjSts WHERE prjSts.code=:projectStatusCodeRejected) ");
            parameters.put("exlcuededProjectStatusId", searchParam.getExcludedProjectStatusId());
        }

        StringBuilder hql = new StringBuilder(
                "SELECT DISTINCT NEW lu.intrasoft.jaspers.entities.Project(p.id, p.temporaryReference, p.projectReference, p.title, p.year, p.relatedSector, p.status, p.yearSequence, p.mainCountry, p.acceptedDate, p.relatedSubSector, p.evolution, p.suspendedDate, p.cancelledDate, p.jaspActComplDate, p.firstExpectedDate, p.lastExpectedDate, p.office, p.totalCost, p.state) FROM ");
        hql.append(from);
        //force left join rules because the joins done by default are not achieving projects with null attributes (relatedSector, mainCountry etc.)
        hql.append(" LEFT JOIN p.relatedSector sect LEFT JOIN p.relatedSubSector subsect LEFT JOIN p.mainCountry ctry LEFT JOIN p.status sts LEFT JOIN p.state ste LEFT JOIN p.evolution evl LEFT JOIN p.office o ");
        if (where.length() > 0) {
            if (managersFilter.length() > 0) {
                where.append(" AND ").append(managersFilter);
            }
        } else {
            if (managersFilter.length() > 0) {
                where.append(managersFilter);
            }
        }
                
        if (where.length() > 0) {
            hql.append(" WHERE ").append(where);
        }
        Query q = em.createQuery(hql.toString());

        for (Map.Entry<String, Object> me : parameters.entrySet()) {
            q.setParameter(me.getKey(), me.getValue());
        }

        return q.list();
    }
    */

    
    @SuppressWarnings("unchecked")
    
    public List<Mission> findProjectsForUserId(Long userId) {

        return em
                .createQuery(
                        "SELECT DISTINCT p FROM Mission p JOIN p.teamMembers tm WHERE tm.user.id=:userId")
                .setParameter("userId", userId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<String> getAllDistinctProjectYears() {

        return em.createQuery(
                "SELECT DISTINCT p.year FROM Project p ORDER BY p.year")
                .getResultList();
    }
    

    /*
    @SuppressWarnings("unchecked")
    public List<String> getAllDistinctProjectCountries() {
        return (List<String>) em
                .createQuery(
                        "SELECT DISTINCT c FROM Project p JOIN p.mainCountry c ORDER BY c.description")
                .list();
    }
    */

    
    
   /*
    
    @SuppressWarnings("unchecked")
    public List<String> getAllDistinctProjectStatuses() {
        
        return (List<String>)em.createQuery("SELECT DISTINCT ps FROM Project p JOIN p.status ps ORDER BY ps.projectStatus").list();
    }
    */

    
    

   /*
    public Document getDocumentByEntityIdAndFileName(Long projectId,
            String fileName) {
        return (Document) em
                .createQuery(
                        "SELECT d FROM Project p JOIN p.documents d WHERE p.id=:projectId AND LOWER(d.fileName)=LOWER(:fileName)")
                .setParameter("projectId", projectId).setParameter(
                        "fileName", fileName).uniqueResult();
    }
    */
}
