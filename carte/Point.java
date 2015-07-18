package carte;

/**
 * Classe permettant de regrouper le information de geolocalisation dans une structure
 */
public class Point{

	private int x;
	private int y;

	/**
	 * Constructeurs par default
	 */
	public Point() {
		this.x=0; this.y=0;
	}

	/**
	 * Construteurs de la classe
	 */
	public Point(int a,int b) {
		this.x=a; this.y=b;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x=x;
	}

	public void setY(int y) {
		this.y=y;
	}
}
