package com.example.tjl01030.duckhunt;

import android.graphics.Rect;
import android.media.Image;
import android.media.MediaPlayer;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by tjl01030 on 12/5/2016.
 */

public class Duck {
    private int duckX, duckY, REVERSE, mDirection, theWidth, theHeight;
    private double mVelocityX, mVelocityY;
    int x, y;

    public double setVelocityX(double velocity) { mVelocityX = velocity;
        return velocity;
    }

    public double setVelocityY(double velocity) { mVelocityY = velocity;
        return velocity;
    }

    public void setX(int x){duckX = x;}
    public int getX(){return duckX;}
    public void setY(int y){duckY = y;}
    public int getY(){return duckY;}

    public Duck (int x, int y, int mWidth, int mHeight){
        theWidth = mWidth;
        theHeight = mHeight;
        this.x = x;
        this.y = y;
        mDirection = 1;
    }



    public void move(ImageView stageV) {
        duckX += 200/mVelocityX;
        duckY += 200/mVelocityY;

        if (duckX > theWidth-150) {
            duckX = theWidth-150;
            mVelocityX *= -1;
            mDirection = -1;
        }
        else if (duckX < stageV.getLeft()) {
            duckX = stageV.getLeft();
            mVelocityX *= -1;
            mDirection = 1;
        }

        if (duckY < stageV.getTop()) {
            duckY = stageV.getTop();
            mVelocityY *= -1;
        }
        else if (duckY > stageV.getBottom()-300) {
            duckY = stageV.getBottom()-300;
            mVelocityY *= -1;
        }


    }

    public int getFacingDirection() {
        return mDirection;
    }
}
