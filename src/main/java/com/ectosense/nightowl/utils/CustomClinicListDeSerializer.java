package com.ectosense.nightowl.utils;

import com.ectosense.nightowl.data.entity.Clinic;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomClinicListDeSerializer extends StdDeserializer<Set<Clinic>>
{
    public CustomClinicListDeSerializer()
    {
        this(Set.class);
    }
    public CustomClinicListDeSerializer(Class<?> c) {
        super(c);
    }

    @Override
    public Set<Clinic> deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException,
            JsonProcessingException {

        return new HashSet<>();
    }
}
