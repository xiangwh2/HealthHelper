package com.example.healthhelper;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ManageFriend extends Activity {
	private ListView mListView;
	private HealthHelper appHealthHelper;
	private ArrayAdapter<String> Adapter;
	private List<String> data = new ArrayList<String>();
	private Button button_new;
	private int select;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_friend);
		
		appHealthHelper = (HealthHelper)getApplicationContext();
		mListView = (ListView) findViewById(R.id.listView1);
		button_new = (Button) findViewById(R.id.button1);
		
		//--------------listView-------
		Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData());
		mListView.setAdapter(Adapter);
								
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
			}			
		});
		
		//-------------button---------
		
		button_new.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Friend friend = new Friend("ÕÅÈý", 18, "ÄÐ");				
				appHealthHelper.getFriManager().getFriendList().add(friend);
				getData();
				Adapter.notifyDataSetChanged();				
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_friend, menu);
		return true;
	}

	private List<String> getData() {    	    
	    List<Friend> list = appHealthHelper.getFriManager().getFriendList(); 	    
	    
	    data.clear();
	    if (!list.isEmpty()) {
	    	for (int i = 0; i < list.size(); ++i) {
	    		String string = list.get(i).getName() + "--" + list.get(i).getAge() + "--" + list.get(i).getSex();
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
			intent.setClass(ManageFriend.this, MainActivity.class);
			startActivity(intent);
			ManageFriend.this.finish();	
			return true;
		}
		return false;
	}
}
