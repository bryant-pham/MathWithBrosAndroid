package com.mathwithbros.listadapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mathwithbros.R;
import com.mathwithbros.databasetable.GameItem;

public class MatchHistoryListAdapter extends ArrayAdapter<GameItem> {

	private List<GameItem> listData;
	private Context context;
	private int textViewResourceId;
	private String USER_NAME;
	final private String VICTORY = "VICTORY";
	final private String DEFEAT = "DEFEAT";
	
	public MatchHistoryListAdapter( Context context, int textViewResourceId, List<GameItem> listData, String USER_NAME ) {
		super( context, textViewResourceId, listData );
		this.listData = listData;
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.USER_NAME = USER_NAME;
	}
	
	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		View row = convertView;
		try {
			ViewHolder viewHolder;
			if( row == null ) {
				LayoutInflater inflater = ( ( Activity ) context ).getLayoutInflater();
				row                     = inflater.inflate( textViewResourceId, parent, false );
				viewHolder              = new ViewHolder();
				viewHolder.playerName   = ( TextView ) row.findViewById( R.id.player_name );
				viewHolder.gameResult   = ( TextView ) row.findViewById( R.id.game_result );
				row.setTag( viewHolder );
			}
			else 
				viewHolder = ( ViewHolder ) row.getTag();
			
			GameItem gameItem = listData.get( position );
			
			if( !( gameItem.getP1UserName().equals( USER_NAME ) ) )
				viewHolder.playerName.setText( gameItem.getP1UserName() );
			else
				viewHolder.playerName.setText( gameItem.getP2UserName() );
			
			if( USER_NAME.equals( determineWinner( gameItem ) ) ) {
				viewHolder.gameResult.setText( VICTORY );
				viewHolder.gameResult.setTextColor( Color.GREEN );
			}
			else {
				viewHolder.gameResult.setText( DEFEAT );
				viewHolder.gameResult.setTextColor( Color.RED );
			}
				
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	private String determineWinner( GameItem gameItem ) {
		if( gameItem.getP1Score() > gameItem.getP2Score() )
			return gameItem.getP1UserName();
		else
			return gameItem.getP2UserName();
		
	}
	
	static class ViewHolder {
		TextView playerName;
		TextView gameResult;
	}
}
