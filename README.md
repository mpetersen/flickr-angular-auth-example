flickr-angular-auth-example
===========================

Example project to show authentication through Flickr in an AngularJS application.

### Installation

Just download the project from Github. You need [Maven](http://maven.apache.org) installed to run the project. 
You also need a valid Flickr account and an [API Key](http://www.flickr.com/services/api/keys/).

Then rename the file `/src/main/resources/flickr.properties.tmp` to `/src/main/resources/flickr.properties` and fill 
in your API Key and Secret.

### Running

This project uses the Maven Jetty plugin. So no additional application server is required. Just run:

     #> mvn clean jetty:run
     
Then open your favourite browser and type: [`http://localhost:8080`](http://localhost:8080).

### Additional information

This project demonstrates how you can implement an authentication mechansim with the Flickr API and AngularJS.
It also shows:

- How to use the Servlet 3.0 API
- How to use Jackson for JSON parsing
- How to use Scribe for OAuth authentication

### Implementation details

#### Backend: Java

On the server side, the example is written in Java. It uses the Jetty server and the Servlet 3.0 API. It also uses Jackson 
for JSON parsing and Scribe for OAuth authentication. The main classes are:

- *Servlet: Three servlet classes for authentication, validation (of OAuth) and providing photo data.
- Flickr*: The classes used to interact with the Flickr web services.

#### Frontend: JavaScript

The client is a simple JavaScript client using AngularJS. The libraries are provided through Maven webjars. 

- index.html: The main page.
- js/app.js: The actual "business logic", displaying photos.
- js/flickrAuth.js: The implementation of authentication handling. This is actually reusable and could be even used in other
applications.
- partials/photos.html: The template showing the photos from Flickr. 