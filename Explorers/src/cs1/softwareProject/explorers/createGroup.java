package cs1.softwareProject.explorers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createGroup extends Activity  implements OnClickListener{

	EditText nameEvent;
	EditText location;
	EditText age;
	EditText time;
	EditText description;
	Button createGroup;
	
	
	String nameOfEvent ="";
	String nameOfLocation ="";
	String ageGroup ="";
	String timeOfEvent ="";
	String des ="";
	public boolean go = false;
	
	private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://10.0.2.2/groups.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.makegroup);
		
		nameEvent =  (EditText)findViewById(R.id.eventName);
		location =  (EditText)findViewById(R.id.location);
		age =  (EditText)findViewById(R.id.EditText01);
		time =  (EditText)findViewById(R.id.EditText02);
		description =  (EditText)findViewById(R.id.EditText03);
		
		
		
		
		
		
		createGroup = (Button)findViewById(R.id.createGroup);
		createGroup.setOnClickListener(this);
		
		
		
	}
	
	public void onClick(View v){
	
		nameOfEvent = nameEvent.getText().toString();
		nameOfLocation = location.getText().toString();
		ageGroup = age.getText().toString();
		timeOfEvent = time.getText().toString();
		des = description.getText().toString();
		
		
		new CreateGroup(nameOfEvent, nameOfLocation ,timeOfEvent, des, ageGroup).execute();
	//	groupData.user_groups.add(new Group(nameOfEvent, nameOfLocation ,timeOfEvent, des, ageGroup, R.drawable.california_snow ));
	
		 Intent i=new Intent(createGroup.this, MainActivity.class);
		// if(go==true){
	     startActivity(i);
	//}
		
	}
	
	
	
	
	
	class CreateGroup extends AsyncTask<String, String, String> {

		  String nameOfEvent;
    		String nameOfLocation;
    		String ageGroup;
    		String timeOfEvent;
    		String des;
		
		
		boolean failure = false;
		
		
		CreateGroup(String groupName,String in ,String t,String des ,String age){
			this.nameOfEvent = groupName;
			this.nameOfLocation =in;
			this.ageGroup = age;
			this.timeOfEvent =t;
			this.des = des;
			
		}
		
		

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(createGroup.this);
           pDialog.setMessage("Creating a Group...");
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(true);
           pDialog.show();
       }

		@Override
		protected String doInBackground(String... args) {
			
           int success;
         

           try {
        	   
        	
         
               List<NameValuePair> params = new ArrayList<NameValuePair>();
               params.add(new BasicNameValuePair("eventName", nameOfEvent));
               params.add(new BasicNameValuePair("location", nameOfLocation));
               params.add(new BasicNameValuePair("time", timeOfEvent));
               params.add(new BasicNameValuePair("description", des));
               params.add(new BasicNameValuePair("ageGroup", ageGroup));
               Log.d("request!", "starting");

               //Posting user data to script
               JSONObject json = jsonParser.makeHttpRequest(
                      LOGIN_URL, "POST", params);

               // full json response
               Log.d("Login attempt", json.toString());

               // json success element
               success = json.getInt(TAG_SUCCESS);
               if (success == 1) {
               	Log.d("Group Created!", json.toString());
               	
               	finish();
               	go = true;
               	return json.getString(TAG_MESSAGE);
               }else{
               	Log.d("Login Failure!", json.getString(TAG_MESSAGE));
               	return json.getString(TAG_MESSAGE);

               }
           } catch (JSONException e) {
               e.printStackTrace();
           }

           return null;

		}
		
       protected void onPostExecute(String file_url) {
           // dismiss the dialog once product deleted
           pDialog.dismiss();
           if (file_url != null){
           	Toast.makeText(createGroup.this, file_url, Toast.LENGTH_LONG).show();
           	
           }

       }

	}
	
	
	

}
