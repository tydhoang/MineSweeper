import java.util.*;
import java.io.*;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

/**
 * This is a class that simulates a game of MineSweeper according to the
 * specifics in the online version, with additional features such as the ability
 * to change the color, win/loss count, and winning/losing messages.
 * 
 * @author Tyler Hoang, Thomas Wei, Bryan Trinh, Kevin Lam, Alvin Phan, Andrew
 *         Nguyen period #4
 */
public abstract class MineSweeper implements ActionListener, MouseListener,
		Game {
	protected final int MINE = -1; // MINEs on buttonNumbers are represented
									// by the
									// number -1
	Random rand;
	private String name;
	protected int numberOfMines;
	protected int sizeOfBoard;
	protected int buttonNumbers[][];
	protected JButton[][] buttons;
	private JFrame frame;
	private Container backBoard;
	private Icon flag;
	private Icon one;
	private Icon two;
	private Icon three;
	private Icon four;
	private Icon five;
	private Icon six;
	private JMenuBar menuBar;
	private JMenu reset;
	private JMenuItem wise;
	private int firstClick = 0;
	private Color c;
	Color green = new Color(5, 145, 19);
	Color red = new Color(169, 16, 16);
	Color blue = new Color(19, 10, 104);
	Color black = new Color(0, 0, 0);
	Color pink = new Color(255, 192, 203);

	/**
	 * Constructor to intialize all the images, the size of the board, the name,
	 * the buttons matrix, and the frame. The frame is positioned in the middle
	 * of the monitor.
	 * 
	 * @param n
	 *            - The name of the Minesweeper game version
	 * @param size
	 *            - Width x Height in pixels
	 * @param frameSize
	 *            - The size of the frame in pixels
	 */
	public MineSweeper(String n, int size, int frameSize, Color color) {

		c = color;
		flag = new ImageIcon("flag.png");
		one = new ImageIcon("1.png");
		two = new ImageIcon("2.png");
		three = new ImageIcon("3.png");
		four = new ImageIcon("4.png");
		five = new ImageIcon("5.png");
		six = new ImageIcon("6.png");
		menuBar = new JMenuBar();
		reset = new JMenu("Reset");
		menuBar.add(reset);
		wise = new JMenuItem("Wise choice...");
		wise.addActionListener(this);
		reset.add(wise);
		sizeOfBoard = size;
		name = n;
		buttons = new JButton[sizeOfBoard][sizeOfBoard];
		frame = new JFrame("MineSweeper - " + name);
		buttonNumbers = new int[sizeOfBoard][sizeOfBoard];
		backBoard = new Container();
		backBoard.setLayout(new GridLayout(sizeOfBoard, sizeOfBoard));
		frame.setSize(frameSize, frameSize);
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				JButton button = new JButton();
				button.setBackground(c);
				button.setOpaque(true);
				button.setBorderPainted(true);
				buttons[i][j] = button;
				buttons[i][j].addMouseListener(this);
				backBoard.add(button);
			}
		}
		frame.setJMenuBar(menuBar);
		frame.add(backBoard, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Inserts a number of mines smaller than the total size of the board.
	 */
	public abstract void setNumberOfMines();

	/**
	 * Method to generate mines on the board randomly.
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
	public void createMines() {
		rand = new Random();
		buttonNumbers = new int[sizeOfBoard][sizeOfBoard];
		ArrayList<Integer> listOfMines = new ArrayList<Integer>();
		for (int i = 0; i < buttonNumbers.length; i++) {
			for (int j = 0; j < buttonNumbers[i].length; j++) {
				listOfMines.add(i * 1000 + j); // numbers are stored in
												// ArrayList
												// being divisible by 1000 for
												// later use
			}
		}
		// Randomly sets certain elements to MINEs
		for (int i = 0; i < numberOfMines; i++) {
			int randomSquare = rand.nextInt(listOfMines.size()); // Random
																	// number

			// A random square is located on the buttonNumbers and is set to a
			// MINE
			buttonNumbers[listOfMines.get(randomSquare) / 1000][listOfMines
					.get(randomSquare) % 1000] = MINE;
			// The element in listOfMines for the square that was just set to a
			// MINE is removed from the ArrayList
			listOfMines.remove(randomSquare);

		}
		// Determines the number of squares that are "neighbors" to the bombs
		for (int i = 0; i < buttonNumbers.length; i++) {
			for (int j = 0; j < buttonNumbers[i].length; j++) {
				int count = 0;
				if (buttonNumbers[i][j] != MINE) {
					if (i > 0 && j > 0 && buttonNumbers[i - 1][j - 1] == MINE) { // top
																					// left
						count++;
					}
					if (i > 0 && buttonNumbers[i - 1][j] == MINE) { // direct
																	// left
						count++;
					}
					if (i > 0 && j < buttonNumbers[0].length - 1
							&& buttonNumbers[i - 1][j + 1] == MINE) { // bottom
																		// left
						count++;
					}
					if (j < buttonNumbers[0].length - 1
							&& buttonNumbers[i][j + 1] == MINE) { // direct
																	// bottom
						count++;
					}
					if (i < buttonNumbers.length - 1
							&& j < buttonNumbers[0].length - 1
							&& buttonNumbers[i + 1][j + 1] == MINE) { // bottom
																		// right
						count++;
					}
					if (i < buttonNumbers.length - 1
							&& buttonNumbers[i + 1][j] == MINE) { // direct
																	// right
						count++;
					}
					if (i < buttonNumbers.length - 1 && j > 0
							&& buttonNumbers[i + 1][j - 1] == MINE) { // top
																		// right
						count++;
					}
					if (j > 0 && buttonNumbers[i][j - 1] == MINE) { // direct
																	// top
						count++;
					}
					buttonNumbers[i][j] = count;
				}
			}
		}
	}

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
	public void clear(ArrayList<Integer> toBeCleared) {
		Iterator<Integer> iter = toBeCleared.iterator();
		while (iter.hasNext()) {
			int i = toBeCleared.get(0) / 1000; // Number is divided by 1000 to
												// get the row
			int j = toBeCleared.get(0) % 1000; // Remainder of number divided by
												// 1000 to get the column
			toBeCleared.remove(0);
			buttons[i][j].setEnabled(false);
			if (buttonNumbers[i][j] == 0) {
				buttons[i][j].setBackground(new Color(255, 255, 255));
				if (i > 0 && j > 0 && buttons[i - 1][j - 1].isEnabled()) { // top
																			// left
					clearHelper(i - 1, j - 1, toBeCleared);
					buttons[i - 1][j - 1]
							.setBackground(new Color(255, 255, 255));
				}
				if (j > 0 && buttons[i][j - 1].isEnabled()) {// direct left
					clearHelper(i, j - 1, toBeCleared);
					buttons[i][j - 1].setBackground(new Color(255, 255, 255));
				}
				if (i < buttonNumbers.length - 1 && j > 0
						&& buttons[i + 1][j - 1].isEnabled()) {// bottom left
					clearHelper(i + 1, j - 1, toBeCleared);
					buttons[i + 1][j - 1]
							.setBackground(new Color(255, 255, 255));
				}
				if (i > 0 && buttons[i - 1][j].isEnabled()) {// direct top
					clearHelper(i - 1, j, toBeCleared);
					buttons[i - 1][j].setBackground(new Color(255, 255, 255));
				}
				if (i < buttonNumbers.length - 1
						&& buttons[i + 1][j].isEnabled()) {// direct
					// bottom
					clearHelper(i + 1, j, toBeCleared);
					buttons[i + 1][j].setBackground(new Color(255, 255, 255));
				}
				if (i > 0 && j < buttonNumbers[0].length - 1
						&& buttons[i - 1][j + 1].isEnabled()) {// top right
					clearHelper(i - 1, j + 1, toBeCleared);
					buttons[i - 1][j + 1]
							.setBackground(new Color(255, 255, 255));
				}
				if (j < buttonNumbers[0].length - 1
						&& buttons[i][j + 1].isEnabled()) {// direct
					// right
					clearHelper(i, j + 1, toBeCleared);
					buttons[i][j + 1].setBackground(new Color(255, 255, 255));
				}
				if (i < buttonNumbers.length - 1
						&& j < buttonNumbers[0].length - 1
						&& buttons[i + 1][j + 1].isEnabled()) {// bottom right
					clearHelper(i + 1, j + 1, toBeCleared);
					buttons[i + 1][j + 1]
							.setBackground(new Color(255, 255, 255));
				}
			}
			clear(toBeCleared);
		}
	}

	/**
	 * Assistance method for clear method, sets buttons to their corresponding
	 * icons. It also sets the zero buttons to be clear.
	 * 
	 * This method is called after the creation of the mines and after the
	 * clearing of all clear tiles. Once the recursive clear method reaches a
	 * numbered tile, it assigns an icon to buttons[row][col] and disables the
	 * button and makes colors the icon background white.
	 * 
	 * @param row
	 *            - The row in the matrix
	 * @param col
	 *            - The column in the matrix
	 */

	public void clearHelper(int row, int col, ArrayList<Integer> arr) {
		if (buttonNumbers[row][col] == 1) {
			buttons[row][col].setIcon(one);
			buttons[row][col].setDisabledIcon(one);
			buttons[row][col].setEnabled(false);
			buttons[row][col].setBackground(new Color(255, 255, 255));
		}
		if (buttonNumbers[row][col] == 2) {
			buttons[row][col].setIcon(two);
			buttons[row][col].setDisabledIcon(two);
			buttons[row][col].setEnabled(false);
			buttons[row][col].setBackground(new Color(255, 255, 255));
		}
		if (buttonNumbers[row][col] == 3) {
			buttons[row][col].setIcon(three);
			buttons[row][col].setDisabledIcon(three);
			buttons[row][col].setEnabled(false);
			buttons[row][col].setBackground(new Color(255, 255, 255));
		}
		if (buttonNumbers[row][col] == 4) {
			buttons[row][col].setIcon(four);
			buttons[row][col].setDisabledIcon(four);
			buttons[row][col].setEnabled(false);
			buttons[row][col].setBackground(new Color(255, 255, 255));
		}
		if (buttonNumbers[row][col] == 5) {
			buttons[row][col].setIcon(five);
			buttons[row][col].setDisabledIcon(five);
			buttons[row][col].setEnabled(false);
			buttons[row][col].setBackground(new Color(255, 255, 255));
		}
		if (buttonNumbers[row][col] == 6) {
			buttons[row][col].setIcon(six);
			buttons[row][col].setDisabledIcon(six);
			buttons[row][col].setEnabled(false);
			buttons[row][col].setBackground(new Color(255, 255, 255));
		}
		if (buttonNumbers[row][col] == 0) {
			buttons[row][col].setText("");
			buttons[row][col].setEnabled(false);
			buttons[row][col].setBackground(new Color(255, 255, 255));
			arr.add(row * 1000 + col);
		}
	}

	/**
	 * Plays audio.
	 * 
	 * @param fname
	 *            - name of audio file
	 */
	public void trySound(String fname) {
		try {
			File click = new File(fname);
			AudioInputStream stream;
			AudioFormat format;
			DataLine.Info info;
			Clip clip;

			stream = AudioSystem.getAudioInputStream(click);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		} catch (Exception e) {

		}
	}

	/**
	 * Sets an action for when the reset button is clicked on. It does the same
	 * thing that the GUI does.
	 */
	public void actionPerformed(ActionEvent event) {
		frame.setVisible(false);
		Object[] options = { "Cancel", "Advanced", "Intermediate", "Beginner" };
		Object[] colors = { "Cancel", "Green", "Red", "Blue", "Black", "Pink" };
		JFrame Frame = new JFrame();
		name = JOptionPane.showInputDialog(Frame, "Enter your name:",
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

	/**
	 * To prevent the user from hitting a mine and losing on the first click, a
	 * recommended square is bordered for the user to click on as a starter.
	 */
	public void determineZero() {
		int count = 0;
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				if (count == 0) {
					if (i > 2 && j > 2 && buttonNumbers[i][j] == 0) {
						buttons[i][j]
								.setBorder(new LineBorder(Color.YELLOW, 4));
						count++;
					}
				}

			}
		}
	}

	/**
	 * Assistant method for mouseClicked, makes a sound and sets the clicked
	 * button to its corresponding image.
	 * 
	 * @param fname
	 *            - The name of the sound file
	 * @param row
	 *            - The row
	 * @param col
	 *            - The column
	 * @param icon
	 *            - The icon
	 */
	public void clickSound(String fname, int row, int col, Icon icon) {
		if (buttons[row][col].isEnabled() != false) {
			trySound(fname);
			buttons[row][col].setIcon(icon);
			buttons[row][col].setDisabledIcon(icon);
			buttons[row][col].setEnabled(false);
		}
	}

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
	@Override
	public void mouseReleased(MouseEvent event) {
		if (firstClick == 0) {
			for (int i = 0; i < buttons.length; i++) {
				for (int j = 0; j < buttons[i].length; j++) {
					buttons[i][j].setBorder(UIManager
							.getBorder("Button.border"));
				}
			}
			firstClick++;
		}
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				if (event.getSource().equals(buttons[i][j])
						&& event.getButton() == MouseEvent.BUTTON3) {
					if (buttons[i][j].isEnabled() == false
							|| buttons[i][j].getIcon() == one
							|| buttons[i][j].getIcon() == two
							|| buttons[i][j].getIcon() == three
							|| buttons[i][j].getIcon() == four
							|| buttons[i][j].getIcon() == five
							|| buttons[i][j].getIcon() == six) {
						break;
					}
					if (buttons[i][j].getIcon() == flag) {
						trySound("click.wav");
						buttons[i][j].setIcon(null);
					} else {
						trySound("click.wav");
						buttons[i][j].setIcon(flag);
						buttons[i][j].setDisabledIcon(null);
					}
					break;
				}
				if (event.getSource().equals(buttons[i][j])
						&& event.getButton() == MouseEvent.BUTTON1) {
					buttons[i][j].setBackground(new Color(255, 255, 255));
					if (buttons[i][j].getIcon() == flag) {
						break;
					}
					if (buttonNumbers[i][j] == MINE) {
						lose();
					} else if (buttonNumbers[i][j] == 0) {
						clickSound("click.wav", i, j, null);
						ArrayList<Integer> zeros = new ArrayList<Integer>();
						zeros.add(i * 1000 + j);
						clear(zeros);
						win();
					} else if (buttonNumbers[i][j] == 1) {
						clickSound("click.wav", i, j, one);
						win();
					} else if (buttonNumbers[i][j] == 2) {
						clickSound("click.wav", i, j, two);
						win();
					} else if (buttonNumbers[i][j] == 3) {
						clickSound("click.wav", i, j, three);
						win();
					} else if (buttonNumbers[i][j] == 4) {
						clickSound("click.wav", i, j, four);
						win();
					} else if (buttonNumbers[i][j] == 5) {
						clickSound("click.wav", i, j, five);
						win();
					} else if (buttonNumbers[i][j] == 6) {
						clickSound("click.wav", i, j, six);
						win();
					} else {
						buttons[i][j].setText(buttonNumbers[i][j] + "");
						buttons[i][j].setEnabled(false);
					}
				}
			}
		}
	}

	/**
	 * Checks if all the buttons, except for mines, have been disabled. As for
	 * the mines, it checks if none of them have been clicked on. If successful,
	 * it will display a win message, along with a reset process.
	 */
	public void win() {
		boolean win = true;
		for (int i = 0; i < buttonNumbers.length; i++) {
			for (int j = 0; j < buttonNumbers[i].length; j++) {
				if (buttonNumbers[i][j] != MINE
						&& buttons[i][j].isEnabled() == true) {
					win = false;
				}
			}
		}
		if (win == true) {
			trySound("win.wav");
			JOptionPane.showMessageDialog(frame,
					"You win!", name,
					JOptionPane.PLAIN_MESSAGE);

			Object[] options = { "Cancel", "Advanced", "Intermediate", "Beginner" };
			Object[] colors = { "Cancel", "Green", "Red", "Blue", "Black", "Pink" };
			frame.setVisible(false);
			JFrame frame = new JFrame();
			int n = JOptionPane.showOptionDialog(frame,
					"Select desired difficulty", "Difficulty",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, options, options[0]);
			
			int i = JOptionPane.showOptionDialog(frame,
					"Select desired color", "",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, colors, colors[0]);
			
			JOptionPane.showMessageDialog(frame, "Click on the yellow square to begin",
					"MineSweeper", JOptionPane.PLAIN_MESSAGE);
			if (n == 3 && (i == 1 || i == 2 || i == 3 || i == 4 || i == 5)) {
				if (i == 1) {
					MineSweeper a = new Beginner(name, new Color(0, 255, 0));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 2) {
					MineSweeper a = new Beginner(name, new Color(255, 0, 0));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 3) {
					MineSweeper a = new Beginner(name, new Color(0, 0, 255));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 4) {
					MineSweeper a = new Beginner(name, new Color(0, 0, 0));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 5) {
					MineSweeper a = new Beginner(name, new Color(255, 192, 203));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
			}
			if (n == 2 && (i == 1 || i == 2 || i == 3 || i == 4 || i == 5)) {
				if (i == 1) {
					MineSweeper a = new Intermediate(name, new Color(0, 255, 0));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 2) {
					MineSweeper a = new Intermediate(name, new Color(255, 0, 0));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 3) {
					MineSweeper a = new Intermediate(name, new Color(0, 0, 255));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 4) {
					MineSweeper a = new Intermediate(name, new Color(0, 0, 0));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 5) {
					MineSweeper a = new Intermediate(name, new Color(255, 192, 203));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
			}
			if (n == 1 && (i == 1 || i == 2 || i == 3 || i == 4 || i == 5)) {
				if (i == 1) {
					MineSweeper a = new Advanced(name, new Color(0, 255, 0));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 2) {
					MineSweeper a = new Advanced(name, new Color(255, 0, 0));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 3) {
					MineSweeper a = new Advanced(name, new Color(0, 0, 255));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 4) {
					MineSweeper a = new Advanced(name, new Color(0, 0, 0));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
				if (i == 5) {
					MineSweeper a = new Advanced(name, new Color(255, 192, 203));
					a.setNumberOfMines();
					a.createMines();
					a.determineZero();
				}
			}
		}
	}

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
	public void lose() {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				if (buttons[i][j].isEnabled()) {
					if (buttonNumbers[i][j] != MINE) {
						if (buttonNumbers[i][j] == 1
								|| buttonNumbers[i][j] == 2
								|| buttonNumbers[i][j] == 3
								|| buttonNumbers[i][j] == 4
								|| buttonNumbers[i][j] == 5
								|| buttonNumbers[i][j] == 6) {
							buttons[i][j].setEnabled(false);
						}

						if (buttonNumbers[i][j] == 0) {
							buttons[i][j].setText("");
							buttons[i][j].setEnabled(false);
						}
					} else {
						ImageIcon bomb = new ImageIcon("Landmine.png");
						Image scaleImage = bomb.getImage().getScaledInstance(
								frame.getWidth() / sizeOfBoard,
								frame.getHeight() / sizeOfBoard,
								Image.SCALE_DEFAULT);
						bomb = new ImageIcon(scaleImage);
						buttons[i][j].setIcon(bomb);
						buttons[i][j].setDisabledIcon(bomb);
						buttons[i][j].setEnabled(false);
					}
				}
			}
		}
		trySound("lose.wav");
		JOptionPane.showMessageDialog(frame, "You lose!",
				name, JOptionPane.PLAIN_MESSAGE);
		frame.setVisible(false);
		JFrame frame = new JFrame();
		Object[] options = { "Cancel", "Advanced", "Intermediate", "Beginner" };
		Object[] colors = { "Cancel", "Green", "Red", "Blue", "Black", "Pink" };
		int n = JOptionPane.showOptionDialog(frame,
				"Select desired difficulty",
				"Difficulty", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		int i = JOptionPane.showOptionDialog(frame,
				"Select desired color", "",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				colors, colors[0]);	
		JOptionPane
				.showMessageDialog(
						frame,
						"Click on the yellow square to begin",
						"MineSweeper", JOptionPane.PLAIN_MESSAGE);
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
				MineSweeper a = new Advanced(name, green); // green
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 2) {
				MineSweeper a = new Advanced(name, red); // red
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 3) {
				MineSweeper a = new Advanced(name, blue); // blue
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 4) {
				MineSweeper a = new Advanced(name, black); // black
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
			if (i == 5) {
				MineSweeper a = new Advanced(name, pink); // pink
				a.setNumberOfMines();
				a.createMines();
				a.determineZero();
			}
		}
	}

	/**
	 * Extra methods that need not be implemented but required for the
	 * implementation of the MouseListener interface.
	 */
	@Override
	public void mouseClicked(MouseEvent event) {

	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub

	}

}
