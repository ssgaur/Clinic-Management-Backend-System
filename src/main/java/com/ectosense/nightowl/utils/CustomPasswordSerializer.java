package com.ectosense.nightowl.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomPasswordSerializer extends StdSerializer<String>
{
    public CustomPasswordSerializer()
    {
        super(null, false);
    }

    @Override
    public void serialize(String password, JsonGenerator generator, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        generator.writeObject("**************");
    }
}
