import java.awt.Color;

/**
 * Beginner subclass that creates a MineSweeper game with Beginner
 * configurations.
 * 
 * 
 */
public class Beginner extends MineSweeper {

	public Beginner(String n, Color color) {
		super(n, 9, 500, color);

	}

	public void setNumberOfMines() {
		numberOfMines = 15;

	}

}
