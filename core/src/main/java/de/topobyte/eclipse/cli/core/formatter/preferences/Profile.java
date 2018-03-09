package de.topobyte.eclipse.cli.core.formatter.preferences;

import java.util.HashMap;
import java.util.Map;

public class Profile
{

	private String name;
	private String version;
	private Map<String, String> settings = new HashMap<>();

	public Profile(String name, String version)
	{
		this.name = name;
		this.version = version;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public Map<String, String> getSettings()
	{
		return settings;
	}

	public void setSettings(Map<String, String> settings)
	{
		this.settings = settings;
	}

}
