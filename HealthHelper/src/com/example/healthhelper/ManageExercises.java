package com.example.healthhelper;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ManageExercises extends Activity {
	private ListView m_ListView;
	private HealthHelper appHealthHelper;
	private ArrayAdapter<String> Adapter;
	private List<String> data = new ArrayList<String>();
	private Button button_new;
	private RadioButton m_rButton0,m_rButton1,m_rButton2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_exercises);
		
		appHealthHelper = (HealthHelper)getApplicationContext();
		m_ListView = (ListView) findViewById(R.id.listView1);
		button_new = (Button) findViewById(R.id.button1);		
		m_rButton0 = (RadioButton) findViewById(R.id.radio0);
		m_rButton1 = (RadioButton) findViewById(R.id.radio1);
		m_rButton2 = (RadioButton) findViewById(R.id.radio2);
		
		//--------------listView-------
		Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData());
		m_ListView.setAdapter(Adapter);
		
		m_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				List<Exercise> list = appHealthHelper.getExerManager().getExercisesList(); 
				if (m_rButton0.isChecked()) {
					try {
						appHealthHelper.setCurrentExercise(list.get(arg2));												
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Intent intent = new Intent();
					intent.setClass(ManageExercises.this, ShowExercise.class);
					startActivity(intent);
					ManageExercises.this.finish();	
				}
				else if (m_rButton1.isChecked()) {
					list.remove(arg2);
					getData();					
					Adapter.notifyDataSetChanged();
				}
				else if (m_rButton2.isChecked()) {					
					try {
						appHealthHelper.setCurrentExercise(list.get(arg2));
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (appHealthHelper.getCurrExercise().getfinish() == false) {
						appHealthHelper.setEtype(1);
						appHealthHelper.setNum(arg2);
						Intent intent = new Intent();
						intent.setClass(ManageExercises.this, StepCount.class);
						startActivity(intent);
						ManageExercises.this.finish();
					}
					else {
						appHealthHelper.DisplayToast("这个运动计划已完成！");
					}				
				}				
			}			
		});		
		
		//-----------button-----------
		button_new.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ManageExercises.this, MakePlan.class);
				startActivity(intent);
				ManageExercises.this.finish();	
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_exercises, menu);
		return true;
	}
	
	private List<String> getData() {    	    
	    List<Exercise> list = appHealthHelper.getExerManager().getExercisesList(); 	    
	    	    
	    data.clear();
	    if (!list.isEmpty()) {
	    	for (int i = 0; i < list.size(); ++i) {
	    		String string = "运动类型：" + list.get(i).getType() +
	    						"\n运动地点：" + list.get(i).getDest();	    						
	    		data.add(string);
	    	}	    			    
	    }  	    	    	   
	    return data;
	}	
	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
			Intent intent = new Intent();
			intent.setClass(ManageExercises.this, MainActivity.class);
			startActivity(intent);
			ManageExercises.this.finish();	
			return true;
		}
		return false;
	}		 	
}
