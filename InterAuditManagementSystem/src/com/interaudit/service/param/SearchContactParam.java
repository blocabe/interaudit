package com.interaudit.service.param;

public class SearchContactParam {
	
	
	private String customerName;
	private String nameLike;
	private String startingLetterName;
	
	
	public SearchContactParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchContactParam(String customerName, String nameLike,
			String startingLetterName) {
		super();
		this.customerName = customerName;
		this.nameLike = nameLike;
		this.startingLetterName = startingLetterName;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	
}
