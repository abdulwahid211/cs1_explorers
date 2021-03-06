package cs1.softwareProject.explore;



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
import org.json.JSONException;
import org.json.JSONObject;







import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

@SuppressLint("NewApi") public class femaleImage extends Activity {
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private String jsonResult;
	String imgString;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String LOGIN_URL = "http://doc.gold.ac.uk/~ma301ma/IgorFile/userImage.php";
	
	
	

	//Creating three arrays with different images for the different sections of the image.
	int hatSection[] = {R.drawable.female_hair_0 , R.drawable.female_hair1, R.drawable.female_hair2, R.drawable.female_hair3, R.drawable.female_hair4, R.drawable.female_hair5};
	
	int glassesSection[] = {R.drawable.empty_image, R.drawable.female_glass1, R.drawable.female_glass2, R.drawable.female_glass3, R.drawable.female_glass4};
	
	//Colour for the faces. White and black.
	int wholeSection[] = {R.drawable.female_white_face, R.drawable.female_black_face};
	
	//int variables to increment the arrays.
	int hatIncrement = -1;
	int glassesIncrement = 0;
	int beardIncrement = -1;
	int wholeIncrement = 0;
	
	int imageSections[] = new int[4];
	
	//This ImageView will display the whole image.
	ImageView wholeImage;
	
	//ImageView for the hat.
	ImageView hatImage;
	
	//ImageView for the glasses.
	ImageView glassesImage;
	
	//This bitmap is for the whole image.
	Bitmap bmp;
	
	//Creating image buttons for the forward and back image buttons.
	ImageButton hatForward;
	ImageButton hatBackwards;
		
	//Creating more image buttons for the forward and back image buttons.
	ImageButton imageSectionForward;
	ImageButton imageSectionBackwards;
		
	
	//Bitmaps for each part of the image.
	Bitmap hatBit;
	Bitmap glassesBit;
		
	//String for sending the image to the php file.
	String image_str=null;

	//Button for the next page.
	Button nextButton;
	
	Button buttonOne;
	Button buttonTwo;
	Button buttonThree;
	
	ImageView black;
	
