package cs1.softwareProject.explore;
// Reference:http://www.mkyong.com/android/android-spinner-drop-down-list-example/
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomOtherCall implements OnItemSelectedListener {

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		if(parent.getItemAtPosition(pos).toString().equals("Other")){
		Toast.makeText(parent.getContext(), 
				"If clicked other, please eneter your language",
				Toast.LENGTH_SHORT).show();
		}
				
	}
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}