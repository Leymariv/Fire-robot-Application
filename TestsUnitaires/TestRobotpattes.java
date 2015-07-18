package TestsUnitaires;

import exception.*;
import robot.*;
import carte.*;

/**
 * Cellule de test unitaire de l'objet Robot_pattes
 */

public class TestRobotpattes 
{
	public static void main (String[] args) throws TerrainException, PenteException, ExcIntensiteNulle, ExcVide
	{
		System.out.println("****************************\nTEST ROBOT PATTES \n********************************");
		RobotPattes Bob= new RobotPattes(new Point(0,0),true);
		System.out.println(Bob.toString());

		Case case1=new Case(Terrain.PATURAGE, 0, 0, 0, new Point(0,0));
		Case case2=new Case(Terrain.PATURAGE, 0, 0, 0, new Point(0,1));
		System.out.println("test avec des terrains à meme hauteur");
		System.out.println("temps de deplacement : " + Bob.tempsDeplacement(case1, case2, 100));
		
		case2.setTerrain(Terrain.LAC);
		try {
			System.out.println("test pour lac");
			System.out.println("temps de deplacement : " + Bob.tempsDeplacement(case1, case2, 100));
		} catch (TerrainException e){
			System.out.println("Peu pas se depalcer sur les lacs");
		}
		case2.setTerrain(Terrain.HUMIDE);
		try {
			System.out.println("test pour les terrains humides");
			System.out.println("temps de deplacement : " + Bob.tempsDeplacement(case1, case2, 100));
		} catch (TerrainException e){
			System.out.println("Peu pas se depalcer sur les terrains humides");
		}
		case2.setTerrain(Terrain.RUISSEAU);
		try {
			System.out.println("test pour les ruisseau");
			System.out.println("temps de deplacement : " + Bob.tempsDeplacement(case1, case2, 100));
		} catch (TerrainException e){
			System.out.println("Peu pas se depalcer sur les ruisseau");
		}

		case2.setAltitude(5);
		System.out.println("test avec des terrains à hauteur différente");
		System.out.println("temps de deplacement : " + Bob.tempsDeplacement(case1, case2, 100));

		try {
			System.out.println("test extinction d'un terrain pas en feu");
			System.out.println("temps de deplacement : " + Bob.tmpEteindre(case2));
		} catch (ExcIntensiteNulle e){
			System.out.println("Intensité null pour ce terrain");
		}

		case2.setIntensite(4);
		System.out.println("test extinction d'un terrain feu");
		System.out.println("temps d'extinction : " + Bob.tmpEteindre(case2));

	}
}

