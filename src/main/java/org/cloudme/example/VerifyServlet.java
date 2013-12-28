package org.cloudme.example;

import static org.scribe.model.OAuthConstants.TOKEN;
import static org.scribe.model.OAuthConstants.TOKEN_SECRET;
import static org.scribe.model.OAuthConstants.VERIFIER;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.model.Token;
import org.scribe.model.Verifier;

@WebServlet( "/verify" )
public class VerifyServlet extends HttpServlet {
    private static final String BASE_URL = "/#/authorize/";
    private final FlickrService service = new FlickrService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String secret = (String) req.getSession().getAttribute(TOKEN_SECRET);
        String token = req.getParameter(TOKEN);
        Token requestToken = new Token(token, secret);
        Verifier verifier = new Verifier(req.getParameter(VERIFIER));
        FlickrAccount account = service.verify(requestToken, verifier, req);
        // Caution! Very unsafe, just for demonstration purposes:
        String clientToken = String.valueOf(System.currentTimeMillis());
        req.getSession().setAttribute(clientToken, account);
        resp.sendRedirect(BASE_URL + clientToken);
    }
}
