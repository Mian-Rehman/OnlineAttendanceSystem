package com.example.onlineattendencesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    TextView text_splash;
    Lottie mylottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_splash=findViewById(R.id.text_splash);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Animation myanimation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        text_splash.setAnimation(myanimation);





        Thread mythread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(MainActivity.this,ConfirmScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        mythread.start();

    }
}