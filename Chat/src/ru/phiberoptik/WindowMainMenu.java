package ru.korenskiy_alexey;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class WindowMainMenu{

	private FrameWindow frameWindowMainMenu;	
	
	private JPanel nicknamePanelWindowMainMenu;			
	private JPanel portPanelWindowMainMenu;				
	private JPanel ipAdressPanelWindowMainMenu;			
	private JPanel buttonPanelWindowMainMenu;			
	private JPanel centerPanelWindowMainMenu;			
	private JPanel westPanelWindowMainMenu;				
	private JPanel eastPanelWindowMainMenu;				
	private JPanel southPanelWindowMainMenu;			
	private JPanel nameChatPanelWindowMainMenu;		
	
	private JFormattedTextField fieldNickname;
	private JFormattedTextField fieldPort;
	private JFormattedTextField fieldIpAdress;
	
	private JLabel labelNickName;
	private JLabel labelPort;
	private JLabel labelIpAdress;
	private JLabel labelNameChat;
	
	private String fontNameChat;
	private String myNickname;
	
	private Color colorCenterPanels;
	private Color colorBorderCenterPanel;
	private Color colorEastWestSouthNorthPanels;
	private Color colorPortPanel;
	private Color colorMenuDefaultData;
	
	private MaskFormatter fieldNicknameFormatter;
	private MaskFormatter fieldIpFormatter;
	private MaskFormatter fieldPortFormatter;
	
	private JButton buttonConnect;
	private JButton buttonSetting;
	
	private WindowChat windowChat;
	
	private ConnectChat connectChat;
	
	private WindowSetting windowSetting;
	
	private String defaultNickName = "Пользователь";
	private String defaultPort = "5555";
	private String defaultIpAdress = "127.000.000.001";

	private Dimension frameMenuSize;

	private UserData userData;

	public WindowMainMenu(){
	}
	
	public void buildWindowMainMenu(){
		createNecessaryObjects();
		setFontItalic20NameChat();
		setValidCharactersNickname();
		setDefaultDataFields();
		setDefaultForegroundFields();
		setPlaceholderFields();
		setSizeFieldsWindowMainMenu();
		addPanelsToFrameWindowMainMenu();
		setLayoutsPanelsWindowMainMenu();
		setSizePanelsWindowMainMenu();
		addPanelsToCenterPanel();
		addNecessaryComponentsToPanels();
		setBorderCenterPanelWindowMainMenu();
		setBackgroundPanels();
		buidFrameWindowMainMenu();
		createActionListenersForButtons();
		createFocusListenersForFields();
		setDefaultFocusOnFieldNickname();
	}
	
	private void createNecessaryObjects(){
		frameMenuSize = new Dimension(600, 400);
		
		frameWindowMainMenu = new FrameWindow("Связь", frameMenuSize);	
		
		nicknamePanelWindowMainMenu = new JPanel();	
		portPanelWindowMainMenu = new JPanel();		
		ipAdressPanelWindowMainMenu = new JPanel();
		buttonPanelWindowMainMenu = new JPanel();
		centerPanelWindowMainMenu = new JPanel();
		westPanelWindowMainMenu = new JPanel();
		eastPanelWindowMainMenu = new JPanel();
		southPanelWindowMainMenu = new JPanel();	
		nameChatPanelWindowMainMenu = new JPanel();
		
		try {
			//22-символьный никнейм
			fieldNicknameFormatter = new MaskFormatter("**********************"); 	
			fieldIpFormatter = new MaskFormatter("###.###.###.###");
			fieldPortFormatter = new MaskFormatter("####");
		} catch (ParseException e) {
			System.out.println("ParseException");
			e.printStackTrace();
		}
		
		fieldNickname = new JFormattedTextField(fieldNicknameFormatter);
		fieldPort = new JFormattedTextField(fieldPortFormatter);
		fieldIpAdress = new JFormattedTextField(fieldIpFormatter);
		
		labelNickName = new JLabel("Nickname: ");
		labelPort = new JLabel("Port:            ");
		labelIpAdress = new JLabel("IP Adress:  ");
		labelNameChat = new JLabel("Связь");
		
		colorCenterPanels = new Color(224,238,224);
		colorBorderCenterPanel = new Color(255,240,245);
		colorEastWestSouthNorthPanels = new Color(238,224,229);
		colorPortPanel = new Color(255,228,225);
		colorMenuDefaultData = new Color(110,200,185);
		
		buttonConnect = new JButton("подключиться");
		buttonSetting = new JButton("настройки");
		
		userData = new UserData();
	}
	
	private void setFontItalic20NameChat(){
		labelNameChat.setFont(new Font(fontNameChat, Font.ITALIC, 80));
	}
	
	private void setValidCharactersNickname(){
		fieldNicknameFormatter.setValidCharacters("123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_ абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ");
	}
	
	private void setDefaultDataFields(){
		fieldNickname.setText(defaultNickName);
		fieldPort.setText(defaultPort);
		fieldIpAdress.setText(defaultIpAdress);
	}
	
	private void setDefaultForegroundFields(){
		fieldNickname.setForeground(colorMenuDefaultData);
		fieldPort.setForeground(colorMenuDefaultData);
		fieldIpAdress.setForeground(colorMenuDefaultData);
	}
	
	private void setPlaceholderFields(){
		fieldNicknameFormatter.setPlaceholder(" ");
		fieldIpFormatter.setPlaceholder(" ");
		fieldPortFormatter.setPlaceholder(" ");
	}
	
	private void setSizeFieldsWindowMainMenu(){
		fieldIpAdress.setPreferredSize(new Dimension(100,20));
		fieldPort.setPreferredSize(new Dimension(35,20));
		fieldNickname.setPreferredSize(new Dimension(205,20));
	}

	private void addPanelsToFrameWindowMainMenu(){
		frameWindowMainMenu.add(centerPanelWindowMainMenu);
		frameWindowMainMenu.add(BorderLayout.WEST,westPanelWindowMainMenu);
		frameWindowMainMenu.add(BorderLayout.EAST,eastPanelWindowMainMenu);
		frameWindowMainMenu.add(BorderLayout.SOUTH,southPanelWindowMainMenu);
		frameWindowMainMenu.add(BorderLayout.NORTH,nameChatPanelWindowMainMenu);
	}
	
	private void setLayoutsPanelsWindowMainMenu(){
		centerPanelWindowMainMenu.setLayout(new BoxLayout(centerPanelWindowMainMenu,BoxLayout.Y_AXIS));
		nicknamePanelWindowMainMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		portPanelWindowMainMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		ipAdressPanelWindowMainMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		nameChatPanelWindowMainMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
	}

	private void setSizePanelsWindowMainMenu(){
		nicknamePanelWindowMainMenu.setPreferredSize(new Dimension(600,40));
		portPanelWindowMainMenu.setPreferredSize(new Dimension(600,40));
		ipAdressPanelWindowMainMenu.setPreferredSize(new Dimension(600,40));
		buttonPanelWindowMainMenu.setPreferredSize(new Dimension(600,40));
		southPanelWindowMainMenu.setPreferredSize(new Dimension(600,112));
		nameChatPanelWindowMainMenu.setPreferredSize(new Dimension(600,130));
		westPanelWindowMainMenu.setPreferredSize(new Dimension(150,400));
		eastPanelWindowMainMenu.setPreferredSize(new Dimension(150,400));
	}
	
	private void addPanelsToCenterPanel(){
		centerPanelWindowMainMenu.add(nicknamePanelWindowMainMenu);
		centerPanelWindowMainMenu.add(portPanelWindowMainMenu);
		centerPanelWindowMainMenu.add(ipAdressPanelWindowMainMenu);	
		centerPanelWindowMainMenu.add(buttonPanelWindowMainMenu);
	}
	
	private void addNecessaryComponentsToPanels(){
		nicknamePanelWindowMainMenu.add(labelNickName);
		nicknamePanelWindowMainMenu.add(fieldNickname);
		portPanelWindowMainMenu.add(labelPort);
		portPanelWindowMainMenu.add(fieldPort);
		ipAdressPanelWindowMainMenu.add(labelIpAdress);
		ipAdressPanelWindowMainMenu.add(fieldIpAdress);
		nameChatPanelWindowMainMenu.add(labelNameChat);
		buttonPanelWindowMainMenu.add(buttonConnect);
		buttonPanelWindowMainMenu.add(buttonSetting);
	}
	
	private void setBorderCenterPanelWindowMainMenu(){
		centerPanelWindowMainMenu.setBorder(BorderFactory.createLineBorder(colorBorderCenterPanel,2));		//задаем границу центральной панели
	}

	private void setBackgroundPanels(){
		nicknamePanelWindowMainMenu.setBackground(colorCenterPanels);
		portPanelWindowMainMenu.setBackground(colorPortPanel);
		ipAdressPanelWindowMainMenu.setBackground(colorCenterPanels);
		buttonPanelWindowMainMenu.setBackground(colorPortPanel);
		southPanelWindowMainMenu.setBackground(colorEastWestSouthNorthPanels);
		nameChatPanelWindowMainMenu.setBackground(colorEastWestSouthNorthPanels);
		westPanelWindowMainMenu.setBackground(colorEastWestSouthNorthPanels);
		eastPanelWindowMainMenu.setBackground(colorEastWestSouthNorthPanels);
	}
	
	private void buidFrameWindowMainMenu(){
		frameWindowMainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameWindowMainMenu.setResizable(false);
		frameWindowMainMenu.setDefaultSizeFrameWindow();
		frameWindowMainMenu.setSizeFrameWindow();
		frameWindowMainMenu.setInitialLocationFrameWindow(); 
		frameWindowMainMenu.setBackground(Color.GRAY);
		frameWindowMainMenu.setVisible(true);
	}

	private void createActionListenersForButtons(){
		buttonSetting.addActionListener(new BuildWindowSetting());
		buttonConnect.addActionListener(new ConnectAndBuildWindowChat(this));
	}
	
	private void createFocusListenersForFields(){
		fieldNickname.addFocusListener(new RefreshFieldTextIfHeChange(fieldNickname, defaultNickName));
		fieldPort.addFocusListener(new RefreshFieldTextIfHeChange(fieldPort, defaultPort));
		fieldIpAdress.addFocusListener(new RefreshFieldTextIfHeChange(fieldIpAdress, defaultIpAdress));
	}
	
	private void setDefaultFocusOnFieldNickname(){
		fieldNickname.requestFocus(true);
	}

	public UserData getUserData() {
		return userData;
	}
	
	public WindowChat getWindowChat(){	
		return windowChat;
	}
	
	public ConnectChat getConnectChat(){		
		return connectChat;
	}
	
	public WindowSetting getSettingMenuGui(){
		return windowSetting;
	}
	
	public String getTextFieldNickName(){		
		return fieldNickname.getText();			
	}
	
	public String getTextFieldPort(){			
		return fieldPort.getText();				
	}
	
	public String getTextFieldIpAdress(){		
		return fieldIpAdress.getText();			
	}
	
	public JFrame getFrameMenu(){				
		return frameWindowMainMenu;
	}
	
	class RefreshFieldTextIfHeChange implements FocusListener{
		private JFormattedTextField formattedTextField;
		private String stringData;
		
		public RefreshFieldTextIfHeChange(JFormattedTextField formattedTextField, String stringData){
			this.formattedTextField = formattedTextField;
			this.stringData = stringData;
		}
		
		@Override
		public void focusGained(FocusEvent event) {
			formattedTextField.setForeground(Color.black);
			if(formattedTextField.getText().trim().equals(stringData)){
				formattedTextField.setText(stringData);
			}else{
				formattedTextField.setText(formattedTextField.getText());
			}
		}
		
		@Override
		public void focusLost(FocusEvent event) {
			if(formattedTextField.getText().equals(null) || formattedTextField.getText().trim().equals("")){
				formattedTextField.setText(stringData);
			}
			formattedTextField.setForeground(colorMenuDefaultData);
		}
	}
	
	class BuildWindowSetting implements ActionListener{
		public BuildWindowSetting(){
		}
		
		@Override
		public void actionPerformed(ActionEvent event){
			windowSetting = new WindowSetting(frameWindowMainMenu, userData);
			windowSetting.buildWindowSetting();
		}
	}
	
	class ConnectAndBuildWindowChat implements ActionListener{
		private WindowMainMenu windowMainMenu;
		
		public ConnectAndBuildWindowChat(){
		}
		
		public ConnectAndBuildWindowChat(WindowMainMenu windowMainMenu){
			this.windowMainMenu = windowMainMenu;
		}
		
		@Override
		public void actionPerformed(ActionEvent event){
			windowChat = new WindowChat(windowMainMenu);
			myNickname = windowMainMenu.getTextFieldNickName().trim();
			userData.userDataCompile(myNickname);	
			connectChat = new ConnectChat(windowMainMenu);
			windowChat.buildWindowChat();
			connectChat.runClient();		
			frameWindowMainMenu.setVisible(false); 		
		}
	}
}
