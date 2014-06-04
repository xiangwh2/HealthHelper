package com.example.healthhelper;

import java.io.IOException;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StepCount extends Activity {
	private TextView m_TextView,timeView,calView;
	private HealthHelper appHealthHelper;
	private int step;
	private Button button_stop;
	private int time;
	private int cal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step_count);
		
		appHealthHelper = (HealthHelper)getApplicationContext();
		m_TextView = (TextView) findViewById(R.id.textView2);
		timeView = (TextView) findViewById(R.id.textView4);
		calView = (TextView) findViewById(R.id.textView6);
		button_stop = (Button) findViewById(R.id.button1);
		time = cal = 0;
		
		appHealthHelper.setStep(0);
		m_TextView.setText(""+0);
		
		bindStepService();			
		
		new Thread(mRunnable).start();
		
		handler.postDelayed(runnable, 1000); 
		
		button_stop.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {													
				if (appHealthHelper.getEtype() == 0) {
					Exercise exercise = appHealthHelper.getCurrExercise();
					exercise.setCount(step);
					exercise.setTotalTime(time);
					exercise.setTotalCal(cal);
					appHealthHelper.getExerManager().addOneExercise(exercise);
					exercise.setfinish(true);
				}
				else if (appHealthHelper.getEtype() == 1) {
					List<Exercise> list = appHealthHelper.getExerManager().getExercisesList();
					Exercise exercise1 = appHealthHelper.getCurrExercise();
					exercise1.setCount(step);
					exercise1.setTotalTime(time);
					exercise1.setTotalCal(cal);
					
					Exercise exercise2 = list.get(appHealthHelper.getNum());
					exercise2.setCount(step);
					exercise2.setTotalTime(time);
					exercise2.setTotalCal(cal);
					exercise2.setfinish(true);					
				}
				
				Intent intent = new Intent();
				intent.setClass(StepCount.this, ShowExercise.class);
				startActivity(intent);
				StepCount.this.finish();	
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step_count, menu);
		return true;
	}
	
	private StepService mService;
    
	private ServiceConnection mConnection = new ServiceConnection() {
	    public void onServiceConnected(ComponentName className, IBinder service) {
	        mService = ((StepService.StepBinder)service).getService();
	    }
	
	    public void onServiceDisconnected(ComponentName className) {
	        mService = null;
	    }
	};
	
    private void bindStepService() {   
        bindService(new Intent(StepCount.this, 
                StepService.class), mConnection, Context.BIND_AUTO_CREATE + Context.BIND_DEBUG_UNBIND);
    }	
    
    private Runnable mRunnable = new Runnable() {
    	public void run() {
    		while(true) {
    			try {
    				Thread.sleep(1000);
    				mHandler.sendMessage(mHandler.obtainMessage());
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    };
    
    
    private static final int STEPS_MSG = 1;
    
    private Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
        	super.handleMessage(msg); 
        	refreshUI();
        }        
    };
    
    private void refreshUI() {
    	step = appHealthHelper.getStep();
    	m_TextView.setText("" + step);
    	cal = step/10;
    	calView.setText("" + cal);
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
			Intent intent = new Intent();
			intent.setClass(StepCount.this, MainActivity.class);
			startActivity(intent);
			StepCount.this.finish();	
			return true;
		}
		return false;
	}		
    
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {  
        @Override  
        public void run() {  
        	time++;  
            timeView.setText("" + time + " s");  
            handler.postDelayed(this, 1000);  
        }  
    };  
}
