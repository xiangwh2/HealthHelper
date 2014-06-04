package com.example.healthhelper;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;

public class Exercise implements Cloneable{
	private String ExerType;      //�˶�����
	private String ExerTime;      //��ʼʱ��
	private int ExerCal;          //Ԥ����·����
	private int TotalTime;        //���˶�ʱ��
	private int TotalCal;         //�ܿ�·����
	private String Destination;   //Ԥ���ص�
	private int Count;            //����
	private List<Friend> friends;
	private boolean finish;
	
	public Exercise(String EType, String ETime, String Dest) {
		ExerType = EType;
		ExerTime = ETime;
		Destination = Dest;
		ExerCal = Count = TotalCal = TotalTime = 0;
		finish = false;
		friends = new ArrayList<Friend>();
	}
	
	public Exercise(String EType, String ETime, String Dest, int ECal) {
		// TODO Auto-generated constructor stub
		ExerType = EType;
		ExerTime = ETime;
		Destination = Dest;
		ExerCal = ECal;
		Count = TotalCal = TotalTime = 0;
		friends = new ArrayList<Friend>();
		finish = false;
	}
	
	public Exercise(String EType, String ETime, String Dest, int ECal, List<Friend> fs) {
		ExerType = EType;
		ExerTime = ETime;
		Destination = Dest;
		ExerCal = ECal;
		Count = TotalCal = TotalTime = 0;
		friends = new ArrayList<Friend>();
		finish = false;
	}
	
	public int getTotalTime() {
		return TotalTime;
	}
	
	public void setTotalTime(int t) {
		TotalTime = t;
	}
	
	public int getTotalCal() {
		return TotalCal;
	}
	
	public void setTotalCal(int c) {
		TotalCal = c;
	}
	
	public String getTime() {
		return ExerTime;
	}
	
	public String getType() {
		return ExerType;
	}
	
	public String getDest() {
		return Destination;
	}
	
	public int getCount() {
		return Count;
	}
	
	public void setCount(int c) {
		Count = c;
	}
	
	public void setfinish(boolean b) {
		finish = b;
	}
	
	public boolean getfinish() {
		return finish;
	}
	
	public Object clone() throws CloneNotSupportedException   {
		Exercise cloned = (Exercise) super.clone();		
		cloned.friends.addAll(friends);
		return cloned;
	}
}
