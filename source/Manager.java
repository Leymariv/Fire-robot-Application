package source;

import java.util.LinkedList;
import java.util.List;

import robot.CheminPlusCourt;
import robot.Robot;
import robot.RobotRes;
import simulator.Simulator;
import carte.Case;
import carte.Point;
import carte.TabCase;
import carte.Terrain;
import exception.ExcIntensiteNulle;
import exception.ExcVide;

/**
 * C'est l'entité qui va reflechir et prendre les decisions lors des simulations
 */
public class Manager {

	private Robot[] listeRobot;
	private TabCase map;
	private Simulator simu;

	/**
	 * Constructeur de la classe Manager
	 */
	public Manager(TabCase m, Simulator s){
		this.map=m;
		this.simu=s;
		this.listeRobot=m.getTabRobot();
	}

	/**
	 * Liste de tous les feux de la map
	 */
	public List<Case> listeFeu() {
		List<Case> listeCase = new LinkedList<Case>();
		for (int i = 0; i < map.getCol(); i++) {
			for (int j = 0; j <  map.getLig(); j++) {
				if (map.getCase(i, j).getIntensite() != 0){
					listeCase.add(map.getCase(i, j));
				}
			}
		}
		return listeCase;
	}

	/**
	 * Permet de savoir si un robot se dirige deja vers la case en feu considere
	 */
	private boolean caseEnCours(Case enfeu){
		for (int i=0; i<listeRobot.length ; i++){
			if (listeRobot[i].getDirectionActu()==enfeu.getPos())
				return true;
		}
		return false;
	}

	/**
	 * Traite tous les robot de la map disponible
	 */
	public void traiteAllRobot(List <Case> casesEnFeu) {
		casesEnFeu=listeFeu();
		for(int i = 0; i < listeRobot.length; i++) {
			if(listeRobot[i].getDisponibilite()){
				traiteRobot(i,casesEnFeu);
			}
		}
	}

	/**
	 * TraiteFeu permet d'ajouter à la file d'evenemnts le deplacement des robots, leur remplissage et l'extinction des feux
	 */
	private void traiteRobot(int i, List<Case> listeenfeu){ 
		try {
			listeRobot[i].tmpEteindre(listeenfeu.get(0));
			Case caseAEteindre=null;
			if ((caseAEteindre=peutRemplirouEteindre(true,listeRobot[i].getPosition()))==null){
				if (listeRobot[i].getCPC()==null){
					long tempsMin=Long.MAX_VALUE;
					Case feuPP=null;
					for (int k=0; k<listeenfeu.size();k++){
						Case casecour=listeenfeu.get(k);
						if (!caseEnCours(casecour)){
							CheminPlusCourt chemin=listeRobot[i].plusCourtChemin(casecour, map);
							if (chemin!=null && chemin.getTempsrestant()<tempsMin){
								tempsMin=chemin.getTempsrestant();
								feuPP=casecour;
								caseAEteindre=casecour;
							}
						}
					}

					if (caseAEteindre!=null){
						listeRobot[i].setDirectionActu(caseAEteindre.getPos());
						listeRobot[i].setCPC(listeRobot[i].plusCourtChemin(feuPP, map));
						listeRobot[i].deplacement(map.getCase(listeRobot[i].getPosition()), map.getCase(listeRobot[i].getCPC().getPosact()), map.getLargeur(), simu);

					}
				} else {
					CheminPlusCourt ch=listeRobot[i].getCPC();
					while(ch.getCheminsuiv()!=null)
						ch=ch.getCheminsuiv();
					if (map.getCase(ch.getPosact()).getIntensite()>0){
					listeRobot[i].setDirectionActu(ch.getPosact());
					listeRobot[i].deplacement(map.getCase(listeRobot[i].getPosition()), map.getCase(listeRobot[i].getCPC().getPosact()), map.getLargeur(), simu);
					} else {
						listeRobot[i].setCPC(null);
						listeRobot[i].setDirectionActu(null);
					}
				}
			} else {
				listeRobot[i].setDirectionActu(caseAEteindre.getPos());
				listeRobot[i].setCPC(null);
				listeRobot[i].extinction(caseAEteindre, simu);
			}

		} catch (ExcIntensiteNulle e) {
			System.out.println("Case Théoriquement impossible");
		} catch (ExcVide e ) {
			//Si le robot a son reservoir vide, il va se remplir

			if (peutRemplirouEteindre(false,listeRobot[i].getPosition())!=null) {
				listeRobot[i].setCPC(null);
				((RobotRes)listeRobot[i]).remplir(simu);
			} else {
				CheminPlusCourt lacPP;
				if (listeRobot[i].getCPC()==null){
					lacPP = lacPlusProche(map,listeRobot[i]);
				} else {
					lacPP=listeRobot[i].getCPC();
				}
				if (lacPP!=null){
					listeRobot[i].deplacement(map.getCase(listeRobot[i].getPosition()), 
							map.getCase(lacPP.getPossuiv()), map.getLargeur(), simu);
				}
			}
		}
	}

