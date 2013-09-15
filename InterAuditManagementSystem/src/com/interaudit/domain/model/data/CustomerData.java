package com.interaudit.domain.model.data;




public class CustomerData implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String companyName;
	private String code;
	private String originName;
	private String associeSignataireName;
	private String customerManagerName;
	private String phone;
	private String email;
    private boolean active = true;    
    private String mainAddress;
    private String mainActivity;
    
    
    
	public CustomerData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerData(Long id, String companyName, String code,
			String originName, String associeSignataireName,
			String customerManagerName, String phone, String email,
			boolean active, String mainAddress, String mainActivity) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.code = code;
		this.originName = originName;
		this.associeSignataireName = associeSignataireName;
		this.customerManagerName = customerManagerName;
		this.phone = phone;
		this.email = email;
		this.active = active;
		this.mainAddress = mainAddress;
		this.mainActivity = mainActivity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getAssocieSignataireName() {
		return associeSignataireName;
	}
	public void setAssocieSignataireName(String associeSignataireName) {
		this.associeSignataireName = associeSignataireName;
	}
	public String getCustomerManagerName() {
		return customerManagerName;
	}
	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getMainAddress() {
		return mainAddress;
	}
	public void setMainAddress(String mainAddress) {
		this.mainAddress = mainAddress;
	}
	public String getMainActivity() {
		return mainActivity;
	}
	public void setMainActivity(String mainActivity) {
		this.mainActivity = mainActivity;
	}
   
    
   
    
    
}
