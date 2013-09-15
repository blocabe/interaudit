package com.interaudit.domain.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "TIMESHEET_ROWS",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class TimesheetRow implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="TimesheetRowSeq")
	@SequenceGenerator(name="TimesheetRowSeq",sequenceName="TIMESHEET_ROW_SEQ", allocationSize=1)
	*/
	private Long id;
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_activity" , nullable = true)
	private Activity activity;
	
	@OneToMany(mappedBy = "row", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TimesheetCell> cells = new HashSet<TimesheetCell>();

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_timesheet" , nullable = false)
	private Timesheet timesheet;
	
	
	
	private String label;
	
	
	private String codePrestation;
	private String custNumber;
	private String assCode;
	private String manCode;
	private String year;
	
	private String taskIdentifier;
	
	
	
	
	public TimesheetRow() {
		
	}
	
	public TimesheetRow(Timesheet timesheet,Activity activity,Task task,String label,String codePrestation,String year) {
		this();
		//cells = new ArrayList<TimesheetCell>(7);
		this.timesheet = timesheet;
		timesheet.addRow(this);
		this.activity = activity;
		this.label=label;
		this.codePrestation = codePrestation;
		this.year = year;
		
		if(this.activity == null){
			this.custNumber = "99900";
				
			if(task != null){
				//this.codePrestation = task.getCodePrestation();
				this.taskIdentifier = task.getId().toString();
			}
		}
		else{
			this.custNumber = this.activity .getMission().getCustomer().getCode();
			this.assCode =  this.activity .getMission().getCustomer().getAssocieSignataire().getCode();
			this.manCode =  this.activity .getMission().getCustomer().getCustomerManager().getCode();
			//this.codePrestation = this.activity.getTask().getCodePrestation();
		}		
	}
	
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof TimesheetRow)  )){
			return false;
		}
		else{
			return this.getId().equals(((TimesheetRow)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	/**
	 * @param dayNumber
	 * @param hours
	 * @return
	 */
	public boolean updateHoursForDay(int dayNumber, double hours){
		Map<String,TimesheetCell> mapCells = getCellsMap();
		TimesheetCell cell = mapCells.get(Integer.toString(dayNumber));
		if(cell == null){
			addCell(new TimesheetCell(dayNumber, hours));
			return true;
		}else{
			cell.setValue(/*cell.getValue() + */hours);
			return true;
		}
		/*
		for(TimesheetCell cell : cells){
			if(cell.getDayNumber() == dayNumber){
				cell.setValue(cell.getValue() + hours);
				return true;
			}			
		}
		*/
		
	}
	
	
	public Map<String,TimesheetCell> getCellsMap(){
		Map<String,TimesheetCell> map = new HashMap<String,TimesheetCell>();
		for(TimesheetCell cell : cells){
			map.put(cell.getDayNumber().toString(), cell);			
		}
		return map;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Timesheet getTimesheet() {
		return timesheet;
	}

	public void setTimesheet(Timesheet timesheet) {
		this.timesheet = timesheet;
	}

	
	

	public Set<TimesheetCell> getCells() {
		return cells;
	}

	public void setCells(Set<TimesheetCell> cells) {
		this.cells = cells;
	}

	public void addCell(TimesheetCell cell) {
		this.cells.add(cell);
		cell.setRow(this);
	}

	public void removeCell(TimesheetCell cell) {
		this.cells.remove(cell);
	}

	public Double getTotalOfHours() {
		Double totalHours = 0.0d;
		for(TimesheetCell cell : cells){			
			totalHours += cell.getValue();			
		}
		return totalHours;
	}

	public Double getTotalOfExtraHours() {
		return getTotalOfHours() <= 40?0.0f:getTotalOfHours()-40.0f;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	

	

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCodePrestation() {
		return codePrestation;
	}

	public void setCodePrestation(String codePrestation) {
		this.codePrestation = codePrestation;
	}

	public String getCustNumber() {
		return custNumber;
	}

	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}

	public String getAssCode() {
		return assCode;
	}

	public void setAssCode(String assCode) {
		this.assCode = assCode;
	}

	public String getManCode() {
		return manCode;
	}

	public void setManCode(String manCode) {
		this.manCode = manCode;
	}

	public String getTaskIdentifier() {
		return taskIdentifier;
	}

	public void setTaskIdentifier(String taskIdentifier) {
		this.taskIdentifier = taskIdentifier;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	

	
}
