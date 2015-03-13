package cs1.softwareProject.explore;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi") public class Register extends Activity implements OnClickListener {

	private EditText user, pass, fname, lname, nation, occup, about, age;
	private Button mRegister;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// php login script

	// localhost :
	// testing on your device
	// put your local ip instead, on windows, run CMD > ipconfig
	// or in mac's terminal type ifconfig and look for the ip under en0 or en1
	// private static final String LOGIN_URL =
	// "http://xxx.xxx.x.x:1234/webservice/register.php";

	// testing on Emulator:
	// private static final String LOGIN_URL = "http://10.0.2.2/register.php";
	//private static final String LOGIN_URL = "http://10.0.2.2/PhpFiles
	private String LOGIN_URL = "http://doc.gold.ac.uk/~ma301ma/IgorFile/register.php";
	// testing from a real server:
	// private static final String LOGIN_URL =
	// "http://www.yourdomain.com/webservice/register.php";

	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) @SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		
		 /// remove the action bar 
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Please Register Your Details");
		actionBar.setLogo(R.drawable.arrow);
      // actionBar.hide();
		
		
		
		
		user = (EditText) findViewById(R.id.username);
		pass = (EditText) findViewById(R.id.password);
		fname = (EditText) findViewById(R.id.postcode);
		lname = (EditText) findViewById(R.id.time);
		age = (EditText) findViewById(R.id.age);
		nation = (EditText) findViewById(R.id.nation);
		occup = (EditText) findViewById(R.id.occu);
		about = (EditText) findViewById(R.id.about);

		mRegister = (Button) findViewById(R.id.register);
		mRegister.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		new CreateUser().execute();

	}

	class CreateUser extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Register.this);
			pDialog.setMessage("Creating User...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;
			String username = user.getText().toString();
			String password = pass.getText().toString();
			String fName = fname.getText().toString();
			String lName = lname.getText().toString();
			String About = about.getText().toString();
			String Age = age.getText().toString();
			String occupa = occup.getText().toString();
			String Nationality_ = nation.getText().toString();

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));
				params.add(new BasicNameValuePair("FirstName", fName));
				params.add(new BasicNameValuePair("LastName", lName));
				params.add(new BasicNameValuePair("Age", Age));
				params.add(new BasicNameValuePair("Nationality", Nationality_));
				params.add(new BasicNameValuePair("About", About));
				params.add(new BasicNameValuePair("Occupation", occupa));
				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
						params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("User Created!", json.toString());
					finish();
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

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG)
						.show();
			}

		}

	}

}