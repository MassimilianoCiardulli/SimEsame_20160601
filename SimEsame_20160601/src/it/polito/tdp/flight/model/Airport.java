package it.polito.tdp.flight.model;

public class Airport implements Comparable<Airport>{
	
	private int airportId ;
	private String name ;
	private String city ;
	private String country ;
	private String iataFaa ;
	private String icao ;
	private double latitude ;
	private double longitude ;
	private float timezone ;
	private String dst ;
	private String tz ;
	
	public Airport(int airportId, String name, String city, String country, String iataFaa, String icao,
			double latitude, double longitude, float timezone, String dst, String tz) {
		super();
		this.airportId = airportId;
		this.name = name;
		this.city = city;
		this.country = country;
		this.iataFaa = iataFaa;
		this.icao = icao;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timezone = timezone;
		this.dst = dst;
		this.tz = tz;
	}

	public int getAirportId() {
		return airportId;
	}

	public void setAirportId(int airportId) {
		this.airportId = airportId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIataFaa() {
		return iataFaa;
	}

	public void setIataFaa(String iataFaa) {
		this.iataFaa = iataFaa;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getTimezone() {
		return timezone;
	}

	public void setTimezone(float timezone) {
		this.timezone = timezone;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}
	
	public String toString() {
		return this.name ;
	}

	@Override
	public int compareTo(Airport other) {
		// TODO Auto-generated method stub
		return this.name.compareTo(other.name);
	}

}