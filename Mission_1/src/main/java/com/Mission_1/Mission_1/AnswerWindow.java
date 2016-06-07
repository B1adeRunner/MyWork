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
	private Dimension sizeScreen;
	
	public AnswerWindow(){
		
	}
	
	public void compile(){
		createNecessaryObjects();
		buildAnswerWindowPanel();
		setMenuInitialLocation();
		buildAnswerWindow();
	}
	
	private void buildAnswerWindow(){
		answerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		answerWindow.setSize(350, 350);
		answerWindow.setResizable(true);
		answerWindow.add(answerWindowPanel);
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
		sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public void buildAnswerTextArea(String answer){
		answerTextArea.setText(answer);
	}
	
	private void setMenuInitialLocation(){
		answerWindow.setLocation((int)(sizeScreen.getWidth() - answerWindow.getSize().getWidth())/2,
				(int)(sizeScreen.height - answerWindow.getSize().getHeight())/3);
	}
}
