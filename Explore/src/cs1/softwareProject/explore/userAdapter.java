package cs1.softwareProject.explore;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class userAdapter extends ArrayAdapter<userObject> {

	private Context context;
	private List<userObject> objects;
	
	
	public userAdapter(Context context, int resource, List <userObject> objects) {
		super(context, resource,objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.objects = objects;
		
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		userObject c = objects.get(position);
		
		LayoutInflater layin = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		View view = layin.inflate(R.layout.user_item, null);
		
		ImageView image = (ImageView) view.findViewById(R.id.hello);
		image.setImageResource(c.image);
		
		TextView tv = (TextView) view.findViewById(R.id.hello2);
		tv.setText(c.username);
		
		TextView tv1 = (TextView) view.findViewById(R.id.hello3);
		tv1.setText(c.interest);
		
		
		return view;		
		
	}

}