package ru.korenskiy_alexey;

import java.awt.*;
import java.awt.event.*;
import java.awt.Color.*;

import javax.swing.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

//создаем графический интерфейс программы 
public class WindowChat {
	private JButton buttonDisconnect;
	private JButton buttonSend; 
	
	private FrameWindow frameWindow;
	
	private LinkedHashMap<String, UserData> collectionUserData;	
	
	private JTextArea fieldChat;
	private JTextArea fieldMessage;
	
	private JList listNickname;
	
	private JPanel panelEast;
	private JPanel panelSouth;
	private JPanel panelButton;
	
	private JScrollPane scrollListUsers;
	private JScrollPane scrollMessageField;
	private JScrollPane scrollChatField;
	
	private DefaultListModel<String> listModel;
	
	private Color colorMenuDefaultData;
	
	private Font defaulFontMessage;
	private Font standartFontMessage;
	
	private JLabel counterUsers;
	
	private Dimension sizeFrameWindow;

	private Message message;
	private WindowMainMenu windowMainMenu;

	public WindowChat(WindowMainMenu windowMainMenu){
		this.windowMainMenu = windowMainMenu;
	}

	public void buildWindowChat() {	
		createNecessaryObject();
		buildFrameWindowMainMenu();
		createButtonsWindowChat();
		createListenersButtons();
		setLayoutsPanels();
		setBackgroundsPanels();
		setForegroundsPanels();
		addComponentsAtPanels();
		setScrollbarListUsers();
		setAlignmentXYPanelButton();
		setDefaultTextFieldMessage();
		createListenersFields();
		setLineWrapFields();
		setFontFieldMessage();
		setEditableModeFieldMessage();
		setBackgroundFields();
		setForegroundFields();
		setTextCounterUsers();
		setFixedCellSizeListNickname();
		setSelectionModeListNickname();
		setToolTipButtons();
		setMarginButtons();
		setScrollbarPolicyChatField();
		setScrollbarPolicyFieldMessage();
		addPanelsAtMainFrame();
		showFrameWindowMainMenu();
	}
	
	private void createNecessaryObject(){
		sizeFrameWindow = new Dimension(700, 400);
		
		frameWindow = new FrameWindow("Связь: соединение для "+getUserData().getNickName(), sizeFrameWindow);
		
		collectionUserData = new LinkedHashMap<String, UserData>();	
		
		listModel = new DefaultListModel<String>();
		listNickname = new JList(listModel);
		
		panelEast = new JPanel();
		panelSouth = new JPanel();
		panelButton = new JPanel();
		
		fieldChat = new JTextArea(); 
		fieldMessage = new JTextArea(2,0);			//ширина=0, т.к. поле растягивается по всей свободной ширине
		
		counterUsers = new JLabel();
		
		defaulFontMessage = new Font("Verdana",Font.ITALIC,12);
		standartFontMessage = new Font("Verdana",Font.PLAIN,12); 
		
		colorMenuDefaultData = new Color(110,200,185);
		
		scrollListUsers = new JScrollPane(listNickname);
		scrollMessageField = new JScrollPane(fieldMessage);
		scrollChatField = new JScrollPane(fieldChat);
	}
	
	private void setLayoutsPanels(){
		panelEast.setLayout(new BorderLayout());
		panelSouth.setLayout(new BorderLayout());
		panelButton.setLayout(new BoxLayout(panelButton,0));
	}

	private void addComponentsAtPanels(){
		panelEast.add(scrollListUsers,BorderLayout.EAST);
		panelEast.add(counterUsers,BorderLayout.SOUTH);
		
		panelSouth.add(BorderLayout.CENTER,scrollMessageField);
		panelSouth.add(BorderLayout.EAST,panelButton);	
		
		panelButton.add(buttonSend);
		panelButton.add(buttonDisconnect);
	}
	
