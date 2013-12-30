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
    private final FlickrService service = new FlickrService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream in = service.photosSearch((FlickrAccount) req.getUserPrincipal());
        ByteStreams.copy(in, resp.getOutputStream());
    }
}
