package uk.co.eelpieconsulting.buses.client.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.eelpieconsulting.busroutes.model.Route;

public class RouteParser {

	public List<Route> parse(String json) throws JSONException {
		List<Route> routes = new ArrayList<Route>();
		final JSONArray jsonRoutes = new JSONArray(json);
		for (int i = 0; i < jsonRoutes.length(); i++) {
			final JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
			routes.add(parseRoute(jsonRoute));
		}
		return routes;
	}
	
	private Route parseRoute(JSONObject jsonRoute) throws JSONException {		// TODO duplicated with StopParser
		return new Route(jsonRoute.getString("route"), jsonRoute.getInt("run"), jsonRoute.getString("towards"));
	}
	
}
