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
	private int i=1;

	public void createTable(List<ElectricalSubstation> listOfSubstation){
		Row[] rows = new Row[listOfSubstation.size()+1];
		rows[0] = sheet.createRow(0);
		rows[0].createCell(0).setCellValue("N");
		rows[0].createCell(1).setCellValue("Наименование");
		rows[0].createCell(2).setCellValue("Страна");
		rows[0].createCell(3).setCellValue("Адрес");
		rows[0].createCell(4).setCellValue("Рабочее напряжение");
		rows[0].createCell(5).setCellValue("Количество силовых трансформаторов");
		rows[0].createCell(6).setCellValue("Под контролем");
		rows[0].createCell(7).setCellValue("Широта");
		rows[0].createCell(8).setCellValue("Долгота");
		
		for(ElectricalSubstation concreteSubstation: listOfSubstation){
			rows[i] = sheet.createRow(i);
			rows[i].createCell(0).setCellValue(i);
			rows[i].createCell(1).setCellValue(concreteSubstation.getName());
			rows[i].createCell(2).setCellValue(concreteSubstation.getCountryOfSubstation());
			rows[i].createCell(3).setCellValue(concreteSubstation.getAreaOfSubstation());
			rows[i].createCell(4).setCellValue(concreteSubstation.getWorkingVoltage());
			rows[i].createCell(5).setCellValue(concreteSubstation.getNumberOfPowerTransformers());
			rows[i].createCell(6).setCellValue(concreteSubstation.getDistribution());
			rows[i].createCell(7).setCellValue(concreteSubstation.getLatitudeOfSubstation());
			rows[i].createCell(8).setCellValue(concreteSubstation.getLongitudeOfSubstation());
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
