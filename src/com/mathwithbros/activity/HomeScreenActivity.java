package com.mathwithbros.activity;

import java.util.List;

import com.mathwithbros.R;
import com.mathwithbros.databasetable.GameItem;
import com.mathwithbros.listadapter.MatchHistoryListAdapter;
import com.mathwithbros.listadapter.YourTurnListAdapter;
import com.mathwithbros.model.GameDBModel;
import com.mathwithbros.usermanager.GlobalVariables;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class HomeScreenActivity extends Activity {

	private ListView yourTurnListview;
	private ListView matchHistoryListView;
	private YourTurnListAdapter yourTurnAdapter;
	private MatchHistoryListAdapter matchHistoryAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		yourTurnListview = ( ListView ) findViewById( R.id.your_turn_listview );
		matchHistoryListView = ( ListView ) findViewById( R.id.match_history_listview );
		
		//Grab data and populate list
		new LoadYourTurnList().execute();
		
		//Set listeners
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
	
	public void startNewGameActivity( View view ) {
		startActivity( new Intent( this, NewGameListActivity.class ) );
	}
	
	private void startGame( GameItem gameItem ) {
		
		//Attach GameItem object to bundle/intent and start activity
		Intent intent = new Intent( HomeScreenActivity.this, MainGameActivity.class );
		Bundle bundle = new Bundle();
		bundle.putParcelable( "gameItem", gameItem );
		intent.putExtras( bundle );
		intent.putExtra( "newGame" , false );
		intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
		startActivity( intent );
	}
	
	//AsyncTask to retrieve GameItem objects from DB and populate list
	private class LoadYourTurnList extends AsyncTask<Void, Void, Void> {
		
		private ProgressDialog pdia;
		private List<GameItem> gameItemList;
		private List<GameItem> historyGameItemList;
		String USER_NAME = ( ( GlobalVariables ) getApplicationContext() ).getGlobalUserName();

		@Override
		protected void onPreExecute(){ 
			super.onPreExecute();
	        pdia = new ProgressDialog( HomeScreenActivity.this );
	        pdia.setMessage("Loading...");
	        pdia.show();    
		}
		
		protected Void doInBackground( Void...voids ) {
			//Grab list data
			GameDBModel derp = new GameDBModel();
			gameItemList = derp.getYourTurnListData( USER_NAME );
			historyGameItemList = derp.getMatchHistoryListData( USER_NAME );
			return null;
		}
		
		public void onPostExecute( Void x ) {
			try{
				//Populate list with data
				yourTurnAdapter = new YourTurnListAdapter( HomeScreenActivity.this, R.layout.activity_your_turn_list_fragment, gameItemList );		
				yourTurnListview.setAdapter( yourTurnAdapter );
				matchHistoryAdapter = new MatchHistoryListAdapter( HomeScreenActivity.this, R.layout.activity_match_history_list, historyGameItemList, USER_NAME );		
				matchHistoryListView.setAdapter( matchHistoryAdapter );
				pdia.dismiss();
				pdia = null;
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
}
