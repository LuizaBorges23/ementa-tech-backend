package com.example.disciplinas.educacao.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class FrontendOriginFilter extends OncePerRequestFilter {

    @Value("${app.frontend.url:http://localhost:4200,http://127.0.0.1:4200}")
    private String frontendUrls;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/h2-console") || path.startsWith("/error") || "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    private boolean isOrigemValida(String header) {
        if (header == null || header.isBlank()) {
            return false;
        }

        if (header.startsWith("http://localhost:4200") || header.startsWith("http://127.0.0.1:4200")) {
            return true;
        }

        if (frontendUrls != null && !frontendUrls.isBlank()) {
            return Arrays.stream(frontendUrls.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .anyMatch(header::startsWith);
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String origin = request.getHeader("Origin");
        String referer = request.getHeader("Referer");
        boolean originValida = isOrigemValida(origin);
        boolean refererValido = isOrigemValida(referer);

        if (!originValida && !refererValido) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Acesso permitido apenas pelo APP-FRONT configurado.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
