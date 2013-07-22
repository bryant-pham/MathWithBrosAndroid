package com.mathwithbros.view;

import com.mathwithbros.R;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class ScoreScreen extends Activity {
	
	TextView p1UserName;
	TextView p2UserName;
	TextView p1Score;
	TextView p2Score;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_screen);
		
		TextView p1UserName = ( TextView ) findViewById( R.id.p1UserName );
		TextView p2UserName = ( TextView ) findViewById( R.id.p2UserName );
		TextView p1Score    = ( TextView ) findViewById( R.id.p1Score );
		TextView p2Score    = ( TextView ) findViewById( R.id.p2Score );
		
		Bundle gameInfo = getIntent().getExtras();
		String p1_UserName = gameInfo.getString( "p1UserName" );
		String p2_UserName = gameInfo.getString( "p2UserName" );
		String p1_score    = Integer.toString( gameInfo.getInt( "p1Score" ) );
		String p2_score    = Integer.toString( gameInfo.getInt( "p2Score" ) );
		
		p1UserName.setText( p1_UserName );
		p2UserName.setText( p2_UserName );
		p1Score.setText( p1_score );
		p2Score.setText( p2_score );
	}

}
