import java.awt.Color;

/**
 * Intermediate subclass that creates a MineSweeper game with Intermediate
 * configurations.
 * 
 * 
 */

public class Intermediate extends MineSweeper {

	public Intermediate(String n, Color color) {
		super(n, 13, 900, color);

	}

	public void setNumberOfMines() {
		numberOfMines = 30;
	}

}
