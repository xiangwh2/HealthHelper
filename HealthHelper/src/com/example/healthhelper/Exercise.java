package com.example.healthhelper;

import java.util.ArrayList;
import java.util.List;

public class Exercise implements Cloneable{
	private String ExerType;      //�˶�����
	private String ExerTime;      //��ʼʱ��
	private int ExerCal;          //Ԥ����·����
	private int TotalTime;        //���˶�ʱ��
	private int TotalCal;         //�ܿ�·����
	private String Destination;   //Ԥ���ص�
	private int Count;            //����
	private List<Friend> friends;
	
	public Exercise(String EType, String ETime, String Dest) {
		ExerType = EType;
		ExerTime = ETime;
		Destination = Dest;
		ExerCal = Count = TotalCal = TotalTime = 0;		
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
	}
	
	public Exercise(String EType, String ETime, String Dest, int ECal, List<Friend> fs) {
		ExerType = EType;
		ExerTime = ETime;
		Destination = Dest;
		ExerCal = ECal;
		Count = TotalCal = TotalTime = 0;
		friends = new ArrayList<Friend>();
	}
	
	public int getTotalTime() {
		return TotalTime;
	}
	
	public int getTotalCal() {
		return TotalCal;
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
	
	public Object clone() throws CloneNotSupportedException   {
		Exercise cloned = (Exercise) super.clone();		
		cloned.friends.addAll(friends);
		return cloned;
	}
}
