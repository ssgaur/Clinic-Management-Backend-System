package com.ectosense.nightowl.utils;

import com.ectosense.nightowl.data.entity.Doctor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomDoctorListDeSerializer extends StdDeserializer<Set<Doctor>>
{
    public CustomDoctorListDeSerializer()
    {
        this(Set.class);
    }
    public CustomDoctorListDeSerializer(Class<?> c)
    {
        super(c);
    }

    @Override
    public Set<Doctor> deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException,
            JsonProcessingException {

        return new HashSet<>();
    }
}
