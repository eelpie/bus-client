package uk.co.eelpieconsulting.buses.client.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.eelpieconsulting.buses.client.model.StopsNear;
import uk.co.eelpieconsulting.busroutes.model.Stop;

public class StopParserTest {

	private StopParser stopParser;

	@Before
	public void setup() {
		stopParser = new StopParser();
	}
	
	@Test
	public void routeStopsShouldHaveTheCorrectRoutesAttached() throws Exception {
		final List<Stop> routeStops = stopParser.parse(ContentLoader.loadContent("route_stops.json"));

		for (Stop stop : routeStops) {
			if (stop.getName().toUpperCase().equals("WHITTON ROAD")) {				
				assertEquals(1, stop.getRoutes().size());
				assertEquals("267", stop.getRoutes().iterator().next().getRoute());
				return;
			}
		}
		fail();
	}
	
	@Test
	public void canParseNearbyStops() throws Exception {
		StopsNear result = stopParser.parseStopsNear(ContentLoader.loadContent("stops_nearby.json"));
		
		assertEquals("York Street, Ham, London Borough of Richmond upon Thames", result.getLocation());
		assertEquals("York Street / Twickenham", result.getStops().get(0).getName());
	}
	
}
