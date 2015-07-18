package robot;
import exception.*;
import carte.*;

public class RobotRoues extends RobotRes {

	
	/**
	 * Pente maximale en valeur absolue que le robot peut prendre
	 */
	private static final int PENTE = 15;
	
	//Constructeurs

	public RobotRoues(){
		this.vitesse = 13.8888889;
		this.disponibilite = true;
		this.position = new Point();
		this.tmpRemplissage = 300;
		this.tmpVidage = 120/5;
		this.reservoir = 500;
		this.salve =100;
		this.tailleReservoir=500;
		this.efficacite = 3;
	}

	public RobotRoues (Point pos, boolean disp, int res){
		this.vitesse =13.8888889;
		this.disponibilite = disp;
		this.position = pos;
		this.reservoir = res;
		this.tmpRemplissage = 300;
		this.tmpVidage = 120/5;
		this.salve=100;
		this.tailleReservoir=500;
		this.efficacite = 3;
	}


	//METHODES

	/**
	 * Descriptions du robot roues
	 */
	public String toString() {
		String result;
		result = "\nJe suis un Robot à roues\n";
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
	 * Retourne le temps que met le robot roues pour se placer entre deux cases adjacentes
	 */
	public long tempsDeplacement(Case depart, Case arrivee, int largeur) throws TerrainException, PenteException {
		//Verification des restrictions de terrains et de pente
		if (arrivee.getTerrain()==Terrain.LAC || arrivee.getTerrain()==Terrain.RUISSEAU) 
			throw new TerrainException();
		double pente = this.calculPente(depart.getAltitude(), arrivee.getAltitude(), largeur);
		if (Math.abs(pente) > PENTE)
			throw new PenteException();
		//return la vitesse en fonction du terrain et de la pente
		if (arrivee.getTerrain() == Terrain.FORET){  
			return (long)(largeur/4);
		} else if (pente <=0) {
			return (long)(largeur/this.vitesse);
		} else {
			return (long)(largeur/(this.vitesse - 0.277778 * pente));
		}
	}

}





