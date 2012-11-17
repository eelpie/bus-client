package uk.co.eelpieconsulting.buses.client.urls;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlBuilder {
	
	private String apiUrl;

	public UrlBuilder(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	
	public String getStopUrl(int stopId) {
		return apiUrl + "/stop/" + stopId;
	}
	
	public String getStopBoardUrl(int stopId) {
		return getStopUrl(stopId) + "/arrivals";
	}
	
	public String getMarkerSearchUrl(double latitude, double longitude, int radius) {
		return apiUrl + "/stops/near?latitude=" + latitude + "&longitude=" + longitude;
	}
	
	public String getStopsNearLocationUrl(double latitude, double longitude, int radius) {
		return getMarkerSearchUrl(latitude, longitude, radius) + "&resolve=true";
	}
	
	public String getNearbyRoutesUrl(double latitude, double longitude, int radius) {
		return apiUrl + "/routes/near?latitude=" + latitude + "&longitude=" + longitude;
	}
	
	public String getStopIdSearchUrl(int id) {
		return apiUrl + "/stop/" + id;				
	}

	public String getRoutesStopUrl(String route, int run) {
		return apiUrl + "/route/" + route + "/" + run + "/stops";
	}

	public String getStopMessagesUrl(int stopId) {
		return getStopUrl(stopId) + "/messages";
	}

	public String getStopMessagesUrl(int[] stopIds) {
		return apiUrl + "/messages?stops=" + commaSeperate(stopIds);
	}
	
	public String getStopSearchUrl(String q) {
		return apiUrl + "/stops/search?q=" + urlEncode(q);
	}
	
	public String getSourceFileInformationUrl() {
		return apiUrl + "/sources";
	}
	
	private String urlEncode(String q) {
		try {
			return URLEncoder.encode(q, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String commaSeperate(int[] stopIds) {
		StringBuilder output = new StringBuilder();
		for (int stopId : stopIds) {
			if (output.length() > 0) {
				output.append(",");
			}
			output.append(stopId);
		}
		return output.toString();
	}
	
}
