package cs1.softwareProject.explorers;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
public class SecondActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_test);
 
        TextView txtView = (TextView) findViewById(R.id.txtDisplayedTab);
        txtView.setText("Create the user event");
    }
}