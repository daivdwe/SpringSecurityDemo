package sec;

import com.google.common.base.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * Test with reverse proxy or curl -H "X-Forwarded-For: 127.0.0.1" localhost:8080
 * See reverse proxy instructions in README.md
 */
public class Filter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Filter.class);

    private static final String XFF_HEADER_NAME = "X-Forwarded-For";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader(XFF_HEADER_NAME) != null) {
            LOGGER.warn("Illegal Header ({}) found in request: {}{}", XFF_HEADER_NAME,
                    request.getRequestURL(), determineQueryString(request));
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Header " + XFF_HEADER_NAME + " is not allowed!"); // Error Page
        } else {
            filterChain.doFilter(request, response); // continue with filter chain
        }
    }

    private String determineQueryString(HttpServletRequest request) {
        String queryString = Strings.nullToEmpty(request.getQueryString());

        if (queryString.isEmpty()) {
            return queryString;
        }
        return "?" + queryString;
    }
}
