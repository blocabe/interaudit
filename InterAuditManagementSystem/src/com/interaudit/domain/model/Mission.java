package com.interaudit.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.interaudit.domain.model.data.Option;

@Entity
@Table(name = "MISSIONS",schema="interaudit")
public class Mission implements Serializable{
	
	public static final String STATUS_PENDING ="PENDING";
	public static final String STATUS_ONGOING ="ONGOING";
	public static final String STATUS_STOPPED ="STOPPED";
	public static final String STATUS_CLOSED ="CLOSED";
	public static final String STATUS_CANCELLED ="CANCELLED";
	public static final String STATUS_TO_TRANSFERED = "TO_TRANSFERED";
	public static final String STATUS_TRANSFERED = "TRANSFERED";
	
	public static final String JOB_STATUS_NOT_STARTED ="JOB NOT STARTED";
	public static final String JOB_STATUS_PENDING ="FIELD WORK TO FINALISE";
	public static final String JOB_STATUS_ONGOING ="CLIENT APPROVAL/REP LETTER";
	public static final String JOB_STATUS_STOPPED ="STOPPED";
	public static final String JOB_STATUS_CLOSED ="FINISHED AND SIGNED";
	public static final String JOB_STATUS_CANCELLED ="CANCELLED";
	
	public static final String TODO_TOREVIEW ="TO_REVIEW";
	public static final String TODO_REVIEWED ="REVIEWED";
	public static final String TODO_SENT ="SENT";
	
	//Management letter status
	public static final String TOFINISH_TOPREPARE ="TO_PREPARE";
	public static final String TOFINISH_DRAFT_ISSUED_TO_CLIENT ="DRAFT_ISSUED_TO_CLIENT";
	public static final String TOFINISH_FINISHED ="FINISHED";
	
	
	
	public  static List<Option> getAllStatusOptions(){
		   List<Option> options= new ArrayList<Option>();
		   /*options.add(new Option(Mission.STATUS_PENDING,Mission.STATUS_PENDING));*/
		   options.add(new Option(Mission.STATUS_ONGOING,Mission.STATUS_ONGOING));
		   /*options.add(new Option(Mission.STATUS_STOPPED,Mission.STATUS_STOPPED));*/
		   options.add(new Option(Mission.STATUS_CLOSED,Mission.STATUS_CLOSED));
		   options.add(new Option(Mission.STATUS_CANCELLED,Mission.STATUS_CANCELLED));
		   
		   return options;
	   }
	
	
	
	public  static List<Option> getAllJobProgressStatusOptions(){
		   List<Option> options= new ArrayList<Option>();
		   options.add(new Option(Mission.JOB_STATUS_NOT_STARTED,Mission.JOB_STATUS_NOT_STARTED));
		   options.add(new Option(Mission.JOB_STATUS_PENDING,Mission.JOB_STATUS_PENDING));		  
		   options.add(new Option(Mission.JOB_STATUS_ONGOING,Mission.JOB_STATUS_ONGOING));
		   options.add(new Option(Mission.JOB_STATUS_CLOSED,Mission.JOB_STATUS_CLOSED));
		   options.add(new Option(Mission.JOB_STATUS_STOPPED,Mission.JOB_STATUS_STOPPED));		  
		   options.add(new Option(Mission.JOB_STATUS_CANCELLED,Mission.JOB_STATUS_CANCELLED));
		   
		   return options;
	   }
	
	
	public  static List<Option> getAllJobtodoStatusOptions(){
		   List<Option> options= new ArrayList<Option>();
		   options.add(new Option(Mission.TODO_TOREVIEW,Mission.TODO_TOREVIEW));
		   options.add(new Option(Mission.TODO_REVIEWED,Mission.TODO_REVIEWED));
		   options.add(new Option(Mission.TODO_SENT,"DRAFT SENT"));
		   //options.add(new Option(Mission.TODO_SENT,Mission.TODO_SENT));
		   
		   return options;
	   }
	

