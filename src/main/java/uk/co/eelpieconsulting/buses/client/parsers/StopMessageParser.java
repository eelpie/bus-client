package uk.co.eelpieconsulting.buses.client.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.eelpieconsulting.buses.client.exceptions.ParsingException;
import uk.co.eelpieconsulting.busroutes.model.Message;
import uk.co.eelpieconsulting.busroutes.model.MultiStopMessage;
import uk.co.eelpieconsulting.busroutes.model.Stop;

public class StopMessageParser {

	private final StopParser stopParser;
	
	public StopMessageParser() {
		this.stopParser = new StopParser();
	}
	
	private static final String ID = "id";
	private static final String STOP_ID = "stopId";
	private static final String MESSAGE = "message";
	private static final String PRIORITY = "priority";
	private static final String END_DATE = "endDate";
	private static final String START_DATE = "startDate";
	private static final String STOPS = "stops";

	public List<MultiStopMessage> parse(final String json) throws ParsingException {
		try {
			JSONArray messagesJson = new JSONArray(json);
			final List<MultiStopMessage> messages = new ArrayList<MultiStopMessage>();
			for (int i = 0; i < messagesJson.length(); i++) {
				JSONObject messageJson = messagesJson.getJSONObject(i);
				final Message message = new Message(messageJson.getString(ID),
						messageJson.getInt(STOP_ID), messageJson
								.getString(MESSAGE), messageJson
								.getInt(PRIORITY), messageJson
								.getLong(START_DATE), messageJson
								.getLong(END_DATE));
								
				final MultiStopMessage multiStopMessage = new MultiStopMessage(message.getId(), message);	 // TODO constructor not the most useful
				if (messageJson.has(STOPS)) {
					final List<Stop> parsedStopList = stopParser.parseStopList(messageJson.getJSONArray(STOPS));
					for (Stop stop : parsedStopList) {
						multiStopMessage.addStop(stop);						
					}
				}
				messages.add(multiStopMessage);
			}
			return messages;
			
		} catch (JSONException e) {
			throw new ParsingException();
		}
	}

}
