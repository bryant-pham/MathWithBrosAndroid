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
import com.mathwithbros.model.GameItem;

public class MainGame extends Activity implements OnClickListener {

	TextView answerBox;
	TextView questionBox;
	TextView timerBox;
	TextView scoreBox;
	Game game;
	MathLibrary mathLibrary;
	int timerCount;
	GameItem receivedGameItem;
	boolean newGame;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main_game );
		
		Bundle data      = getIntent().getExtras();
		receivedGameItem = ( GameItem ) data.getParcelable( "gameItem" );
		newGame          = data.getBoolean( "newGame" );
		
		questionBox = (TextView) findViewById( R.id.question_box );
		answerBox   = (TextView) findViewById( R.id.answer_box );
		timerBox    = (TextView) findViewById( R.id.timer_box );
		scoreBox    = (TextView) findViewById( R.id.score_box );
		
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
		
		setTimer( 5 ); //TODO: CHANGE THIS NUMBER TO CHANGE TIMER
		startTimer();
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
	
	private void check() {
		if( mathLibrary.checkAnswer( answerBox.getText().toString() ) ) {
			game.incrementScore();
			updateScoreDisplay();
			
			Log.i( "Correct answer", "Correct answer input - score incremented" );
			Log.i( "Score", "Score: " + Integer.toString( game.getScore() ) );
			
			setNewEquation();
		}
	}
	
	private void setNewEquation() {
		questionBox.setText(mathLibrary.getEquation());
		answerBox.setText("");
	}
	
	private void updateScoreDisplay() {
		scoreBox.setText(Integer.toString(game.getScore())); //TODO: possibly change the return type of getScore()
	}
	
	private void updateTimerDisplay() {
		timerCount--;
		timerBox.setText(Integer.toString(timerCount));
	}
	
	private void startTimer() {
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
					
					//Record game into database
					recordGame();
					
					//Start ScoreScreen activity
					showScoreScreen();
				}
			}
		};
		handler.postDelayed(runnable, 1000);
	}
	
	/* Method to set allotted time for each round
	 * 
	 * @param timerCount - Amount of time in seconds
	 */
	private void setTimer( int timerCount ) {
		this.timerCount = timerCount;
	}
	
	private void recordGame() {
		if( newGame ) {
			new RecordNewGame().execute( receivedGameItem );
		}
		else {
			new RecordFinishedGame().execute( receivedGameItem );
		}
	}
	
	private void showScoreScreen() {
		//Build intent and start activity here
	}
	
	private class RecordNewGame extends AsyncTask< GameItem, Void, Void > {
		
		protected Void doInBackground( GameItem... gameItemList ) {
			DynamoDBModel ddb = new DynamoDBModel();
			GameItem gameItem = gameItemList[ 0 ];
			gameItem.setP1Score( game.getScore() );
			ddb.recordNewGame( gameItem );
			return null;
		}
	}
	
	private class RecordFinishedGame extends AsyncTask< GameItem, Void, Void > {
		protected Void doInBackground( GameItem... gameItemList ) {
			DynamoDBModel ddb = new DynamoDBModel();
			GameItem gameItem = gameItemList[ 0 ];
			gameItem.setP2Score( game.getScore() );
			ddb.recordFinishedGame( gameItem );
			return null;
		}
	}
}
