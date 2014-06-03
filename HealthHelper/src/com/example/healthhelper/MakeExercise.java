package com.example.healthhelper;

import java.security.PublicKey;
import android.R.bool;
import android.R.string;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MakeExercise extends Activity {
	private EditText m_EditText;
	private Spinner m_Spinner;
	private RadioGroup m_rGroup;
	private RadioButton m_rButton0,m_rButton1;
	private static final String[] m_dest = {"中大","华师"};
	private ArrayAdapter<String> adapter;
	private HealthHelper appHealthHelper;
	private String eType, dest, eTime;	
	private boolean start;
	private Button button_start, button_show;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		eType = "跑步";
		dest = "中大";
		eTime = "0000-00-00";
		start = false;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_exercise);
					
		appHealthHelper = (HealthHelper)getApplicationContext();
		m_EditText = (EditText) findViewById(R.id.editText1);
		m_Spinner = (Spinner) findViewById(R.id.spinner1);
		m_rGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		m_rButton0 = (RadioButton) findViewById(R.id.radio0);
		m_rButton1 = (RadioButton) findViewById(R.id.radio1);		
		button_start = (Button) findViewById(R.id.button1);
		button_show = (Button) findViewById(R.id.button2);	
		
		//-----Spinner-----
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,m_dest);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		m_Spinner.setAdapter(adapter);
		
		m_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
			@Override 
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) { 				
				dest = (String)adapterView.getItemAtPosition(position); 
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});
		
		//----------Button-----------		
		button_start.setText("开始");		
		button_start.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startStepService();
				
				if (button_start.getText().toString() == "开始") {				
					button_start.setText("停止");	
					start = true;
					eTime = m_EditText.getText().toString();												
					Exercise oneExercise = new Exercise(eType, eTime, dest);
					appHealthHelper.getExerManager().addOneExercise(oneExercise);
					
					int s = appHealthHelper.getStep();
					appHealthHelper.DisplayToast("Step:" + s);
					
					try {					
						appHealthHelper.setCurrentExercise(oneExercise);				
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {				
					button_start.setText("开始");
					start = false;
				}					
			}
		});
		
		button_show.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MakeExercise.this, ShowExercise.class);
				startActivity(intent);
				MakeExercise.this.finish();	
			}
		});
		
		//--------Radio----------------------------
		m_rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == m_rButton0.getId()) {
					eType = m_rButton0.getText().toString();					
				}
				else if (checkedId == m_rButton1.getId()) {
					eType = m_rButton1.getText().toString();		
				}
				appHealthHelper.DisplayToast(eType);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.make_exercise, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
			Intent intent = new Intent();
			intent.setClass(MakeExercise.this, MainActivity.class);
			startActivity(intent);
			MakeExercise.this.finish();	
			return true;
		}
		return false;
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
	
    private void startStepService() {        
            startService(new Intent(MakeExercise.this,
                    StepService.class));
    }
    
    private void bindStepService() {   
        bindService(new Intent(MakeExercise.this, 
                StepService.class), mConnection, Context.BIND_AUTO_CREATE + Context.BIND_DEBUG_UNBIND);
    }
	
}
