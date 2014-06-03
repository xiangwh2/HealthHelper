package com.example.healthhelper;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
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

public class MakePlan extends Activity {
	private Button button_finish;
	private EditText edit_time, edit_alltime, edit_cal;
	private Spinner spin_dest, spin_friend;
	private RadioGroup m_rGroup;
	private RadioButton m_rButton0,m_rButton1;
	private static final String[] m_dest = {"中大","华师"};	
	private ArrayAdapter<String> adapter_dest, adapter_friend;
	private String eType, dest, eTime, eAllTime;
	private int eCal;	
	private HealthHelper appHealthHelper;
	private boolean change;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		eType = "跑步";
		dest = "中大";
		eTime = null;
		eAllTime = null;
		eCal = 0;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_plan);
		
		appHealthHelper = (HealthHelper)getApplicationContext();	
		//------------------button---------------
		button_finish = (Button) findViewById(R.id.button1);		
		button_finish.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				eTime = edit_time.getText().toString();
				eAllTime = edit_alltime.getText().toString();
				eCal = Integer.parseInt(edit_cal.getText().toString());
							
				Exercise oneExercise = new Exercise(eType, eTime, dest, eCal);
				appHealthHelper.getExerManager().getExercisesList().add(oneExercise);
				
				Intent intent = new Intent();
				intent.setClass(MakePlan.this, ManageExercises.class);
				startActivity(intent);
				MakePlan.this.finish();	
			}
			
		});
		
		//-----------------editView---------------
		edit_time = (EditText) findViewById(R.id.editText1);
		edit_alltime = (EditText) findViewById(R.id.editText2);
		edit_cal = (EditText) findViewById(R.id.editText3);
		
		//-----------------spinner----------------
		spin_dest = (Spinner) findViewById(R.id.spinner1);
		spin_friend = (Spinner) findViewById(R.id.spinner2);
		
		adapter_dest = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,m_dest);
		adapter_dest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spin_dest.setAdapter(adapter_dest);
		
		spin_dest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
			@Override 
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) { 				
				dest = (String)adapterView.getItemAtPosition(position); 
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});
		
		List<Friend> f_list = appHealthHelper.getFriManager().getFriendList();
		int size = f_list.size();
		String[] friends = new String [size];
		for (int i = 0; i < size; i++) friends[i] = f_list.get(i).getName();
		
		adapter_friend = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,friends);
		adapter_friend.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spin_friend.setAdapter(adapter_friend);
		
		spin_dest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
			@Override 
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) { 				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});
		
		//------------------radio---------------
		m_rGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		m_rButton0 = (RadioButton) findViewById(R.id.radio0);
		m_rButton1 = (RadioButton) findViewById(R.id.radio1);	
		
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
		getMenuInflater().inflate(R.menu.make_plan, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
			Intent intent = new Intent();
			intent.setClass(MakePlan.this, MainActivity.class);
			startActivity(intent);
			MakePlan.this.finish();	
			return true;
		}
		return false;
	}	
}
