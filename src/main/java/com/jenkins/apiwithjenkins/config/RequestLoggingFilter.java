package com.jenkins.apiwithjenkins.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;

@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectWriter JSON_WRITER = MAPPER.writerWithDefaultPrettyPrinter();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

        try {
            chain.doFilter(wrappedRequest, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            String body = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
            String formattedBody = formatJsonSafely(body);

            String headers = getHeadersAsString(request);

            log.info("""
                    
                    ======================= [REQUEST LOG] =======================
                    Method:   {}
                    URI:      {}
                    Duration: {} ms
                    Headers:
                    {}
                    Body:
                    {}
                    =================================================================
                    """,
                    request.getMethod(),
                    request.getRequestURI(),
                    duration,
                    headers.isEmpty() ? "(no headers)" : headers,
                    formattedBody.isBlank() ? "(empty body)" : formattedBody
            );
        }
    }

    private String formatJsonSafely(String body) {
        if (body == null || body.isBlank()) return "";
        if (!isJson(body)) return body;
        try {
            Object json = MAPPER.readValue(body, Object.class);
            return JSON_WRITER.writeValueAsString(json);
        } catch (Exception e) {
            return body;
        }
    }

    private boolean isJson(String body) {
        String trimmed = body.trim();
        return trimmed.startsWith("{") || trimmed.startsWith("[");
    }

    private String getHeadersAsString(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder("{\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) return "";
        for (String name : Collections.list(headerNames)) {
            sb.append("  ").append(name).append(": ")
                    .append(request.getHeader(name)).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
