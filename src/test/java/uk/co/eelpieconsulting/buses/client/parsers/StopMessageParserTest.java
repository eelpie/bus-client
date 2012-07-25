package uk.co.eelpieconsulting.buses.client.parsers;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.eelpieconsulting.busroutes.model.Message;

public class StopMessageParserTest {

	private StopMessageParser stopMessageParser;

	@Before
	public void setup() {
		stopMessageParser = new StopMessageParser();
	}
	
	@Test
	public void canParseMessages() throws Exception {
		final List<Message> messages = stopMessageParser.parse(ContentLoader.loadContent("messages.json"));

		assertEquals(2, messages.size());
		final Message message = messages.get(1);
		assertEquals(47843, message.getStopId());
		assertEquals("Bus route 214 on diversion in both directions in the Highgate West Hill area due to emergency roadworks.", message.getMessage());
	}
	
}
