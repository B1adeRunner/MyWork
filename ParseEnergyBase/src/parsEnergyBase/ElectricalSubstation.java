package parsEnergyBase;

public class ElectricalSubstation{
	public ElectricalSubstation(){
		
	}
	
	private String name = "";
	private String distribution = "";
	private String workingVoltage = "";
	private String numberOfPowerTransformers = "";
	private String heightAboveSeaLevel = "";
	private LocationOfElectricalSubstation locationOfElectricalSubstation = new LocationOfElectricalSubstation();
	
	public String getHeightAboveSeaLevel() {
		return heightAboveSeaLevel;
	}
	public void setHeightAboveSeaLevel(String heightAboveSeaLevel) {
		this.heightAboveSeaLevel = heightAboveSeaLevel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDistribution() {
		return distribution;
	}
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}
	public String getWorkingVoltage() {
		return workingVoltage;
	}
	public void setWorkingVoltage(String workingVoltage) {
		this.workingVoltage = workingVoltage;
	}
	public String getNumberOfPowerTransformers() {
		return numberOfPowerTransformers;
	}
	public void setNumberOfPowerTransformers(String numberOfPowerTransformers) {
		this.numberOfPowerTransformers = numberOfPowerTransformers;
	}
	public String getLongitudeOfSubstation() {
		return locationOfElectricalSubstation.getLongitude();
	}
	public void setLongitudeOfSubstation(String longitude) {
		locationOfElectricalSubstation.setLongitude(longitude);
	}
	public String getAreaOfSubstation() {
		return locationOfElectricalSubstation.getArea();
	}
	public void setAreaOfSubstation(String area) {
		locationOfElectricalSubstation.setArea(area);
	}
	public String getCountryOfSubstation() {
		return locationOfElectricalSubstation.getCountry();
	}
	public void setCountryOfSubstation(String country) {
		locationOfElectricalSubstation.setCountry(country);
	}
	public String getLatitudeOfSubstation() {
		return locationOfElectricalSubstation.getLatitude();
	}
	public void setLatitudeOfSubstation(String latitude) {
		locationOfElectricalSubstation.setLatitude(latitude);
	}
}