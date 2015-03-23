package cs1.softwareProject.explore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
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
		if(position%2==0)
        {
            view.setBackgroundColor(Color.parseColor("#BBC5CC"));
        }
		// image for the group profile
		ImageView image = (ImageView) view.findViewById(R.id.list_image);
		byte[] decodedByte = Base64.decode(c.image , Base64.DEFAULT);
		Bitmap bit = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
		image.setImageBitmap(bit);
		
		
		TextView tv = (TextView) view.findViewById(R.id.title);
		tv.setText(c.nameOfEvent+" ("+c.language+")");
		
		TextView tv1 = (TextView) view.findViewById(R.id.locationname);
		tv1.setText(c.location);
		
		TextView tv2 = (TextView) view.findViewById(R.id.lan);
		tv2.setText(c.postCode);
		
		
		return view;		
		
	}

}