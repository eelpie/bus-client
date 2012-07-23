package uk.co.eelpieconsulting.buses.client.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.buses.client.model.Arrival;
import uk.co.eelpieconsulting.buses.client.model.StopBoard;
import uk.co.eelpieconsulting.busroutes.model.Route;

public class StopBoardParser {

	private static final String ESTIMATED_WAIT = "estimatedWait";
	private static final String LAST_UPDATED = "lastUpdated";
	private static final String TOWARDS = "towards";
	private static final String RUN = "run";
	private static final String ROUTE = "route";
	private static final String ARRIVALS = "arrivals";

	public StopBoard parse(final String json) throws ParsingException {
		try {			
			
			JSONObject jsonObject = new JSONObject(json);

			final List<Arrival> arrivals = new ArrayList<Arrival>();
			JSONArray arrivalsJson = jsonObject.getJSONArray(ARRIVALS);
			for (int i = 0; i < arrivalsJson.length(); i++) {
				JSONObject arrivalJson = arrivalsJson.getJSONObject(i);
				JSONObject routeJson = arrivalJson.getJSONObject(ROUTE);
				
				final Route route = new Route(routeJson.getString(ROUTE), routeJson.getInt(RUN), routeJson.getString(TOWARDS));				
				final long estimatedWait = arrivalJson.getLong(ESTIMATED_WAIT);
				arrivals.add(new Arrival(route, estimatedWait));
			}
			
			Collections.sort(arrivals);			
			return new StopBoard(jsonObject.getLong(LAST_UPDATED), arrivals);
			
		} catch (JSONException e) {	
			throw new ParsingException();
		}
	}

}
