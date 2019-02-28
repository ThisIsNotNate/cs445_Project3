package com.example.gravrun;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Thread gameThread = null; // I used this tutorial for animating spritesheets, but I custom made the spritesheet. http://gamecodeschool.com/android/coding-android-sprite-sheet-animations/
    long fps=1;
    long timeThisFrame;
    long lastFrameChangeTime = 0;

    private int frameCount = 8; //All animations have 8 frames
    private int currentFrame = 0;
    private int frameDuration = 50; // in ms

    private SurfaceHolder surfaceHolder;
    private boolean running;
    public static Canvas canvas;
    public Paint paint;

    boolean inverseGravity = false;

    Bitmap floor;

    Character gravRunner;

    private Rect gravSpriteFrame;
    private RectF gravDrawFrame;
    ArrayList<Object> entities = new ArrayList<>();

    //private Rect floorFrame = new Rect(0, 0, screenHeight,50);



    public GameView(Context context){
        super(context);

        surfaceHolder = getHolder();

        floor = BitmapFactory.decodeResource(this.getResources(), R.mipmap.game_floor);
        floor = Bitmap.createScaledBitmap(floor, screenHeight, 50, false);

        paint = new Paint();
        gravRunner = new Character(context);
        entities.add(gravRunner);
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
        setFocusable(true);

    }

    public void update(){
        //where we'll update game code
        //gravRunnerX = gravRunnerX + (60/fps);
        gravRunner.updateY(inverseGravity,timeThisFrame);
    }

    @SuppressLint("MissingSuperCall")
    public void draw(Canvas canvas){


        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 26, 128, 182));
            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(40);

            canvas.drawText("FPS:"+fps, 20, 80, paint);

            RectF drawFrame = gravRunner.getDrawFrame();
            Rect spriteFrame = gravRunner.getSpriteFrame();
            getCurrentFrame();
            canvas.drawBitmap(gravRunner.getBitmap(inverseGravity), spriteFrame, drawFrame, paint);
            paint.setColor(Color.argb(255, 90, 90, 90));
            canvas.drawRect(0,0,screenWidth,50,paint);
            canvas.drawRect(0,screenHeight-50,screenWidth,screenHeight,paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while(running){
            long startTime = System.currentTimeMillis();

            update();

            draw(canvas);

            timeThisFrame = System.currentTimeMillis()-startTime;
            if(timeThisFrame >= 1)
                fps = 1000/timeThisFrame;
        }


    }

    public void getCurrentFrame() {

        long time = System.currentTimeMillis();
        if (running) {
            if (time > lastFrameChangeTime + frameDuration) {
                lastFrameChangeTime = time;
                currentFrame++;
                if (currentFrame >= frameCount) {

                    currentFrame = 0;
                }
                gravRunner.nextSpriteFrame(currentFrame);
            }

        }
    }

    public void invertGravity(){
        inverseGravity = !inverseGravity;
    }

    public void jump(){
        gravRunner.jump(inverseGravity);
    }
}
