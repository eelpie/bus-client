package uk.co.eelpieconsulting.buses.client;

import java.util.List;

import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.buses.client.model.StopBoard;
import uk.co.eelpieconsulting.buses.client.parsers.RouteParser;
import uk.co.eelpieconsulting.buses.client.parsers.StopBoardParser;
import uk.co.eelpieconsulting.buses.client.parsers.StopMessageParser;
import uk.co.eelpieconsulting.buses.client.parsers.StopParser;
import uk.co.eelpieconsulting.buses.client.urls.UrlBuilder;
import uk.co.eelpieconsulting.busroutes.model.MultiStopMessage;
import uk.co.eelpieconsulting.busroutes.model.Route;
import uk.co.eelpieconsulting.busroutes.model.Stop;
import uk.co.eelpieconsulting.common.http.HttpFetchException;
import uk.co.eelpieconsulting.common.http.HttpFetcher;

public class BusesClient {
	
	private final UrlBuilder countdownApiUrlBuilder;
	private final HttpFetcher httpFetcher;
	private final StopBoardParser stopBoardParser;
	private final StopParser stopSearchParser;
	private final StopMessageParser stopMessageParser;
	private final RouteParser routeParser;

	public BusesClient(String apiUrl) {
		this.countdownApiUrlBuilder = new UrlBuilder(apiUrl);
		this.httpFetcher = new HttpFetcher();
		this.stopBoardParser = new StopBoardParser();
		this.stopSearchParser = new StopParser();
		this.stopMessageParser = new StopMessageParser();
		this.routeParser = new RouteParser();
	}
	
	public BusesClient(UrlBuilder countdownApiUrlBuilder, HttpFetcher httpFetcher, StopBoardParser stopBoardParser, StopParser stopSearchParser, StopMessageParser stopMessageParser, RouteParser routeParser) {
		this.countdownApiUrlBuilder = countdownApiUrlBuilder;
		this.httpFetcher = httpFetcher;
		this.stopBoardParser = stopBoardParser;
		this.stopSearchParser = stopSearchParser;
		this.stopMessageParser = stopMessageParser;
		this.routeParser = routeParser;
	}
	
	public StopBoard getStopBoard(int stopId) throws HttpFetchException, ParsingException {
		return stopBoardParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getStopBoardUrl(stopId)));
	}

	public List<Stop> findStopsWithin(double latitude, double longitude, int radius) throws HttpFetchException, ParsingException {
		return stopSearchParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getMarkerSearchUrl(latitude, longitude, radius)));
	}
	
	public List<Route> findRoutesWithin(double latitude, double longitude, int radius) throws HttpFetchException, ParsingException {
		return routeParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getNearbyRoutesUrl(latitude, longitude, radius)));
	}
	
	public List<Stop> searchStops(String q) throws HttpFetchException, ParsingException {
		return stopSearchParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getStopSearchUrl(q)));
	}
	
	public Stop findStopById(int id) throws HttpFetchException, ParsingException {
		return stopSearchParser.parseStop(httpFetcher.fetchContent(countdownApiUrlBuilder.getStopIdSearchUrl(id)));
	}
	
	public List<MultiStopMessage> getStopMessages(int stopId) throws HttpFetchException, ParsingException {
		return stopMessageParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getStopMessagesUrl(stopId)));
	}
	
	public List<MultiStopMessage> getMultipleStopMessages(int[] stopIds) throws HttpFetchException, ParsingException {
		return stopMessageParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getStopMessagesUrl(stopIds)));
	}
	
	public List<Stop> getRouteStops(String route, int run) throws HttpFetchException, ParsingException {
		return stopSearchParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getRoutesStopUrl(route, run)));
	}
	
}
