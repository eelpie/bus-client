package uk.co.eelpieconsulting.countdown.urls;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CountdownApiUrlBuilderTest {

	private static final String API_HOST = "http://buses.eelpieconsulting.co.uk";
	private CountdownApiUrlBuilder urlBuilder;
	
	@Before
	public void setup() {
		urlBuilder = new CountdownApiUrlBuilder(API_HOST);
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

}
