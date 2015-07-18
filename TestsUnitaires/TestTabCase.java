package TestsUnitaires;

import carte.*;

import fr.ensimag.simulation.SimulationPlayer;
import InterfaceGraphique.SimuPlayer;

public class TestTabCase {
	public static void main (String[] args){
		TabCase map = new TabCase("/home/jodin/Documents/apoo/map/desertOfDeath-20x20.map");
		SimulationPlayer player = new SimuPlayer(map.getCol(), map.getLig(), 5, map, null,null,true);
		map.setFenetre(((SimuPlayer)player).getFenetre());
		map.AfficheMapGraphique();
		try{
			for(int i=0;i<5;i++){
				Thread.sleep(1000);
				map.getTabRobot()[0].setPosition(new Point(map.getTabRobot()[0].getPosition().getX(),map.getTabRobot()[0].getPosition().getY()+1));
			}
			for (int i=0; i<5; i++){
				Thread.sleep(1000);
				map.getCase(0, 9).setIntensite(map.getCase(0,9).getIntensite()-1);
			}	

		}
		catch (InterruptedException ie){
			;

		}
	}
}
