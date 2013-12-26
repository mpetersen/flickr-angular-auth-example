flickr-angular-auth-example
===========================

Example project to show authentication through Flickr in an AngularJS application.

Installation
============

Just download the project from Github. You need [Maven](http://maven.apache.org) installed to run the project. 
You also need a valid Flickr account and an [API Key](http://www.flickr.com/services/api/keys/).

Then rename the file `/src/main/resources/flickr.properties.tmp` to `/src/main/resources/flickr.properties` and fill 
in your API Key and Secret.

Running
=======

This project uses the Maven Jetty plugin. So no additional application server is required. Just run:

     #> mvn clean jetty:run

