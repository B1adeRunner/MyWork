package ru.korenskiy_alexey;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class WindowSetting extends JDialog{
	private FrameWindow frameWindowMainMenu;
	
	private Dimension dimensionWindowSetting;
	private Dimension sizeScreen;
	private Dimension dimensionMainPanelWindow;
	
	private Color backgroundMainPanelWindow;
	private Color foregroundMainPanelWindow;
	
	private PanelWindow mainPanelWindowSetting;
	
	private WindowMainMenu windowMainMenu;
	
	private JButton buttonChooserBackgroundFieldChat;
	private JButton buttonChooserBackgroundFieldMessage;
	private JButton buttonChooserBackgroundListNickname;
	private JButton buttonChooserForegroundFieldChat;
	private JButton buttonChooserForegroundFieldMessage;
	private JButton buttonChooserForegroundListNickname;
	private JButton buttonOk;
	
	private JLabel labelFieldChat;
	private JLabel labelFieldMessage;
	private JLabel labelListNickname;
	
	private UserData userData;
	
	public WindowSetting(FrameWindow frameWindowMainMenu, UserData userData){
		super(frameWindowMainMenu.getFrameWindow(), "настройки цвета", true);
		this.frameWindowMainMenu = frameWindowMainMenu;
		this.userData = userData;
		this.sizeScreen = frameWindowMainMenu.getSizeScreen();
	}
	
	public void buildWindowSetting(){
		createDimensionMainPanelWindow();
		createBackgroundMainPanelWindow();
		createForegroundMainPanelWindow();
		createMainPanelWindowSetting();
		createLabelsFields();
		createButtonOk();
		creteButtonsChooserColor();
		createDimensionWindowSetting();
		setDefaultSizeWindowSetting();
		setSizeWindowSetting();
		setWindowSettingResizableMode(true);
		setWindowSettingInitialLocation();
		buildMainPanelWindowSetting();
		addMainPanelAtWindowSetting();
		addComponentsAtMainPanel();
		setDefaultColorFields();
		createActionListenersButtons();
		setVisibleModWindowSetting(true);
	}
	
	private void createDimensionMainPanelWindow(){
		dimensionMainPanelWindow = new Dimension(400, 160);
	}
	
	private void createDimensionWindowSetting(){
		dimensionWindowSetting = new Dimension(400, 160);
	}
	
	private void createBackgroundMainPanelWindow(){
		backgroundMainPanelWindow = new Color(238, 224, 229);
	}
	
	private void createForegroundMainPanelWindow(){
		foregroundMainPanelWindow = Color.BLACK;
	}
	
	private void setSizeWindowSetting(){
		if (this.getHeight()>sizeScreen.height){
			dimensionWindowSetting.height=sizeScreen.height;
			this.setSize(dimensionWindowSetting);
		}
		if (this.getWidth()>sizeScreen.width){
			dimensionWindowSetting.width=sizeScreen.width;
			this.setSize(dimensionWindowSetting);
		}
	}
	
	private void addComponentsAtMainPanel(){
		mainPanelWindowSetting.add(labelFieldChat);
		mainPanelWindowSetting.add(buttonChooserBackgroundFieldChat);
		mainPanelWindowSetting.add(buttonChooserForegroundFieldChat);
		
		mainPanelWindowSetting.add(labelFieldMessage);
		mainPanelWindowSetting.add(buttonChooserBackgroundFieldMessage);
		mainPanelWindowSetting.add(buttonChooserForegroundFieldMessage);
		
		mainPanelWindowSetting.add(labelListNickname);
		mainPanelWindowSetting.add(buttonChooserBackgroundListNickname);
		mainPanelWindowSetting.add(buttonChooserForegroundListNickname);
		mainPanelWindowSetting.add(buttonOk);
	}
	
	private void createMainPanelWindowSetting(){
		mainPanelWindowSetting = new PanelWindow(dimensionMainPanelWindow);
	}
	
	private void buildMainPanelWindowSetting(){
		mainPanelWindowSetting.setSizePanelWindow();
		mainPanelWindowSetting.setAlignmentXYPanelWindow(LEFT_ALIGNMENT, CENTER_ALIGNMENT);
		mainPanelWindowSetting.setBackgroundPanelWindow(backgroundMainPanelWindow);
		mainPanelWindowSetting.setForegroundPanelWindow(foregroundMainPanelWindow);
	}
	
	private void creteButtonsChooserColor(){
		buttonChooserBackgroundFieldChat = new JButton("фон");
		buttonChooserBackgroundFieldMessage = new JButton("фон");
		buttonChooserBackgroundListNickname = new JButton("фон");
		buttonChooserForegroundFieldChat = new JButton("шрифт");
		buttonChooserForegroundFieldMessage = new JButton("шрифт");
		buttonChooserForegroundListNickname = new JButton("шрифт");
	}
	
	private void createLabelsFields(){
		labelFieldChat = new JLabel("область вывода сообщений: ");
		labelFieldMessage = new JLabel("область ввода сообщений:    ");
		labelListNickname = new JLabel("список пользователей:           ");
	}
	
	private void createButtonOk(){
		buttonOk = new JButton("Применить");
	}
	
	private void setDefaultColorFields(){
		buttonChooserBackgroundFieldChat.setBackground(userData.getBackgroundFieldChat());
		buttonChooserBackgroundFieldMessage.setBackground(userData.getBackgroundFieldMessage());
		buttonChooserBackgroundListNickname.setBackground(userData.getBackgroundListNickname());
		buttonChooserForegroundFieldChat.setForeground(userData.getForegroundFieldChat());
		buttonChooserForegroundFieldMessage.setForeground(userData.getForegroundFieldMessage());
		buttonChooserForegroundListNickname.setForeground(userData.getForegroundListNickname());
	}
	
	private void createActionListenersButtons(){
		buttonChooserBackgroundFieldChat.addActionListener(new ChooserBackground(
				userData.getBackgroundFieldChat(),buttonChooserBackgroundFieldChat));
		buttonChooserBackgroundFieldMessage.addActionListener(new ChooserBackground(
				userData.getBackgroundFieldMessage(),buttonChooserBackgroundFieldMessage));
		buttonChooserBackgroundListNickname.addActionListener(new ChooserBackground(
				userData.getBackgroundListNickname(),buttonChooserBackgroundListNickname));
		buttonChooserForegroundFieldChat.addActionListener(new ChooserForeground(
				userData.getForegroundFieldChat(),buttonChooserForegroundFieldChat));
		buttonChooserForegroundFieldMessage.addActionListener(new ChooserForeground(
				userData.getForegroundFieldMessage(),buttonChooserForegroundFieldMessage));
		buttonChooserForegroundListNickname.addActionListener(new ChooserForeground(
				userData.getForegroundListNickname(),buttonChooserForegroundListNickname));
		buttonOk.addActionListener(new ApplyChosenSettings(this));
	}
	
	private void addMainPanelAtWindowSetting(){
		this.add(mainPanelWindowSetting);
	}
	
	class ApplyChosenSettings implements ActionListener{
		private WindowSetting windowSetting;
		
		public ApplyChosenSettings(WindowSetting windowSetting){
			this.windowSetting = windowSetting;
		}
		
		@Override
		public void actionPerformed(ActionEvent event){
			userData.setBackgroundFieldChat(buttonChooserBackgroundFieldChat.getBackground());
			userData.setBackgroundFieldMessage(buttonChooserBackgroundFieldMessage.getBackground());
			userData.setBackgroundListNickname(buttonChooserBackgroundListNickname.getBackground());
			userData.setForegroundFieldChat(buttonChooserForegroundFieldChat.getForeground());
			userData.setForegroundFieldMessage(buttonChooserForegroundFieldMessage.getForeground());
			userData.setForegroundListNickname(buttonChooserForegroundListNickname.getForeground());
			windowSetting.setVisible(false); 	//скрываем окно
		}
	}
	
	class ChooserBackground implements ActionListener{
		private Color background;
		private JButton buttonChooserBackground;
		
		public ChooserBackground(){
		}
		
		public ChooserBackground(Color background, JButton buttonChooserBackground){
			this.background = background;
			this.buttonChooserBackground = buttonChooserBackground;
		}
		
		@Override
		public void actionPerformed(ActionEvent event){
			background = JColorChooser.showDialog(null, "окно выбора цвета", Color.WHITE);
			buttonChooserBackground.setBackground(background);
		}
	}
	
	class ChooserForeground implements ActionListener{
		private Color foreground;
		private JButton buttonChooserForeground;
		
		public ChooserForeground(){
		}
		
		public ChooserForeground(Color foreground, JButton buttonChooserForeground){
			this.foreground = foreground;
			this.buttonChooserForeground = buttonChooserForeground;
		}
		
		@Override
		public void actionPerformed(ActionEvent event){
			foreground = JColorChooser.showDialog(null, "окно выбора цвета", Color.WHITE);
			buttonChooserForeground.setForeground(foreground);
		}
	}
	
	public void setDefaultSizeWindowSetting(){
		this.setSize(dimensionWindowSetting);
	}
	
	public void setWindowSettingResizableMode(Boolean trueOrFalse){
		this.setResizable(trueOrFalse);
	}
	
	public void setWindowSettingInitialLocation(){
		this.setLocation((sizeScreen.width - this.getWidth())/2,
				(sizeScreen.height - this.getHeight())/3);
	}

	public void setVisibleModWindowSetting(Boolean trueOrFalse){
		this.setVisible(trueOrFalse);
	}
}
