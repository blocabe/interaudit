package com.interaudit.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "TIMESHEET_CELLS",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class TimesheetCell implements java.io.Serializable, Comparable<TimesheetCell>{

	public TimesheetCell(Integer dayNumber, Double value) {
		super();
		this.dayNumber = dayNumber;
		this.value = value;
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof TimesheetCell)  )){
			return false;
		}
		else{
			return this.getId().equals(((TimesheetCell)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public int compareTo(TimesheetCell o)
    {
		TimesheetCell c = (TimesheetCell)o;
		return dayNumber.compareTo(c.dayNumber);
    }

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="TimesheetCellSeq")
	@SequenceGenerator(name="TimesheetCellSeq",sequenceName="TIMESHEET_CELL_SEQ", allocationSize=1)
	*/
	private Long id;
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	
	private Integer dayNumber; 
	private Double value;

	public TimesheetCell() {
		super();
		// TODO Auto-generated constructor stub
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_row" , nullable = false)
	private TimesheetRow row;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public TimesheetRow getRow() {
		return row;
	}

	public void setRow(TimesheetRow row) {
		this.row = row;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}


}
