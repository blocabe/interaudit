package com.interaudit.domain.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "ITEM_EVENT_PLANNING",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class ItemEventPlanning implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String DURATION_TYPE_NA ="NA";
	public static final String DURATION_TYPE_WHOLEDAY ="WD";
	public static final String DURATION_TYPE_HALFDAYAM ="AM";
	public static final String DURATION_TYPE_HALFDAYPM ="PM";
	
	private static DateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy" , new Locale("fr","FR"));
	@Id
	@GeneratedValue
	private Long id;
	
	private boolean mission;
	
	private Long idEntity;
	
	private String title;
	
	private Date dateOfEvent;
	
	/*private String startDate;
	private String endDate;
	*/
	private String durationType;
	private double duration;
	
	private Date expectedStartDate;
	private Date expectedEndDate;
	
	private Date realStartDate;
	private Date realEndDate;
	private double totalHoursSpent;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_eventplanning" , nullable = false)
    private EventPlanning eventPlanning;
	
	public ItemEventPlanning() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemEventPlanning(String title, Long idEntity, boolean mission,
			/*String startDate,
	        String endDate,*/
	        String durationType,
	        double duration,Date expectedStartDate,Date expectedEndDate) {
		super();
		this.mission = mission;
		this.idEntity = idEntity;
		this.title = title;
		this.dateOfEvent = Calendar.getInstance().getTime();
		/*this.startDate = startDate;
		this.endDate = endDate;*/
		this.durationType = durationType;
		this.duration = duration;
		this.expectedStartDate= expectedStartDate;
		this.expectedEndDate=expectedEndDate;
	
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof ItemEventPlanning)  )){
			return false;
		}
		else{
			return this.getId().equals(((ItemEventPlanning)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Long getIdEntity() {
		return idEntity;
	}

	public void setIdEntity(Long idEntity) {
		this.idEntity = idEntity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public EventPlanning getEventPlanning() {
		return eventPlanning;
	}

	public void setEventPlanning(EventPlanning eventPlanning) {
		this.eventPlanning = eventPlanning;
	}

	public boolean isMission() {
		return mission;
	}

	public void setMission(boolean mission) {
		this.mission = mission;
	}

	public Date getDateOfEvent() {
		return dateOfEvent;
	}

	public void setDateOfEvent(Date dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}
/*
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
*/
	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public String getDurationTypeConverted() {
		if( durationType == null || durationType.equalsIgnoreCase(ItemEventPlanning.DURATION_TYPE_NA)){
			return "";
		}
		
		else if( durationType.equalsIgnoreCase(ItemEventPlanning.DURATION_TYPE_WHOLEDAY)){
			return "Journ&eacute;e";
		}
		
		
		else if( durationType.equalsIgnoreCase(ItemEventPlanning.DURATION_TYPE_HALFDAYAM)){
			return "Matin";
		}
		
		
		else if( durationType.equalsIgnoreCase(ItemEventPlanning.DURATION_TYPE_HALFDAYPM)){
			return "Apres-midi";
		}
		
		else
			return "";
		
	}
	String sFormat="d.M.yyyy";
	 public static Date stringToDate(String sDate, String sFormat) throws Exception {
	        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
	        return sdf.parse(sDate);
	} 
	
	

	
	public String getFormattedStartDate() throws Exception{
		Date date =  this.getExpectedStartDate();//stringToDate(this.getStartDate()+"."+eventPlanning.getYear(),  "d.M.yyyy");
		return  dateFormat.format(date);
	}
	
	public String getFormattedEndDate()throws Exception{
		Date date =  this.getExpectedEndDate();//stringToDate(this.getEndDate()+"."+eventPlanning.getYear(),  "d.M.yyyy");
		return dateFormat.format(date);
	}

	public Date getExpectedStartDate() {
		return expectedStartDate;
	}

	public void setExpectedStartDate(Date expectedStartDate) {
		this.expectedStartDate = expectedStartDate;
	}

	public Date getExpectedEndDate() {
		return expectedEndDate;
	}

	public void setExpectedEndDate(Date expectedEndDate) {
		this.expectedEndDate = expectedEndDate;
	}

	public Date getRealStartDate() {
		return realStartDate;
	}

	public void setRealStartDate(Date realStartDate) {
		this.realStartDate = realStartDate;
	}

	public Date getRealEndDate() {
		return realEndDate;
	}

	public void setRealEndDate(Date realEndDate) {
		this.realEndDate = realEndDate;
	}

	public double getTotalHoursSpent() {
		return totalHoursSpent;
	}

	public void setTotalHoursSpent(double totalHoursSpent) {
		this.totalHoursSpent = totalHoursSpent;
	}
	
	
	
	

	

}
