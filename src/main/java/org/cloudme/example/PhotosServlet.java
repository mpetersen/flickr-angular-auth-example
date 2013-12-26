package org.cloudme.example;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.io.ByteStreams;

@WebServlet( "/photos" )
public class PhotosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientToken = req.getHeader("clientToken");
        FlickrAccount account = (FlickrAccount) req.getSession().getAttribute(clientToken);
        if (account == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else {
            InputStream in = new FlickrService().photosSearch(account);
            ByteStreams.copy(in, resp.getOutputStream());
        }
    }
}
