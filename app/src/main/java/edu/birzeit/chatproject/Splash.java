package edu.birzeit.chatproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private Animation imageanim, txtanim;
    private TextView txt;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        img = (ImageView) findViewById(R.id.img);
        imageanim = AnimationUtils.loadAnimation(this, R.anim.imageanim);
        img.setAnimation(imageanim);
        txt = (TextView) findViewById(R.id.txt);
        txtanim = AnimationUtils.loadAnimation(this, R.anim.textanim);
        txt.setAnimation(txtanim);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this, LoginPage.class);
                startActivity(mainIntent);
                finish();
            }
        }, 5000);
    }


}