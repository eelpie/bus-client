package uk.co.eelpieconsulting.buses.client.model;

import java.io.Serializable;
import java.util.List;

import uk.co.eelpieconsulting.busroutes.model.Route;

public class RoutesNear implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final List<Route> routes;
	private final String location;
	
	public RoutesNear(List<Route> routes, String location) {
		this.routes = routes;
		this.location = location;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "RoutesNear [location=" + location + ", routes=" + routes + "]";
	}
	
}
