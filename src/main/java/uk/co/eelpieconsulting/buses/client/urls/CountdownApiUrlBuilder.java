package uk.co.eelpieconsulting.buses.client.urls;

public class CountdownApiUrlBuilder {
	
	private String apiUrl;

	public CountdownApiUrlBuilder(String apiUrl) {
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
		return apiUrl + "/messages?stops[]=" + commaSeperate(stopIds);
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
