package br.com.aqueteron.boilerplate.components.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
public class LoggingApiWebFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        chain.doFilter(requestWrapper, responseWrapper);
        byte[] requestBody = requestWrapper.getContentAsByteArray();
        byte[] responseBody = responseWrapper.getContentAsByteArray();
        logRequest(requestWrapper, new String(requestBody, StandardCharsets.UTF_8));
        logResponse(requestWrapper, responseWrapper, new String(responseBody, StandardCharsets.UTF_8));
        responseWrapper.copyBodyToResponse();
    }

    private void logRequest(final HttpServletRequest request, final String requestBody) throws IOException {
        log.info("Processing request {} {} with params: {} , headers: {} and body: {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                requestHeadersToMap(request),
                requestBody
        );
    }

    private void logResponse(final HttpServletRequest request, final HttpServletResponse response, final String responseBody) {
        log.info("Processing response {} {} with status: {} , headers {} and body: {}",
                request.getMethod(),
                request.getRequestURI(),
                HttpStatus.valueOf(response.getStatus()),
                responseHeadersToMap(response),
                responseBody
        );
    }

    private Map<String, String> requestHeadersToMap(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headersEnumeration = request.getHeaderNames();
        while (headersEnumeration.hasMoreElements()) {
            String headerKey = headersEnumeration.nextElement();
            headers.put(headerKey, request.getHeader(headerKey));
        }
        return headers;
    }

    private Map<String, Collection<String>> responseHeadersToMap(HttpServletResponse httpServletResponse) {
        Map<String, Collection<String>> headers = new HashMap<>();
        Collection<String> headersEnumeration = httpServletResponse.getHeaderNames();
        for (String headerKey : headersEnumeration) {
            headers.put(headerKey, httpServletResponse.getHeaders(headerKey));
        }
        return headers;
    }
}
