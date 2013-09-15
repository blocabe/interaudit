package com.interaudit.domain.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.test.AbstractTransactionalSpringContextTests;

import com.interaudit.domain.dao.IContactDao;
import com.interaudit.domain.dao.ICustomerDao;
import com.interaudit.domain.dao.IProjectDao;
import com.interaudit.domain.dao.IRoleDao;
import com.interaudit.domain.dao.IUserDao;
import com.interaudit.domain.dao.IWeekAssignmentPerEmployeDao;
import com.interaudit.domain.model.Contact;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Position;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.data.WeekAssignmentPerEmployee;
import com.interaudit.domain.model.data.WeekPlanning;
import com.interaudit.service.IFactureService;
import com.interaudit.service.IPlanningService;
import com.interaudit.service.ITimesheetService;
import com.interaudit.service.view.TimesheetRowView;
import com.interaudit.service.view.TimesheetView;
import com.interaudit.util.Constants;

public class UserHibernateDAOTest extends AbstractTransactionalSpringContextTests{
	
	static {
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		}
	
	//	The logger
	private static final Logger log = Logger.getLogger(UserHibernateDAOTest.class);
	
	
	private IUserDao dao = null;
	
	private IRoleDao roleDao = null;
	
	private ICustomerDao customerDao = null;
	
	private IContactDao contactDao = null;
	
	private IProjectDao projectDao = null;
	
	private IWeekAssignmentPerEmployeDao weekAssignmentPerEmployeDao = null;
	
	private IPlanningService planningService = null;
	
	private ITimesheetService timesheetService = null;
	
	private IFactureService factureService = null;

	public void setDao(IUserDao dao) {
		this.dao = dao;
	}
	
	
	public IUserDao getDAO(){
		return this.dao;
	}

	protected String[] getConfigLocations() {
		return new String[] {
				"file:./conf/applicationContext.xml" };
	}

	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
		
		
	}

	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
	}
	
	public void testCreateFacture(){
		Invoice facture = null;//factureService.createFactureForMission(2L,0.0d, "libelle facture",Constants.FACTURE_TYPE_ACOMPTE);
		if( facture != null)
			System.out.print(facture.getId());
		this.setComplete();	
	}
	
	
	public void testCreateTimesheet(){
		 Employee user = this.getDAO().getOne(1L);
		String exercise = "2008";
	    int weekNumber = 1;
		 //Timesheet  timesheet = this.getTimesheetService().createOne( user, weekNumber,  exercise);
		 
		 this.setComplete();	
	}
	
	public void testAddRowToTimesheet(){
		
		//this.getTimesheetService().addRowToTimesheet(1L,null);
		this.setComplete();
	}
	
	
	public void testFindOneTimesheetForUserForAWeek(){
		
	    Long userId = 1L;
	    int weekNumber = 1;
	    int year = 2008;
		 Timesheet  timesheet = this.getTimesheetService().getOneTimesheetForUser( userId, weekNumber, year);
		 if(timesheet != null){
			 System.out.println(timesheet.getId());
			 TimesheetView view =  this.getTimesheetService().getTimesheetView(timesheet);
			 if(view != null){
				 System.out.println(timesheet.toString()); 
				 
				 double[] cells = {8d,8d,8d,8d,8d};
				 boolean[] updates = {true,true,true,true,true};
				 for (TimesheetRowView rowView : view.getRows()) {
					rowView.setCells( cells) ;
					rowView.setUpdate(updates);
				 }
				 
				 
				 Timesheet t2 = this.getTimesheetService().updateTimesheetFromTimesheetView(view);
				 if(t2 != null){
					 System.out.println(t2.getId());
				 }
				 
			 }
		 }
			
		 this.setComplete();
	}
	
	public void testCreateAnnualPlanning(){
		Employee responsable = this.getDAO().getOne(1L);
		String exercise = "2008";
		this.getPlanningService().createAnnualPlanning(responsable, exercise);
		
		this.setComplete();	
	}
	
	
	public void testGetWeekPlanning(){
		String exercise = "2008";
	    int weekNumber = 1;
		WeekPlanning weekPlanning = this.getPlanningService().getWeekPlanning( exercise,  weekNumber);
		
		this.setComplete();	
	}
	
	
	public void testCreateAssignmentForUser(){
		
		Calendar c = Calendar.getInstance();
		c.set(2008, 0, 2,0,0);
		 Date startDate = c.getTime();
		 c.set(2008, 0, 4,0,0);
		 Date endDate= c.getTime();
		 int weekNumber = 1;
		 String exercise ="2008";
		 long mission = 1L; 
		 long teamMember = 2L;
		 String code = Constants.WEEK_ASSIGNMENT_ACTIVITY_CODE_PROJECT;
		 String description ="Une mission a réalisé";
		 
		 try{
			 
		 
		 
		 WeekAssignmentPerEmployee weekAssignmentPerEmployee = 
			 this.getPlanningService().createAssignmentForMember( 
					  startDate,
					 /* endDate,*/
					  weekNumber,
					  exercise, 
					  mission, 
					  teamMember,
					  code,
					  description) ;
		 
		 this.setComplete();	
		 }catch(Exception e){
			 System.out.print(e);
		 }
	}
	
