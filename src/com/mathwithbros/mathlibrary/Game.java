package com.mathwithbros.mathlibrary;

/**
 * Class used to keep track of scoring for an individual round
 */

public class Game {

	private int score;
	
	public Game() {
		score = 0;
	}
	
	public int getScore() {
		return score;
	}
	
	public void incrementScore() {
		score++;
	}
}
