package com.example.gravrun;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

public class Character {
    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private int frameWidth = 75;
    private int frameHeight = 108;
    private int frameCount = 8;
    private int bounds[]={50,screenHeight-50-frameHeight};

    final float ACCELERATION = 2.5f;
    final float JUMP_VELOCITY = 40f;
    float acceleration = ACCELERATION;
    boolean canInvert = true;

    float velocity = 0;
    float posX = 200;
    float posY = screenHeight-50-frameHeight;


    Bitmap gravRunner;
    Bitmap invertedGravRunner;

    private Rect currentSpriteFrame;
    private Rect spriteFrame;
    private RectF drawFrame;
    public Character(Context context){
        gravRunner = BitmapFactory.decodeResource(context.getResources(), R.drawable.gravrunner_spritesheet);
        gravRunner = Bitmap.createScaledBitmap(gravRunner, frameWidth*frameCount, frameHeight, false);
//        invertedGravRunner = BitmapFactory.decodeResource(context.getResources(), R.drawable.gravrunner_invertedsprites);
//        invertedGravRunner = Bitmap.createScaledBitmap(invertedGravRunner, frameWidth*frameCount, frameHeight, false);
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f,-1.0f);
        invertedGravRunner = Bitmap.createBitmap(gravRunner, 0,0,frameWidth*frameCount, frameHeight, matrix, true);

        spriteFrame = new Rect(0,0,frameWidth, frameHeight);
        drawFrame = new RectF(posX,posY, posX+frameWidth, posY+frameHeight);
    }

    public void updateY(boolean inverseGravity, long timeThisFrame){
        if(inverseGravity)
            acceleration = -ACCELERATION;
        else
            acceleration = ACCELERATION;
        //if(velocity == 0) velocity = acceleration;
        posY += (timeThisFrame / 25f) * velocity;
        velocity += (timeThisFrame / 25f) * acceleration;
        if(posY < bounds[0]){//would go through top of screen
            posY = bounds[0];
            velocity = 0;
            canInvert=true;
        }
        else if(posY > bounds[1]){//would go through bottom of screen
            posY=bounds[1];
            velocity = 0;
            canInvert=true;
        }
        drawFrame.set(posX,posY, posX+frameWidth, posY+frameHeight);

    }

    public RectF getDrawFrame(){
        return drawFrame;
    }

    public Rect getSpriteFrame(){
        return spriteFrame;
    }

    public boolean canInvert(){
        return canInvert;
    }

    public void cantInvert(){
        canInvert = false;
    }

    public Bitmap getBitmap(boolean inverseGravity){
        if(!inverseGravity)
            return gravRunner;
        else
            return invertedGravRunner;//something's wrong witht his bitmap
    }

    public void nextSpriteFrame(int currentFrame){
        spriteFrame.left = currentFrame * frameWidth;
        spriteFrame.right = (int)spriteFrame.left + frameWidth;
    }

    public void jump(boolean inverseGravity){
        if(inverseGravity && posY == bounds[0])
            velocity = JUMP_VELOCITY;
        else if (!inverseGravity && posY == bounds[1])
            velocity = -JUMP_VELOCITY;
    }
}
