package com.mathwithbros;

import com.mathwithbros.activity.HomeScreenActivity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

public class GlobalState extends Application {

	private String GLOBAL_USERNAME;
	public static final String USER_FILE     = "USER_FILE";
	public static final String USERNAME_KEY  = "username";
	SharedPreferences pref;
	
	@Override
    public void onCreate() {
        super.onCreate();
        getSharedPreferences(USER_FILE, 0).edit().clear().commit(); //TODO: REMOVE LATER - USED TO CLEAR SHARED PREFS FOR TESTING
		pref = getSharedPreferences( USER_FILE, MODE_PRIVATE );
	    GLOBAL_USERNAME = pref.getString( USERNAME_KEY, null );
	    if( GLOBAL_USERNAME != null ) 
	    	redirectToHome();
	}
	
	public String getUSERNAME() {
		return GLOBAL_USERNAME;
	}
	
	public void saveUSERNAME( String userName ) {
		getSharedPreferences( USER_FILE, MODE_PRIVATE )
						.edit()
						.putString( USERNAME_KEY, userName )
						.commit();
		GLOBAL_USERNAME = pref.getString( USERNAME_KEY, null );
	}
	
	private void redirectToHome() {
		Intent intent = new Intent( this, HomeScreenActivity.class );
		intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
		intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
		startActivity( intent );
	}
}
