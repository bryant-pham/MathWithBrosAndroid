package com.mathwithbros.activity;

import java.util.List;

import com.mathwithbros.GlobalState;
import com.mathwithbros.R;
import com.mathwithbros.databasetable.GameItem;
import com.mathwithbros.databasetable.UserItem;
import com.mathwithbros.listadapter.GetAllPlayersListAdapter;
import com.mathwithbros.model.DynamoDBModel;
import com.mathwithbros.model.GameDBModel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Intent;

public class NewGameListActivity extends Activity {

	private ListView playerListView;
	private GetAllPlayersListAdapter allPlayersAdapter;
	private List<UserItem> playerList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game_list);
		
		playerListView = ( ListView ) findViewById( R.id.all_players_listview );
		new LoadPlayerList().execute();
		
		//Set listener
		playerListView.setOnItemClickListener( selectPlayer );
	}
	
	private OnItemClickListener selectPlayer = new OnItemClickListener() {
		@Override
		public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
			//Grab UserItem object from the list item clicked
			UserItem selectedPlayer = allPlayersAdapter.getItem( position );
			GameItem gameItem = new GameItem();

			GlobalState globalVariables = (GlobalState) getApplicationContext();
			String P1UserName = globalVariables.getUSERNAME();
			gameItem.setP1UserName( P1UserName );
			gameItem.setP2UserName( selectedPlayer.getUserName() );
			
			startGame( gameItem );
		}
	};
	
	private void startGame( GameItem gameItem ) {
		
		//Attach GameItem object to bundle/intent and start activity
		Intent intent = new Intent( NewGameListActivity.this, MainGameActivity.class );
		Bundle bundle = new Bundle();
		bundle.putParcelable( "gameItem", gameItem );
		intent.putExtras( bundle );
		intent.putExtra( "newGame" , true );
		startActivity( intent );
		finish();
	}

	private class LoadPlayerList extends AsyncTask<Void, Void, Void> {
		
		protected Void doInBackground( Void... voids ) {
			GameDBModel derp = new GameDBModel();
			playerList = derp.getAllPlayers();
			return null;
		}
		
		public void onPostExecute( Void x ) {
			allPlayersAdapter = new GetAllPlayersListAdapter( NewGameListActivity.this, R.layout.activity_your_turn_list_fragment, playerList );
			playerListView.setAdapter( allPlayersAdapter );
		}
	}
}
