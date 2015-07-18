package exception;

public class ExcIntensiteNulle extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * Exception levée dans le cas où un robot tente d'éteindre une case avec aucun feu
	 */
	public ExcIntensiteNulle() {
		;
	}
}
