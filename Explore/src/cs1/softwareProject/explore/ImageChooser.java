package cs1.softwareProject.explore;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;


import java.util.List;

//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class ImageChooser extends Activity {
	
		ImageView firstImage;
	    //ImageView secondImage;
		//ImageView thirdImage;
		ImageButton forward;
		ImageButton back;
		int imageForward = 0;
		int imageBackward = 0;
		int imageNumber = 0;
		
		TextView textMaleOrFemale;
		
		Bitmap nextImage;
		
		//This bitmap will be used to get the image selected so we can display it on the next page.
		Bitmap bmp;
		
		byte[] byteArray;
		
		//Variable to get the path that will be displayed on screen. So we can display the selected image on the next screen.
		String getFilePath;
		
		List<ImageView> images = new ArrayList<ImageView>();
		
		//Each number in the array represents the images that are located in the res/drawable-mdpi folder.
		//The first index is of the male face. The second index is of the female face.
		int imageArray[] = { R.drawable.male__face/*2130837584*/, R.drawable.female__face/*2130837565*/};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_chooser);
		
		firstImage = (ImageView) findViewById(R.id.firstImage);
		//secondImage = (ImageView) findViewById(R.id.secondImage);
		//thirdImage = (ImageView) findViewById(R.id.thirdImage);
		
		images.add(firstImage);
		//images.add(secondImage);
		//images.add(thirdImage);
		
		forward = (ImageButton) findViewById(R.id.nextImage);
		back = (ImageButton) findViewById(R.id.previousImage);
		
		textMaleOrFemale = (TextView) findViewById(R.id.text);
		
		//firstImage.setVisibility(View.VISIBLE);
		//secondImage.setVisibility(View.GONE);
		//thirdImage.setVisibility(View.GONE);
		
		//Calling the function that changes the image forwards.
		forward();
		
		//Calling the function that changes the image backwards.
		backward();
		
		//Calling the function that directs the user to the next page.
		nextPage();
		
		//selectImage();
	
	}//End of onCreate() function.


	//Function that allows the user to change image (change image forwards) once the forward button is pressed.
	public void forward()
	{
		forward.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				//Incrementing the int variable.
				imageNumber ++;
				
				//If the variable is greater less than 0 then we make it stay at 20 so the array does not go out of bounds.
				if(imageNumber >= 1)
				{
					imageNumber = 1;
				}
				
				//Getting the image file by using the array.

				 nextImage = BitmapFactory.decodeResource(getResources(), imageArray[imageNumber]);
				 
				System.out.println(nextImage);
				
				textMaleOrFemale.setText("Female");
				
				//Displaying the image.
				firstImage.setImageBitmap(nextImage);
			}//End of onClick() function.
			
		});//End of setOnClickListene() function.
		
	}//End of function.
	
	//Function that allows the user to change image (change image backwards) once the previous button is pressed.
	public void backward()
	{
		back.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				//Incrementing the int variable.
				imageNumber --;
					
				//If the variable is less than 0 then we make it stay at 0 so the array does not go out of bounds.
				if(imageNumber < 0)
				{
					imageNumber = 0;
				}
					
				//Getting the image file by using the array.
				 nextImage = BitmapFactory.decodeResource(getResources(), imageArray[imageNumber]);
				 
				System.out.println(nextImage);
				
				textMaleOrFemale.setText("Male");
					
				//Displaying the image.
				firstImage.setImageBitmap(nextImage);

			}//End of onClick() function.
				
		});//End of setOnClickListene() function.
		
	}//End of function.
	
	public void selectImage()
	{
		
		firstImage.setOnClickListener(new ImageView.OnClickListener()
        {

			//Bitmap scaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.firstimage);
			@Override
			public void onClick(View v) 
			{
				TextView text = (TextView) findViewById(R.id.text);
				
				text.setText(imageArray[imageNumber]);
				
				//Getting the string that the text displays as we will use this to display the image on the next page.
				//getFilePath = text.getText().toString();
				
				System.out.println(getPath());
				
				
			}
        });
	}
	
	public String getPath()
	{
		return getFilePath;
	}
	
	
	public void nextPage()
	{
		Button nextPage = (Button) findViewById(R.id.nextPage);
		
		
		
		//Setting up the onClick listener for the nextPage button.
		nextPage.setOnClickListener(new View.OnClickListener() 
		{
			
		
			public void onClick(View v) 
			{
				 //Getting the users selected image.
				 //Only problem is that you can send an image through to the next page without selecting an image.
			     //I wanted to be able to get the file path of the image and send that through but I don't need to.
				 //I could do it that way later.
				Bitmap firstbit = BitmapFactory.decodeResource(getResources(), imageArray[0]);
				Bitmap secondbit = BitmapFactory.decodeResource(getResources(), imageArray[1]);
				 bmp = BitmapFactory.decodeResource(getResources(), imageArray[imageNumber]);
				 ByteArrayOutputStream stream = new ByteArrayOutputStream();
				 bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
				 byteArray = stream.toByteArray();
				
				  //If the user chooses a certain image then we do the following.
				  if (bmp.getWidth() == firstbit.getWidth() && bmp.getHeight() == firstbit.getHeight()) 
				  {
				        int[] pixels1 = new int[bmp.getWidth() * bmp.getHeight()];
				        int[] pixels2 = new int[firstbit.getWidth() * firstbit.getHeight()];
				        bmp.getPixels(pixels1, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
				        firstbit.getPixels(pixels2, 0, firstbit.getWidth(), 0, 0, firstbit.getWidth(), firstbit.getHeight());
				        
				        if (Arrays.equals(pixels1, pixels2)) 
				        {
				        	Intent intent = new Intent(v.getContext(), maleImage.class);
							startActivityForResult (intent, 0);
							
							//This part is for being able to display the selected image on the next page.
							//Sending the users selected image to be displayed on the next page.
							intent.putExtra("maleimage", byteArray);
							startActivity(intent);
							
							System.out.println("First one.");
				        }
				     
				  }//End of if statement.
				  
				  //If the user chooses a certain image then we do the following.
				  if (bmp.getWidth() == secondbit.getWidth() && bmp.getHeight() == secondbit.getHeight()) 
				  {
				        int[] pixels1 = new int[bmp.getWidth() * bmp.getHeight()];
				        int[] pixels2 = new int[secondbit.getWidth() * secondbit.getHeight()];
				        bmp.getPixels(pixels1, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
				        secondbit.getPixels(pixels2, 0, secondbit.getWidth(), 0, 0, secondbit.getWidth(), secondbit.getHeight());
				        
				        if (Arrays.equals(pixels1, pixels2)) 
				        {
				        	Intent intent = new Intent(v.getContext(), femaleImage.class);
							startActivityForResult (intent, 0);
							
							//This part is for being able to display the selected image on the next page.
							//Sending the users selected image to be displayed on the next page.
							intent.putExtra("femaleimage", byteArray);
							startActivity(intent);
							
				        }
				     
				  }//End of if statement.
				
				  	
			}//End of onClick() function.
		
		});//End of submit.setOnClickListener.
		
	}//End of function.
	
}//End of class.
