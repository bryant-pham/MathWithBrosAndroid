package com.mathwithbros.mathlibrary;

/**
 * Class used to perform all the game math logic; generating equations, checking answers etc
 */

import java.util.Random;

public class MathLibrary {

	private Random rand;
	private int max; 
	private int min;
	private int answer;
	private char[] mathOperatorArray = {'+', '-', '*'};
	
	public MathLibrary() {
		rand = new Random();
		max = 9;
		min = 0;
	}
	
	private int getRandomNumber() {
		return rand.nextInt(max - min + 1) + min;
	}
	
	private char getMathOperator() {
		//nextInt(3) because there are currently 3 elements in mathOperatorArray
		//rand.nextInt(3) returns random number from 0-2
		return mathOperatorArray[rand.nextInt(3)];
	}
	
	private void setAnswer(int answer) {
		this.answer = answer;
	}
	
	private int getAnswer() {
		return this.answer;
	}
	
	private void calculateAnswer(int number1, int number2, char operator) {
		int calculatedAnswer = 0;
		switch(operator) {
			case '+':
				calculatedAnswer = number1 + number2;
				break;
			case '-':
				calculatedAnswer = number1 - number2;
				break;
			case '*':
				calculatedAnswer = number1 * number2;
				break;
		}
		setAnswer(calculatedAnswer);
	}
	
	public String getEquation() {
		int number1 = getRandomNumber();
		int number2 = getRandomNumber();
		char operator = getMathOperator();
		calculateAnswer(number1, number2, operator);
		String num1 = Integer.toString(number1);
		String num2 = Integer.toString(number2);
		String equation = num1 + ' ' + operator + ' ' + num2;
		return equation;
	}
	
	public boolean checkAnswer(String input) {
		int userInput = Integer.parseInt(input);
		if(userInput == getAnswer()) {
			return true;
		}
		else
			return false;
	}
	
}
