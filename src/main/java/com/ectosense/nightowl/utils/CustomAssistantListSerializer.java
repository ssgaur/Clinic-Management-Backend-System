package com.ectosense.nightowl.utils;

import com.ectosense.nightowl.data.entity.Assistant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CustomAssistantListSerializer extends StdSerializer<Set<Assistant>>
{
    public CustomAssistantListSerializer()
    {
        super(null, false);
    }

    @Override
    public void serialize(Set<Assistant> assistantList, JsonGenerator generator, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        Set<UUID> ids = new HashSet<>();

        for (Assistant assistant : assistantList) {
            ids.add(assistant.getId());
        }
        generator.writeObject(ids);
    }
}