public void testCreateAssignmentForUser2(){
		
		Calendar c = Calendar.getInstance();
		c.set(2008, 0, 2,0,0);
		 Date startDate = c.getTime();
		 c.set(2008, 0, 4,0,0);
		 Date endDate= c.getTime();
		 int weekNumber = 1;
		 String exercise ="2008";
		 long mission = 2L; 
		 long teamMember = 9L;
		 String code = Constants.WEEK_ASSIGNMENT_ACTIVITY_CODE_PROJECT;
		 String description ="Une mission a réalisé";
		 
		 try{
			 
		 
		 
		 WeekAssignmentPerEmployee weekAssignmentPerEmployee = 
			 this.getPlanningService().createAssignmentForMember( 
					  startDate,
					  /*endDate,*/
					  weekNumber,
					  exercise, 
					  mission, 
					  teamMember,
					  code,
					  description) ;
		 
		 this.setComplete();	
		 }catch(Exception e){
			 System.out.print(e);
		 }
	}

   public void testFindScheduledProjectsForUser(){
	  
	   Employee user = this.getDAO().getOne(19L);
		String exercise = "2008";
		int weekNumber = 1;
		List<Mission> missions = this.getWeekAssignmentPerEmployeDao().getAllScheduledProjectForUserInWeek(user, weekNumber, exercise);
		
		for(Mission mission : missions){
			System.out.println("mission : " + mission.getId());
		}
   }
   
   public void testFindProjectsForUserId(){
	   List<Mission> missions = this.getProjectDao().findProjectsForUserId(1L);
	   for(Mission mission : missions){
			System.out.println("mission : " + mission.getId());
		}
   }
   
	
	
	public void testCreateProjectFromScratch(){
		/*
		Customer customer = this.getCustomerDao().getOne(1L);
		String exercise = "2008";
		String title ="TITRE PROJECT TEST";
		String reference = "P_0001";
			
		Mission project = new Mission( customer,
				  exercise,
		        title,
		       reference
		);
		
		this.getProjectDao().saveOne(project);
		
		this.setComplete();	
		*/
	}
	
	public void testCreateProjectFromScratch2(){
		/*
		Customer customer = this.getCustomerDao().getOne(2L);
		String exercise = "2008";
		String title ="TITRE PROJECT TEST2";
		String reference = "P_0002";
			
		Mission project = new Mission( customer,
				  exercise,
		        title,
		       reference
		);
		
		this.getProjectDao().saveOne(project);
		
		this.setComplete();
		*/	
		
	}
	
	public void testCreateProjectFromScratch3(){
		
		/*
		Customer customer = this.getCustomerDao().getOne(3L);
		String exercise = "2008";
		String title ="TITRE PROJECT TEST3";
		String reference = "P_0003";
			
		Mission project = new Mission( customer,
				  exercise,
		        title,
		       reference
		);
		
		this.getProjectDao().saveOne(project);
		
		this.setComplete();	
		*/
		
	}
	
	public void testAddUnScheduledProjectToTimesheet(){
		/*
		Mission project = this.getProjectDao().getOne(3L);
		Timesheet  timesheet = this.getTimesheetService().getOne(1L);
		this.getTimesheetService().addProjectToTimesheet(timesheet, project);
		this.setComplete();	
		*/
	}
	
	public void testRemoveProjectFromTimesheet(){
		/*
		Mission project = this.getProjectDao().getOne(3L);
		Timesheet  timesheet = this.getTimesheetService().getOne(1L);
		this.getTimesheetService().removeProjectFromTimesheet(timesheet, project);
		this.setComplete();	
		*/
	}
	
	
	public void testAddTeamToProject(){
		/*
		Mission project = this.getProjectDao().getOne(2L);
		
		User user1 = this.getDAO().getOne(16L);
		User user2 = this.getDAO().getOne(17L);
		User user3 = this.getDAO().getOne(18L);
		
		Profile roleInTeam = this.getRoleDao().getRoleByCode("AS");
		
		TeamMember teamMember1 = new  TeamMember(  true,
											      user1,
											      roleInTeam,
											      project);
		
		project.getTeamMembers().add(teamMember1);
		
		TeamMember teamMember2 = new  TeamMember(  true,
			      user2,
			      roleInTeam,
			      project);
		
		project.getTeamMembers().add(teamMember2);
		
		TeamMember teamMember3 = new  TeamMember(  true,
			      user3,
			      roleInTeam,
			      project);
		project.getTeamMembers().add(teamMember3);
		
		this.getProjectDao().updateOne(project);
			
		this.setComplete();	
		*/
		
	}
	
	public void testAddTeamToProject2(){
		/*
		Mission project = this.getProjectDao().getOne(2L);
		
		User user1 = this.getDAO().getOne(1L);
		User user2 = this.getDAO().getOne(2L);
		User user3 = this.getDAO().getOne(3L);
		
		Profile roleInTeam = this.getRoleDao().getRoleByCode("AS");
		
		TeamMember teamMember1 = new  TeamMember(  true,
											      user1,
											      roleInTeam,
											      project);
		
		project.getTeamMembers().add(teamMember1);
		
		TeamMember teamMember2 = new  TeamMember(  true,
			      user2,
			      roleInTeam,
			      project);
		
		project.getTeamMembers().add(teamMember2);
		
		TeamMember teamMember3 = new  TeamMember(  true,
			      user3,
			      roleInTeam,
			      project);
		project.getTeamMembers().add(teamMember3);
		
		this.getProjectDao().updateOne(project);
			
		this.setComplete();	
		*/
		
	}
	
	
	
	
	

	public void testCreateRoles() throws Exception  {	
	
	   
		/*
		this.getRoleDao().saveOne(new Position(Constants.ROLE_NAME_SENIOR_MANAGER,
										   Constants.ROLE_NAME_SENIOR_MANAGER_CODE,
										   "Senior Manager in InterAudit"));
		
		this.getRoleDao().saveOne(new Position(Constants.ROLE_NAME_SENIOR,
				Constants.ROLE_NAME_SENIOR_CODE,
		   "Senior in InterAudit"));
		
		this.getRoleDao().saveOne(new Position(Constants.ROLE_NAME_ASSOCIE,
				Constants.ROLE_NAME_ASSOCIE_CODE,
		   "Associé in InterAudit"));
		
		this.getRoleDao().saveOne(new Position(Constants.ROLE_NAME_SECRETAIRE,				
				Constants.ROLE_NAME_SECRETAIRE_CODE,
		   "Secrétaire in InterAudit"));
		
		this.getRoleDao().saveOne(new Position(Constants.ROLE_NAME_MANAGER,
				Constants.ROLE_NAME_MANAGER_CODE,
		   "Manager in InterAudit"));
		
		this.getRoleDao().saveOne(new Position(Constants.ROLE_NAME_ASSISTANT,
				Constants.ROLE_NAME_ASSISTANT_CODE,
		   "Assistant in InterAudit"));
		
		this.getRoleDao().saveOne(new Position(Constants.ROLE_NAME_ASSISTANT_MANAGER,
				Constants.ROLE_NAME_ASSISTANT_MANAGER_CODE,
		   "Assistant Manager in InterAudit"));
		
		this.getRoleDao().saveOne(new Position(Constants.ROLE_NAME_SUPERVISOR,
				Constants.ROLE_NAME_SUPERVISOR_CODE,
		   "Supervisor in InterAudit"));
		
		this.getRoleDao().saveOne(new Position(Constants.ROLE_NAME_ADMIN,				
				Constants.ROLE_NAME_ADMIN_CODE,
		   "System Administrator"));
		
		this.setComplete();	
		*/			
	}
	
	

	public void testListRoles(){
		List<Position> roles = this.getRoleDao().getAll();
		
		if( roles != null){
			
			for(java.util.Iterator iter = roles.iterator(); iter.hasNext();){
				
				Position role = (Position) iter.next();
				
				log.debug("--------------*****ROLE*******-------------");
				
				log.debug("Identifier : " + role.getId());
				log.debug("Name : " + role.getName());
				//log.debug("Description : " + role.getDescription());
			}
			
		}
	
	}
	
	public void testListUsers(){
		List<Employee> users = this.getDAO().getAll();
		
		if( users != null){
			
			for(java.util.Iterator iter = users.iterator(); iter.hasNext();){
				
				Employee user = (Employee) iter.next();
				
				log.debug("--------------*****ROLE*******-------------");
				
				log.debug("Identifier : " + user.getId());
				log.debug("Name : " + user.getFirstName() + " " + user.getLastName());
				log.debug("Eùmail : " + user.getEmail());
			}
			
		}
	
	}
	
	public void testCreateUsers() throws Exception  {
	String[] personnels ={
			
			
			"MB;CIMOLINO;Murielle;150;SM;1;murielle.badoux@interaudit.lu;mbdoux;123456",
			
			"EK;KOSTKA;Edward;250;ASS;1;edward.kostka@interaudit.lu;ekostka;123456",
			
			"ET;THOLL;Eliane;80;SEC;0;eliane.tholl@interaudit.lu;etholl;123456",
			
			"OB;BIREN;Olivier;110;M;1;olivier.bien@interaudit.lu;obiren;123456",
			
			"BG;BASTIN;Geneviève;110;M;1;genevieve.gbastin@interaudit.lu;gbastin;123456",
			
			"STG;GAILLARD;Stéphane;80;AM;1;stephane.gaillard@interaudit.lu;sgaillard;123456",
			
			"VD;DOGS;Vincent;250;ASS;1;vincent.dogs@interaudit.lu;vdogs;123456",
			
			"MG;GRUN;Martine;65;AS;1;martine.grun@interaudit.lu;mgrun;123456",
			
			"LAM;MARIANI;Laurent;80;AM;1;laurent.mariani@interaudit.lu;lmariani;123456",
			
			"NEG;GONCALVES;Nélia;80;SE;1;nelia.goncalves@interaudit.lu;ngoncalves;123456",
			
			"IM;MUNAUT;Inge;80;SE;1;inge.munaut@interaudit.lu;imunaut;123456",
			
			"MAM;AMICO;Manuela;65;AS;1;manuela.amico@interaudit.lu;mamico;123456",
			
			"VB;BLOCAIL;Véronique;40;SEC;1;veronique.blocail@interaudit.lu;vblocail;123456",
			
			"MBT;BARTHELEMY;Maud;65;SE;1;maud.barthelemy@interaudit.lu;mbarthelemy;123456",
			
			"FC;COIMBRA;Filipe;65;SE;0;filipe.coimbra@interaudit.lu;fcoimbra;123456",
			
			"VS;SCHOUMACKER;Virginie;40;SE;1;virginie.schoumacker@interaudit.lu;vschoumacker;123456",
			
			"SD;DUMENY;Sébastien;40;AS;1;sebastien.dumeny@interaudit.lu;sdumeny;123456",
			
			"OD;OGE;DOROTHEE;40;AS;1;dorothee.oge@interaudit.lu;doge;123456",
			
			"GH;GOVERNO;Hervé;40;AS;1;herve.governo@interaudit.lu;hgoverno;123456",
			
			"DP;DAMOISEAU;Philippe;40;AS;1;phillippe.damoiseau@interaudit.lu;pdamoiseau;123456",
			
			"SS;SIMON;Sabrina;40;AS;1;sabrina.simon@interaudit.lu;ssimon;123456",
			
			"CC;COLOT;CELINE;40;AS;1;celine.colot@interaudit.lu;ccolot;123456",
			
			"AG;Andrea;Goltz;40;AS;1;andrea.goltz@interaudit.lu;agoltz;123456",
			
			"MW;Wiwinius;Maureen;40;AS;1;maureen.wiwinius@interaudit.lu;mwiwinius;123456",
			
			"DH;Hubin;Dimitri;40;AS;1;dimitri.hubin@interaudit.lu;dhubin;123456",
			
			"JS;Soreille;Jonathan;40;AS;1;jonathan.soreille@interaudit.lu;jsoreille;123456",
			
			"OJ;Janssen;Olivier;40;AS;1;olivier.jansen@interaudit.lu;ojansen;123456",
			
			"AM;Mechachti;Ahmed;40;SE;1;ahmed.mechachti.lu;amechachti;123456",
			
			"FD;Dodemont;Fabrice;40;SE;1;fabrice.dodemont@interaudit.lu;fdodemont;123456",
			
			"SR;Rufo;Sergio;40;SE;1;sergio.rufo@interaudit.lu;srufo;123456",
			
			"SB;Baumeister;Sabine;40;SE;1;sabine.baumeister@interaudit.lu;sbaumeister;123456"

			
	};
	
//	{"CODE;NOM;PRENOM;TAUX HORAIRE;ROLE_CODE;ACTIVE;EMAIL;LOGIN;PASSWORD"},
	
	for(String data :personnels){
		
		StringTokenizer stzr = new StringTokenizer(data,";");
		int count = 0;
		Employee user = new Employee();
		while(stzr.hasMoreTokens()){
			String token = stzr.nextToken();
			token = token.toLowerCase().trim();
			
			switch(count){
			case 0:
				user.setCode(token);
				break;
			
			case 1:
				user.setFirstName(token);
				break;
			
			case 2:
				user.setLastName(token);
				break;
				
			case 3:
				//user.setTauxHoraire(Float.parseFloat(token));
				break;
				
			case 4:
				Position defaultRole = this.getRoleDao().getRoleByCode(token.toUpperCase());
				//user.setDefaultRole(defaultRole);
				break;
			
			case 5:
				user.setUserActive( token.equalsIgnoreCase("1")?true:false);
				break;
			
			case 6:
				user.setEmail(token);
				break;
				
			
			case 7:
				user.setLogin(token);				
				break;
				
			case 8:
				user.setPassword(token);
				break;
			

			
			}
			
			count++;
		}
		
		this.getDAO().saveOne(user);
		
	}
	
	
	
	this.setComplete();	
	}
	
	
	public void testCreateCustomers(){
		
		String[] customers ={
				
				"10388;SABLIERE HEIN SA;VD;STG",
				"82633;DIMPEX SA;EK;OB",
				"11261;GULF OIL INTERNATIONAL LTD-audit;EK;OB",
				"65255;INN SIDE HOTEL (LUXEMBOURG) SA;VD;OB"
				
		};
		
		
		for(String data :customers){
			
			StringTokenizer stzr = new StringTokenizer(data,";");
			int count = 0;
			Customer customer = new Customer();
			while(stzr.hasMoreTokens()){
				String token = stzr.nextToken();
				token = token.toLowerCase().trim();
				
				switch(count){
				case 0:
					customer.setCode(token);
					break;
				
				case 1:
					customer.setCompanyName(token);
					break;
				
				case 2:
					Employee associeSignataire = this.getDAO().getOneFromCode(token);
					//customer.setAssocieSignataire(associeSignataire);
					break;
					
				case 3:
					Employee customerManager =  this.getDAO().getOneFromCode(token);
					//customer.setCustomerManager(customerManager);
					break;
				
				}
				
				count++;
			}
			
			this.getCustomerDao().saveOne(customer);
			
		}
		
		this.setComplete();

	}
	
	
	public void testAddContactsToCustomers(){
		String[] contacts ={
				
				
				 "10388;Angélique;Marx;a.marx@heingroup.lu",							
				 "82633;Michael;Hemmer",	
				 "11261;Alain;Dujean",																					
				 "65255;Horreman"																
		};
		
		for(String data :contacts){
			
			StringTokenizer stzr = new StringTokenizer(data,";");
			int count = 0;
			Contact contact = new Contact();
			while(stzr.hasMoreTokens()){
				String token = stzr.nextToken();
				token = token.toLowerCase().trim();
				
				switch(count){
				case 0:
					Customer customer = this.getCustomerDao().getOneFromCode(token);
					contact.setCustomer(customer);
					break;
				
				case 1:
					contact.setFirstName(token);
					break;
				
				case 2:
					contact.setLastName(token);
					break;
					
				case 3:
					contact.setEmail(token);
					break;
				
				}
				
				count++;
			}
			
			this.getContactDao().saveOne(contact);
			
		}
		
		this.setComplete();

		
	}
	
	
	public void testCalendar(){
		int year = Integer.parseInt("2008");
		int weekNumber = 1;
		
		Calendar c = Calendar.getInstance();			
		c.set(Calendar.YEAR,year);
		c.set(Calendar.WEEK_OF_YEAR,weekNumber);
		c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
		
		int dayOfFirstWeek = c.getFirstDayOfWeek();
		//c.set(Calendar.DAY_OF_WEEK , dayOfFirstWeek);
        int minimalDaysInWeek =  c.getMinimalDaysInFirstWeek();
        
        
		
		System.out.print("minimalDaysInWeek " + minimalDaysInWeek);
		
		System.out.print("dayOfFirstWeek " + dayOfFirstWeek);
		
		Date date = c.getTime();
		
		System.out.print("first date " + date);
		
		c.set(Calendar.DAY_OF_WEEK , Calendar.FRIDAY);
		c.set(Calendar.YEAR,year);
		
		Date date2 = c.getTime();
		
		System.out.print("last date " + date2);
		
		 minimalDaysInWeek =  c.getMinimalDaysInFirstWeek();
		
		System.out.print("minimalDaysInWeek " + minimalDaysInWeek);
		
	}
	
	
	
	
	
	
	
	


	
	
	
	
	public IRoleDao getRoleDao() {
		return roleDao;
	}


	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}


	public ICustomerDao getCustomerDao() {
		return customerDao;
	}


	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}


	public IContactDao getContactDao() {
		return contactDao;
	}


	public void setContactDao(IContactDao contactDao) {
		this.contactDao = contactDao;
	}


	public IProjectDao getProjectDao() {
		return projectDao;
	}


	public void setProjectDao(IProjectDao projectDao) {
		this.projectDao = projectDao;
	}


	public IPlanningService getPlanningService() {
		return planningService;
	}


	public void setPlanningService(IPlanningService planningService) {
		this.planningService = planningService;
	}


	public IWeekAssignmentPerEmployeDao getWeekAssignmentPerEmployeDao() {
		return weekAssignmentPerEmployeDao;
	}


	public void setWeekAssignmentPerEmployeDao(
			IWeekAssignmentPerEmployeDao weekAssignmentPerEmployeDao) {
		this.weekAssignmentPerEmployeDao = weekAssignmentPerEmployeDao;
	}


	public ITimesheetService getTimesheetService() {
		return timesheetService;
	}


	public void setTimesheetService(ITimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}


	public IFactureService getFactureService() {
		return factureService;
	}


	public void setFactureService(IFactureService factureService) {
		this.factureService = factureService;
	}
	
	
	

}
