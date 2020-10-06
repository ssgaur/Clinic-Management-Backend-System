package com.ectosense.nightowl.controller;

import com.ectosense.nightowl.exception.ResourceNotFoundException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TestControllerTest
{
    private MockMvc mockMvc;

    @InjectMocks
    private TestController _testController = new TestController();

    @BeforeClass
    public void setup()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ResourceNotFoundException(), _testController).build();
    }

    @BeforeMethod
    public void initMocks()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUserTest() throws Exception
    {
        String data = "{\n" +
                "\t\"firstName\" :  \"Shailendra\",\n" +
                "\t\"lastName\"  :  \"Singh\",\n" +
                "\t\"email\"     :  \"ssg@tt.com\",\n" +
                "\t\"password\"  :  \"123456\"\n" +
                "}  ";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tests")
                .header("X-Customer-ID", UUID.randomUUID().toString())
                .header("X-User-ID", "user_1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data))
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$.errors[0]", Matchers.is("{groupMeta.name.notNull}")))
                .andReturn();
    }

}
