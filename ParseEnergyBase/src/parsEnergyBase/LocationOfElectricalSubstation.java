package parsEnergyBase;

public class LocationOfElectricalSubstation {
	public LocationOfElectricalSubstation(){
		
	}
	
	private String country = "";
	private String area = "";
	private String longitude = "";
	private String latitude = "";
		
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
}