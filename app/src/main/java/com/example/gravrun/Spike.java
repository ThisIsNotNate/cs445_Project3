package com.example.gravrun;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

public class Spike {
    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    Bitmap spikes, invertedSpikes;
    Bitmap sprite;
    int spikeWidth = 150;
    int spikeHeight = 50;
    int spikeY = screenHeight-50-spikeHeight;
    int invertedSpikeY=50;
    float posY;
    float posX = screenWidth-150;
    //float velocity = 10f;

    private RectF drawFrame;
    private Rect spriteFrame;

    public Spike(Context context, boolean top){
        spikes = BitmapFactory.decodeResource(context.getResources(), R.drawable.spikes);
        sprite = Bitmap.createScaledBitmap(spikes, spikeWidth, spikeHeight, false);


        if(top){
            Matrix matrix = new Matrix();
            matrix.preScale(1.0f,-1.0f);
            sprite = Bitmap.createBitmap(sprite, 0,0,spikeWidth, spikeHeight, matrix, true);
            posY = invertedSpikeY;
        }
        else{
            posY = spikeY;


        }
        drawFrame = new RectF(posX, posY, posX+spikeWidth, posY+spikeHeight);
        spriteFrame = new Rect(0,0,spikeWidth, spikeHeight);
    }

    public void updateX(){
        posX -= .06;
        drawFrame.set(posX,posY,posX+spikeWidth,posY+spikeHeight);
    }

    public boolean offScreen(){
        if(posX < -150)
            return true;
        else
            return false;
    }
    public Bitmap getBitmap(){
        return sprite;
    }

    public RectF getDrawFrame(){
        return drawFrame;
    }

    public Rect getSpriteFrame(){
        return spriteFrame;
    }
}
