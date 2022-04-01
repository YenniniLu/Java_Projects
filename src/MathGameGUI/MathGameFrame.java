package MathGameGUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Author Name: Xinyi Lu 
 * Course: CS170-01 Project - simple math calculation game for kids 
 * Submission Date: 6:20 am, Friday (5/14) 
 * Brief Description: Operation class - MathGameFrame 
 * extends JFrame, implements ActionListener Use GUI,
 * graphics, colors, sounds, animations or images, event handling, exception
 * handling, Layout managers, file I/O and other techniques covered in this
 * course to develop math-learning program as an educational game for pre-school
 * or first grade kids. The game should be interactive and display player’s
 * names and scores. A file will store the top 5 player’s names and scores, and
 * be displayed when a button is pressed any time during the game. 
 * 1. use HashMap store user name and related score 
 * 2. display 10 different simple math calculating questions and and let user input answers 
 * 3. checking answers and give point when answer is correct 
 * 4. let users choose to start game, quit game, let next users play and check score ranking
 */

@SuppressWarnings("serial")
class MathGameFrame extends JFrame implements ActionListener { // or ignore warning

	Map<Integer, Record> map = new HashMap<Integer, Record>();
	Random rand; // Random class object declared
	String name;

	// To store current level of the game
	private int level;
	// To store first, second random number
	private int first, second;
	// To store the calculate result and user current score
	private int result, score;
	// To count number of questions asked
	private int counter;

	private JTextField inputT, scoreT, nameT;
	private JLabel levelL, numberL, inputL, scoreL, nameL, introL0, introL1, introL2, continueL1, continueL2,
			continueL3, nameMessageL, imageL, imageL2;
	private JButton calculateB, nextB, rankB, startButton, quitButton, nextPlayerB, loginB;
	private JPanel stage, menuP, questionPanel, loginPanel, lastPanel, askNamePanel, byePanel;

