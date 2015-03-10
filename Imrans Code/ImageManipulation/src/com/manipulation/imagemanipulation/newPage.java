package com.manipulation.imagemanipulation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class newPage extends MainActivity {
	//Creating an image view which will be initialised to the skeletonEnemy image.
	ImageView skeletonEnemy;
	 Point p1 = new Point();

	 //Creating global variables.
     
	 //Creating an array that holds different colours.
     private String colourArray[] = {"BLACK" , "#f4a460", "WHITE", "RED", "GREEN", "CYAN", "MAGENTA", "YELLOW"}; 
     //Variable to increment the colour array's index.
     private int incrementIndex=0;
     
     TextView colourName;
	 
	floodFill f = new floodFill(); 
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newpage);
		
		//Getting the id of the text view.
		colourName = (TextView) findViewById(R.id.colourName);
		
		//Displaying the first colour of the array straight away.
		colourName.setText("Colour: " + colourArray[0]);
		
		//Calling functions.
		backButton();
		changeColour();
		colour();

	}
	
	public void backButton()
	{
		Button backToMenu = (Button) findViewById(R.id.backButton);
		backToMenu.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v)
				{
					Intent intent = new Intent(v.getContext(), MainActivity.class);
					startActivityForResult (intent, 0);
				}
				
		});
	}
	
	
	
	 
	//This function will increment the variable each time it gets called, and creates a check, so if the value of the variable called incrementIndex is equal
	//to the colourArray arrays index length, then we reset the incrementIndex back to 0 so we don't get an array index out of bounds exception.
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
		final Button colourChanger = (Button) findViewById(R.id.colourChanger);
		
		
		//Setting up the onClick listener for the face button.
		colourChanger.setOnClickListener(new View.OnClickListener() {
		    
				public void onClick(View v) {
				
					//Calling the function of colourIncrementation();
					colourIncrementation();
					
				}//End of onClick() function.
		});//End of submit.setOnClickListener.
	}//End of function.
	
	public void colour()
    {
		
		//final BitmapDrawable view = (BitmapDrawable)  getResources().getDrawable(R.drawable.ic_launcher);/*BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);*/
        //Bitmap view = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        skeletonEnemy = (ImageView) findViewById(R.id.skeletonEnemy);
        
		final Bitmap bmp = ((BitmapDrawable)skeletonEnemy.getDrawable()).getBitmap();
		
		

		skeletonEnemy.setOnTouchListener(new ImageView.OnTouchListener()
        {
            
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int x = (int)event.getX();
                int y = (int)event.getY();

                //Calling the index within the array and placing it within the parseColor() function.
                //This allows us to change colour.
                int colour = Color.parseColor(colourArray[incrementIndex]);

                f.Fill(bmp, new Point(x, y), bmp.getPixel(x, y), colour);
                skeletonEnemy.setImageBitmap(bmp);
				return false;
			}
        });
    }
	
	
	
	
}
