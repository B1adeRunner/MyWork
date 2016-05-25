package parsEnergyBase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class TxtFile{
	private XWPFDocument document = new XWPFDocument();
	private int i=0;
	
	public void createForme(List<ElectricalSubstation> listOfSubstation){
		XWPFParagraph[] paragraphMas = new XWPFParagraph[listOfSubstation.size()+1];
		for(ElectricalSubstation concreteSubstation: listOfSubstation){
			paragraphMas[i] = document.createParagraph();
			paragraphMas[i].createRun().setText(concreteSubstation.getName());
			paragraphMas[i].createRun().setText(", ");
			paragraphMas[i].createRun().setText(concreteSubstation.getLatitudeOfSubstation());
			paragraphMas[i].createRun().setText(", ");
			paragraphMas[i].createRun().setText(concreteSubstation.getLongitudeOfSubstation());
			i++;
		}
	}
	
	public void writeToFile(){
		try (FileOutputStream fileOut = new FileOutputStream("workbookForGeocalc.docx")){
			document.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}