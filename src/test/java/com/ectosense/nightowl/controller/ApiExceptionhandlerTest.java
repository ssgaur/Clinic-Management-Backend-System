package com.ectosense.nightowl.controller;


import com.ectosense.nightowl.controller.helper.CustomApiResponse;
import com.ectosense.nightowl.exception.ResourceCreationException;
import com.ectosense.nightowl.exception.ResourceDeletionException;
import com.ectosense.nightowl.exception.ResourceExistException;
import com.ectosense.nightowl.exception.ResourceNotFoundException;
import com.ectosense.nightowl.utils.MockController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * These are unittests for the exception handlers
 */
public class ApiExceptionhandlerTest
{
    private MockMvc mockMvc;

    @Mock
    private MockController mockController = new MockController();

    @BeforeMethod
    public void setupMethod()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(
                mockController
        ).setControllerAdvice(new ApiExceptionHandler()).build();
    }

    private ResultActions runTest() throws Exception
    {
        return mockMvc.perform(MockMvcRequestBuilders.get("/test"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testHandleArgumentTypeMismatch() throws Exception
    {
        MethodParameter mp = new MethodParameter(
                MockController.class.getMethod("test", Integer.class), 0);

        Mockito.when(mockController.test(null)).thenThrow(new MethodArgumentTypeMismatchException(
                "Test",
                Integer.class,
                "Integer",
                mp,
                new NumberFormatException()
        ));

        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; " +
                        "nested exception is java.lang.NumberFormatException");
    }

    @Test
    public void testhandleInvalidEncoding() throws Exception
    {
        Mockito.when(mockController.test(null)).thenThrow(new UnsupportedEncodingException());
        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "Invalid encoding detected");
    }

    @Test
    public void testHandleResourceCreationException() throws Exception
    {
        Mockito.when(mockController.test(null)).thenThrow(new ResourceCreationException());
        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "Error creating resource");
    }

    @Test
    public void testHandleIOException() throws Exception
    {
        Mockito.when(mockController.test(null)).thenThrow(new IOException());
        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "Encountered IO Exception");
    }

    @Test
    public void testHandleBadArgumentException() throws Exception
    {
        Mockito.when(mockController.test(null)).thenThrow(new IllegalArgumentException());
        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "Encountered illegal argument");
    }

    @Test
    public void testHandleResourceNotFoundException() throws Exception
    {
        Mockito.when(mockController.test(null)).thenThrow(new ResourceNotFoundException());
        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "Data not found");
    }

    @Test
    public void testHandleAllOtherExceptions() throws Exception
    {
        Mockito.when(mockController.test(null)).thenThrow(new Exception());
        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "Something went wrong. Please try again.");
    }

    @Test
    public void testHandleResourceNotFoundCustomMessageException() throws Exception
    {
        Mockito.when(mockController.test(null)).thenThrow
                (new ResourceNotFoundException("Resource Not Found!"));

        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "Resource Not Found!");
    }

    @Test
    public void testHttpRequestMethodNotSupportedException() throws Exception
    {
        Mockito.when(mockController.test(null)).thenThrow
                (new HttpRequestMethodNotSupportedException("Methond not Found or Implemented"));

        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "Request method 'Methond not Found or Implemented' not supported");
    }

    @Test
    public void testResourceExistException() throws Exception
    {
        Mockito.when(mockController.test(null)).thenThrow(new ResourceExistException());
        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "Resource already exists.");
    }

    @Test
    public void testHandleRequiredRequestVariableMissingException() throws Exception
    {
        Mockito.when(mockController.test(null)).thenThrow
                (new ServletRequestBindingException("One or more request variables not supplied."));

        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "One or more request variables not supplied.");
    }

    @Test
    public void testHandleResourceDeletionException() throws Exception
    {
        Mockito.when(mockController.test(null)).thenThrow
                (new ResourceDeletionException("Resource could not be deleted."));

        String jsonRes = runTest().andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CustomApiResponse res = mapper.readValue(jsonRes, CustomApiResponse.class);

        List<String> errors = (List<String>) res.getErrors();
        Assert.assertEquals(errors.get(0),
                "Resource could not be deleted.");
    }
}
