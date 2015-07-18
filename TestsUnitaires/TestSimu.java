package TestsUnitaires;

import InterfaceGraphique.SimuPlayer;
import simulator.*;
import carte.*;
import robot.*;
import fr.ensimag.simulation.SimulationPlayer;


public class TestSimu {



	public static void main(String[] args) 
	{
		TabCase map = new TabCase("/home/jodin/Documents/apoo/map/desertOfDeath-20x20.map");

		SimulationPlayer player = new SimuPlayer(map.getCol(), map.getLig(), 5, map, null,null,true);
		map.setFenetre(((SimuPlayer)player).getFenetre());
		map.AfficheMapGraphique();

		Simulator simu = new Simulator();
		Robot cour=map.getTabRobot()[5];
		try{
			Thread.sleep(1000);
			cour.deplacement(map.getCase(cour.getPosition()), map.getCase(cour.getPosition().getX()-1, cour.getPosition().getY()), map.getLargeur(), simu);
			runSimulation(simu);
			Thread.sleep(1000);
			cour.deplacement(map.getCase(cour.getPosition()), map.getCase(cour.getPosition().getX()-1, cour.getPosition().getY()), map.getLargeur(), simu);
			runSimulation(simu);
			Thread.sleep(1000);
			cour.deplacement(map.getCase(cour.getPosition()), map.getCase(cour.getPosition().getX()-1, cour.getPosition().getY()), map.getLargeur(), simu);
			runSimulation(simu);
			Thread.sleep(1000);
			cour.deplacement(map.getCase(cour.getPosition()), map.getCase(cour.getPosition().getX()-1, cour.getPosition().getY()), map.getLargeur(), simu);
			runSimulation(simu);
			
			Thread.sleep(1000);
			cour.extinction(map.getCase(4, 9), simu);
			runSimulation(simu);
			Thread.sleep(1000);
			cour.extinction(map.getCase(4, 9), simu);
			runSimulation(simu);
			
			Thread.sleep(1000);
			cour.deplacement(map.getCase(cour.getPosition()), map.getCase(cour.getPosition().getX()-1, cour.getPosition().getY()), map.getLargeur(), simu);
			runSimulation(simu);
			Thread.sleep(1000);
			cour.extinction(map.getCase(3, 9), simu);
			runSimulation(simu);
			Thread.sleep(1000);
			cour.extinction(map.getCase(3, 9), simu);
			runSimulation(simu);
			
			Thread.sleep(1000);
			cour.setPosition(new Point(0,18));
			((RobotRes)cour).remplir(simu);
			runSimulation(simu);			
			
			Thread.sleep(1000);
			cour.setPosition(new Point(0,8));
			Thread.sleep(1000);
			cour.extinction(map.getCase(0, 9), simu);
			runSimulation(simu);
		}
		catch (InterruptedException ie){
			;

		}
		System.out.println("FIN TEST SIMU");
	}


	public static void runSimulation ( Simulator simulator ) {
		while ( simulator.hasNext() ) {
			simulator.nextStep();
		}
	}

}
