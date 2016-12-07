package com.example.tjl01030.duckhunt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity {
    private ImageView stageView;
    private AnimationDrawable frameAnimation, deathAnimation;
    private ImageView theDuck, theDog;
    private Duck duck;
    private int xLocation;
    private int yLocation;
    private Thread mThread;
    private int theHeight;
    private int theWidth;
    private int getX;
    private int getY;
    private MediaPlayer mp, mp2, mp4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        duck = new Duck(xLocation, yLocation);
        stageView = (ImageView)findViewById(R.id.imageView3);
        theDuck = (ImageView)findViewById(R.id.imageViewDuck);
        theDog = (ImageView)findViewById(R.id.dog_movement);
        //Change xLocation and yLocation for the duck to hide in the grass once movement is figured out
        xLocation = 500;
        yLocation = 500;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        theWidth = size.x;
        theHeight = size.y;
        mThread = new Thread(calculateAction);
        theDuck.setVisibility(View.GONE);
        mp = MediaPlayer.create(this, R.raw.intro);
        mp.start();
        startGame();
    }

    private void startGame(){
        theDog.setVisibility(View.VISIBLE);
        //Get location of dog. Start at edge of screen, and go to x coordinate of 1250. Y coordinates of 750 (start x, end x, start y, end y
        final TranslateAnimation translateAnimation = new TranslateAnimation(stageView.getLeft(), 1250, 750, 750);
        translateAnimation.setFillAfter(true);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dogJump();
            }
        }, 5000);
        //Play dog animation. Take 7 seconds to walk across screen
        frameAnimation = (AnimationDrawable)theDog.getDrawable();
        frameAnimation.start();
        theDog.startAnimation(translateAnimation);
        translateAnimation.setDuration(5000);

    }

    private void dogJump(){
        //set image of the dog standing still
        theDog.setImageResource(R.drawable.dog_pause);
        final TranslateAnimation translateAnimation = new TranslateAnimation(1250, 1350, 750, 450);
        mp = MediaPlayer.create(this, R.raw.dog_bark);
        final Handler handler = new Handler();
        translateAnimation.setFillAfter(true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //animation of dog going up
                theDog.setImageResource(R.drawable.dog_jump_1);
                theDog.startAnimation(translateAnimation);
                translateAnimation.setDuration(500);
                mp.start();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //animation of dog going down
                        final TranslateAnimation newAnimation = new TranslateAnimation(1350, 1450, 450, 850);
                       theDog.setImageResource(R.drawable.dog_jump_2);
                        stageView.bringToFront();
                       theDog.startAnimation(newAnimation);
                        newAnimation.setDuration(700);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //get dog off the screen, run callDuck
                                if (!mp.isPlaying()) {
                                    if (!mp.isPlaying()) {
                                        mp.start();
                                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                            @Override
                                            public void onCompletion(MediaPlayer mediaPlayer) {
                                                mp.release();
                                            }
                                        });
                                    }
                                }
                                translateAnimation.cancel();
                                theDog.clearAnimation();
                                theDog.setVisibility(View.GONE);
                                callDuck();
                                mThread.start();
                            }
                        }, 700);
                    }
                }, 1000);
            }
        }, 1000);
    }


    private void callDuck() {
        theDuck.setVisibility(View.VISIBLE);
        duck.setVelocityX(30);
        duck.setVelocityY(30);
        duck.setX(duck.getX());
        duck.setY(duck.getY());
        theDuck.setX(duck.getX());
        theDuck.setY(duck.getY());
        Log.v("callDuck Bird X", String.valueOf(duck.getX()));
        Log.v("callDuck Bird Y", String.valueOf(duck.getY()));
        frameAnimation = (AnimationDrawable) theDuck.getDrawable();
        frameAnimation.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

    public void clickGun(View view) {
        mp = MediaPlayer.create(this, R.raw.gun_shot);
        mp.start();
    }

    private Runnable calculateAction = new Runnable() {
        //duck moves once then doesn't move again???
        private static final int DELAY = 30;

        public void run() {
            while(true){
            try {
                duck.move(stageView, theDuck);
                Thread.sleep(DELAY);
                threadHandler.sendEmptyMessage(0);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }
        }};

    public Handler threadHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //theDuck.setScaleX((float) (duck.getFacingDirection()));
            theDuck.setX((float) duck.getX());
            theDuck.setY((float) duck.getY());
        }
    };



    public void clickBird(View view){
        //Current thread is calculateAction
        try {
            Thread.currentThread().sleep(3350);
        } catch (InterruptedException e){
        }
        theDuck.setImageResource(R.drawable.duck_shot_delay);
        mp2 = MediaPlayer.create(this, R.raw.dead_duck_lands);
        mp = MediaPlayer.create(this, R.raw.duck_falling);
        mp4 = MediaPlayer.create(this, R.raw.gun_shot);
        if (!mp4.isPlaying()){
            mp4.start();
            mp4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mp4.release();
                }
            });
        }
        final TranslateAnimation translateAnimation = new TranslateAnimation(duck.getX(),duck.getX(),duck.getY(), stageView.getBottom());
        Log.v("Bird X", String.valueOf(duck.getX()));
        Log.v("Bird Y", String.valueOf(duck.getY()));
        translateAnimation.setFillAfter(true);
        final Handler handler = new Handler();

        //fasdfasdf
        //After one second, duck death animation plays
        handler.postDelayed(new Runnable() {
            @Override
            //After one second, the still image of the duck goes to the falling animation.
            public void run() {
                theDuck.setImageResource(R.drawable.duck_death_animate);
                deathAnimation = (AnimationDrawable)theDuck.getDrawable();
                deathAnimation.start();
                theDuck.startAnimation(translateAnimation);
                translateAnimation.setDuration(3000);
                mp.start();
                Log.v("Duck Hit", String.valueOf(duck.getX()));
                Log.v("Duck Hit", String.valueOf(duck.getY()));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!mp2.isPlaying()) {
                            mp2.start();
                            mp2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    mp2.release();
                                }
                            });
                        }
                        dogHoldingDuck();
                    }
                }, 1750);
            }
        }, 1000);
    }

    public void dogHoldingDuck() {
        final Handler handler = new Handler();
        mp = MediaPlayer.create(this, R.raw.got_duck);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mp.isPlaying()) {
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mp.release();
                        }
                    });
                }
                theDog.setImageResource(R.drawable.dog_holding_one);
                final TranslateAnimation translateAnimation = new TranslateAnimation(1000, 1000, 650, 550);
                theDog.startAnimation(translateAnimation);
                translateAnimation.setDuration(450);
                translateAnimation.setFillAfter(true);
                theDog.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //theDog.setImageResource(R.drawable.dog_holding_one);
                        final TranslateAnimation translateAnimation = new TranslateAnimation(1000, 1000, 550, 550);
                        theDog.startAnimation(translateAnimation);
                        translateAnimation.setDuration(450);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //theDog.setImageResource(R.drawable.dog_holding_one);
                                final TranslateAnimation translateAnimation = new TranslateAnimation(1000, 1000, 550, 650);
                                theDog.startAnimation(translateAnimation);
                                translateAnimation.setDuration(450);
                                translateAnimation.setFillAfter(true);
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        theDog.setVisibility(View.INVISIBLE);
                                        translateAnimation.cancel();
                                        theDog.clearAnimation();
                                        theDog.setVisibility(View.GONE);
                                        callDuck();
                                    }
                                }, 450);
                            }
                        }, 450);

                    }
                }, 450);
            }
        }, 100);
    }

}
