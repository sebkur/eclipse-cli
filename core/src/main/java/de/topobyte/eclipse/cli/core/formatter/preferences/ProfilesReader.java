// Copyright 2018 Sebastian Kuerten
//
// This file is part of eclipse-cli.
//
// eclipse-cli is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// eclipse-cli is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with eclipse-cli. If not, see <http://www.gnu.org/licenses/>.

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
