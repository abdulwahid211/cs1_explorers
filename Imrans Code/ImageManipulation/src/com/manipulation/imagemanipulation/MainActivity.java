package com.manipulation.imagemanipulation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {
	ImageView mIV;
	ImageView image;
	ImageView imageScale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tintedClick();
		originalClick();
		scaleClick();
		rotateClick();
		
		//Function for the next page.
		nextPage();
	}

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
	
	
	public void imageManipulation(double deg)
	{
	
		//Initialising the imageView variable.
	  	mIV = (ImageView) findViewById(R.id.GreenAlien);
		
				//Storing the image as a bitmap.
				Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				
				//Getting the width and height of the bitmap image.
				int mPhotoWidth = mBitmap.getWidth();
				int mPhotoHeight = mBitmap.getHeight();
				
				
				int[] pix = new int[mPhotoWidth * mPhotoHeight];
				mBitmap.getPixels(pix, 0, mPhotoWidth, 0, 0, mPhotoWidth, mPhotoHeight);
					        
				double angle = (3.14159d * (double)deg) / 180.0d;	        
				int S = (int)(256.0d * Math.sin(angle));	        
				int C = (int)(256.0d * Math.cos(angle));

					int r, g, b, index;
				int RY, BY, RYY, GYY, BYY, R, G, B, Y;

				for (int y = 0; y < mPhotoHeight; y++) {    
				    for (int x = 0; x < mPhotoWidth; x++) {
				        index = y * mPhotoWidth + x;
				        r = (pix[index] >> 16) & 0xff;
				        g = (pix[index] >> 8) & 0xff;
				        b = pix[index] & 0xff;	    	    	
				        RY = (70 * r - 59 * g - 11 * b) / 100;
				        BY = (-30 * r - 59 * g + 89 * b) / 100;
				        Y = (30 * r + 59 * g + 11 * b) / 100;
				        RYY = (S * BY + C * RY) / 256;
				        BYY = (C * BY - S * RY) / 256;
				        GYY = (-51 * RYY - 19 * BYY) / 100;
				        R = Y + RYY;
				        R = (R < 0) ? 0 : ((R > 255) ? 255 : R);
				        G = Y + GYY;
				        G = (G < 0) ? 0 : ((G > 255) ? 255 : G);
				        B = Y + BYY;
				        B = (B < 0) ? 0 : ((B > 255) ? 255 : B);
				        pix[index] = 0xff000000 | (R << 16) | (G << 8) | B;
				    }
				}

				Bitmap bm = Bitmap.createBitmap(mPhotoWidth, mPhotoHeight, Bitmap.Config.ARGB_8888);
				bm.setPixels(pix, 0, mPhotoWidth, 0, 0, mPhotoWidth, mPhotoHeight); 	

				if (null != mBitmap) {
				    mBitmap.recycle();
				}
				mBitmap = bm;

					//Placing the updated bitmap into the main view
					mIV.setImageBitmap(mBitmap);	
				   mIV.invalidate();

				pix = null;

	}
	
	
	 public void tintedClick()
	 {
		Button Tint = (Button) findViewById(R.id.tintedButton);
			
			//Setting up the onClick listener for the Tint button.
		    Tint.setOnClickListener(new View.OnClickListener() {
		    
				
				public void onClick(View v) {
				    //Perform the tinting operation.
			        imageManipulation(60);
					
				}//End of onClick() function.
			});//End of submit.setOnClickListener.
	 }
	 
	 public void originalClick()
	 {
		 Button originalImage = (Button) findViewById(R.id.originalButton);
			
			//Setting up the onClick listener for the originalImage button.
		 originalImage.setOnClickListener(new View.OnClickListener() {
		    
				
				public void onClick(View v) {
					Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
					
			        image = (ImageView) findViewById(R.id.GreenAlien);
			        image.setImageBitmap(originalBitmap);
					
				}//End of onClick() function.
			});//End of submit.setOnClickListener.
	 }
	 
	 public void scaleClick()
	 {
		 Button scaleImage = (Button) findViewById(R.id.scaleButton);
		 
		//Setting up the onClick listener for the originalImage button.
		 scaleImage.setOnClickListener(new View.OnClickListener() {
		    
				
				public void onClick(View v) {
					imageScale = (ImageView) findViewById(R.id.GreenAlien);
					
					//Loading the original bitmap resource. 
					Bitmap scaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
					
					//Applying the scaling to the image.
					//The four parameters are:
					//1-The source bitmap.
					//2-The new bitmap width.
					//3-The new bitmap height.
					//4- Enable whether the source should be filtered.
					Bitmap bMapScaled = Bitmap.createScaledBitmap(scaleBitmap, 150, 100, true);
					
					//Placing the bitmap of bMapScaled within the imageView variable called imageScale.
			        imageScale.setImageBitmap(bMapScaled);
					
				}//End of onClick() function.
			});//End of submit.setOnClickListener.
	 }
	 
	 public void rotateClick()
	 {
		 
		 Button rotateImage = (Button) findViewById(R.id.rotateButton);
		 
		//Setting up the onClick listener for the originalImage button.
		 rotateImage.setOnClickListener(new View.OnClickListener() {
		    
				
				public void onClick(View v) {
					
				//Initialising the imageView variable.
				imageScale = (ImageView) findViewById(R.id.GreenAlien);
				
				//Create a bitmap resource, rotateBitmap, containing the original bitmap.  
				Bitmap rotateBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				
				//Create a new instance of the Matrix class.
				Matrix mat = new Matrix();
				
				//Use the postRotate() method of the Matrix instance to set the amount of rotation in degrees. 
				mat.postRotate(90);
				
				//This instance of createBitmap() expects the original bitmap, starting X, starting Y, ending X, ending Y, Matrix translation, true to enable filters as arguments.
				Bitmap bMapRotate = Bitmap.createBitmap(rotateBitmap, 0, 0, rotateBitmap.getWidth(), rotateBitmap.getHeight(), mat, true);
					
				//We now set the imageView variable to display the new version of the bitmap image.
				imageScale.setImageBitmap(bMapRotate);
				}//End of onClick() function.
			});//End of submit.setOnClickListener.
		 
	 }//End of function.
	 
	 public void nextPage()
	 {
		 Button nextPage = (Button) findViewById(R.id.nextPage);
		 
		 nextPage.setOnClickListener(new OnClickListener() 
		 {
			 public void onClick(View v)
			 {
				 Intent intent = new Intent(v.getContext(), newPage.class);
				 startActivityForResult(intent, 0);
			 }
		 });
	 }
	 
	 
}
