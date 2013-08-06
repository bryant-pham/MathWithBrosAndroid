package com.mathwithbros.activity;

import com.mathwithbros.R;

import com.mathwithbros.databasetable.GameItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class ScoreScreenActivity extends Activity {
	
	private TextView p1UserName;
	private TextView p2UserName;
	private TextView p1Score;
	private TextView p2Score;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_screen);
		
		p1UserName = ( TextView ) findViewById( R.id.p1UserName );
		p2UserName = ( TextView ) findViewById( R.id.p2UserName );
		p1Score    = ( TextView ) findViewById( R.id.p1Score );
		p2Score    = ( TextView ) findViewById( R.id.p2Score );
		
		Bundle gameInfo = getIntent().getExtras();
		GameItem receivedGameItem = gameInfo.getParcelable( "gameItem" );
		
		p1UserName.setText( receivedGameItem.getP1UserName() );
		p2UserName.setText( receivedGameItem.getP2UserName() );
		p1Score.setText( Integer.toString( receivedGameItem.getP1Score() ) );
		p2Score.setText( Integer.toString( receivedGameItem.getP2Score() ) );
	}

	public void backToHome( View view ) {
		Intent intent = new Intent( this, HomeScreenActivity.class );
		startActivity( intent );
	}
}
