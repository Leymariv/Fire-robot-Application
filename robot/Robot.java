package robot;

import carte.*;
import simulator.*;

import java.util.*;
import exception.*;


/**
 * Robot est la classe abstraite représentant n'importe quel robot
 * Un robot est caractérisé par:
 * sa position sur la carte
 * sa vitesse de déplacement
 * sa disponibilité à pouvoir agir suite aux ordres du manager
 * son efficacité à éteindre un incendit
 */
public abstract class Robot {

	//ATTRIBUTS

	/**
	 * Permet de savoir si le robot va eteindre un feu, et si oui, lequel
	 */
	private Point directionActu;

	/**
	 * Memorise le dernier chemin le plus court recherche
	 */
	private CheminPlusCourt cpc;


	/**
	 * Position du robot sur la carte sous forme d'un objet Point
	 * 
	 * @see Robot#getPosition()
	 * @see Robot#setPosition(Point) 
	 * @see Point
	 */
	protected Point position;

	/**
	 * Donne la vitesse du robot en mètre par seconde
	 * 
	 * @see Robot#getVitesse()
	 * @see Robot#setVitesse(int)
	 */
	protected double vitesse;

	/**
	 * Disponibilité du robot à pouvoir réagir aux ordres du manager
	 * 
	 * @see Robot#getDisponibilite()
	 * @see Robot#setDisponibilite(boolean)
	 */
	protected boolean disponibilite;

	/**
	 * Efficacite du robot à pouvoir éteindre un incendit
	 * 1 : faible efficacité
	 * 2 : éfficacité normale
	 * 3 : grande efficacité
	 * 
	 * @see Robot#getEfficacite()
	 * @see Robot#getEfficacite()
	 */
	protected int efficacite;

	//Accesseurs  

	public CheminPlusCourt getCPC(){
		return this.cpc;
	}

	public Point getDirectionActu(){
		return this.directionActu;
	}

	public Point getPosition() {
		return position;
	}

	public double getVitesse() {
		return vitesse;
	}

	public boolean getDisponibilite() {
		return disponibilite;
	}

	public int getEfficacite() {
		return efficacite;
	}


	//Mutateurs
	
	public void setCPC(CheminPlusCourt ch){
		this.cpc=ch;
	}

	public void setDirectionActu(Point pos){
		this.directionActu=pos;
	}

	public void setPosition(Point pos) {
		this.position = pos;
	}

	public void setVitesse(double vit) {
		this.vitesse = vit;
	}

	public void setDisponibilite(boolean disp) {
		this.disponibilite = disp;
	}

	public void setEfficacite (int eff) {
		this.efficacite = eff;
	}

	//METHODES

	/**
	 * Description du robot
	 */
	public abstract String toString();

	/**
	 * Calcul de la pente entre la case d'arrivée et la case de départ
	 * @param dep
	 * 		altitude de la case de départ
	 * @param arr
	 * 		altitude de la case d'arrivée
	 * @param largeur
	 * 		largeur d'une case
	 * @return
	 * 		Le pourcentage de la pente
	 */
	protected double calculPente(int dep, int arr, int largeur) {
		return (((double)arr-(double)dep)*100) / (double)largeur;
	}

	/**
	 * Calcul du temps de déplacement du robot entre deux case contigues
	 * @param depart
	 * 			case de depart du robot (avec tous ces attributs
	 * 			@see Case
	 * @param arrivee
	 * 			case de destination du robot
	 * @param largeur
	 * 			la largeur d'une case du terrain (élément présent dans l'objet TabCase)
	 * 			@see TabCase
	 * @return
	 * 		le temps mis par le robot pour se déplacer (en secondes)
	 */
	public abstract long tempsDeplacement(Case depart, Case arrivee, int largeur) throws TerrainException, PenteException;

	/**
	 * eteindre un feu sur une case adjacente
	 * 
	 * @param arrivee
	 * 			La case où le robot doit éteindre le feu
	 * @return le temps mis par le robot à éteindre le feu
	 */
	public abstract long tmpEteindre(Case arrivee) throws ExcIntensiteNulle, ExcVide;

	/**
	 * Reduit l'intensite des feux, et vide le reservoir si necessaire
	 */
	public abstract void eteindre(Case arrivee);

	/** 
	 * Met a jour le temps pour atteindre la case (k,l) depuis la case (i,j) dans l'algorithme de recherche du chemin le plus court
	 */
	private void checkCase(CheminPlusCourt mapPoids[][],int i,int j, int k, int l,TabCase map,Case arrivee,PriorityQueue<CheminPlusCourt> fileAtt){
		try {
			if (map.getCase(mapPoids[k][l].getPosact()).getIntensite()==0 | map.getCase(mapPoids[k][l].getPosact())==arrivee){
				long poidsTmp=mapPoids[i][j].getTempsrestant()+tempsDeplacement(map.getCase(i, j), map.getCase(k, l), map.getLargeur());
				if (mapPoids[k][l].getTempsrestant()>poidsTmp){
					mapPoids[k][l].setTempsrestant(poidsTmp);
					mapPoids[k][l].setCheminprec(mapPoids[i][j]);
					fileAtt.add(mapPoids[k][l]);
				}
			}
		} catch (TerrainException e) {
			if (map.getCase(mapPoids[k][l].getPosact())==arrivee && mapPoids[k][l].getTempsrestant()>mapPoids[i][j].getTempsrestant()){
				mapPoids[k][l].setTempsrestant(mapPoids[i][j].getTempsrestant());
				mapPoids[k][l].setCheminprec(mapPoids[i][j]);
			}
		}
		catch (PenteException e) {
			;
		}
	}

