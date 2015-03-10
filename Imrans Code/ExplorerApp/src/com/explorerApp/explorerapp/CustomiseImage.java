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
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

public class CustomiseImage extends Activity {
	
	
	/////////////////http://stackoverflow.com/questions/9632114/how-to-find-pixels-color-in-particular-coordinate-in-images/////////////////
	
	///////////---------------Customise image.------------------////////////
	ImageView customiseImage;
	ImageView displayImage;
    ImageView imageDisplay;
	
	Matrix matrix;
	
	Bitmap bmp;
	
	Bitmap sendEditedImage;
	Bitmap sendEditedImage2;

	Point p1 = new Point();
	 
	floodFill f= new floodFill(); 
	
	Button nextButton;
	
	int pixel = 0;

	String image_str=null;
	
	int getCurrentColour;
	
	//String array for colours.
	int colour[] = {-16777216, 16711936,  -65536  };
	
	//Variable for changing colours.
	int colourNumber = -1;

	int colourNumber1 = 2;
	
	Bitmap  bitm;
	
	Bitmap bitm2;
	
	Bitmap newBitmap;

	Bitmap bm1;
	
	Bitmap bm2;
	
	//Used for masking.
	Bitmap result;
	
	//Creating image buttons for the forward and back image buttons.
	ImageButton forward;
	ImageButton backwards;
	
	//Creating more image buttons for the forward and back image buttons.
	ImageButton forward1;
	ImageButton backwards1;
	
	//Creating an array that holds different colours.
    private String colourArray[] = {"BLACK" , "#f4a460", "WHITE", "RED", "GREEN", "CYAN", "MAGENTA", "YELLOW"}; 
    //Variable to increment the colour array's index.
    private int incrementIndex=0;
    
    TextView colourName;
	
