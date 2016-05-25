package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import parsEnergyBase.ElectricalSubstation;
import parsEnergyBase.TxtFile;

public class TestTxtFile {
	private static TxtFile txtFile;
	private static List<ElectricalSubstation> listOfSubstation;
	private static ElectricalSubstation elSub;

	@BeforeClass
	public static void setUp() throws Exception {
		txtFile = new TxtFile();
		listOfSubstation = new ArrayList<ElectricalSubstation>();
		elSub = new ElectricalSubstation();
		elSub.setAreaOfSubstation("some data");
		elSub.setCountryOfSubstation("some data");
		elSub.setDistribution("some data");
		elSub.setHeightAboveSeaLevel("some data");
		elSub.setLatitudeOfSubstation("some data");
		elSub.setLongitudeOfSubstation("some data");
		elSub.setName("some data");
		elSub.setNumberOfPowerTransformers("some data");
		elSub.setWorkingVoltage("some data");
		for(int i=0;i<1000;i++){
			listOfSubstation.add(elSub);
		}
		txtFile.fetchParagraphArray(listOfSubstation);
	}

	@Test
	public void testIsParagraphArrayFilled(){
		txtFile.fillTheTxtFile(listOfSubstation);
		for(int i=0;i<txtFile.getParagraphArray().length-1;i++){
			assertNotNull(txtFile.getParagraphArray()[i].getParagraphText());
		}
	}
}
