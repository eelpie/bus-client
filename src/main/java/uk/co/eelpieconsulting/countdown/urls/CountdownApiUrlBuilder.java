package uk.co.eelpieconsulting.countdown.urls;

public class CountdownApiUrlBuilder {
	
	private String apiUrl;

	public CountdownApiUrlBuilder(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	
	public String getStopBoardUrl(int stopId) {
		return apiUrl + "/stop/" + stopId + "/arrivals";
	}
	
	public String getMarkerSearchUrl(double latitude, double longitude, int radius) {
		return apiUrl + "/stops/near?latitude=" + latitude + "&longitude=" + longitude;
	}
	
	public String getStopIdSearchUrl(int id) {
		return apiUrl + "/stop/" + id;				
	}

	public String getRoutesStopUrl(String route, int run) {
		return apiUrl + "/route/" + route + "/" + run + "/stops";
	}

}
