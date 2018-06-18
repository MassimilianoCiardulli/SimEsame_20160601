package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private AirlineIdMap airlineIdMap ;
	private AirportIdMap airportIdMap ;
	private FlightDAO dao ;
	private Graph<Airport,DefaultWeightedEdge> graph ;
	private List<Airport> raggiungibili ;
	
	public Model() {
		
	}
	
	public List<Airline> getAirlines() {
		airlineIdMap = new AirlineIdMap() ;
		dao = new FlightDAO() ;
		
		return dao.getAirlines(airlineIdMap);
	}

	public void createGraph(Airline airline) {
		dao = new FlightDAO() ;
		airportIdMap = new AirportIdMap() ;
		graph = new SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class) ;
		
		List<Airport> airports = dao.getAllAirports(airportIdMap);
		
		Graphs.addAllVertices(this.graph, airports) ;
		System.out.println("vertici = "+graph.vertexSet().size());
		List<AirportSourceDestination> edges = dao.getAllSourceDestination(airportIdMap, airline) ;
		
		for(AirportSourceDestination asd : edges) {
			double weight = calcolaPeso(asd.getSource(), asd.getDestination()) ;
			Graphs.addEdge(this.graph, asd.getSource(), asd.getDestination(), weight);
		}
		System.out.println("archi = "+graph.edgeSet().size());
	}

	private double calcolaPeso(Airport source, Airport destination) {
		return LatLngTool.distance(new LatLng(source.getLatitude(), source.getLongitude()), 
												new LatLng(destination.getLatitude(), destination.getLongitude()), LengthUnit.KILOMETER) ;

	}
	
	/**
	 * Calcolo gli aeroporti raggiunti
	 * @param airline 
	 * @return lista degli aeroporti raggiunti dalla compagnia aerea
	 */
	public List<Airport> getReachedAirports(Airline airline) {
		
		if(graph == null)
			this.createGraph(airline);
		
		raggiungibili = dao.getRaggiungibili(airportIdMap, airline) ;
		Collections.sort(raggiungibili);
		return raggiungibili ;
	}

	public List<Airport> getReachedAirports(Airline airline, Airport airport) {
		if(graph == null)
			this.createGraph(airline);
		
		List<AirportSourceDestination> asd = new ArrayList<>();
		
		for(Airport dest : raggiungibili) {
			DijkstraShortestPath<Airport, DefaultWeightedEdge> dsp = new DijkstraShortestPath<>(this.graph, airport, dest);
			GraphPath<Airport, DefaultWeightedEdge> gp = dsp.getPath();
			if(gp!=null) {
				double weight = this.calcolaPeso(airport, dest) ;
				asd.add(new AirportSourceDestination(airport, dest, weight));
			}
		}
		
		Collections.sort(asd);
		
		List<Airport> result = new ArrayList<Airport>() ;
		for(AirportSourceDestination a : asd)
			result.add(a.getDestination()) ;
		
		return result;
	}

}
