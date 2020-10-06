package com.ectosense.nightowl.controller;

import com.ectosense.nightowl.controller.helper.CustomApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ectosense.nightowl.exception.ResourceCreationException;
import com.ectosense.nightowl.exception.ResourceDeletionException;
import com.ectosense.nightowl.exception.ResourceExistException;
import com.ectosense.nightowl.exception.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * This contains all exception-handling logic.
 */
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler
{
    private static final String DEFAULT_ERROR_MESSAGE = "Something went wrong. Please try again.";

    private String getMessageFromError(Exception e, String defaultMessage)
    {
        if (e.getMessage() == null)
        {
            return defaultMessage;
        }
        else
        {
            return e.getLocalizedMessage();
        }

    }

    // ******************************* Spring Exception ********************************************************

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleArgumentTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletResponse response)
            throws IOException
    {
        log.warn("Argument type mismatch", e);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        String message = getMessageFromError(e,"Argument type mismatch");
        writeError(message, response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handleMethodNotFound(HttpRequestMethodNotSupportedException e, HttpServletResponse response)
            throws IOException
    {
        log.warn("Methond not Found or Implemented", e);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        String message = getMessageFromError(e,"Not Found!");
        writeError(message, response);
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public void handleInvalidEncoding(UnsupportedEncodingException e, HttpServletResponse response)
            throws IOException
    {
        log.warn("Invalid encoding detected", e);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        String message = getMessageFromError(e,"Invalid encoding detected");
        writeError(message, response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleBadArgumentException(IllegalArgumentException e, HttpServletResponse response)
            throws IOException
    {
        log.warn("Encountered illegal argument", e);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String message = getMessageFromError(e,"Encountered illegal argument");
        writeError(message, response);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public void handleRequiredRequestVariableMissingException(ServletRequestBindingException e,
                                                              HttpServletResponse response) throws IOException
    {
        log.warn(e.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        String message = getMessageFromError(e,"One or more request variables not supplied.");
        writeError(message, response);
    }

    @ExceptionHandler(IOException.class)
    public void handleIOException(IOException e, HttpServletResponse response)
            throws IOException
    {
        log.error("Encountered IO Exception", e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        String message = getMessageFromError(e,"Encountered IO Exception");
        writeError(message, response);
    }


    // ******************************* User Defined Exception ********************************************************

    @ExceptionHandler(ResourceCreationException.class)
    public void handleResourceCreationException(ResourceCreationException e, HttpServletResponse response)
            throws IOException
    {
        log.error("Error creating resource", e);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        String message = getMessageFromError(e,"Error creating resource");
        writeError(message, response);
    }

    @ExceptionHandler(ResourceDeletionException.class)
    public void handleResourceDeletionException(ResourceDeletionException e,
                                                HttpServletResponse response) throws IOException
    {
        log.warn("Resource deletion Failed" + e.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        String message = getMessageFromError(e,"Resource could not be deleted.");
        writeError(message, response);
    }

    @ExceptionHandler(ResourceExistException.class)
    public void handleResourceExistsException(ResourceExistException e, HttpServletResponse response)
            throws IOException
    {
        log.warn("Resource Already Exists: " + e.getMessage());
        response.setStatus(HttpServletResponse.SC_CONFLICT);

        String message = getMessageFromError(e,"Resource already exists.");
        writeError(message, response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFoundException(ResourceNotFoundException e, HttpServletResponse response)
            throws IOException
    {
        log.warn("Resource Not Found: " + e.getMessage());
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        String message = getMessageFromError(e,"Data not found");
        writeError(message, response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public void handleJWTTokenExpired(ExpiredJwtException e, HttpServletResponse response)
            throws IOException
    {
        log.warn("JWT Token has been expired: " + e.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        writeError("Your token has been expired. Please login again.", response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleRoleBasedAccessDenied(AccessDeniedException e, HttpServletResponse response)
            throws IOException
    {
        log.warn("You do not have enough access rights." + e.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        writeError("You do not have enough access rights.", response);
    }

    @ExceptionHandler
    public void handleAllOtherExceptions(Exception e, HttpServletResponse response) throws IOException
    {
        log.warn("Unhandled exception", e);

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        writeError(DEFAULT_ERROR_MESSAGE, response);
    }

    /**
     * This creates an error response so that the client can get more detailed insight into errors.
     *
     * @param message the message to be returned
     * @param response the response to write the content to
     */
    private void writeError(String message, HttpServletResponse response) throws IOException
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        CustomApiResponse apiMessage = new CustomApiResponse(response.getStatus(), "Failed",
                "", Arrays.asList(message));
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        out.print(mapper.writeValueAsString(apiMessage));
        out.flush();
    }
}
