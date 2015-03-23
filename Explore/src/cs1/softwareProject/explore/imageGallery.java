package cs1.softwareProject.explore;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;




import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import cs1.softwareProject.explore.userImage.makeImage;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//http://code.tutsplus.com/tutorials/android-sdk-displaying-images-with-an-enhanced-gallery--mobile-11130
public class imageGallery extends Activity {

	// variable for selection intent
	private final int PICKER = 1;
	// variable to store the currently selected image
	private int currentPic = 0;
	// gallery object
	@SuppressWarnings("deprecation")
	private Gallery picGallery;
	// image view for larger display
	private ImageView picView;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	
	private static final String LOGIN_URL = "http://doc.gold.ac.uk/~ma301ma/IgorFile/userImage.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	// adapter for gallery view
	private PicAdapter imgAdapt;

	TextView tv;
	ImageView im;
	
	public static int userImage =0;
	public boolean choosed = false;
	String a;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagepicker);

		// get the large image view
		picView = (ImageView) findViewById(R.id.picture);

		// get the gallery view
		picGallery = (Gallery) findViewById(R.id.gallery);
		tv = (TextView) findViewById(R.id.abz);
		im = (ImageView) findViewById(R.id.wadz);

		picGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				 a = String.valueOf(position);
				tv.setText(a);
				// display the user's image 
				im.setImageResource(imgAdapt.getMyImageList().get(position));
				if(choosed==true){
				setUserImage(position);
				}
			}
		});

		// create a new adapter
		imgAdapt = new PicAdapter(this);

		// set the gallery adapter
		picGallery.setAdapter(imgAdapt);

	}
	
	public static void setUserImage(int a){
		userImage =a;
	}
	public static int getUserImage(){
		return userImage;
	}
	
	public void ChooseImage(View v){
		choosed = true;
		new makeImage(String.valueOf(a)).execute();
		Intent i = new Intent(imageGallery.this, PreviewProfile.class);
		startActivity(i);
		
	}

	public class PicAdapter extends BaseAdapter {

		// use the default gallery background image
		int defaultItemBackground;

		// gallery context
		private Context galleryContext;

		// array to store bitmaps to display
		private Bitmap[] imageBitmaps;

		// placeholder bitmap for empty spaces in gallery
		Bitmap placeholder[];

		ArrayList<Integer> myImageList;

		// ImageView im;

		public PicAdapter(Context c) {

			// instantiate context
			galleryContext = c;

			myImageList = new ArrayList<Integer>();
			myImageList.add(R.drawable.img1);
			myImageList.add(R.drawable.img2);
			myImageList.add(R.drawable.img3);
			myImageList.add(R.drawable.img0);
			// create bitmap array
			imageBitmaps = new Bitmap[myImageList.size()];

			placeholder = new Bitmap[myImageList.size()];

			for (int i = 0; i < myImageList.size(); i++) {

				// decode the placeholder image
				placeholder[i] = BitmapFactory.decodeResource(getResources(),
						myImageList.get(i));
			}
			// more processing

			// set placeholder as all thumbnail images in the gallery initially
			for (int i = 0; i < myImageList.size(); i++) {
				imageBitmaps[i] = placeholder[i];
			}

			// get the styling attributes - use default Andorid system resources
			TypedArray styleAttrs = galleryContext
					.obtainStyledAttributes(R.styleable.PicGallery);

			// get the background resource
			defaultItemBackground = styleAttrs.getResourceId(
					R.styleable.PicGallery_android_galleryItemBackground, 0);

			// recycle attributes
			styleAttrs.recycle();

			// im = (ImageView) findViewById(R.id.wadz);

		}

		public ArrayList<Integer> getMyImageList() {
			return myImageList;
		}

		// return number of data items i.e. bitmap images
		public int getCount() {
			return imageBitmaps.length;
		}

		// return item at specified position
		public Object getItem(int position) {
			return position;
		}

		// return item ID at specified position
		public long getItemId(int position) {
			return position;
		}

		// get view specifies layout and display options for each thumbnail in
		// the gallery
		@SuppressWarnings("deprecation")
		public View getView(int position, View convertView, ViewGroup parent) {

			// create the view
			ImageView imageView = new ImageView(galleryContext);
			// specify the bitmap at this position in the array

			imageView.setImageBitmap(imageBitmaps[position]);

			// increase the image gallery size
			imageView.setLayoutParams(new Gallery.LayoutParams(600, 400));

			// scale type within view area
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			// set default gallery item background
			imageView.setBackgroundResource(defaultItemBackground);
			// return the view
			return imageView;
		}

	}
	
class makeImage extends AsyncTask<String, String, String> {

		int a = R.drawable.arabic;
		String image_no;

		makeImage(String no) {
			this.image_no = no;
          

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(imageGallery.this);
			pDialog.setMessage("Making a Image...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		public byte[] getBytesFromBitmap(Bitmap bitmap) {
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bitmap.compress(CompressFormat.PNG, 70, stream);
		    return stream.toByteArray();
		}

		@Override
		protected String doInBackground(String... args) {

			int success;

			try {
               
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.female_glass4);
				String imgString = Base64.encodeToString(getBytesFromBitmap(largeIcon), 
	                       Base64.NO_WRAP);
				
				
				
				params.add(new BasicNameValuePair("user_image", imgString));
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
				Toast.makeText(imageGallery.this, file_url, Toast.LENGTH_LONG)
						.show();

			}

		}

	}// end shit


}
