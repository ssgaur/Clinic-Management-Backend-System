package com.ectosense.nightowl.security.jwt;

import com.ectosense.nightowl.security.config.SecurityConstants;
import com.ectosense.nightowl.security.users.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.Jwts;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter
{
    private CustomUserDetailsService customUserDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, ApplicationContext applicationContext)
    {
        super(authManager);
        this.customUserDetailsService = applicationContext.getBean(CustomUserDetailsService.class);
    }

    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException
    {
        String header = req.getHeader(SecurityConstants.TOKEN_HEADER);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX))
        {
            chain.doFilter(req, res);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        }
        catch (ExpiredJwtException e)
        {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Your token has been expired. Please login again");
        }
    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
            throws ExpiredJwtException {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        if (token != null) {
            // parse the token.
            String username = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (userDetails != null) {
                return new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
