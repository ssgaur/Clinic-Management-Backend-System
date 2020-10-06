package com.ectosense.nightowl.exception;

public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException()
    {

    }

    public ResourceNotFoundException(String message, Object... args)
    {
        super(String.format(message, args));
    }
}
