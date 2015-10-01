package ru.korenskiy_alexey;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class PanelWindow extends JPanel{
	private Dimension dimensionPanelWindow;
	private Color backgroundPanelWindow;
	private Color foregroundPanelWindow;
	
	public PanelWindow(){
	}
	
	public PanelWindow(Dimension dimensionPanelWindow){
		this.dimensionPanelWindow = dimensionPanelWindow;
	}

	public void setSizePanelWindow(){
		this.setPreferredSize(dimensionPanelWindow);
	}
	
	public void setAlignmentXYPanelWindow(float alignmentX, float alignmentY){
		this.setAlignmentX(alignmentX);
		this.setAlignmentY(alignmentY);
	}

	public Dimension getDimensionPanelWindow() {
		return dimensionPanelWindow;
	}

	public void setDimensionPanelWindow(Dimension dimensionPanelWindow) {
		this.dimensionPanelWindow = dimensionPanelWindow;
	}

	public Color getBackgroundPanelWindow() {
		return this.getBackground();
	}
	
	public Color getForegroundPanelWindow() {
		return this.getForeground();
	}

	public void setBackgroundPanelWindow(Color backgroundPanelWindow) {
		this.backgroundPanelWindow = backgroundPanelWindow;
		this.setBackground(backgroundPanelWindow);
	}

	public void setForegroundPanelWindow(Color foregroundPanelWindow) {
		this.foregroundPanelWindow = foregroundPanelWindow;
		this.setForeground(foregroundPanelWindow);
	}
}