	/***************************************************************************************
	 * Function to center the frame
	 ************************************************************************************/
	private void centerWindow(Window w) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setLocation((d.width - w.getWidth()) / 2, (d.height - w.getHeight()) / 2);
	}

	/***************************************************************************************
	 * constructor of MathGameFrame
	 * different parts of Panels, add them to stage(main Panel) in different situations
	 * different buttons listener and actions
	 * Game Part: give out 10 math questions according to 4 level
	 * level 1: plus questions
	 * level 2: minus questions
	 * level 3: multiplications
	 * level 4: divisions
	 * grade and display correct result when answer wrong
	 * correct answer get 1 point for each question
	 * let user choose to restart , quit, or let next player play or check score ranking
	 * this game has images and sound too
     ************************************************************************************/

	public MathGameFrame() {
		setTitle("Simple Math-Learning Game");
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// welcomeJO();
		// Initializes level to 1
		level = 1;
		// Initializes score to 0
		score = 0;
		// Initializes question counter to 1
		counter = 0;
		// Creates Random class object
		rand = new Random();

		stage = new JPanel();
		 stage.setBackground(Color.white);

		/**
		 * menuPanel setup: on the top, has startButton, QuitButton, NextPlayerB, //
		 * ScoreRankingB, // scoreT, scoreL
		 *
		 */
		menuP = new JPanel();
		 menuP.setBackground(Color.white);
		startButton = new JButton("Start", new ImageIcon("images/play.gif"));
		startButton.setPreferredSize(new java.awt.Dimension(120, 40));
		startButton.addActionListener(this);
		menuP.add(startButton);

		quitButton = new JButton("Quit", new ImageIcon("images/stop.gif"));
		quitButton.setPreferredSize(new java.awt.Dimension(120, 40));
		quitButton.addActionListener(this);
		menuP.add(quitButton);

		nextPlayerB = new JButton("Next Player");
		nextPlayerB.setPreferredSize(new java.awt.Dimension(120, 40));
		nextPlayerB.addActionListener(this);
		menuP.add(nextPlayerB);

		rankB = new JButton("Score Rank");
		rankB.setPreferredSize(new java.awt.Dimension(120, 40));
		rankB.addActionListener(this);
		menuP.add(rankB);

		// scorePanel setup
		scoreL = new JLabel("Score ");
		scoreT = new JTextField(3);
		scoreT.setEditable(false);
		
		menuP.add(scoreL);
		menuP.add(scoreT);
		menuP.setLayout(new FlowLayout(FlowLayout.CENTER));
		stage.add(menuP, BorderLayout.NORTH);
		
/**
 * loginPanel setup
 * askNamePanel: ask user input name, has nameT(name textfield), nameL(name Label)
 * intro statements: 0, 1, 2
 * imageL: cute gif image
 * nameMessageL: give out warning for invalid input or login success message
 * 
 */
		askNamePanel = new JPanel();
		askNamePanel.setBackground(Color.yellow);
		loginPanel = new JPanel();
		loginPanel.setBackground(Color.pink);
		introL0 = new JLabel("Welcome to Simple Math Learning Game!");
		introL1 = new JLabel("Please click 'Start' to start the game!");
		introL2 = new JLabel("Correct answer get 1 point for each. Wrong answer no point.");
		nameL = new JLabel("Name");
		nameMessageL = new JLabel();
		imageL = new JLabel(new ImageIcon("images/jiayou.gif"));
		nameT = new JTextField(10);
		nameT.setText("");
		loginB = new JButton("Login");
		askNamePanel.add(nameL);
		askNamePanel.add(nameT);
		askNamePanel.add(loginB);
		// askNamePanel.setPreferredSize(new java.awt.Dimension(600, 33));
		loginPanel.add(introL0);
		loginPanel.add(introL1);
		loginPanel.add(introL2);
		loginPanel.add(askNamePanel);
		loginPanel.add(nameMessageL);
		loginPanel.add(imageL);
		loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		loginPanel.setPreferredSize(new java.awt.Dimension(600, 300));
		
		/**
		 * questionPanel setup
		 * 
		 */
		questionPanel = new JPanel();
		questionPanel.setBackground(Color.pink);
		questionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		questionPanel.setPreferredSize(new java.awt.Dimension(600, 200));
		// questionPanel.add(scorePanel);
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();

		// Creates labels for questionPanel
		levelL = new JLabel("Level - " + level);
		numberL = new JLabel();
		inputL = new JLabel();

		// Creates a text files for input in questionPanel
		inputT = new JTextField(5);

		// Creates a buttons
		calculateB = new JButton("Grade and Show Result");
		nextB = new JButton("Next Question");

		// Adds main level to panel 1
		jp1.add(levelL);

		// Adds levels to panel 2
		jp2.add(numberL);
		jp2.add(inputT);
		jp2.add(inputL);

		// Adds buttons to panel 3
		jp3.add(calculateB);
		jp3.add(nextB);

		// Adds 3 panels to questionPanel
		questionPanel.add(jp1);
		questionPanel.add(jp2);
		questionPanel.add(jp3);

		/**
		 * lastPanel setup
		 * lastPanel: shows up when users finish all the qustions
		 */
		lastPanel = new JPanel();
		lastPanel.setBackground(Color.orange);
		lastPanel.setPreferredSize(new java.awt.Dimension(600, 200));
		lastPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		continueL1 = new JLabel("Click 'Start' to re-strat the game.");
		continueL2 = new JLabel("Click 'Score Rank' to check the rank.");
		continueL3 = new JLabel("Click the 'Quit' to quit the game.");

		lastPanel.add(continueL1);
		lastPanel.add(continueL2);
		lastPanel.add(continueL3);

		/**
		 * byePanel setup
		 * byePanel: at the end shows up a cute byebye gif image
		 */
		byePanel = new JPanel();
		byePanel.setBackground(Color.white);
		byePanel.setPreferredSize(new java.awt.Dimension(600, 600));
		byePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		imageL2 = new JLabel(new ImageIcon("images/bye1.gif"));
		byePanel.add(imageL2);
		
		stage.add(loginPanel, BorderLayout.CENTER);

		/**
		 * buttons actionListeners
		 * see override function: public void actionPerformed(ActionEvent e) 
		 */
		nextB.addActionListener(this);
		calculateB.addActionListener(this);
		loginB.addActionListener(this);
		rankB.addActionListener(this);
		nextPlayerB.addActionListener(this);

		this.add(stage);
		centerWindow(this);
	}

	// actionPerformed(ActionEvent e):
	// is used by calculateB.addActionListener(new MathGamePanel(name));
	@Override
	public void actionPerformed(ActionEvent e) {
		Object button = e.getSource();
		//when click quitButton
		if (button == quitButton) { 
			loginPanel.setVisible(false);
			lastPanel.setVisible(false);
			questionPanel.setVisible(false);
			stage.add(byePanel);
			JOptionPane.showMessageDialog(null, "Thank you " + name + " for playing this game. ");
			System.exit(0);
		} 
		//when click login BUTTON
		else if (button == loginB) {
			name = nameT.getText();
			if (nameT.getText().trim().equals("") || nameT.getText().length() == 0) {
				nameMessageL.setText("Please enter your name to login first.");
			} else {
				nameMessageL.setText(nameT.getText() + " Login Success!");
			}

		} 
		//when click start button
		else if (button == startButton) {
			//validate input is not empty
			if (nameT.getText().trim().equals("") || nameT.getText().length() == 0) {
				nameMessageL.setText("Please enter your name to login first.");
			} else {// jf.getText().trim().equals("")||jf.getText().length()==0
				nameMessageL.setText("Please enter your name to login first.");
				loginPanel.setVisible(false);
				lastPanel.setVisible(false);
				counter = 0;
				level = 1;
				score = 0;
				scoreT.setText(Integer.toString(score));
				generateRandomNumber(level);
				levelL.setText("Level - " + level);
				questionPanel.setLayout(new GridLayout(3, 1));
				stage.add(questionPanel, BorderLayout.CENTER);
				questionPanel.setVisible(true);
			}
		} 
		//when click "Grade and Show Result" button
		else if (button == calculateB) {
			
			// Setting level constructor/ rules:
			// 4 questions - level 2
			// 6 questions - level 3
			// 8 questions - level 4
			// 10 questions - end of one round questions and display score result
			// Checks if question counter is 3 set the level to 2

			if (counter == 4)
				level = 2;

			// Otherwise checks if question counter is 8 set the level to 3
			else if (counter == 6)
				level = 3;

			// Otherwise checks if question counter is 8 set the level to 4
			else if (counter == 8)
				level = 4;

			// Otherwise checks if question counter is 10 set the level to 1

			// Set the level number in main label
			levelL.setText("Level - " + level);

			resultChecking();

		}
		//when click next button
		else if (button == nextB) {
			counter++;
			if (counter == 11) { // limit the amount of questions to 10

				JOptionPane.showMessageDialog(null, name + ", your score is " + score, "Score Report",
						JOptionPane.INFORMATION_MESSAGE);
				// Clears the contents of result label and text field
				inputT.setText("");
				inputL.setText("");
				questionPanel.setVisible(false);
				lastPanel.setLayout(new GridLayout(3, 1));
				stage.add(lastPanel, BorderLayout.CENTER);
				lastPanel.setVisible(true);
				adding(); // add player name and score to hashmap
			}
			// Clears the contents of result label and text field
			inputT.setText("");
			inputL.setText("");

//Calls the method to generate question
			generateRandomNumber(level);

		} 
		//when click "Score Rank"button
		else if (button == rankB) {
			String tempList = sorting();
			display(tempList);
		} 
		//when click Next Player button
		else if (button == nextPlayerB) {
			lastPanel.setVisible(false);
			nameT.setText("");
			loginPanel.setVisible(true);
		} // end of all the situation of buttons actionPerformeds
	}// End of method: public void actionPerformed(ActionEvent e)

	

