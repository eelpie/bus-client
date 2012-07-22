package uk.co.eelpieconsulting.countdown.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.eelpieconsulting.busroutes.model.Stop;

public class StopSearchParserTest {

	private StopSearchParser stopSearchParser;

	@Before
	public void setup() {
		stopSearchParser = new StopSearchParser();
	}
		
	@Test
	public void canParseMarkerSearchResultsIntoListOfStops() throws Exception {
		final List<Stop> stops = stopSearchParser.parse(ContentLoader.loadContent("marker_search.json"));
		assertEquals(26, stops.size());
		
		final Stop stop = stops.get(0);
		assertEquals(49948, stop.getId());
		assertEquals("POPES GROTTO", stop.getName());
		assertEquals(51.44231079780184, stop.getLatitude(), 0);
		assertEquals(-0.3313948004782073, stop.getLongitude(), 0);
		
		assertFalse(stop.getRoutes().isEmpty());
	}
	
}
