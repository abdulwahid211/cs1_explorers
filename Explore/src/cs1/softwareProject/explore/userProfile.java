package cs1.softwareProject.explore;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class userProfile extends Activity {
	
	
	@SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userprofile);
		
	Intent intent = getIntent();
	//String username = intent.getStringExtra("userName");
	String fname = intent.getStringExtra("firstName");
	String lname = intent.getStringExtra("lastName");
	String age = intent.getStringExtra("age");
	String occupation = intent.getStringExtra("occupation");
	String nationality = intent.getStringExtra("nationality");
	String about = intent.getStringExtra("about");
	String image = intent.getStringExtra("image");
	
	
		
	ActionBar actionBar = getActionBar();
	actionBar.setTitle("                 "+fname+" "+lname);
    actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(210, 120, 2)));
    
    TextView tv1 = (TextView) findViewById(R.id.textView2);	
    tv1.setText("               Nationality: "+nationality);
    
    TextView tv2 = (TextView) findViewById(R.id.age);	
    tv2.setText("                Age: "+age);
    
   TextView tv3 = (TextView) findViewById(R.id.occu);	
    tv3.setText("                Occupation: "+occupation);
    
    TextView tv4 = (TextView) findViewById(R.id.about);	
    tv4.append("About: "+about);
    
    
    
    ImageView im = (ImageView) findViewById(R.id.imageView1);	
    byte[] decodedByte = Base64.decode(image , Base64.DEFAULT);
	Bitmap bit = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    
	im.setImageBitmap(bit);
    
    getActionBar().setDisplayHomeAsUpEnabled(true);
	
	
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    onBackPressed();
	    return true;
	}
	
	

}
