package uk.co.eelpieconsulting.countdown.api;

import java.util.List;

import uk.co.eelpieconsulting.busroutes.model.Stop;
import uk.co.eelpieconsulting.countdown.exceptions.HttpFetchException;
import uk.co.eelpieconsulting.countdown.exceptions.ParsingException;
import uk.co.eelpieconsulting.countdown.model.StopBoard;
import uk.co.eelpieconsulting.countdown.parsers.StopBoardParser;
import uk.co.eelpieconsulting.countdown.parsers.StopSearchParser;
import uk.co.eelpieconsulting.countdown.urls.CountdownApiUrlBuilder;
import uk.co.eelpieconsulting.countdown.util.HttpFetcher;

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
	
}
