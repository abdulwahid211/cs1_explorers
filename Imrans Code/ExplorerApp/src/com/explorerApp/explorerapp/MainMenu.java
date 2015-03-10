package com.explorerApp.explorerapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity {
	
	//Two button variables.
	Button signUp;
	Button login;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		//Initialising the buttons.
		signUp = (Button) findViewById(R.id.signUp);
		login = (Button) findViewById(R.id.login);
				
		//Calling a function that directs the user to the sign up page.
		SignUp();
		
	}//End of onCreate function.
	
	public void SignUp()
	{
		signUp.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(v.getContext(), SignUp.class);
				startActivityForResult (intent, 0);
			}//End of onClick() function.
			
		});//End of setOnClickListene() function.
	}//End of function.

}//End of class.
