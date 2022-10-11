package com.nix.zhylina.jwt;

import com.nix.zhylina.utils.TokenConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private String secret;

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Encoders
                .BASE64
                .encode(key.getEncoded());
    }

    public String createToken(String username, String roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + TokenConstants.MAX_VALIDITY_TIME);
        Claims claims = Jwts.claims().setSubject(username);

        claims.put(TokenConstants.CONST_VALUE_ROLES, roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(TokenConstants.CONST_VALUE_TOKEN);

        if (bearerToken != null && bearerToken.startsWith(TokenConstants.CONST_PREFIX_BEARER)) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean isValidateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthenticationCredentialsNotFoundException(TokenConstants.ERROR_AUTH_CREDENTIALS_EXPIRED_OR_INVALID);
        }
    }
}
