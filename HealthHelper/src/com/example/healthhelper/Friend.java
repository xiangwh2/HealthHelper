package com.example.healthhelper;

import java.util.jar.Attributes.Name;

import com.example.healthhelper.R.string;

import android.R.integer;

public class Friend {
	private String Name;
	private int Age;
	private String Sex;
	
	public Friend(String name, int age, String sex) {
		// TODO Auto-generated constructor stub
		Name = name;
		Age = age;
		Sex = sex;
	}
	
	public String getName() {
		return Name;
	}
	
	public int getAge() {
		return Age;
	}
	
	public String getSex() {
		return Sex;
	}
}
