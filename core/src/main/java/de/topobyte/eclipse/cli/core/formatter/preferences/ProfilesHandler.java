package de.topobyte.eclipse.cli.core.formatter.preferences;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ProfilesHandler extends DefaultHandler
{

	private static final Logger logger = LoggerFactory
			.getLogger(ProfilesHandler.class);

	private Profiles profiles = new Profiles();

	private boolean inProfile;
	private Profile profile = null;

	public Profiles getProfiles()
	{
		return profiles;
	}

	public static Profiles parseConfiguration(Path path)
	{
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			ProfilesHandler handler = new ProfilesHandler();
			parser.parse(path.toFile(), handler);
			return handler.profiles;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error("error while parsing configuration", e);
		}
		return null;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		if (!inProfile && "profile".equals(qName)) {
			inProfile = true;
			String name = attributes.getValue("name");
			String version = attributes.getValue("version");
			profile = new Profile(name, version);
			profiles.getProfiles().add(profile);
		} else if (inProfile && "setting".equals(qName)) {
			String id = attributes.getValue("id");
			String value = attributes.getValue("value");
			profile.getSettings().put(id, value);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		if ("profile".equals(qName)) {
			inProfile = false;
		}
	}

}
