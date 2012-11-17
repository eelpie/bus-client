package uk.co.eelpieconsulting.buses.client.model;

import java.io.Serializable;
import java.util.List;

import uk.co.eelpieconsulting.busroutes.model.Stop;

public class StopsNear implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final List<Stop> stops;
	private final String location;
	
	public StopsNear(List<Stop> stops, String location) {
		this.stops = stops;
		this.location = location;
	}

	public List<Stop> getStops() {
		return stops;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "StopsNear [location=" + location + ", stops=" + stops + "]";
	}
	
}
