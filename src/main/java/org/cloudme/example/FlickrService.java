package org.cloudme.example;

import static com.google.common.collect.ImmutableMap.of;
import static org.cloudme.example.FlickrProperties.API_KEY;
import static org.cloudme.example.FlickrProperties.API_SECRET;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FlickrApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FlickrService {
    public FlickrAuthorizeResult authorize(String callback) {
        OAuthService service = service(callback);
        Token requestToken = service.getRequestToken();
        String authorizationUrl = service.getAuthorizationUrl(requestToken);
        return new FlickrAuthorizeResult(requestToken.getSecret(), authorizationUrl);
    }
    
    public FlickrAccount verify(Token requestToken, Verifier verifier, HttpServletRequest req) {
        OAuthService service = service();
        Token accessToken = service.getAccessToken(requestToken, verifier);
        InputStream in = testLogin(accessToken);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FlickrAccount account = objectMapper.readValue(in, FlickrAccount.class);
            account.setAccessToken(accessToken);
            return account;
        }
        catch (IOException e) {
            return null;
        }
    }
    
    public InputStream testLogin(Token accessToken) {
        return call("flickr.test.login", accessToken);
    }

    public InputStream photosSearch(Token accessToken, String id) {
        return call("flickr.photos.search", accessToken, of("user_id", id));
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
    
    private InputStream call(String method, Token accessToken) {
        return call(method, accessToken, null);
    }

    private InputStream call(String method, Token accessToken, Map<String, String> params) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://ycpi.api.flickr.com/services/rest");
        request.addQuerystringParameter("format", "json");
        request.addQuerystringParameter("nojsoncallback", "1");
        request.addQuerystringParameter("method", method);
        if (params != null) {
            for (Entry<String, String> param : params.entrySet()) {
                request.addQuerystringParameter(param.getKey(), param.getValue());
            }
        }
        service().signRequest(accessToken, request);
        return request.send().getStream();
    }
}
