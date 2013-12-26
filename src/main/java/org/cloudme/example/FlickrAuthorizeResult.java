package org.cloudme.example;

public class FlickrAuthorizeResult {
    private String authorizationUrl;
    private String secret;
    
    public FlickrAuthorizeResult() {
    }

    public FlickrAuthorizeResult(String secret, String authorizationUrl) {
        this.secret = secret;
        this.authorizationUrl = authorizationUrl;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }
    
    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String getSecret() {
        return secret;
    }
    
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
