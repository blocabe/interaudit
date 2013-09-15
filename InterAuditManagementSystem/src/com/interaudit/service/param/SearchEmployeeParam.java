package com.interaudit.service.param;


public class SearchEmployeeParam {
	
	private String employeeCode;
	public SearchEmployeeParam(String employeeCode) {
		super();
		this.employeeCode = employeeCode;
	}
	private String roleName;
	private String nameLike;
	private String startingLetterName;
	
	public SearchEmployeeParam() {
		super();
	}
	
	public SearchEmployeeParam(String roleName, String nameLike,
			String startingLetterName) {
		super();
		this.roleName = roleName;
		this.nameLike = nameLike;
		this.startingLetterName = startingLetterName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getNameLike() {
		return nameLike;
	}
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}
	public String getStartingLetterName() {
		return startingLetterName;
	}
	public void setStartingLetterName(String startingLetterName) {
		this.startingLetterName = startingLetterName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	
	
	
	
	

}
