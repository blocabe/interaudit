package tests;

import java.text.ParseException;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.interaudit.domain.model.Origin;
import com.interaudit.domain.model.Position;
import com.interaudit.service.IOriginService;
import com.interaudit.service.IRoleService;

public class RoleTestNG {

	// couche service
	private IRoleService service;
	private IOriginService originservice;

	@BeforeClass
	public void init() throws ParseException {
		
		// log
		log("init");
		// configuration de l'application
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		// couche service
		service = (IRoleService) ctx.getBean("roleService");
		originservice = (IOriginService) ctx.getBean("originService");
		
		// on affiche
		dump();
		
		
		// on vide la base
		//clean();
		
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
		
		// Create the positions
		service.saveOne(new Position(Position.SENIOR_MANAGER, new Date(), new Date(),true));
		service.saveOne(new Position(Position.DIRECTOR, new Date(), new Date(),true));
		service.saveOne(new Position(Position.ASSISTANTS, new Date(), new Date(),true));
		service.saveOne(new Position(Position.PARTNER, new Date(), new Date(),true));
		
		service.saveOne(new Position(Position.MANAGING_PARTNER, new Date(), new Date(),true));
		service.saveOne(new Position(Position.SECRETAIRE, new Date(), new Date(),true));
		service.saveOne(new Position(Position.MANAGER, new Date(), new Date(),true));
		service.saveOne(new Position(Position.ASSISTANT_MANAGER, new Date(), new Date(),true));
		service.saveOne(new Position(Position.SUPERVISOR, new Date(), new Date(),true));
		service.saveOne(new Position(Position.SENIORS, new Date(), new Date(),true));
		
		
		
		
		
		//Create the origins
		originservice.saveOne(new Origin("IF","InterFiduciaire"));
		originservice.saveOne(new Origin("IA","InterAudit"));
		
		
		
		
		
		
	}

	// supression éléments des tables
	public void clean() {
		//On supprime tous les positions
		for (Position profile : service.getAll()) {
			service.deleteOne(profile.getId());
		}
		
		//On supprime tous les positions
		for (Origin origin : originservice.getAll()) {
			originservice.deleteOne(origin.getId());
		}
	}

	// affichage contenu table categorie
	private void dump() {		 
		System.out.format("[profiles]%n");
		for (Position profile : service.getAll()) {
			System.out.println(profile);
		}
		
		System.out.format("[origins]%n");
		for (Origin origin : originservice.getAll()) {
			System.out.println(origin);
		}
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

	public IRoleService getService() {
		return service;
	}

	public void setService(IRoleService service) {
		this.service = service;
	}

	public IOriginService getOriginservice() {
		return originservice;
	}

	public void setOriginservice(IOriginService originservice) {
		this.originservice = originservice;
	}
}
