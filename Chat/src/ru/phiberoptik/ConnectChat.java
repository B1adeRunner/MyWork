package ru.korenskiy_alexey;
 
import static java.lang.System.out;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedHashMap;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class ConnectChat {
	
	private final int MESSAGE = 1;
	private final int USERDATA = 2;
	private final int DELETEMESSAGE = 4;

	private Socket clientSocket;	
	
	private ObjectOutputStream writerOnServer;
	private ObjectInputStream readerFromServer;	

	private LinkedHashMap<String, UserData> collectionUserData;	
	
	private LocalTime time;
	
	private WindowMainMenu windowMainMenu;
	
	private Object receivedObject;

	private UserData receivedUserData;
	private UserData myUserData;
	
	private Message receivedMessage;
	private Message messageOfDisconnect;
	
	private Integer counterOfPings;
	private Integer counterOfPongs;
	
	public ConnectChat(WindowMainMenu windowMainMenu){
		this.windowMainMenu = windowMainMenu;
		this.myUserData = windowMainMenu.getUserData();
	}
	
	public Message getMessageToSend() {
		return getWindowChat().getMessage();
	}

	public FrameWindow getFrameWindow() {
		return getWindowChat().getFrameWindow();
	}

	public JLabel getCounterUsers() {
		return getWindowChat().getCounterUsers();
	}

	public DefaultListModel<String> getListModel() {
		return getWindowChat().getListModel();
	}

	public JTextArea getFieldChat() {
		return getWindowChat().getFieldChat();
	}

	public WindowChat getWindowChat() {
		return windowMainMenu.getWindowChat();
	}
	
	public void runClient(){	
		try {
			Integer portInteger = new Integer(windowMainMenu.getTextFieldPort());	
			int port = portInteger.intValue();
			
			clientSocket = new Socket(windowMainMenu.getTextFieldIpAdress(), port);
			
			writerOnServer = new ObjectOutputStream(clientSocket.getOutputStream());	
			collectionUserData = new LinkedHashMap<String, UserData> ();
			readerFromServer = new ObjectInputStream(clientSocket.getInputStream());
			
			Thread readThread = new Thread(new ReadRunnable());		
			readThread.start();
			
		} catch (UnknownHostException e) {
			out.println("UnknownHostException");
			e.printStackTrace();
		} catch (IOException e) {
			out.println("IOException");
			e.printStackTrace(); 
		}
	}

	class ReadRunnable implements Runnable{
		ReadRunnable(){
		}
		
		public void run(){
			while(!clientSocket.isClosed()){
				readAndPrintMessage();
			}
		}

		public void readAndPrintMessage(){	
			//считывает данные с сервера и выводит на экран в поле чата и списке юзеров
			try {
				receivedObject = readerFromServer.readObject();
				if (myUserData.getStatusDelivery().equals("sent")){
					collectionUserData = (LinkedHashMap<String, UserData> )receivedObject;
					if(!isEmptyCollection())
						changeMyNicknameIfHeAlreadyTaken();
					addUserCollectionToUserList();
					sendUserData(myUserData);
					showWindowName();
					myUserData.setStatusDelivery("received"); 
				}else{
					receivedMessage = (Message)receivedObject;
					switch (receivedMessage.getWhatIsIt()){
						case MESSAGE: 
							showReceivedMessage();
							break;
						case USERDATA: 
							receivedUserData = (UserData)receivedMessage;
							addNewUserToUserCollection(receivedUserData);
							showNoticeOfNewUser();
							refreshCounterUsers();
							addNicknameNewUserToUserList(receivedUserData);
							break;
						case DELETEMESSAGE: 
							removeDisconnectedUserFromUserCollection();
							removeDisconnectedUserFromUserList();
							refreshCounterUsers();
							break;
					}
				}
			
			} catch (ClassNotFoundException | IOException e) {
				out.println("ClassNotFoundException or IOException");
				e.printStackTrace();
			}
		}
	}
			
	public void sendMessage(Message message){
		try {
			writerOnServer.writeObject(message);
			writerOnServer.flush();
		} catch (IOException e) {
			out.println("IOException");
			e.printStackTrace();
		}
	}
	
	public void sendUserData(UserData userData){
		try {
			writerOnServer.writeObject(userData);
			writerOnServer.flush();
		} catch (IOException e) {
			out.println("IOException");
			e.printStackTrace();
		}
	}
	
	public LinkedHashMap<String, UserData>  getCollectionUserData(){	
		return collectionUserData;
	}
	
	public void showReceivedMessage(){
		time = LocalTime.now();
		getFieldChat().append(time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)).concat("  ").concat(receivedMessage.getConcatName()).concat(": ").concat(receivedMessage.getMessage()).concat("\n"));
		getFieldChat().setCaretPosition(getFieldChat().getDocument().getLength());
	}
	
	public void showNoticeOfNewUser(){
		time = LocalTime.now();
		getFieldChat().append(time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)).concat("  ").concat(receivedMessage.getNotice()).concat(receivedMessage.getConcatName()).concat("\n"));
		getFieldChat().setCaretPosition(getFieldChat().getDocument().getLength());
	}
	
	public Boolean isEmptyCollection(){
		if(collectionUserData.isEmpty())
			return true;
		else
			return false;
	}
	
	public void changeMyNicknameIfHeAlreadyTaken(){
		for(UserData receivedUserData: collectionUserData.values()){
			if(isThatNameAlreadyTaken(receivedUserData))
				changeNickname(receivedUserData);
		}
	}
	
	public void addUserCollectionToUserList(){
		for(UserData receivedUserData: collectionUserData.values()){
			addNicknameNewUserToUserList(receivedUserData);
		}
		refreshCounterUsers();
	}

	public Boolean isThatNameAlreadyTaken(UserData receivedUserData){
		if(receivedUserData.getConcatName().equals(myUserData.getConcatName())){
			return true;
		}else{
			return false;
		}
	}
	
	public void changeNickname(UserData receivedUserData){
		myUserData.setCounterName(receivedUserData.getCounterName()+1);
		myUserData.setConcatName(myUserData.getNickName().concat(myUserData.getCounterName().toString()));
	}
	
	public void refreshCounterUsers(){
		getCounterUsers().setText("Пользователей онлайн: "+collectionUserData.size());
	}

	public void addNicknameNewUserToUserList(UserData receivedUserData){
		getListModel().addElement(receivedUserData.getConcatName());
	}

	public void showNoticeOfNameChange(){
		time = LocalTime.now();
		getFieldChat().append(time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)).concat("  Ваш никнейм уже занят другим пользователем и будет изменен\n"));
		getFieldChat().setCaretPosition(getFieldChat().getDocument().getLength());
	}
	
	public void showWindowName(){
		getFrameWindow().setTitle("Связь: соединение для ".concat(myUserData.getConcatName()));
	}
	
	public void addNewUserToUserCollection(UserData userData){
		collectionUserData.put(userData.getConcatName(), userData);
	}
	
	public void removeDisconnectedUserFromUserCollection(){
		collectionUserData.remove(receivedMessage.getConcatName());							
	}
	
	public void removeDisconnectedUserFromUserList(){
		getListModel().removeElement(receivedMessage.getConcatName());							
	}
	
	public void disconnectMethod(){
		compileMessageOfDisconnect();
		getFieldChat().append("Вы вышли из чата. Свзяь разорвана.");
		sendMessage(messageOfDisconnect);
		collectionUserData.clear();
		getListModel().clear();
		refreshCounterUsers();
		try {	
			clientSocket.close();	
		} catch (IOException e) {
			out.println("IOException");
			e.printStackTrace();
		}
	}
	
	public void compileMessageOfDisconnect(){
		messageOfDisconnect = new Message();
		messageOfDisconnect.setWhatIsIt(DELETEMESSAGE);
		messageOfDisconnect.setConcatName(myUserData.getConcatName());
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}
}
