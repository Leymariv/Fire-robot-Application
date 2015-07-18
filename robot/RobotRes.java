package robot;
import exception.*;
import carte.*;
import simulator.*;

public abstract class RobotRes extends Robot {
	//declaration des attributs
	protected int tmpRemplissage;
	protected int tmpVidage;
	protected int tailleReservoir;
	protected int reservoir;
	protected int salve; //salve: quantité d'eau utilisé lors d'UNE extinction


	//Mutateurs

	public void setReservoir (int res) throws ExcReservoir {
		if (res > this.tailleReservoir) throw new ExcReservoir();
		else this.reservoir = res;
	}

	//Accesseurs  
	public int getTmpremplissage() {
		return tmpRemplissage;
	}

	public int getTmpvidage() {
		return tmpVidage;
	}

	public int getReservoir() {
		return reservoir;
	}

	public int getTaillereservoir() {
		return tailleReservoir;
	}
	//Méthodes

	/**
	 * Ajoute les evenements de debut et de fin de remplissage dans le simulateur
	 */
	public void remplir (Simulator simu) {
		this.disponibilite=false;
		Event deb_remp = new EventDepart (simu.getCurrentDate(), this);
		Event fin_remp = new EventFinRemplissage(this, simu.getCurrentDate()+this.tmpRemplissage );

		simu.addEvent(deb_remp);
		simu.addEvent(fin_remp);
	}

	/**
	 *On retranche à l'intensité du feu l'efficacité du Robot
	 *@see Robot#eteindre(Case)
	 */
	public long tmpEteindre(Case arrivee) throws ExcIntensiteNulle, ExcVide {  //retourne le temps d'extinction
		if (reservoir == 0) 
			throw new ExcVide();		
		if (arrivee.getIntensite() == 0) 
			throw new ExcIntensiteNulle();		//Si le réservoir est vide, biensûr on lève une exception

		return this.tmpVidage;
	}


	/** 
	 * envoi un salve sur le feu
	 * 
	 * @see Robot#eteindre(carte.Case)
	 */
	public void eteindre(Case arrivee)  {
		if (this.reservoir < this.salve) {						
			arrivee.setIntensite(arrivee.getIntensite() - 1);		
			this.reservoir = 0;						
		} else {								
			if (arrivee.getIntensite() - this.efficacite > 0) {		
				arrivee.setIntensite(arrivee.getIntensite() - this.efficacite);
			}
			else {	
				arrivee.setIntensite(0);
				arrivee.setEtat(2);
			}

			this.reservoir = this.reservoir - this.salve;

		}
	}
}


