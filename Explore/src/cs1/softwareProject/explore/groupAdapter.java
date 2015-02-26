package cs1.softwareProject.explore;

import java.util.ArrayList;
import java.util.HashSet;
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

public class groupAdapter extends ArrayAdapter<Group> {

	private Context context;
	private List<Group> objects;
	
	
	public groupAdapter(Context context, int resource, List <Group> objects) {
		super(context, resource,objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.objects = objects;
		
		
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Group c = objects.get(position);
		
		LayoutInflater layin = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		View view = layin.inflate(R.layout.group_item, null);
		
		ImageView image = (ImageView) view.findViewById(R.id.hello);
		image.setImageResource(c.image);
		
		TextView tv = (TextView) view.findViewById(R.id.hello2);
		tv.setText(c.nameOfEvent);
		
		TextView tv1 = (TextView) view.findViewById(R.id.hello3);
		tv1.setText(c.location);
		
		
		return view;		
		
	}

}
