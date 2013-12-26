package org.cloudme.example;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class FlickrProperties {
    private static final ResourceBundle bundle;
    
    static {
        try {
            bundle = ResourceBundle.getBundle("flickr");
        }
        catch (MissingResourceException e) {
            throw new IllegalStateException("You need to create your own 'flickr.properties' file with API Key and Secret in: /src/main/resources.\n"
                    + "Just rename the existing 'flickr.properties.tmp' file and fill in your values.");
        }
    }
    
    public static final String API_KEY = bundle.getString("api.key");
    public static final String API_SECRET = bundle.getString("api.secret");
    
    private FlickrProperties() {
    }
}
