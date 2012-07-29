package uk.co.eelpieconsulting.buses.client.parsers;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.eelpieconsulting.busroutes.model.Message;
import uk.co.eelpieconsulting.busroutes.model.MultiStopMessage;

public class StopMessageParserTest {

	private StopMessageParser stopMessageParser;

	@Before
	public void setup() {
		stopMessageParser = new StopMessageParser();
	}
	
	@Test
	public void canParseMessages() throws Exception {
		final List<MultiStopMessage> messages = stopMessageParser.parse(ContentLoader.loadContent("single_stop_messages.json"));

		assertEquals(2, messages.size());
		
		final Message message = messages.get(0);
		assertEquals(55350, message.getStopId());
		assertEquals("Sunday 29 July - Mens and Womens Olympic Cycling Road Race taking place. Road closures will be in place throughout the weekend affecting many bus routes. Check www.tfl.gov.uk/buses for more information.", message.getMessage());
	}
	
	@Test
	public void canParseEffectedStopsCorrectly() throws Exception {
		final List<MultiStopMessage> messages = stopMessageParser.parse(ContentLoader.loadContent("multiple_stops_messages.json"));
		
		assertEquals(2, messages.size());
		
		final MultiStopMessage message = messages.get(0);
		assertEquals(2, message.getStops().size());		
	}
	
}
