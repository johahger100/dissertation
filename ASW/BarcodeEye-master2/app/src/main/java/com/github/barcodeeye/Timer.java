package com.github.barcodeeye;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jhager on 2015-03-23.
 */
public class Timer {
    private static Timer ourInstance = new Timer();

    public static Timer getInstance() {
        return ourInstance;
    }

    private Timer() {
    }

    private boolean timerRunning = false;
    private Long startTime;
    private Long stopTime;

    public void startTimer() {
        if(timerRunning) {
            Log.d("TIMER", "Timer already running");
        }
        else {
            startTime = System.nanoTime();
            timerRunning = true;
        }
    }

    public void stopTimer() {
        if(!timerRunning) {
            Log.d("TIMER", "No timer running");
        }
        else {
            stopTime = System.nanoTime();
            timerRunning = false;
        }
    }

    private long getElapsedTime() {
        return stopTime - startTime;
    }

    public void logElapsedTime(String information) {
        Log.d("TIMER", information + ": " + String.valueOf(getElapsedTime()) + " nano seconds");
    }

    public void endOfRun()
    {
        Log.d("TIMER", "* * * * *");
    }
}

