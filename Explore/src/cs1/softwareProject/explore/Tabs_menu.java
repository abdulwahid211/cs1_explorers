package cs1.softwareProject.explore;

/* Reference: http://www.technotalkative.com/android-tab-bar-example-1/
 * 
 * 
 * 
 * 
 * 
 */


import com.google.android.gms.maps.GoogleMap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
 

public class Tabs_menu extends TabActivity implements OnTabChangeListener  {
	ActionBar actionBar;
	 public  String real_user ="";
	    public  String real_pass ="";
	View someView;
	 View root;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        Intent i = getIntent();
		String Uname = i.getStringExtra("username");
		String Pss = i.getStringExtra("password");
		setRealUser(Uname);
    	setRealPass(Pss);
        actionBar = getActionBar();
        actionBar.setTitle("                 All Events");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(210, 120, 2)));
       someView = findViewById(R.id.mylayout);
     // Find the root view
    //   root = someView.getRootView();

        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent [] = new Intent[3];
 
       // first tab
        intent[0] = new Intent().setClass(this, showGroup.class);
        spec = tabHost.newTabSpec("First").setIndicator("First")
                .setContent(intent[0]);
        
        tabHost.addTab(spec);
        
        /*
        spec = tabHost.newTabSpec("First").setIndicator(
        		null,getResources().getDrawable(R.drawable.ic_launcher)).setContent(intent);
        		*/
       
        //Third tab
        
        intent[1] = new Intent().setClass(this, createGroup.class);
        spec = tabHost.newTabSpec("Second").setIndicator("Second")
                      .setContent(intent[1]);
        tabHost.addTab(spec);
     
        
        intent[2] = new Intent().setClass(this, createGroup.class);
        spec = tabHost.newTabSpec("Third").setIndicator("Third")
                      .setContent(intent[2]);
        tabHost.addTab(spec);
        
        
 
        /*
        intent = new Intent().setClass(this, FourthActivity.class);
        spec = tabHost.newTabSpec("Fourth").setIndicator("Fourth")
                      .setContent(intent);
        tabHost.addTab(spec);
        
        */
     //  setTabColor( tabHost) ;
     //  tabHost.setCurrentTab(2);
       tabHost.setOnTabChangedListener(this);
        
        
    }
    
    public  void setRealUser(String a){
		real_user =a;
	}
	public  void setRealPass(String a){
		real_pass =a;
	}
	public String getRealUser(){
		return real_user;
	}
	public String getRealPass(){
		return real_pass;
	}
    
    @Override
    protected void onPause() {
    	
    	super.onPause();
    	
    }

    
    /*
    public static void setTabColor(TabHost tabhost) {
        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++) {
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(R.drawable.bac_tab); //unselected
        }
    }
    
    */
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mapTypeNormal:
			ExploreMap.eMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.mapTypeSatellite:
			ExploreMap.eMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.mapTypeTerrain:
			ExploreMap.eMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		case R.id.mapTypeHybrid:
			ExploreMap.eMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@SuppressLint("NewApi") public void onTabChanged(String tabId ) {
		@SuppressWarnings("deprecation")
		TabHost tabhost = getTabHost() ;
		if(tabId.equals("First")){
	//	tabhost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#66CCFF")); //unselected
		actionBar.setTitle("                 All Events");
		
		someView.setBackgroundColor(Color.parseColor("#FBAD47"));
		}
		if(tabId.equals("Second")){
		//	tabhost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#66CCFF")); //unselected
			actionBar.setTitle("                     Map");
			someView.setBackgroundColor(Color.parseColor("#FBAD47"));
			}
		if(tabId.equals("Third")){
		//	tabhost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#66CCFF")); //unselected
			//root.setBackgroundColor(getResources().getColor(Color.parseColor("#FFFFFF")));
			
			actionBar.setTitle("              Create an Event");
			someView.setBackgroundColor(Color.parseColor("#FBAD47"));
		}
		
	}
	
}