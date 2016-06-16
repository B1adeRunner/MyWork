package com.Mission_1.Mission_1;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
	private MenuWindow menuWindow;
	private DataCollector dataCollector;
	private GaussMethodEquationsSystemSolution gaussMethodEquationsSystemSolution;
	private AnswerWindow answerWindow;

	public Controller() {
		menuWindow = new MenuWindow();
		dataCollector = new DataCollector();
		menuWindow.addObserver(this);
		answerWindow = new AnswerWindow();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o.getClass().equals(menuWindow.getClass())) {
			startDataCollector();
			startGaussMethod();
		}else
			if(o.getClass().equals(gaussMethodEquationsSystemSolution.getClass())){
				answerWindow.compile();
				answerWindow.buildAnswerTextArea(gaussMethodEquationsSystemSolution.getAnswer().toString());
			}
	}
	
	public void openGui() {
		menuWindow.compile();
	}

	public void startDataCollector() {
		dataCollector.setFilePath(menuWindow.getFilePath());
		dataCollector.gatherData(); 
	}
	
	public void startGaussMethod() {
		gaussMethodEquationsSystemSolution = new GaussMethodEquationsSystemSolution(dataCollector);
		gaussMethodEquationsSystemSolution.addObserver(this);
		gaussMethodEquationsSystemSolution.solution();
	}
}
