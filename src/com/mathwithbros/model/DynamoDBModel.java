package com.mathwithbros.model;

/**
 * Class used to perform database operations against DynamoDB
 */

import com.amazonaws.auth.AWSCredentials;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.mathwithbros.model.DynamoDBClient;

public abstract class DynamoDBModel {
	
	protected AmazonDynamoDBClient client;
	protected DynamoDBMapper mapper;
	
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
}
