package com.mathwithbros.databasetable;

import android.os.Parcel;
import android.os.Parcelable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

@DynamoDBTable( tableName = "Game" )
public class GameItem implements Parcelable {

	private String gameID;
	private String p1_userName;
	private String p2_userName;
	private int p1_score;
	private int p2_score = 0;
	
	public GameItem() {}
	
	@DynamoDBAutoGeneratedKey
	@DynamoDBHashKey( attributeName = "gameID" )
	public String getGameID() { return gameID; }
	public void setGameID( String gameID ) { this.gameID = gameID; }
	
	@DynamoDBAttribute( attributeName = "p1_userName" )
	public String getP1UserName() { return p1_userName; }
	public void setP1UserName( String p1_userName ) { this.p1_userName = p1_userName; }
	
	@DynamoDBAttribute( attributeName = "p2_userName" )
	public String getP2UserName() { return p2_userName; }
	public void setP2UserName( String p2_userName ) { this.p2_userName = p2_userName; }
	
	@DynamoDBAttribute( attributeName = "p1_score" )
	public int getP1Score() { return p1_score; }
	public void setP1Score( int p1_score ) { this.p1_score = p1_score; }
	
	@DynamoDBAttribute( attributeName = "p2_score" )
	public int getP2Score() { return p2_score; }
	public void setP2Score( int p2_score ) { this.p2_score = p2_score; }
	
	/**
	 * Parcelable interface implementation methods
	 */
	public GameItem( Parcel pc ) {
		gameID      = pc.readString();
		p1_userName = pc.readString();
		p2_userName = pc.readString();
		p1_score    = pc.readInt();
		p2_score    = pc.readInt();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel( Parcel dest, int flags ) {
		dest.writeString( gameID );
		dest.writeString( p1_userName );
		dest.writeString( p2_userName );
		dest.writeInt( p1_score );
		dest.writeInt( p2_score );
	}
	
	public static final Parcelable.Creator<GameItem> CREATOR = new Parcelable.Creator<GameItem>() {
		public GameItem createFromParcel( Parcel pc ) {
			return new GameItem( pc );
		}
		
		public GameItem[] newArray( int size ) {
			return new GameItem[ size ];
		}
	};
}