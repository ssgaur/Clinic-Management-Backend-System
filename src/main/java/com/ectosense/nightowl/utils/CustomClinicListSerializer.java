package com.ectosense.nightowl.utils;

import com.ectosense.nightowl.data.entity.Clinic;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CustomClinicListSerializer extends StdSerializer<Set<Clinic>>
{
    public CustomClinicListSerializer()
    {
        super(null, false);
    }

    @Override
    public void serialize(Set<Clinic> clinicList, JsonGenerator generator, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        Set<UUID> ids = new HashSet<>();

        for (Clinic clinic : clinicList) {
            ids.add(clinic.getId());
        }
        generator.writeObject(ids);
    }
}
