package org.cloudme.example;

import static org.scribe.model.OAuthConstants.TOKEN_SECRET;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( "/authorize" )
public class AuthorizeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String callback = getFullUrl(req, "/verify");
        FlickrService service = new FlickrService();
        FlickrAuthorizeResult result = service.authorize(callback);
        String secret = result.getSecret();
        req.getSession().setAttribute(TOKEN_SECRET, secret);
        resp.sendRedirect(result.getAuthorizationUrl());
    }
    
    public String getFullUrl(HttpServletRequest req, String path) {
        StringBuilder sb = new StringBuilder();
        sb.append(req.getProtocol().toLowerCase().contains("https") ? "https" : "http");
        sb.append("://");
        sb.append(req.getServerName());
        sb.append(":");
        sb.append(req.getServerPort());
        sb.append(path);
        return sb.toString();
    }
}
