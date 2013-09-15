package tests;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Origin;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IOriginService;
import com.interaudit.service.IUserService;

public class CustomerTestNG {

	// couche service
	private ICustomerService service;
	private IUserService userService;
	private IOriginService originservice;

	@BeforeClass
	public void init() throws ParseException {
		
		// log
		log("init");
		// configuration de l'application
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		// couche service
		service = (ICustomerService) ctx.getBean("customerService");
		userService  = (IUserService) ctx.getBean("userService");
		originservice = (IOriginService) ctx.getBean("originService");

		
		// on affiche
		//dump();
		
		
		// on vide la base
		//clean();
		
		// on la remplit
		//fill();
		
		addBillingAddress() ;
		
		
		// on affiche
		//dump();
		
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
		
		List<Employee> partners = userService.getPartners();
		    
		List<Employee> managers = userService.getManagers();
		    
		Employee associeSignataire = partners.get(0);
		Employee customerManager = managers.get(0);
		Origin origin = originservice.getAll().get(0);
		
		// cr�er trois societes
		/*
		Contract contractA = new Contract( "contract1", "descripion contract",
				new Date(), new Date(), 200000, "EURO");
		Customer customerA = new Customer(associeSignataire,customerManager,origin ,"10388", true, 
				"SABLIERE HEIN SA", "Fournisseur de sable","a.marx@sabliere.lu", 
				 "20.45.96.369");
		service.saveOne(customerA);
		
		Contract contractB = new Contract( "contract1", "descripion contract",
				new Date(), new Date(), 300000, "EURO");
		Customer customerB = new Customer(associeSignataire,customerManager,origin ,"82633", true, 
				"DIMPEX SA", "Fournisseur de sable","a.marx@sabliere.lu", 
				 "20.45.96.369");
		service.saveOne(customerB);
		
		Contract contractC = new Contract( "contract1", "descripion contract",
				new Date(), new Date(), 300000, "EURO");
		Customer customerC = new Customer(associeSignataire,customerManager,origin ,"11261", true, 
				"GULF OIL INTERNATIONAL LTD", "Fournisseur de sable","a.marx@sabliere.lu", 
				 "20.45.96.369");
		service.saveOne(customerC);
		
		Contract contractD = new Contract( "contract1", "descripion contract",
				new Date(), new Date(), 300000, "EURO");
		Customer customerD = new Customer(associeSignataire,customerManager,origin ,"16000", true, 
				"BORG LUXEMBOURG SA", "Fournisseur de sable","a.marx@sabliere.lu", 
				 "20.45.96.369");
		service.saveOne(customerD);
		
		Contract contractE = new Contract( "contract1", "descripion contract",
				new Date(), new Date(), 300000, "EURO");
		Customer customerE = new Customer(associeSignataire,customerManager,origin ,"12084", true, 
				"GAMET INVESTMENT SA", "Fournisseur de sable","a.marx@sabliere.lu", 
				 "20.45.96.369");
		service.saveOne(customerE);
		
		
		Contract contractF = new Contract( "contract1", "descripion contract",
				new Date(), new Date(), 300000, "EURO");
		Customer customerF = new Customer(associeSignataire,customerManager,origin ,"12084", true, 
				"LUXLAIT SA", "Fournisseur de sable","a.marx@sabliere.lu", 
				 "20.45.96.369");
		service.saveOne(customerF);
		*/
		
		
	}

	// supression �l�ments des tables
	public void clean() {
		//On supprime tous les employees
		for (Customer customer : service.getAll()) {
			service.deleteOne(customer.getId());
		}
	}

	// affichage contenu table categorie
	private void dump() {
	
		 
		System.out.format("[customers]%n");
		for (Customer customer : service.getAll()) {
			System.out.println(customer);
		}
		
	}
	
	
	private void addBillingAddress() {
		
		 
		System.out.format("[customers]%n");
		for (Customer customer : service.getAll()) {
			/*
			customer.setBillingAddress(new Address("france", "metz", "57000", "lorraine",
					"robert parisot", "57"));
					*/
			
			service.updateOne(customer);
		}
		
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

	public ICustomerService getService() {
		return service;
	}

	public void setService(ICustomerService service) {
		this.service = service;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IOriginService getOriginservice() {
		return originservice;
	}

	public void setOriginservice(IOriginService originservice) {
		this.originservice = originservice;
	}
}
