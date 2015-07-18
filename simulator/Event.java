package simulator;

import robot.*;
import exception.*;

/**
 * Classe événment 
 * Classe mère de tous les événements pouvant se produire au cours de la simulation
 */
public abstract class Event {
	
	/**
	 * Date de l'évènement
	 */
	protected long date;
	
	/**
	 * Robot appelé pour réaliser l'évènement (signature)
	 */
	protected Robot robotCour;

	//ACCESSEURS

	public long getDate() {
		return date;
	}
	
	public Robot getRobot() {
		return robotCour;
	}
	
	//MUTATEURS
	public void setDate(long time){
		date = time;
	}
	
	//METHODES
	/**
	 * Exécuter l'événement
	 * @param simu Le simulateur qui lance l'événement
	 * @throws ExcReservoir Exception levée si l'événement fin d'extinction est lancé et que le réservoire
	 * du robot est vide
	 */
	public abstract void execute(Simulator simu) throws ExcReservoir;
	
}