    //Used for the cropped image.
    Bitmap original;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customiseimage);
		
		
		customiseImage = (ImageView) findViewById(R.id.customiseImage);
		
		displayImage = (ImageView) findViewById(R.id.customiseImage2);
		
		//Getting the id from the xml file.
		forward = (ImageButton) findViewById(R.id.forward);
		
		//Getting the id from the xml file.
		backwards = (ImageButton) findViewById(R.id.back);
		
		//Getting the id from the xml file.
		forward1 = (ImageButton) findViewById(R.id.forward1);
				
		//Getting the id from the xml file.
		backwards1 = (ImageButton) findViewById(R.id.back1);

		//Creating a variable for the submit button, and retrieving an id from the xml file.
        nextButton = (Button) findViewById(R.id.nextButton);
         
        colourName = (TextView) findViewById(R.id.nameOfColour);
        
        imageDisplay  = (ImageView) findViewById(R.id.customiseImage3);
		
		displayImage();
		
		sendImage();
		
		forward();
		
		backward();
		
		bottomForward();
		
		bottomBack();
		
		//Set the back button to be invisible.
		backwards.setVisibility(View.GONE);
		
		

        bm1 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), (bmp.getHeight() / 2));
        
        bm2 = Bitmap.createBitmap(bmp, 0, (bmp.getHeight() / 2), bmp.getWidth(), (bmp.getHeight() / 2));   
       
        
	}//End of onCreate function.
	
	
	public void displayImage()
	{
		//Getting the image file by using the array.
	  //Bitmap displayImage = BitmapFactory.decodeFile(pImage.getPath());
		//Bitmap displayImage = BitmapFactory.decodeResource(getResources(), pImage.imageArray[pImage.imageNumber]);	
		//System.out.println(pImage.getPath());
		
		Bundle extras = getIntent().getExtras();
		byte[] byteArray = extras.getByteArray("picture");

		 bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		 
		 displayImage.setImageBitmap(bmp);
		 
	}//End of function.
	
	
	public void colourIncrementation()
	{	
		//Incrementing the variables by one each time this function gets called.
		incrementIndex++;
		
		//If the value of the variable called incrementIndex is equal
		//to the colourArray arrays index length, then we reset the incrementIndex back to 0 so we don't get an array index out of bounds exception.
		if(incrementIndex > 7)
		{
			incrementIndex = 0;
		}
		
		//Setting the above textView variable to a certain text, in this case it's the colour/index of the array.
		//So every time this function is called the index and colour shown in the text will change.
		//So the user can see what colour they are on.
		colourName.setText("Colour: " + colourArray[incrementIndex]);
		
		
	}
	
	
	//Creating a function that changes the variable of incrementIndex every time this function is called.
	//This function will allow us to change colour each time this function is called.
	//The incrementIndex variable will be placed within the colourArray array, so each time the index is incremented the index of the array will change,
	//thus a different colour will be used.
	public void changeColour()
	{
		//Setting up the onClick listener for the face button.
		forward.setOnClickListener(new View.OnClickListener() {
		    
				public void onClick(View v) {
				
					//Calling the function of colourIncrementation();
					colourIncrementation();
					
				}//End of onClick() function.
		});//End of submit.setOnClickListener.
	}//End of function.
	
	public void colour()
    {
		//final Bitmap bmp = ((BitmapDrawable)customiseImage.getDrawable()).getBitmap();
		
		customiseImage.setOnTouchListener(new ImageView.OnTouchListener()
        {
            
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
			     int x = (int)event.getX();
			        int y = (int)event.getY();
			         //pixel = bitmap.getPixel(x,y);
			         
			         Matrix inverse = new Matrix();
			         ((ImageView) v).getImageMatrix().invert(inverse);
			         float[] touchPoint = new float[] {event.getX(), event.getY()};
			         inverse.mapPoints(touchPoint);
			         int  xCoord = Integer.valueOf((int)touchPoint[0]);
			         int yCoord = Integer.valueOf((int)touchPoint[1]);
			         

                //Calling the index within the array and placing it within the parseColor() function.
                //This allows us to change colour.
                int colour = Color.parseColor(colourArray[incrementIndex]);

                f.Fill(bmp, new Point(xCoord, yCoord), bmp.getPixel(xCoord, yCoord), colour);
                customiseImage.setImageBitmap(bmp);
				return false;
			}
        });
    }
	
	public void forward()
	{
		//final ImageView imageDisplay = (ImageView) findViewById(R.id.customiseImage3);
		//final Bitmap bit = ((BitmapDrawable)customiseImage.getDrawable()).getBitmap();	
		
		//Setting up the onClick listener for the forward button.
		forward.setOnClickListener(new View.OnClickListener() 
		{				
			
			public void onClick(View v) 
			{
				
				//Once the forward button has been pressed, the backwards button will become visible.
				backwards.setVisibility(View.VISIBLE);
				
				colourNumber++;
				
 				System.out.println(bmp);

 				if(colourNumber >= colour.length)
 				{
 					colourNumber = 2;
 				}
 				
 				
 				//final Bitmap b = replaceColor(bmp,getColour(), colour[colourNumber]);
				//customiseImage.setImageBitmap(bmp);
 							 
 				original = bmp;
 				Bitmap mask = BitmapFactory.decodeResource(getResources(),R.drawable.mask1);
 				result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
 			    Canvas mCanvas = new Canvas(result);
 				Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
 				paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
 				mCanvas.drawBitmap(original, 0, 0, null);
 				mCanvas.drawBitmap(mask, 0, 0, paint);
 				paint.setXfermode(null);
 				imageDisplay.setImageBitmap(result);
 				imageDisplay.setScaleType(ScaleType.CENTER);   		
 				
 				bitm2 = replaceColor(result, Color.WHITE, colour[colourNumber]);  	
 				
 				imageDisplay.setImageBitmap(bitm2);
 				
                int[] locations = new int[2];
                imageDisplay.getLocationOnScreen(locations);
                //mImageView.getLocationInWindow(locations);
               int mOldX = locations[0];
               int mOldY = locations[1];

                System.out.println("X: " + mOldX + " Y: " + mOldY);
 				
 				

			}//End of onClick() function.

		});//End of forward.setOnClickListener.
		
	}//End of function.
	
	
	public void backward()
	{

		//final Bitmap bit = ((BitmapDrawable)customiseImage.getDrawable()).getBitmap();
		
		//Setting up the onClick listener for the forward button.
		backwards.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				//sendEditedImage2 = imageData(v);
				
				//customiseImage.setImageBitmap(bitm);
				
				colourNumber--;
 				System.out.println(bmp);

 				if(colourNumber < 0)
 				{
 					colourNumber = 0;
 				}
 				

 				//  bitm = replaceColor(bmp,Color.BLUE, colour[colourNumber]);
 				 
				
				//canvas.drawBitmap(bm1, 0f, 0f, null);
 				
 				//Place this within a function.
 				original = bmp;
 				Bitmap mask = BitmapFactory.decodeResource(getResources(),R.drawable.mask1);
 				result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
 			    Canvas mCanvas = new Canvas(result);
 				Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
 				paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
 				mCanvas.drawBitmap(original, 0, 0, null);
 				mCanvas.drawBitmap(mask, 0, 0, paint);
 				paint.setXfermode(null);
 				imageDisplay.setImageBitmap(result);
 				imageDisplay.setScaleType(ScaleType.CENTER);   
 					
 				bitm2 = replaceColor(result, Color.WHITE, colour[colourNumber]);
 				
 				//getCurrentColour = colour[colourNumber];
 				//changePosition();
 				//pos(bitm2);
 				
 				imageDisplay.setImageBitmap(bitm2);
				

			}//End of onClick() function.

		});//End of forward.setOnClickListener.
		
	}//End of function.
	
	public void bottomForward()
	{

		//Setting up the onClick listener for the forward button.
		forward1.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				//sendEditedImage2 = imageData(v);
				
				//customiseImage.setImageBitmap(bitm);
				
				colourNumber++;
 				System.out.println(bmp);

 				if(colourNumber >= 2)
 				{
 					colourNumber = 2;
 				}
 				

 				//  bitm = replaceColor(bmp,Color.BLUE, colour[colourNumber]);
 				 
				
				//canvas.drawBitmap(bm1, 0f, 0f, null);
 				
 				
 				bitm = replaceColor(bm2, Color.WHITE, colour[colourNumber]);
 				
 				
 				//sendEditedImage = imageData(v);
 				
 				
 					// newBitmap = combineImages(bitm, bitm2);
 					customiseImage.setImageBitmap(/*newBitmap*/ bitm);
 					
 			
				

			}//End of onClick() function.

		});//End of forward.setOnClickListener.
	}//End of function.
	
	
	public void bottomBack()
	{

		//Setting up the onClick listener for the forward button.
		backwards1.setOnClickListener(new View.OnClickListener() 
		{				

			public void onClick(View v) 
			{
				//sendEditedImage2 = imageData(v);
				
				//customiseImage.setImageBitmap(bitm);
				
				colourNumber--;
 				System.out.println(bmp);

 				if(colourNumber < 0)
 				{
 					colourNumber = 0;
 				}
 				

 				//  bitm = replaceColor(bmp,Color.BLUE, colour[colourNumber]);
 				 
				
				//canvas.drawBitmap(bm1, 0f, 0f, null);
 				
 				
 				bitm = replaceColor(bm2, Color.WHITE, colour[colourNumber]);
 				
 				
 				//sendEditedImage = imageData(v);
 				
 				
 					// newBitmap = combineImages(bitm, bitm2);
 					customiseImage.setImageBitmap(/*newBitmap*/ bitm);
 					
 			
				

			}//End of onClick() function.

		});//End of forward.setOnClickListener.
	}//End of function.
	

	
	
	public Bitmap combineImages(Bitmap c, Bitmap s, Bitmap a) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom 
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
		return combineImages(bmp, bitm, bitm2);
	}
	
	public int getColour()
	{
		
		final Bitmap bitmap = ((BitmapDrawable)customiseImage.getDrawable()).getBitmap();
		customiseImage.setOnTouchListener(new ImageView.OnTouchListener()
        {
			 
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
		        int x = (int)event.getX();
		        int y = (int)event.getY();
		         //pixel = bitmap.getPixel(x,y);
		         
		         Matrix inverse = new Matrix();
		         ((ImageView) v).getImageMatrix().invert(inverse);
		         float[] touchPoint = new float[] {event.getX(), event.getY()};
		         inverse.mapPoints(touchPoint);
		         int  xCoord = Integer.valueOf((int)touchPoint[0]);
		         int yCoord = Integer.valueOf((int)touchPoint[1]);
		         
		         pixel = bitmap.getPixel(xCoord,yCoord);
		         
		         System.out.println(pixel);
		         
		         final Bitmap b = replaceColor(bmp,pixel, colour[colourNumber]);
					//customiseImage.setImageBitmap(bmp);
	 			
					customiseImage.setImageBitmap(b);
		        		 
		        		 
		        return false;
		        }
		   });
		return pixel;

	}
	
	
			 
	
     //Function to replace the colour of an image.
	 public Bitmap replaceColor(Bitmap src,int fromColor, int targetColor) {
	        if(src == null) {
	            return null;
	        }
	     // Source image size 
	        int width = src.getWidth()/*/2*/;
	        int height = src.getHeight()/*/2*/;
	     
	        int[] pixels = new int[width * height];
	        //get pixels
	        src.getPixels(pixels, 0, width, 0, 0, width, height);
	  
	        for(int x = 0; x < pixels.length; ++x) {
	        	
	            pixels[x] = (pixels[x] == fromColor) ? targetColor : pixels[x];
	        }
	     // create result bitmap output 
	        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
	        //set pixels
	        result.setPixels(pixels, 0, width, 0, 0, width, height);
	  
	        return result;
	        
	      /*  Paint paint = new Paint();
	        float[] matrix = { 
	            1, 1, 1, 1, 1, //red
	            0, 0, 0, 0, 0, //green
	            0, 0, 0, 0, 0, //blue
	            1, 1, 1, 1, 1 //alpha
	        };
	        paint.setColorFilter(new ColorMatrixColorFilter(matrix));

	        Rect source = new Rect(0, 0, 100, 100);
	        Rect dest = new Rect(0, 0, 100, 100);

	        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sampleimage);
	        customiseImage.drawBitmap(bitmap , source, dest, paint);*/
	  
	        
	    }//End of function.
	
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

	
	
	
	
	
	

}
