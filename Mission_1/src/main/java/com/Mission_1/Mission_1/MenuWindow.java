package com.Mission_1.Mission_1;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuWindow extends Observable implements Observer {
	private JFrame menu;
	private JFileChooser fileChooser;
	private JPanel menuPanel;
	private JButton buttonFileChooser;
	private FileChooserListener fileChooserListener;
	private JLabel menuLabel;
	private File targetFile;
	private String filePath;
	private Dimension sizeScreen;
	
	public MenuWindow(){
		
	}
	
	public void compile(){
		createNecessaryObjects();
		buildButtonFileChooser();
		registrationObservableObject();
		buildMenuPanel();
		setMenuInitialLocation();
		buildMenu();

	}
	
	private void buildButtonFileChooser(){
		buttonFileChooser.addActionListener(fileChooserListener);
	}
	
	private void registrationObservableObject(){
		fileChooserListener.addObserver(this);
	}
	
	private void buildMenu(){
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(150, 95);
		menu.setResizable(false);
		menu.add(menuPanel);
		menu.setVisible(true);
	}
	
	private void buildMenuPanel(){
		menuPanel.add(menuLabel);
		menuPanel.add(buttonFileChooser);
	}
	
	private void createNecessaryObjects(){
		menu = new JFrame();
		fileChooser = new JFileChooser();
		menuPanel = new JPanel();
		buttonFileChooser = new JButton("выбрать");
		fileChooserListener = new FileChooserListener();
		menuLabel = new JLabel("Выберите файл");
		sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public FileChooserListener getFileChooserListener() {
		return fileChooserListener;
	}

	class FileChooserListener extends Observable implements ActionListener{
		public FileChooserListener(){
			
		}
		
		@Override
		public void actionPerformed(ActionEvent event){
			fileChooser.showOpenDialog(fileChooser);
			targetFile = fileChooser.getSelectedFile();
			filePath = targetFile.getPath();
			System.out.println(filePath);
			setChanged();
			notifyObservers();
			menu.dispose();
		}
	}
	
	private void setMenuInitialLocation(){
		menu.setLocation((int)(sizeScreen.getWidth() - menu.getSize().getWidth())/2,
				(int)(sizeScreen.height - menu.getSize().getHeight())/3);
	}

	public String getFilePath() {
		return filePath;
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
}
