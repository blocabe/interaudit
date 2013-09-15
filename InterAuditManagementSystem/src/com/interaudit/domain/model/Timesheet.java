package com.interaudit.domain.model;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.interaudit.domain.model.data.TimesheetDataForPlanning;
import com.interaudit.util.Constants;


@Entity
@Table(name = "TIMESHEETS",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class Timesheet implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="TimesheetSeq")
	@SequenceGenerator(name="TimesheetSeq",sequenceName="TIMESHEET_SEQ", allocationSize=1)
	*/
	private Long id;
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	
	private String exercise;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid" , nullable = false)
	private Employee user;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "createdate", nullable = false)
	private Date date;
	
	@Temporal(TemporalType.DATE)
	private Date updateDate;
	
	@Temporal(TemporalType.DATE)
	private Date acceptedDate;
	
	@Temporal(TemporalType.DATE)
    private Date rejectedDate;
	
	@Temporal(TemporalType.DATE)
    private Date submitDate;
	
	@Temporal(TemporalType.DATE)
	private Date validationDate;
	
	@Temporal(TemporalType.DATE)
	private Date startDateOfWeek;
	
	@Temporal(TemporalType.DATE)
	private Date endDateOfWeek;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_employee")
	private Employee validationUser;
	
	private double prixVente = 0;
	private double prixRevient = 0;
	private double coutHoraire = 0;
	
	@OneToMany(mappedBy = "timesheet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<TimesheetRow> rows;
	
	
	@OneToMany(mappedBy = "timesheet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Comment>  comments = new HashSet<Comment>();
	
	
	
	private String status;
	
	
	private int weekNumber;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "lastrejecteddate", nullable = true)
	private Date lastRejectedDate;
	
	
	public Timesheet() {
		rows = new HashSet<TimesheetRow>();
		date = new Date();
	}
	
	public Timesheet(Employee employee, String exercise ,int weekNumber ){
		this();
		this.user = employee;
		this.exercise = exercise;
		this.weekNumber = weekNumber;
		this.status = Constants.TIMESHEET_STATUS_STRING_DRAFT;
		
		this.prixVente = employee.getPrixVente();
		this.prixRevient = employee.getPrixRevient();
		this.coutHoraire = employee.getCoutHoraire();
		
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, Integer.parseInt(exercise));
		c.set(Calendar.WEEK_OF_YEAR,weekNumber);
		
		
		
		c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
		this.startDateOfWeek = c.getTime();
		c.set(Calendar.DAY_OF_WEEK , Calendar.SATURDAY);
		this.endDateOfWeek  = c.getTime();
		
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Timesheet)  )){
			return false;
		}
		else{
			return this.getId().equals(((Timesheet)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	/**
	 * @param missionId
	 * @return
	 */
	public TimesheetRow findRowForMissionFromTask(Long missionId, Long taskId){		
		for(TimesheetRow row : rows){			
			Activity activity = row.getActivity();
			if(activity != null && activity.getMission().getId().equals(missionId)&& activity.getTask().getId().equals(taskId)){
				return row;
			}			
		}		
		return null;		
	}
	
	
	public Double getTotalOfHours() {
		Double totalHours = 0.0d;
		for(TimesheetRow row : rows){			
			totalHours += row.getTotalOfHours();			
		}
		return totalHours;
	}
	
	public boolean checkHoursPerDay(){
		Double totalHours = 0.0d;
		for(int i = 1; i<6; i++){
			totalHours = 0.0d;
			for(TimesheetRow row : rows){
				Map<String,TimesheetCell> mapCells = row.getCellsMap();
				TimesheetCell cell = mapCells.get(Integer.toString(i));
				if(cell != null){
					totalHours += cell.getValue();
					if(totalHours > 8.0){
						return false;
					}
				}				
			}
		}
		return true;
	}
	
	public TimesheetDataForPlanning buildTimesheetDataForPlanning(long idEvent){
		
		Double totalHours= 0.0d;
		
		int startDayNumber = 5;
		int endDayNumber = 1;
		boolean found = false;
		
		for(TimesheetRow row : rows){	
			TimesheetRow targetRow = null;
			Activity activity = row.getActivity();
			//It is amission
			if(activity != null && activity.getMission().getId().equals(idEvent)){				
				targetRow = row;
			}
			else if( (row.getTaskIdentifier()!= null) && (row.getTaskIdentifier().equalsIgnoreCase(Long.toString(idEvent)))){
				//It is a task				
					targetRow = row;
			}
			
			//We found a matching row
			if(targetRow != null){
				found = true;
				totalHours += targetRow.getTotalOfHours();
				int minDay = minDayNumberForRow(targetRow);
				int maxDay =  maxDayNumberForRow(targetRow);
				
				if(minDay < startDayNumber){
					startDayNumber = minDay;
				}
				
				if(maxDay > endDayNumber){
					endDayNumber = maxDay;
				}
			}
			
		}
		
		
		if( found == true ){
			TimesheetDataForPlanning ret = buildTimesheetDataForPlanning( startDayNumber, endDayNumber, totalHours);
			return ret;
		}
		else{
			return null;
		}
	}
	
	
	public TimesheetDataForPlanning buildTimesheetDataForPlanning(int startDayNumber,int endDayNumber,Double totalHours){
		
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YY", new Locale("fr","FR"));
		
	 	String year = this.getExercise();
	 	String week = Integer.toString(this.getWeekNumber());
	 	
	   //Compute the maximun dates of the selected month
	   Calendar c = Calendar.getInstance();
	   c.set(Calendar.YEAR,Integer.parseInt(year));
	   c.set(Calendar.WEEK_OF_YEAR,Integer.parseInt(week));
	   
	   Date startDate = getDateFromIndice(startDayNumber,  c );
	   Date endDate= getDateFromIndice(endDayNumber,  c );
	   
	  return new TimesheetDataForPlanning(startDate,endDate,totalHours);
	}
	
	public Date getDateFromIndice(int indice,  Calendar c ){
		if(indice == 1){
			 c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
			 return c.getTime();
		}
		
		if(indice == 2){
			 c.set(Calendar.DAY_OF_WEEK , Calendar.TUESDAY);
			 return c.getTime();
		}
		
		if(indice == 3){
			 c.set(Calendar.DAY_OF_WEEK , Calendar.WEDNESDAY);
			 return c.getTime();
		}
		
		if(indice == 4){
			 c.set(Calendar.DAY_OF_WEEK , Calendar.THURSDAY);
			 return c.getTime();
		}
		
		if(indice == 5){
			 c.set(Calendar.DAY_OF_WEEK , Calendar.FRIDAY);
			 return c.getTime();
		}
		
		return null;
	}
	
	public int minDayNumberForRow(TimesheetRow row){
		Map<String,TimesheetCell> mapCells = row.getCellsMap();
		//Search
		for(int i = 1; i<=5; i++){
			TimesheetCell cell = mapCells.get(Integer.toString(i));
			if(cell !=null && cell.getValue() > 0.0d){
				return cell.getDayNumber();
				
			}
		}
		return 1;
	}
	
	public int maxDayNumberForRow(TimesheetRow row){
    	Map<String,TimesheetCell> mapCells = row.getCellsMap();
    	for(int i = 5; i>=1; i--){
			TimesheetCell cell = mapCells.get(Integer.toString(i));
			if(cell !=null && cell.getValue() > 0.0d){
				return cell.getDayNumber();
			}
		}
    	
    	return 1;
	}
	
	/**
	 * @param missionId
	 * @return
	 */
	public TimesheetRow findRowForMissionFromActivity(Long missionId, Long activityId){		
		for(TimesheetRow row : rows){			
			Activity activity = row.getActivity();
			if(activity != null && activity.getMission().getId().equals(missionId)&& activity.getId().equals(activityId)){
				return row;
			}			
		}		
		return null;		
	}
	
	
	   
	
	/**
	 * @param label
	 * @return
	 */
	public TimesheetRow findRowForType(String label){		
		for(TimesheetRow row : rows){						
			if(row.getLabel().equalsIgnoreCase(label.trim())){				
				return row;				
			}			
		}		
		return null;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<TimesheetRow> getRows() {
		return rows;
	}

	public void setRows(Set<TimesheetRow> rows) {
		this.rows = rows;
	}

	public void addRow(TimesheetRow line) {
		this.rows.add(line);
	}

	public void removeRow(TimesheetRow line) {
		this.rows.add(line);
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

	

	public Date getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(Date validationDate) {
		this.validationDate = validationDate;
	}

	public Employee getValidationUser() {
		return validationUser;
	}

	public void setValidationUser(Employee validationUser) {
		this.validationUser = validationUser;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public Date getStartDateOfWeek() {
		return startDateOfWeek;
	}

	public void setStartDateOfWeek(Date startDateOfWeek) {
		this.startDateOfWeek = startDateOfWeek;
	}

	public Date getEndDateOfWeek() {
		return endDateOfWeek;
	}

	public void setEndDateOfWeek(Date endDateOfWeek) {
		this.endDateOfWeek = endDateOfWeek;
	}

	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	public Date getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public Date getRejectedDate() {
		return rejectedDate;
	}

	public void setRejectedDate(Date rejectedDate) {
		this.rejectedDate = rejectedDate;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}

	public double getPrixRevient() {
		return prixRevient;
	}

	public void setPrixRevient(double prixRevient) {
		this.prixRevient = prixRevient;
	}

	public double getCoutHoraire() {
		return coutHoraire;
	}

	public void setCoutHoraire(double coutHoraire) {
		this.coutHoraire = coutHoraire;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	
	public Set<TimesheetRow> getSortedRows() {
		Set<TimesheetRow> collection  = new TreeSet<TimesheetRow>(new TimesheetRowComparator());
		collection.addAll(rows);
		return  collection;
		
	}
	
	
   public Set<Comment> getSortedComments() {
	
		Set<Comment> collection  = new TreeSet<Comment>(new CommentComparator());
		collection.addAll(comments);
		return  collection;
	}
   
   
   class TimesheetRowComparator implements Comparator<TimesheetRow>{

		public int compare(TimesheetRow emp1, TimesheetRow emp2){

		//parameter are of type Object, so we have to downcast it to Employee objects

			Long id1 = ( (TimesheetRow) emp1).getId();

			Long id2 = ( (TimesheetRow) emp2).getId();

		if( id1 > id2 )

		return -1;

		else if( id1 < id2 )

		return 1;

		else

		return 0;

		}

	}
	

	class CommentComparator implements Comparator<Comment>{

		public int compare(Comment emp1, Comment emp2){

		//parameter are of type Object, so we have to downcast it to Employee objects

			Long id1 = ( (Comment) emp1).getId();

			Long id2 = ( (Comment) emp2).getId();

		if( id1 > id2 )

		return -1;

		else if( id1 < id2 )

		return 1;

		else

		return 0;

		}

	}


	public Date getLastRejectedDate() {
		return lastRejectedDate;
	}

	public void setLastRejectedDate(Date lastRejectedDate) {
		this.lastRejectedDate = lastRejectedDate;
	}
    

	
	
	

}