	/**
	 * Permet de savoir si la case considere est une case sur laquel le robot peut se remplir
	 */
	private boolean caseEau(TabCase map,int i, int j){
		return (map.getCase(i,j).getTerrain() == Terrain.LAC || map.getCase(i,j).getTerrain() == Terrain.RUISSEAU);
	}

	/**
	 * Fonction qui permet de savoir si le robot est a cote d'un point d'eau ou d'un feu
	 * @param eteindre Permet de savoir s'il faut cherche un point d'eau ou une case en feu
	 */
	private Case peutRemplirouEteindre(Boolean eteindre,Point caseRobot) {

		if (!eteindre && caseRobot.getY()< map.getLig()-1 && caseEau(map,caseRobot.getX(),caseRobot.getY()+1))
			return map.getCase(caseRobot.getX(),caseRobot.getY()+1);
		if (!eteindre && caseRobot.getY()>0 && caseEau(map,caseRobot.getX(),caseRobot.getY()-1))
			return map.getCase(caseRobot.getX(),caseRobot.getY()-1);
		if (!eteindre && caseRobot.getX()>0 && caseEau(map,caseRobot.getX()-1,caseRobot.getY()))
			return map.getCase(caseRobot.getX()-1,caseRobot.getY());
		if (!eteindre && caseRobot.getX()<map.getCol()-1 && caseEau(map,caseRobot.getX()+1,caseRobot.getY()))
			return map.getCase(caseRobot.getX()+1,caseRobot.getY());

		if (eteindre && caseRobot.getY()< map.getLig()-1 && map.getCase(caseRobot.getX(),caseRobot.getY()+1).getIntensite()>0)
			return map.getCase(caseRobot.getX(),caseRobot.getY()+1);
		if (eteindre && caseRobot.getY()>0 && map.getCase(caseRobot.getX(),caseRobot.getY()-1).getIntensite()>0)
			return map.getCase(caseRobot.getX(),caseRobot.getY()-1);
		if (eteindre && caseRobot.getX()>0 && map.getCase(caseRobot.getX()-1,caseRobot.getY()).getIntensite()>0)
			return map.getCase(caseRobot.getX()-1,caseRobot.getY());
		if (eteindre && caseRobot.getX()<map.getCol()-1 && map.getCase(caseRobot.getX()+1,caseRobot.getY()).getIntensite()>0 )
			return map.getCase(caseRobot.getX()+1,caseRobot.getY());

		return null;
	}


	/**
	 * Fonction de recherche du point d'eau le plus proche
	 * @return retourne le chemin le plus court au lac le plus proche
	 */
	private CheminPlusCourt lacPlusProche(TabCase map, Robot rob) {
		Case lacPP = null;
		long distanceLac = Long.MAX_VALUE;
		for (int i = 0; i < map.getCol(); i++) {
			for (int j = 0; j < map.getLig(); j++){
				if (caseEau(map,i,j)) {
					CheminPlusCourt chemintmp=rob.plusCourtChemin(map.getCase(i,j),map);
					if(chemintmp!=null){
						long distanceTmp=chemintmp.getTempsrestant();
						if (distanceTmp < distanceLac){
							distanceLac = distanceTmp;
							lacPP = map.getCase(i, j);
						}
					}
				}	
			}
		}
		return rob.plusCourtChemin(lacPP, map);
	}

}
