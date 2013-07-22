package com.mathwithbros.model;

import android.util.Log;

import com.amazonaws.auth.AWSCredentials;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.mathwithbros.model.DynamoDBClient;
import com.mathwithbros.model.UserItem;
import com.mathwithbros.model.GameItem;
import com.mathwithbros.model.GameAssignItem;

public class DynamoDBModel {
	
	AmazonDynamoDBClient client;
	DynamoDBMapper mapper;
	
	private final String regionEndpoint = "dynamodb.us-west-2.amazonaws.com";
	
	public DynamoDBModel() {
		try {
			AWSCredentials credentials = new DynamoDBClient().getCredentials();
			client = new AmazonDynamoDBClient( credentials );
			client.setEndpoint( regionEndpoint );
			mapper = new DynamoDBMapper( client );
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public void registerUser( String userName, String password ) {
		try {
			UserItem userItem = new UserItem();
			userItem.setUserName( userName );
			userItem.setPassword( password );
			mapper.save( userItem );
			Log.i( "Success", "Successfully registered user" );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public void recordScore( String p1UserName, String p2UserName, int p1Score, int p2Score ) {
		try { 
			GameItem gameItem = new GameItem();
			gameItem.setP1UserName( p1UserName );
			gameItem.setP1Score( p1Score );
			gameItem.setP2UserName( p2UserName );
			gameItem.setP2Score( p2Score );
			mapper.save( gameItem );
			assignGame( gameItem.getGameID(), p1UserName );
			assignGame( gameItem.getGameID(), p2UserName );
			Log.i( "Success", "Successfully inserted game score" );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void assignGame( String gameID, String userName ) {
		try {
			GameAssignItem gameAssignItem = new GameAssignItem();
			gameAssignItem.setUserName( userName );
			gameAssignItem.setGameID( gameID );
			mapper.save( gameAssignItem );
			Log.i( "Success", "Successfully assigned game to player: " + userName );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
