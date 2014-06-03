package com.example.healthhelper;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class StepService extends Service{
	private SensorManager mSensorManager;
    private Sensor mSensor;
    private StepDetector mStepDetector;
    private StepDisplayer mStepDisplayer;
    private SharedPreferences mState;
    
    private int mSteps;
    private HealthHelper appHealthHelper;
    
    public class StepBinder extends Binder {
        StepService getService() {
            return StepService.this;
        }
    }
    
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public void onCreate() {
		super.onCreate();
		
		appHealthHelper = (HealthHelper)getApplicationContext();
		
		mStepDetector = new StepDetector();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        registerDetector();
        
        mStepDisplayer = new StepDisplayer();
        mStepDisplayer.setSteps(0);
        mStepDisplayer.addListener(mStepListener);
        mStepDetector.addStepListener(mStepDisplayer);
	}
	
	@Override
    public void onStart(Intent intent, int startId) {        
        super.onStart(intent, startId);
    }
	
	@Override
    public void onDestroy() {
		unregisterDetector();
		super.onDestroy();
	}
	
	private void registerDetector() {
        mSensor = mSensorManager.getDefaultSensor(
            Sensor.TYPE_ACCELEROMETER /*| 
            Sensor.TYPE_MAGNETIC_FIELD | 
            Sensor.TYPE_ORIENTATION*/);
        mSensorManager.registerListener(mStepDetector,
            mSensor,
            SensorManager.SENSOR_DELAY_FASTEST);
    }
	
	private void unregisterDetector() {
        mSensorManager.unregisterListener(mStepDetector);
    }
	
	public interface ICallback {
        public void stepsChanged(int value);
        public void paceChanged(int value);
        public void distanceChanged(float value);
        public void speedChanged(float value);
        public void caloriesChanged(float value);
    }
    
    private ICallback mCallback;

    public void registerCallback(ICallback cb) {
        mCallback = cb; 
    }	  
    
	private StepDisplayer.Listener mStepListener = new StepDisplayer.Listener() {        		
		public void stepsChanged(int value) {
            mSteps = value;
            appHealthHelper.setStep(mSteps);
            passValue();
        }
        public void passValue() {
            if (mCallback != null) {
                mCallback.stepsChanged(mSteps);
            }
        }
    };
}