	private void setBackgroundsPanels(){
		panelEast.setBackground(getUserData().getBackgroundListNickname());
		panelButton.setBackground(Color.DARK_GRAY);
	}
	
	private void setForegroundsPanels(){
		panelEast.setForeground(getUserData().getForegroundListNickname());
	}
	
	private void setScrollbarListUsers(){
		scrollListUsers.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollListUsers.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	}

	public void setAlignmentXYPanelButton(){
		panelButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panelButton.setAlignmentY(JComponent.CENTER_ALIGNMENT);
	}

	public void setDefaultTextFieldMessage(){
		fieldMessage.setText("введите сообщение");
	}
	
	private void createListenersButtons(){
		buttonDisconnect.addActionListener(new DisconnectFromServer());
		buttonSend.addActionListener(new SendMessageIfMessageNotEmpty());
	}
	
	private void createListenersFields(){
		fieldMessage.addKeyListener(new SendMessageIfMessageNotEmptyForKeyEnter());
		fieldMessage.addFocusListener(new ClearTextFieldIfGetFocus(fieldMessage));
	}

	private void setLineWrapFields(){
		fieldChat.setLineWrap(true);
		fieldMessage.setLineWrap(true);
	}
	
	private void setFontFieldMessage(){
		fieldMessage.setFont(defaulFontMessage);
	}
	
	private void setEditableModeFieldMessage(){
		fieldChat.setEditable(false);
	}
	
	private void setBackgroundFields(){ 
		fieldChat.setBackground(getUserData().getBackgroundFieldChat());
		listNickname.setBackground(getUserData().getBackgroundListNickname());
		fieldMessage.setBackground(getUserData().getBackgroundFieldMessage());
		counterUsers.setBackground(getUserData().getBackgroundListNickname());
	}
	
	private void setForegroundFields(){ 
		fieldChat.setForeground(getUserData().getForegroundFieldChat());
		listNickname.setForeground(getUserData().getForegroundListNickname());
		fieldMessage.setForeground(colorMenuDefaultData);
		counterUsers.setForeground(getUserData().getForegroundListNickname());
	}

	private void setTextCounterUsers(){
		counterUsers.setText("Пользователей онлайн: "+collectionUserData.size());
	}

	private void setFixedCellSizeListNickname(){
		listNickname.setFixedCellHeight(20);					
		listNickname.setFixedCellWidth(171);	
	}
	
	private void setSelectionModeListNickname(){
		listNickname.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
	}
	
	private void setToolTipButtons(){
		buttonSend.setToolTipText("отправить сообщение");
		buttonDisconnect.setToolTipText("разсоединиться с сервером");
	}
	
	private void setMarginButtons(){
		buttonSend.setMargin(new Insets(7,7,7,7));
		buttonDisconnect.setMargin(new Insets(7,7,7,7));
	}
	
