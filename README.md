# Eclipse CLI

**Table of Contents**
* [License](#license)
* [Requirements](#requirements)
* [Command line interface (CLI)](#command-line-interface-cli)
    * [Building the CLI module](#building-the-cli-module)
    * [CLI usage and tasks](#cli-usage-and-tasks)
* [Development](#development)

# License

This project is released under the terms of the GNU Lesser General Public
License.

See  [LGPL.md](LGPL.md) and [GPL.md](GPL.md) for details.

## Requirements

In order to run the software from the development tree you need a Java
Development Kit (JDK), Version 8 or later. The project uses Gradle as a
build tool, but you should use the included Gradle Wrapper for building
the project.

On Debian-based systems such as Ubuntu or Mint, you can install the JDK
like this:

    sudo apt-get install openjdk-8-jdk

## Command line interface (CLI)

### Building the CLI module

Run the Gradle `createRuntime` task to build the CLI:

    ./gradlew clean createRuntime

### CLI usage and tasks

This project has a main executable that can be executed like this:

    ./scripts/eclipse-cli <task>

Alternatively, add the `scripts` directory to your `PATH` environment
variable in order to run `eclipse-cli` without specifying its location
each time. The following examples assume you have done that:

    export PATH=$PATH:$(readlink -f scripts)

Then invoke the main executable like this:

    eclipse-cli <task>

Where `<task>` can be any of the following:

    format
    create-workspace

Each task accepts its own set of command line parameters. To run the formatter
you would type:

    eclipse-cli --config /path/to/profiles.xml <dir>

## Development

To work on the project using Eclipse as an IDE which appears like an obvious
choice for this Eclipse-related project, use Gradle to set up the Eclipse
project files:

    ./gradlew cleanEclipse eclipse
