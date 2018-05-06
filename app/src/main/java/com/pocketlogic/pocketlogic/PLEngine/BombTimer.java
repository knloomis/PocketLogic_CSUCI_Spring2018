package com.pocketlogic.pocketlogic.PLEngine;

import android.os.CountDownTimer;

import com.pocketlogic.pocketlogic.PocketLogic.Director;

public class BombTimer extends CountDownTimer {

    Director director;

    public BombTimer(Director director, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.director = director;
    }

    public void onTick(long millisUntilFinished){
        director.bombTick((int)(millisUntilFinished / 1000));
    }

    public void onFinish() {
        director.bombExplode();
    }
}
