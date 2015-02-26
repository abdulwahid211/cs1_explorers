package cs1.softwareProject.explore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class userProfile extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userprofile);
		
	Intent intent = getIntent();
	String username = intent.getStringExtra("userName");
	String interest = intent.getStringExtra("interest");
	String bio = intent.getStringExtra("bio");
	int image = intent.getIntExtra("image",0);
	
	
		
    TextView tv = (TextView) findViewById(R.id.abzy211);	
    tv.setText(username);
    
    TextView tv1 = (TextView) findViewById(R.id.textView2);	
    tv1.setText(interest);
    
    TextView tv2 = (TextView) findViewById(R.id.textView3);	
    tv2.setText(bio);
    
    
    ImageView im = (ImageView) findViewById(R.id.imageView1);	
    im.setImageResource(image);
    
    
	
	}
	
	
	
	
	

}
