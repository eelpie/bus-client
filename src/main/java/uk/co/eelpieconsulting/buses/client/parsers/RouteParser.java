package uk.co.eelpieconsulting.buses.client.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.busroutes.model.Route;

public class RouteParser {

	private static final String TOWARDS = "towards";
	private static final String RUN = "run";
	private static final String ROUTE = "route";
	
	public List<Route> parse(String json) throws ParsingException {
		List<Route> routes = new ArrayList<Route>();
		try {
			JSONArray jsonRoutes = new JSONArray(json);
			for (int i = 0; i < jsonRoutes.length(); i++) {
				final JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
				routes.add(parseRoute(jsonRoute));
			}
		} catch (JSONException e) {
			throw new ParsingException();
		}
		return routes;
	}
	
	Route parseRoute(JSONObject jsonRoute) throws JSONException {	// TODO duplicated with StopParser
		return new Route(jsonRoute.getString(ROUTE), jsonRoute.getInt(RUN), jsonRoute.getString(TOWARDS));
	}
	
}
