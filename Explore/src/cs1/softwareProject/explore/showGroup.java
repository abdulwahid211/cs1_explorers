package cs1.softwareProject.explore;

//https://github.com/chrisbanes/Android-PullToRefresh
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class showGroup extends ListActivity {
	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;
	static final int MENU_SET_MODE = 2;
	static final int MENU_DEMO = 3;
	public 	HashMap <String,Integer> groupImg;

	private PullToRefreshListView mPullRefreshListView;

	private String jsonResult;
	// private String url = "http://10.0.2.2/groupDetails.php";

	private String url = "http://doc.gold.ac.uk/~ma301ma/IgorFile/groupDetails.php";
	// PullToRefreshListView mNewsList;

	// private String url = "http://10.0.2.2/PhpFiles/groupDetails.php";

	public static List<Group> user_group = new groupData().getGroup();
	groupAdapter adapter;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_list);
		adapter = new groupAdapter(this, R.layout.group_item, user_group);
		ActionBar actionBar = getActionBar();
		// actionBar.setTitle("All Events");
		accessWebService();
		setListAdapter(adapter);
		
		
		
		Toast.makeText(getApplicationContext(),
				"Please Pull down to refresh page!",
				1).show();
		 
		
		
		
		
		
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

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
						//BubbleSort(user_group);
					  setListAdapter(adapter);
						
					}
				});

		// Add an end-of-list listener
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						Toast.makeText(showGroup.this, "End of Event List!",
								Toast.LENGTH_SHORT).show();
					}
				});

		/**
		 * Add Sound Event Listener
		 */

		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(
				this);
	//	soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
	//	soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
	//	soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
	//	mPullRefreshListView.setOnPullEventListener(soundListener);

		ListView actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);
		//groupImg.put("Shisha",R.drawable.img1);
		//setUserImage();
		
	}
	
	
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		accessWebService();
		setListAdapter(adapter);
		Log.d("YouView ", "Its Resume baby");
		accessWebService();
		
	}
	
	

	public int sendImage(String a){
		if(a.equals("Shisha")){
			return R.drawable.shishaa;
		}
		if(a.equals("Museum")){
			return R.drawable.tsq;
		}
		if(a.equals("Bar")){
			return R.drawable.glass;
		}
		if(a.equals("Cafe")){
			return R.drawable.coffee;
		}
		if(a.equals("Entertainment")){
			return R.drawable.musician;
		}
		if(a.equals("Restaurant")){
			return R.drawable.knif;
		}
		
		
		return R.drawable.questionmark;
		
	}
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		int pos = position - 1;
		System.out.println("size " + user_group.size() + "pos: " + pos);
		Group c = user_group.get(pos);

		Intent intent = new Intent(this, GroupProfile.class);
	    intent.putExtra("Image", c.image);
		intent.putExtra("groupId", c.groupId);
		intent.putExtra("adminId", c.adminId);
		intent.putExtra("EventName", c.nameOfEvent);
		intent.putExtra("location", c.location);
		intent.putExtra("language", c.language);
		intent.putExtra("postCode", c.postCode);
		intent.putExtra("time", c.time);
		intent.putExtra("ageGroup", c.ageGroup);
		intent.putExtra("description", c.description);
		startActivity(intent);
	}

	


	private class GetDataTask extends AsyncTask<List<Group>, Void, List<Group>> {

		@Override
		protected List<Group> doInBackground(List<Group>... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
				// setListAdapter(adapter);
			} catch (InterruptedException e) {
			}
			return user_group;
		}

		protected void onPostExecute(List<Group> result) {
			// ((LinkedList<Group>)
			// user_group).addFirst("Added after refresh...");
			adapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
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
	}// end async task

	public void accessWebService() {
		// create the json data
		JsonObjectData task = new JsonObjectData();
		task.execute(new String[] { url });
	}

	public void DisplayData() {

		try {
			user_group.clear();
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
				String image_no = jsonChildNode.optString("image_no");
				String pos = jsonChildNode.optString("postCode");
				String lan = jsonChildNode.optString("language");
				user_group.add(new Group(groupId, adminId, eventName, location,
						pos, time, des, ageGroup, sendImage(image_no),
						lan));
			}

		//	BubbleSort(user_group);
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(),
					"Failed to display data! " + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

	}

	public static void BubbleSort(List<Group> list) {
		Group a;

		for (int i = list.size() - 1; i > 0; i--) {

			for (int j = 0; j < i; j++) {

				if (list.get(j).language.toUpperCase()
						.compareTo(list.get(j + 1).language.toUpperCase()) > 0) {
					a = list.get(j);

					list.set(j, list.get(j + 1));
					list.set(j + 1, a);

				}
			}

		}

	}

}