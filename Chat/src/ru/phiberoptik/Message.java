package ru.korenskiy_alexey;

import java.io.*;

public class Message implements Serializable{			//класс хранения данных о пользователе чата

	private static final long serialVersionUID = -2813819954887177172L;

	public Message(){
	}
		
	//поля данных
	
	private String nickname = "";		
	
	private String timeSend = "";
	
	private String message = ""; 		
	
	//сообщение при подключении
	private String notice = "Добро пожаловать в Связь, ";  

	//счетчик совпадения имен
	private Integer counterChangeName = 0;
	
	//поле для конкатенации никнейма и счетчика изменения никнейма
	private String concatName = "";
	
	private final int MESSAGE = 1;
	
	private final int DELETEMESSAGE = 4;
	
	private int itIs = MESSAGE;

	//геттеры для полей данных

	public String getNickName() {
		return nickname;
	}
	
	public String getTime() {
		return timeSend;
	}
	
	public String getMessage() {
		return message;
	}

	public String getNotice() {
		return notice;
	}

	public Integer getCounterName() {
		return counterChangeName;
	}
	
	public String getConcatName() {
		return concatName;
	}
	
	public int getWhatIsIt() {
		return itIs;
	}
	
	//сеттеры для полей данных
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setTime(String time) {
		this.timeSend = time;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public void setCounterName(int counterName){
		this.counterChangeName = counterName;
	}
	
	public void setConcatName(String concatName){
		this.concatName = concatName;
	}
	public void setWhatIsIt(int flag){
		itIs = flag;
	}
}
