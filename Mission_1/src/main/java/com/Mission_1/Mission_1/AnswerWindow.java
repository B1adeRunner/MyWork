package com.Mission_1.Mission_1;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AnswerWindow {
	private JFrame answerWindow;
	private JPanel answerWindowPanel;
	private JLabel answerWindowLabel;
	private JTextArea answerTextArea;
	private Dimension screenSize;
	private int screenWidth;
	private int screenHeight;
	private int answerWindowWidth;
	private int answerWindowHeight;
	
	public AnswerWindow(){
		
	}
	
	public void compile(){
		createNecessaryObjects();
		buildAnswerWindowPanel();
		setAnswerWindowInitialLocation();
		buildAnswerWindow();
	}
	
	private void buildAnswerWindow(){
		setAnswerWindowDefaultCloseOperation();
		setAnswerWindowSize();
		setAnswerWindowResizable();
		addAnswerWindowPanelToAnswerWindow();
		setAnswerWindowVisible();
	}
	
	private void setAnswerWindowDefaultCloseOperation(){
		answerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setAnswerWindowSize(){
		answerWindow.setSize(360, 360);
	}
	
	private void setAnswerWindowResizable(){
		answerWindow.setResizable(true);
	}
	
	private void addAnswerWindowPanelToAnswerWindow(){
		answerWindow.add(answerWindowPanel);
	}
	
	private void setAnswerWindowVisible(){
		answerWindow.setVisible(true);
	}
	
	private void buildAnswerWindowPanel(){
		answerWindowPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		answerWindowPanel.add(answerWindowLabel);
		answerWindowPanel.add(answerTextArea);
	}
	
	private void createNecessaryObjects(){
		answerWindow = new JFrame();
		answerWindowPanel = new JPanel();
		answerTextArea = new JTextArea();
		answerWindowLabel = new JLabel("Решение СЛАУ:");
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int)screenSize.getWidth();
		screenHeight = (int)screenSize.getHeight();
		answerWindowWidth = (int)answerWindow.getSize().getWidth();
		answerWindowHeight = (int)answerWindow.getSize().getHeight();
	}
	
	public void buildAnswerTextArea(String answer){
		answerTextArea.setText(answer);
	}
	
	private void setAnswerWindowInitialLocation(){
		answerWindow.setLocation((screenWidth - answerWindowWidth)/2, (screenHeight - answerWindowHeight)/3);
	}
}
