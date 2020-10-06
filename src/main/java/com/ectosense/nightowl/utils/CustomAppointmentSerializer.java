package com.ectosense.nightowl.utils;

import com.ectosense.nightowl.data.entity.Appointment;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomAppointmentSerializer extends StdSerializer<Appointment>
{
    public CustomAppointmentSerializer()
    {
        super(null, false);
    }
    @Override
    public void serialize(Appointment appointment, JsonGenerator generator, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        generator.writeObject(appointment.getId());
    }
}
