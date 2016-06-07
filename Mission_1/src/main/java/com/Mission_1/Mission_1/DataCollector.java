package com.Mission_1.Mission_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataCollector {
	private float[][] dataSource;
	private int rowCount;
	private String filePath;
	
	public DataCollector() {
		
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void gatherData() {
		try(Scanner scanner = new Scanner(new File(filePath))) {
			rowCount = scanner.nextInt();
			dataSource = new float[rowCount][rowCount+1];
			int j=0;
			while(scanner.hasNext()){
				for(int i=0; i<rowCount+1; i++)
					dataSource[j][i] = scanner.nextInt();
				j++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public float[][] getDataSource() {
		return dataSource;
	}

	public int getRowCount() {
		return rowCount;
	}
}
