package com.ectosense.nightowl.utils;

import com.ectosense.nightowl.data.entity.Doctor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CustomDoctorListSerializer extends StdSerializer<Set<Doctor>>
{
    public CustomDoctorListSerializer()
    {
        super(null, false);
    }
    @Override
    public void serialize(Set<Doctor> doctorList, JsonGenerator generator, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        Set<UUID> ids = new HashSet<>();

        for (Doctor doctor : doctorList) {
            ids.add(doctor.getId());
        }
        generator.writeObject(ids);
    }
}
