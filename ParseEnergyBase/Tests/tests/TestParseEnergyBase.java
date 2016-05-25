package tests;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import parsEnergyBase.ElectricalSubstation;
import parsEnergyBase.ParseEnergyBase;

public class TestParseEnergyBase {

	@Before
	public void initialNeededObjects(){

	
/*	public void parsePageOfSubstationList(){
		String firstPartOfURL = "http://energybase.ru/substation/index?page=";
		String secondPartOfURL = new String(countOfPage.toString());
		try {
			pageOfSubstationList = Jsoup.connect(firstPartOfURL.concat(secondPartOfURL).toString()).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		elemWithNeededSubstring = pageOfSubstationList.getElementsByAttributeValueContaining("class", "icon substation");
	}*/
	
/*	@Test
	public void testIsParseEnergyBaseCreated(){
		assertNotEquals(null, );
	}	
	
	@Test
	public void testIsListOfSubstationCreated(){
		assertNotEquals(null, );
	}*/	
	
/*	@Test
	public void testIsSecondPartOfURLCreated(){
		assertNotEquals(null, secondPartOfURL);
	}	
	
	@Test
	public void testIsParseEnergyBaseCreated(){
		assertNotEquals(null, parseEnergyBase);
	}	
	
	@Test
	public void testIsParseEnergyBaseCreated(){
		assertNotEquals(null, parseEnergyBase);*/
	}	
	
	
	@After
	public void destroyAllObjects(){
		
	}

}
