package com.mathwithbros.usermanager;

import android.app.Application;

public class GlobalVariables extends Application {

	private String GLOBAL_USERNAME;
	private SessionManager session;
	
	@Override
    public void onCreate() {
        super.onCreate();
        session = new SessionManager( getApplicationContext() );        
    	GLOBAL_USERNAME = session.getGlobalUserName();
	}
	
	public String getGlobalUserName() {
		return GLOBAL_USERNAME;
	}
	
	public void saveGlobalUserName( String userName ) {
		session.saveGlobalUserName( userName );
		GLOBAL_USERNAME = userName;
	}
}
