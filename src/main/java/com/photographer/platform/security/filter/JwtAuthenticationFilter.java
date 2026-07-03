package com.photographer.platform.security.filter;

import com.photographer.platform.security.jwt.JwtService;
import com.photographer.platform.security.service.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        System.out.println("========================================");
        System.out.println("Request Path : " + path);

        // Skip public endpoints
        if (path.startsWith("/api/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars")) {

            System.out.println("Public API - Skipping JWT Filter");
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        System.out.println("Authorization Header : " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            System.out.println("Bearer Token Not Found");

            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7).trim();

        System.out.println("JWT Token : " + jwt);

        if (jwt.isBlank()) {

            System.out.println("JWT Token is Empty");

            filterChain.doFilter(request, response);
            return;
        }

        try {

            String userEmail = jwtService.extractUsername(jwt);

            System.out.println("Email From Token : " + userEmail);

            if (userEmail != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(userEmail);

                System.out.println("User Loaded : " + userDetails.getUsername());

                if (jwtService.isTokenValid(jwt, userDetails)) {

                    System.out.println("JWT Token Valid");

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);

                    System.out.println("Authentication Set Successfully");
                } else {

                    System.out.println("JWT Token Invalid");
                }
            }

        } catch (JwtException e) {

            System.out.println("JWT Exception : " + e.getMessage());

        } catch (Exception e) {

            System.out.println("Authentication Error : " + e.getMessage());
            e.printStackTrace();

        }

        filterChain.doFilter(request, response);
    }
}