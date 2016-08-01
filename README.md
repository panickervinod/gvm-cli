# SDKMAN! CLI
### The Software Development Kit Manager Command Line Interface

[![Build Status](https://travis-ci.org/sdkman/sdkman-cli.svg?branch=master)](https://travis-ci.org/sdkman/sdkman-cli)
[![Gitter](https://badges.gitter.im/sdkman/user-issues.png)](https://gitter.im/sdkman/user-issues)

SDKMAN is a tool for managing parallel Versions of multiple Software Development Kits on any Unix based system. It provides a convenient command line interface for installing, switching, removing and listing Candidates.

See documentation on the [SDKMAN! website](http://sdkman.io).

Please report any bugs and feature request on the [GitHub Issue Tracker](https://github.com/sdkman/sdkman-cli/issues).

## Installation

Open your favourite terminal and enter the following:

    $ curl -s api.sdkman.io | bash

If the environment needs tweaking for SDKMAN to be installed, the installer will prompt you accordingly and ask you to restart.

## Running the Cucumber Features

All SDKMAN's BDD tests are written in Cucumber and can be found under `src/test/cucumber/sdkman`.
These can be run with Gradle by running the following command:

    $ ./gradlew test


__Please ensure that the JAVA_HOME environment variable is set to JDK 1.8 on your system!__

Mac users can add the following line to their `~/.bash_profile` file to set this variable:

	export JAVA_HOME=$(/usr/libexec/java_home -v1.8)

### Using Docker for tests

You can run the tests in a Docker container to guarantee a clean test environment.

    $ docker build --tag=sdkman-cli/gradle .
    $ docker run --rm -it sdkman-cli/gradle test

By running the following command, you don't need to wait for downloading Gradle wrapper and other dependencies. The test reports can be found under the local `build` directory.

    $ docker run --rm -it -v $PWD:/usr/src/app -v $HOME/.gradle:/root/.gradle sdkman-cli/gradle test

### Local Installation

To install SDKMAN locally running against your local server, run the following commands:

	$ ./gradlew install
	$ source ~/.sdkman/bin/sdkman-init.sh
