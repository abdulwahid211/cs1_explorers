package cs1.softwareProject.explorers;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class showGroup extends ListActivity {
	private String jsonResult;
	//private String url = "http://10.0.2.2/groupDetails.php";
	private String url = "http://doc.gold.ac.uk/~ma301ma/IgorFile/groupDetails.php";
	public static  List<Group> user_group =  new groupData().getGroup();
	
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_list);
	
		groupAdapter adapter = new groupAdapter(this,R.layout.group_item,user_group);
		
	
		setListAdapter(adapter);
		accessWebService();
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

	
	public void DisplayData() {

		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			// name of the table
			JSONArray jsonUserDetails = jsonResponse.optJSONArray("userGroups");
			for (int i = 0; i < jsonUserDetails.length(); i++) {
				JSONObject jsonChildNode = jsonUserDetails.getJSONObject(i);
				// list all the attributes of the groups
		     	int groupId = jsonChildNode.optInt("id");
				int adminId = jsonChildNode.optInt("admin_id");
				String eventName = jsonChildNode.optString("eventName");
				String location = jsonChildNode.optString("location");
				String time = jsonChildNode.optString("time");
				String ageGroup = jsonChildNode.optString("ageGroup");
				String des = jsonChildNode.optString("description");
				user_group.add(new Group(groupId,adminId,eventName, location  ,time, des, ageGroup, R.drawable.california_snow ));
			}
		} 
		catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Failed to display data! " + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	
		super.onListItemClick(l, v, position, id);
		
		Group c = user_group.get(position);
		
		Intent intent = new Intent(this, GroupProfile.class);
		intent.putExtra("groupId", c.groupId);
		intent.putExtra("adminId", c.adminId);
		intent.putExtra("EventName", c.nameOfEvent);
		intent.putExtra("Image", c.image);
		intent.putExtra("location", c.location);
		intent.putExtra("time", c.time);
		intent.putExtra("ageGroup", c.ageGroup);
		intent.putExtra("description", c.description);
		startActivity(intent);
	}
	
	


	

}