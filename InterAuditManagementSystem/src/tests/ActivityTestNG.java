package tests;

import java.text.ParseException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Position;
import com.interaudit.domain.model.Task;
import com.interaudit.domain.model.data.ActivityData;
import com.interaudit.domain.model.data.MissionData;
import com.interaudit.service.IActivityService;
import com.interaudit.service.IMissionService;
import com.interaudit.service.IRoleService;
import com.interaudit.service.ITaskService;
import com.interaudit.service.IUserService;
import com.interaudit.service.param.SearchActivityParam;
import com.interaudit.service.param.SearchEmployeeParam;
import com.interaudit.service.param.SearchMissionParam;
import com.interaudit.service.view.ActivityView;
import com.interaudit.service.view.EmployeeView;
import com.interaudit.service.view.InterventionDetailsView;
import com.interaudit.service.view.MissionView;

public class ActivityTestNG {

	// couche service
	private IActivityService service;
	private IMissionService missionService;
	private ITaskService taskService;
	private IRoleService roleService;
	private IUserService userService;

	
	
	@BeforeClass
	public void init() throws ParseException {
		
		// log
		log("init");
		// configuration de l'application
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		// couche service
		service = (IActivityService) ctx.getBean("activityService");
		//missionService = (IMissionService )ctx.getBean("missionService");
		//taskService = (ITaskService) ctx.getBean("taskService");
		//roleService = (IRoleService) ctx.getBean("roleService");
		//userService = (IUserService) ctx.getBean("userService");
		
		
		 loadMissionDocuments();
		//listMissions();
		
		//dump2();
		
		// on affiche
		//dump();
		
		// on vide la base
		//clean();
		
		// on la remplit
		//fill();
		
		// on affiche
		//dump();
		//listActivities();
		
		
	}

	@AfterClass
	public void terminate() {
		/*
		// affichage tables
		System.out.println("--------------- contenu de la base");
		dumpCategories();
		dumpArticles();
		System.out.println("-----------------------------------");
		*/
	}

	// logs
	private void log(String message) {
		//System.out.println("----------- " + message);
	}
	
	public void loadMissionDocuments() {
		//InterventionDetailsView object =service.buildInterventionDetailsView (360L);
		//System.out.println("----------- " + object);
	}

	// remplissage tables
	public void fill() throws ParseException {
		
		
		SearchEmployeeParam paramE = new SearchEmployeeParam();
    	EmployeeView viewE = userService.getMatchingEmployees(paramE,1,10);
    	List<Employee> employees = viewE.getEmployees();
    	
    	Task task = taskService.getOne(14L);
		
		//On supprime toutes les missions 
		SearchMissionParam param = new SearchMissionParam();
    	MissionView view = missionService.searchMissions(null,param);
    	//System.out.println("Total Mission : " + view.getMissions().size());
    	for(MissionData missionData : view.getMissions()){
    		//System.out.println("Mission : " + missionData.getId());
    		Mission mission = missionService.getOne(missionData.getId());
    		
    		if( mission != null){
    			int order = 0;
    			for(Employee employee : employees){
    				Position profile =  roleService.getOne(employee.getPosition().getId());
    				/*
    				Activity activity =  new Intervention( employee,  task, new Date(),
    						new Date(), mission, profile, 10,++order,"" +
    								"NORMAL",0.0,0.0);
    								*/
    				Activity activity = null;
    				service.saveOne(activity);
    				
    			}
    			
    		}
    	}
		
		
		
	}
	
	
	public void listActivities(){
		SearchActivityParam param = new SearchActivityParam();
    	ActivityView view = service.searchActivities(param);
    	for(ActivityData activityData : view.getActivities()){
    		//System.out.println("activityData : " + activityData.getId());
    	}
	}

	// supression éléments des tables
	public void clean() {
		/*
		//On supprime toutes les missions 
		SearchMissionParam param = new SearchMissionParam();
    	MissionView view = missionService.searchMissions(param);
    	System.out.println("Total Mission : " + view.getMissions().size());
    	for(MissionData missionData : view.getMissions()){
    		System.out.println("Mission : " + missionData.getId());
    		missionService.deleteOne(missionData.getId());
    	}
    	
    	//On supprime toutes les budgets
    	List<Integer> years = new ArrayList<Integer> ();
		years.add(1998);
		years.add(1999);
		years.add(2000);
		years.add(2001);
		years.add(2002);
		years.add(2003);
		years.add(2004);
		years.add(2005);
		years.add(2006);
		years.add(2007);
		years.add(2008);
		years.add(2009);
    	SearchBudgetParam param2 = new SearchBudgetParam();
		param2.setYears(years);
		BudgetGeneralView budgets = service.searchBudgets(param2);
		
		int i = 0;
		
		//On supprime tous les employees
		for (AnnualBudgetData budgetData : budgets.getBudgets()) {
			System.out.println(budgetData.getId());
			service.deleteOne(budgetData.getId());
			i++;
		}
		
		*/
	}

	// affichage contenu table categorie
	private void dump() {
	
		 /*
		System.out.format("[customers]%n");
		for (Exercise exercise : service.getAll()) {
			System.out.println(exercise);
		}
		*/
		
	}
	
	
	
	 

	

