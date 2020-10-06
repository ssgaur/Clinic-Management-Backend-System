package com.ectosense.nightowl.utils;

import com.ectosense.nightowl.data.entity.Assistant;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomAssistantListDeSerializer extends StdDeserializer<Set<Assistant>>
{
    public CustomAssistantListDeSerializer()
    {
        this(Set.class);
    }

    public CustomAssistantListDeSerializer( Class<?> c) {
        super(c);
    }

    @Override
    public Set<Assistant> deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException, JsonProcessingException {

        return new HashSet<>();
    }
}
