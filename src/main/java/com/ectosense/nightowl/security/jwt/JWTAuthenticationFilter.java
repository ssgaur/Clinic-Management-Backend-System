package com.ectosense.nightowl.security.jwt;

import com.ectosense.nightowl.security.config.SecurityConstants;
import com.ectosense.nightowl.security.users.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ectosense.nightowl.data.model.LoginPojo;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.jsonwebtoken.Jwts;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException
    {
        try
        {
            LoginPojo creds = new ObjectMapper().readValue(request.getInputStream(), LoginPojo.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                    creds.getPassword(),
                            new ArrayList<>())
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse
            response, FilterChain chain, Authentication authResult) throws IOException, ServletException
    {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        List<String> role = authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();

        String email = user.getUsername();

        Date expirationTime = new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME*1000);
        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationTime)
                .claim("role",role.get(0) )
                .signWith(signingKey)
                //.signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        response.addHeader(SecurityConstants.TOKEN_HEADER ,  SecurityConstants.TOKEN_PREFIX  + token);
        response.getWriter().write("{\"jwt\":\"" +token +"\"}");
    }
}
