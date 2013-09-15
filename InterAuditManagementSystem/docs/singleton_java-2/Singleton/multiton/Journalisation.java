// Auteur : Mathieu G.
// Site : http://www.design-patterns.fr
// Licence : GNU General Public License
package multiton;
//Utilisé pour la date afficher dans les logs.
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
// Utilisé pour la table de hashage d'instances.
import java.util.HashMap;

//Classe basée sur le pattern Singleton qui permet la journalisation de l'application.
public class Journalisation 
{
	private static HashMap<String,Journalisation> instances = new HashMap<String,Journalisation>();// Table de hashage d'instance (clef, valeur).
	private String log;// Chaine de caractères représentant les messages de log.
	
	// Constructeur en privé (donc inaccessible à l'extérieur de la classe).
	private Journalisation()
	{
		log = new String();
	}
	
	// Méthode statique qui sert de pseudo-constructeur (utilisation du mot clef "synchronized" pour le multithread).
	public static synchronized Journalisation getInstance(String clef)
	{
		Journalisation inst = instances.get(clef);
		if (inst == null) 
		{
            inst = new Journalisation();
            instances.put(clef, inst);
		}
		return inst;
	}
	
	// Méthode qui permet d'ajouter un message de log.
	public void ajouterLog(String log)
	{
		// On ajoute également la date du message.
		Date d = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH'h'mm");
		this.log+="["+dateFormat.format(d)+"] "+log+"\n";
	}
	
	// Méthode qui retourne tous les messages de log.
	public String afficherLog()
	{
		return log;
	}
}

