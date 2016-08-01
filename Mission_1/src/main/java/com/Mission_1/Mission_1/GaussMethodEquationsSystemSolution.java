package com.Mission_1.Mission_1;

import java.util.Observable;

public class GaussMethodEquationsSystemSolution extends Observable implements EquationsSystemSolution {
	private int rowCount;
	private float[][] dataSource;
	private float[] arrayOfFreeMembers;
	private float supportingCoefficient;
	private StringBuilder answer;
	
	public StringBuilder getAnswer() {
		return answer;
	}

	public GaussMethodEquationsSystemSolution(DataCollector dataCollector) {
		this.dataSource = dataCollector.getDataSource();
		this.rowCount = dataCollector.getRowCount();
		answer = new StringBuilder();
	}
	
 	private void addTheMatrixToTheAnswer(){
 		answer.append("\n");
 		for(float[] array: dataSource) {
 			for(float value: array){
 				answer.append(value + "   ");
 			}
 			answer.append("\n");
 		}
 	}
 	
	private void compileArrayOfFreeMembers(){
 		arrayOfFreeMembers = new float[rowCount];
 		for (int i = 0; i < rowCount; i++) {
 		    arrayOfFreeMembers[i] = dataSource[i][rowCount];
 		}
	}
	
	private void forwardStroke(){
 		for (int i = 1; i < rowCount; i++) {
 			for (int j = i; j < rowCount; j++) {
 				supportingCoefficient = dataSource[j][i - 1] / dataSource[i-1][i-1];
 				for (int t = 0; t < dataSource[j].length; t++) {
 					dataSource[j][t] = dataSource[j][t] - supportingCoefficient * dataSource[i-1][t];
 				}
                arrayOfFreeMembers[j] = arrayOfFreeMembers[j] - supportingCoefficient * arrayOfFreeMembers[i-1];
 			}
 		}
	}
	
	public void checkForConsistencyOfTheSystem() {
		for(int i=0; i<rowCount; i++){
			int k=0;
			for(int j=0; j<rowCount; j++){
				if(dataSource[i][j] == 0)
					k++;
			}
			if(k>=rowCount)
				try {
					throw new Exception("Система не имеет единственного решения");
				} catch (Exception e) {
					answer.append("\n").append(e.getMessage());
					e.printStackTrace();
				}
		}		
	}
	
	private void reverseStroke(){
 		for (int i=rowCount-1; i>=0; i--) {
 			for (int j=i+1; j<rowCount; j++) 
 				arrayOfFreeMembers[i]-=dataSource[i][j]*arrayOfFreeMembers[j];
 			arrayOfFreeMembers[i] = arrayOfFreeMembers[i] / dataSource[i][i];
 		}
	}
	
	private void addTargetVariablesToTheAnswer(){
		int i=0;
		for (float value: arrayOfFreeMembers) {
 			answer.append("\n").append("x"+i+" = "+value);
 			i++;
 		}
	}
	
	private void notifyController(){
		setChanged();
		notifyObservers();
	}
	
	public void solution() {
		addTheMatrixToTheAnswer();
		compileArrayOfFreeMembers();
		forwardStroke();
 		addTheMatrixToTheAnswer();
 		checkForConsistencyOfTheSystem();
 		reverseStroke();
 		addTargetVariablesToTheAnswer();
 		notifyController();
	}
}
