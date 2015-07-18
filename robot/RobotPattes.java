package robot;
import exception.*;
import carte.*;

public class RobotPattes extends Robot {

	/**
	 * Pente maximale en valeur absolue que le robot peut prendre
	 */
	private static final int PENTE = 5;


	/**
	 * Constructeur par défault de l'objet RobotPattes
	 */
	public RobotPattes() {			
		this.position = new Point();
		this.vitesse = 1.38;
		this.disponibilite = true;
		this.efficacite = 1;
	}

	/**
	 * Constructeur principal de l'objet RobotPattes
	 * 		Efficacité du robot à pattes mise à un  
	 * 
	 * @param pos 
	 * 		la position du robot à actualiser pour l'attribut position
	 * @param disp
	 * 		la disponibilité du robot  
	 */
	public RobotPattes(Point pos, boolean disp) {
		this.position = pos;
		this.vitesse = 1.38;
		this.disponibilite = disp;
		this.efficacite = 1;

	}

	/*
	 * Méthodes
	 */

	/**
	 * Description du robot à pattes et de ces caractériqtiques 
	 * principales
	 * @see Robot#toString()
	 */
	public String toString() {
		String result;
		result = "\nJe suis un Robot à pattes\n";
		result += "Ma position est ( " + this.position.getX() + " , " + this.position.getY() + " )\n";
		result += "Ma vitesse est de " + this.vitesse + " m/s\n";
		/* Condition de test pour la disponibilité */
		if (this.disponibilite) 
			result += "Je suis actuellment disponible\n";
		else
			result += "Je suis actuellment indisponible\n";
		return result;
	}


	/**
	 * @see Robot#tempsDeplacement(Case, Case, int)
	 */
	public long tempsDeplacement(Case depart, Case arrivee, int largeur) throws TerrainException, PenteException {
		//Verification du type de terrain et de la pente
		if (arrivee.getTerrain() == Terrain.LAC)
			throw new TerrainException();
		double pente = this.calculPente(depart.getAltitude(), arrivee.getAltitude(), largeur);
		if (Math.abs(pente) > PENTE)
			throw new PenteException();
		//renvoi la valeur de vitesse
		return (long)(largeur / this.vitesse);
	}

	/**
	 * renvoi le temps que met le robot a pattes pour envoyer une salve
	 * @see Robot#eteindre(Case)
	 */
	public long tmpEteindre(Case arrivee) throws ExcIntensiteNulle, ExcVide {
		if (arrivee.getIntensite() == 0)
			throw new ExcIntensiteNulle();
		return (long)(20);
	}

	/** 
	 * envoi un salve sur le feu
	 * 
	 * @see Robot#eteindre(carte.Case)
	 */
	public void eteindre(Case arrivee) {	
		if (arrivee.getIntensite() - this.efficacite > 0) {				
			arrivee.setIntensite(arrivee.getIntensite() - this.efficacite);
		}
		else {
			arrivee.setIntensite(0);
			arrivee.setEtat(2); //mise a jour de l'état de la case
		}	
	}
}


