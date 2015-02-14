package cs1.softwareProject.explorers;

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
	String location = intent.getStringExtra("location");
	String name = intent.getStringExtra("EventName");
	String time = intent.getStringExtra("time");
	String description = intent.getStringExtra("description");
	String ageGroup = intent.getStringExtra("ageGroup");
	int image = intent.getIntExtra("Image",0);
	
	
		
    TextView tv = (TextView) findViewById(R.id.abzy211);	
    tv.setText(name);
    
    TextView tv1 = (TextView) findViewById(R.id.textView2);	
    tv1.setText(location);
    
    TextView tv2 = (TextView) findViewById(R.id.textView3);	
    tv2.setText(time);
    
    
    ImageView im = (ImageView) findViewById(R.id.imageView1);	
    im.setImageResource(image);
    
    
	
	}
	
	
	
	
	

}
