package ru.korenskiy_alexey;

import static java.lang.System.out;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.Timer;


public class ConnectServer {
	
	private final int MESSAGE = 1;
	private final int USERDATA = 2;
	private final int DELETEMESSAGE = 4;

	
	private ObjectOutputStream tmpWriterToUserStream; 					//временное размещение потока вывода объекта в методе sendAll()	
	private LinkedHashMap<String, UserData> collectionUserData;							//коллекция информации о пользователях юзеров
	private List<Message> messageCollection;							//лог чата
	private LinkedHashMap<String, ObjectOutputStream> writerToUserStreamCollection;		//коллекция исходящих потоков с польз-ми
	private Message messageReceived;
	private Message messageToSend;
	private UserData userData;
	private UserData userDataToSend;
	private Socket clientSocket;
	private LocalTime time;
	
	public void runServer(){	
		try {
			ServerSocket serverSock = new ServerSocket(5555);
			writerToUserStreamCollection = new LinkedHashMap<String, ObjectOutputStream>();
			collectionUserData = new LinkedHashMap<String, UserData>();	
			messageCollection = new ArrayList<Message>();
			while(true){
				clientSocket = serverSock.accept();				
				Thread readUserThread = new Thread(new ReadUserRunnable());
				readUserThread.start();
			}
		}catch (IOException e) {
			out.println("IOException");
			e.printStackTrace();
		}
	}
	
	class ReadUserRunnable implements Runnable{			//определяем задачу для потока чтения юзера
		private ObjectInputStream readerFromUserStream;
		private ObjectOutputStream writeToUserStream;
		private Message noticeOfDisconnectedUser;
		private Message messageForDeleteDisconnectedUser;
		
		private ReadUserRunnable(){
		}
		
		public void run(){	
			try {
				readerFromUserStream = new ObjectInputStream(clientSocket.getInputStream());
				writeToUserStream = new ObjectOutputStream(clientSocket.getOutputStream());
			} catch (IOException e) {
				out.println("IOException");
				e.printStackTrace();
			}

			sendPersonalUserDataCollection(writeToUserStream);
			
			while(!clientSocket.isClosed())
				readStreamWhileConnect();
		}
	
		public void readStreamWhileConnect(){
			try {
				messageReceived = (Message)(readerFromUserStream.readObject());						
					switch (messageReceived.getWhatIsIt()){
						case USERDATA: 
							userDataToSend = (UserData)messageReceived; 
							addToUserDataCollection();
							addToUserStreamCollection(writeToUserStream);
							break;
						case MESSAGE: 
							messageToSend = messageReceived;
							messageCollection.add(messageToSend);
							break;
						case DELETEMESSAGE: 
							removeDisconnectedUserFromUserCollection();
							removeDisconnectedUserFromStreamCollection();
							buildNoticeOfDisconnectedUser();
							sendMessageToAll(noticeOfDisconnectedUser);
							buildMessageForDeleteDisconnectedUser();
							sendMessageToAll(messageForDeleteDisconnectedUser);
							clientSocket.close();
							break;
					}
					Thread writeUserThread = new Thread(new WriteUserRunnable());
					writeUserThread.start();
			} catch (ClassNotFoundException | IOException e) {
				out.println("ClassNotFoundException or IOException");
				e.printStackTrace();
			}
		}
		
		public void buildNoticeOfDisconnectedUser(){
			noticeOfDisconnectedUser = new Message();
			noticeOfDisconnectedUser.setMessage("Пользователь ".concat(messageReceived.getConcatName().concat(" вышел из чата")));
		}
		
		public void buildMessageForDeleteDisconnectedUser(){
			messageForDeleteDisconnectedUser = new Message();
			messageForDeleteDisconnectedUser.setConcatName(messageReceived.getConcatName());
			messageForDeleteDisconnectedUser.setWhatIsIt(DELETEMESSAGE);
		}
	}
	
	class WriteUserRunnable implements Runnable{	
		public void run(){
			switch (messageReceived.getWhatIsIt()){
			case USERDATA: 
				sendUserDataToAll(userDataToSend);
				break;
			case MESSAGE: 
				sendMessageToAll(messageToSend);
				break;
			}
		}
	}
	
	public synchronized void sendMessageToAll(Message messageToSend){		
		for(Object tmpWriter: writerToUserStreamCollection.values()){
			tmpWriterToUserStream = (ObjectOutputStream)tmpWriter;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				out.println("InterruptedException");
				e.printStackTrace();
			}
			try {
				tmpWriterToUserStream.writeObject(messageToSend);	
				tmpWriterToUserStream.flush();
			} catch (IOException e) {
				out.println("IOException");
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void sendUserDataToAll(UserData dataToSend){		
		for(Object tmpWriter: writerToUserStreamCollection.values()){
			tmpWriterToUserStream = (ObjectOutputStream)tmpWriter;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				out.println("InterruptedException");
				e.printStackTrace();
			}
			try {
				tmpWriterToUserStream.writeObject(dataToSend);	
				tmpWriterToUserStream.flush();
			} catch (IOException e) {
				out.println("IOException");
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void sendPersonalUserDataCollection(ObjectOutputStream stream){	
		try {
			stream.writeObject(collectionUserData);	
		} catch (IOException e) {
			out.println("IOException");
			e.printStackTrace();
		}
	}
	
	public synchronized void addToUserDataCollection(){
		//копируем поля польз-х данных в аналогичный объект на сервере
		userData = new UserData();
		userData.setNickname(userDataToSend.getNickName());
		userData.setConcatName(userDataToSend.getConcatName());
		userData.setCounterName(userDataToSend.getCounterName());
		userData.setWhatIsIt(USERDATA);
		collectionUserData.put(userData.getConcatName(), userData);		
	}
				
	public static void main(String[] args) {
		ConnectServer connectServer = new ConnectServer();
		connectServer.runServer();
	}
	
	public void addToUserStreamCollection(ObjectOutputStream stream){
		writerToUserStreamCollection.put(userDataToSend.getConcatName(), stream);
	}
	
	public void removeDisconnectedUserFromUserCollection(){
		collectionUserData.remove(messageReceived.getConcatName());							
	}
	
	public void removeDisconnectedUserFromStreamCollection(){						
		writerToUserStreamCollection.remove(messageReceived.getConcatName());
	}
}
