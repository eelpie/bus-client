package uk.co.eelpieconsulting.buses.client;

import java.util.List;

import org.json.JSONException;

import uk.co.eelpieconsulting.buses.client.exceptions.HttpFetchException;
import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.buses.client.model.StopBoard;
import uk.co.eelpieconsulting.buses.client.parsers.RouteParser;
import uk.co.eelpieconsulting.buses.client.parsers.StopBoardParser;
import uk.co.eelpieconsulting.buses.client.parsers.StopMessageParser;
import uk.co.eelpieconsulting.buses.client.parsers.StopParser;
import uk.co.eelpieconsulting.buses.client.urls.UrlBuilder;
import uk.co.eelpieconsulting.buses.client.util.HttpFetcher;
import uk.co.eelpieconsulting.busroutes.model.MultiStopMessage;
import uk.co.eelpieconsulting.busroutes.model.Route;
import uk.co.eelpieconsulting.busroutes.model.Stop;

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
		return stopBoardParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getStopBoardUrl(stopId), "UTF-8"));
	}

	public List<Stop> findStopsWithin(double latitude, double longitude, int radius) throws HttpFetchException, ParsingException {
		return stopSearchParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getMarkerSearchUrl(latitude, longitude, radius), "UTF-8"));
	}
	
	public List<Route> findRoutesWithin(double latitude, double longitude, int radius) throws HttpFetchException, JSONException {
		return routeParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getNearbyRoutesUrl(latitude, longitude, radius), "UTF-8"));
	}
	
	public List<Stop> searchStops(String q) throws HttpFetchException, ParsingException {
		return stopSearchParser.parse(httpFetcher.fetchContent(countdownApiUrlBuilder.getStopSearchUrl(q), "UTF-8"));
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
