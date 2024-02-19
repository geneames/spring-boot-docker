## spring-boot-docker

Project to demonstrate knowledge of developing and testing a ReST API in Java.

### Specifications

- A microservice that stores and shuffles card decks.
- A card may be represented as a simple string such as “5-heart”, or “K-spade”.
- A deck is an ordered list of 52 standard playing cards.
- Expose a RESTful interface that allows a user to:
    - PUT an idempotent request for the creation of a new named deck. New decks are created in some initial sorted
      order.
    - POST a request to shuffle an existing named deck.
    - GET a list of the current decks persisted in the service.
    - GET a named deck in its current sorted/shuffled order.
    - DELETE a named deck.
- Two shuffle algorithms, one random shuffle, the other that simulates "hand shuffle" by splitting the deck and
  interleaving cards multiple times.
- Deck state is persisted in memory by default. This can be changed to persisted in most any RDBMS via JDBC
  configuration.
- Persistence method and shuffle algorithm are configurable at deploy time via application properties.

###Implementation
This application was built with Spring framework as the foundation. The build is managed by Gradle. The distributable
artifact is a Spring Boot application that can run stand-alone or, with some modifications to the Gradle build file, can
be deployed to most any J2EE compliant application server or cloud container.

### REST Endpoints

- Create Card Deck
    - **http://localhost/api/decks/**
        - Idempotent behavior, meaning a new deck will be created if a deck with the provided deck name doesn't already
          exist in the database. However, if a deck in the database has the deck name requested by this operation, the
          deck in the database will be returned in its current state.
        - HTTP Method: PUT / Status Code: 201 for created deck
        - Input: application/x-www-form-urlencoded (deckName=Name+of+Deck)
        - Returns: JSON {deckName: "Name of Deck" {cards: ["A-Spades","2-Spades","3-Spades",...]}} for new deck.
        - HTTP Method: PUT / Status Code: 200 for existing deck
        - Returns: JSON {deckName: "Name of Deck" {cards: ["J-Diamonds","3-Clubs","8-Hearts",...]}} (i.e. current deck
          state.)
- Shuffle Deck
    - **http://localhost/api/decks/shuffle**
        - HTTP Method: POST / Status Code: 200
        - Input: application/x-www-form-urlencoded (deckName=Name+of+Deck)
        - Returns: JSON {deckName: "Name of Deck" {cards: ["J-Diamonds","3-Clubs","8-Hearts",...]}} (i.e. current deck
          state.)
        - If deck not found in the database an empty JSON body is returned
- Retrieve List of Decks
    - **http://localhost/api/decks/**
        - HTTP Method: GET / Status Code: 200
        - Input: N/A
        - Returns: JSON [{"deckName":"another-test-deck"},{"deckName":"test-deck"}]
- Retrieve a Single Deck
    - **http://localhost/api/decks/Name+of+Deck**
        - HTTP Method: GET / Status Code: 200
        - Input: N/A
        - Returns: Current deck state as JSON {deckName: "Name of Deck" {cards: ["A-Spades","2-Spades","3-Spades",...]}}
        - If deck not found in the database an empty JSON body is returned
- Remove a Single Deck
    - **http://localhost/api/decks/Name+of+Deck**
        - HTTP Method: DELETE / Status Code: 204
        - Input: N/A

### Configuration

The configuration files are contained in the */resources* folders under the *main/* and *test/* folders. The
*applications.properties* file is used solely to configure logging. All other configurations are in the
*application.yml* file. XML configuration files have been avoided at all costs, however this application uses LogBack
which is configured with an XML file. There are two Spring Configuration
classes in the code base. These are used to initialize the proper Shuffler bean at boot-time.

Obviously, since these configuration files are in the */resources* folder, they are rolled into the WAR file at build
time. The implication is if any configuration changes are desired, such as selecting a different shuffler or data
source, this requires a new build. If it is desired, the configuration can be externalized. By placing the configuration
files (i.e. *application.properties* and *application.yml*) in a folder named *config/* located in the ROOT context of
the application, the Spring framework will recognize this as the place to retrieve configurations at boot-time.

### Running the Service

This service is a Spring Boot application, which has an embedded application server. Also, this application can be
deployed to an existing application server with some modifications to the Gradle build file (
See [Deploying Spring Boot Applications](https://spring.io/blog/2014/03/07/deploying-spring-boot-applications)).

**Run Stand-Alone**

1. Install or verify Java 1.8 or greater JDK is installed on the host computer.
2. Create or verify a *JAVA_HOME* environment variable is configured.
3. Create or verify *JAVA_HOME/bin* is in the system or shell *PATH*.
4. Install or verify Gradle is installed on the host computer.
5. Create or verify a *GRADLE_HOME* environment variable is configured.
6. Clone this GitHub repository.
7. In a shell or command prompt window, go to the */your/path/to/rest-example*
8. At the command prompt, type *gradle bootRun*. The service should start.

### Improvements or Enhancements

This example was put together rather hastily, so there are some things missing one would like to see in a production
ready application. For example:

- Logging is sparse. Additional logging statements need to added at all levels of logging.
- More robust verification of the deck state in the controller unit/integration tests.