	public void setScrollbarPolicyChatField(){
		scrollChatField.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollChatField.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	private void createButtonsWindowChat(){
		buttonDisconnect = new JButton("отключиться");
		buttonSend = new JButton("на печать");
	}
	
	public void setScrollbarPolicyFieldMessage(){
		scrollMessageField.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollMessageField.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	private void buildFrameWindowMainMenu(){
		frameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameWindow.setResizable(false);
		frameWindow.setDefaultSizeFrameWindow();
		frameWindow.setSizeFrameWindow();
		frameWindow.setInitialLocationFrameWindow(); 
		frameWindow.setBackground(Color.GRAY);
	}
	
	private void addPanelsAtMainFrame(){
		frameWindow.add(BorderLayout.EAST,panelEast);
		frameWindow.add(BorderLayout.SOUTH,panelSouth);
		frameWindow.add(BorderLayout.CENTER, scrollChatField);
	}
	
	private void showFrameWindowMainMenu(){
		frameWindow.setVisible(true);
	}
	
	public Message compileMessage(){
		message = new Message();
		message.setMessage(fieldMessage.getText().trim());	
		message.setNickname(getUserData().getNickName());	
		message.setConcatName(getUserData().getConcatName());
		message.setCounterName(getUserData().getCounterName());
		return message;
	}

	public JTextArea getFieldChat(){	//геттер для области вывода всех сообщений
		return fieldChat;
	}
	
	public Message getMessage(){		//геттер для объекта message
		return message;
	}
	
	public JLabel getCounterUsers(){		//геттер для объекта counterUsers
		return counterUsers;
	}
	
	public JList getNicknameList(){		//геттер для списка пользователей
		return listNickname;
	}
	
	public DefaultListModel getListModel(){
		return listModel;
	}
	
	public FrameWindow getFrameWindow(){		//геттер для mainFrame
		return frameWindow;
	}
	
	public LinkedHashMap<String, UserData> getCollectionUserData(){
		return windowMainMenu.getConnectChat().getCollectionUserData();
	}
	
	public ConnectChat getConnectChat(){
		return windowMainMenu.getConnectChat();
	}
	
	public UserData getUserData(){
		return windowMainMenu.getUserData();
	}

	class SendMessageIfMessageNotEmptyForKeyEnter implements KeyListener{
		public SendMessageIfMessageNotEmptyForKeyEnter(){
		}
		
		@Override
		public void keyPressed(KeyEvent event){
		}
		
		@Override
		public void keyReleased(KeyEvent event){
		} 
		
		@Override
		public void keyTyped(KeyEvent event){
			if(event.getKeyChar() == '\n'){
				if(!getConnectChat().getClientSocket().isClosed()){
					compileMessage();
					if(message.getMessage().trim().equals("") || message.getMessage().equals(null)) {
					}else{	//если сообщение не пустое, вызываем функцию отправки сообщения на сервер
						getConnectChat().sendMessage(message);
						fieldMessage.setText("");
						fieldChat.setCaretPosition(fieldChat.getDocument().getLength());
					}
				}
			}
		}
	}
	
	class DisconnectFromServer implements ActionListener{				
		public DisconnectFromServer(){
		}
		
		@Override
		public void actionPerformed(ActionEvent event){
			if(!getConnectChat().getClientSocket().isClosed())
				getConnectChat().disconnectMethod();
		}
	}
	
	class SendMessageIfMessageNotEmpty implements ActionListener{					
		public SendMessageIfMessageNotEmpty(){
		}
		
		@Override
		public void actionPerformed(ActionEvent event){
			if(!getConnectChat().getClientSocket().isClosed()){
				compileMessage();
				if(message.getMessage().trim().equals("") || message.getMessage().equals(null) || message.getMessage().equals("введите сообщение")) {
				}else{								//если сообщение не пустое и не дефолтное
													//вызываем функцию отправки сообщения на сервер
					getConnectChat().sendMessage(message);
					fieldMessage.setForeground(colorMenuDefaultData);
					fieldMessage.setFont(defaulFontMessage);
					fieldMessage.setText("введите сообщение");
					fieldChat.setCaretPosition(fieldChat.getDocument().getLength());
				}
			}
		}
	}
	
	class ClearTextFieldIfGetFocus implements FocusListener{
		private JTextArea textArea;
		
		public ClearTextFieldIfGetFocus(JTextArea textArea){
			this.textArea = textArea;
		}
		
		@Override
		public void focusGained(FocusEvent ie){
			if (textArea.getText().equals("введите сообщение")){
				textArea.setText("");
				textArea.setForeground(getUserData().getForegroundFieldMessage());
				fieldMessage.setFont(standartFontMessage);
			}
		}
		
		@Override
		public void focusLost(FocusEvent ie){
			if(textArea.getText().equals(null) || textArea.getText().trim().equals("")){
				textArea.setForeground(colorMenuDefaultData);
				textArea.setFont(defaulFontMessage);
				textArea.setText("введите сообщение");
			}
		}
	}
}

