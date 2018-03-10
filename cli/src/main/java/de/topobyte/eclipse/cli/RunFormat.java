package de.topobyte.eclipse.cli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;

import de.topobyte.eclipse.cli.core.formatter.Formatting;
import de.topobyte.eclipse.cli.core.formatter.preferences.Profile;
import de.topobyte.eclipse.cli.core.formatter.preferences.Profiles;
import de.topobyte.eclipse.cli.core.formatter.preferences.ProfilesReader;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;

public class RunFormat
{

	private static final String OPTION_CONFIG = "config";

	public static ExeOptionsFactory OPTIONS_FACTORY = new ExeOptionsFactory() {

		@Override
		public ExeOptions createOptions()
		{
			Options options = new Options();
			// @formatter:off
			OptionHelper.addL(options, OPTION_CONFIG, true, false, "file", "an Eclipse formatter config file");
			// @formatter:on
			return new CommonsCliExeOptions(options, "[options] <file>...");
		}

	};

	public static void main(String name, CommonsCliArguments arguments)
			throws Exception
	{
		CommandLine line = arguments.getLine();

		Path pathConfig = null;

		if (line.hasOption(OPTION_CONFIG)) {
			String argConfig = line.getOptionValue(OPTION_CONFIG);
			pathConfig = Paths.get(argConfig);
			System.out.println("Config: " + pathConfig);
		}

		List<String> args = line.getArgList();
		if (args.isEmpty()) {
			System.out.println("Please specify one or more files");
			arguments.getOptions().usage(name);
			System.exit(1);
		}

		List<Path> paths = new ArrayList<>();
		for (String arg : args) {
			paths.add(Paths.get(arg));
		}

		CodeFormatter formatter = null;
		if (pathConfig == null) {
			formatter = ToolFactory.createCodeFormatter(null);
		} else {
			Profiles configuration = ProfilesReader.parseProfiles(pathConfig);
			List<Profile> profiles = configuration.getProfiles();
			if (profiles.isEmpty()) {
				System.out.println(
						"Configuration file doesn't contain any profiles");
				System.exit(1);
			} else if (profiles.size() > 1) {
				System.out.println(String.format(
						"More than one profile found in configuration, using first ('%s')",
						profiles.get(0).getName()));
			}
			Profile profile = profiles.get(0);
			formatter = ToolFactory.createCodeFormatter(profile.getSettings());
		}

		for (Path path : paths) {
			if (Files.isRegularFile(path)) {
				System.out
						.println(String.format("formatting file: '%s'", path));
				Formatting.format(formatter, path);
			} else if (Files.isDirectory(path)) {
				System.out.println(
						String.format("formatting directory: '%s'", path));
				Formatting.formatRecursive(formatter, path);
			}
		}
	}

}
