package uk.co.eelpieconsulting.buses.client.parsers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.buses.client.model.FileInformation;

public class SourceFileInformationParser {

	public List<FileInformation> parse(String json) throws ParsingException {
		final List<FileInformation> sources = new ArrayList<FileInformation>();
		try {
			final JSONArray sourcesJson = new JSONArray(json);
			for (int i = 0; i < sourcesJson.length(); i++) {
				final JSONObject sourceJson = sourcesJson.getJSONObject(i);
				sources.add(new FileInformation(sourceJson.getString("name"),
						new Date(Long.parseLong(sourceJson.getString("date"))), 
						sourceJson.getString("md5")));
			}
			return sources;

		} catch (JSONException e) {
			e.printStackTrace();
			throw new ParsingException();
		}
	}

}
