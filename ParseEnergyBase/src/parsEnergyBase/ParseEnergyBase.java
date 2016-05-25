package parsEnergyBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseEnergyBase {
	private Integer countOfPage = 1;
	private ElectricalSubstation electricalSubstation;
	private Document pageOfSubstationList = null;
	private Document pageOfSubstation = null;
	private Elements elementWithNeededSubstring = null;
	private List<ElectricalSubstation> listOfSubstation = new ArrayList<ElectricalSubstation>();
	private String distribution;
	private String latitude;
	private String longitude;
	private String name;
	private String numberOfPowerTransformers;
	private String workingVoltage;
	private String area;
	private String country;
	private ExcelFile excelTable;
	private TxtFile txtFile;
	private StringTokenizer stringTokenizer;
	private String urlOfSubstationListPage;
	private String urlOfPageConcreteSubstation;

	public void doParse(){
		int countOfSubstation = 1;
		for(int i=357; i>=countOfPage; countOfPage++) {
			getUrlOfSubstationListPage();
			getPageOfSubstationList();
			parsePageOfSubstationList();
			for(Element element: elementWithNeededSubstring){
				getUrlOfPageConcreteSubstation(element);
				getPageOfConcreteSubstation();
				parseSubstationModel();
				addSubstationInToCollection(compileElectricalSubstation());
				System.out.println(countOfPage+": "+countOfSubstation);
				countOfSubstation++;
			}
			clearElementWithNeededSubstring();
		}
	}
	
	public void clearElementWithNeededSubstring(){
		elementWithNeededSubstring.clear();
	}
	
	public void getUrlOfSubstationListPage(){
		urlOfSubstationListPage = new String("http://energybase.ru/substation/index?page=".concat(countOfPage.toString()));
	}
	
	public void getPageOfSubstationList(){
		try {
			pageOfSubstationList = Jsoup.connect(urlOfSubstationListPage).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parsePageOfSubstationList(){
		elementWithNeededSubstring = pageOfSubstationList.getElementsByAttributeValueContaining("class", "icon substation");
	}
	
	public void getUrlOfPageConcreteSubstation(Element element){
		urlOfPageConcreteSubstation = new String("http://energybase.ru".concat(element.attr("href")));
		
	}
	
	public void getPageOfConcreteSubstation(){
		try {
			pageOfSubstation = Jsoup.connect(urlOfPageConcreteSubstation).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseSubstationModel(){
		fetchDistributionOfSubstation();
		fetchNameOfSubstation();
		fetchCoordinatesOfSubstation();
		fetchWorkingVoltageOfSubstation();
		fetchNumberOfPowerTransformersOfSubstation();
		fetchAreaOfSubstation();
		fetchCountryOfSubstation();
	}
	
	public void fetchNameOfSubstation(){
		name = pageOfSubstation.getElementsByTag("h1").text();
	}
	
	public void fetchDistributionOfSubstation(){
		Elements elems = pageOfSubstation.getElementsByAttributeValueContaining("href", "/distribution/");
		if(elems.isEmpty()){
			distribution = "";
		} else {
			distribution = elems.first().text();
		}
	}
	
	public void fetchCoordinatesOfSubstation(){
		Elements elems = pageOfSubstation.getElementsByAttributeValueContaining("href", "#yandex-map");
		if (elems.isEmpty()){
			latitude = "";
			longitude = "";
		} else {
			stringTokenizer = new StringTokenizer(elems.get(0).text(), " ");
			for(int i=0;i<2;i++)
				latitude = stringTokenizer.nextToken();
			stringTokenizer = new StringTokenizer(elems.get(1).text(), " ");
			for(int i=0;i<2;i++)
				longitude = stringTokenizer.nextToken();
		}
	}
	
	public void fetchWorkingVoltageOfSubstation(){
		Elements elems = pageOfSubstation. 	getElementsContainingOwnText("Рабочее напряжение:");
		if(elems.isEmpty()){
			workingVoltage = "";
		} else {
			stringTokenizer = new StringTokenizer(elems.get(0).text(), ": ");
			for(int i =0;i<3;i++)
				workingVoltage = stringTokenizer.nextToken();
		}
	}
	
	public void fetchNumberOfPowerTransformersOfSubstation(){
		Elements elems = pageOfSubstation.getElementsContainingOwnText("Количество силовых трансформаторов:");
		if(elems.isEmpty()){
			numberOfPowerTransformers = "";
		} else {
			stringTokenizer = new StringTokenizer(elems.get(0).text(), ": ");
			for(int i=0;i<4;i++)
				numberOfPowerTransformers = stringTokenizer.nextToken();
		}
	}
	
	public void fetchAreaOfSubstation(){
		Elements elems = pageOfSubstation.getElementsByAttributeValueContaining("itemprop", "streetAddress");
		if(elems.isEmpty()){
			area = "";
		} else {
			area = elems.get(0).attr("content");
		}
	}
	
	public void fetchCountryOfSubstation(){
		Elements elems = pageOfSubstation.getElementsByAttributeValueContaining("itemprop", "addressCountry");
		if(elems.isEmpty()){
			country = "";
		} else {
			country = elems.get(0).attr("content");
		}
	}
	
	public ElectricalSubstation compileElectricalSubstation(){
		electricalSubstation = new ElectricalSubstation();
		electricalSubstation.setDistribution(distribution);
		electricalSubstation.setLatitudeOfSubstation(latitude);
		electricalSubstation.setLongitudeOfSubstation(longitude);
		electricalSubstation.setName(name);
		electricalSubstation.setNumberOfPowerTransformers(numberOfPowerTransformers);
		electricalSubstation.setWorkingVoltage(workingVoltage);
		electricalSubstation.setAreaOfSubstation(area);
		electricalSubstation.setCountryOfSubstation(country);
		return electricalSubstation;
	}
	
	public void addSubstationInToCollection(ElectricalSubstation electricalSubstation){
		listOfSubstation.add(electricalSubstation);
	}
	
	public void buildEcxelFile(){
		excelTable = new ExcelFile();
		excelTable.createTable(listOfSubstation);
		excelTable.writeToFile();
	}
	
	public void buildTxtFile(){
		txtFile = new TxtFile();
		txtFile.createForme(listOfSubstation);
		txtFile.writeToFile();
	}
	
	public List<ElectricalSubstation> getListOfSubstation() {
		return listOfSubstation;
	}

	public void setListOfSubstation(List<ElectricalSubstation> listOfSubstation) {
		this.listOfSubstation = listOfSubstation;
	}
	
	public static void main(String[] args) {
		ParseEnergyBase parseEnergyBase = new ParseEnergyBase();
		parseEnergyBase.doParse();
		parseEnergyBase.buildEcxelFile();
		parseEnergyBase.buildTxtFile();
	}
}
