package com.ectosense.nightowl.exception;

public class ResourceCreationException extends RuntimeException
{
    public ResourceCreationException()
    {
    }
    public ResourceCreationException(String message, Object... args)
    {
        super(String.format(message, args));
    }

}
