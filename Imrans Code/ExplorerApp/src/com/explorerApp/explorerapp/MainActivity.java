package com.explorerApp.explorerapp;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements Runnable{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
			rotateImage(100);
		
		//Creating a thread.
		Thread splashPageThread = new Thread()
		{
			public void run()
			{	
				//rotateImage(360);
				try
				{
					//Screen waits for 5000 milliseconds.
					//Waits for 5 seconds before displaying another activity/page.
					sleep(5000);
					
					
					
					Intent mainIntent = new Intent("android.intent.action.MainMenu");
					startActivity(mainIntent);
					
				}//End of try block.
                 catch (InterruptedException e) 
				{
					e.printStackTrace();
				}//End of catch block.
				finally
				{
					//Finish/end the current activity.
					//The finish() function will end this activity before starting a new one.
					finish();
				}//End of finally block.
			}//End of run function.
		};//End of thread function.
		
		//Starting the thread.
		splashPageThread.start();
		
	}//End of onCreate function.
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void rotateImage(int angle)
	{
		
		//Initialising the imageView variable.
		ImageView imageScale = (ImageView) findViewById(R.id.imageLoading);
		
		//Create a bitmap resource, rotateBitmap, containing the original bitmap.  
		Bitmap rotateBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imageloading);
		
		//Create a new instance of the Matrix class.
		Matrix mat = new Matrix();
		
		//int i = 0;
	//	while(i < angle)
		//{
			//i +=10;
		//Use the postRotate() method of the Matrix instance to set the amount of rotation in degrees. 
		mat.postRotate(angle);
		//}
		
		//This instance of createBitmap() expects the original bitmap, starting X, starting Y, ending X, ending Y, Matrix translation, true to enable filters as arguments.
		Bitmap bMapRotate = Bitmap.createBitmap(rotateBitmap, 0, 0, rotateBitmap.getWidth(), rotateBitmap.getHeight(), mat, true);
			
		//We now set the imageView variable to display the new version of the bitmap image.
		imageScale.setImageBitmap(bMapRotate); 
		
		
	}

	@Override
	public void run() {
		
		
	}

	
}
