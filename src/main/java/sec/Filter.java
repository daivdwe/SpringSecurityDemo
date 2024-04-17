package sec;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class Filter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Filter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String xffHeaderName = "X-Forwarded-For";

        if (request.getHeader(xffHeaderName) != null) {
            // test with reverse proxy or curl -H "X-Forwarded-For: 127.0.0.1" localhost:8080
            LOGGER.warn("Illegal Header found in request: {}", xffHeaderName);
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Header " + xffHeaderName + " is not allowed!"); // Error Page
        } else {
            filterChain.doFilter(request, response); // continue with filter chain
        }
    }
}
