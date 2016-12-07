package com.example.tjl01030.duckhunt;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button gameButton;
    private Button helpButton;
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameButton = (Button)findViewById(R.id.button1);
        helpButton = (Button)findViewById(R.id.button2);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/duckhunt.ttf");
        gameButton.setTypeface(custom_font);
        helpButton.setTypeface(custom_font);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.title);
        mp.start();
    }

    public void theIntent(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}
