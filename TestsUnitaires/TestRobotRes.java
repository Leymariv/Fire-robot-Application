package TestsUnitaires;

import robot.*;
import carte.*;
import exception.*;

public class TestRobotRes {

	private static void test(Robot Bob) throws TerrainException, PenteException, ExcIntensiteNulle, ExcVide{
		Case case1=new Case(Terrain.PATURAGE, 0, 0, 0, new Point(0,0));
		Case case2=new Case(Terrain.PATURAGE, 0, 0, 0, new Point(0,1));
		
		System.out.println(Bob.toString());
		System.out.println("test avec des terrains à meme hauteur");
		System.out.println("temps de deplacement : " + Bob.tempsDeplacement(case1, case2, 100));

		case1.setAltitude(10);
		System.out.println("test avec des terrains en descente");
		System.out.println("temps de deplacement : " + Bob.tempsDeplacement(case1, case2, 100));

		case1.setAltitude(0);
		case2.setAltitude(10);
		System.out.println("test avec des terrains en montée correct");
		System.out.println("temps de deplacement : " + Bob.tempsDeplacement(case1, case2, 100));

		case2.setAltitude(25);
		try{
			System.out.println("test avec des terrains en montée trop élevé");
			System.out.println("temps de deplacement : " + Bob.tempsDeplacement(case1, case2, 100));
		} catch (PenteException e){
			System.out.println("Montée trop élevé");
		}

		case2.setAltitude(0);
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

		case1.setTerrain(Terrain.FORET);
		case2.setTerrain(Terrain.FORET);
		System.out.println("test en foret");
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

	public static void main (String[] args) throws TerrainException, PenteException, ExcIntensiteNulle, ExcVide {
		
		Robot Bob=new RobotChenille(new Point(0,0),true,100);
		System.out.println("\n***************************\nTEST ROBOT CHENILLES");
		test(Bob);

		Robot Bob2=new RobotRoues(new Point(0,0),true,500);
		System.out.println("\n***************************\nTEST ROBOT ROUES");
		test(Bob2);

		Robot Bob3=new RobotVolant(new Point(0,0),true,400);
		System.out.println("\n***************************\nTEST ROBOT VOLANT");
		test(Bob3);
	}
}
