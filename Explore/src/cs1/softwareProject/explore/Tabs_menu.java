package cs1.softwareProject.explore;

/* Reference: http://www.technotalkative.com/android-tab-bar-example-1/
 * 
 * 
 * 
 * 
 * 
 */


import com.google.android.gms.maps.GoogleMap;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
 
@SuppressWarnings("deprecation")
public class Tabs_menu extends TabActivity implements OnTabChangeListener  {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
 
       // first tab
        intent = new Intent().setClass(this, showGroup.class);
        spec = tabHost.newTabSpec("First").setIndicator("First")
                .setContent(intent);
        tabHost.addTab(spec);
        
        /*
        spec = tabHost.newTabSpec("First").setIndicator(
        		null,getResources().getDrawable(R.drawable.ic_launcher)).setContent(intent);
        		*/
       
     
        //second tab
        intent = new Intent().setClass(this, createGroup.class);
        spec = tabHost.newTabSpec("Second").setIndicator("Second")
                      .setContent(intent);
        tabHost.addTab(spec);
        //Third tab
        /*
        intent = new Intent().setClass(this, MainActivity.class);
        spec = tabHost.newTabSpec("Third").setIndicator("Third")
                      .setContent(intent);
        tabHost.addTab(spec);
 
        /*
        intent = new Intent().setClass(this, FourthActivity.class);
        spec = tabHost.newTabSpec("Fourth").setIndicator("Fourth")
                      .setContent(intent);
        tabHost.addTab(spec);
        
        */
     //  setTabColor( tabHost) ;
     //  tabHost.setCurrentTab(2);
     //  tabHost.setOnTabChangedListener(this);
        
        
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
			MainActivity.eMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.mapTypeSatellite:
			MainActivity.eMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.mapTypeTerrain:
			MainActivity.eMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		case R.id.mapTypeHybrid:
			MainActivity.eMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
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


	public void onTabChanged(String tabId ) {
		TabHost tabhost = getTabHost() ;
		if(tabId.equals("Second")){
		tabhost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#66CCFF")); //unselected
		}
		
	}
	
}