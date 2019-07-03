import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Tyler Hoang, Thomas Wei, Kevin Lam, Andrew Nguyen period #4
 */
public class MineSweeperGUIRunner {
	private static String name;
	static Color green = new Color(5, 145, 19);
	static Color red = new Color(169, 16, 16);
	static Color blue = new Color(19, 10, 104);
	static Color black = new Color(0, 0, 0);
	static Color pink = new Color(255, 192, 203);

	/**
	 * MineSweeper GUI Runner that welcomes the user to a set up process in
	 * order for them to enter their name, desired difficulty and color, and
	 * then the game is created for the user to play.
	 * 
	 * 
	 */
	public static void main(String[] args) {
		Object[] options = { "Cancel", "Advanced", "Intermediate", "Beginner" };
		Object[] colors = { "Cancel", "Green", "Red", "Blue", "Black", "Pink" };
		JFrame Frame = new JFrame();
		name = JOptionPane.showInputDialog(Frame, "Please enter your name:",
				"Name", JOptionPane.PLAIN_MESSAGE);
		int n = JOptionPane.showOptionDialog(Frame, "Select desired difficulty:",
				"", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		
		int i = JOptionPane.showOptionDialog(Frame, "Select desired color:", "",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				colors, colors[0]);
		
		JOptionPane.showMessageDialog(Frame,
				"Click on the yellow square to begin", "MineSweeper",
				JOptionPane.PLAIN_MESSAGE);
		if (n == 3 && (i == 1 || i == 2 || i == 3 || i == 4 || i == 5)) {
			if (i == 1) {
				MineSweeper a = new Beginner(name, green);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 2) {
				MineSweeper a = new Beginner(name, red);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 3) {
				MineSweeper a = new Beginner(name, blue);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 4) {
				MineSweeper a = new Beginner(name, black);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 5) {
				MineSweeper a = new Beginner(name, pink);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
		}
		if (n == 2 && (i == 1 || i == 2 || i == 3 || i == 4 || i == 5)) {
			if (i == 1) {
				MineSweeper a = new Intermediate(name, green);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 2) {
				MineSweeper a = new Intermediate(name, red);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 3) {
				MineSweeper a = new Intermediate(name, blue);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 4) {
				MineSweeper a = new Intermediate(name, black);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 5) {
				MineSweeper a = new Intermediate(name, pink);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
		}
		if (n == 1 && (i == 1 || i == 2 || i == 3 || i == 4 || i == 5)) {
			if (i == 1) {
				MineSweeper a = new Advanced(name, green);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 2) {
				MineSweeper a = new Advanced(name, red);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 3) {
				MineSweeper a = new Advanced(name, blue);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 4) {
				MineSweeper a = new Advanced(name, black);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 5) {
				MineSweeper a = new Advanced(name, pink);
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
		}
	}
}
