package com.example.rosehime.mobileassignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by RoseHime on 27/11/2016.
 */

import android.view.SurfaceHolder;
import android.graphics.Canvas;

public class GamePanelSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    // Implement this interface to receive information about changes to the surface.

    private GameThread myThread = null; // Thread to control the rendering

    // 1a) Variables used for background rendering
    private Bitmap bg, scaledbg;

    // 1b) Define Screen width and Screen height as integer
    int ScreenWidth, ScreenHeight;

    // 1c) Variables for defining background start and end point
    private short bgX = 0, bgY = 0;

    // 4a) bitmap array to stores 4 images of the spaceship
    private Bitmap[] spaceship = new Bitmap[4];

    // 4b) Variable as an index to keep track of the spaceship images
    private short spaceshipIndex = 0;

    // For spaceship location
    int mX, mY;

    // Variables for FPS
    public float FPS;
    float deltaTime;
    long dt;

    // Variable for Game State check
    private short GameState;

    //constructor for this GamePanelSurfaceView class
    public GamePanelSurfaceView (Context context){

        // Context is the current state of the application/object
        super(context);

        // Adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // 1d) Set information to get screen size
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        // 1e)load the image when this class is being instantiated
        bg = BitmapFactory.decodeResource(getResources(), R.drawable.gamescene);
        scaledbg = Bitmap.createScaledBitmap(bg, ScreenWidth, ScreenHeight, true);

        // 4c) Load the images of the spaceships

        //spaceship[0] = BitmapFactory.decodeResource(getResources(), R.drawable.ship2_1);
        //spaceship[1] = BitmapFactory.decodeResource(getResources(), R.drawable.ship2_2);
        //spaceship[2] = BitmapFactory.decodeResource(getResources(), R.drawable.ship2_3);
        //spaceship[3] = BitmapFactory.decodeResource(getResources(), R.drawable.ship2_4);
        //student to scaled your spaceship base on scaledbg xxxx
        //spaceship[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ship2_1), ScreenWidth, ScreenHeight, true);
        //spaceship[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ship2_2), ScreenWidth, ScreenHeight, true);
        //spaceship[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ship2_3), ScreenWidth, ScreenHeight, true);
        //spaceship[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ship2_4), ScreenWidth, ScreenHeight, true);


        // Create the game loop thread
        myThread = new GameThread(getHolder(), this);

        // Make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    //must implement inherited abstract methods
    public void surfaceCreated(SurfaceHolder holder){
        // Create the thread
        if (!myThread.isAlive()){
            myThread = new GameThread(getHolder(), this);
            myThread.startRun(true);
            myThread.start();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder){
        // Destroy the thread
        if (myThread.isAlive()){
            myThread.startRun(false);


        }
        boolean retry = true;
        while (retry) {
            try {
                myThread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    public void RenderGameplay(Canvas canvas) {
        // 2) Re-draw 2nd image after the 1st image ends
        if (canvas == null) {
            return;
        }
        canvas.drawBitmap(scaledbg, bgX, bgY, null);
        canvas.drawBitmap(scaledbg, bgX + ScreenWidth, bgY, null);

        // 4d) Draw the spaceships
        //canvas.drawBitmap(spaceship[spaceshipIndex], mX, mY, null);

        // Bonus) To print FPS on the screen

    }


    //Update method to update the game play
    public void update(float dt, float fps){
        FPS = fps;

        switch (GameState) {
            case 0: {
                // 3) Update the background to allow panning effect
                bgX -= 500 * dt; // change the number of panning speed
                if (bgX < - ScreenWidth) {
                    bgX = 0;
                }
                // 4e) Update the spaceship images / shipIndex so that the animation will occur.
                spaceshipIndex++;

                //if (spaceshipIndex > 3)
                //{
                //    spaceshipIndex = 0;
                //}
            }
            break;
        }
    }

    // Rendering is done on Canvas
    public void doDraw(Canvas canvas){
        switch (GameState)
        {
            case 0:
                RenderGameplay(canvas);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        // 5) In event of touch on screen, the spaceship will relocate to the point of touch
        short X = (short) event.getX();
        short Y = (short) event.getY();

        //if (event.getAction() == MotionEvent.ACTION_DOWN) {
        //    mX = (short)(X - spaceship[spaceshipIndex].getWidth()/2);
        //    mY = (short)(Y - spaceship[spaceshipIndex].getHeight()/2);
        //}

        return super.onTouchEvent(event);
    }
}

