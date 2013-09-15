package com.interaudit.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.interaudit.domain.model.data.EventPlanningData;
import com.interaudit.util.DateUtils;

@Entity
@Table(name = "EVENT_PLANNING",schema="interaudit")
public class EventPlanning implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_employee" , nullable = false)
	private Employee employee;
	
    @OneToMany(mappedBy = "eventPlanning", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ItemEventPlanning> activities  = new HashSet<ItemEventPlanning>();
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_planning" , nullable = false)
	private PlanningAnnuel planning;
    
    
    private int year;
    private int weekNumber;    
    private boolean validated = false;
    
	public EventPlanning() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EventPlanningData getEventPlanningData(){
		return new EventPlanningData(this.getId(),this.getCompressedItemEventPlannings(),
				this.year, this.weekNumber, this.validated, this.employee.getFullName());
	}
	
			
	public EventPlanning(Employee employee, int year, int weekNumber) {
		super();		
		this.employee = employee;		
		this.year = year;
		this.weekNumber = weekNumber;
		this.validated = false;
		
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof EventPlanning)  )){
			return false;
		}
		else{
			return this.getId().equals(((EventPlanning)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	
	
	public String getEventsAsHtml(){
		//StringBuffer buffer = new StringBuffer("<p align=\"left\">");
		/*
		<ol>
		  <li>Coffee</li>
		  <li>Tea</li>
		  <li>Milk</li>
		</ol>

		*/
		StringBuffer buffer = new StringBuffer("");
		buffer.append("<br/>") ;
		int count = activities == null?0:activities.size();
		int index =1;
		for(ItemEventPlanning item : activities){
				//buffer.append("<li style=\" padding-left:1%;margin-left:1px\">") ;
				buffer.append(" <span style=\" color: black;\">" +index + "</span>. ") ;
			    int endIndex = item.getTitle().length() <= 8 ?item.getTitle().length(): 8;
				String temp = item.getTitle().substring(0,endIndex).toUpperCase();
				buffer.append(temp) ;
				buffer.append(" [<span style=\" color: black;\">" +DateUtils.getDay(item.getExpectedStartDate())+ "</span>-<span style=\" color: black;\">" +DateUtils.getDay(item.getExpectedEndDate()) + "</span>]");				
				
				if(index < count){
					buffer.append("<BR>") ;
				}
				index++;
				//buffer.append("</li>") ;				
		}
		buffer.append("<br/>") ;
		buffer.append("<br/>") ;
		//return buffer.append("</p>").toString();
		return buffer.toString();
	}
	
	public String getEventsAsHtml2(){
		//StringBuffer buffer = new StringBuffer("<p align=\"left\">");
		StringBuffer buffer = new StringBuffer("");
		//buffer.append("<ol ALIGN=\"LEFT\">") ;
		buffer.append("<br/>") ;
		int count = activities == null?0:activities.size();
		int index =1;
		for(ItemEventPlanning item : activities){	
				//buffer.append("<li style=\" padding-left:1%;margin-left:1px\">") ;
				buffer.append(" <span style=\" color: black;\">" +index + "</span>. ") ;
				String temp = item.getTitle().toUpperCase() +" [ <span style=\" color: black;\">" +DateUtils.getDay(item.getExpectedStartDate())+ "</span> - <span style=\" color: black;\">" +DateUtils.getDay(item.getExpectedEndDate()) + "</span> ]" ;
				buffer.append(temp) ;
				if(item.getDurationType() != null && !item.getDurationType().equalsIgnoreCase("NA") ){
					buffer.append(" "+item.getDurationType()) ;	
				}
				
				if(index < count){
					buffer.append("<BR>") ;
				}
				index++;
				//buffer.append("</li>") ;
		}
		//return buffer.append("</p>").toString();
		//buffer.append("</ol>") ;
		buffer.append("<br/>") ;
		buffer.append("<br/>") ;
		return buffer.toString();
	}
	
	public boolean containsMissionWithIdentifier(Long missionId){
		
		for(ItemEventPlanning item : activities){			    
			
			if(missionId.equals(item.getIdEntity())){
				return true ;
			}
		}
		return false;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
	public PlanningAnnuel getPlanning() {
		return planning;
	}

	public void setPlanning(PlanningAnnuel planning) {
		this.planning = planning;
	}
	
	public int getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	public Set<ItemEventPlanning> getActivities() {
		return activities;
	}
	public void setActivities(Set<ItemEventPlanning> activities) {
		this.activities = activities;
	}
	
	public boolean isActivityRetainForPlanning(String row){
    	
    	/*
    		 *CONGE:9930-9931-9932-9920
	   		 *MALADIE:9910
	   		 *FORMATION:9859
	   		 *JOUR FERIE
	   		 */
    		if(
    				( row.equalsIgnoreCase("Jours feries"))||
    				( row.equalsIgnoreCase("Conge legal"))||
    				( row.equalsIgnoreCase("Conge sans solde"))|| 
    				( row.equalsIgnoreCase("Congé extraordinaire"))||
    				( row.equalsIgnoreCase("Congé de maternité"))||    				
    				( row.equalsIgnoreCase("Maladie avec certificat"))||
    				( row.equalsIgnoreCase("Maladie sans certificat"))||    				  				
    				( row.equalsIgnoreCase("Maladie récupérable enfant"))||
    				( row.equalsIgnoreCase("Formation interne (bureau IA)"))||
    				( row.equalsIgnoreCase("Formation externe tiers"))||
    				( row.equalsIgnoreCase("Formation interne - Conception"))||
    				( row.equalsIgnoreCase("Formation externe (IRE, OEC,Université Luxembourg)"))
    			)
    		{
    			return true;	
    		}
    		else{
    			return false;
    		}
    	
   }
	
	public Collection<ItemEventPlanning> getCompressedItemEventPlannings() {
		Map <Long,ItemEventPlanning> items = new HashMap<Long,ItemEventPlanning>();
		List<ItemEventPlanning> results= new ArrayList<ItemEventPlanning>();
		for(ItemEventPlanning item : activities){	
			ItemEventPlanning activity = items.get(item.getIdEntity());
			//We avoid to display mission for partners, managers and secretaires
			boolean eligible = !(item.isMission() && (employee.getPosition().isPartner() || employee.getPosition().isManagerMission() || employee.getPosition().isSecretaire() ));
			
			//If non-chargeable we must filter some specifics items only
			if(eligible){
				if(!item.isMission()){
					eligible = isActivityRetainForPlanning(item.getTitle());
				}
			}
			if(eligible){
				if(activity == null){
					items.put(item.getIdEntity(), item);
				}
				else{
					activity.setTotalHoursSpent(activity.getTotalHoursSpent()+item.getTotalHoursSpent());
				}
			}
			
		}
		
		
		//Filter only items greater or equalls to 2 hours
		for( ItemEventPlanning item :items.values()){
			if(item.getTotalHoursSpent()>=2){
				results.add(item);
			}
		}
		
		return results;
	}
	
	
	public Set<ItemEventPlanning> getSortedItemEventPlannings() {
		
		Set<ItemEventPlanning> collection  = new TreeSet<ItemEventPlanning>(new ItemPlanningComparator());
		collection.addAll(activities);
		return  collection;
	}
	
	
	class ItemPlanningComparator implements Comparator<ItemEventPlanning>{

		public int compare(ItemEventPlanning emp1, ItemEventPlanning emp2){

		//parameter are of type Object, so we have to downcast it to Employee objects

			Long id1 = ( (ItemEventPlanning) emp1).getId();

			Long id2 = ( (ItemEventPlanning) emp2).getId();

		if( id1 > id2 )

		return -1;

		else if( id1 < id2 )

		return 1;

		else

		return 0;

		}

		}


	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
    
    

}
