package it.polito.tdp.flight.model;

public class AirportSourceDestination implements Comparable<AirportSourceDestination>{
	private Airport source ;
	private Airport destination ;
	private double weight ;
	public AirportSourceDestination(Airport source, Airport destination) {
		super();
		this.source = source;
		this.destination = destination;
	}
	public AirportSourceDestination(Airport airport, Airport dest, double weight) {
		this.source = airport ;
		this.destination = dest ;
		this.setWeight(weight) ;
	}
	public Airport getSource() {
		return source;
	}
	public Airport getDestination() {
		return destination;
	}
	public String toString() {
		return source.getName() + " ---> " + destination.getName() ;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Override
	public int compareTo(AirportSourceDestination other) {
		if(this.weight > other.weight)
			return 1 ;
		if(this.weight < other.weight)
			return -1 ;
		return 0;
	}
}
