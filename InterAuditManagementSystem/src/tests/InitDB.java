package tests;

import java.text.ParseException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.interaudit.domain.model.Task;



public class InitDB {

	// constantes
	//private final static String TABLE_NAME = "jpa02_hb_personne";

	public static void main(String[] args) throws ParseException {

		// Contexte de persistance
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = null;
		// on r�cup�re un EntityManager � partir de l'EntityManagerFactory pr�c�dent
		em = emf.createEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// requ�te
		//Query sql1;
		// supprimer les �l�ments de la table PERSONNE
		//sql1 = em.createNativeQuery("delete from " + TABLE_NAME);
		//sql1.executeUpdate();
		// cr�ation personnes
		
		// persistance des personnes
		
		// affichage personnes
		System.out.println("[personnes]");
		for (Object p : em.createQuery("select t from Task t order by t.name asc").getResultList()) {
			System.out.println(p);
		}
		// fin transaction
		tx.commit();
		// fin EntityManager
		em.close();
		// fin EntityManagerFactory
		emf.close();
		// log
		System.out.println("termin�...");

	}
}
