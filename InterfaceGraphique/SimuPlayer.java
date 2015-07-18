package InterfaceGraphique;
import carte.*;
import simulator.*;
import fr.ensimag.gui.Display;
import fr.ensimag.simulation.SimulationPlayer;
import java.util.List;
import source.*;

/**
 * Classe permettant la visualisation graphique de la simulation
 */
public class SimuPlayer extends SimulationPlayer{

	/**
	 * Permet de tester des modules sans le manager ou le simulateur
	 */
	private Boolean test;
	/**
	 * fenêtre d'affichage graphique de la carte 
	 */
	private Display fenetre;
	/**
	 * carte sur laquelle on effectue la simulation
	 */
	private TabCase map;
	/**
	 * liste des cases en feu sur la carte
	 */
	private List<Case> caseEnFeu;
	/**
	 * manager qui gère les actions des robots
	 */
	private Manager Manager;
	/**
	 * simulateur qui gère les différents événements de la simulation
	 */
	private Simulator simu;

	/**
	 * constructeur par défaut
	 */
	public SimuPlayer(){
		super();
		fenetre = new Display(0,0);
		map = new TabCase(0);
	}
	/**
	 * Constructeur 
	 * @param lig nombre de lignes de la carte
	 * @param col nombre de colonnes de la carte
	 * @param nbPixels taille en pixel d'une case de la carte
	 * @param map carte
	 * @param man manager qui gère les actions des robots
	 * @param simu simulateur qui gère les événements de la simulation
	 */
	public SimuPlayer(int lig, int col, int nbPixels, TabCase map, Manager man, Simulator simu, Boolean test){
		super();
		fenetre = new Display(lig, col, nbPixels, this);
		this.map = map;
		this.simu = simu;
		this.Manager = man;
		this.test=test;
	}

	/**
	 * fonction qui est executer lors de l'appui sur le bouton suivant du gestionnaire de simulation ou bien à intervalle régulier
	 * si le bouton lecture est enclenché
	 * appelle le manager puis execute tous les événements de même date du simulateur
	 */
	public void next(){
		if (!test){
			caseEnFeu = Manager.listeFeu();
			if (caseEnFeu.size()!=0) {
				Manager.traiteAllRobot(caseEnFeu);
				if (simu.hasNext()){
					do{
						simu.nextStep();
					} while (simu.hasNext() && simu.getCurrentDate()==simu.getNextDate());
				}

			}
		}
		map.AfficheMapGraphique();

	}
	/**
	 * accesseur sur la fenêtre d'affichage
	 * @return la fenêtre d'affichage de la simulation
	 */
	public Display getFenetre(){
		return this.fenetre;
	}

}