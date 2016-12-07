package com.example.tjl01030.duckhunt;

import android.graphics.Rect;
import android.widget.ImageView;

/**
 * Created by tjl01030 on 12/5/2016.
 */

public class Duck {
    private int duckX, duckY, REVERSE, mDirection;
    private double mVelocityX, mVelocityY;
    Rect field, duck;
    int x, y;

    public double setVelocityX(double velocity) { mVelocityX = velocity;
        return velocity;
    }
    //public double getVelocityX() {return mVelocityX;}
    public double setVelocityY(double velocity) { mVelocityY = velocity;
        return velocity;
    }
    //public double getVelocityY() {return mVelocityY;}

    public void setX(int x){duckX = x;}
    public int getX(){return duckX;}
    public void setY(int y){duckY = y;}
    public int getY(){return duckY;}

    public Duck (int x, int y){
        this.x = x;
        this.y = y;
        mDirection = 1;
    }

    public void move(ImageView fieldv, ImageView duckv) {
        REVERSE = -1;
        duckX += mVelocityX;
        duckY += mVelocityY;

        if (Math.abs(mVelocityX) < 1){
            mVelocityX = 2;
        }
        if (Math.abs(mVelocityY) < 1){
            mVelocityY = 2;
        }

        field = new Rect(fieldv.getLeft(), fieldv.getTop(), fieldv.getRight(), fieldv.getBottom());
        duck = new Rect(duckv.getLeft(), duckv.getTop(), duckv.getRight(), duckv.getBottom());

        if(Rect.intersects(field, duck)){
            duckX *= REVERSE;
            duckY *= REVERSE;
        }
    }
}
