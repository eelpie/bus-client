package uk.co.eelpieconsulting.buses.client;

import java.util.List;

import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.buses.client.model.FileInformation;
import uk.co.eelpieconsulting.buses.client.model.RoutesNear;
import uk.co.eelpieconsulting.buses.client.model.StopBoard;
import uk.co.eelpieconsulting.buses.client.model.StopsNear;
import uk.co.eelpieconsulting.buses.client.parsers.RouteParser;
import uk.co.eelpieconsulting.buses.client.parsers.SourceFileInformationParser;
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
	
	private final UrlBuilder urlBuilder;
	private final HttpFetcher httpFetcher;
	private final StopBoardParser stopBoardParser;
	private final StopParser stopSearchParser;
	private final StopMessageParser stopMessageParser;
	private final RouteParser routeParser;
	private final SourceFileInformationParser sourceFileInformationParser;

	public BusesClient(String apiUrl) {
		this.urlBuilder = new UrlBuilder(apiUrl);
		this.httpFetcher = new HttpFetcher();
		this.stopBoardParser = new StopBoardParser();
		this.stopSearchParser = new StopParser();
		this.stopMessageParser = new StopMessageParser();
		this.routeParser = new RouteParser();
		this.sourceFileInformationParser = new SourceFileInformationParser();
	}
	
	public BusesClient(UrlBuilder urlBuilder, HttpFetcher httpFetcher, StopBoardParser stopBoardParser, StopParser stopSearchParser, StopMessageParser stopMessageParser, RouteParser routeParser, SourceFileInformationParser sourceFileInformationParser) {
		this.urlBuilder = urlBuilder;
		this.httpFetcher = httpFetcher;
		this.stopBoardParser = stopBoardParser;
		this.stopSearchParser = stopSearchParser;
		this.stopMessageParser = stopMessageParser;
		this.routeParser = routeParser;
		this.sourceFileInformationParser = sourceFileInformationParser;
	}
	
	public StopBoard getStopBoard(int stopId) throws HttpFetchException, ParsingException {
		return stopBoardParser.parse(httpFetcher.get(urlBuilder.getStopBoardUrl(stopId)));
	}

	public List<Stop> findStopsWithin(double latitude, double longitude, int radius) throws HttpFetchException, ParsingException {
		return stopSearchParser.parse(httpFetcher.get(urlBuilder.getMarkerSearchUrl(latitude, longitude, radius)));
	}
	
	public StopsNear findStopsNearLocation(double latitude, double longitude, int radius) throws HttpFetchException, ParsingException {
		return stopSearchParser.parseStopsNear(httpFetcher.get(urlBuilder.getStopsNearLocationUrl(latitude, longitude, radius)));
	}
	
	public RoutesNear findRoutesNearLocation(double latitude, double longitude, int radius) throws HttpFetchException, ParsingException {
		return routeParser.parseRoutesNear(httpFetcher.get(urlBuilder.getRoutesNearLocationUrl(latitude, longitude, radius)));
	}
	
	public List<Route> findRoutesWithin(double latitude, double longitude, int radius) throws HttpFetchException, ParsingException {
		return routeParser.parse(httpFetcher.get(urlBuilder.getNearbyRoutesUrl(latitude, longitude, radius)));
	}
	
	public List<Stop> searchStops(String q) throws HttpFetchException, ParsingException {
		return stopSearchParser.parse(httpFetcher.get(urlBuilder.getStopSearchUrl(q)));
	}
	
	public Stop findStopById(int id) throws HttpFetchException, ParsingException {
		return stopSearchParser.parseStop(httpFetcher.get(urlBuilder.getStopIdSearchUrl(id)));
	}
	
	public List<MultiStopMessage> getStopMessages(int stopId) throws HttpFetchException, ParsingException {
		return stopMessageParser.parse(httpFetcher.get(urlBuilder.getStopMessagesUrl(stopId)));
	}
	
	public List<MultiStopMessage> getMultipleStopMessages(int[] stopIds) throws HttpFetchException, ParsingException {
		return stopMessageParser.parse(httpFetcher.get(urlBuilder.getStopMessagesUrl(stopIds)));
	}
	
	public List<Stop> getRouteStops(String route, int run) throws HttpFetchException, ParsingException {
		return stopSearchParser.parse(httpFetcher.get(urlBuilder.getRoutesStopUrl(route, run)));
	}
	
	public List<FileInformation> getSourceFileInformation() throws HttpFetchException, ParsingException {
		return sourceFileInformationParser.parse(httpFetcher.get(urlBuilder.getSourceFileInformationUrl()));
	}
	
}
