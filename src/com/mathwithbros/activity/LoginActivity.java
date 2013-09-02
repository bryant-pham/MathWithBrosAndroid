package com.mathwithbros.activity;

import com.mathwithbros.GlobalState;
import com.mathwithbros.R;

import com.mathwithbros.model.UserDBModel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.app.Activity;
import android.content.Intent;

public class LoginActivity extends Activity {

	private EditText userNameView;
	private EditText passwordView;
	private ViewFlipper viewFlipper;
	private TextView errorMessage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		viewFlipper  = ( ViewFlipper ) findViewById( R.id.view_flipper );
		userNameView = ( EditText ) findViewById( R.id.username );
		passwordView = ( EditText ) findViewById( R.id.password );
		errorMessage = ( TextView ) findViewById( R.id.error_message );
	}
	
	public void login( View v ) {
		new Login().execute( userNameView.getText().toString().trim(), passwordView.getText().toString() );
	}
	
	public void register( View v ) {
		new Register().execute( userNameView.getText().toString().trim(), passwordView.getText().toString() );
	}
	
	public void switchView( View v ) {
		viewFlipper.showNext();
	}
	
	private void redirectHome() {
		startActivity( new Intent( LoginActivity.this, HomeScreenActivity.class ) );
	}

	private class Login extends AsyncTask<String, Void, Void> {
		
		private boolean successfulLogin;
		private String userName;
		private String password;
		
		protected Void doInBackground( String... userInfo ) {
			try {
				userName = userInfo[ 0 ];
				password = userInfo[ 1 ];
				UserDBModel derp = new UserDBModel();
				successfulLogin = ( derp.loginUser( userName, password ) ) ? true : false;
			} catch ( Exception e ) {
				e.printStackTrace();
			}
			return null;
		}
		
		public void onPostExecute( Void x ) {
			if( successfulLogin ) {
				GlobalState app = (GlobalState) getApplicationContext();
				app.saveUSERNAME( userName );
				redirectHome();
			} else {
				errorMessage.setText( "Incorrect Password" );
			}
		}
	}
	
	private class Register extends AsyncTask<String, Void, Void> {
		
		private boolean successfulRegister;
		private String userName;
		private String password;
		
		protected Void doInBackground( String... userInfo ) {
			try {
				userName = userInfo[ 0 ];
				password = userInfo[ 1 ];
				UserDBModel derp = new UserDBModel();
				successfulRegister = ( derp.registerUser( userName, password ) ) ? true : false;
			} catch ( Exception e ) {
				e.printStackTrace();
			}
			return null;
		}
		
		public void onPostExecute( Void x ) {
			if( successfulRegister ) {
				GlobalState app = (GlobalState) getApplicationContext();
				app.saveUSERNAME( userName );
				redirectHome();
			} else {
				errorMessage.setText( "Username already taken" );
			}
		}
	}
}
