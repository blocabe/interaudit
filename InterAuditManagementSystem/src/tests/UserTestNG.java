package tests;

import java.text.ParseException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.interaudit.service.IRoleService;
import com.interaudit.service.IUserService;

public class UserTestNG {

	// couche service
	private IUserService service;
	private IRoleService roleservice;

	@BeforeClass
	public void init() throws ParseException {
		
		// log
		log("init");
		// configuration de l'application
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		// couche service
		service = (IUserService) ctx.getBean("userService");
		roleservice = (IRoleService) ctx.getBean("roleService");
		
		// on affiche
		dump();

		// on vide la base
		clean();
		
		// on la remplit
		fill();
		
		// on affiche
		dump();
		
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
		System.out.println("----------- " + message);
	}

	// remplissage tables
	public void fill() throws ParseException {
		
		// créer trois employee
		/*
		Position positionPartner = roleservice.getRoleByCode(Position.PARTNER);
		
		Employee employeeA = new  Employee( roleservice.getOne(positionPartner.getId()) ,"VB", "Vero", "Veronique", "Blocail", "veronique12", "blocabe","veronique.blocail@interaudit.lu","47.68.46.853", true);
		service.saveOne(employeeA);
		
		Position positionAssistant = roleservice.getRoleByCode(Position.ASSISTANTS);
		Employee employeeB = new  Employee( roleservice.getOne(positionAssistant.getId()),"BB", "BeBe", "Bernard", "Blocail", "bernard12", "blocabe","bernard.blocail@interaudit.lu","47.68.46.852",  true);
		service.saveOne(employeeB);
		
		Position positionDirector = roleservice.getRoleByCode(Position.DIRECTOR);
		Employee employeeC = new  Employee( roleservice.getOne(positionDirector.getId()),"MB", "MoMo", "Morine", "Blocail", "morine12", "blocabe","morine.blocail@interaudit.lu","47.68.46.851",  true);
		service.saveOne(employeeC);
		
		Position positionSeniorManager = roleservice.getRoleByCode(Position.SENIOR_MANAGER);
		Employee employeeD = new  Employee(roleservice.getOne(positionSeniorManager.getId()) ,"LB", "LuLu", "Lucille", "Blocail", "lucille12", "blocabe","lucille.blocail@interaudit.lu","47.68.46.850",  true);
		service.saveOne(employeeD);
		
		
		
		/*
		 * public Employee saveOne(Employee jaspersUser);
		Categorie categorieA = new Categorie();
		categorieA.setNom("A");
		Categorie categorieB = new Categorie();
		categorieB.setNom("B");
		Categorie categorieC = new Categorie();
		categorieC.setNom("C");
		// créer 3 articles
		Article articleA1 = new Article();
		articleA1.setNom("A1");
		Article articleA2 = new Article();
		articleA2.setNom("A2");
		Article articleB1 = new Article();
		articleB1.setNom("B1");
		// les relier à leur catégorie
		articleA1.setCategorie(categorieA);
		categorieA.getArticles().add(articleA1);
		articleA2.setCategorie(categorieA);
		categorieA.getArticles().add(articleA2);
		articleB1.setCategorie(categorieB);
		categorieB.getArticles().add(articleB1);
		// persister les catégories et les articles associés
		service.saveCategoriesWithArticles(new Categorie[] { categorieA, categorieB, categorieC });
		*/
	}

	// supression éléments des tables
	public void clean() {
		//On supprime tous les employees
		/*
		for (Employee employee : service.getAll()) {
			service.deleteOne(employee.getId());
		}
		*/
	}

	// affichage contenu table categorie
	private void dump() {
	
		 /*
		System.out.format("[employees]%n");
		for (Employee employee : service.getAll()) {
			System.out.println(employee);
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

	public IUserService getService() {
		return service;
	}

	public void setService(IUserService service) {
		this.service = service;
	}

	public IRoleService getRoleservice() {
		return roleservice;
	}

	public void setRoleservice(IRoleService roleservice) {
		this.roleservice = roleservice;
	}

	
}
