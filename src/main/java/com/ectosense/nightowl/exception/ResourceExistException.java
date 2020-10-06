package com.ectosense.nightowl.exception;

public class ResourceExistException extends RuntimeException
{
    public ResourceExistException()
    {

    }
    public ResourceExistException(String message, Object... args)
    {
        super(String.format(message, args));
    }
}

