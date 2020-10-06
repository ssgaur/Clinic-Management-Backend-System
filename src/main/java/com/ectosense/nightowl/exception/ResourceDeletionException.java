package com.ectosense.nightowl.exception;

public class ResourceDeletionException extends RuntimeException
{
    public ResourceDeletionException()
    {
    }
    public ResourceDeletionException(String message, Object... args)
    {
        super(String.format(message, args));
    }

}
