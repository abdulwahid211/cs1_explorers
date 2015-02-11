package cs1.softwareProject.explorers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupProfile extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupprofile);
		
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
    
    TextView tv3 = (TextView) findViewById(R.id.textView4);	
    tv3.setText(description);
    
    TextView tv4 = (TextView) findViewById(R.id.textView5);	
    tv4.setText(ageGroup);
    
    ImageView im = (ImageView) findViewById(R.id.imageView1);	
    im.setImageResource(image);
    
    
	
	}
	
	
	
	
	

}
