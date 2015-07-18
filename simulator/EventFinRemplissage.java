package simulator;

import robot.*;
import exception.*;

public class EventFinRemplissage extends Event{

	//constructeur
	/**
	 * Constructeur de la classe
	 */
	public EventFinRemplissage(RobotRes r, long Date) {
		this.date = Date; 
		this.robotCour = r;
	}
	
	// Méthode
	/**
	 * Remplit le reservoir du robot, puis le remet dans un etat disponible
	 */
	public void execute(Simulator simu) throws ExcReservoir {
		try {
			((RobotRes)this.robotCour).setReservoir(((RobotRes)this.robotCour).getTaillereservoir());
		} catch (ExcReservoir e) {
			e.printStackTrace();
		}
		this.robotCour.setDisponibilite(true);
	}

}
