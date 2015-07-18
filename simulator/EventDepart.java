package simulator;

import robot.*;

/**
 * Evénement demandé pour tous nouvelle action du robot
 * @see Event
 */
public class EventDepart extends Event {	
	
	/**
	 * Constructeur de la classe EventDepart
	 * @param tDepart Heure de l'activation de l'événement
	 * @param robot Robot qui effectue l'événement
	 */
	public EventDepart(long tDepart, Robot robot) {
		this.date = tDepart;
		this.robotCour = robot;
	}
	
	/**
	 * Méthode Exécution de l'événment
	 * Mise en indisponibilité du robot
	 */
	public void execute(Simulator Simul) {
		this.robotCour.setDisponibilite(false);
		return;
	}
	
}