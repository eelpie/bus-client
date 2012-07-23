package uk.co.eelpieconsulting.buses.client;

import java.util.List;

import uk.co.eelpieconsulting.buses.client.exceptions.HttpFetchException;
import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.buses.client.model.StopBoard;
import uk.co.eelpieconsulting.buses.client.parsers.StopBoardParser;
import uk.co.eelpieconsulting.buses.client.parsers.StopSearchParser;
import uk.co.eelpieconsulting.buses.client.urls.CountdownApiUrlBuilder;
import uk.co.eelpieconsulting.buses.client.util.HttpFetcher;
import uk.co.eelpieconsulting.busroutes.model.Stop;

public class CountdownApi {
	
	final private CountdownApiUrlBuilder countdownApiUrlBuilder;
	final private HttpFetcher httpFetcher;
	final private StopBoardParser stopBoardParser;
	final private StopSearchParser stopSearchParser;

	public CountdownApi(String apiUrl) {
		this.countdownApiUrlBuilder = new CountdownApiUrlBuilder(apiUrl);
		this.httpFetcher = new HttpFetcher();
		this.stopBoardParser = new StopBoardParser();
		this.stopSearchParser = new StopSearchParser();
	}
	
	public CountdownApi(CountdownApiUrlBuilder countdownApiUrlBuilder, HttpFetcher httpFetcher, StopBoardParser stopBoardParser, StopSearchParser stopSearchParser) {
		this.countdownApiUrlBuilder = countdownApiUrlBuilder;
		this.httpFetcher = httpFetcher;
		this.stopBoardParser = stopBoardParser;
		this.stopSearchParser = stopSearchParser;
	}
	
	public StopBoard getStopBoard(int stopId) throws HttpFetchException, ParsingException {
		return stopBoardParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getStopBoardUrl(stopId), "UTF-8"));
	}

	public List<Stop> findStopsWithin(double latitude, double longitude, int radius) throws HttpFetchException, ParsingException {
		return stopSearchParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getMarkerSearchUrl(latitude, longitude, radius), "UTF-8"));
	}

	public Stop findStopById(int id) throws HttpFetchException, ParsingException {
		return stopSearchParser.parseStop(httpFetcher.fetchContent(countdownApiUrlBuilder.getStopIdSearchUrl(id), "UTF-8"));
	}
	
	public List<Stop> getRouteStops(String route, int run) throws HttpFetchException, ParsingException {
		return stopSearchParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getRoutesStopUrl(route, run), "UTF-8"));
	}
	
}