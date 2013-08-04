package com.mathwithbros.view;

import java.util.List;

import com.mathwithbros.R;
import com.mathwithbros.helper.YourTurnListAdapter;
import com.mathwithbros.model.DynamoDBModel;
import com.mathwithbros.model.GameItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class HomeScreen extends Activity {

	ListView yourTurnListview;
	List<GameItem> gameItem;
	YourTurnListAdapter yourTurnAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		yourTurnListview = ( ListView ) findViewById( R.id.your_turn_listview );
		
		//Grab data and populate list
		new loadYourTurnList().execute();
		
		//Set listener
		yourTurnListview.setOnItemClickListener( selectGame );
	}
	
	private OnItemClickListener selectGame = new OnItemClickListener() {
		@Override
		public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
			//Grab GameItem object from the list item clicked
			GameItem gameItem = yourTurnAdapter.getItem( position );
			
			startGame( gameItem );
		}
	};
	
	private void startGame( GameItem gameItem ) {
		
		//Attach GameItem object to bundle/intent and start activity
		Intent intent = new Intent( HomeScreen.this, MainGame.class );
		Bundle bundle = new Bundle();
		bundle.putParcelable( "gameItem", gameItem );
		intent.putExtras( bundle );
		intent.putExtra( "newGame" , false );
		startActivity( intent );
	}
	
	//AsyncTask to retrieve GameItem objects from DB and populate list
	private class loadYourTurnList extends AsyncTask<String, Void, Void> {
		
		protected Void doInBackground( String... playerName ) {
			//String userName = ( String ) playerName[ 0 ];
			
			/**
			 *  TODO: DA HARDCODED USERNAME REPLACE THIS LATER
			 */
			String userName = "herp";
			
			//Grab list data
			DynamoDBModel derp = new DynamoDBModel();
			gameItem = derp.getYourTurnListData( userName );
			return null;
		}
		
		public void onPostExecute( Void x ) {
			try{
				//Populate list with data
				yourTurnAdapter = new YourTurnListAdapter( HomeScreen.this, R.layout.activity_your_turn_list_fragment, gameItem );		
				yourTurnListview.setAdapter( yourTurnAdapter );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
}