	@Test()
	public void test01() {
		/*
		log("test01");
		dumpCategories();
		dumpArticles();
		// liste des catégories
		List<Categorie> categories = service.getAllCategories();
		assert 3 == categories.size();
		// liste des articles
		List<Article> articles = service.getAllArticles();
		assert 3 == articles.size();
		*/
	}

	@Test(dependsOnMethods = "test01")
	public void test02() {
		/*
		log("test02");
		// catégorie A
		List<Categorie> categories = service.getAllCategoriesWithNomLike("A%");
		assert 1 == categories.size();
		// categorie B
		categories = service.getAllCategoriesWithNomLike("B%");
		assert 1 == categories.size();
		// categorie C
		categories = service.getAllCategoriesWithNomLike("C%");
		assert 1 == categories.size();
		// articles A
		List<Article> articles = service.getAllArticlesWithNomLike("A%");
		assert 2 == articles.size();
		// articles B
		articles = service.getAllArticlesWithNomLike("B%");
		assert 1 == articles.size();
		// articles C
		articles = service.getAllArticlesWithNomLike("C%");
		assert 0 == articles.size();
		*/
	}

	@Test(dependsOnMethods = "test02")
	public void test03() {
		/*
		log("test03");
		// catégorie A
		List<Categorie> categories = service.getAllCategoriesWithNomLike("A%");
		assert 1 == categories.size();
		Categorie categorieA = categories.get(0);
		// articles associés
		List<Article> articles = service.getArticlesFromCategorie(categorieA.getId());
		// vérification
		assert 2 == articles.size();
		// on ajoute un article
		Article articleA3 = new Article();
		articleA3.setNom("A3");
		// on le met dans la catégorie A
		articleA3.setCategorie(categorieA);
		// on le persiste
		service.saveArticle(articleA3);
		// articles associés à la catégorie A
		articles = service.getArticlesFromCategorie(categorieA.getId());
		// vérification - il doit y en avoir 1 de plus
		assert 3 == articles.size();
		// articles A
		articles = service.getAllArticlesWithNomLike("A%");
		assert 3 == articles.size();
		*/
	}

	@Test(dependsOnMethods = "test03")
	public void test04() {
		/*
		log("test04");
		// suppression catégorie B avec les articles associés
		List<Categorie> categories = service.getAllCategoriesWithNomLike("B%");
		assert 1 == categories.size();
		Categorie categorieB = categories.get(0);
		service.deleteCategoriesWithArticles(new Categorie[] { categorieB });
		// vérification
		categories = service.getAllCategoriesWithNomLike("B%");
		assert 0 == categories.size();
		// articles B
		List<Article> articles = service.getAllArticlesWithNomLike("B%");
		assert 0 == articles.size();
		*/
	}

	@Test(dependsOnMethods = "test04")
	public void test05() {
		/*
		log("test05");
		// catégorie A
		List<Categorie> categories = service.getAllCategoriesWithNomLike("A%");
		assert 1 == categories.size();
		Categorie categorieA = categories.get(0);
		// on modifie son nom
		categorieA.setNom("A+");
		service.updateCategorie(categorieA);
		// catégorie A
		categories = service.getAllCategoriesWithNomLike("A%");
		assert 1 == categories.size();
		categorieA = categories.get(0);
		assert "A+".equals(categorieA.getNom());
		// article A2
		List<Article> articles = service.getAllArticlesWithNomLike("A2%");
		assert 1 == articles.size();
		Article articleA2 = articles.get(0);
		// on modifie son nom
		articleA2.setNom("A2+");
		service.updateArticle(articleA2);
		// article A2
		articles = service.getAllArticlesWithNomLike("A2%");
		assert 1 == articles.size();
		articleA2 = articles.get(0);
		assert "A2+".equals(articleA2.getNom());
		*/
	}

	@Test(dependsOnMethods = "test05")
	public void test06() {
		/*
		log("test06");
		// créer deux nouvelles catégories
		Categorie categorieD = new Categorie();
		categorieD.setNom("D");
		Categorie categorieE = new Categorie();
		categorieD.setNom("E");
		// créer 1 nouvel article avec un nom existant (violation contrainte d'unicité)
		Article articleD1 = new Article();
		articleD1.setNom("A1");
		// le relier à catégorie D
		articleD1.setCategorie(categorieD);
		categorieD.getArticles().add(articleD1);
		// on doit avoir une exception lors de la sauvegarde de article D1 et un rollback général
		boolean erreur = false;
		try {
			// persister les catégories et les articles associés
			service.saveCategoriesWithArticles(new Categorie[] { categorieD, categorieE });
		} catch (RuntimeException e) {
			erreur = true;
		}
		// vérifications : il y a du avoir une exception
		assert erreur;
		// et un rollback
		// catégorie D : elle ne doit pas exister
		List<Categorie> categories = service.getAllCategoriesWithNomLike("D%");
		assert 0 == categories.size();
		// catégorie E : elle ne doit pas exister
		categories = service.getAllCategoriesWithNomLike("E%");
		assert 0 == categories.size();
		// articles : il doit tjrs y avoir 3 articles
		List<Article> articles = service.getAllArticles();
		assert 3 == articles.size()
		*/
	}

	

	
}
