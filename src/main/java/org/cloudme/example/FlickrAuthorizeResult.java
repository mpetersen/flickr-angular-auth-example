package org.cloudme.example;

public class FlickrAuthorizeResult {
    private final String authorizationUrl;
    private final String secret;
    
    public FlickrAuthorizeResult(String secret, String authorizationUrl) {
        this.secret = secret;
        this.authorizationUrl = authorizationUrl;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public String getSecret() {
        return secret;
    }
}
