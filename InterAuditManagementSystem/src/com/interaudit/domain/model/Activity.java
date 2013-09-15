package com.interaudit.domain.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "ACTIVITIES",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public  class Activity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String STATUS_PENDING ="FIELD WORK TO FINALISE";
	public static final String STATUS_ONGOING ="CLIENT APPROVAL/REP LETTER";
	public static final String STATUS_STOPPED ="STOPPED";
	public static final String STATUS_CLOSED ="FINISHED AND SIGNED";
	public static final String STATUS_CANCELLED ="CANCELLED";
	
	public static final String TODO_TOREVIEW ="TO_REVIEW";
	public static final String TODO_REVIEWED ="REVIEWED";
	public static final String TODO_SENT ="SENT";

	
	//Technical statuses
	public static final String STATUS_TO_TRANSFERED = "TO_TRANSFERED";
	public static final String STATUS_TRANSFERED = "TRANSFERED";
	
	public static final String PRIORITY_CRITICAL ="CRITICAL";
	public static final String PRIORITY_MAJOR ="MAJOR";
	public static final String PRIORITY_NORMAL ="NORMAL";
	public static final String PRIORITY_MINOR ="MINOR";
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="ActivitySeq")
	@SequenceGenerator(name="ActivitySeq",sequenceName="ACTIVITY_SEQ", allocationSize=1)
	*/
	private Long id;
	
	private String status;
	
	private String toDo;
	
	private String comments;

	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	@Column(name = "ORDRE", nullable = false)
	private int order;
	
	/*
	@OneToOne
	@JoinColumn( name="fk_employee" , nullable = false)
	private Employee employee;
	*/
	
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE", nullable = false)
	private Date   startDate;
	
	@Temporal(TemporalType.DATE)
	private Date   updateDate;
	
	@Temporal(TemporalType.DATE)
	private Date   endDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Mission mission;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Task task;
	
	@OneToMany(mappedBy = "activity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Event> events;
	
	
	public Activity(Mission mission, /*Employee employee, */Task task,Date startDate,int order) {
		super();
		this.mission = mission;
		/*this.employee = employee;*/
		this.task = task;
		this.status = Activity.STATUS_PENDING;
		this.toDo = Activity.TODO_TOREVIEW;
		this.startDate = startDate;
		this.order = order;		
	}



	public Activity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Activity)  )){
			return false;
		}
		else{
			return this.getId().equals(((Activity)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/*
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	*/
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}



	public String getToDo() {
		return toDo;
	}



	public void setToDo(String toDo) {
		this.toDo = toDo;
	}



	public Mission getMission() {
		return mission;
	}



	public void setMission(Mission mission) {
		this.mission = mission;
	}



	public String getComments() {
		return comments;
	}



	public void setComments(String comments) {
		this.comments = comments;
	}



	public Task getTask() {
		return task;
	}



	public void setTask(Task task) {
		this.task = task;
	}



	public Set<Event> getEvents() {
		return events;
	}



	public void setEvents(Set<Event> events) {
		this.events = events;
	}



	public int getOrder() {
		return order;
	}



	public void setOrder(int order) {
		this.order = order;
	}

	
}
