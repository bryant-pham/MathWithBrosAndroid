package com.mathwithbros.model;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

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
	
	//TODO: Consider refactoring this method
	public void recordNewGame( GameItem gameItem ) {
		try { 
			mapper.save( gameItem );
			assignGame( gameItem.getGameID(), gameItem.getP1UserName(), true );
			assignGame( gameItem.getGameID(), gameItem.getP2UserName(), true );
			Log.i( "Success", "Successfully inserted game score" );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	//TODO: Consider refactoring this method
	public void recordFinishedGame( GameItem gameItem ) {
		try { 
			mapper.save( gameItem );
			assignGame( gameItem.getGameID(), gameItem.getP1UserName(), false );
			assignGame( gameItem.getGameID(), gameItem.getP2UserName(), false );
			Log.i( "Success", "Successfully inserted game score" );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void assignGame( String gameID, String userName, boolean newGame ) {
		try {
			GameAssignItem gameAssignItem = new GameAssignItem();
			gameAssignItem.setUserName( userName );
			gameAssignItem.setGameID( gameID );
			
			// If it is a newly started game, set completedGameFlag = 0 to signify game is not completed
			if( newGame )
				gameAssignItem.setCompletedGameFlag( 0 );
			else
				gameAssignItem.setCompletedGameFlag( 1 );
			
			mapper.save( gameAssignItem );
			Log.i( "Success", "Successfully assigned game to player: " + userName );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private List<String> getIncompleteGameIDs( String userName ) {
		List<String> gameIDs = new ArrayList<String>();
		try {
			GameAssignItem gameItem = new GameAssignItem();
			gameItem.setUserName( userName );
			gameItem.setCompletedGameFlag( 0 );
			DynamoDBQueryExpression<GameAssignItem> query = new DynamoDBQueryExpression<GameAssignItem>();
			query.withHashKeyValues( gameItem );
			query.withIndexName( "completedGameFlag" );
			List<GameAssignItem> result = mapper.query( GameAssignItem.class, query );
			
			for( GameAssignItem gameAssignItem : result ) {
				gameIDs.add( gameAssignItem.getGameID() );
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return gameIDs;
	}
	
	public List<GameItem> getYourTurnListData( String userName ) {
		List<GameItem> gameItems = new ArrayList<GameItem>();
		try {
			List<String> gameIDs = getIncompleteGameIDs( userName );
			for( String gameID : gameIDs ) {
				GameItem gameItem = mapper.load( GameItem.class, gameID );
				gameItems.add( gameItem );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return gameItems;
		
	}
}
