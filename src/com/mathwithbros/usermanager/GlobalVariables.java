package com.mathwithbros.usermanager;

import com.mathwithbros.activity.LoginActivity;

import android.app.Application;
import android.content.Intent;

public class GlobalVariables extends Application {

	private String GLOBAL_USERNAME;
	private SessionManager session;
	
	@Override
    public void onCreate() {
        super.onCreate();
        session = new SessionManager( getApplicationContext() );
        
        if( !session.checkLoggedIn() )
        	redirectToLogin();
        else
        	GLOBAL_USERNAME = session.getGlobalUserName();
	}
	
	public String getGlobalUserName() {
		return GLOBAL_USERNAME;
	}
	
	public void saveGlobalUserName( String userName ) {
		session.saveGlobalUserName( userName );
		GLOBAL_USERNAME = userName;
	}
	
	private void redirectToLogin() {
		Intent intent = new Intent( this, LoginActivity.class );
		intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
		startActivity( intent );
	}
}
