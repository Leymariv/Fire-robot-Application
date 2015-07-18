package robot;

import carte.*;

/**
 * Structure permettant de construire un chemin entre deux points
 */
public class CheminPlusCourt{
	private long temps_restant;
	private Point position_suivante, position_actuel;
	private CheminPlusCourt suivant,precedent;
	
	public CheminPlusCourt(){
		this.temps_restant=Long.MAX_VALUE;
		this.position_suivante= null;
		this.position_actuel= null;
		this.suivant = null;
		this.precedent= null;
	}
	
	public CheminPlusCourt(long temps, Point pos_s, Point pos_a, CheminPlusCourt suiv, CheminPlusCourt prec){
		this.temps_restant=temps;
		this.position_suivante= pos_s;
		this.position_actuel= pos_a;
		this.suivant = suiv;
		this.precedent= prec;
	}

	public void setTempsrestant(long temps){
		this.temps_restant=temps;
	}

	public void setPossuiv(Point poss){
		this.position_suivante =poss;
	}

	public void setPosact(Point posa){
		this.position_actuel =posa;
	}

	public void setCheminsuiv(CheminPlusCourt suiv){
		this.suivant = suiv;
	}

	public void setCheminprec(CheminPlusCourt prec){
		this.precedent = prec;
	}

	public long getTempsrestant(){
		return this.temps_restant;
	}
	public Point getPossuiv(){
		return this.position_suivante;
	}
	public Point getPosact(){
		return this.position_actuel;
	}
	public CheminPlusCourt getCheminsuiv(){
		return this.suivant;
	}
	public CheminPlusCourt getCheminprec(){
		return this.precedent;
	}
}

