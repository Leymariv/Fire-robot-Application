package carte;

/**
 * Classe qui definit les cases de la carte pour les simulations
 */
public class Case {
	/**
	 * Nature du terrain de la case
	 */
	private Terrain nature;
	/**
	 * Altitude du terrain de la case
	 */
	private int altitude;
	/**
	 * Etat du feu présent s'il y a sur la case, 0 par défault s'il n'y a aucun feu
	 */
	private int etat;
	/*
	 * 0: normal
	 * 1: flammes
	 * 2: brulé
	 */
	/**
	 * Intensite du feu sur la case, 0 si aucun feu
	 */
	private int intensite;//0..5
	/**
	 * Position de la case sur la map
	 */
	private Point pos;


	/**
	 * Constructeur par défault de l'objet Case
	 */
	public Case ()
	{
		nature = Terrain.BLACK;
		altitude = 0;
		etat = 0;
		intensite = 0;
		pos = new Point(0,0);
	}

	/**
	 * Constructeurs de la classe case
	 */
	public Case (Terrain natureC, int altitudeC, int etatC, int intensiteC, Point posC) {
		nature = natureC;
		altitude = altitudeC;
		etat = (etatC < 0 || etatC > 2) ? -1 : etatC;
		intensite = (intensiteC < 0 || intensiteC > 5) ? 0 : intensiteC;
		pos = posC;
	}


	/*
	 * ACCESSEURS
	 */
	public Terrain getTerrain() {
		return nature;
	}

	public int getAltitude() {
		return altitude;
	}

	public int getEtat() {
		return etat;
	}

	public int getIntensite() {
		return intensite;
	}

	public Point getPos() {
		return pos;
	}

	/*
	 * MUTATEURS
	 */
	 public void setTerrain(Terrain n){
		 this.nature = n;
	 }

	 public void setAltitude(int a){
		 this.altitude = a;
	 }

	 public void setEtat(int e){
		 this.etat = (e < 0 || e > 2) ? -1 : e;
	 }
	 
	 public void setEtat(){
		 if (this.intensite>0){
			 this.etat = 1;
		 } else{
			 this.intensite = 0;
		 }
		 
	 }
	 
	 public void setIntensite(int i){
		 this.intensite = (i < 0 || i > 5) ? -1 : i;
	 }

	 public void setPos(Point p){
		 this.pos = p;
	 }

}