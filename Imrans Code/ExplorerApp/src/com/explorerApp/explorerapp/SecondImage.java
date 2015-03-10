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
import android.graphics.drawable.BitmapDrawable;
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

public class SecondImage extends Activity {
	

	 floodFill f= new floodFill(); 

	 Point p1 = new Point();
	
	//Creating three arrays with different images for the different sections of the image.
	int secondHatSection[] = {2130837572, 2130837570, 2130837571};
	
	int secondMiddleSection[] = {2130837575, 2130837573, 2130837574};
	
	
	//int variables to increment the arrays.
	int hatIncrement = 0;
	int middleIncrement = 0;
	
	//This ImageView will display the whole image.
	ImageView wholeImage;
	
	//ImageView for the hat.
	ImageView hatImage;
	
	//ImageView for the glasses.
	ImageView middleImage;
	
	//This bitmap is for the whole image.
	Bitmap bmp;
	
	//Creating image buttons for the forward and back image buttons.
	ImageButton hatForward;
	ImageButton hatBackwards;
		
	//Creating more image buttons for the forward and back image buttons.
	ImageButton middleForward;
	ImageButton middleBackwards;
	
	//Bitmaps for each part of the image.
	Bitmap hatBit = null;
	Bitmap middleBit = null;
		
	//String for sending the image to the php file.
	String image_str=null;

	//Button for the next page.
	Button nextButton;
	
	//Buttons for the colour.
	ImageView colourForward;
	ImageView colourBack;

	//Creating an array that holds different colours.
    private String colourArray[] = {"BLACK" , "#f4a460", "WHITE", "RED", "GREEN", "CYAN", "MAGENTA", "YELLOW"}; 
    //Variable to increment the colour array's index.
    private int colourIndex = 0;
   
