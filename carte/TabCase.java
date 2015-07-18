package carte;
import java.util.*;

import exception.*;
import robot.*;
import java.util.regex.MatchResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;
import fr.ensimag.gui.Display;
import fr.ensimag.gui.MapIndexOutOfBoundsException;
import fr.ensimag.gui.Oval;
import fr.ensimag.gui.Text;

/**
 * Gère une carte
 */
public class TabCase {
	/**
	 * tableaux de case représentant la carte
	 */
	private  Case map[][];
	/**
	 * liste des robots présents sur la carte
	 */
	private  Robot TabRobot[];
	/**
	 * largeur d'une case de la carte
	 */
	private int largeur;
	/**
	 * nombre de ligne, de colonnes et de robots sur la carte
	 */
	private int lig, col, nb_robots;
	/**
	 * fenêtre d'affichage de la carte
	 */
	private Display fenetre;

	/**
	 * 
	 * Cette fonction associe la chaine de caractère lue 
	 * dans le fichier map à l'énumération des types de terrains.
	 * @param cas la case en question
	 * @param str la chaine de caractère lue sur le fichier texte
	 * @throws ExcFichierTexte
	 */
	private void gereTerrain(Case cas, String str) throws ExcFichierTexte{
		if (str.equals("TERRE_ARABLE")){
			cas.setTerrain(Terrain.ARABLE);
		}
		else if (str.equals("ROCHE_NUE")){
			cas.setTerrain(Terrain.ROCHE);
		}
		else if (str.equals("LAC")){
			cas.setTerrain(Terrain.LAC);
		}
		else if (str.equals("COURS_D_EAU")){
			cas.setTerrain(Terrain.RUISSEAU);
		}
		else if (str.equals("FORET")){
			cas.setTerrain(Terrain.FORET);
		}
		else if (str.equals("CULTURE_PERMANENTE")){
			cas.setTerrain(Terrain.CULTURE);
		}
		else if (str.equals("PATURAGE")){
			cas.setTerrain(Terrain.PATURAGE);
		}
		else if (str.equals("TERRE_AGRICOLE_HETEROGENE")){
			cas.setTerrain(Terrain.AGRICOLE);
		}
		else if (str.equals("BROUSSAILLES")){
			cas.setTerrain(Terrain.BROUSSAILLES);
		}
		else if (str.equals("TERRE_HUMIDE")){
			cas.setTerrain(Terrain.HUMIDE);
		}
		else{
			cas.setTerrain(Terrain.BLACK);
			throw new ExcFichierTexte();
		}
	}

	/**
	 * 
	 * Cette fonction instancie les robots à partir des noms des robots lus dans le fichier
	 * @param i ordonnée du robot
	 * @param j abscisse du robot
	 * @param str chaine de caractère lue sur le fichier texte
	 * @return le robot instancié
	 * @throws ExcFichierTexte
	 */ 
	private Robot placeRobots(int i, int j, String str) throws ExcFichierTexte{
		if (str.equals("DRONE")){
			return new RobotVolant(new Point(i, j), true, 400);
		}
		if (str.equals("CHENILLES")){
			return new RobotChenille(new Point(i, j), true, 100 );

		}
		if (str.equals("ROUES")){
			return new RobotRoues(new Point(i, j), true, 500);

		}
		if (str.equals("PATTES")){
			return new RobotPattes(new Point(i, j), true);
		}
		throw new ExcFichierTexte();

	}

