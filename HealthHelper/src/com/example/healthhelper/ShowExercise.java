package com.example.healthhelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;

public class ShowExercise extends Activity {
	private TextView mTextView1,mTextView2,mTextView3;
	private TextView mTextView4,mTextView5,mTextView6;
	private HealthHelper appHealthHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_exercise);
		
		appHealthHelper = (HealthHelper)getApplicationContext();
		mTextView1 = (TextView) findViewById(R.id.textView2);
		mTextView2 = (TextView) findViewById(R.id.textView3);
		mTextView3 = (TextView) findViewById(R.id.textView4);
		mTextView4 = (TextView) findViewById(R.id.textView5);
		mTextView5 = (TextView) findViewById(R.id.textView6);
		mTextView6 = (TextView) findViewById(R.id.textView7);
		
		mTextView1.setText("运动类型:" + appHealthHelper.getCurrExercise().getType());
		mTextView2.setText("运动开始时间:" + appHealthHelper.getCurrExercise().getTime());
		mTextView3.setText("运动开始地点:" + appHealthHelper.getCurrExercise().getDest());
		mTextView4.setText("运动总时间:" + appHealthHelper.getCurrExercise().getTotalTime());
		mTextView5.setText("步数:" + appHealthHelper.getCurrExercise().getCount());
		mTextView6.setText("卡路里消耗量:" + appHealthHelper.getCurrExercise().getTotalCal());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_exercise, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
			Intent intent = new Intent();
			intent.setClass(ShowExercise.this, MainActivity.class);
			startActivity(intent);
			ShowExercise.this.finish();	
			return true;
		}
		return false;
	}	

}
