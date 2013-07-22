package com.mathwithbros.model;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.AWSCredentials;

public class DynamoDBClient {

	private final String accessKeyID = "AKIAIM77WHZK7SNTL73A";
	private final String secretAccessKey = "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";
	
	AWSCredentials credentials;
	
	public DynamoDBClient() {
		credentials = new BasicAWSCredentials( accessKeyID, secretAccessKey );
	}
	
	public AWSCredentials getCredentials() {
		return credentials;
	}
}
