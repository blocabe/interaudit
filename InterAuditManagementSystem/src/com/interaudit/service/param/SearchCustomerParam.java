package com.interaudit.service.param;


public class SearchCustomerParam {
	
	private String customerCode;
	private String managerName = "";
	private String associeName = "";
	private String nameLike = "";
	private String startingLetterName;
	
	
	
	public SearchCustomerParam(String customerCode) {
		super();
		this.customerCode = customerCode;
	}

	public SearchCustomerParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchCustomerParam(String managerName, String nameLike,
			String startingLetterName,String associeName) {
		super();
		this.managerName = managerName;
		this.nameLike = nameLike;
		this.startingLetterName = startingLetterName;
		this.associeName = associeName;
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
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getAssocieName() {
		return associeName;
	}

	public void setAssocieName(String associeName) {
		this.associeName = associeName;
	}

	
	
	
	
	

}
