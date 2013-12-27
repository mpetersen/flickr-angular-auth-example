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