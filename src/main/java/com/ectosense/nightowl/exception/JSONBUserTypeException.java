package com.ectosense.nightowl.exception;

public class JSONBUserTypeException extends RuntimeException
{
    public  JSONBUserTypeException()
    {

    }

    public JSONBUserTypeException(String message, Object... args)
    {
        super(String.format(message, args));
    }
}