	//TextView to display the colour.
	TextView colourName;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondimage);
		
		//Getting the id of the ImageView and placing it within the wholeImage ImageView.
		wholeImage = (ImageView) findViewById(R.id.overallImage);
		
		//Getting the id of the ImageView and placing it within the hatImage ImageView.
		hatImage = (ImageView) findViewById(R.id.hatChanger);
		
		//Getting the id of the ImageView and placing it within the glassesImage ImageView.
		middleImage = (ImageView) findViewById(R.id.middleChanger);
		//Getting the id from the xml file.
		hatForward = (ImageButton) findViewById(R.id.hatForward);
		
		//Getting the id from the xml file.
		hatBackwards = (ImageButton) findViewById(R.id.hatBack);
		
		//Getting the id from the xml file.
		middleForward = (ImageButton) findViewById(R.id.middleForward);
		
		//Getting the id from the xml file.
		middleBackwards = (ImageButton) findViewById(R.id.middleBack);
		
		//Creating a variable for the submit button, and retrieving an id from the xml file.
        nextButton = (Button) findViewById(R.id.nextButton);
        
        colourForward = (ImageButton) findViewById(R.id.colourForward);
        colourBack = (ImageButton) findViewById(R.id.colourBack);	
        
        colourName = (TextView) findViewById(R.id.colourName);
        
		//Displaying the first colour of the array straight away.
		colourName.setText("Colour: " + colourArray[0]);
		
		//Calling the function that displays the image chosen by the user.
		displayImage();
		
		//Calling the function to change the hat style forward.
		hatForward();
		
		//Calling the function to change the hat style backwards.
		hatBackwards();
		
		//Calling the function to change the glasses style forwards.
		middleForward();
		
		middleBack();
		
		//Sending the image to the database.
		sendImage();
		
		colourForward();
		colourBack();
		colour();
		
	}//End of onCreate function.
	
	public void displayImage()
	{
		//Getting the image file by using the array.
	  //Bitmap displayImage = BitmapFactory.decodeFile(pImage.getPath());
		//Bitmap displayImage = BitmapFactory.decodeResource(getResources(), pImage.imageArray[pImage.imageNumber]);	
		//System.out.println(pImage.getPath());
		
		Bundle extras = getIntent().getExtras();
		byte[] byteArray = extras.getByteArray("secondImage");

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
	
	
	public void hatForward()
	{
		//Setting up the onClick listener for the forward button.
		hatForward.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				hatIncrement++;
				
				if(hatIncrement >= secondHatSection.length)
				{
					hatIncrement = 2;
				}
				
				//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
				
				hatBit = BitmapFactory.decodeResource(getResources(), secondHatSection[hatIncrement]);
				
				hatImage.setImageBitmap(hatBit);

			}//End of onClick() function.

		});//End of forward.setOnClickListener.
		
	}//End of function.
	
	public void hatBackwards()
	{
		//Setting up the onClick listener for the forward button.
		hatBackwards.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				hatIncrement--;
						
				if(hatIncrement < 0)
				{
					hatIncrement = 0;
				}
						
				//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
			
						
				hatBit = BitmapFactory.decodeResource(getResources(), secondHatSection[hatIncrement]);
				
				hatImage.setImageBitmap(hatBit);

			}//End of onClick() function.

		});//End of forward.setOnClickListener.
				
	}//End of function.
	
	public void middleForward()
	{
		//Setting up the onClick listener for the forward button.
		middleForward.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				middleIncrement++;
						
				if(middleIncrement >= secondMiddleSection.length)
				{
					middleIncrement = 2;
				}
						
				//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
			
			
				
						
				middleBit = BitmapFactory.decodeResource(getResources(), secondMiddleSection[middleIncrement]);
				
				
				middleImage.setImageBitmap(middleBit);

			}//End of onClick() function.

		});//End of forward.setOnClickListener.
				
	}//End of function.
	
	public void middleBack()
	{
		//Setting up the onClick listener for the forward button.
		middleBackwards.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				middleIncrement--;
						
				if(middleIncrement < 0)
				{
					middleIncrement = 0;
				}
						
				//masking(hatBit, 2130837558 , hatImage/* int first_image_type*/);
			
						
				middleBit = BitmapFactory.decodeResource(getResources(), secondMiddleSection[middleIncrement]);
			
				middleImage.setImageBitmap(middleBit);

			}//End of onClick() function.

		});//End of forward.setOnClickListener.
				
	}//End of function.
	
	public Bitmap combineImages(Bitmap c, Bitmap s, Bitmap a/*, Bitmap b*/) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom 
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

	    comboImage.drawBitmap(c, 0f,  100f, null); 
	    comboImage.drawBitmap(s, 0f, 40f, null); 
	    comboImage.drawBitmap(a, -40f, 240, null);
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
		if(hatBit == null)
		{
			hatBit = BitmapFactory.decodeResource(getResources(), R.drawable.empty_image);
		}
		if(middleBit == null)
		{
			middleBit = BitmapFactory.decodeResource(getResources(), R.drawable.empty_image);
		} 
		
		return combineImages(bmp, hatBit, middleBit);
	}
	
	
	 
		//This function will increment the variable each time it gets called, and creates a check, so if the value of the variable called incrementIndex is equal
		//to the colourArray arrays index length, then we reset the incrementIndex back to 0 so we don't get an array index out of bounds exception.
		public void colourIncrementation()
		{	
			//Incrementing the variables by one each time this function gets called.
			colourIndex++;
			
			//If the value of the variable called incrementIndex is equal
			//to the colourArray arrays index length, then we reset the incrementIndex back to 0 so we don't get an array index out of bounds exception.
			if(colourIndex > 7)
			{
				colourIndex = 7;
			}
			
			//Setting the above textView variable to a certain text, in this case it's the colour/index of the array.
			//So every time this function is called the index and colour shown in the text will change.
			//So the user can see what colour they are on.
			colourName.setText("Colour: " + colourArray[colourIndex]);
				
		}//End of function.
		
		public void colourDecrementation()
		{	
			//Decrementing the variables by one each time this function gets called.
			colourIndex--;
			
			if(colourIndex < 0)
			{
				colourIndex = 0;
			}
			
			//Setting the above textView variable to a certain text, in this case it's the colour/index of the array.
			//So every time this function is called the index and colour shown in the text will change.
			//So the user can see what colour they are on.
			colourName.setText("Colour: " + colourArray[colourIndex]);
				
		}//End of function.
		

		//Creating a function that changes the variable of incrementIndex every time this function is called.
		//This function will allow us to change colour each time this function is called.
		//The incrementIndex variable will be placed within the colourArray array, so each time the index is incremented the index of the array will change,
		//thus a different colour will be used.
		public void colourForward()
		{
			
			//Setting up the onClick listener for the face button.
			colourForward.setOnClickListener(new View.OnClickListener() {
			    
					public void onClick(View v) {
					
						//Calling the function of colourIncrementation();
						colourIncrementation();
						
					}//End of onClick() function.
			});//End of submit.setOnClickListener.
		}//End of function.
		
		public void colourBack()
		{
			
			//Setting up the onClick listener for the face button.
			colourBack.setOnClickListener(new View.OnClickListener() {
			    
					public void onClick(View v) {
					
						//Calling the function of colourIncrementation();
						colourDecrementation();
						
					}//End of onClick() function.
					
			});//End of submit.setOnClickListener.
			
		}//End of function.
		
		public void colour()
	    {
			
	        
			wholeImage.setOnTouchListener(new ImageView.OnTouchListener()
	        {
	            
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					int x = (int)event.getX();
	                int y = (int)event.getY();
	                int pixel = sendCombinedImages().getPixel(x,y);

	                //Calling the index within the array and placing it within the parseColor() function.
	                //This allows us to change colour.
	                int colour = Color.parseColor(colourArray[colourIndex]);
	                
	                f.Fill(sendCombinedImages(), new Point(x, y), sendCombinedImages().getPixel(x, y), colour);
	                 
	               // f.Fill(bmp, new Point(x, y), bmp.getPixel(x, y), colour);
	                wholeImage.setImageBitmap(sendCombinedImages());
					return false;
				}
	        });
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

