package br.com.aqueteron.bp.service.components;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import java.io.IOException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class CorrelationWebFilterTest {

    @InjectMocks
    private CorrelationWebFilter correlationWebFilter;

    @Test
    void shouldDoFilter_whenNoApiPath_doFilter() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain chain = Mockito.mock(FilterChain.class);

        when(httpServletRequest.getServletPath()).thenReturn("/test");

        this.correlationWebFilter.doFilter(httpServletRequest, httpServletResponse, chain);

        verify(chain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    void shouldDoFilter_whenApiPathAndWithoutCorrelationIdHeader_doFilter() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain chain = Mockito.mock(FilterChain.class);

        when(httpServletRequest.getServletPath()).thenReturn("/api/resource");
        when(httpServletRequest.getHeader("x-correlation-id")).thenReturn(null);

        this.correlationWebFilter.doFilter(httpServletRequest, httpServletResponse, chain);

        verify(chain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    void shouldDoFilter_whenApiPathAndWithEmptyCorrelationIdHeader_doFilter() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain chain = Mockito.mock(FilterChain.class);

        when(httpServletRequest.getServletPath()).thenReturn("/api/resource");
        when(httpServletRequest.getHeader("x-correlation-id")).thenReturn("");

        this.correlationWebFilter.doFilter(httpServletRequest, httpServletResponse, chain);

        verify(chain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    void shouldDoFilter_whenApiPathAndWithCorrelationIdHeader_doFilter() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain chain = Mockito.mock(FilterChain.class);
        String correlationId = UUID.randomUUID().toString();

        when(httpServletRequest.getServletPath()).thenReturn("/api/resource");
        when(httpServletRequest.getHeader("x-correlation-id")).thenReturn(correlationId);

        this.correlationWebFilter.doFilter(httpServletRequest, httpServletResponse, chain);

        verify(chain).doFilter(httpServletRequest, httpServletResponse);
    }
}
