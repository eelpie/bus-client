package uk.co.eelpieconsulting.buses.client.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.buses.client.model.RoutesNear;
import uk.co.eelpieconsulting.busroutes.model.Route;

public class RouteParser {

	private static final String TOWARDS = "towards";
	private static final String RUN = "run";
	private static final String ROUTE = "route";
	
	public List<Route> parse(String json) throws ParsingException {
		try {
			JSONArray jsonRoutes = new JSONArray(json);
			return parseRoutes(jsonRoutes);
			
		} catch (JSONException e) {
			throw new ParsingException();
		}
	}
	
	public RoutesNear parseRoutesNear(String json) throws ParsingException {
		try {
			JSONObject stopsNearJson = new JSONObject(json);
			return new RoutesNear(parseRoutes(stopsNearJson.getJSONArray("routes")), stopsNearJson.getString("location"));
			
		} catch (JSONException e) {
			e.printStackTrace();
			throw new ParsingException();
		}
	}
	
	Route parseRoute(JSONObject jsonRoute) throws JSONException {	// TODO duplicated with StopParser
		return new Route(jsonRoute.getString(ROUTE), jsonRoute.getInt(RUN), jsonRoute.getString(TOWARDS));
	}
	
	private List<Route> parseRoutes(JSONArray jsonRoutes) throws JSONException {
		final List<Route> routes = new ArrayList<Route>();
		for (int i = 0; i < jsonRoutes.length(); i++) {
			final JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
			routes.add(parseRoute(jsonRoute));
		}
		return routes;
	}
	
}
