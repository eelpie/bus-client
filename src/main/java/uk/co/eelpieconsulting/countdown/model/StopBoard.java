package uk.co.eelpieconsulting.countdown.model;

import java.util.List;


public class StopBoard {

	private final long lastUpdated;
	private final List<Arrival> arrivals;
	
	public StopBoard(long lastUpdated, List<Arrival> arrivals) {
		this.lastUpdated = lastUpdated;
		this.arrivals = arrivals;
	}
	
	public long getLastUpdated() {
		return lastUpdated;
	}
	
	public List<Arrival> getArrivals() {
		return arrivals;
	}

	@Override
	public String toString() {
		return "StopBoard [arrivals=" + arrivals + "]";
	}
	
}
