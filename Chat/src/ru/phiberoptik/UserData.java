package ru.korenskiy_alexey;

import java.awt.Color;
import java.io.Serializable;

public class UserData extends Message implements Serializable{

	private static final long serialVersionUID = 1799777254567840243L;
	
	private final int USERDATA = 2;
	
	private String statusDelivery = "sent";

	//переменные для хранения цветовых решений
	
	private Color backgroundFieldChat = new Color(255,255,229);
	private Color backgroundFieldMessage = new Color(255,255,255);
	private Color backgroundListNickname = new Color(55,234,241);
	private Color foregroundFieldChat = new Color(0,0,0);
	private Color foregroundFieldMessage = new Color(0,0,0);
	private Color foregroundListNickname = new Color(0,0,0);
	
	public UserData(){
	}

	public void userDataCompile(String myNickname){	
		this.setNickname(myNickname);
		this.setMessage(this.getNotice());
		this.setWhatIsIt(USERDATA);
		this.setConcatName(this.getNickName());
	}
	
	public String getStatusDelivery() {
		return statusDelivery;
	}

	public void setStatusDelivery(String statusDelivery) {
		this.statusDelivery = statusDelivery;
	}
	
	//геттеры для цвета фона компонент 
	
	public Color getBackgroundFieldChat(){			//цвет фона области вывода сообщений
		return backgroundFieldChat;
	}
	public Color getBackgroundFieldMessage(){		//цвет фона области ввода сообщений
		return backgroundFieldMessage;
	}
	public Color getBackgroundListNickname(){		//цвет фона списка юзеров
		return backgroundListNickname;
	}

	//геттеры для цвета шрифта компонент
	
	public Color getForegroundFieldChat(){			//цвет шрифта области вывода сообщений
		return foregroundFieldChat;
	}
	public Color getForegroundFieldMessage(){		//цвет шрифта области ввода сообщений
		return foregroundFieldMessage;
	}
	public Color getForegroundListNickname(){		//цвет шрифта списка юзеров
		return foregroundListNickname;
	}
	
	//сеттеры для цвета фона компонент
	
	public void setBackgroundFieldChat(Color backgroundFieldChat){			//цвет фона области вывода сообщений
		this.backgroundFieldChat = backgroundFieldChat;
	}
	public void setBackgroundFieldMessage(Color backgroundFieldMessage){		//цвет фона области ввода сообщений
		this.backgroundFieldMessage = backgroundFieldMessage;
	}
	public void setBackgroundListNickname(Color backgroundListNickname){		//цвет фона списка юзеров
		this.backgroundListNickname = backgroundListNickname;
	}

	//сеттеры для цвета шрифта компонент
	
	public void setForegroundFieldChat(Color foregroundFieldChat){			//цвет шрифта области вывода сообщений
		this.foregroundFieldChat = foregroundFieldChat;
	}
	public void setForegroundFieldMessage(Color foregroundFieldMessage){		//цвет шрифта области ввода сообщений
		this.foregroundFieldMessage = foregroundFieldMessage;
	}
	public void setForegroundListNickname(Color foregroundListNickname){		//цвет шрифта списка юзеров
		this.foregroundListNickname = foregroundListNickname;
	}
}
