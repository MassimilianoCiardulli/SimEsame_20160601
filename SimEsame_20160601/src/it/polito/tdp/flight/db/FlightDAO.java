package it.polito.tdp.flight.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.AirlineIdMap;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.AirportIdMap;
import it.polito.tdp.flight.model.AirportSourceDestination;

public class FlightDAO {

	public List<Airport> getAllAirports(AirportIdMap airportIdMap) {
		
		String sql = "SELECT * FROM airport" ;
		
		List<Airport> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Airport airport = new Airport(
						res.getInt("Airport_ID"),
						res.getString("name"),
						res.getString("city"),
						res.getString("country"),
						res.getString("IATA_FAA"),
						res.getString("ICAO"),
						res.getDouble("Latitude"),
						res.getDouble("Longitude"),
						res.getFloat("timezone"),
						res.getString("dst"),
						res.getString("tz")) ;
				list.add(airportIdMap.get(airport)) ;
			}
			
			conn.close();
			
			return list ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Airline> getAirlines(AirlineIdMap airlineIdMap) {
		final String sql = "select * from airline order by name asc" ;
		
		List<Airline> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Airline a = new Airline(res.getInt("Airline_ID"), res.getString("name"), res.getString("alias"),
						res.getString("IATA"), res.getString("ICAO"), res.getString("Callsign"),
						res.getString("Country"), res.getString("Active"));
				list.add(airlineIdMap.get(a));
			}
			
			conn.close();
			
			return list ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}

	public List<AirportSourceDestination> getAllSourceDestination(AirportIdMap airportIdMap, Airline airline) {
		final String sql = "select distinct r1.Source_airport_ID, r2.Destination_airport_ID " + 
				"from route as r1, route as r2 " + 
				"where r1.Airline_ID = r2.Airline_ID " + 
				"and r1.Airline_ID = ? " + 
				"and r1.Source_airport_ID<>r2.Destination_airport_ID " + 
				"order by r1.Source_airport_ID " ;
		List<AirportSourceDestination> result = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, airline.getAirlineId());
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				result.add(new AirportSourceDestination(airportIdMap.get(res.getInt("Source_airport_ID")),
						airportIdMap.get(res.getInt("Destination_airport_ID")))) ;
			}
			
			conn.close();
			
			return result ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}

	public List<Airport> getRaggiungibili(AirportIdMap airportIdMap, Airline airline) {
		final String sql = "select distinct a.Airport_ID " + 
				"from route as r, airport as a " + 
				"where r.Airline_ID=? " + 
				"and (r.Source_airport_ID = a.Airport_ID " + 
				"or r.Destination_airport_ID = a.Airport_ID)" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, airline.getAirlineId());
			ResultSet res = st.executeQuery() ;
			List<Airport> result = new ArrayList<>();
			while(res.next()) {
				
				result.add(airportIdMap.get(res.getInt("Airport_ID"))) ;
			}
			
			conn.close();
			
			return result ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}
	
}
