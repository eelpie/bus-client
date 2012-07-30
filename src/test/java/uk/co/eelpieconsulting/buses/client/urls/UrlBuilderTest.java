package uk.co.eelpieconsulting.buses.client.urls;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uk.co.eelpieconsulting.buses.client.urls.UrlBuilder;

public class UrlBuilderTest {

	private static final String API_HOST = "http://buses.eelpieconsulting.co.uk";
	private UrlBuilder urlBuilder;
	
	@Before
	public void setup() {
		urlBuilder = new UrlBuilder(API_HOST);
	}
	
	@Test
	public void canConstructUrlForStopBoardJSONRequest() {				
		assertEquals("http://buses.eelpieconsulting.co.uk/stop/53550/arrivals", urlBuilder.getStopBoardUrl(53550));
	}
	
	@Test
	public void canConstructUrlForMarkerSearch() throws Exception {
		assertEquals("http://buses.eelpieconsulting.co.uk/stops/near?latitude=51.454&longitude=-0.351", urlBuilder.getMarkerSearchUrl(51.454, -0.351, 250));
	}
	
	@Test
	public void canConstructUrlForStopIdSearch() throws Exception {
		assertEquals("http://buses.eelpieconsulting.co.uk/stop/53550", urlBuilder.getStopIdSearchUrl(53550));
	}
	
	@Test
	public void canConstructUrlForRouteStops() throws Exception {
		assertEquals("http://buses.eelpieconsulting.co.uk/route/N22/1/stops", urlBuilder.getRoutesStopUrl("N22", 1));
	}
	
	@Test
	public void canConstructMultipleStopMessagesUrl() throws Exception {
		assertEquals("http://buses.eelpieconsulting.co.uk/messages?stops=53550,53551", urlBuilder.getStopMessagesUrl(new int[] {53550, 53551}));
	}
	
	@Test
	public void canConstructStopSearchUrl() throws Exception {
		assertEquals("http://buses.eelpieconsulting.co.uk/stops/search?q=53550", urlBuilder.getStopSearchUrl("53550"));
	}
}
