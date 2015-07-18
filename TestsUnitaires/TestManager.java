package TestsUnitaires;

import source.*;
import carte.*;
import robot.*;
import simulator.*;
import java.util.*;

public class TestManager
{
	public static void main (String[] args) {
		TabCase map = new TabCase(5);
		map.setLargeur(100);
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				map.setCase(new Case(Terrain.PATURAGE,0,0,0,new Point(i,j)));
			}
		}

		Manager man1=new Manager(map,null);
		map.setCase(new Case(Terrain.FORET,0,0,3,new Point(0,0)));
		map.setCase(new Case(Terrain.FORET,0,0,3,new Point(3,2)));
		map.setCase(new Case(Terrain.FORET,0,0,3,new Point(2,4)));
		map.setCase(new Case(Terrain.LAC,0,0,0,new Point(4,3)));
		map.setCase(new Case(Terrain.LAC,0,0,0,new Point(3,4)));
		map.setCase(new Case(Terrain.LAC,0,0,0,new Point(3,0)));
		map.setCase(new Case(Terrain.LAC,0,0,0,new Point(4,1)));
		map.setCase(new Case(Terrain.LAC,0,0,0,new Point(0,3)));
		map.setCase(new Case(Terrain.LAC,0,0,0,new Point(1,4)));
		System.out.println("******************************************************");
		System.out.println("test de la fonction de recherche des cases en feu");
		List<Case> listefeu=man1.listeFeu();
		for (int i=0; i<listefeu.size();i++){
			System.out.println(listefeu.get(i).getPos().getX() + "  " + listefeu.get(i).getPos().getY());
		}

		Robot[] listerobot=new Robot[3];
		listerobot[0]= new RobotChenille(new Point(4,4),true, 10);
		listerobot[1]= new RobotPattes(new Point(4,0),true);
		listerobot[2]= new RobotRoues(new Point(0,4),true, 10);
		map.setTabRobot(listerobot);
		Simulator simu= new Simulator();
		Manager man2=new Manager(map,simu);

		System.out.println(" ");
		System.out.println("******************************************************");
		System.out.println("test de la fonction de traiteAllRobot avec aucun feu");

		man2.traiteAllRobot(null);
		while(simu.hasNext()) {
			Event e;
			e=simu.nextStep();
			System.out.println(e.getRobot().toString());
		}


		listerobot[0].setDisponibilite(false);
		listerobot[1].setDisponibilite(false);
		listerobot[2].setDisponibilite(false);
		Simulator simu2= new Simulator();
		Manager man3=new Manager(map,simu2);

		System.out.println(" ");
		System.out.println("******************************************************");
		System.out.println("test de la fonction de traiteAllRobot avec aucun robot disponible");
		man3.traiteAllRobot(man3.listeFeu());
		
		while(simu2.hasNext()) {
			Event e;
			e=simu2.nextStep();
			System.out.println(e.getRobot().toString());
		}

		listerobot[0].setDisponibilite(true);
		listerobot[1].setDisponibilite(true);
		listerobot[2].setDisponibilite(true);
		map.setCase(new Case(Terrain.PATURAGE,0,0,0,new Point(1,4)));
		Simulator simu3= new Simulator();
		Manager man4=new Manager(map,simu3);

		System.out.println(" ");
		System.out.println("******************************************************");
		System.out.println("test de la fonction de traiteAllRobot avec un seul robot pouvant acceder au feu");
		man4.traiteAllRobot(man4.listeFeu());

		while(simu3.hasNext()) {
			Event e;
			e=simu3.nextStep();
			System.out.println(e.getRobot().toString());
		}
		
		
		map.setCase(new Case(Terrain.PATURAGE,0,0,0,new Point(3,4)));
		map.setCase(new Case(Terrain.PATURAGE,0,0,0,new Point(4,1)));
		Simulator simu4= new Simulator();
		Manager man5=new Manager(map,simu4);

		System.out.println(" ");
		System.out.println("******************************************************");
		System.out.println("test de la fonction de traiteAllRobot avec tous les robots");
		man5.traiteAllRobot(man5.listeFeu());

		while(simu4.hasNext()) {
			Event e;
			e=simu4.nextStep();
			System.out.println(e.getRobot().toString());
		}
	}

}