// Auteur : Mathieu G.
// Site : http://www.design-patterns.fr
// Licence : GNU General Public License
package multiton;

//Classe principale de l'application.
public class Main 
{
	// Méthode principale.
	public static void main(String[] args) 
	{
		// Création et utilisation du CompteBancaire cb1.
		CompteBancaire cb1 = new CompteBancaire(123456789);
		cb1.deposerArgent(100);
		cb1.retirerArgent(80);
		// Création et utilisation du CompteBancaire cb2.
		CompteBancaire cb2 = new CompteBancaire(987654321);
		cb2.retirerArgent(10);
		// Affichage des logs "informations" en console.
		String s = Journalisation.getInstance("informations").afficherLog();
		System.out.println("Informations :\n"+s);
		// Affichage des logs "erreurs" en console.
		s = Journalisation.getInstance("erreurs").afficherLog();
		System.out.println("Erreurs :\n"+s);
	}

}
