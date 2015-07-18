package source;

import simulator.*;
import carte.*;
import fr.ensimag.simulation.SimulationPlayer;
import InterfaceGraphique.SimuPlayer;

/**
 * Point d'entree du programme
 */
public class Main {

	/**
	 * Fonction principale qui instancie les differents objets necessaire
	 * a la simulation des robots pompiers sur une certaine carte
	 * @param args
	 */
	public static void main(String[] args) {

		Simulator simu= new Simulator();
		String nomMap = "spiralOfMadness-50x50.map";
		TabCase map = new TabCase(System.getProperty("user.dir")+"/map/"+nomMap);
		Manager Manager=new Manager(map,simu);
		SimulationPlayer player = new SimuPlayer(map.getCol(), map.getLig(), 5, map, Manager,simu,false);
		map.setFenetre(((SimuPlayer)player).getFenetre());
		map.AfficheMapGraphique();
	}
}
