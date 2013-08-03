package com.mathwithbros.view;

import com.mathwithbros.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.util.Log;
import android.widget.Button;
import android.os.Handler;
import java.lang.Runnable;
import android.os.AsyncTask;
import android.content.Intent;

import com.mathwithbros.mathlibrary.MathLibrary;
import com.mathwithbros.mathlibrary.Game;

import com.mathwithbros.model.DynamoDBModel;

public class MainGame extends Activity implements OnClickListener {

	TextView answerBox;
	TextView questionBox;
	TextView timerBox;
	TextView scoreBox;
	Game game;
	MathLibrary mathLibrary;
	short timerCount;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main_game );
		
		questionBox = (TextView) findViewById( R.id.question_box );
		answerBox = (TextView) findViewById( R.id.answer_box );
		timerBox = (TextView) findViewById( R.id.timer_box );
		scoreBox = (TextView) findViewById( R.id.score_box );
		
		Button button0  = (Button) findViewById( R.id.button0 );
		Button button1  = (Button) findViewById( R.id.button1 );
		Button button2  = (Button) findViewById( R.id.button2 );
		Button button3  = (Button) findViewById( R.id.button3 );
		Button button4  = (Button) findViewById( R.id.button4 );
		Button button5  = (Button) findViewById( R.id.button5 );
		Button button6  = (Button) findViewById( R.id.button6 );
		Button button7  = (Button) findViewById( R.id.button7 );
		Button button8  = (Button) findViewById( R.id.button8 );
		Button button9  = (Button) findViewById( R.id.button9 );
		Button clear    = (Button) findViewById( R.id.clear );
		Button negative = (Button) findViewById( R.id.negative );
		
		button0.setOnClickListener( this );
		button1.setOnClickListener( this );
		button2.setOnClickListener( this );
		button3.setOnClickListener( this );
		button4.setOnClickListener( this );
		button5.setOnClickListener( this );
		button6.setOnClickListener( this );
		button7.setOnClickListener( this );
		button8.setOnClickListener( this );
		button9.setOnClickListener( this );
		clear.setOnClickListener( this );
		negative.setOnClickListener( this );
		
		game = new Game();
		mathLibrary = new MathLibrary();
		questionBox.setText( mathLibrary.getEquation() );
		timerBox.setText( Integer.toString( timerCount ) );
		
		setTimer( ( short ) 5 ); //TODO: CHANGE THIS NUMBER TO CHANGE TIMER
		startTimer();
		
		Log.i( "onCreate", "onCreate() Successful" );
	}

	public void onClick( View v ) {
		switch( v.getId() ) {
			case R.id.button0:
				answerBox.append("0");
				check();
				break;
			case R.id.button1:
				answerBox.append("1");
				check();
				break;
			case R.id.button2:
				answerBox.append("2");
				check();
				break;
			case R.id.button3:
				answerBox.append("3");
				check();
				break;
			case R.id.button4:
				answerBox.append("4");
				check();
				break;
			case R.id.button5:
				answerBox.append("5");
				check();
				break;
			case R.id.button6:
				answerBox.append("6");
				check();
				break;
			case R.id.button7:
				answerBox.append("7");
				check();
				break;
			case R.id.button8:
				answerBox.append("8");
				check();
				break;
			case R.id.button9:
				answerBox.append("9");
				check();
				break;
			case R.id.clear:
				answerBox.setText("");
				break;
			case R.id.negative:
				answerBox.setText("-");
				break;
		}
	}
	
	public void check() {
		try {
			if( mathLibrary.checkAnswer( answerBox.getText().toString() ) ) {
				game.incrementScore();
				updateScoreDisplay();
				
				Log.i( "Correct answer", "Correct answer input - score incremented" );
				Log.i( "Score", "Score: " + Integer.toString( game.getScore() ) );
				
				setNewEquation();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setNewEquation() {
		questionBox.setText(mathLibrary.getEquation());
		answerBox.setText("");
	}
	
	public void updateScoreDisplay() {
		scoreBox.setText(Integer.toString(game.getScore())); //TODO: possibly change the return type of getScore()
	}
	
	public void updateTimerDisplay() {
		timerCount--;
		timerBox.setText(Integer.toString(timerCount));
	}
	
	public void startTimer() {
		final Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if(timerCount != 0) {
					updateTimerDisplay();
					handler.postDelayed(this, 1000);
				}
				else {
					handler.removeCallbacks(this);
					
					/**
					 *  TODO: HARDCODED USERNAME REPLACE THIS LATER
					 *  Currently commented out so new games aren't being inserted every time I test
					 */
					//new RecordScore().execute( "herp", "lolol", game.getScore(), 0 );
					
					//Passing hardcoded intents for testing
					/*Intent intent = new Intent( MainGame.this, ScoreScreen.class );
					intent.putExtra( "p1UserName", "herp" );
					intent.putExtra( "p2UserName", "lolol" );
					intent.putExtra( "p1Score", game.getScore() );
					intent.putExtra( "p2Score", 0 );
					startActivity( intent );*/
				}
			}
		};
		handler.postDelayed(runnable, 1000);
	}
	
	/* Method to set allotted time for each round
	 * 
	 * @param timerCount - Amount of time in seconds
	 */
	public void setTimer( short timerCount ) {
		this.timerCount = timerCount;
	}
	
	private class RecordScore extends AsyncTask< Object, Void, Void > {
		
		protected Void doInBackground( Object... userInfo ) {
			DynamoDBModel ddb = new DynamoDBModel();
			String p1UserName = ( String ) userInfo[ 0 ];
			String p2UserName = ( String ) userInfo[ 1 ];
			int p1Score       = ( Integer ) userInfo[ 2 ];
			int p2Score       = ( Integer ) userInfo[ 3 ];
			ddb.recordScore( p1UserName, p2UserName, p1Score, p2Score );
			return null;
		}
	}
}
