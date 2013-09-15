// Auteur : Mathieu G.
// Site : http://www.design-patterns.fr
// Licence : GNU General Public License
package singleton;

// Classe principale de l'application.
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
		// Affichage des logs en console.
		String s = Journalisation.getInstance().afficherLog();
		System.out.println(s);
	}

}
