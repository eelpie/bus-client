package uk.co.eelpieconsulting.buses.client.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.busroutes.model.Message;

public class StopMessageParser {

	private static final String ID = "id";
	private static final String STOP_ID = "stopId";
	private static final String MESSAGE = "message";
	private static final String PRIORITY = "priority";
	private static final String END_DATE = "endDate";
	private static final String START_DATE = "startDate";

	public List<Message> parse(final String json) throws ParsingException {
		try {
			JSONArray messagesJson = new JSONArray(json);
			final List<Message> messages = new ArrayList<Message>();
			for (int i = 0; i < messagesJson.length(); i++) {
				JSONObject messageJson = messagesJson.getJSONObject(i);
				final Message message = new Message(messageJson.getString(ID),
						messageJson.getInt(STOP_ID), messageJson
								.getString(MESSAGE), messageJson
								.getInt(PRIORITY), messageJson
								.getLong(START_DATE), messageJson
								.getLong(END_DATE));
				messages.add(message);
			}
			return messages;
			
		} catch (JSONException e) {
			System.out.println(e.getMessage());
			throw new ParsingException();
		}
	}

}
