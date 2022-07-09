package it.polito.tdp.nyc.model;

public class CittaDistanza implements Comparable<CittaDistanza>{
	
	private String city;
	private double distance;
	public CittaDistanza(String city, double distance) {
		super();
		this.city = city;
		this.distance = distance;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	@Override
	public int compareTo(CittaDistanza o) {
		// TODO Auto-generated method stub
		return (int)(this.getDistance()-o.getDistance());
	}
	
	

}
