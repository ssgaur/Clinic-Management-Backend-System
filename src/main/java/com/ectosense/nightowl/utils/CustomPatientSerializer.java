package com.ectosense.nightowl.utils;

import com.ectosense.nightowl.data.entity.Patient;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomPatientSerializer extends StdSerializer<Patient>
{
    public CustomPatientSerializer()
    {
        super(null, false);
    }

    @Override
    public void serialize(Patient patient, JsonGenerator generator, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        generator.writeObject(patient.getId());
    }
}
