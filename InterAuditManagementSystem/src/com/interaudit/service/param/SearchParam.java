package com.interaudit.service.param;


public class SearchParam {
    private String searchScope;
    private String expression;
    private String year;
    private Long countryId;
    private Long sectorId;
    private Long subsectorId;
    private Long projectStatusId;
    private Long[] regionalManagerIds;
    private Long[] sectorManagerIds;
    private Long[] taskManagerIds;
    private Long projectOfficeId;
    private Long excludedProjectStatusId;

    public String getSearchScope() {
        return searchScope;
    }

    public void setSearchScope(String searchScope) {
        this.searchScope = searchScope;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getProjectStatusId() {
        return projectStatusId;
    }

    public void setProjectStatusId(Long projectStatusId) {
        this.projectStatusId = projectStatusId;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public Long getSubsectorId() {
        return subsectorId;
    }

    public void setSubsectorId(Long subsectorId) {
        this.subsectorId = subsectorId;
    }

    public Long[] getRegionalManagerIds() {
        return regionalManagerIds;
    }

    public void setRegionalManagerIds(Long[] regionalManagerIds) {
        this.regionalManagerIds = regionalManagerIds;
    }

    public Long[] getSectorManagerIds() {
        return sectorManagerIds;
    }

    public void setSectorManagerIds(Long[] sectorManagerIds) {
        this.sectorManagerIds = sectorManagerIds;
    }

    public Long[] getTaskManagerIds() {
        return taskManagerIds;
    }

    public void setTaskManagerIds(Long[] taskManagerIds) {
        this.taskManagerIds = taskManagerIds;
    }

	public Long getProjectOfficeId() {
		return projectOfficeId;
	}

	public void setProjectOfficeId(Long projectOfficeId) {
		this.projectOfficeId = projectOfficeId;
	}

    public Long getExcludedProjectStatusId() {
        return excludedProjectStatusId;
    }

    public void setExcludedProjectStatusId(Long excludedProjectStatusId) {
        this.excludedProjectStatusId = excludedProjectStatusId;
    }
}
