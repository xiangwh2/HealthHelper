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
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class MakeExercise extends Activity {
	private EditText m_EditTextDate;
	private EditText m_EditTextTime;
	private Spinner m_Spinner;
	private RadioGroup m_rGroup;
	private RadioButton m_rButton0,m_rButton1;
	private static final String[] m_dest = {"中大","华师"};
	private ArrayAdapter<String> adapter;
	private HealthHelper appHealthHelper;
	private String eType, dest, eTime;	
	private boolean start;
	private Button button_start;
	private EditText showDate = null;
	private Button pickDate = null;
	private EditText showTime = null;
	private Button pickTime = null;
	
	private static final int SHOW_DATAPICK = 0; 
    private static final int DATE_DIALOG_ID = 1;  
    private static final int SHOW_TIMEPICK = 2;
    private static final int TIME_DIALOG_ID = 3;
    
    private int mYear;  
    private int mMonth;
    private int mDay; 
    private int mHour;
    private int mMinute;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		eType = "跑步";
		dest = "中大";
		eTime = "0000-00-00";
		start = false;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_exercise);
		 initializeViews();
	        
	        final Calendar c = Calendar.getInstance();
	        mYear = c.get(Calendar.YEAR);  
	        mMonth = c.get(Calendar.MONTH);  
	        mDay = c.get(Calendar.DAY_OF_MONTH);
	        
	        mHour = c.get(Calendar.HOUR_OF_DAY);
	        mMinute = c.get(Calendar.MINUTE);
	        
	        setDateTime(); 
	        setTimeOfDay();		
		appHealthHelper = (HealthHelper)getApplicationContext();
		m_EditTextDate = (EditText) findViewById(R.id.showdate);
		m_EditTextTime = (EditText) findViewById(R.id.showtime);
		m_Spinner = (Spinner) findViewById(R.id.spinner1);
		m_rGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		m_rButton0 = (RadioButton) findViewById(R.id.radio0);
		m_rButton1 = (RadioButton) findViewById(R.id.radio1);		
		button_start = (Button) findViewById(R.id.button1);		
		
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
				start = true;
				eTime = m_EditTextDate.getText().toString()+"   "+m_EditTextTime.getText().toString();												
				Exercise oneExercise = new Exercise(eType, eTime, dest);
				appHealthHelper.getExerManager().addOneExercise(oneExercise);									
				
				try {					
					appHealthHelper.setCurrentExercise(oneExercise);				
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Intent intent = new Intent();
				intent.setClass(MakeExercise.this, StepCount.class);
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
		   
    private void initializeViews(){
        showDate = (EditText) findViewById(R.id.showdate);  
        pickDate = (Button) findViewById(R.id.pickdate); 
        showTime = (EditText)findViewById(R.id.showtime);
        pickTime = (Button)findViewById(R.id.picktime);
        
        pickDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	           Message msg = new Message(); 
	           if (pickDate.equals((Button) v)) {  
	              msg.what = MakeExercise.SHOW_DATAPICK;  
	           }  
	           MakeExercise.this.dateandtimeHandler.sendMessage(msg); 
			}
		});
        
        pickTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	           Message msg = new Message(); 
	           if (pickTime.equals((Button) v)) {  
	              msg.what = MakeExercise.SHOW_TIMEPICK;  
	           }  
	           MakeExercise.this.dateandtimeHandler.sendMessage(msg); 
			}
		});
    }

    /**
     * 设置日期
     */
	private void setDateTime(){
       final Calendar c = Calendar.getInstance();  
       
       mYear = c.get(Calendar.YEAR);  
       mMonth = c.get(Calendar.MONTH);  
       mDay = c.get(Calendar.DAY_OF_MONTH); 
  
       updateDateDisplay(); 
	}
	
	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay(){
       showDate.setText(new StringBuilder().append(mYear).append("-")
    		   .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
               .append((mDay < 10) ? "0" + mDay : mDay)); 
	}
	
    /** 
     * 日期控件的事件 
     */  
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {  
  
       public void onDateSet(DatePicker view, int year, int monthOfYear,  
              int dayOfMonth) {  
           mYear = year;  
           mMonth = monthOfYear;  
           mDay = dayOfMonth;  

           updateDateDisplay();
       }  
    }; 
	
	/**
	 * 设置时间
	 */
	private void setTimeOfDay(){
	   final Calendar c = Calendar.getInstance(); 
       mHour = c.get(Calendar.HOUR_OF_DAY);
       mMinute = c.get(Calendar.MINUTE);
       updateTimeDisplay();
	}
	
	/**
	 * 更新时间显示
	 */
	private void updateTimeDisplay(){
       showTime.setText(new StringBuilder().append(mHour).append(":")
               .append((mMinute < 10) ? "0" + mMinute : mMinute)); 
	}
    
    /**
     * 时间控件事件
     */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			
			updateTimeDisplay();
		}
	};
    
    @Override  
    protected Dialog onCreateDialog(int id) {  
       switch (id) {  
       case DATE_DIALOG_ID:  
           return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,  
                  mDay);
       case TIME_DIALOG_ID:
    	   return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
       }
    	   
       return null;  
    }  
  
    @Override  
    protected void onPrepareDialog(int id, Dialog dialog) {  
       switch (id) {  
       case DATE_DIALOG_ID:  
           ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);  
           break;
       case TIME_DIALOG_ID:
    	   ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
    	   break;
       }
    }  
  
    /** 
     * 处理日期和时间控件的Handler 
     */  
    Handler dateandtimeHandler = new Handler() {
  
       @Override  
       public void handleMessage(Message msg) {  
           switch (msg.what) {  
           case MakeExercise.SHOW_DATAPICK:  
               showDialog(DATE_DIALOG_ID);  
               break; 
           case MakeExercise.SHOW_TIMEPICK:
        	   showDialog(TIME_DIALOG_ID);
        	   break;
           }  
       }  
  
    }; 
}
