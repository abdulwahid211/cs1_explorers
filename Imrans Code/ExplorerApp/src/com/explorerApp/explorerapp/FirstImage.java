package com.explorerApp.explorerapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;



public class FirstImage extends Activity {
	
	//Creating three arrays with different images for the different sections of the image.
	int hatSection[] = {2130837567, 2130837569, 2130837568, 2130837566};
	
	int glassesSection[] = {2130837565};
	
	int beardSection[] = {2130837564};
	
	//int variables to increment the arrays.
	int hatIncrement = -1;
	int glassesIncrement = -1;
	int beardIncrement = -1;
	
	int imageSections[] = new int[4];
	
	//This ImageView will display the whole image.
	ImageView wholeImage;
	
	//ImageView for the hat.
	ImageView hatImage;
	
	//ImageView for the glasses.
	ImageView glassesImage;
	
	//ImageView for the beard.
	ImageView beardImage;
	
	//This bitmap is for the whole image.
	Bitmap bmp;
	
	//Creating image buttons for the forward and back image buttons.
	ImageButton hatForward;
	ImageButton hatBackwards;
		
	//Creating more image buttons for the forward and back image buttons.
	ImageButton imageSectionForward;
	ImageButton imageSectionBackwards;
		
	//Creating more image buttons for the forward and back image buttons.
	ImageButton beardForward;
	ImageButton beardBackwards;
	
	//Bitmaps for each part of the image.
	Bitmap hatBit;
	Bitmap glassesBit;
	Bitmap beardBit;
		
	//String for sending the image to the php file.
	String image_str=null;

	//Button for the next page.
	Button nextButton;
	
	Button buttonOne;
	Button buttonTwo;
	Button buttonThree;
	
	ImageView black;
	
	private int getColour = 0;
	
	ImageView colourSpectrum[] = new ImageView[6];
	
	private String colourArray[] = {"BLACK" , "#f4a460", "WHITE", "RED", "GREEN", "CYAN", "MAGENTA", "YELLOW"}; 
	
	private int colour[] = {-16777216, -16711936, -1, -256, -65536, -16776961};
	TextView colourName;
	
	  //Colours of the images once they have been pressed.
	 /* int colourBlack = 0;
	  int colourBlue = 0;
	  int colourGreen = 0;
	  int colourRed = 0;
	  int colourWhite = 0;
	  int colourYellow = 0;*/
	  
	  //private int colourFunction[] = 

