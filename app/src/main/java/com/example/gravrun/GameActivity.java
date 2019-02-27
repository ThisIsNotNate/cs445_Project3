package com.example.gravrun;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class GameActivity extends Activity {
    private GameView game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        game = new GameView(this);

        FrameLayout frame = new FrameLayout(this);
        LinearLayout buttons = new LinearLayout(this);
        Button invertGravity = new Button(this);
        Button jump = new Button(this);

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        invertGravity.setX(0);
        invertGravity.setY(0);
        invertGravity.setWidth(screenWidth/2);
        invertGravity.setHeight(screenHeight);
        invertGravity.setVisibility(View.VISIBLE);
        invertGravity.setBackgroundColor(Color.TRANSPARENT);

        jump.setWidth(screenWidth/2);
        jump.setHeight(screenHeight);
        jump.setVisibility(View.VISIBLE);
        jump.setBackgroundColor(Color.TRANSPARENT);

        buttons.addView(invertGravity);
        buttons.addView(jump);

        frame.addView(game);
        frame.addView(buttons);

        setContentView(frame);

        invertGravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Button Pressed", "Invert Gravity");
                game.invertGravity();
            }
        });

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Button Pressed", "Jump");
                game.jump();
            }
        });
    }

}
