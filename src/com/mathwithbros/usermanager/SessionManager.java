package com.mathwithbros.usermanager;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
	
	private static final String USER_FILE     = "USER_FILE";
	private static final String USERNAME_KEY  = "username";
	
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	
	public SessionManager( Context context ) {
		pref   = context.getSharedPreferences( USER_FILE, 0 );
		editor = pref.edit();
		editor.clear().commit();
	}
	
	public boolean checkLoggedIn() {
		return pref.getString( USERNAME_KEY, null ) != null ? true : false;
	}
	
	public String getGlobalUserName() {
		return pref.getString( USERNAME_KEY, null );
	}
	
	public void saveGlobalUserName( String userName ) {
		editor.clear();
		editor.putString( USERNAME_KEY, userName );
		editor.commit();
	}
}
