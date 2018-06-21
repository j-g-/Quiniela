/*
 * This file is distributed under the WTFPL2 license.
 * To view the complete license text go to:
 * http://www.wtfpl.net/txt/copying/
 */
package quiniela;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Contains methods to manage a "quiniela"
 * @author J. Garcia <u0x004a at gmail.com>
 */

public class Quiniela {


	/**
	 * Holds the information about a score prediction
	 */
	class Prediction {
		Game game;
		int predictedScores[] = {0,0};
		int pointsObtained = 0;
		/**
		 * Set a score prediction.
		 * @param team1Score score predicted for first team.
 		 * @param team2Score score predicted for second team.
		 */
		public void setPrediction(int team1Score, int team2Score){
			predictedScores[0] = team1Score;
			predictedScores[1] = team2Score;
		}
	} 

	class Game {
		public int gameID;
		String teamNames[] = {"",""};
		int scores[] = {-1,-1};
		int Winner = -1;
		/**
		 * Game Constructor
		 * @param team1Name name for first team playing.
		 * @param team2Name name for second team playing.
		 * @param gameID unique integer to identify this game.
		 */
		public Game(String team1Name, String team2Name, int gameID){
			this.teamNames[0] = team1Name;
			this.teamNames[1] = team2Name;
		
		}
		/**
		 * Set a score for a game.
		 * @param team1Score score obtained by the first team.
 		 * @param team2Score score obtained by the second team.
		 */
		public void setScores(int team1Score, int team2Score){
			scores[0] = team1Score;
			scores[1] = team2Score;
		}
	
	}

	class Participant {
		String name = "";
		ArrayList<Prediction> predictions; 
		int totalPoints = 0;
		/**
		 * Calculate total sum of points obtained.		 
		 */
		public void calculateTotalPoints(){
			for (Prediction p : predictions){
				totalPoints += p.pointsObtained;
			}
		}
	} 
	ArrayList<Game> games;
	ArrayList<Participant> participants; 
	public int gameCount = 0;

	public Quiniela() {
		this.createGameList();
		this.participants = new ArrayList<>();
	}
	/**
	 * Create a list of games asking to enter info from standard input.
	 */
	public void createGameList() {
		this.games = new ArrayList<>();
		boolean keepAdding = true;
		while (keepAdding){
			addGame();
			System.out.print("Add another game?");
			keepAdding = askYesOrNo();
		}
	}
	
	/**
	 * Add a new game from standard input.
	 */
	public void addGame(){
		Scanner scr = new Scanner(System.in);
		System.out.println("Enter name of first team:");
		String t1 = scr.nextLine();
		System.out.println("Enter name of second team :");
		String t2 = scr.nextLine();
		gameCount++;
		Game g = new Game(t1, t2, gameCount);
		this.games.add(g);
	}
	
			
	/**
	 * Get a yes or no from user.
	 *
	 * @return boolean with value.
	 */
	static boolean askYesOrNo() {
		boolean result = false;
		Scanner s = new Scanner(System.in);
		for (;;) {
			System.out.print(" [y/n] ");
			String answer = s.nextLine();
			boolean yes = answer.compareTo("y") == 0;
			boolean no = answer.compareTo("n") == 0;
			if (yes || no) {
				if (yes) {
					result = true;
				}
				if (no) {
					result = false;
				}
				break;
			}
		}

		return result;
	}
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
	}
	
}