	/**
	 * Constructeur par défaut
	 * @param n nombre de lignes et colonnes (carte carrée)
	 */
	public TabCase(int n) {
		lig=n;
		col=n;
		map = new Case[n][n];
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				map[i][j] = new Case();
			}
		}
	}

	/**
	 * Fonction principale de lecture du fichier map.
	 * On lit chaque ligne du fichier à l'aide d'une boucle. On parse chaque ligne pour savoir à quoi
	 * celle-ci correspond (ie utilisation de regex cf plus loin)
	 * @param str adresse du fichier texte contenant la carte
	 */
	public TabCase(String str){

		int i, j, nbIncendies = 0;
		/*
		 * Lors de la lecture la map, une ligne de 3 entiers peut correspondre
		 * aux caractéristiques de celle-ci (lig, col, taille) ou bien à 
		 * celles des incendies (lig, col, intensités).
		 * Ces deux booleans nous permettent de se rappeler ce que l'on a déjà traité.
		 */
		boolean casesLues = false, robotsLus = false; 
		String nature;
		Scanner scan = null;
		MatchResult resultat;


		try {
			scan = new Scanner (new File(str));
			scan.useDelimiter("\n");

			// On parcourt le fichier ligne par ligne à l'aide d'une boucle
			while (scan.hasNext()){
				// On parse la ligne suivante pour déterminer ce qu'on lit dans le fichier...

				//... Si c'est un commentaire on le saute en passant directement à la ligne suivante.
				if (scan.hasNext("(#.*)")){
					scan.nextLine();
				}
				/* 
				 * Si on lit 3 entiers mais que les caractéristiques de la map n'ont pas encore
				 * été lues, cela signifie qu'on lit les caractéristiques générales de la map. 
				 */
				else if (scan.hasNext("(\\d+) (\\d+) (\\d+)") && !casesLues){
					resultat = scan.match();;

					lig = Integer.parseInt(resultat.group(1));
					col = Integer.parseInt(resultat.group(2));
					largeur = Integer.parseInt(resultat.group(3));
					map = new Case[lig][col];
					casesLues = true;
					scan.nextLine();
				}
				/*
				 * Si on ne lit qu'un entier et que le nombre de robots n'a pas encore été lu,
				 * on lit le nombre de robots.
				 */
				else if (scan.hasNext("(\\d+)") && robotsLus){
					resultat = scan.match();	
					nbIncendies =  Integer.parseInt(resultat.group(1));
					scan.nextLine();
				}
				/*
				 * si on lit trois entier et que les nombres de lignes et colonnes a déjà été lu
				 * c'est qu'on lit la position et l'intensité des feux
				 */
				else if (scan.hasNext("(\\d+) (\\d+) (\\d+)")&& casesLues){

					for (i=0;i<nbIncendies;i++){
						while (!scan.hasNext("(\\d+) (\\d+) (\\d+)")){
							scan.nextLine();
						}
						resultat = scan.match();
						map[Integer.parseInt(resultat.group(1))][Integer.parseInt(resultat.group(2))].setIntensite (Integer.parseInt(resultat.group(3)));
						map[Integer.parseInt(resultat.group(1))][Integer.parseInt(resultat.group(2))].setEtat();
						scan.nextLine();
					}
				}
				/*
				 * si on lit un seul entier et que le nombre de robots n'a pas été lu c'est que c'est ça
				 */
				else if (scan.hasNext("(\\d+)") && !robotsLus){
					resultat = scan.match();
					nb_robots =  Integer.parseInt(resultat.group(1));
					TabRobot = new Robot[nb_robots];
					robotsLus = true;
					scan.nextLine();
				}
				/*
				 * si on li une chaine de caractère suivit d'un entier, c'est la nature et l'altitude d'une case
				 */
				else if (scan.hasNext("(\\w+) (\\d+)")){

					for (i=0;i<lig;i++){

						for(j=0;j<col;j++){

							while (!scan.hasNext("(\\w+) (\\d+)")){
								scan.nextLine();
							}
							resultat = scan.match();
							map[i][j] = new Case();
							nature = resultat.group(1);
							try{
								gereTerrain(map[i][j], nature);
							} 
							catch(ExcFichierTexte e) {
								System.out.println("Le fichier texte n'est pas valide ou corrompu");
							}
							map[i][j].setAltitude(Integer.parseInt(resultat.group(2))); 
							map[i][j].setPos(new Point (j, i));

							scan.nextLine();

						}
					}
				}
				/*
				 * si on li deux entier et une chaine de caractère, c'est la position et le type de robots
				 */
				else if(scan.hasNext("(\\d+) (\\d+) (\\w+)")) {

					for (i=0;i<nb_robots;i++){   
						while (!scan.hasNext("(\\d+) (\\d+) (\\w+)")){
							scan.nextLine();
						}
						resultat = scan.match();
						try{
							TabRobot[i] = placeRobots(Integer.parseInt(resultat.group(2)), Integer.parseInt(resultat.group(1)),resultat.group(3));
						}
						catch(ExcFichierTexte e) {
							System.out.println("Le fichier texte n'est pas valide ou corrompu");
						}
						scan.nextLine();
					}
				}
				else {
					break;
				}

			}
		}
		catch (NoSuchElementException e){
			System.out.println("Erreur formatage du fichier");
		}
		catch (IllegalStateException d){
			System.out.println("Erreur fichier");
		}
		catch (FileNotFoundException f){
			System.out.println("Erreur fichier non trouvé");
		}

	}

	public void afficheMap(){
		int i, j;

		for (i=0;i<lig;i++){
			for (j=0;j<col;j++){
				System.out.println("case : ("+i+";"+j+")");
				System.out.println("nature : "+map[i][j].getTerrain());
				System.out.println("altitude : "+map[i][j].getAltitude()); 
				System.out.println("Intensite : "+map[i][j].getIntensite());
				System.out.println("Etat : "+map[i][j].getEtat());				
				System.out.println("\n\n"); 
			}
		}

		for (i=0;i<nb_robots;i++){
			System.out.println(TabRobot[i].toString());
			System.out.println("\n");
		}
	}


	/**
	 * affiche la carte sur la fenêtre graphique avec les robots en position
	 */
	public void AfficheMapGraphique(){
		int i, j;

		try {
			for (i=0;i<lig;i++){
				for (j=0;j<col;j++){
					switch (map[i][j].getIntensite()){
					case 5 : 	fenetre.paintBox(j, i,new Color(255, 0, 0));
								fenetre.paintGraphicalElement(j, i, new Text(new Color(0, 0,0) , "Feu"));
					break;

					case 4 : 	fenetre.paintBox(j, i,new Color(255, 63, 0));
								fenetre.paintGraphicalElement(j, i, new Text(new Color(0, 0,0) , "Feu"));
					break;

					case 3 : 	fenetre.paintBox(j, i,new Color(255, 126, 0));
								fenetre.paintGraphicalElement(j, i, new Text(new Color(0, 0,0) , "Feu"));
					break;

					case 2 : 	fenetre.paintBox(j, i,new Color(255, 189, 0));
								fenetre.paintGraphicalElement(j, i, new Text(new Color(0, 0,0) , "Feu"));
					break;

					case 1 : 	fenetre.paintBox(j, i,new Color(255, 255, 0));
								fenetre.paintGraphicalElement(j, i, new Text(new Color(0, 0,0) , "Feu"));
					break;

					default :
						switch (map[i][j].getEtat()){
						case 2 : fenetre.paintBox(j, i,new Color(0, 0, 0));
						break;
						
						default :
							switch (map[i][j].getTerrain()){
							case LAC : 		fenetre.paintBox(j, i,new Color(0, 0, 255));
							break;
	
							case FORET :	fenetre.paintBox(j, i,new Color(69, 139, 0)); 
							break;
	
							case ARABLE :	fenetre.paintBox(j, i,new Color(205, 133, 63));
							break;
	
							case ROCHE :	fenetre.paintBox(j, i,new Color(205, 200, 177));
							break;
	
							case RUISSEAU : fenetre.paintBox(j, i,new Color(102, 255, 255));
							break;
	
							case PATURAGE : fenetre.paintBox(j, i,new Color(0, 255, 0));
							break;
	
							case CULTURE : fenetre.paintBox(j, i,new Color(255, 215, 0));
							break;
	
							case AGRICOLE : fenetre.paintBox(j, i,new Color(102, 205, 0));
							break;
	
							case BROUSSAILLES : fenetre.paintBox(j, i,new Color(154, 205, 50));
							break;
	
							case HUMIDE : fenetre.paintBox(j, i,new Color(139, 69, 19));
							break;
							}
						}
					}
				}

			}
			for(i=0;i<nb_robots;i++){
				int x = TabRobot[i].getPosition().getX();
				int y = TabRobot[i].getPosition().getY();
				int col=0;
				if (map[y][x].getEtat()==2){
					col = 255;
				}
				fenetre.paintGraphicalElement(x, y, new Oval(new Color(col, col, col), 50));

				if (TabRobot[i].getClass().getName()=="robot.RobotPattes")
					fenetre.paintGraphicalElement(x, y, new Text(new Color(col, col,col) , "P"));
				if (TabRobot[i].getClass().getName() == "robot.RobotRoues" )
					fenetre.paintGraphicalElement(x, y, new Text(new Color(col, col, col) , "R"));
				if (TabRobot[i].getClass().getName() == "robot.RobotVolant" )
					fenetre.paintGraphicalElement(x, y, new Text(new Color(col, col, col) , "V"));
				if (TabRobot[i].getClass().getName() == "robot.RobotChenille" )
					fenetre.paintGraphicalElement(x, y, new Text(new Color(col, col, col) , "C"));
			}


		}
		catch (MapIndexOutOfBoundsException e){
			;
		}
	}

	public void setTabRobot(Robot[] LR){
		this.TabRobot=LR;
	}
	
	/**
	 * modifie une case de la carte
	 * @param cas nouvelle case
	 */
	public void setCase(Case cas) {
		Point pos = cas.getPos();
		map[pos.getY()][pos.getX()].setAltitude(cas.getAltitude());
		map[pos.getY()][pos.getX()].setEtat(cas.getEtat());
		map[pos.getY()][pos.getX()].setIntensite(cas.getIntensite());
		map[pos.getY()][pos.getX()].setPos(pos);
		map[pos.getY()][pos.getX()].setTerrain(cas.getTerrain());
	}


	/**
	 * modifie la largeur d'une case
	 * @param larg nouvelle largeur
	 */
	public void setLargeur (int larg)
	{
		this.largeur = larg;
	}
	
	/**
	 * modifie la fenêtre d'affichage de la carte
	 * @param fenetre nouvelle fenêtre
	 */
	public void setFenetre(Display fenetre){
		this.fenetre = fenetre;
	}

	/**
	 * permet d'obtenir une case de la carte
	 * @param p position de la case souhaitée
	 * @return la case voulut
	 */
	public Case getCase (Point p){
		return this.map[p.getY()][p.getX()];
	}

	/**
	 * permet d'obtenir une case de la carte
	 * 
	 * @param x abscisse de la case voulut
	 * @param y ordonnée de la case voulut
	 * @return la case souhaitée
	 */
	public Case getCase(int x, int y){
		return this.map[y][x];	//x représente les colonnes et y les lignes
	}

	/**
	 * permet d'obtenir la largeur d'une case
	 * @return l'attribut largeur
	 */
	public int getLargeur ()
	{
		return this.largeur;
	}

	/**
	 * permet d'obtenir le tableau des robots sur la carte
	 * @return la liste des robots
	 */
	public Robot[] getTabRobot(){
		return this.TabRobot;
	}

	/**
	 * permet d'obtenir le nombre de lignes de la carte
	 * @return l'attribut lig
	 */
	public int getLig(){
		return this.lig;
	}
	
	/**
	 * permet d'obtenir le nombre de colonnes de la carte
	 * @return l'attribut col
	 */
	public int getCol(){
		return this.col;
	}
}