package cs1.softwareProject.explore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
public class PreviewProfile extends Activity {
String email = "";
ImageView showImage;
private String jsonResult;
private String url = "http://doc.gold.ac.uk/~ma301ma/IgorFile/mrX.php";

protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.previewprofile);
showImage = (ImageView) findViewById(R.id.previewImage);

//showImage.setImageResource(a);
accessWebService();
}//End of onCreate function.


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
}// end shit

public void accessWebService() {
	// create the json data
	JsonObjectData task = new JsonObjectData();
	task.execute(new String[] { url });
}

public void DisplayData() {
	
	try {
		String image_no = null;
        
		JSONObject jsonResponse = new JSONObject(jsonResult);
		
		// name of the user group
		JSONArray jsonUserDetails = jsonResponse.optJSONArray("userInfo");
		for (int i = 0; i < jsonUserDetails.length(); i++) {

			JSONObject jsonChildNode = jsonUserDetails.getJSONObject(i);
		
			 image_no = jsonChildNode.optString("image_no");
			
		  
		}
		byte[] decodedByte = Base64.decode(image_no , Base64.DEFAULT);
		Bitmap bit = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
		
		
		showImage.setImageBitmap(bit);
		//showImage.
		

	} catch (JSONException e) {
		Toast.makeText(getApplicationContext(),
				"Failed to display data! " + e.toString(),
				Toast.LENGTH_SHORT).show();
	}
	
}


}//End of class.