/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiniela;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author J. Garcia <jyo.garcia at gmail.com>
 */

public class Quiniela {


	class Prediction {
		Game game;
		int predictedScores[] = {0,0};
		int pointsObtained = 0;
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
		public Game(String team1Name, String team2Name, int gameID){
			this.teamNames[0] = team1Name;
			this.teamNames[1] = team2Name;
		
		}
		public void setScores(int team1Score, int team2Score){
			scores[0] = team1Score;
			scores[1] = team2Score;
		}
	
	}

	class Participant {
		String name = "";
		ArrayList<Prediction> predictions; 
		int totalPoints = 0;

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

	public void createGameList() {
		this.games = new ArrayList<>();
		boolean keepAdding = true;
		while (keepAdding){
			addGame();
			System.out.print("Add another game?");
			keepAdding = askYesOrNo();
		}
	}
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
