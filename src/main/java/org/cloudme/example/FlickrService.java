package org.cloudme.example;

import static org.cloudme.example.FlickrProperties.API_KEY;
import static org.cloudme.example.FlickrProperties.API_SECRET;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FlickrApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FlickrService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FlickrAuthorizeResult authorize(String callback) {
        OAuthService service = service(callback);
        Token requestToken = service.getRequestToken();
        String authorizationUrl = service.getAuthorizationUrl(requestToken);
        return new FlickrAuthorizeResult(requestToken.getSecret(), authorizationUrl);
    }
    
    public FlickrAccount verify(Token requestToken, Verifier verifier, HttpServletRequest req) {
        Token accessToken = service().getAccessToken(requestToken, verifier);
        FlickrAccount account = testLogin(accessToken);
        account.setAccessToken(accessToken);
        // Caution! Very unsafe, just for demonstration purposes:
        account.setClientToken(String.valueOf(System.currentTimeMillis()));
        return account;
    }
    
    public FlickrAccount testLogin(Token accessToken) {
        try {
            Response response = signAndSend(accessToken, createRequest("flickr.test.login"));
            return objectMapper.readValue(response.getStream(), FlickrAccount.class);
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public InputStream photosSearch(FlickrAccount account) {
        OAuthRequest request = createRequest("flickr.photos.search");
        request.addQuerystringParameter("user_id", account.getId());
        Response response = signAndSend(account.getAccessToken(), request);
        return response.getStream();
    }

    private OAuthService service() {
        return service(null);
    }
    
    private OAuthService service(String callback) {
        ServiceBuilder serviceBuilder = new ServiceBuilder()
                .provider(FlickrApi.class)
                .apiKey(API_KEY)
                .apiSecret(API_SECRET);
        if (callback != null) {
            serviceBuilder.callback(callback);
        }
        return serviceBuilder.build();
    }

    private OAuthRequest createRequest(String method) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://ycpi.api.flickr.com/services/rest");
        request.addQuerystringParameter("format", "json");
        request.addQuerystringParameter("nojsoncallback", "1");
        request.addQuerystringParameter("method", method);
        return request;
    }
    
    private Response signAndSend(Token accessToken, OAuthRequest request) {
        service().signRequest(accessToken, request);
        return request.send();
    }
}
