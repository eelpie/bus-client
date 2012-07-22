package uk.co.eelpieconsulting.countdown.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.eelpieconsulting.busroutes.model.Route;
import uk.co.eelpieconsulting.busroutes.model.Stop;
import uk.co.eelpieconsulting.countdown.exceptions.ParsingException;

public class StopSearchParser {

	private static final String STOP_ID = "id";
	private static final String STOP_NAME = "name";
	private static final String TOWARDS = "towards";
	private static final String STOP_INDICATOR = "indicator";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
		
	public List<Stop> parse(String json) throws ParsingException {
		try {
			final JSONArray stopsJson = new JSONArray(json);			

			final List<Stop> stops = new ArrayList<Stop>();
			for (int i = 1; i < stopsJson.length(); i++) {
				stops.add(parseSingleStop(stopsJson.getJSONObject(i)));
			}			
			return stops;
			
		} catch (JSONException e) {			
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
	
	private Stop parseSingleStop(final JSONObject stopJson) throws JSONException {		
		Stop stop = new Stop(stopJson.getInt(STOP_ID),
					stopJson.getString(STOP_NAME), 
					stopJson.has(TOWARDS) ? stopJson.getString(TOWARDS) : null,
					stopJson.isNull(STOP_INDICATOR) ? null : stopJson.getString(STOP_INDICATOR), 
					stopJson.getDouble(LATITUDE), stopJson.getDouble(LONGITUDE), 
					null, null);
		
		if (stopJson.has("routes")) {
			JSONArray jsonRoutes = stopJson.getJSONArray("routes");
			for (int i = 1; i < jsonRoutes.length(); i++) {
				final JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
				stop.addRoute(new Route(jsonRoute.getString("route"), jsonRoute.getInt("run"), jsonRoute.getString("towards")));
			}	
		}
		
		return stop;
	}

}
