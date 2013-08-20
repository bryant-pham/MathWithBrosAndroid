package com.mathwithbros.model;

import android.util.Log;

import com.mathwithbros.databasetable.CredentialsItem;
import com.mathwithbros.databasetable.UserItem;

public class UserDBModel extends DynamoDBModel {

	public boolean registerUser( String userName, String password ) {
		try {
			if( !( checkUserExists( userName ) ) ) {
				CredentialsItem credentialsItem = new CredentialsItem();
				credentialsItem.setUserName( userName );
				credentialsItem.setPassword( password );
				mapper.save( credentialsItem );
				
				UserItem userItem = new UserItem();
				userItem.setUserName( userName );
				mapper.save( userItem );
				
				Log.i( "Success", "Successfully registered user" );
				return true;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean loginUser( String userName, String password ) {
		try {
			if( checkUserExists( userName ) ) {
				return ( mapper.load( CredentialsItem.class, userName, password ) != null ) ? true : false; 
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean checkUserExists( String userName ) {
		return ( mapper.load( UserItem.class, userName ) != null ) ? true : false;
	}
}
