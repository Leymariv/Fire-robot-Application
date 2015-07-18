package simulator;

import robot.*;
import carte.*;

/**
 * Classe Evénement d'annonce de fin de déplacement
 * Héritage de la classe Event
 * @see Event
 */
public class EventFinDeplacement extends Event{
		/**
		 * Case d'arrivée suite au déplacement du Robot
		 */
		private Point arrivee;
		
		/**
		 * Constructeur de la classe EventFinDeplacement
		 * @param robotC Le Robot qui effectue l'événement
		 * @param dateC La date d'éxécution de l'événement
		 * @param arr La case d'arrivée du Robot suite au déplacement
		 */
		public EventFinDeplacement(Robot robotC, long dateC, Point arr) {
			this.robotCour = robotC;
			this.date = dateC;
			arrivee = arr;
		}

		/**
		 * Méthode d'éxécution de l'événement
		 * Mise en disponibilité du Robot
		 * Mise à jour de la position du Robot
		 */
		public void execute (Simulator simu) {
			this.robotCour.setDisponibilite(true);
			this.robotCour.setPosition(arrivee);
			this.robotCour.setDirectionActu(null);
			}
	
}
