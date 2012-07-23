package uk.co.eelpieconsulting.buses.client.model;

import uk.co.eelpieconsulting.busroutes.model.Route;

public class Arrival implements Comparable<Arrival> {
	
	private Route route;
	private long estimatedWait;
	
	public Arrival(Route route, long estimatedWait) {
		this.route = route;
		this.estimatedWait = estimatedWait;
	}
	
	public Route getRoute() {
		return route;
	}

	public long getEstimatedWait() {
		return estimatedWait;
	}
	
	@Override
	public int compareTo(Arrival o) {
		long diff = estimatedWait - o.getEstimatedWait();
		return diff < 0 ? -1 : 1;
	}
	
}
