package com.mathwithbros.activity;

import com.mathwithbros.GlobalState;
import com.mathwithbros.R;

import com.mathwithbros.model.DynamoDBModel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class LoginActivity extends Activity {

	private EditText userNameView;
	private EditText passwordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		userNameView = (EditText) findViewById( R.id.username );
		passwordView = (EditText) findViewById( R.id.password );
	}
	
	public void submit( View v ) {
		new LoginRegister().execute( userNameView.getText().toString(), passwordView.getText().toString() );
	}
	
	private void redirectHome() {
		startActivity( new Intent( LoginActivity.this, HomeScreenActivity.class ) );
		finish();
	}

	private class LoginRegister extends AsyncTask<String, Void, Void> {
		
		protected Void doInBackground( String... userInfo ) {
			try {
				String userName = userInfo[ 0 ];
				String password = userInfo[ 1 ];
				DynamoDBModel derp = new DynamoDBModel();
				derp.loginUser( userName, password );
				GlobalState app = (GlobalState) getApplicationContext();
				app.saveUSERNAME( userName );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
			return null;
		}
		
		public void onPostExecute( Void x ) {
			redirectHome();
		}
	}
}
