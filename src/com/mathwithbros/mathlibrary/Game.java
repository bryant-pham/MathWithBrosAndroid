package com.mathwithbros.mathlibrary;

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
