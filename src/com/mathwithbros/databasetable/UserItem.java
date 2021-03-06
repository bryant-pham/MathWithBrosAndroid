package com.mathwithbros.databasetable;

/**
 * Class used by DynamoDB's mapper API to map the schema for the User table
 */

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

@DynamoDBTable( tableName = "Users" )
public class UserItem {

	private String userName;
	
	@DynamoDBHashKey( attributeName = "userName" )
	public String getUserName() { return userName; }
	public void setUserName( String userName ) { this.userName = userName; }
}
