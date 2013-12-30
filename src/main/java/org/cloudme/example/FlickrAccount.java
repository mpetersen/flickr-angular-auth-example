package org.cloudme.example;

import java.security.Principal;

import org.scribe.model.Token;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize( using = FlickrAccountDeserializer.class )
public class FlickrAccount implements Principal {
    private String id;
    private String username;
    private String oauthSecret;
    private String oauthToken;
    
    public void setAccessToken(Token accessToken) {
        oauthSecret = accessToken.getSecret();
        oauthToken = accessToken.getToken();
    }
    
    public Token getAccessToken() {
        return new Token(oauthToken, oauthSecret);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
    public String getName() {
        return username;
    }
}
