package parsEnergyBase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelFile{
	private Workbook workBook = new HSSFWorkbook();
	private Sheet sheet = workBook.createSheet();
	private Row[] rowArray;
	private int i=1;

	public void getRowArray(List<ElectricalSubstation> listOfSubstation){
		rowArray = new Row[listOfSubstation.size()+1];
	}
	
	public void createTable(List<ElectricalSubstation> listOfSubstation){
		rowArray[0] = sheet.createRow(0);
		rowArray[0].createCell(0).setCellValue("N");
		rowArray[0].createCell(1).setCellValue("Наименование");
		rowArray[0].createCell(2).setCellValue("Страна");
		rowArray[0].createCell(3).setCellValue("Адрес");
		rowArray[0].createCell(4).setCellValue("Рабочее напряжение");
		rowArray[0].createCell(5).setCellValue("Количество силовых трансформаторов");
		rowArray[0].createCell(6).setCellValue("Под контролем");
		rowArray[0].createCell(7).setCellValue("Широта");
		rowArray[0].createCell(8).setCellValue("Долгота");
	}
	
	public void fillTheForm(List<ElectricalSubstation> listOfSubstation){
		for(ElectricalSubstation concreteSubstation: listOfSubstation){
			rowArray[i] = sheet.createRow(i);
			rowArray[i].createCell(0).setCellValue(i);
			rowArray[i].createCell(1).setCellValue(concreteSubstation.getName());
			rowArray[i].createCell(2).setCellValue(concreteSubstation.getCountryOfSubstation());
			rowArray[i].createCell(3).setCellValue(concreteSubstation.getAreaOfSubstation());
			rowArray[i].createCell(4).setCellValue(concreteSubstation.getWorkingVoltage());
			rowArray[i].createCell(5).setCellValue(concreteSubstation.getNumberOfPowerTransformers());
			rowArray[i].createCell(6).setCellValue(concreteSubstation.getDistribution());
			rowArray[i].createCell(7).setCellValue(concreteSubstation.getLatitudeOfSubstation());
			rowArray[i].createCell(8).setCellValue(concreteSubstation.getLongitudeOfSubstation());
			i++;
		}
	}
	
	public void writeToFile(){
		try (FileOutputStream fileOut = new FileOutputStream("workbook.xls")){
			workBook.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
