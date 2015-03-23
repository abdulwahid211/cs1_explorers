package cs1.softwareProject.explore;
/// Referenced: lynda tutorials Google Map http://www.lynda.com/Android-tutorials/Customizing-marker-info-windows/133347/144384-4.html
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExploreMap extends FragmentActivity {

	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	private static final double lat = 51.5095422;
	private static final double lng = -0.0294027;
	private static final float zoom = 10;
	static GoogleMap eMap;
	Marker marker;
	Marker marker2;
	public ArrayList<Group> t = (ArrayList<Group>) showGroup.user_group;
	HashMap <String,Integer> flagsImg = new HashMap <String,Integer>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StartExploreMap();
		

	}
	
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		 try {
			 t = (ArrayList<Group>) showGroup.user_group;
			UserGeoLocate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void StartExploreMap(){
		setFlagImage();
		if (servicesOK()) {
			setContentView(R.layout.activity_map);

			if (myMap()) {
				Toast.makeText(this, "Explore's Map ready!", Toast.LENGTH_SHORT)
						.show();
				try {
					UserGeoLocate();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//	gotoUserLocation(lat, lng, zoom);
				try {
					goTODefaultLocation();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				eMap.setMyLocationEnabled(true);
			} else {
				Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT)
						.show();
			}

		} else {
			setContentView(R.layout.activity_main);
		}
	}
	
	public void setFlagImage(){
		flagsImg.put("English", R.drawable.english);
		flagsImg.put("French", R.drawable.french);
		flagsImg.put("Spanish", R.drawable.spanish);
		flagsImg.put("Italian", R.drawable.italian);
		flagsImg.put("German", R.drawable.german);
		flagsImg.put("Arabic", R.drawable.arabic);
		flagsImg.put("Portuguese", R.drawable.portoguese);
		flagsImg.put("Hindi", R.drawable.hindi);
		flagsImg.put("Japanese", R.drawable.japanese);
		flagsImg.put("Mandarin", R.drawable.mandarin);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean servicesOK() {
		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		} else {
			Toast.makeText(this, "Can't connect to Google Play services",
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	private boolean myMap() {
		if (eMap == null) {
			SupportMapFragment myMapfrag = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			eMap = myMapfrag.getMap();
			
			
			if (eMap != null) {
				eMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
					
					@Override
					public View getInfoWindow(Marker arg0) {
						return null;
					}
					
					@Override
					public View getInfoContents(Marker marker) {
						View v = getLayoutInflater().inflate(R.layout.info_window, null);
					
					
						TextView tvName = (TextView) v.findViewById(R.id.tv_name);
						TextView tvDes = (TextView) v.findViewById(R.id.tv_des);
						
						tvName.setText(marker.getTitle());
						tvDes.setText(marker.getSnippet());
						
						return v;
					}
					
				});
			}	
		}
		return (eMap != null);
	}

	public void gotoUserLocation(double lat, double lon, float zoom) {
		LatLng ll = new LatLng(lat, lon); // create longtitude and latitutde
											// object
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom); // update
																			// the
																			// camera
		eMap.moveCamera(update); // move around

	}

	// find the location
	public void geoLocate(View v) throws IOException {
		hideSoftKeyboard(v);
		float zoom2 = 14;
		EditText et = (EditText) findViewById(R.id.editText1);
		String location = et.getText().toString();

		if (location.length() == 0) {
			Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		Geocoder gc = new Geocoder(this);
		// 1 represents single address
		List<android.location.Address> list = gc.getFromLocationName(location,
				1);
		android.location.Address add = list.get(0);
		String locationName = add.getLocality();
		//String country = add.getCountryName();
		//String postCode = add.getPostalCode();
		Toast.makeText(this, location, Toast.LENGTH_LONG).show();

		double lat1 = add.getLatitude();
		double lng1 = add.getLongitude();

		gotoUserLocation(lat1, lng1, zoom2);
		


	}
	
	public void goTODefaultLocation() throws IOException {
		
		
		String location = "London";

		Geocoder gc = new Geocoder(this);
		// 1 represents single address
		List<android.location.Address> list = gc.getFromLocationName(location,
				1);
		android.location.Address add = list.get(0);
		
		Toast.makeText(this, location, Toast.LENGTH_LONG).show();

		double lat1 = add.getLatitude();
		double lng1 = add.getLongitude();

		gotoUserLocation(lat1, lng1, zoom);
		


	}

	public void UserGeoLocate() throws IOException {

		for (int i = 0; i < t.size(); i++) {

			String postCode = t.get(i).postCode;
			String name = t.get(i).nameOfEvent;
			String language = t.get(i).language;
			int imageFlag = R.drawable.other;;
			String location = t.get(i).location;

			if(flagsImg.containsKey(t.get(i).language)){
				imageFlag = flagsImg.get(t.get(i).language);
			}
			
			
			
			
			if (postCode.length() == 0) {
				return;
			}

			Geocoder gc = new Geocoder(this);
			// 1 represents single address
			List<android.location.Address> list = gc.getFromLocationName(
					postCode, 1);
			android.location.Address add = list.get(0);
			// String locationName = add.getLocality();
			// String country = add.getCountryName();
			String postCode1 = add.getPostalCode();

			double lat1 = add.getLatitude();
			double lng1 = add.getLongitude();

			// gotoUserLocation(lat1, lng1, zoom);
			setFlagMarker(name, lat1, lng1, language, location, imageFlag);
			
			

		}
		
	}

	private void hideSoftKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mapTypeNormal:
			eMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.mapTypeSatellite:
			eMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.mapTypeTerrain:
			eMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		case R.id.mapTypeHybrid:
			eMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	private void setFlagMarker(String nameEvent, double lat, double lng,
			String country, String locationName, int flagImage) {
		
		
	
		MarkerOptions userMarker = new MarkerOptions()
				.title(nameEvent)
				.position(new LatLng(lat, lng))
				.icon(BitmapDescriptorFactory
						.fromResource(flagImage));
		if (country.length() > 0) {
			userMarker.snippet(country + "\n" + locationName);
			
		}
		
		marker2 = eMap.addMarker(userMarker);

	}
	

	

}
