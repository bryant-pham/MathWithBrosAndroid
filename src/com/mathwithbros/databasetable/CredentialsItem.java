package com.mathwithbros.databasetable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable( tableName = "Credentials" )
public class CredentialsItem {

	private String userName;
	private String password;
	
	@DynamoDBHashKey( attributeName = "userName" )
	public String getUserName() { return userName; }
	public void setUserName( String userName ) { this.userName = userName; }
	
	@DynamoDBRangeKey( attributeName = "password" )
	public String getPassword() { return password; }
	public void setPassword( String password ) { this.password = password; }
}
