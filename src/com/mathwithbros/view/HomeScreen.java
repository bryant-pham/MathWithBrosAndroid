package com.mathwithbros.view;

import java.util.List;

import com.mathwithbros.R;
import com.mathwithbros.helper.YourTurnListAdapter;
import com.mathwithbros.model.DynamoDBModel;
import com.mathwithbros.model.GameItem;

import android.os.Bundle;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class HomeScreen extends Activity {

	ListView yourTurnListview;
	List<GameItem> gameItem;
	
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
			Log.i( "test", gameItem.get( position ).getP2UserName() );
			Log.i( "test", "onItemClick FIRED" );
		}
	};
	
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
				yourTurnListview.setAdapter( yourTurnAdapter );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
}
