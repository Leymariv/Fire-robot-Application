package robot;
import exception.*;
import carte.*;

public class RobotVolant extends RobotRes{

	//Constructeurs

	public RobotVolant(){
		this.disponibilite = true;
		this.vitesse = 22.22;
		this.position = new Point();
		this.reservoir = 400;
		this.tmpRemplissage = 60;
		this.tmpVidage = 60/4;
		this.salve = 100;
		this.tailleReservoir=400;
		this.efficacite = 3;

	}

	public RobotVolant (Point pos, boolean disp, int res){
		this.disponibilite = disp;
		this.vitesse = 22.22;
		this.position = pos;
		this.reservoir = res;
		this.tmpRemplissage = 60;
		this.tmpVidage = 60/4;
		this.salve = 100;
		this.tailleReservoir=400;
		this.efficacite = 3;

	}

	//Méthodes

	/**
	 * Description du robot
	 */
	public String toString() {
		String result;
		result = "\nJe suis un Robot volant\n";
		result += "Ma position est ( " + this.position.getX() + " , " + this.position.getY() + " )\n";
		result += "Ma vitesse est de " + this.vitesse + " m/s\n";
		result += "Mon réservoir vaut " + this.reservoir +"\n";
		/* Condition de test pour la disponibilité */
		if (this.disponibilite) 
			result += "Je suis actuellment disponible\n";
		else
			result += "Je suis actuellment indisponible\n";
		return result;
	}

	/**
	 * @return le temps de deplacement entre deux cases adjacentes du robot volant
	 */
	public long tempsDeplacement(Case depart, Case arrivee, int largeur) throws TerrainException, PenteException {
		return (long)(largeur/this.vitesse);
	}
}