//Method to generate question
	public void generateRandomNumber(int currentLevel) {

//Generates random number between 1 and 9 inclusive
		first = rand.nextInt(9) + 1;
		second = rand.nextInt(9) + 1;

//Checks if second number is greater than the first number then swap the numbers
		if (second > first) {

//Swapping process
			int temp = first;
			first = second;
			second = temp;

		} // End of if condition

//Checks if level is 1 then addition
		if (currentLevel == 1) {

//Sets the question to number label
			numberL.setText(first + " + " + second + " = ");

//Calculates the result
			result = first + second;

		} // End of if condition

//Otherwise checks if level is 2 then subtraction
		else if (currentLevel == 2) {

//Sets the question to number label
			numberL.setText(first + " - " + second + " = ");

//Calculates the result
			result = first - second;

		} // End of else if condition

//Otherwise checks if level is 3 then multiplication
		else if (currentLevel == 3) {

//Sets the question to number label
			numberL.setText(first + " X " + second + " = ");

//Calculates the result
			result = first * second;

		} // End of else if condition

//Otherwise checks if level is 4 then division
		else if (currentLevel == 4) {

//Sets the question to number label
			numberL.setText(first + " / " + second + " = ");

//Calculates the result
			result = first / second;

		} // End of else if condition
	}

	/**
	 * public String welcomeJO() { JOptionPane.showMessageDialog(null, "Welcome to
	 * the Simple Math Game!"); player = JOptionPane.showInputDialog(null, "Enter
	 * your name: "); playerAfterValidate = nameValidator(player); // validate name
	 * format: correct format is letters or space only
	 * record.setName(playerAfterValidate); return playerAfterValidate; }
	 **/
	/**
	 * public String optionValidator(String input) { String y = "Y"; String n = "N";
	 * String newInput = ""; int result1 = input.compareToIgnoreCase(y); // if
	 * equal, int = 0 int result2 = input.compareToIgnoreCase(n); // if equal, int =
	 * 0 if (result1 != 0 && result2 != 0) { while (result1 != 0 && result2 != 0) {
	 * newInput = JOptionPane.showInputDialog( "Error! Please enter only
	 * letter.\nEnter Y/y to restart the game.\nEnter N/n to quit the game.");
	 * result1 = newInput.compareToIgnoreCase(y); // if equal, int = 0 result2 =
	 * newInput.compareToIgnoreCase(n); } } else newInput = input;
	 * 
	 * return newInput; }
	 **/
	/**
	 * public String nameValidator(String name) {
	 * 
	 * String input = ""; boolean checker1; input = name; boolean checker =
	 * name.matches("[A-Z]{1}[a-z]+,\s+([A-Z]{1}[a-z]+)"); if (checker == false) {
	 * do {
	 * 
	 * //nameMessageL.setText( // "Please enter name again. FORMAT:(Xxxx, Xxxx where
	 * x is any letters and X is a capital letter)"); //input =
	 * JOptionPane.showInputDialog( // "Please re-enter number:\n" + "(Xxxx, Xxxx
	 * where x is any letters and X is a // capital letter)"); checker1 =
	 * input.matches("[A-Z]{1}[a-z]+,\s+([A-Z]{1}[a-z]+)"); } while (checker1 ==
	 * false); } return input; }
	 **/
	//method to play music when users get correct and wrong answers
	//handle exceptions at the same time for cannot find music file
	public static void music(String musicName) throws UnsupportedAudioFileException, LineUnavailableException
	{
//try block begins
		try {
			File file = new File(musicName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} // End of try block

//Catch block to handle exception
		catch (IOException error) {
			System.out.print("file not found");
		} // End of catch block

	}// End of method: public static void music(String musicName)

	
	//method of checking if answer is correct
	//if is correct, add 1 point
	//if is wrong, give out correct answer and have no point
	//play 2 sounds when get correct and wrong answers
	public void resultChecking() {
		if (counter < 11) {
			try {
				// Checks if calculated result is equals to user entered answer
				if (result == Double.parseDouble(inputT.getText())) { // may throw to Exception e1

					// Increase the score by one
					score++;
					scoreT.setText(Integer.toString(score));

					inputL.setText("Correct Answer!");

					// Sets the congratulation message in result label
					try {
						music("music/cheer.wav");
					} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} // End of if condition

				// Otherwise wrong answer
				else {
					try {
						music("music/error.wav");
					} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// Sets the wrong message in result label and displays the correct answer
					inputL.setText("Sorry Wrong Answer" + "\n Correct Answer: " + result);
				}

			} catch (Exception e1) {// Exception e1: handle input isn't number
				try {
					music("music/error.wav");
				} catch (UnsupportedAudioFileException | LineUnavailableException e2) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				inputT.setText("");
				inputL.setText("Error! Please enter number.");
			}
		}

	}
	
	//add Record to map by using score as keys
	public void add(Record record) {
		map.put(record.getScore(), record);
	}

	//method of adding one person's info
	public void adding() {
		Record record = new Record();
		record.setScore(score);
		record.setName(name);
		add(record);
	}

	//get info by using score as key
	public Record getByScore(int score) { //get info by using name as key
		return map.get(score);
	}


	public String sorting() {
		Record info = new Record(); // create new FriendInfo object
		// create new ArrayList<String> to sort sortedKeys setting by key(name)
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>(map.keySet());
		Collections.sort(sortedKeys); 
		Collections.reverse(sortedKeys);// sort
		String output = "";
		for (int score : sortedKeys) { // keep info one by one person by using name
			info = getByScore(score); 
			output+=" "+info.toString();
		}
		return output;
	}

	public void display(String tempList) { // display all the info in map
		sorting();
		JOptionPane.showMessageDialog(null, tempList);
	}

}
