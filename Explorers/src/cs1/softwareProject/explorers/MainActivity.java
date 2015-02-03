package cs1.softwareProject.explorers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
/*Reference: http://www.geeks.gallery/retrieve-data-from-mysql-database-using-php-and-displaying-it-by-tableview-in-android/
 * 
 * 
 */
public class MainActivity extends Activity {
	private String jsonResult;
	private String url = "http://10.0.2.2/userDetails.php";
	// private String url = "http://doc.gold.ac.uk/~ma301ma/employee.php";
	TextView resultView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		resultView = (TextView) findViewById(R.id.TextMate);
		accessWebService();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

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
	}// end async task

	public void accessWebService() {
		// create the json data
		JsonObjectData task = new JsonObjectData();
		task.execute(new String[] { url });
	}

	/// display the user details 
	public void DisplayData() {

		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			// name of the table
			JSONArray jsonUserDetails = jsonResponse.optJSONArray("abz");
			String results = "";
			for (int i = 0; i < jsonUserDetails.length(); i++) {
				JSONObject jsonChildNode = jsonUserDetails.getJSONObject(i);
				// list all the attributes 
				String id = jsonChildNode.optString("id");
				String username = jsonChildNode.optString("username");
				String password = jsonChildNode.optString("password");
				String interest = jsonChildNode.optString("interest");
				String bio = jsonChildNode.optString("bio");

				if (username.equals(Login.getRealUser())
						&& password.equals(Login.getRealPass())) {
					results = "> Id: " + id + "\n" + "> Username: " + username
							+ "\n" + "> Interest: " + interest + "\n"
							+ "> Bio: " + bio;
				}

				resultView.setText(results);

			}
		} 
		catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Failed to display data! " + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

	}

}