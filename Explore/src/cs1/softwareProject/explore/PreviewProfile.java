package cs1.softwareProject.explore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

public class PreviewProfile extends Activity {
	
	String email = "";
	ImageView showImage;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.previewprofile);
		
		showImage = (ImageView) findViewById(R.id.previewImage);
		
		retrieveInformation();
		
	}//End of onCreate function.
	
	public void retrieveInformation()
	{
			    	String result = "";
					InputStream stream = null;
					//JSONObject jArray = null;
							try
							{
								//Setting up a http client.
								HttpClient httpClient = new DefaultHttpClient();
								
								//The default ip of the android emulator is 10.0.2.2
								//The ip of a phone will be different.
								HttpPost post = new HttpPost("http://10.0.2.2/ExplorerAppPhp/retrieve_profile_information.php");
								
								//Getting the response.
								HttpResponse response = httpClient.execute(post);
								
								//Setting up the entity.
								HttpEntity entity = response.getEntity();
								
								stream = entity.getContent();
							}
							catch(Exception e)
							{
								System.out.println("You have a problem.");
							}
							
							//Convert response to string.
							try
							{
								BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "iso-8859-1"), 8);
							    StringBuilder sb = new StringBuilder();
							    String line = null;
							    
							    while((line = reader.readLine()) !=null)
							    {
							    	sb.append(line + "\n");
							    }
							    
							    stream.close();
							    
							    result = sb.toString();
							
							}
							catch(Exception e)
							{
								System.out.println("Problem.");
							}
							//Parsing the json data.
						//	try
						//	{
								
								String s = "";

								String image = "";
								
								//byte[] tmp2 = Base64.decode(result, 0); 
								//String val2 = new String(tmp2, "UTF-8");
							/*	
								JSONArray jArray = new JSONArray(val2);
								
								for(int i = 0; i < jArray.length(); i++)
								{
									JSONObject json = jArray.getJSONObject(i);

									TextView text = (TextView) findViewById(R.id.text);
									
									text.setText(i);
									
									    image = json.getString("ProfileImage");
									    
								}
								*/

								System.out.println("hihihih");
								//System.out.println("I is:" + i);
								//System.out.println("Image has: " + image);
								 
								
								byte[] decodedByte = Base64.decode(result, Base64.DEFAULT);
								Bitmap bit = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
							
                                showImage.setImageBitmap(bit);
								
							
								
							//}
							//catch(/*JSONException*/ IOException  e)
							//{
								//System.out.println("Your extra problem is: " + e);
							//}
						
	}//End of function.

}//End of class.
