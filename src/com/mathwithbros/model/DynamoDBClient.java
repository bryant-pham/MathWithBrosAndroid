package com.mathwithbros.model;

/**
 * Class used to provide the credentials object required for database operations
 */

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.AWSCredentials;

public class DynamoDBClient {

	private final String accessKeyID = "SOME KEY";
	private final String secretAccessKey = "SOME KEY";
	
	AWSCredentials credentials;
	
	public DynamoDBClient() {
		credentials = new BasicAWSCredentials( accessKeyID, secretAccessKey );
	}
	
	public AWSCredentials getCredentials() {
		return credentials;
	}
}
