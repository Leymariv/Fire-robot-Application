package simulator;

import robot.*;
import carte.*;

public class EventFinExtinction extends Event{

	/**
	 * Case a eteindre
	 */
	private Case pFeu;

	/**
	 * Constructeur de la classe
	 */
	public EventFinExtinction(Robot robotC, long dateC, Case enFeu) {
		this.robotCour = robotC;
		this.date = dateC;
		pFeu = enFeu;
	}

	/**
	 * Eteint le feu demander, et remet le robot dans un etat disponible
	 */
	public void execute (Simulator simu) {
		this.robotCour.setDisponibilite(true);
		this.robotCour.eteindre(pFeu);
		this.robotCour.setDirectionActu(null);
	}
}



