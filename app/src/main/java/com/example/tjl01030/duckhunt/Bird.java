
package com.example.tjl01030.duckhunt;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Handler;

import java.util.Random;
import java.util.concurrent.RunnableFuture;

import static com.example.tjl01030.duckhunt.R.id.imageView;


/**
 * Created by tjl01030 on 11/29/2016.
 */


public class Bird {
    private int mX;
    public int x;
    public int y;
    private int mY;
    private int mVelocity;
    private int mTheHeight;
    private int mTheWidth;
    private int mDirection;

    private int moveX, moveY;

    public void setVelocity(int velocity){
        mVelocity = velocity;
    }
    public int getVelocity(){
        return mVelocity;
    }

    public void setX(int x){mX = x;}
    public void setY(int y){
        mY = y;
    }
    public int getX(){
        return mX;
    }
    public int getY(){return mY;}

    public Bird (int x, int y, int theWidth, int theHeight){
        this.x = x;
        this.y = y;
        mVelocity = 30;
        mDirection = 1;
        mTheHeight = theHeight;
        mTheWidth = theWidth;
        moveX = (int)(Math.ceil(Math.random()*mTheWidth));
        moveY = (int)(Math.random()*mTheHeight);
    }

    //proportional movement
    public void move(){
        int distX = moveX - mX;
        int distY = moveY -  mY;
        mX += distX / mVelocity;
        mY += distY / mVelocity;

        if (moveX < x)
            mDirection = -1;
        else
            mDirection = 1;

        if (Math.abs(distX) < 5 && Math.abs(distY) < 5) {
            moveX = (int) (Math.ceil(Math.random() * mTheWidth));
            moveY = (int) (Math.random() * mTheHeight/2);
        }
    }

    public int getFacingDirection() {
        return mDirection;
    }
}


