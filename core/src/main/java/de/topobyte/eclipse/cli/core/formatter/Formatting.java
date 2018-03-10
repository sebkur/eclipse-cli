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

package de.topobyte.eclipse.cli.core.formatter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.melon.paths.PathUtil;

public class Formatting
{

	final static Logger logger = LoggerFactory.getLogger(Formatting.class);

	public static String format(CodeFormatter formatter, String source)
	{
		TextEdit edit = formatter.format(CodeFormatter.K_COMPILATION_UNIT,
				source, 0, source.length(), 0,
				System.getProperty("line.separator"));

		IDocument document = new Document(source);
		try {
			edit.apply(document);
		} catch (MalformedTreeException | BadLocationException e) {
			logger.warn("Error while formatting", e);
		}

		return document.get();
	}

	public static void format(CodeFormatter formatter, Path path)
			throws IOException
	{
		logger.info("formatting: " + path);

		String source = null;
		try {
			source = IOUtils.toString(Files.newInputStream(path));
		} catch (IOException e) {
			logger.error("Error while reading file", e);
			return;
		}

		String formatted = format(formatter, source);
		OutputStream output = Files.newOutputStream(path);
		IOUtils.write(formatted, output);
		output.close();
	}

	public static void formatRecursive(CodeFormatter formatter, Path base)
			throws IOException
	{
		Collection<Path> javaFiles = PathUtil.findRecursive(base, "*.java");

		for (Path path : javaFiles) {
			format(formatter, path);
		}
	}

}
