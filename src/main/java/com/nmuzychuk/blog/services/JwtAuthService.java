package com.nmuzychuk.blog.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;

import static java.time.ZoneOffset.UTC;
import static java.util.Collections.emptyList;

/**
 * Service responsible for JSON Web Token authentication.
 */
@Service
public class JwtAuthService {

    private static final String ISSUER = "com.nmuzychuk.blog";
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Bearer";
    private static final byte[] AUTH_SECRET = "secret".getBytes();

    public static void addAuthHeader(HttpServletResponse response, String username) {
        response.addHeader(AUTH_HEADER, AUTH_HEADER_PREFIX + " " + getAuthToken(username));
    }

    public static Authentication verifyAuthHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader != null) {
            String username = getUsername(authHeader);

            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, emptyList());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getUsername(String authHeader) {
        return Jwts.parser()
                .setSigningKey(AUTH_SECRET)
                .parseClaimsJws(authHeader.replace(AUTH_HEADER_PREFIX, ""))
                .getBody()
                .getSubject();
    }

    private static String getAuthToken(String username) {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(1).toInstant(UTC));

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(username)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, AUTH_SECRET)
                .compact();
    }

}