	//Initialising to black to prevent an image from turning transparent from pressing an image without clicking a coloured squared button. 
	private int getColour = Color.BLACK;
	
	
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
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.femaleface);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Customise your female image");
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(210, 120, 2)));
		
		//Getting the id of the ImageView and placing it within the wholeImage ImageView.
		wholeImage = (ImageView) findViewById(R.id.overallImage);
		

		//Getting the id of the ImageView and placing it within the wholeImage ImageView.
		wholeImage = (ImageView) findViewById(R.id.overallImage);
		
		//Getting the id of the ImageView and placing it within the hatImage ImageView.
		hatImage = (ImageView) findViewById(R.id.hatChanger);

		
		//Getting the id of the ImageView and placing it within the glassesImage ImageView.
		glassesImage = (ImageView) findViewById(R.id.glassesChanger);
		
		
		//Getting the id from the xml file.
		/*hatForward = (ImageButton) findViewById(R.id.hatForward);
		
		//Getting the id from the xml file.
		hatBackwards = (ImageButton) findViewById(R.id.hatBack);*/
		
		//Getting the id from the xml file.
		imageSectionForward = (ImageButton) findViewById(R.id.forwardButton);
		
		//Getting the id from the xml file.
		imageSectionBackwards = (ImageButton) findViewById(R.id.backButton);
		
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
        
        /*colourSpectrum[0] = (ImageView) findViewById(R.id.colourSpectrum);
        colourSpectrum[1] = (ImageView) findViewById(R.id.colourSpectrum1);
        colourSpectrum[2] = (ImageView) findViewById(R.id.colourSpectrum2);
        colourSpectrum[3] = (ImageView) findViewById(R.id.colourSpectrum3);
        colourSpectrum[4] = (ImageView) findViewById(R.id.colourSpectrum4);
        colourSpectrum[5] = (ImageView) findViewById(R.id.colourSpectrum5);*/
        
		//Calling the function that displays the image chosen by the user.
		displayImage();
		

       // overLay(bmp, hatBit);
		
		
		buttonTwo();
		buttonOne();
		buttonThree();
		
		//Sending the image to the database.
		sendImage();
		
		//colourSpectrum();
		
		/*colourBlack();
		colourBlue();
		colourGreen();
		colourRed();
		colourWhite();
		colourYellow();*/
		
		//Functions for the coloured squared images and getting their colours.
		/*blackColourTouch();
		greenColourTouch();
		whiteColourTouch();
		yellowColourTouch();
		redColourTouch();
		blueColourTouch();*/
		
		//Functions to allow for the changing of the colours for the different sections of the image.
		//hatColourChanger();
		//glassesColourChanger();
		
		//The forward and backwards buttons will initially be set to false.
		imageSectionForward.setEnabled(false);
		imageSectionBackwards.setEnabled(false);
		
	}//End of onCreate() function.
	
	public void overLay(Bitmap overall, Bitmap hair)
	{
		// Canvas canvas = new Canvas(overall);
		 
		// Bitmap bmOverlay = Bitmap.createBitmap(overall.getWidth(), overall.getHeight(), overall.getConfig());
		 
	        Canvas canvas = new Canvas(overall);
	       // canvas.drawBitmap(overall, new Matrix(), null);
	       // canvas.drawBitmap(hair,0,0, null);
	       
	        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
	        canvas.drawBitmap(hair, 0, 0, paint);
	}
	
	public void displayImage()
	{
		
		Bundle extras = getIntent().getExtras();
		byte[] byteArray = extras.getByteArray("femaleimage");

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
					
				    //The forward and backwards buttons will be set to true once a button is pressed.
				    imageSectionForward.setEnabled(true);
				    imageSectionBackwards.setEnabled(true);
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
					
					//The forward and backwards buttons will be set to true once a button is pressed.
				    imageSectionForward.setEnabled(true);
				    imageSectionBackwards.setEnabled(true);
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
					
					//The forward and backwards buttons will be set to true once a button is pressed.
				    imageSectionForward.setEnabled(true);
				    imageSectionBackwards.setEnabled(true);
					
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
						hatIncrement = 5;
					}
					
					//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
					
					hatBit = BitmapFactory.decodeResource(getResources(), hatSection[hatIncrement]);
					//hatBit = BitmapFactory.decodeResource(getResources(), R.drawable.female_white_face)
							//female_hair_0, female_hair1, female_hair2, female_hair3, female_hair4, female_hair5
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
					
					if(glassesIncrement >= glassesSection.length)
					{
						glassesIncrement = 4;
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
					glassesIncrement--;
					
					if(glassesIncrement < 0)
					{
						glassesIncrement = 0;
					}
							
					//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
					
							
					glassesBit = BitmapFactory.decodeResource(getResources(), glassesSection[glassesIncrement]);
					
					glassesImage.setImageBitmap(glassesBit);
				}
				
			});
			
		}//End of function.
		
		public void buttonThreeForward()
		{
			imageSectionForward.setOnClickListener(new View.OnClickListener() 
			{				

				public void onClick(View v) 
				{
					wholeIncrement++;
					
					if(wholeIncrement >= wholeSection.length)
					{
						wholeIncrement = 1;
					}
							
					//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
				
							
					bmp = BitmapFactory.decodeResource(getResources(), wholeSection[wholeIncrement]);
					wholeImage.setImageBitmap(bmp);
				}
				
			});
			
		}//End of function.
		
		public void buttonThreeBack()
		{
			
			
			imageSectionBackwards.setOnClickListener(new View.OnClickListener() 
			{				

				public void onClick(View v) 
				{

					wholeIncrement--;
					
					if(wholeIncrement < 0)
					{
						wholeIncrement = 0;
					}
							
					//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
				
							
					bmp = BitmapFactory.decodeResource(getResources(), wholeSection[wholeIncrement]);
					wholeImage.setImageBitmap(bmp);
				}
				
			});
			
		}//End of function.
		
		
		//------------Functions for displaying the coloured squared images begins.--------------//
				public void colourBlack()
				{
					Bitmap blackColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
					Bitmap resizedbitmap=  Bitmap.createBitmap(blackColour, 0, 0, 200, 200);
					
					//colourSpectrum[0].setImageBitmap(Bitmap.createScaledBitmap(black, 100, 100, false));
					//colourSpectrum[0].setImageBitmap(black);
					colourSpectrum[0].setImageBitmap(resizedbitmap);
				}
				
				public void colourGreen()
				{
					Bitmap greenColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
					Bitmap resizedbitmap1=  Bitmap.createBitmap(greenColour, 150, 0, 120, 200);
					
					colourSpectrum[1].setImageBitmap(resizedbitmap1);
				}
				
				public void colourBlue()
				{
					Bitmap blueColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
					
					Bitmap resizedbitmap5 =  Bitmap.createBitmap(blueColour, 650, 0, 120, 200);
					
					colourSpectrum[5].setImageBitmap(resizedbitmap5);
				}
				
				public void colourRed()
				{
					Bitmap redColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
					
					Bitmap resizedbitmap4 =  Bitmap.createBitmap(redColour, 520, 0, 120, 200);
					
					colourSpectrum[4].setImageBitmap(resizedbitmap4);
				}
				
				public void colourWhite()
				{
					Bitmap whiteColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
					
					Bitmap resizedbitmap2=  Bitmap.createBitmap(whiteColour, 300, 0, 120, 200);
					
					colourSpectrum[2].setImageBitmap(resizedbitmap2);
				}
				
				public void colourYellow()
				{
					Bitmap yellowColour = BitmapFactory.decodeResource(getResources(), R.drawable.colourspectrum);
					
					Bitmap resizedbitmap3 =  Bitmap.createBitmap(yellowColour, 400, 0, 120, 200);
					
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
	        		colourName.setText("Colour: Black");
	        		
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
	        		colourName.setText("Colour: Green");
	        		
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
	        		colourName.setText("Colour: White");
	        		
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
	        		colourName.setText("Colour: Yellow");
	        		
	        		
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
	        		colourName.setText("Colour: Red");
	        		
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
	        		colourName.setText("Colour: Blue");
	        		
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

		
		//Function to change the colour of the hair images.
		public void hatColourChanger()
		{
			
			hatImage.setOnTouchListener(new ImageView.OnTouchListener()
	        {
	            
				@Override
				public boolean onTouch(View v, MotionEvent event) 
				{
					
					int x = (int)event.getX();
		            int y = (int)event.getY();
		            
						
		            if(v == hatImage)
					{
		            	if(hatBit.getPixel(x, y) == Color.TRANSPARENT)
		            	{
		            		f.Fill(hatBit, new Point(x, y), hatBit.getPixel(x, y), Color.TRANSPARENT);
		            		
		            		System.out.println(x + "  " + y);
		            	}
		            	
		            	else
		            	{
		            		f.Fill(hatBit, new Point(x, y), hatBit.getPixel(x, y), getColour());
		            		System.out.println("Pressing the hat.");
		            	}
					}
					hatImage.setImageBitmap(hatBit);
					
					System.out.println("Pressed it so gooddddd.");
					return false;
					
				}
				
	        });
			
		}//End of function.
		
		
		

		//Function to change the colour of the glasses images.
		public void glassesColourChanger()
		{
			//glassesBitReplacement = glassesBit;
			glassesImage.setOnTouchListener(new ImageView.OnTouchListener()
	        {
	            
				@Override
				public boolean onTouch(View v, MotionEvent event) 
				{
					
					int x = (int)event.getX();
		            int y = (int)event.getY();
		           
						
		          
		            if(glassesBit.getPixel(x, y) == Color.TRANSPARENT)
	            	{
	            		f.Fill(glassesBit, new Point(x, y), glassesBit.getPixel(x, y), Color.TRANSPARENT);
	            		
	            		System.out.println(x + "  " + y);
	            	}
		            
		            else
		            {
		            	f.Fill(glassesBit, new Point(x, y), glassesBit.getPixel(x, y), getColour());
		            	// glassesBit.setPixel(x, y, Color.BLACK);
		            }
		            
					System.out.println("Pressed itsss so gooddddd.");
		            glassesImage.setImageBitmap(glassesBit);
					
					return false;
					
				}
				
	        });
			
		}//End of function.
		

	
		  
		
		public Bitmap combineImages(Bitmap c, Bitmap s, Bitmap a) {
		    Bitmap cs = null; 

		    int width, height = 0; 

		    if(c.getHeight() > s.getHeight()) {
		      width = c.getWidth() /*+ s.getWidth()*/; 
		      height = c.getHeight() + s.getHeight(); 
		    } else { 
		      width = s.getWidth() + s.getWidth(); 
		      height = c.getHeight(); 
		    } 

		    cs = Bitmap.createBitmap(1400,1400, Bitmap.Config.ARGB_8888);

		    Canvas comboImage = new Canvas(cs);

		    comboImage.drawBitmap(c, 10f,  -15f, null); 
		    comboImage.drawBitmap(s, 270f, 250f, null);
		    comboImage.drawBitmap(a, 370f, 470f, null);

		    return cs; 
		  } 
		
		public Bitmap sendCombinedImages()
		{
			//These if statements are for if the user decides not to customise their image.
			if(hatBit == null)
			{
				hatBit = BitmapFactory.decodeResource(getResources(), R.drawable.empty_image);
			}
			if(glassesBit == null)
			{
				glassesBit = BitmapFactory.decodeResource(getResources(), R.drawable.empty_image);
			}
			
			return combineImages(bmp, hatBit, glassesBit);
		}
		public byte[] getBytesFromBitmap(Bitmap bitmap) {
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bitmap.compress(CompressFormat.PNG, 70, stream);
		    return stream.toByteArray();
		}
		
		public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
		    int width = bm.getWidth();
		    int height = bm.getHeight();
		    float scaleWidth = ((float) newWidth) / width;
		    float scaleHeight = ((float) newHeight) / height;
		    // CREATE A MATRIX FOR THE MANIPULATION
		    Matrix matrix = new Matrix();
		    // RESIZE THE BIT MAP
		    matrix.postScale(scaleWidth, scaleHeight);

		    // "RECREATE" THE NEW BITMAP
		    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		    return resizedBitmap;
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
				       
				        imgString = Base64.encodeToString(getBytesFromBitmap(sendCombinedImages()), 
			                       Base64.NO_WRAP);
				        byte [] byte_arr = stream1.toByteArray();
			         image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
			         CreateGroup cs = new CreateGroup();
			         cs.execute();
			         
			       
					
			         Intent intent = new Intent(v.getContext(), Login.class);
					 startActivityForResult (intent, 0);
				}//End of onClick() function.
				
			});//End of submit.setOnClickListener.
			
	}//End of function.

		
		class CreateGroup extends AsyncTask<String, String, String> {


			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(femaleImage.this);
				pDialog.setMessage("Creating a image...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			@Override
			protected String doInBackground(String... args) {

				int success;

				try {

					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("image_no", imgString));
					
					Log.d("request!", "starting");

					// Posting user data to script
					JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
							params);

					// full json response
					Log.d("Login attempt", json.toString());

					// json success element
					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {
						Log.d("Group Created!", json.toString());

					//	finish();
						return json.getString(TAG_MESSAGE);
					} else {
						Log.d("Login Failure!", json.getString(TAG_MESSAGE));
						return json.getString(TAG_MESSAGE);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;

			}

			protected void onPostExecute(String file_url) {
				// dismiss the dialog once product deleted
				pDialog.dismiss();
				if (file_url != null) {
					Toast.makeText(femaleImage.this, file_url, Toast.LENGTH_LONG)
							.show();

				}

			}
		}


}//End of class.
