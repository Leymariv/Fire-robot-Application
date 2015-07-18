package exception;

public class TerrainException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * Exception levée dans le cas où un robot veut aller sur un terrain interdit
	 */	
	public TerrainException() {
		;
	}
}
