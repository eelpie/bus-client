package uk.co.eelpieconsulting.buses.client.parsers;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class ContentLoader {

	public static String loadContent(String filename) throws IOException {
		return IOUtils.toString(ClassLoader.getSystemResource(filename));
	}

}
