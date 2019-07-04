import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * 
 * @author Tyler Hoang, Thomas Wei, Bryan Trinh, Kevin Lam, Alvin Phan, Andrew
 *         Nguyen period #4
 *
 */
/**
 * MineSweeper interface that contains the required methods that must be used in
 * the MineSweeper game.
 * 
 */
public interface Game {
	/**
	 * Method to generate mines on the board randomly
	 * 
	 * An ArrayList listOfMines is created to add integers which will determine
	 * the coordinates of buttonNumbers. Since they add integers by thousands,
	 * they can store the dimensions for a matrix up to a maximum of 999x999
	 * pixels.
	 * 
	 * The next for loop creates an integer which receives a random number from
	 * 0 to the ArrayList's size. This integer represents the index of
	 * listOfMines. During the loop, mines are being added to buttonNumbers to
	 * represent the location of the mines. The for loop runs until it stops at
	 * the number of mines to be created.
	 * 
	 * The next nested for loop assigns the number of mines on the buttons that
	 * are next to a mine.
	 */
	public void createMines();

	/**
	 * Clear method is a recursive method that takes in an arrayList with a
	 * button that has a value of 0. The method checks the neighboring buttons
	 * to see if they are zeros as well. It calls the clearHelper method in
	 * order to set all the zeros to clear, disabled buttons.
	 * 
	 * The base case of this recursive method is when the size of the parameter
	 * ArrayList toBeCleared is equal to 0. Clear buttons that are diagonally,
	 * horizontally, or vertically across other clear buttons are set to a white
	 * tile and are disabled. order to set all the zeros to clear, disabled
	 * buttons.
	 * 
	 * @param toBeCleared
	 *            - ArrayList of locations on the buttons matrix where the
	 *            button is 0
	 */
	public void clear(ArrayList<Integer> a);

	/**
	 * Sets an action for when the reset button is clicked on. It does the same
	 * thing that the GUI does.
	 */
	public void actionPerformed(ActionEvent event);

	/**
	 * Method that branches into two paths. Allows the user to left click and
	 * right click as one would in a normal MineSweeper game. This is by far the
	 * most complicated method in this project. The method takes in a mouse
	 * event, and then if its the first click, it will remove the red border
	 * around the recommended square. Continuing on, subsequent mouse events
	 * will have specific actions based on what is clicked and what button is
	 * being used to click on the mouse.
	 * 
	 * @param event
	 *            - A mouse event that occurred in a component within the
	 *            component's bounds
	 */
	public void mouseReleased(MouseEvent event);

	/**
	 * Checks if all the buttons, except for mines, have been disabled. As for
	 * the mines, it checks if none of them have been clicked on. If successful,
	 * it will display a win message, along with a reset process.
	 */
	public void win();

	/**
	 * Displays the lost board. All un-clicked buttons are disabled. All mines
	 * are shown on the board. The board is then disabled and the user must
	 * either exit the program or click the reset button.
	 * 
	 * The nested for-loop goes throughout buttonNumbers to see which tiles are
	 * numbered, or cleared, and disables them. It then searches throughout the
	 * matrix to see which have the value of -1, which represents a mine. Places
	 * where the matrix has a value of -1 corresponds to the coordinates of the
	 * buttons matrix, where the icon of a mine replaces the default button.
	 * 
	 * 
	 */
	public void lose();
}
