package cs1.softwareProject.explorers;

/* Reference: http://www.technotalkative.com/android-tab-bar-example-1/
 * 
 * 
 * 
 * 
 * 
 */


import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
        intent = new Intent().setClass(this, ThirdActivity.class);
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


	public void onTabChanged(String tabId ) {
		TabHost tabhost = getTabHost() ;
		if(tabId.equals("Second")){
		tabhost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#66CCFF")); //unselected
		}
		
	}
	
}