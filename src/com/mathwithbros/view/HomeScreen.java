package com.mathwithbros.view;

import com.mathwithbros.R;
import com.mathwithbros.helper.YourTurnListAdapter;
import com.mathwithbros.model.GameItem;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

public class HomeScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		
		GameItem gameItem[] = new GameItem[] {
			new GameItem(),
			new GameItem()
		};
		
		YourTurnListAdapter yourTurnAdapter = new YourTurnListAdapter( this, R.layout.activity_your_turn_list_fragment, gameItem );
		
		ListView yourTurnListview = ( ListView ) findViewById( R.id.your_turn_listview );
		
		yourTurnListview.setAdapter( yourTurnAdapter );
	}

}
