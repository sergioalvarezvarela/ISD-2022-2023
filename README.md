# ws-app

## Installing the development environment

- [Instructions for Unix-like operating systems (spanish)] (https://github.com/udc-fic-isd/isd-entorno/blob/main/LEEME_UNIX.md).
- [Instructions for Windows (spanish)] (https://github.com/udc-fic-isd/isd-entorno/blob/main/LEEME_WINDOWS.md).

## Initializing the database and building the project

	mvn sql:execute install

## Running the project

It requires the database server to be running.

### Running the service with Maven/Jetty

	cd ws-app-service
	mvn jetty:run

### Running the service with Tomcat

- Copy the `.war` file (`ws-app-service/target/ws-app-service.war`)
  to Tomcat's `webapps` directory.

- Start Tomcat:

      cd <TOMCAT_HOME>/bin
      startup.sh

- Shutdown Tomcat:

      shutdown.sh

### Running the client application

Configure `ws-app-client/src/main/resources/ConfigurationParameters.properties`
to specify the client implementation (REST or Thrift) to be used and
the port number of the web server in the `endpointAddress` property
(9090 for Jetty, 8080 for Tomcat)

	cd ws-app-client

- Execute the client 

      mvn exec:java -Dexec.mainClass="..." -Dexec.args="..."

