package cs1.softwareProject.explore;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
	
	
		
	ActionBar actionBar = getActionBar();
	actionBar.setTitle(fname+" "+lname);
    actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(24, 41, 59)));
    
    //TextView tv1 = (TextView) findViewById(R.id.nation);	
    //tv1.setText(nationality);
    
    TextView tv2 = (TextView) findViewById(R.id.age);	
    tv2.setText("Age: "+age+" years old\n\n"+"Occupation: "+occupation+"\n\n"+"Nationality: "+nationality);
    
   // TextView tv3 = (TextView) findViewById(R.id.occu);	
    //tv3.setText("Occupation: "+occupation);
    
    TextView tv4 = (TextView) findViewById(R.id.about);	
    tv4.append(about);
    
    
    
    ImageView im = (ImageView) findViewById(R.id.imageView1);	
    im.setImageResource(image);
    
    getActionBar().setDisplayHomeAsUpEnabled(true);
	
	
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    onBackPressed();
	    return true;
	}
	
	

}
