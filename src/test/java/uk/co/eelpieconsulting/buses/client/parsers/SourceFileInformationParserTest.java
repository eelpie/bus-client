package uk.co.eelpieconsulting.buses.client.parsers;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.eelpieconsulting.buses.client.model.FileInformation;

public class SourceFileInformationParserTest {

	private SourceFileInformationParser sourceFileInformationParser;

	@Before
	public void setup() {
		sourceFileInformationParser = new SourceFileInformationParser();
	}
	
	@Test
	public void routeStopsShouldHaveTheCorrectRoutesAttached() throws Exception {
		final List<FileInformation> sources = sourceFileInformationParser.parse(ContentLoader.loadContent("sources.json"));
		
		assertEquals(1, sources.size());
		
		FileInformation fileInformation = sources.get(0);
		assertEquals("routes_20120831.csv", fileInformation.getName());
		assertEquals("31 Aug 2012 22:05:05 GMT", fileInformation.getDate().toGMTString());
		assertEquals("7bdfcb1f24819eca5e8afd5a19d7d5ac", fileInformation.getMd5());
	}
	
}
