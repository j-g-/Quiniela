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
		int[] predictedScore = new int[2];
		int pointsObtained = 0;
		int predictedWinner =  -1;
		public Prediction (int scoreTeam1, int scoreTeam2, Game game){
			this.game = game;
			this.predictedScore[0] = scoreTeam1;
			this.predictedScore[1] = scoreTeam2;
		}

		/**
		 * Set a score prediction.
		 * @param team1Score score predicted for first team.
 		 * @param team2Score score predicted for second team.
		 */
		public void setPrediction(int team1Score, int team2Score){
			predictedScore[0] = team1Score;
			predictedScore[1] = team2Score;
		}
		public void updatePredictedWinner(){
			this.predictedWinner = analyzeScore(predictedScore);
		}

		public void calculatePointsObtained(){
			this.pointsObtained = 0;
			this.updatePredictedWinner();
			if (this.predictedWinner == this.game.winner) {
				this.pointsObtained += 3;
			}
			if (this.predictedScore[0] == this.game.score[0]) {
				this.pointsObtained += 1;
			}
			if (this.predictedScore[1] == this.game.score[1]) {
				this.pointsObtained += 1;
			}
		}
	} 

	class Game {
		public int gameID;
		String teamNames[] = {"",""};
		int score[] ;
		int winner = -1;
		/**
		 * Game Constructor
		 * @param team1Name name for first team playing.
		 * @param team2Name name for second team playing.
		 * @param gameID unique integer to identify this game.
		 */
		public Game(String team1Name, String team2Name, int gameID){
			this.teamNames[0] = team1Name;
			this.teamNames[1] = team2Name;
			this.score = new int[2];
			this.setScores( -1, -1);
		}
		/**
		 * Set a score for a game.
		 * @param team1Score score obtained by the first team.
 		 * @param team2Score score obtained by the second team.
		 */
		public void setScores(int team1Score, int team2Score){
			score[0] = team1Score;
			score[1] = team2Score;
		}
		/**
		 * Check winner index.
		 */
		public void updateWinner(){
			this.winner = analyzeScore(score);
		}
	}

	class Participant {
		String name ;
		ArrayList<Prediction> predictions; 
		int totalPoints = 0;
		public Participant(String name) {
			this.name = name;
			predictions = null; 
		}

		public void updatePredictions( ArrayList<Prediction> predictions){
			this.predictions = predictions;
		}
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
		this.participants = null;
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
	public void updateGameScores(){
		for (Game g : this.games){
			System.out.print(
						String.format("Game, %s vs %s. ", 
						g.teamNames[0], g.teamNames[1])
			);

			if (isScoreValid(g.score)){
				System.out.println(
						String.format("Current score is %d-%d", 
						  g.score[0], g.score[1]
						)
				);
			} else {
				System.out.println("No scored.");
			}
			askScore(g.score);
		}
	}
	
	public void createParticipantsList(){
		if (this.participants == null){
			this.participants = new ArrayList<>();
		}
		boolean keepAdding = true;
		while (keepAdding){
			addGame();
			System.out.print("Add another participant?");
			keepAdding = askYesOrNo();
		}
	}

	public void addParticipant(){
		Scanner scr = new Scanner(System.in);
		System.out.println("Enter name of participant:");
		String name = scr.nextLine();
		Participant p = new Participant(name);
		this.participants.add(p);
	}
	
			
	static int[] askScore(int currentScore[]){
		System.out.print("Enter new score eg. 0-0 or n to keep current: ");
		Scanner scr = new Scanner(System.in);
		String input = scr.nextLine();
		int[] score = new int[2];
		if (input.contentEquals("n")){
			score = currentScore;
		} else {
			String parts[] = input.split("-");
			score[0] = Integer.parseInt(parts[0]);
			score[1] = Integer.parseInt(parts[1]);
		}
		return score;
	}
	/**
	 * Get a yes or no from user.
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
	 * Check if score is valid.
	 * Checks the score entered is more than 0.
	 * @return true if scores are valid
	 */
	static boolean isScoreValid(int score[]){
		if (score[0] >= -1 && score[1] >= -1){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Finds the winner index.
	 * Checks who is the winner if any.
	 * @return winner index, or -1 if there is a tie.
	 */
	static int analyzeScore(int score[]){
		int winner = -1;
		if (isScoreValid(score)){
			if (score[0] > score[1] ){
				winner = 0;
			} else if (score[1] > score[0]){
				winner = 1;
			} else {
				winner = -1; // tie
			}
			
		}
		return winner;
	}
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
	}
	
}
