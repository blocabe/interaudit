package tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.interaudit.domain.model.Exercise;
import com.interaudit.service.IExerciseService;

public class ExerciseTestNG {

	// couche service
	private IExerciseService service;
	

	@BeforeClass
	public void init() throws ParseException {
		
		// log
		log("init");
		// configuration de l'application
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		// couche service
		service = (IExerciseService) ctx.getBean("exerciseService");
		
		//dump2();
		
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
		
		// cr�er trois societes
		/*
		Customer customer = customerService.getOne(115L);
		
		Contract contract = (Contract)customer.getContracts().toArray()[0];
		
		Exercise exercise = new Exercise(1998);
		
		Employee employee = userService.getOne(20L);
		
		//Associe associe = new Associe(employee, exercise);
		
		AnnualBudget budget = new AnnualBudget( exercise, contract);
		
		exercise.getBudgets().add(budget);
		//exercise.getAssocies().add(associe);
		service.saveOne(exercise);
		
		service.buildExercise(1998);
		service.buildExercise(1999);
		service.buildExercise(2000);
		service.buildExercise(2001);
		service.buildExercise(2002);
		service.buildExercise(2003);
		service.buildExercise(2004);
		service.buildExercise(2005);
		service.buildExercise(2006);
		service.buildExercise(2007);
		service.buildExercise(2008);
		service.buildExercise(2009);
		*/
		
		
		
		
	}

	// supression �l�ments des tables
	public void clean() {
		//On supprime tous les employees
		for (Exercise exercise : service.getAll()) {
			service.deleteOne(exercise.getId());
		}
	}

	// affichage contenu table categorie
	private void dump() {
	
		 
		System.out.format("[customers]%n");
		for (Exercise exercise : service.getAll()) {
			System.out.println(exercise);
		}
		
	}
	
	private void dump2() {
		
		 
		System.out.format("[budgets]%n");
		List<Integer> years = new ArrayList<Integer> ();
		years.add(1998);
		years.add(1999);
		years.add(2000);
		years.add(2008);
		/*
		List<AnnualBudgetView> budgets = service.getBudgetsForSelectedExercises(years );
		for (AnnualBudgetView budget : budgets) {
			System.out.println(budget);
		}
		*/
		
	}
	
	 

	

	@Test()
	public void test01() {
		/*
		log("test01");
		dumpCategories();
		dumpArticles();
		// liste des cat�gories
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
		// cat�gorie A
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
		// cat�gorie A
		List<Categorie> categories = service.getAllCategoriesWithNomLike("A%");
		assert 1 == categories.size();
		Categorie categorieA = categories.get(0);
		// articles associ�s
		List<Article> articles = service.getArticlesFromCategorie(categorieA.getId());
		// v�rification
		assert 2 == articles.size();
		// on ajoute un article
		Article articleA3 = new Article();
		articleA3.setNom("A3");
		// on le met dans la cat�gorie A
		articleA3.setCategorie(categorieA);
		// on le persiste
		service.saveArticle(articleA3);
		// articles associ�s � la cat�gorie A
		articles = service.getArticlesFromCategorie(categorieA.getId());
		// v�rification - il doit y en avoir 1 de plus
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
		// suppression cat�gorie B avec les articles associ�s
		List<Categorie> categories = service.getAllCategoriesWithNomLike("B%");
		assert 1 == categories.size();
		Categorie categorieB = categories.get(0);
		service.deleteCategoriesWithArticles(new Categorie[] { categorieB });
		// v�rification
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
		// cat�gorie A
		List<Categorie> categories = service.getAllCategoriesWithNomLike("A%");
		assert 1 == categories.size();
		Categorie categorieA = categories.get(0);
		// on modifie son nom
		categorieA.setNom("A+");
		service.updateCategorie(categorieA);
		// cat�gorie A
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
		// cr�er deux nouvelles cat�gories
		Categorie categorieD = new Categorie();
		categorieD.setNom("D");
		Categorie categorieE = new Categorie();
		categorieD.setNom("E");
		// cr�er 1 nouvel article avec un nom existant (violation contrainte d'unicit�)
		Article articleD1 = new Article();
		articleD1.setNom("A1");
		// le relier � cat�gorie D
		articleD1.setCategorie(categorieD);
		categorieD.getArticles().add(articleD1);
		// on doit avoir une exception lors de la sauvegarde de article D1 et un rollback g�n�ral
		boolean erreur = false;
		try {
			// persister les cat�gories et les articles associ�s
			service.saveCategoriesWithArticles(new Categorie[] { categorieD, categorieE });
		} catch (RuntimeException e) {
			erreur = true;
		}
		// v�rifications : il y a du avoir une exception
		assert erreur;
		// et un rollback
		// cat�gorie D : elle ne doit pas exister
		List<Categorie> categories = service.getAllCategoriesWithNomLike("D%");
		assert 0 == categories.size();
		// cat�gorie E : elle ne doit pas exister
		categories = service.getAllCategoriesWithNomLike("E%");
		assert 0 == categories.size();
		// articles : il doit tjrs y avoir 3 articles
		List<Article> articles = service.getAllArticles();
		assert 3 == articles.size()
		*/
	}

	public IExerciseService getService() {
		return service;
	}

	public void setService(IExerciseService service) {
		this.service = service;
	}

	
}
