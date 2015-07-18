package TestsUnitaires;

import robot.*;
import carte.*;

public class TestCPC
{
	public static void main (String[] args) {

		TabCase map = new TabCase(5);

		map.setLargeur(100);
		Robot[] listerobot=new Robot[4];
		listerobot[0]=new RobotChenille(new Point(1,1),true, 10);
		listerobot[1]=new RobotVolant(new Point(1,1),true, 10);
		listerobot[2]=new RobotRoues(new Point(1,1),true, 10);
		listerobot[3]=new RobotPattes(new Point(1,1),true);

		CheminPlusCourt chemin;
		for (int i=0; i<4; i++){

			for(int k = 0; k < 5; k++)
			{
				for(int j = 0; j < 5; j++)
				{
					map.setCase(new Case(Terrain.FORET,0,0,0,new Point(k,j)));

				}
			}

			System.out.println("*****************************************************");
			System.out.println("Test pour robot " + i);

			System.out.println(" ");
			System.out.println("test robot deja a l'arrivee");
			chemin=listerobot[i].plusCourtChemin(map.getCase(1,1),map);
			affiche(chemin);

			System.out.println(" ");
			System.out.println("test chemin simple");
			chemin=listerobot[i].plusCourtChemin(map.getCase(3,2),map);
			affiche(chemin);


			map.setCase(new Case(Terrain.LAC,0,0,0,new Point(1,2)));
			map.setCase(new Case(Terrain.LAC,0,0,0,new Point(2,1)));
			map.setCase(new Case(Terrain.LAC,0,0,0,new Point(2,2)));
			map.setCase(new Case(Terrain.LAC,0,0,0,new Point(3,1)));
			map.setCase(new Case(Terrain.LAC,0,0,0,new Point(3,2)));
			System.out.println(" ");
			System.out.println("test chemin complique");
			chemin=listerobot[i].plusCourtChemin(map.getCase(3,2),map);
			affiche(chemin);

			map.setCase(new Case(Terrain.LAC,0,0,0,new Point(2,0)));
			map.setCase(new Case(Terrain.LAC,0,0,0,new Point(0,2)));
			System.out.println(" ");
			System.out.println("test pas de chemin possible");
			chemin=listerobot[i].plusCourtChemin(map.getCase(3,2),map);
			affiche(chemin);

		}
	}

	private static void affiche(CheminPlusCourt chemin){
		if (chemin==null)
			System.out.println("pas de chemin existant entre les 2 points considérés");

		while (chemin!=null){
			System.out.print("case actu: " + chemin.getPosact().getX() + chemin.getPosact().getY());
			if (chemin.getPossuiv()!=null)
				System.out.print(" case suiv: " + chemin.getPossuiv().getX() + chemin.getPossuiv().getY());
			System.out.println(" temps : " + chemin.getTempsrestant());
			chemin=chemin.getCheminsuiv();
		}

	}
}