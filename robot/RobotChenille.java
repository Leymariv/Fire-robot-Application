package robot;
import exception.*;
import carte.*;

public class RobotChenille extends RobotRes {

	/**
	 * Pente maximale en valeur absolue que le robot peut prendre
	 */
	private static final int PENTE = 20;
	
	//constructeurs

	public RobotChenille() {
		this.vitesse = 5.55; // Vitesse en m.s-1
		this.disponibilite = false;
		this.position = new Point();
		this.tmpRemplissage = 60;
		this.tmpVidage = 120/4;
		this.reservoir = 100;
		this.salve = 25;
		this.tailleReservoir=100;
		this.efficacite=2;
	}

	public RobotChenille (Point pos, boolean disp, int res) {
		this.position = pos;
		this.vitesse = 5.55;
		this.disponibilite = disp;
		this.reservoir = res;
		this.tmpVidage = 120/4;
		this.tmpRemplissage = 60;
		this.salve = 25;
		this.tailleReservoir=100;
		this.efficacite=2;
	}


	//Méthodes

	/**
	 *Description du robot à chenille
	 */
	public String toString() {
		String result;
		result = "\nJe suis un Robot à chenille\n";
		result += "Ma position est ( " + this.position.getX() + " , " + this.position.getY() + " )\n";
		result += "Ma vitesse est de " + this.vitesse + " m/min\n";
		result += "Mon réservoir vaut " + this.reservoir +"\n";
		/* Condition de test pour la disponibilité */
		if (this.disponibilite) 
			result += "Je suis actuellment disponible\n";
		else
			result += "Je suis actuellment indisponible\n";
		return result;

	}

	/**
	 * Retourne le temps que met le robot chenille pour se placer entre deux cases adjacentes
	 */
	public long tempsDeplacement(Case depart, Case arrivee, int largeur) throws TerrainException, PenteException {
		//Verification des restrictions de terrains et de pente
		if (arrivee.getTerrain()==Terrain.LAC || arrivee.getTerrain()==Terrain.RUISSEAU || arrivee.getTerrain()==Terrain.HUMIDE) 
			throw new TerrainException();
		double pente = this.calculPente(depart.getAltitude(), arrivee.getAltitude(), largeur);
		if (Math.abs(pente) > PENTE)
			throw new PenteException();
		//return la vitesse en fonction du terrain et de la pente
		if (arrivee.getTerrain() == Terrain.FORET){  
			return (long)(largeur/1.3888888889);
		} else if (pente <=0) {
			return (long)(largeur/this.vitesse);
		} else {
			return (long)(largeur/(this.vitesse - 0.277778 * pente));
		}
	}
}


