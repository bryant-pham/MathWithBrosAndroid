package com.mathwithbros.view;

import java.util.List;

import com.mathwithbros.R;
import com.mathwithbros.helper.YourTurnListAdapter;
import com.mathwithbros.model.DynamoDBModel;
import com.mathwithbros.model.GameItem;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.os.AsyncTask;

public class HomeScreen extends Activity {

	List<GameItem> gameItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		
		new loadYourTurnList().execute();
	}
	
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
				YourTurnListAdapter yourTurnAdapter = new YourTurnListAdapter( HomeScreen.this, R.layout.activity_your_turn_list_fragment, gameItem );		
				ListView yourTurnListview = ( ListView ) findViewById( R.id.your_turn_listview );
				yourTurnListview.setAdapter( yourTurnAdapter );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
}
