package uk.co.eelpieconsulting.buses.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.co.eelpieconsulting.buses.client.CountdownApi;
import uk.co.eelpieconsulting.buses.client.exceptions.HttpFetchException;
import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.buses.client.model.StopBoard;
import uk.co.eelpieconsulting.buses.client.parsers.StopBoardParser;
import uk.co.eelpieconsulting.buses.client.parsers.StopSearchParser;
import uk.co.eelpieconsulting.buses.client.urls.CountdownApiUrlBuilder;
import uk.co.eelpieconsulting.buses.client.util.HttpFetcher;
import uk.co.eelpieconsulting.busroutes.model.Stop;

public class CountdownApiTest {
	
	private static final int STOPBOARD_ID = 123;
	private static final String STOPBOARD_URL = "http://stopboard/123";
	private static final String STOPBOARD_JSON = "{some json}";
	private static final String STOP_SEARCH_URL = "http://stopsearch/bounding/box";
	private static final String STOP_SEARCH_JSON = "{some json with stops in it}";
	private static final double LAT = 1;
	private static final double LNG = -1;
	private static final int RADIUS = 250;
	
	@Mock CountdownApiUrlBuilder countdownApiUrlBuilder;
	@Mock HttpFetcher httpFetcher;	
	@Mock StopBoardParser stopBoardParser;
	@Mock StopSearchParser stopSearchParser;
	
	@Mock StopBoard stopBoard;
	@Mock List<Stop> stops;
	
	private CountdownApi api;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		api = new CountdownApi(countdownApiUrlBuilder, httpFetcher, stopBoardParser, stopSearchParser);
	}
	
	@Test
	public void canFetchStopboardFromLiveApiEndpoints() throws Exception {		
		when(countdownApiUrlBuilder.getStopBoardUrl(STOPBOARD_ID)).thenReturn(STOPBOARD_URL);
		when(httpFetcher.fetchContent(STOPBOARD_URL, "UTF-8")).thenReturn(STOPBOARD_JSON);
		when(stopBoardParser.parse(STOPBOARD_JSON)).thenReturn(stopBoard);
		
		StopBoard returnedStopBoard = api.getStopBoard(STOPBOARD_ID);
		
		assertEquals(stopBoard, returnedStopBoard);		
	}
	
	@Test(expected = HttpFetchException.class)
	public void shouldThrowInformativeExceptionIfHttpFetchFails() throws Exception {
		when(countdownApiUrlBuilder.getStopBoardUrl(STOPBOARD_ID)).thenReturn(STOPBOARD_URL);
		when(httpFetcher.fetchContent(STOPBOARD_URL, "UTF-8")).thenThrow(new HttpFetchException());
		
		api.getStopBoard(STOPBOARD_ID);
	}
	
	@Test(expected = ParsingException.class)
	public void shouldThrowInformativeExceptionIfParsingFails() throws Exception {
		when(countdownApiUrlBuilder.getStopBoardUrl(STOPBOARD_ID)).thenReturn(STOPBOARD_URL);
		when(httpFetcher.fetchContent(STOPBOARD_URL, "UTF-8")).thenReturn(STOPBOARD_JSON);
		when(stopBoardParser.parse(STOPBOARD_JSON)).thenThrow(new ParsingException());
		
		api.getStopBoard(STOPBOARD_ID);
	}
	
	@Test
	public void canSearchForStopsWithinBoundingBox() throws Exception {
		when(countdownApiUrlBuilder.getMarkerSearchUrl(LAT, LNG, RADIUS)).thenReturn(STOP_SEARCH_URL);
		when(httpFetcher.fetchContent(STOP_SEARCH_URL, "UTF-8")).thenReturn(STOP_SEARCH_JSON);
		when(stopSearchParser.parse(STOP_SEARCH_JSON)).thenReturn(stops);
		
		List<Stop> returnedStops = api.findStopsWithin(LAT, LNG, RADIUS);
		
		assertEquals(stops, returnedStops);
	}
	
}
