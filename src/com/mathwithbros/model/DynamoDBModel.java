package com.mathwithbros.model;

/**
 * Class used to perform database operations against DynamoDB
 */

import android.util.Log;
import java.util.List;
import java.util.ArrayList;

import com.amazonaws.auth.AWSCredentials;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import com.mathwithbros.databasetable.GameAssignItem;
import com.mathwithbros.databasetable.GameItem;
import com.mathwithbros.databasetable.UserItem;
import com.mathwithbros.model.DynamoDBClient;

public class DynamoDBModel {
	
	private AmazonDynamoDBClient client;
	private DynamoDBMapper mapper;
	
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
	
	/**
	 * Method to insert the new GameItem and flag it as incomplete
	 * 
	 * @param gameItem - GameItem object to be updated in database
	 */
	//TODO: Consider refactoring this method
	public void recordNewGame( GameItem gameItem ) {
		try { 
			mapper.save( gameItem );
			assignP1Game( gameItem.getGameID(), gameItem.getP1UserName() );
			assignP2Game( gameItem.getGameID(), gameItem.getP2UserName() );
			Log.i( "Success", "Successfully inserted game score" );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to insert the updated GameItem and flag it as completed
	 * 
	 * @param gameItem - GameItem object to be updated in database
	 */
	//TODO: Consider refactoring this method
	public void recordFinishedGame( GameItem gameItem ) {
		try { 
			mapper.save( gameItem );
			assignCompletedGame( gameItem.getGameID(), gameItem.getP1UserName() );
			assignCompletedGame( gameItem.getGameID(), gameItem.getP2UserName() );
			Log.i( "Success", "Successfully inserted game score" );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to insert GameAssignItems for a specified player and gameID; used to link two players to a GameItem object
	 * 
	 * @param gameID
	 * @param userName - Username of player
	 * @param newGame  - Boolean value to signify a newly started game
	 */
	private void assignCompletedGame( String gameID, String userName ) {
		try {
			GameAssignItem gameAssignItem = new GameAssignItem();
			gameAssignItem.setUserName( userName );
			gameAssignItem.setGameID( gameID );
			gameAssignItem.setPlayedRoundFlag( 1 );
			gameAssignItem.setCompletedGameFlag( 1 );	
			mapper.save( gameAssignItem );
			Log.i( "Success", "Successfully assigned completed game to player: " + userName );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to insert a new GameAssignItem for the specified player1 and flag that they have completed their turn
	 * 
	 * @param gameID
	 * @param p1UserName - Username of player 1
	 */
	private void assignP1Game( String gameID, String p1UserName ) {
		try {
			GameAssignItem gameAssignItem = new GameAssignItem();
			gameAssignItem.setUserName( p1UserName );
			gameAssignItem.setGameID( gameID );
			gameAssignItem.setCompletedGameFlag( 0 );
			gameAssignItem.setPlayedRoundFlag( 1 );
			mapper.save( gameAssignItem );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to insert a new GameAssignItem for the specified player2 and flag that they have NOT completed their turn
	 * 
	 * @param gameID
	 * @param p2UserName - Username of player 2
	 */
	private void assignP2Game( String gameID, String p2UserName ) {
		try {
			GameAssignItem gameAssignItem = new GameAssignItem();
			gameAssignItem.setUserName( p2UserName );
			gameAssignItem.setGameID( gameID );
			gameAssignItem.setCompletedGameFlag( 0 );
			gameAssignItem.setPlayedRoundFlag( 0 );
			mapper.save( gameAssignItem );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to return a list of gameIDs that have NOT been played to completion by the specified player; used to retrieve corresponding GameItems from Game table
	 * 
	 * @param userName - Username of player
	 * @return List<String>
	 */
	private List<String> getIncompleteGameIDs( String userName ) {
		List<String> gameIDs = new ArrayList<String>();
		try {
			GameAssignItem gameItem = new GameAssignItem();
			gameItem.setUserName( userName );
			
			Condition indexKeyCondition = new Condition();
			indexKeyCondition.withComparisonOperator( ComparisonOperator.EQ )
				.withAttributeValueList( new AttributeValue().withN( "0" ) );
			
			DynamoDBQueryExpression<GameAssignItem> query = new DynamoDBQueryExpression<GameAssignItem>();
			query.withHashKeyValues( gameItem );
			query.withRangeKeyCondition( "playedRoundFlag", indexKeyCondition );
			List<GameAssignItem> result = mapper.query( GameAssignItem.class, query );
			
			for( GameAssignItem gameAssignItem : result ) {
				gameIDs.add( gameAssignItem.getGameID() );
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return gameIDs;
	}
	
	/**
	 * Method to return a list of gameIDs that have been played to completion by the specified player; used to retrieve corresponding GameItems from Game table
	 * 
	 * @param userName - Username of player
	 * @return List<String>
	 */
	private List<String> getCompleteGameIDs( String userName ) {
		List<String> gameIDs = new ArrayList<String>();
		try {
			GameAssignItem gameItem = new GameAssignItem();
			gameItem.setUserName( userName );
			
			Condition indexKeyCondition = new Condition();
			indexKeyCondition.withComparisonOperator( ComparisonOperator.EQ )
				.withAttributeValueList( new AttributeValue().withN( "1" ) );
			
			DynamoDBQueryExpression<GameAssignItem> query = new DynamoDBQueryExpression<GameAssignItem>();
			query.withHashKeyValues( gameItem );
			query.withRangeKeyCondition( "completedGameFlag", indexKeyCondition );
			List<GameAssignItem> result = mapper.query( GameAssignItem.class, query );
			
			for( GameAssignItem gameAssignItem : result ) {
				gameIDs.add( gameAssignItem.getGameID() );
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return gameIDs;
	}
	
	/**
	 * Method to return a list of GameItems that have NOT been played to completion by the specified player
	 * 
	 * @param userName - Username of player
	 * @return List<GameItem>
	 */
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
	
	
	/**
	 * Method to return a list of GameItems that have been played to completion by the specified player; used to retrieve match history of a player
	 * 
	 * @param userName - Username of player
	 * @return List<GameItem>
	 */
	public List<GameItem> getMatchHistoryListData( String userName ) {
		List<GameItem> gameItems = new ArrayList<GameItem>();
		try {
			List<String> gameIDs = getCompleteGameIDs( userName );
			for( String gameID : gameIDs ) {
				GameItem gameItem = mapper.load( GameItem.class, gameID );
				gameItems.add( gameItem );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return gameItems;
	}
	
	public List<UserItem> getAllPlayers() {
		List<UserItem> list = mapper.scan( UserItem.class, new DynamoDBScanExpression() );
		return list;
	}
}
