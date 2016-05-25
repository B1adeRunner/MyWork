package parsEnergyBase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class TxtFile{
	private XWPFDocument document = new XWPFDocument();
	private XWPFParagraph[] paragraphArray;
	private int i=0;
	
	public void fetchParagraphArray(List<ElectricalSubstation> listOfSubstation){
		paragraphArray = new XWPFParagraph[listOfSubstation.size()+1];
	}
	
	public void setParagraphArray(XWPFParagraph[] paragraphArray) {
		this.paragraphArray = paragraphArray;
	}

	public XWPFParagraph[] getParagraphArray() {
		return paragraphArray;
	}

	public void fillTheTxtFile(List<ElectricalSubstation> listOfSubstation){
		for(ElectricalSubstation concreteSubstation: listOfSubstation){
			paragraphArray[i] = document.createParagraph();
			paragraphArray[i].createRun().setText(concreteSubstation.getName());
			paragraphArray[i].createRun().setText(", ");
			paragraphArray[i].createRun().setText(concreteSubstation.getLatitudeOfSubstation());
			paragraphArray[i].createRun().setText(", ");
			paragraphArray[i].createRun().setText(concreteSubstation.getLongitudeOfSubstation());
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