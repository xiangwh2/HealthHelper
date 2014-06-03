package com.example.healthhelper;

import java.util.ArrayList;
import java.util.List;

public class ExercisePlan implements Cloneable{
	private List<Exercise> exercises;
	
	public ExercisePlan() {
		// TODO Auto-generated constructor stub
		exercises = new ArrayList<Exercise>(exercises);
	}
	
	public void addOneExercise(Exercise exer) {
		exercises.add(exer);
	}
	
	public Object clone() throws CloneNotSupportedException {
		ExercisePlan cloned = (ExercisePlan) super.clone();		
		cloned.exercises.addAll(exercises);		
		return cloned;
	}
}
