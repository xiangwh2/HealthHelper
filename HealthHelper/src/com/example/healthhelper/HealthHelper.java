package com.example.healthhelper;

import android.app.Application;
import android.widget.Toast;

public class HealthHelper extends Application{
	private ExerciseManager exerciseManager;
	private FriendManager friendManager;
	//private ExercisePlan currentEP;
	private Exercise currentE;
	private int step;
	
	public HealthHelper() {
		exerciseManager = new ExerciseManager();
		friendManager = new FriendManager();
		step = 0;
	}
	
	public ExerciseManager getExerManager() {
		return exerciseManager;
	}
	
	public FriendManager getFriManager() {
		return friendManager;
	}
	
	/*public ExercisePlan getCurrExercisePlan() {
		return currentEP;
	}*/
	
	public Exercise getCurrExercise() {
		return currentE;
	}
	
	public void setCurrentExercise(Exercise exer) throws CloneNotSupportedException {
		currentE = (Exercise) exer.clone();
	}
	
	public void setStep(int s) {
		step = s;
	}
	
	public int getStep() {
		return step;
	}
	
	/*public void setCurrentExercisePlan(ExercisePlan exerP) throws CloneNotSupportedException {
		currentEP = (ExercisePlan) exerP.clone();
	}*/
	
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
