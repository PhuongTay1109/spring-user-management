package com.tay.usermanager.config;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tay.usermanager.service.UsersService;
import com.tay.usermanager.util.JWTUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// This class inherits from OncePerRequestFilter,
// ensuring that this filter is only called once per request.
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private UsersService usersService;

	private static final Set<String> EXCLUDE_URL_PATTERNS = Stream.of("/auth/", "/public/")
            .collect(Collectors.toSet());


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		if (ignore(request.getRequestURI())) {
			System.err.println("URI IGNORE");
			filterChain.doFilter(request, response);
			return;
		}

		final String jwt;
		jwt = resolveToken(request);

		// Request doesn't have jwt
		if (jwt == null || jwt.equals("null")) {
			SecurityContextHolder.clearContext();
			System.out.println("Invalid JWT token");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			return; // Stop further filter execution and return
		}

		try {
			String userEmail = jwtUtils.extractUsername(jwt);

			if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = usersService.loadUserByUsername(userEmail);

				if (jwtUtils.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(token);
				}
			}
		} catch (ExpiredJwtException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token has expired");
			return;
		} catch (MalformedJwtException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token");
			return;
		} catch (SignatureException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature");
			return;
		} catch (UnsupportedJwtException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported JWT token");
			return;
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT token compact of handler are invalid");
			return;
		}

		filterChain.doFilter(request, response);
	}

	// Extract "Bearer $token" fron "Authorization" header
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("authorization");

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			String jwtString = bearerToken.substring(7);
			if (jwtString == null)
				return null;
			return bearerToken.substring(7);
		}

		return null;
	}

	private boolean ignore(String path) {
        return EXCLUDE_URL_PATTERNS.stream().anyMatch(path::startsWith);
    }
}
