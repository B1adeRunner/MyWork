package ru.korenskiy_alexey;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class FrameWindow extends JFrame{
	private Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();

	private Dimension sizeFrame;
	
	public FrameWindow(){
	}
	
	public FrameWindow(String nameFrame, Dimension sizeFrame){
		super(nameFrame);
		this.sizeFrame = sizeFrame;
	}

	public void setDefaultSizeFrameWindow(){
		this.setSize(sizeFrame);
	}
	
	public void setSizeFrameWindow(){
		if (this.getHeight()>sizeScreen.height){
			sizeFrame.height=sizeScreen.height;
			this.setSize(sizeFrame);
		}
		if (this.getWidth()>sizeScreen.width){
			sizeFrame.width=sizeScreen.width;
			this.setSize(sizeFrame);
		}
	}
	
	public void setInitialLocationFrameWindow(){
		this.setLocation((sizeScreen.width - this.getWidth())/2,
				(sizeScreen.height - this.getHeight())/3);
	}
	
	public FrameWindow getFrameWindow(){
		return this;
	}
	
	public Dimension getSizeScreen() {
		return sizeScreen;
	}
}
