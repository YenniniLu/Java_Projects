package MathGameGUI;

/**
 * Author Name: Xinyi Lu 
 * Course: CS170-01 
 * Project - simple math calculation game for kids 
 * Submission Date: 6:20 am, Friday (5/14) 
 * Brief Description: Operation class - Record
 * set and get username and score
 */
public class Record {
	
	private String name, stringScore;
	private int score;
	
	

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setScore(int score) {
		this.score= score;
	}
	
	public void display() {
		System.out.println("Name: "+name+"\nScore: "+score);
	}
	
	public String toString() {
		name=" "+name;
		stringScore = " "+String.valueOf(score);
		return name+stringScore;
	}
}
