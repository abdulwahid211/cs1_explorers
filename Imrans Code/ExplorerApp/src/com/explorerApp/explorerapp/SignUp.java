package com.explorerApp.explorerapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends Activity {
	//Creating variables for the sign up page.
	EditText email;
	EditText password;
	EditText confirmPassword;
	EditText firstName;
	EditText surname;
	EditText gender;
	EditText age;
	EditText nationality;
	EditText occupation;
	EditText about;
	Button next;
	TextView displayText;
	TextView dText;
	TextView ageNumber;
	
	
	//Creating an ArrayList for what the user types into the specified form fields.
	List<NameValuePair> nameValuePairs;
		
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		//Initialising the variables.
		email = (EditText) findViewById(R.id.emailText);
		password = (EditText) findViewById(R.id.passText);
		confirmPassword = (EditText) findViewById(R.id.confirmText);
		firstName = (EditText) findViewById(R.id.firstText);
		surname = (EditText) findViewById(R.id.surnText);
		gender = (EditText) findViewById(R.id.genderText);
		age = (EditText) findViewById(R.id.ageText);
		nationality = (EditText) findViewById(R.id.natText);
		occupation = (EditText) findViewById(R.id.occText);
		about = (EditText) findViewById(R.id.aboutText);
		next = (Button) findViewById(R.id.profNext);
		displayText = (TextView) findViewById(R.id.displayText);
		dText = (TextView) findViewById(R.id.dText);
		ageNumber = (TextView) findViewById(R.id.ageNumber);
		//next.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
		
		profileButton();
		
	}//End of onCreate function.
	

	//Creating a function that deals with sending information to the php file and creating a http connection.
	public void profileButton()
	{
				
				//Setting up the onClick listener for the submit button.
				next.setOnClickListener(new View.OnClickListener() {
					
					InputStream stream = null;
					
					public void onClick(View v) {
						//Storing the values of the EditText fields within String variables.
						String userEmail = "" + email.getText().toString();
						String userPass = "" + password.getText().toString();
						String userConfirm = "" + confirmPassword.getText().toString();
						String userFName = "" + firstName.getText().toString();
						String userSName = "" + surname.getText().toString();
						String userGender = "" + gender.getText().toString();
						String userAge = "" + age.getText().toString();
						String userNation = "" + nationality.getText().toString();
						String userOccup = "" + occupation.getText().toString();
						String userAbout = "" + about.getText().toString();
						
						//--------Check if each field is empty.--------//
						if(email.getText().toString().equals(""))
						{
							email.setText("Please enter your email.");
						}
						
						if(password.getText().toString().equals(""))
						{
							password.setText("Please enter your password.");
						}
					
						if(confirmPassword.getText().toString().equals(""))
						{
							confirmPassword.setText("Please confirm your password.");
						}
						
						if(firstName.getText().toString().equals(""))
						{
							firstName.setText("Please enter your first name.");
						}
						
						if(surname.getText().toString().equals(""))
						{
							surname.setText("Please enter your surname.");
						}
						
						if(gender.getText().toString().equals(""))
						{
							gender.setText("Please enter your gender.");
						}
						
						//Checking if the age entered is less than 10 or greater than 110.
						if(age.getText().toString().equals("") || Integer.parseInt(age.getText().toString()) < 10 || Integer.parseInt(age.getText().toString())> 110)
						{
							age.setText("Your age must be between 10 - 110.");
						}
						////////----------Need to check if the age entered is characters.------/////////
						
						if(nationality.getText().toString().equals(""))
						{
							nationality.setText("Please enter your nationality.");
						}
						
						if(occupation.getText().toString().equals(""))
						{
							occupation.setText("Please enter your occupation.");
						}
						
						if(about.getText().toString().equals(""))
						{
							about.setText("Please enter your about.");
						}
						//------Checking if each field is empty ends.-----//
						
						//-----Checking if the password is less than 5 characters.
						if(password.getText().toString().length() < 5)
						{
							dText.setText("Your password must be atleast 5 characters long.");
						}
						
						//Check if the password does not equal the confirm password.
						if(!password.getText().toString().equals(confirmPassword.getText().toString()))
						{
							displayText.setText("Your password does not match the confirm password.");
						}	
						
						else
						{
						
						//Creating an ArrayList.
						nameValuePairs = new ArrayList<NameValuePair>();
						
						//Storing the string variables we created within the ArrayList.
						nameValuePairs.add(new BasicNameValuePair("Email", userEmail));
						nameValuePairs.add(new BasicNameValuePair("Pass", userPass));
						nameValuePairs.add(new BasicNameValuePair("Fname", userFName));
						nameValuePairs.add(new BasicNameValuePair("Sname", userSName));
						nameValuePairs.add(new BasicNameValuePair("Gend", userGender));
						nameValuePairs.add(new BasicNameValuePair("Age", userAge));
						nameValuePairs.add(new BasicNameValuePair("Nation", userNation));
						nameValuePairs.add(new BasicNameValuePair("Occup", userOccup));
						nameValuePairs.add(new BasicNameValuePair("About", userAbout));		
						
						//Setting up a connection.
						try
						{
							//Setting up a http client.
							HttpClient httpClient = new DefaultHttpClient();
							
							//Setting up the http post method.
							//Sending the url for the database.
							//Sending the ip address of the localhost.
							//Also adding the php file which connects android with mysql.
							
							//The default ip of the android emulator is 10.0.2.2
							//The ip of a phone will be different.
							HttpPost post = new HttpPost("http://10.0.2.2/ExplorerAppPhp/signUp.php");
							
							//Passing the nameValuePairs ArrayList within the HttpPost.
							post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
							
							//Getting the response.
							HttpResponse response = httpClient.execute(post);
							
							//Setting up the entity.
							HttpEntity entity = response.getEntity();
							
							//Setting up content inside and InputStream.
							stream = entity.getContent();
							
							//Displaying a message if the data has been entered in successfully.
							String message = "The data has been entered successfully.";
							
							Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
							
							//Once the submit button has been pressed then the user will be directed to a new page and the username and password
							//will be sent to the mysql database.
							Intent intent = new Intent(v.getContext(), PickImage.class);
							startActivityForResult (intent, 0);
							
						}//End of try block.
						catch(IOException clientException)//Catching any exceptions that arise from the try block.
						{
							clientException.printStackTrace();
						}//End of catch block.
						
						}//End of else statement.
						
					}//End of onClick() function.
					
				});//End of submit.setOnClickListener.
				
				
	}//End of function.

}
