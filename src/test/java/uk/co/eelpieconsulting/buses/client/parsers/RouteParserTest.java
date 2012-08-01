package uk.co.eelpieconsulting.buses.client.parsers;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.eelpieconsulting.busroutes.model.Route;

public class RouteParserTest {

	private RouteParser routeParser;

	@Before
	public void setup() {
		routeParser = new RouteParser();
	}
	
	@Test
	public void canParseListOfRoutes() throws Exception {
		final List<Route> routes = routeParser.parse(ContentLoader.loadContent("routes_nearby.json"));

		assertEquals(10, routes.size());
		assertEquals("281", routes.get(0).getRoute());
		assertEquals(1, routes.get(0).getRun());
		assertEquals("Hounslow Bus Station", routes.get(0).getTowards());
	}
	
}
