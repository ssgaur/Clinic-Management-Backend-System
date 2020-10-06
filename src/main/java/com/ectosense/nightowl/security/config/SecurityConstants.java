package com.ectosense.nightowl.security.config;

public final class SecurityConstants
{
    public static final String AUTH_LOGIN_URL = "/login";

    // Signing key for HS512 algorithm
    // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
    public static final String JWT_SECRET = "AA9B4A2A38E3AA9B4A2A38E335C89977B5CE1454435C89977B5CE14544";
    public static final long EXPIRATION_TIME = 86400;
    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
}
