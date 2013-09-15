package com.interaudit.service.param;

import java.io.Serializable;
import java.util.List;

public class SearchBudgetParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Integer> years;
	private String type;
	private Long origin;
	private Long manager;
	private Long associe;
	private String customerStartString;
	private Integer page;
	private boolean toClose;
	
	public SearchBudgetParam(String type, Long origin, Long manager,
			Long associe,boolean toClose) {
		super();
		this.type = type;
		this.origin = origin;
		this.manager = manager;
		this.associe = associe;
		this.toClose =  toClose;
	}
	
	public SearchBudgetParam(String type, Long origin, Long manager,
			Long associe,List<Integer> years,boolean toClose) {
		this(type,origin,manager,associe,toClose);
		this.years = years;
	}

	
	public SearchBudgetParam(String type, Long origin, Long manager,
			Long associe,List<Integer> years,String customerStartString,Integer p,boolean toClose) {
		this(type,origin,manager,associe,years,toClose);
		this.customerStartString = customerStartString;
		this.page=p;
	}
	
	
	public SearchBudgetParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public List<Integer> getYears() {
		return years;
	}
	public void setYears(List<Integer> years) {
		this.years = years;
	}
	
	public Long getOrigin() {
		return origin;
	}
	public void setOrigin(Long origin) {
		this.origin = origin;
	}
	public Long getManager() {
		return manager;
	}
	public void setManager(Long manager) {
		this.manager = manager;
	}
	public String getCustomerStartString() {
		return customerStartString;
	}
	public void setCustomerStartString(String customerStartString) {
		this.customerStartString = customerStartString;
	}
	public Long getAssocie() {
		return associe;
	}
	public void setAssocie(Long associe) {
		this.associe = associe;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean equals (Object obj){
		  if (obj!= null && (obj.getClass().equals(this.getClass()))){		      
			  SearchBudgetParam param = (SearchBudgetParam)obj;	
			  
			 if ( this.type == null && param.getType() != null) return false;
			 if ( this.origin == null && param.getOrigin() != null) return false;
			 if ( this.manager == null && param.getManager() != null) return false;
			 if ( this.associe == null && param.getAssocie() != null) return false;
			 if ( this.customerStartString == null && param.getCustomerStartString()!= null) return false;
			 if ( this.page == null && param.getPage()!= null) return false;
			 if ( this.years == null && param.getYears()!= null) return false;
			 
			 if( this.toClose != param.isToClose()) return false;
			 
			 if ( this.type != null && param.getType() == null) return false;
			 if ( this.origin != null && param.getOrigin() == null) return false;
			 if ( this.manager != null && param.getManager() == null) return false;
			 if ( this.associe != null && param.getAssocie() == null) return false;
			 if ( this.customerStartString != null && param.getCustomerStartString()== null) return false;
			 if ( this.page != null && param.getPage()== null) return false;
			 if ( this.years != null && param.getYears()== null) return false;
			 
			 if(( (this.page != null && param.getPage()!= null) && (! this.page.equals(param.getPage())))) return false;
			 
			 if (
					( this.type == null && param.getType() == null) && 
					( this.origin == null && param.getOrigin() == null) &&
				 	( this.manager == null && param.getManager() == null) &&
				 	( this.associe == null && param.getAssocie() == null) &&
				 	( this.customerStartString == null && param.getCustomerStartString()== null) &&
				 	( (this.page == null && param.getPage()== null) || ( this.page.equals(param.getPage()))) &&
				 	( ( this.years == null && param.getYears()== null) || ( this.years.equals(param.getYears())))
				 	
				 
			 	){
					return true;
			 }
			 else{
				 
				 if ( this.type == null || 
					  this.origin == null || 
					  this.manager == null || 
					  this.associe == null ||
					  this.customerStartString == null || 
					  this.page == null ||
					  this.years == null) return false;
				
				 return (
			    		  ( this.type.equals(param.getType())) &&
			    		  ( this.origin.equals(param.getOrigin()))&&
			    		  ( this.manager.equals(param.getManager()))&&
			    		  ( this.associe.equals(param.getAssocie()))&&
			    		  ( this.customerStartString.equals(param.getCustomerStartString())) &&
			    		  ( this.page.equals(param.getPage())) &&
			    		  ( this.years.equals(param.getYears()))
			    		
			    		  );
			 }
				 
			  
		     
		  }
		  return false;
		}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public boolean isToClose() {
		return toClose;
	}

	public void setToClose(boolean toClose) {
		this.toClose = toClose;
	}

	



	
	
	

}
