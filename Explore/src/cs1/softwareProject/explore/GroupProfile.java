package cs1.softwareProject.explore;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
//import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupProfile extends Activity {
	public HashMap<String, Integer> userImg;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private String jsonResult;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private PullToRefreshListView mPullRefreshListView;
	userAdapter adapter;
	/*
	 * private static final String url = "http://10.0.2.2/PhpFiles/mrX.php";
	 * private static final String LOGIN_URL_userJoined =
	 * "http://10.0.2.2/PhpFiles/submitUserJoined.php";
	 */
	private static final String url = "http://doc.gold.ac.uk/~ma301ma/IgorFile/mrX.php";
	private static final String LOGIN_URL_userJoined = "http://doc.gold.ac.uk/~ma301ma/IgorFile/submitUserJoined.php";

	public static List<userObject> joined_user = new userData().getUsers();

	ArrayList<Integer> map = new ArrayList<Integer>();
	ArrayList<Integer> value = new ArrayList<Integer>();

	List<userObject> oragnisedUsers;
	ListView userList;
	int user_id;
	int group_id;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupprofile);

		userImg = new HashMap<String, Integer>();
		accessWebService();
		Intent intent = getIntent();
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.userView);
		group_id = intent.getIntExtra("groupId", 0);
		String postCode = intent.getStringExtra("postCode");
		String name = intent.getStringExtra("EventName");
		String time = intent.getStringExtra("time");
		String description = intent.getStringExtra("description");
		String ageGroup = intent.getStringExtra("ageGroup");
		int image = intent.getIntExtra("Image", 0);
		String location = intent.getStringExtra("location");
		String lanuage = intent.getStringExtra("language");

		ActionBar actionBar = getActionBar();
		actionBar.setTitle("                 " + name);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.rgb(210, 120, 2)));

		adapter = new userAdapter(this, R.layout.user_item, joined_user);
		// userList = (ListView) findViewById(R.id.userView);
		mPullRefreshListView.setAdapter(adapter);

		TextView tv12 = (TextView) findViewById(R.id.lan);
		tv12.setText("Language: " + lanuage);

		TextView tv13 = (TextView) findViewById(R.id.lc);
		tv13.setText("Location: " + location);

		TextView tv1 = (TextView) findViewById(R.id.textView2);
		tv1.setText("Post Code: " + postCode.toUpperCase());

		TextView tv2 = (TextView) findViewById(R.id.textView3);
		tv2.setText("Event Time: " + time);

		TextView tv3 = (TextView) findViewById(R.id.textView4);
		tv3.setText("Description: " + description);

		TextView tv4 = (TextView) findViewById(R.id.textView5);
		tv4.setText("Age group: " + ageGroup);

		ImageView im = (ImageView) findViewById(R.id.imageView1);

		im.setImageResource(sendImage(image));

		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
				userObject c = joined_user.get(position - 1);

				Intent intent = new Intent(GroupProfile.this, userProfile.class);
				intent.putExtra("userId", c.id);
				intent.putExtra("userName", c.username);
				intent.putExtra("firstName", c.fname);
				intent.putExtra("lastName", c.lname);
				intent.putExtra("age", c.age);
				intent.putExtra("about", c.about);
				intent.putExtra("nationality", c.nationality);
				intent.putExtra("occupation", c.occupation);
				intent.putExtra("image", c.image);
				startActivity(intent);
			}
		});

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@SuppressWarnings("unchecked")
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new GetDataTask().execute();
						accessWebService();
						mPullRefreshListView.setAdapter(adapter);
						// setListAdapter(adapter);
					}
				});

		// Add an end-of-list listener
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						Toast.makeText(GroupProfile.this, "End of Event List!",
								Toast.LENGTH_SHORT).show();
					}
				});

		/**
		 * Add Sound Event Listener
		 */

		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(
				this);
		soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
		soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
		mPullRefreshListView.setOnPullEventListener(soundListener);

		ListView actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public int sendImage(int a) {
		// shisha
		if (a == R.drawable.shishaa) {
			return R.drawable.shisha;
		}
		// museum
		if (a == R.drawable.tsq) {
			return R.drawable.mus;
		}
		// bar
		if (a == R.drawable.glass) {
			return R.drawable.barb;
		}
		// coffe
		if (a == R.drawable.coffee) {
			return R.drawable.coffff;
		}
		// Entertainment
		if (a == R.drawable.musician) {
			return R.drawable.enter;
		}
		// restr
		if (a == R.drawable.knif) {
			return R.drawable.res;
		}
		// other
		return R.drawable.mystery;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		onBackPressed();
		return true;
	}

	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		accessWebService();
		// mPullRefreshListView.setAdapter(adapter);
		Log.d("MainActivity", "onResumeGP()");
		// accessWebService();
	}

	public void joinGroup(View v) {
		Login a = new Login();

		new JoinedGroup(group_id, a.id).execute();

	}

	private class GetDataTask extends
			AsyncTask<List<userObject>, Void, List<userObject>> {

		@Override
		protected List<userObject> doInBackground(List<userObject>... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
				// setListAdapter(adapter);
			} catch (InterruptedException e) {
			}
			return joined_user;
		}

		protected void onPostExecute(List<userObject> result) {
			// ((LinkedList<Group>)
			// user_group).addFirst("Added after refresh...");
			adapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	@SuppressWarnings("null")
	public List<userObject> oragnisedUserId(int groupId,
			List<userObject> relUsers) {
		List<userObject> oragnisedUsers = relUsers;

		HashSet<Integer> newSet = new HashSet<Integer>();

		for (int i = 0; i < map.size(); i++) {
			if (map.get(i) == groupId) {
				newSet.add(value.get(i));

			}
		}

		for (int i = 0; i < oragnisedUsers.size(); i++) {
			if (!newSet.contains(oragnisedUsers.get(i).getIDs())) {
				/*
				 * Log.d("THIS IS THE TEST:",
				 * String.valueOf(oragnisedUsers.get(i).getIDs()) + " " +
				 * oragnisedUsers.get(i).getUsername());
				 * Log.d("this is the value", "Wadz:" + "true baby");
				 */
				oragnisedUsers.remove(i);

			}

		}

		for (int i = 0; i < oragnisedUsers.size(); i++) {
			if (!newSet.contains(oragnisedUsers.get(i).getIDs())) {
				oragnisedUsers.remove(i);
			}
		}
		for (int i = 0; i < oragnisedUsers.size(); i++) {
			if (!newSet.contains(oragnisedUsers.get(i).getIDs())) {
				oragnisedUsers.remove(i);
			}
		}
		for (int i = 0; i < oragnisedUsers.size(); i++) {
			if (!newSet.contains(oragnisedUsers.get(i).getIDs())) {
				oragnisedUsers.remove(i);
			}
		}

		// / remove unknown ids

		return oragnisedUsers;

	}

	class JoinedGroup extends AsyncTask<String, String, String> {

		public String groupId;
		public String userId;

		JoinedGroup(int gId, int uId) {
			this.groupId = String.valueOf(gId);
			this.userId = String.valueOf(uId);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(GroupProfile.this);
			pDialog.setMessage("Adding you...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			int success;

			try {

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("group_id", groupId));
				params.add(new BasicNameValuePair("user_id", userId));

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						LOGIN_URL_userJoined, "POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Sucessfully joined!", json.toString());

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

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(GroupProfile.this, file_url, Toast.LENGTH_LONG)
						.show();

			}

		}

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
	}

	public void accessWebService() {
		// create the json data
		JsonObjectData task = new JsonObjectData();
		task.execute(new String[] { url });
	}

	private String getStringFromBitmap(Bitmap bitmapPicture) {
		/*
		 * This functions converts Bitmap picture to a string which can be
		 * JSONified.
		 */
		final int COMPRESSION_QUALITY = 100;
		String encodedImage;
		ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
		bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
				byteArrayBitmapStream);
		byte[] b = byteArrayBitmapStream.toByteArray();
		encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		return encodedImage;
	}

	public void DisplayData() {
		// adapter.clear();
		Tabs_menu a = new Tabs_menu();
		joined_user.clear();
		map.clear();
		value.clear();
		try {

			JSONObject jsonResponse = new JSONObject(jsonResult);

			// name of the user group
			JSONArray jsonUserDetails = jsonResponse.optJSONArray("userInfo");
			for (int i = 0; i < jsonUserDetails.length(); i++) {

				JSONObject jsonChildNode = jsonUserDetails.getJSONObject(i);
				int userId = jsonChildNode.optInt("id");
				String userName = jsonChildNode.optString("username");
				String password = jsonChildNode.optString("password");
				String fname = jsonChildNode.optString("FirstName");
				String lname = jsonChildNode.optString("LastName");
				String image_no = jsonChildNode.optString("image_no");
				String professional = jsonChildNode.optString("Occupation");
				String nation = jsonChildNode.optString("Nationality");
				String age = jsonChildNode.optString("Age");
				String about = jsonChildNode.optString("About");

				int maps = jsonChildNode.optInt("group_id");
				int values = jsonChildNode.optInt("user_id");
				// list all the attributes of the group

				// list all the attributes of the group
				if (userId != 0) {
					joined_user.add(new userObject(userId, userName, fname,
							lname, image_no, age, nation, professional, about));
				}
				if (maps != 0 && values != 0) {
					map.add(maps);
					value.add(values);
				}

			}

		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(),
					"Failed to display data! " + e.toString(),
					Toast.LENGTH_SHORT).show();
		}
		joined_user = oragnisedUserId(group_id, joined_user);
	}

}