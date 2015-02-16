package cs1.softwareProject.explorers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;






import android.app.Activity;
//import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class GroupProfile extends Activity {
	
	private String jsonResult;
	private String url = "http://10.0.2.2/userDetails.php";
	public static  List<userObject> joined_user =  new userData().getUsers();
	ListView userList;
	int admin_id;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupprofile);
		
	Intent intent = getIntent();
	admin_id = intent.getIntExtra("adminId",0);
	String location = intent.getStringExtra("location");
	String name = intent.getStringExtra("EventName");
	String time = intent.getStringExtra("time");
	String description = intent.getStringExtra("description");
	String ageGroup = intent.getStringExtra("ageGroup");
	int image = intent.getIntExtra("Image",0);
	
	
	
	accessWebService();
	userAdapter adapter = new userAdapter(this,R.layout.user_item,joined_user);
    userList=(ListView)findViewById(R.id.userView);
	userList.setAdapter(adapter);
	
	
	
	
	
		
    TextView tv = (TextView) findViewById(R.id.abzy211);	
    tv.setText(name);
    
    TextView tv1 = (TextView) findViewById(R.id.textView2);	
    String test = "ID "+String.valueOf(admin_id);
    tv1.setText(test);
    
    TextView tv2 = (TextView) findViewById(R.id.textView3);	
    tv2.setText(time);
    
    TextView tv3 = (TextView) findViewById(R.id.textView4);	
    tv3.setText(description);
    
    TextView tv4 = (TextView) findViewById(R.id.textView5);	
    tv4.setText(ageGroup);
    
    ImageView im = (ImageView) findViewById(R.id.imageView1);	
    im.setImageResource(image);
    
    
    
    
    userList.setOnItemClickListener(new OnItemClickListener() {
    	
		   @Override
		   public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
				userObject c = joined_user.get(position);
				
				Intent intent = new Intent(GroupProfile.this, userProfile.class);
			//	intent.putExtra("userId", c.id);
				intent.putExtra("userName", c.username);
				intent.putExtra("bio", c.bio);
				intent.putExtra("interest", c.interest);
				intent.putExtra("image", c.image);
				startActivity(intent);
		   } 
		});
    
    
    
    
    
    
    
    
	}
	
	public void joinGroup(View v){
		Toast.makeText(GroupProfile.this, "Sucessfully joined", Toast.LENGTH_LONG).show();
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
		joined_user.clear();
		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			// name of the user group
			JSONArray jsonUserDetails = jsonResponse.optJSONArray("abz");
			for (int i = 0; i < jsonUserDetails.length(); i++) {
				JSONObject jsonChildNode = jsonUserDetails.getJSONObject(i);
				int userId = jsonChildNode.optInt("id");
				String userName = jsonChildNode.optString("username");
				String bio = jsonChildNode.optString("bio");
				String interest = jsonChildNode.optString("interest");
				
				
				
				
				// list all the attributes of the groups
		     
				joined_user.add(new userObject(userId,userName ,R.drawable.california_snow,bio,interest ));
				
				
				
				
				
			}
		} 
		catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Failed to display data! " + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

	}
	
	
	
	
	
	
	 
	
	
	
	



}
