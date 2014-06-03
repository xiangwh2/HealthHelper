package com.example.healthhelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button_start = (Button) findViewById(R.id.button1);
		Button button_manage = (Button) findViewById(R.id.button3);
		Button button_friend = (Button) findViewById(R.id.button4);
		
		button_start.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MakeExercise.class);
				startActivity(intent);
				MainActivity.this.finish();				
			}
		});
		
		button_manage.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ManageExercises.class);
				startActivity(intent);
				MainActivity.this.finish();				
			}
		});
		
		button_friend.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ManageFriend.class);
				startActivity(intent);
				MainActivity.this.finish();				
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}		
}