	  Point p1 = new Point();
	  floodFill f = new floodFill();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstimage);
		
		//Getting the id of the ImageView and placing it within the wholeImage ImageView.
		wholeImage = (ImageView) findViewById(R.id.overallImage);
		
		//Getting the id of the ImageView and placing it within the hatImage ImageView.
		hatImage = (ImageView) findViewById(R.id.hatChanger);
		
		//Getting the id of the ImageView and placing it within the glassesImage ImageView.
		glassesImage = (ImageView) findViewById(R.id.glassesChanger);
		
		//Getting the id of the ImageView and placing it within the wholeImage ImageView.
		beardImage = (ImageView) findViewById(R.id.beardChanger);
		
		//Getting the id from the xml file.
		/*hatForward = (ImageButton) findViewById(R.id.hatForward);
		
		//Getting the id from the xml file.
		hatBackwards = (ImageButton) findViewById(R.id.hatBack);*/
		
		//Getting the id from the xml file.
		imageSectionForward = (ImageButton) findViewById(R.id.glassesForward);
		
		//Getting the id from the xml file.
		imageSectionBackwards = (ImageButton) findViewById(R.id.glassesBack);
		
		//colourSpectrum = (ImageView) findViewById(R.id.colourSpectrum);
		
		//Getting the id from the xml file.
	/*	beardForward = (ImageButton) findViewById(R.id.beardForward);
		
		//Getting the id from the xml file.
		beardBackwards = (ImageButton) findViewById(R.id.beardBack);*/
		
		//Creating a variable for the submit button, and retrieving an id from the xml file.
        nextButton = (Button) findViewById(R.id.nextButton);
        
        buttonOne = (Button) findViewById(R.id.ButtonOne);
        buttonTwo = (Button) findViewById(R.id.ButtonTwo);
        buttonThree = (Button) findViewById(R.id.ButtonThree);
        
        colourName = (TextView) findViewById(R.id.colourName);
        
        colourSpectrum[0] = (ImageView) findViewById(R.id.colourSpectrum);
        colourSpectrum[1] = (ImageView) findViewById(R.id.colourSpectrum1);
        colourSpectrum[2] = (ImageView) findViewById(R.id.colourSpectrum2);
        colourSpectrum[3] = (ImageView) findViewById(R.id.colourSpectrum3);
        colourSpectrum[4] = (ImageView) findViewById(R.id.colourSpectrum4);
        colourSpectrum[5] = (ImageView) findViewById(R.id.colourSpectrum5);
        //colourSpectrum[0] = (ImageView) findViewById(R.id.colourBack);
       /* colourSpectrum[1] = (ImageView) findViewById(R.id.colourBlue);
        colourSpectrum[2] = (ImageView) findViewById(R.id.colourGreen);
        colourSpectrum[3] = (ImageView) findViewById(R.id.colourRed);
        colourSpectrum[4] = (ImageView) findViewById(R.id.colourWhite);
        colourSpectrum[5] = (ImageView) findViewById(R.id.colourYellow);*/
		
		
		//Calling the function that displays the image chosen by the user.
		displayImage();
		
		//Calling the function to change the hat style forward.
		/*hatForward();
		
		//Calling the function to change the hat style backwards.
		hatBackwards();
		
		//Calling the function to change the glasses style forwards.
		glassesForward();
		
		//Calling the function to change the beard style forwards.
		beardForward();*/
		
		buttonOne();
		buttonTwo();
		buttonThree();
		
		//Sending the image to the database.
		sendImage();
		
		//colourSpectrum();
		
		colourBlack();
		colourBlue();
		colourGreen();
		colourRed();
		colourWhite();
		colourYellow();
		
		//Functions for the images and getting their colours.
		blackColourTouch();
		greenColourTouch();
		whiteColourTouch();
		yellowColourTouch();
		redColourTouch();
		blueColourTouch();
		
		hatColourChanger();
		
	}//End of onCreate function.
	
	public void displayImage()
	{
		
		Bundle extras = getIntent().getExtras();
		byte[] byteArray = extras.getByteArray("firstImage");

		 bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		 
		 wholeImage.setImageBitmap(bmp);
		 
	}//End of function.
	
	
	//Function to mask.
	//------------------Next is to create the forward, back, etc functions.----------------//
	public void masking(Bitmap bit, int imageMaskNumber, ImageView image /* int first_image_type*/)
	{
			Bitmap original = bit;
			//Bitmap original =  BitmapFactory.decodeResource(getResources(), first_image_type);
			Bitmap mask = BitmapFactory.decodeResource(getResources(), imageMaskNumber);
			Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
		    Canvas mCanvas = new Canvas(result);
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
			mCanvas.drawBitmap(original, 0, 0, null);
			mCanvas.drawBitmap(mask, 0, 0, paint);
			paint.setXfermode(null);
			image.setImageBitmap(result);
			image.setScaleType(ScaleType.CENTER);  
	}
	
	
	
	public void buttonOne()
	{
		buttonOne.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				buttonOneForward();
				buttonOneBack();
				
				buttonOne.setEnabled(false);
				buttonTwo.setEnabled(true);
				buttonThree.setEnabled(true);
				
			}
			
		});
	}//End of function.
	
	public void buttonTwo()
	{
		buttonTwo.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				buttonTwoForward();
				buttonTwoBack();
				
				buttonTwo.setEnabled(false);
				buttonOne.setEnabled(true);
				buttonThree.setEnabled(true);
			}
			
		});
	}//End of function.
	
	public void buttonThree()
	{
		buttonThree.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				buttonThreeForward();
				buttonThreeBack();
				
				buttonThree.setEnabled(false);
				buttonOne.setEnabled(true);
				buttonTwo.setEnabled(true);
				
			}
			
		});
	}//End of function.
	
	public void buttonOneForward()
	{
		imageSectionForward.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				hatIncrement++;
				
				if(hatIncrement >= hatSection.length)
				{
					hatIncrement = 3;
				}
				
				//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
				
				hatBit = BitmapFactory.decodeResource(getResources(), hatSection[hatIncrement]);
				
				hatImage.setImageBitmap(hatBit);
			}
			
		});
	}//End of function.
	
	public void buttonOneBack()
	{
		imageSectionBackwards.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				hatIncrement--;
						
				if(hatIncrement < 0)
				{
					hatIncrement = 0;
				}
						
				//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
			
				
				hatBit = BitmapFactory.decodeResource(getResources(), hatSection[hatIncrement]);
				
				hatImage.setImageBitmap(hatBit);
			}
			
		});
		
	}//End of function.
	
	public void buttonTwoForward()
	{
		imageSectionForward.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				glassesIncrement++;
				
				if(glassesIncrement >= 0)
				{
					glassesIncrement = 0;
				}
						
				//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
				
						
				glassesBit = BitmapFactory.decodeResource(getResources(), glassesSection[glassesIncrement]);
				
				glassesImage.setImageBitmap(glassesBit);
			}
			
		});
	}//End of function.
	
	public void buttonTwoBack()
	{
		imageSectionBackwards.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				
			}
			
		});
		
	}//End of function.
	
	public void buttonThreeForward()
	{
		imageSectionForward.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{

				beardIncrement++;
						
				if(beardIncrement >= 0)
				{
					beardIncrement = 0;
				}
						
				//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
			
						
				beardBit = BitmapFactory.decodeResource(getResources(), beardSection[beardIncrement]);
				beardImage.setImageBitmap(beardBit);
			}
			
		});
		
	}//End of function.
	
	public void buttonThreeBack()
	{
		
		
		imageSectionBackwards.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				/*int variableNumber = 0;
				
				variableNumber++;*/
			}
			
		});
		
	}//End of function.
	
	//------------Functions for displaying the coloured squared images begins.--------------//
	public void colourBlack()
	{
		Bitmap blackColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
		Bitmap resizedbitmap=  Bitmap.createBitmap(blackColour, 0, 0, 100, 100);
		
		//colourSpectrum[0].setImageBitmap(Bitmap.createScaledBitmap(black, 100, 100, false));
		//colourSpectrum[0].setImageBitmap(black);
		colourSpectrum[0].setImageBitmap(resizedbitmap);
	}
	
	public void colourGreen()
	{
		Bitmap greenColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
		Bitmap resizedbitmap1=  Bitmap.createBitmap(greenColour, 100, 0, 50, 100);
		
		colourSpectrum[1].setImageBitmap(resizedbitmap1);
	}
	
	public void colourBlue()
	{
		Bitmap blueColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
		
		Bitmap resizedbitmap5 =  Bitmap.createBitmap(blueColour, 300, 0, 50, 100);
		
		colourSpectrum[5].setImageBitmap(resizedbitmap5);
	}
	
	public void colourRed()
	{
		Bitmap redColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
		
		Bitmap resizedbitmap4 =  Bitmap.createBitmap(redColour, 250, 0, 50, 100);
		
		colourSpectrum[4].setImageBitmap(resizedbitmap4);
	}
	
	public void colourWhite()
	{
		Bitmap whiteColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
		
		Bitmap resizedbitmap2=  Bitmap.createBitmap(whiteColour, 150, 0, 50, 100);
		
		colourSpectrum[2].setImageBitmap(resizedbitmap2);
	}
	
	public void colourYellow()
	{
		Bitmap yellowColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
		
		Bitmap resizedbitmap3 =  Bitmap.createBitmap(yellowColour, 200, 0, 50, 100);
		
		colourSpectrum[3].setImageBitmap(resizedbitmap3);
	}
	
	//-----------Functions for displaying the coloured squared images ends.------------//
	
	//----------Functions to detect if the coloured squared images have been pressed begins.-----------//
	public void blackColourTouch()
	{
		
		colourSpectrum[0].setOnTouchListener(new ImageView.OnTouchListener()
        {
            
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				// TODO Auto-generated method stub
				int x = (int)event.getX();
                int y = (int)event.getY();
		
                

        		//Setting the above textView variable to a certain text, in this case it's the colour/index of the array.
        		//So every time this function is called the index and colour shown in the text will change.
        		//So the user can see what colour they are on.
        		colourName.setText("Colour: " + colour[0]);
        		
        		//colourBlack = colour[0];
        		
        		//Placing the function that determines if the squared image has been pressed within this function.
        		onClick(v);
	
			return false;
		
			}
			
        });
		
		//return colourBlack;
		
	}//End of function.
	

	public void greenColourTouch()
	{
		
		colourSpectrum[1].setOnTouchListener(new ImageView.OnTouchListener()
        {
            
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				// TODO Auto-generated method stub
				int x = (int)event.getX();
                int y = (int)event.getY();
		
                

        		//Setting the above textView variable to a certain text, in this case it's the colour/index of the array.
        		//So every time this function is called the index and colour shown in the text will change.
        		//So the user can see what colour they are on.
        		colourName.setText("Colour: " + colour[1]);
        		
        		//colourGreen = colour[1];

        		//Placing the function that determines if the squared image has been pressed within this function.
        		onClick(v);
	
			return false;
		
			}
			
        });
		
		//return colourGreen;
		
	}//End of function.
	

	public void whiteColourTouch()
	{
		
		colourSpectrum[2].setOnTouchListener(new ImageView.OnTouchListener()
        {
            
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				// TODO Auto-generated method stub
				int x = (int)event.getX();
                int y = (int)event.getY();
		
                

        		//Setting the above textView variable to a certain text, in this case it's the colour/index of the array.
        		//So every time this function is called the index and colour shown in the text will change.
        		//So the user can see what colour they are on.
        		colourName.setText("Colour: " + colour[2]);
        		
        		//colourWhite = colour[2];
        		
        		//Placing the function that determines if the squared image has been pressed within this function.
        		onClick(v);
	
			return false;
		
			}
			
        });
		
		//return colourWhite;
		
	}//End of function.
	

	public void yellowColourTouch()
	{
		
		colourSpectrum[3].setOnTouchListener(new ImageView.OnTouchListener()
        {
            
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				// TODO Auto-generated method stub
				int x = (int)event.getX();
                int y = (int)event.getY();
		
                

        		//Setting the above textView variable to a certain text, in this case it's the colour/index of the array.
        		//So every time this function is called the index and colour shown in the text will change.
        		//So the user can see what colour they are on.
        		colourName.setText("Colour: " + colour[3]);
        		
        		
        		//colourYellow = colour[3];
        		
        		//Placing the function that determines if the squared image has been pressed within this function.
        		onClick(v);
	
			return false;
		
			}
			
        });
		
		//return colourYellow;
		
	}//End of function.
	

	public void redColourTouch()
	{
		
		colourSpectrum[4].setOnTouchListener(new ImageView.OnTouchListener()
        {
            
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				// TODO Auto-generated method stub
				int x = (int)event.getX();
                int y = (int)event.getY();
		
                

        		//Setting the above textView variable to a certain text, in this case it's the colour/index of the array.
        		//So every time this function is called the index and colour shown in the text will change.
        		//So the user can see what colour they are on.
        		colourName.setText("Colour: " + colour[4]);
        		
        		//colourRed = colour[4];
        		
        		//Placing the function that determines if the squared image has been pressed within this function.
        		onClick(v);
	
			return false;
		
			}
			
        });
		
		//return colourRed;
		
	}//End of function.
	

	public void blueColourTouch()
	{
		
		colourSpectrum[5].setOnTouchListener(new ImageView.OnTouchListener()
        {
            
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				// TODO Auto-generated method stub
				int x = (int)event.getX();
                int y = (int)event.getY();
		
                

        		//Setting the above textView variable to a certain text, in this case it's the colour/index of the array.
        		//So every time this function is called the index and colour shown in the text will change.
        		//So the user can see what colour they are on.
        		colourName.setText("Colour: " + colour[5]);
        		
        		//colourBlue = colour[5];
        		
        		//Placing the function that determines if the squared image has been pressed within this function.
        		onClick(v);
	
			return false;
		
			}
			
        });
		
		//return colourBlue;
		
	}//End of function.
	
	//----------Functions to detect if the coloured squared images have been pressed ends.-----------//
	
	
	//Function to determine which squared colour image has been pressed.
	public void onClick(View v) 
	{
		//If the black squared colour image is pressed then make the variable equal to the black colour.
	     if(v==colourSpectrum[0]) 
	     {
	    	 	System.out.println("colourSpectrum[0] was pressed.");
	       
	    	 	getColour = colour[0];
	     }
	    
	     //If the green squared colour image is pressed then make the variable equal to green.
	     if(v==colourSpectrum[1]) 
	     {
	    	 	System.out.println("colourSpectrum[1] was pressed.");
	       
	    	 	getColour = colour[1];
	     }
	     
	     //If the white squared colour image is pressed then make the variable equal to white.
	     if(v == colourSpectrum[2])
		 {
		    	System.out.println("colourSpectrum[2] was pressed.");
		    	
		    	getColour = colour[2];
		 }
	     
	     //If the yellow squared colour image is pressed then make the variable equal to yellow.
	     if(v == colourSpectrum[3])
		 {
		    	System.out.println("colourSpectrum[3] was pressed.");
		    	
		    	getColour = colour[3];
		 }
	     
	     //If the red squared colour image is pressed then make the variable equal to red.
	     if(v == colourSpectrum[4])
		 {
		    	System.out.println("colourSpectrum[4] was pressed.");
		    	
		    	getColour = colour[4];
		 }
	    
	    //If the blue squared colour image is pressed then make the variable equal to blue.
	    if(v == colourSpectrum[5])
	    {
	    		System.out.println("colourSpectrum[5] was pressed.");
	    	
	    		getColour = colour[5];
	    }
	}//End of function.
	
	//Getting the colour of the squared image that has been pressed.
	public int getColour()
	{
		return getColour;
	}
	
	
	//Function to change the colour of the hat image.
	public void hatColourChanger()
	{
		
		hatImage.setOnTouchListener(new ImageView.OnTouchListener()
        {
            
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				
				int x = (int)event.getX();
	            int y = (int)event.getY();
	            	
				f.Fill(hatBit, new Point(x, y), hatBit.getPixel(x, y), getColour());	
				
				hatImage.setImageBitmap(hatBit);
				
				System.out.println("Pressed it so gooddddd.");
				return false;
				
			}
			
        });
		
	}//End of function.
	
	
	
	public Bitmap combineImages(Bitmap c, Bitmap s, Bitmap a, Bitmap b) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom 
	    Bitmap cs = null; 

	    int width, height = 0; 

	    if(c.getHeight() > s.getHeight()) { 
	      width = c.getWidth() /*+ s.getWidth()*/; 
	      height = c.getHeight() + s.getHeight(); 
	    } else { 
	      width = s.getWidth() + s.getWidth(); 
	      height = c.getHeight(); 
	    } 

	    cs = Bitmap.createBitmap(600, 700, Bitmap.Config.ARGB_8888); 

	    Canvas comboImage = new Canvas(cs); 

	    comboImage.drawBitmap(c, 0f,  0f, null); 
	    comboImage.drawBitmap(s, 0f, 270, null); 
	    comboImage.drawBitmap(a, 0f, 0f, null);
	    //comboImage.drawBitmap(b, 0f, 0f, null);
	    // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location 
	    /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png"; 

	    OutputStream os = null; 
	    try { 
	      os = new FileOutputStream(loc + tmpImg); 
	      cs.compress(CompressFormat.PNG, 100, os); 
	    } catch(IOException e) { 
	      Log.e("combineImages", "problem combining images", e); 
	    }*/ 

	    return cs; 
	  } 
	
	public Bitmap sendCombinedImages()
	{
		return combineImages(bmp, hatBit, glassesBit, beardBit);
	}
	
	public void sendImage()
	{
		
		//Setting up the onClick listener for the submit button.
         nextButton.setOnClickListener(new View.OnClickListener() {
			InputStream stream = null;
			
			public void onClick(View v) {
				
				/*------------Converting image----------*/
		        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.imageloading); 
				
				//--------Could use this way to send an edited image to the database.-------//
				//So convert the imageView that has been edited to a bitmap.
				//customiseImage.buildDrawingCache();
				//Bitmap bmap = customiseImage.getDrawingCache();
				
				//Or the other way is to place this next part within the onclick function so once this function is
				//pressed, the edited bitmap will be sent to the database.
		        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
		        sendCombinedImages().compress(Bitmap.CompressFormat.PNG, 90, stream1); //compress to which format you want.
		        byte [] byte_arr = stream1.toByteArray();
		         image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
				/*-----------------Ending converting image.---------------*/
				
				//Creating an ArrayList.
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				
				//Storing the string variables we created within the ArrayL
				nameValuePairs.add(new BasicNameValuePair("image",image_str));
				
				//Setting up a connection.
				try
				{
					//Setting up a http client.
					HttpClient httpClient = new DefaultHttpClient();
					
					//Setting up the http post method.
					//Sending the url for the database.
					//Sending the ip address of the localhost.
					//Also adding the php file which connects android with mysql.
					
					//The default ip of the android emulator is 10.0.2.2
					//The ip of a phone will be different.
					HttpPost post = new HttpPost("http://10.0.2.2/ExplorerAppPhp/send_image.php");
					
					//Passing the nameValuePairs ArrayList within the HttpPost.
					post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					
					//Getting the response.
					HttpResponse response = httpClient.execute(post);
					
					//Setting up the entity.
					HttpEntity entity = response.getEntity();
					
					//Setting up content inside and InputStream.
					stream = entity.getContent();
					
					//Displaying a message if the data has been entered in successfully.
					String message = "The data has been entered successfully.";
					
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
					

					Intent intent = new Intent(v.getContext(), PreviewProfile.class);
					startActivityForResult (intent, 0);
					
				}//End of try block.
				catch(IOException clientException)//Catching any exceptions that arise from the try block.
				{
					clientException.printStackTrace();
				}//End of catch block.
				
			}//End of onClick() function.
			
		});//End of submit.setOnClickListener.
		
}//End of function.

	
	
	

}//End of class.
