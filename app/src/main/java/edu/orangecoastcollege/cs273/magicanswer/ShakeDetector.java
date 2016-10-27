package edu.orangecoastcollege.cs273.magicanswer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by jcabrera31 on 10/27/2016.
 */

public class ShakeDetector implements SensorEventListener{

    //constant to represent a shake threshold
    private static final float SHAKE_THRESHOLD = 25f;

    //constant to represent how long between shakes (milliseconds)
    private static final int SHAKE_TIME_LAPSE = 2000;

    //what was the last time the even occured
    private long timeOfLastShake;

    //define a listener to register onShake events
    private OnShakeListener shakeListener;

    // Constructor to create a new SHakeDetector passing in an OnShakeListener as argument:
    public ShakeDetector(OnShakeListener listener)
    {
        shakeListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //Determine if the event is an accelerometer
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            //Get the x, y, z values when this event occurs:
            //values is an array of floating point values
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Compare all 3 values against gravity
            float gForceX = x- SensorManager.GRAVITY_EARTH;
            float gForceY = y- SensorManager.GRAVITY_EARTH;
            float gForceZ = z- SensorManager.GRAVITY_EARTH;

            //compute sume of squares
            double vector = gForceX*gForceX +gForceY*gForceY +gForceZ*gForceZ;

            //compute the gForce;
            float gForce = (float) Math.sqrt(vector);



            //compare gForce against the threshold
            if(gForce > SHAKE_THRESHOLD)
            {
                //get the current time
                long now = System.currentTimeMillis();

                //compare to see if the current time is at least 2000 milliseconds
                //greater than the time of the laste shake
                if(now-timeOfLastShake >= SHAKE_TIME_LAPSE)
                {
                    timeOfLastShake = now;
                    //register a shake event
                    shakeListener.onShake();

                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //define our own interface (methodfor aother classes to implement)
    //called onShake()
    //It's the responsibility of MagicAnswerActivity to implement this method
    public interface OnShakeListener
    {
        void onShake();

    }
}
