package cs1.softwareProject.explore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import android.widget.Spinner;
import android.widget.Toast;

public class createGroup extends Activity implements OnClickListener {
	
	EditText nameEvent;
	EditText location;
	EditText age;
	EditText time;
	EditText description;
	EditText ps;
	EditText other;
	Button createGroup;
	Spinner spinner1;
	Spinner spinner2;
	String place =" ";
	String nameOfEvent = "";
	String nameOfLocation = "";
	String ageGroup = "";
	String timeOfEvent = "";
	String des = "";
	String post="";
	String otherLanguage= "";
	String Language="";
	public  int adminId;
  
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private String jsonResult;
	/*
	private static final String LOGIN_URL = "http://10.0.2.2/PhpFiles/groups.php";
	private static final String LOGIN_URL_user = "http://10.0.2.2/PhpFiles/userDetails.php";
	*/
	private static final String LOGIN_URL = "http://doc.gold.ac.uk/~ma301ma/IgorFile/groups.php";
	private static final String LOGIN_URL_user = "http://doc.gold.ac.uk/~ma301ma/IgorFile/userDetails.php";
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	
	
	ArrayList<userObject> user = new ArrayList<userObject>();
	
	public boolean a = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creategroup);
		 accessWebService();
		nameEvent = (EditText) findViewById(R.id.eventName);
		location = (EditText) findViewById(R.id.location);
		age = (EditText) findViewById(R.id.age);
		time = (EditText) findViewById(R.id.time);
		description = (EditText) findViewById(R.id.des);
		ps = (EditText) findViewById(R.id.postcode);
		createGroup = (Button) findViewById(R.id.createGroup);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		createGroup.setOnClickListener(this);
	    other = (EditText) findViewById(R.id.OtherText);
	   
	  // accessWebService();
	   spinner1.setOnItemSelectedListener(new CustomOtherCall());
	
	
		
	}
	
	

	public void onClick(View v) {
		
		//accessWebService();
		nameOfEvent = nameEvent.getText().toString();
		nameOfLocation = location.getText().toString();
		ageGroup = age.getText().toString();
		timeOfEvent = time.getText().toString();
		des = description.getText().toString();
		post = ps.getText().toString();
		place=String.valueOf(spinner2.getSelectedItem());
		
		if(spinner1.getSelectedItem().equals("Other")){
			other.setVisibility(View.VISIBLE);
			otherLanguage=other.getText().toString();
		}
		else{
			otherLanguage=String.valueOf(spinner1.getSelectedItem());
		}
		
/*
		if(nameOfEvent.length() >1 && nameOfLocation.length() >1 && ageGroup.length() >1 && timeOfEvent.length() > 1 && 
				des.length() >1 && post.length()>1){
			*/
		
		new CreateGroup(adminId, nameOfEvent, nameOfLocation, timeOfEvent, des,
				ageGroup,post,otherLanguage, place).execute();
		
	
		nameEvent.setText("");
		location.setText("");
		age.setText("");
		time.setText("");
		description.setText("");
		ps.setText("");
		other.setText("");
		
		/*
		}
		else{
			Toast.makeText(getApplicationContext(),
					"You must enter fill everything!",
					Toast.LENGTH_SHORT).show();
		}
*/

		

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	//	accessWebService();
	}
	
	public int getUserId(){
		return adminId;
	}

	class CreateGroup extends AsyncTask<String, String, String> {

		// /group variables
		String admin_id;
		String nameOfEvent;
		String nameOfLocation;
		String ageGroup;
		String timeOfEvent;
		String des;
		String post;
		String language;
		String img ;
		int x;
		String x1;
		boolean failure = false;
		Tabs_menu a;
		CreateGroup(int adId, String groupName, String in, String t,
				String des, String age, String p, String lanuage, String img ) {
			this.img = img;
			this.language = lanuage;
            this.post =p;
			this.nameOfEvent = groupName;
			this.nameOfLocation = in;
			this.ageGroup = age;
			admin_id = Integer.toString(adId);
			this.timeOfEvent = t;
			this.des = des;
			a = new Tabs_menu();
			
			for(int i =0; i<user.size();i++){
				if(a.getRealUser().contains(user.get(i).username)){
					x = 100;
				}
			}
			
			
			x1 =String.valueOf(adminId);
			

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
				params.add(new BasicNameValuePair("admin_id",x1));
				params.add(new BasicNameValuePair("eventName", nameOfEvent));
				params.add(new BasicNameValuePair("location", nameOfLocation));
				params.add(new BasicNameValuePair("time", timeOfEvent));
				params.add(new BasicNameValuePair("description", des));
				params.add(new BasicNameValuePair("ageGroup", ageGroup));
				params.add(new BasicNameValuePair("postCode", post));
				params.add(new BasicNameValuePair("language", language));
				params.add(new BasicNameValuePair("image_no", img));
				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
						params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Group Created!", json.toString());

				//	finish();
					return json.getString(TAG_MESSAGE);
				} else {
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
			if (file_url != null) {
				Toast.makeText(createGroup.this, file_url, Toast.LENGTH_LONG)
						.show();

			}

		}

	}// end shit 

	private class JsonObjectData extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

		@Override
		protected void onPostExecute(String result) {
			DisplayData();
		}

	}

	public void accessWebService() {
		// create the json data
		JsonObjectData task = new JsonObjectData();
		task.execute(new String[] { LOGIN_URL_user });
		
	}

	// / display the user details
	public void DisplayData() {
 Tabs_menu a = new Tabs_menu();
		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			// name of the table
			JSONArray jsonUserDetails = jsonResponse.optJSONArray("userInfo");
			Intent intent = getIntent();
			
			for (int i = 0; i < jsonUserDetails.length(); i++) {
				JSONObject jsonChildNode = jsonUserDetails.getJSONObject(i);
				// list all the attributes
				//adminId = Integer.parseInt(jsonChildNode.optString("id"));
				int o = jsonChildNode.optInt("id");
				String username = jsonChildNode.optString("username");
				String password = jsonChildNode.optString("password");

				if (true) {
					adminId = o;
					/*
					String fname = jsonChildNode.optString("FirstName");
					String lname = jsonChildNode.optString("LastName");
					String professional = jsonChildNode.optString("Occupation");
					String nation = jsonChildNode.optString("Nationality");
					String age = jsonChildNode.optString("Age");
					String about  = jsonChildNode.optString("About"); 
					*/
				}
					

			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(),
					"Failed to display data! " + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

	}

}