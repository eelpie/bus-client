package uk.co.eelpieconsulting.buses.client.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.buses.client.model.StopsNear;
import uk.co.eelpieconsulting.busroutes.model.Stop;

public class StopParser {

	private static final String STOP_ID = "id";
	private static final String STOP_NAME = "name";
	private static final String TOWARDS = "towards";
	private static final String STOP_INDICATOR = "indicator";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";

	private final RouteParser routeParser;
	
	public StopParser() {
		this.routeParser = new RouteParser();
	}
	
	public List<Stop> parse(String json) throws ParsingException {
		try {
			final JSONArray stopsJson = new JSONArray(json);
			return parseStopList(stopsJson);
			
		} catch (JSONException e) {			
			throw new ParsingException();
		}
	}
	
	public StopsNear parseStopsNear(String json) throws ParsingException {
		System.out.println(json);
		try {
			JSONObject stopsNearJson = new JSONObject(json);
			final JSONArray stopsJson = stopsNearJson.getJSONArray("stops");			
			return new StopsNear(parseStopList(stopsJson), stopsNearJson.getString("location"));
			
		} catch (JSONException e) {
			e.printStackTrace();
			throw new ParsingException();
		}
	}

	
	public Stop parseStop(String json) throws ParsingException {
		try {
			return parseSingleStop(new JSONObject(json));
		} catch (JSONException e) {
			throw new ParsingException();
		}
	}
	
	List<Stop> parseStopList(final JSONArray stopsJson) throws JSONException {
		final List<Stop> stops = new ArrayList<Stop>();
		for (int i = 0; i < stopsJson.length(); i++) {
			stops.add(parseSingleStop(stopsJson.getJSONObject(i)));
		}			
		return stops;
	}
	
	private Stop parseSingleStop(final JSONObject stopJson) throws JSONException {		
		Stop stop = new Stop(stopJson.getInt(STOP_ID),
					stopJson.getString(STOP_NAME), 
					stopJson.has(TOWARDS) ? stopJson.getString(TOWARDS) : null,
					stopJson.isNull(STOP_INDICATOR) ? null : stopJson.getString(STOP_INDICATOR), 
					stopJson.getDouble(LATITUDE), stopJson.getDouble(LONGITUDE), 
					null, null, null);
		
		if (stopJson.has("routes")) {
			JSONArray jsonRoutes = stopJson.getJSONArray("routes");
			for (int i = 0; i < jsonRoutes.length(); i++) {
				final JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
				stop.addRoute(routeParser.parseRoute(jsonRoute));
			}
		}
		
		return stop;
	}
	
}
