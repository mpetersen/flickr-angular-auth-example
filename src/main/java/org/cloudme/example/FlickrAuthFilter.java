package org.cloudme.example;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

@WebFilter( urlPatterns = { "/photos" } )
public class FlickrAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {
        doHttpFilter((HttpServletRequest) req, (HttpServletResponse) resp, chain);
    }

    private void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException,
            ServletException {
        String clientToken = req.getHeader("clientToken");
        final FlickrAccount account = (FlickrAccount) req.getSession().getAttribute(clientToken);
        if (account == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else {
            req = new HttpServletRequestWrapper(req) {
                @Override
                public Principal getUserPrincipal() {
                    return account;
                }
                
                @Override
                public String getAuthType() {
                    return "FLICKR";
                }
            };
            chain.doFilter(req, resp);
        }
    }
    
    @Override
    public void destroy() {
    }
}
