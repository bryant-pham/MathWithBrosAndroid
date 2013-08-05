package com.mathwithbros.databasetable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable( tableName = "Game_Assign" )
public class GameAssignItem {

	private String gameID;
	private String userName;
	
	/**
	 * DynamoDB doesn't support boolean values so we'll set completedGameFlag 0 = FALSE and 1 = TRUE
	 */
	private int completedGameFlag;
	
	@DynamoDBHashKey( attributeName = "userName" )
	public String getUserName() { return userName; }
	public void setUserName( String userName ) { this.userName = userName; }
	
	@DynamoDBRangeKey( attributeName = "gameID" )
	public String getGameID() { return gameID; }
	public void setGameID( String gameID ) { this.gameID = gameID; }
	
	@DynamoDBIndexRangeKey( attributeName = "completedGameFlag", localSecondaryIndexName="completedGameFlag" )
	public int getCompletedGameFlag() { return completedGameFlag; }
	public void setCompletedGameFlag( int completedGameFlag ) { this.completedGameFlag = completedGameFlag; }
}
