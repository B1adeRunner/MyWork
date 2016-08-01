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
	private Dimension screenSize;
	private int screenWidth;
	private int screenHeight;
	private int menuWindowWidth;
	private int menuWindowHeight;
	
	public MenuWindow(){
		
	}
	
	public void compile(){
		initializeNecessaryObjects();
		buildButtonFileChooser();
		registrationObservableObject();
		buildMenuPanel();
		setMenuWindowInitialLocation();
		buildMenu();

	}
	
	private void buildButtonFileChooser(){
		buttonFileChooser.addActionListener(fileChooserListener);
	}
	
	private void registrationObservableObject(){
		fileChooserListener.addObserver(this);
	}
	
	private void buildMenu(){
		setMenuDefaultCloseOperation();
		setMenuWindowSize();
		setMenuWindowResizable();
		addMenuPanelToMenuWindow();
		setMenuWindowVisible();
	}
	
	private void setMenuDefaultCloseOperation(){
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setMenuWindowSize(){
		menu.setSize(150, 95);
	}
	
	private void setMenuWindowResizable(){
		menu.setResizable(false);
	}
	
	private void setMenuWindowVisible(){
		menu.setVisible(true);
	}

	private void addMenuPanelToMenuWindow(){
		menu.add(menuPanel);
	}
	
	private void buildMenuPanel(){
		menuPanel.add(menuLabel);
		menuPanel.add(buttonFileChooser);
	}
	
	private void initializeNecessaryObjects(){
		menu = new JFrame();
		fileChooser = new JFileChooser();
		menuPanel = new JPanel();
		buttonFileChooser = new JButton("выбрать");
		fileChooserListener = new FileChooserListener();
		menuLabel = new JLabel("Выберите файл");
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int)screenSize.getWidth();
		screenHeight = (int)screenSize.getHeight();
		menuWindowWidth = (int)menu.getSize().getWidth();
		menuWindowHeight = (int)menu.getSize().getHeight();
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
			setChanged();
			notifyObservers();
			menu.dispose();
		}
	}
	
	private void setMenuWindowInitialLocation(){
		menu.setLocation((screenWidth - menuWindowWidth)/2, (screenHeight - menuWindowHeight)/3);
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
