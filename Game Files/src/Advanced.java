import java.awt.Color;

/**
 * Advanced subclass that creates a MineSweeper game with Advanced
 * configurations.
 * 
 * 
 */

public class Advanced extends MineSweeper {

	public Advanced(String n, Color color) {
		super(n, 16, 1200, color);

	}

	public void setNumberOfMines() {
		numberOfMines = 50;
	}

}
