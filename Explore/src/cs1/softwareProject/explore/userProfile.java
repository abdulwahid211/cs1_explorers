package cs1.softwareProject.explore;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
	int image = intent.getIntExtra("image",0);
	
	
		
    TextView tv = (TextView) findViewById(R.id.userName);	
    tv.setText("Full Name: "+fname+" "+lname);
    
    TextView tv1 = (TextView) findViewById(R.id.textView2);	
    tv1.setText("Nationality: "+nationality);
    
    TextView tv2 = (TextView) findViewById(R.id.textView3);	
    tv2.setText("Age: "+age+" years old");
    
    TextView tv3 = (TextView) findViewById(R.id.occu);	
    tv3.setText("Occupation: "+occupation);
    
    TextView tv4 = (TextView) findViewById(R.id.descrip);	
    tv4.setText("About: "+about);
    
    ImageView im = (ImageView) findViewById(R.id.imageView1);	
    im.setImageResource(image);
    
    getActionBar().setDisplayHomeAsUpEnabled(true);
	
	
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    onBackPressed();
	    return true;
	}
	
	

}
