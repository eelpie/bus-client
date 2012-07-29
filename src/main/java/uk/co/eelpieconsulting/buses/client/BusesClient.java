package uk.co.eelpieconsulting.buses.client;

import java.util.List;

import uk.co.eelpieconsulting.buses.client.exceptions.HttpFetchException;
import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.buses.client.model.StopBoard;
import uk.co.eelpieconsulting.buses.client.parsers.StopBoardParser;
import uk.co.eelpieconsulting.buses.client.parsers.StopMessageParser;
import uk.co.eelpieconsulting.buses.client.parsers.StopParser;
import uk.co.eelpieconsulting.buses.client.urls.UrlBuilder;
import uk.co.eelpieconsulting.buses.client.util.HttpFetcher;
import uk.co.eelpieconsulting.busroutes.model.Message;
import uk.co.eelpieconsulting.busroutes.model.MultiStopMessage;
import uk.co.eelpieconsulting.busroutes.model.Stop;

public class BusesClient {
	
	final private UrlBuilder countdownApiUrlBuilder;
	final private HttpFetcher httpFetcher;
	final private StopBoardParser stopBoardParser;
	final private StopParser stopSearchParser;
	final private StopMessageParser stopMessageParser;

	public BusesClient(String apiUrl) {
		this.countdownApiUrlBuilder = new UrlBuilder(apiUrl);
		this.httpFetcher = new HttpFetcher();
		this.stopBoardParser = new StopBoardParser();
		this.stopSearchParser = new StopParser();
		this.stopMessageParser = new StopMessageParser();
	}
	
	public BusesClient(UrlBuilder countdownApiUrlBuilder, HttpFetcher httpFetcher, StopBoardParser stopBoardParser, StopParser stopSearchParser, StopMessageParser stopMessageParser) {
		this.countdownApiUrlBuilder = countdownApiUrlBuilder;
		this.httpFetcher = httpFetcher;
		this.stopBoardParser = stopBoardParser;
		this.stopSearchParser = stopSearchParser;
		this.stopMessageParser = stopMessageParser;
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
	
	public List<MultiStopMessage> getStopMessages(int stopId) throws HttpFetchException, ParsingException {
		return stopMessageParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getStopMessagesUrl(stopId), "UTF-8"));
	}
	
	public List<MultiStopMessage> getMultipleStopMessages(int[] stopIds) throws HttpFetchException, ParsingException {
		return stopMessageParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getStopMessagesUrl(stopIds), "UTF-8"));
	}
	
	public List<Stop> getRouteStops(String route, int run) throws HttpFetchException, ParsingException {
		return stopSearchParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getRoutesStopUrl(route, run), "UTF-8"));
	}
	
}
