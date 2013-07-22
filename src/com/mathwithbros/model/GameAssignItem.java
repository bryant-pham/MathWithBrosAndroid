package com.mathwithbros.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable( tableName = "Game_Assign" )
public class GameAssignItem {

	String gameID;
	String userName;
	
	@DynamoDBHashKey( attributeName = "userName" )
	public String getUserName() { return userName; }
	public void setUserName( String userName ) { this.userName = userName; }
	
	@DynamoDBRangeKey( attributeName = "gameID" )
	public String getGameID() { return gameID; }
	public void setGameID( String gameID ) { this.gameID = gameID; }
}
