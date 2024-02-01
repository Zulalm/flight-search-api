package com.example.flightsearchapi.config;

import com.example.flightsearchapi.services.UserService;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final String secret = "secretKey";
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), signatureAlgorithm.getJcaName());

    @Autowired
    UserService userDetailsServiceImplementation;
    public String getUsername(String token){
        try {
            Claims allClaims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
            return allClaims.getSubject();
        } catch (ExpiredJwtException | MalformedJwtException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public String createToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3 * 60L * 60L * 1000L))// token is valid for 3 hours
                .signWith(SignatureAlgorithm.HS256, signingKey).compact();
    }
    private Boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUsername(token);
        Claims allClaims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        boolean isTokenExpired = allClaims.getExpiration().before(new Date());

        return username.equals(userDetails.getUsername()) && !isTokenExpired;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            final String authHeader = request.getHeader(AUTHORIZATION);
            final String username;
            final String jwtToken;

            if(authHeader == null || !authHeader.startsWith("Bearer")){
                filterChain.doFilter(request,response);
                return;
            }
            jwtToken = authHeader.substring(7);
            username = getUsername(jwtToken);
            if(username  != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsServiceImplementation.loadUserByUsername(username);
                final boolean isTokenValid = isTokenValid(jwtToken,userDetails);
                if(isTokenValid){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request,response);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }
}
