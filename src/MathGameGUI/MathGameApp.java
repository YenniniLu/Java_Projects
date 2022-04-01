package MathGameGUI;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/**
 * Author Name: Xinyi Lu 
 * Course: CS170-01 
 * Project - simple math calculation game for kids 
 * Submission Date: 6:20 am, Friday (5/14) 
 * Brief Description: Driver class - MathGameApp
 * 
 */

public class MathGameApp {

	public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException {

		JFrame frame = new MathGameFrame();

		frame.setVisible(true);

	}
}