	/**
	 * Comparateur pour la file d'attente presente dans l'algorithme de recherche du chemin le plus court
	 */
	private class ComparateurCPC implements Comparator<CheminPlusCourt> {
		public int compare(CheminPlusCourt C1, CheminPlusCourt C2) {
			long difference = C1.getTempsrestant() - C2.getTempsrestant();

			if (difference < 0) {
				return -1;
			} else if (difference > 0) {
				return 1;
			}
			return 0;
		}
	}

	/**
	 * Algorithme de recherche du chemin le plus court par Djikstra
	 */
	public CheminPlusCourt plusCourtChemin(Case arrivee, TabCase map){
		//Initialisation des structures de données
		Comparator<CheminPlusCourt> comparator = new ComparateurCPC();
		PriorityQueue<CheminPlusCourt> fileAtt = new PriorityQueue<CheminPlusCourt>(10, comparator);

		CheminPlusCourt mapPoids[][] = new CheminPlusCourt[map.getCol()][map.getLig()];
		for (int i = 0; i < map.getCol(); i++){
			for (int j = 0; j < map.getLig(); j++){
				mapPoids[i][j] = new CheminPlusCourt();
				mapPoids[i][j].setPosact(map.getCase(i,j).getPos());
			}
		}
		mapPoids[this.position.getX()][this.position.getY()].setTempsrestant(0);
		fileAtt.add(mapPoids[this.position.getX()][this.position.getY()]);

		//boucle de propagation de l'algorithme
		while(fileAtt.size()!=0){
			CheminPlusCourt ch=fileAtt.poll();
			int i=ch.getPosact().getX();
			int j=ch.getPosact().getY();
			if (i<map.getCol()-1){
				checkCase(mapPoids,i,j,i+1,j,map,arrivee,fileAtt);
			}

			if (i>0){
				checkCase(mapPoids,i,j,i-1,j,map,arrivee,fileAtt);
			}

			if (j>0){
				checkCase(mapPoids,i,j,i,j-1,map,arrivee,fileAtt);
			}

			if (j<map.getLig()-1){
				checkCase(mapPoids,i,j,i,j+1,map,arrivee,fileAtt);
			}
		}
		CheminPlusCourt courant=mapPoids[arrivee.getPos().getX()][arrivee.getPos().getY()];
		if (courant!=null && courant.getTempsrestant()==Long.MAX_VALUE){
			return null;
		}
		courant.setTempsrestant(0);
		//Rechainage du chemin le plus court dans le bon sens
		while(courant!=mapPoids[this.position.getX()][this.position.getY()]){
			courant.getCheminprec().setCheminsuiv(courant);
			courant.getCheminprec().setPossuiv(courant.getPosact());
			try {
				courant.getCheminprec().setTempsrestant(courant.getTempsrestant()+tempsDeplacement(map.getCase(courant.getCheminprec().getPosact().getX(), courant.getCheminprec().getPosact().getY()),map.getCase(courant.getPosact().getX(), courant.getPosact().getY()) , map.getLargeur()));
			} catch (TerrainException e){
				;
			} catch (PenteException e){
				;
			}
			courant=courant.getCheminprec();
		}
		return this.cpc=mapPoids[this.position.getX()][this.position.getY()];
	}


	/**
	 * Permet d'ajouter les evenements de depart et de fin du deplacement d'un robot
	 */
	public void deplacement(Case depart, Case arrivee, int largeur, Simulator simu) 
	{
		this.cpc=cpc.getCheminsuiv();
		long temps = simu.getCurrentDate();					// on récupère le temps acutel
		long tempsSup = 0;
		Event departC = new EventDepart(temps,this);		// envoie l'event départ au simulateur qui le met dans la file
		this.disponibilite=false;
		simu.addEvent(departC);						// on ajoute l'evenement
		try{
			tempsSup = this.tempsDeplacement(depart, arrivee, largeur);	// on calcul le temps de deplacement
		} catch (TerrainException a){
			System.out.println("TerrainException Levée\n");
		} catch (PenteException b){
			System.out.println("PenteException levée\n");
		}

		Event ArriveC = new EventFinDeplacement(this, temps + tempsSup, arrivee.getPos());	//envoie l'event arrive
		simu.addEvent(ArriveC);
	}

	/**
	 * Permet d'ajouter les evenements necessaire au depart et de la fin d'extinction d'un feu par un robot
	 */
	public void extinction( Case feu,Simulator simu){
		long temps = simu.getCurrentDate();					// on récupère le temps acutel
		long tempsSup = 0;
		Event debutExtinction = new EventDepart (temps,this);
		this.disponibilite=false;
		try{
			tempsSup = this.tmpEteindre(feu);// on calcul le temps d'extinction
		} catch (ExcVide a){
			System.out.println("exception excVide levee");
			return;
		} catch (ExcIntensiteNulle b){
			System.out.println("exception excIntensiteNull levee");
			return;
		}

		Event finExtinction = new EventFinExtinction(this, temps + tempsSup,feu );	//envoie l'event arrive
		simu.addEvent(debutExtinction);
		simu.addEvent(finExtinction);
	}

}

