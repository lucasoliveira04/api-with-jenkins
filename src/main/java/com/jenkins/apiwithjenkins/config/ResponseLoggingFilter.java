package com.jenkins.apiwithjenkins.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ResponseLoggingFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        long startTime = System.currentTimeMillis();

        try {
            chain.doFilter(request, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            byte[] content = wrappedResponse.getContentAsByteArray();
            String responseBody = new String(content, StandardCharsets.UTF_8);

            String formattedBody = formatJsonSafely(responseBody);

            log.info("""
                    
                    ======================= [RESPONSE LOG] ======================
                    Method:   {}
                    Endpoint: {}
                    Status:   {}
                    Duration: {} ms
                    Body:
                    {}
                    =================================================================
                    """,
                    request.getMethod(),
                    request.getRequestURI(),
                    wrappedResponse.getStatus(),
                    duration,
                    formattedBody.isBlank() ? "(empty body)" : formattedBody
            );

            wrappedResponse.copyBodyToResponse();
        }
    }

    private String formatJsonSafely(String content) {
        if (content == null || content.isBlank()) return "";
        try {
            JsonNode node = MAPPER.readTree(content);
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (Exception e) {
            return content;
        }
    }
}
