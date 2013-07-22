package com.mathwithbros.helper;

import com.mathwithbros.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mathwithbros.model.GameItem;

public class YourTurnListAdapter extends ArrayAdapter<GameItem> {

	GameItem[] listData;
	Context context;
	int textViewResourceId;
	
	public YourTurnListAdapter( Context context, int textViewResourceId, GameItem[] listData ) {
		super( context, textViewResourceId, listData );
		this.listData = listData;
		this.context = context;
		this.textViewResourceId = textViewResourceId;
	}
	
	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		View row = convertView;
		ViewHolder viewHolder;
		if( row == null ) {
			LayoutInflater inflater = ( ( Activity ) context ).getLayoutInflater();
			row = inflater.inflate( textViewResourceId, parent, false );
			viewHolder = new ViewHolder();
			viewHolder.playerName = ( TextView ) row.findViewById( R.id.player_name );
			row.setTag( viewHolder );
		}
		else {
			viewHolder = ( ViewHolder ) row.getTag();
		}
		
		viewHolder.playerName.setText( "HERP DERP" );
		
		return row;
	}
	
	static class ViewHolder {
		TextView playerName;
	}
}
