package com.example.employee;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {


    private static int time = 2500;
    private Animation topAnim, bottomAnim, leftAnim, rightAnim;
    private ImageView image_left;
    private ImageView image_top;
    private ImageView image_right;
    private TextView lable;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        image_left = findViewById(R.id.employee_left);
        image_top = findViewById(R.id.employee_top);
        image_right = findViewById(R.id.employee_right);
        lable = findViewById(R.id.programeName);
        desc = findViewById(R.id.description);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        leftAnim = AnimationUtils.loadAnimation(this, R.anim.left);
        rightAnim = AnimationUtils.loadAnimation(this, R.anim.right);

        image_top.setAnimation(topAnim);
        image_left.setAnimation(leftAnim);
        image_right.setAnimation(rightAnim);
        lable.setAnimation(bottomAnim);
        desc.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent next = new Intent(splashScreen.this, MainActivity.class);
                startActivity(next);
                finish();
            }
        }, time);
    }
}