	public  static List<Option> getAllJobtoFinishStatusOptions(){
		   List<Option> options= new ArrayList<Option>();
		   options.add(new Option(Mission.TOFINISH_TOPREPARE,Mission.TOFINISH_TOPREPARE));
		   options.add(new Option(Mission.TOFINISH_DRAFT_ISSUED_TO_CLIENT,Mission.TOFINISH_DRAFT_ISSUED_TO_CLIENT));
		   options.add(new Option(Mission.TOFINISH_FINISHED,Mission.TOFINISH_FINISHED));		   
		   return options;
	   }
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="MissionSeq")
	@SequenceGenerator(name="MissionSeq",sequenceName="MISSION_SEQ", allocationSize=1)
	*/
	private Long id;
	
	private String exercise;
	private String title;
	
	private int startWeek;
	
	private int startYear;
	
	@Column(name = "refer", nullable = false)
	private String reference;
	
	@Column(name = "comments", nullable = false)
	private String comment;
    
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
    private Date createDate; 
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
    private Date startDate; 
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
    private Date dueDate; 
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
    private Date approvalDate;
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
    private Date signedDate;
    
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
    private Date dateCloture;
	
	@Temporal(TemporalType.DATE)
    private Date updateDate;
    private String updatedUser;
    
    private String status;
    
    @Column(name = "typ", nullable = false)
    private String type;
    
    
    @Column(name = "VERSION", nullable = false)
	@Version
	private int version;
   
    
    @OneToMany(mappedBy = "mission", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Activity> interventions;
    
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Cost> extraCosts = new HashSet<Cost>();
    
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("createDate DESC")
	private Set<Message> messages = new TreeSet<Message>(new MessageComparateur());
    
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("numero")
    private Set<Alerte> alertes = new TreeSet<Alerte>(new AlerteComparateur());
    
    @ManyToMany
	@JoinTable(name = "DOCS_MISSIONS", joinColumns = @JoinColumn(name = "ID_MISSION"), inverseJoinColumns = @JoinColumn(name = "ID_DOCUMENT"))
	private Set<Document> documents  = new HashSet<Document>();
    
    private String language;
    
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @OrderBy("dateFacture DESC")
	private Set<Invoice> factures  = new HashSet<Invoice>();
	
    @ManyToOne(fetch = FetchType.EAGER)
    private AnnualBudget annualBudget;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_parent" , nullable = true)
    private Mission parent;
    
    @Column(name = "JOB_STATUS", nullable = false)
    private String jobStatus;
	
	private String toDo;
	
	private String jobComment;
	
	private String toFinish;
	
	@OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<AnnualBudget> budgets = new HashSet<AnnualBudget>();
	
	
	 @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 private List<MissionMember> members;
	 
	 @Column(name = "MEMBER_STRING", nullable = true)
	 private String memberAsString;

    
    
	public Mission(){
		this.createDate = new Date();
		this.status = Mission.STATUS_PENDING;
	}
	
	public Mission(AnnualBudget annualBudget,Mission parent,String mandat){	
		this();
		this.annualBudget = annualBudget;
		this.budgets.add(annualBudget);
		//this.exercise = String.valueOf(annualBudget.getExercise().getYear()) ;
		this.exercise = mandat;
        String valueReference = annualBudget.getContract().getCustomer().getCode().toUpperCase() + "_" + annualBudget.getExercise().getYear();	
        this.reference=valueReference.toUpperCase();
        this.title =  annualBudget.getContract().getCustomer().getCompanyName().toUpperCase();
        this.comment = this.title;
        this.type = annualBudget.getContract().getMissionType();
        this.language = annualBudget.getContract().getLanguage();
        this.parent = parent;
        
        
        if(parent == null){
        	this.status = Mission.STATUS_PENDING;
        	this.jobStatus = Mission.JOB_STATUS_NOT_STARTED;          
        }
        else{
        	this.status = Mission.STATUS_ONGOING;
        	this.jobStatus = parent.getJobStatus();
            this.toDo = parent.getToDo(); 
            this.jobComment = parent.getJobComment();
            this.toFinish = parent.getToFinish();
        	this.startWeek = parent.getStartWeek();
        	this.createDate = parent.getCreateDate(); 
        	this.startDate = parent.getStartDate(); 
        	this.dueDate = parent.getDueDate(); 
            this.dateCloture = parent.getDateCloture();
        }
        
	}
	
	
	 // Add an employee to the project.
	  // Create an association object for the relationship and set its' data.
	  public void addMember(Employee member) {
		  MissionMember association = new  MissionMember();
		  association.setEmployee(member);
		  association.setProject(this);
		  association.setMemberId(member.getId());
		  association.setMissionId(this.getId());
		  association.setStarDate(new Date());

		  members.add(association);
	  }

	
	public boolean equals(Object obj){		
		if((this.getId()== null ) || (null == obj) || ( !(obj instanceof Mission)  )){
			return false;
		}
		else{
			return this.getId().equals(((Mission)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public Activity getDefaultActivity(){
		
		 //Load the activity by defaulft	        
        Object[] activities = this.getInterventions().toArray();
        if(activities.length == 0){
        	return null;
        }
        
        Activity activity  = (Activity)activities[0];
        return activity;
		
	}

	

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getExercise() {
		return exercise;
	}
	
	

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return this.annualBudget == null? null:this.annualBudget.getContract().getCustomer(); 
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Invoice> getFactures() {
		return factures;
	}

	public void setFactures(Set<Invoice> factures) {
		this.factures = factures;
	}


	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Set<Activity> getInterventions() {
		return interventions;
	}

	public void setInterventions(Set<Activity> interventions) {
		this.interventions = interventions;
	}

	

	public Set<Cost> getExtraCosts() {
		return extraCosts;
	}

	public void setExtraCosts(Set<Cost> extraCosts) {
		this.extraCosts = extraCosts;
	}
	
	
	public double getTotalExtraCost(){
		
		double result =0.0;
		for ( Cost cost : extraCosts){
			result += cost.getAmount();
		}
		return result;
	}
	
	/**
	 * @return la liste des employees de la mission
	 */
	public List<Employee> getTeamMembers(){
		List<Employee> team = new ArrayList<Employee>();
		for ( Activity intervention : interventions){
			//team.add(intervention.getEmployee());
		}
		return team;
		
	}

	public AnnualBudget getAnnualBudget() {
		return annualBudget;
	}

	public void setAnnualBudget(AnnualBudget annualBudget) {
		this.annualBudget = annualBudget;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	
	
	/*
	public static String[] getTypes(){
		return Mission.types;
	}
	*/

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
	
	public List<Cost> getAllExtraCosts() {
		
		List<Cost> collection  = new ArrayList<Cost>();
		collection.addAll(extraCosts);
		 if(parent != null){
			 List<Cost> costsParent = parent.getAllExtraCosts();
			 collection.addAll(costsParent);
		 }
		 
		return  collection;
		
	}
	
	public List<Message> getSortedMessages() {

		//Set<Message> collection  = new TreeSet<Message>(new MessageComparateur());
		List<Message> collection  = new ArrayList<Message>();
		collection.addAll(messages);
		 if(parent != null){
			 List<Message> messagesParent = parent.getSortedMessages();
			 collection.addAll(messagesParent);
		 }
		 
		return  collection;
	}
	
	public List<Invoice> getSortedInvoices() {

		List<Invoice> collection  = new ArrayList<Invoice>(/*new InvoiceComparateur()*/);
		collection.addAll(factures);
		 if(parent != null){
			 List<Invoice> invoicesParent = parent.getSortedInvoices();
			 collection.addAll(invoicesParent);
		 }
		 
		return  collection;
	}
	
	

	public Set<Message> getMessages() {
		return  messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	 
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Mission getParent() {
		return parent;
	}

	public void setParent(Mission parent) {
		this.parent = parent;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getToDo() {
		return toDo;
	}

	public void setToDo(String toDo) {
		this.toDo = toDo;
	}

	public String getJobComment() {
		return jobComment;
	}

	public void setJobComment(String jobComment) {
		this.jobComment = jobComment;
	}



	public Date getDateCloture() {
		return dateCloture;
	}



	public void setDateCloture(Date dateCloture) {
		this.dateCloture = dateCloture;
	}



	public String getToFinish() {
		return toFinish;
	}



	public void setToFinish(String toFinish) {
		this.toFinish = toFinish;
	}



	public int getStartWeek() {
		return this.startWeek;
		
	}



	public void setStartWeek(int startWeek) {
		this.startWeek = startWeek;
	}



	public Date getApprovalDate() {
		return approvalDate;
	}



	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}



	public Date getSignedDate() {
		return signedDate;
	}



	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}



	public Set<AnnualBudget> getBudgets() {
		return budgets;
	}



	public void setBudgets(Set<AnnualBudget> budgets) {
		this.budgets = budgets;
	}



	public Set<Alerte> getAlertes() {
		return alertes;
	}



	public void setAlertes(Set<Alerte> alertes) {
		this.alertes = alertes;
	}



	public List<MissionMember> getMembers() {
		return members;
	}



	public void setMembers(List<MissionMember> members) {
		this.members = members;
	}



	public String getMemberAsString() {
		return memberAsString;
	}



	public void setMemberAsString(String memberAsString) {
		this.memberAsString = memberAsString;
	}



	public int getStartYear() {
		return startYear;
	}



	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}



	

	

}

class AlerteComparateur implements Comparator<Alerte> {
	  public int compare(Alerte obj1, Alerte obj2){
	    return ((Comparable<Alerte>)obj2).compareTo(obj1);
	  }
	  public boolean equals(Object obj){
	    return this.equals(obj);
	  }
	}

class InvoiceComparateur implements Comparator<Invoice> {
	  public int compare(Invoice obj1, Invoice obj2){
	    return ((Comparable<Invoice>)obj2).compareTo(obj1);
	  }
	  public boolean equals(Object obj){
	    return this.equals(obj);
	  }
	}

 class MessageComparateur implements Comparator<Message> {
	  public int compare(Message obj1, Message obj2){
	    return ((Comparable<Message>)obj2).compareTo(obj1);
	  }
	  public boolean equals(Object obj){
	    return this.equals(obj);
	  }
	}
