package de.topobyte.eclipse.cli.core.formatter.preferences;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ProfilesReader
{

	public static Profiles parseProfiles(Path path)
	{
		try {
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			ProfilesHandler preferences = new ProfilesHandler();
			saxParser.parse(path.toFile(), preferences);
			return preferences.getProfiles();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
