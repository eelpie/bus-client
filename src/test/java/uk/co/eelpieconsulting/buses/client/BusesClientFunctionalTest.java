package uk.co.eelpieconsulting.buses.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.eelpieconsulting.buses.client.BusesClient;
import uk.co.eelpieconsulting.buses.client.model.Arrival;
import uk.co.eelpieconsulting.buses.client.model.StopBoard;
import uk.co.eelpieconsulting.busroutes.model.Stop;

public class BusesClientFunctionalTest {
	
	private static final String API_URL = "http://buses.eelpieconsulting.co.uk";
	
	private BusesClient api;

	@Before
	public void setup() {		
		api = new BusesClient(API_URL);
	}

	@Test
	public void stopBoardTest() throws Exception {		
		StopBoard stopBoard = api.getStopBoard(53550);
		
		assertNotNull(stopBoard);		
		for (Arrival arrival : stopBoard.getArrivals()) {
			System.out.println(arrival);
		}
	}
	
	@Test
	public void stopSearchTest() throws Exception {
		List<Stop> stops = api.findStopsWithin(51.454, -0.351, 1000);
		
		assertNotNull(stops);
		assertFalse(stops.isEmpty());
		for (Stop stop : stops) {
			System.out.println(stop);
		}
	}
	
	@Test
	public void routeStopsTest() throws Exception {
		final List<Stop> routeStops = api.getRouteStops("R68", 2);
		for (Stop stop : routeStops) {
			System.out.println(stop.getName());
		}
		assertEquals(46, routeStops.size());
	}
	
	@Test
	public void canLocateStopById() throws Exception {
		final Stop stop = api.findStopById(53550);
		
		assertEquals(53550, stop.getId());
		assertEquals("York Street / Twickenham", stop.getName());
	}
	
	// Example usage for README file
	@Test
	public void exampleUsage() throws Exception {
		final BusesClient api = new BusesClient(API_URL);
		
		// Load a list of stops within a circle
		System.out.println("Searching for stops within circle");
		List<Stop> stops = api.findStopsWithin(51.448, -0.3269, 250);
		System.out.println("Found " + stops.size() + " stops");
		
		Stop firstStop = stops.get(0);
		System.out.println("This first one is: " + firstStop.getName() + " (" + firstStop.getId() + ") towards " + firstStop.getTowards() + " at " + firstStop.getLatitude() + ", " + firstStop.getLongitude());
		
		// Load a list of expected arrivals for a stop
		System.out.println("Loading arrivals for stop: " + firstStop.getId());
		StopBoard stopBoard = api.getStopBoard(firstStop.getId());
		Arrival firstArrival = stopBoard.getArrivals().get(0);	
		System.out.println("Next arrival is " + firstArrival.getRoute().getRoute() + " to " + firstArrival.getRoute().getTowards() + ": " + firstArrival.getEstimatedWait());
		System.out.println("Last updated: " + stopBoard.getLastUpdated());
	}
	
}
