package com.ectosense.nightowl.config;

import com.ectosense.nightowl.exception.JSONBUserTypeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

@Slf4j
public class JSONBUserType implements UserType, ParameterizedType
{
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private Class<?> clazz;

    @Override
    public void setParameterValues(Properties params)
    {
        String className = params.getProperty("className");
        switch (className)
        {
            case "com.ectosense.nightowl.data.model.UserMeta":
                clazz = com.ectosense.nightowl.data.model.UserMeta.class;
                break;
            default:
                break;
        }
    }

    @Override
    public int[] sqlTypes()
    {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class returnedClass()
    {
        return clazz;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException
    {
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException
    {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor session,
                              Object owner) throws HibernateException, SQLException
    {
        String json = resultSet.getString(names[0]);
        if (json == null)
        {
            return null;
        }

        try {
            return MAPPER.readValue(json.getBytes("UTF-8"), returnedClass());
        } catch (IOException e) {
            log.error("Failed to convert String to " +
                    returnedClass() + e.getMessage(), e);
            throw new JSONBUserTypeException("Failed to convert String to " +
                    returnedClass() + e.getMessage(), e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
                            SharedSessionContractImplementor session) throws HibernateException, SQLException
    {
        if (value == null)
        {
            st.setNull(index, Types.OTHER);
        } else
        {
            try {
                final StringWriter writer = new StringWriter();
                MAPPER.writeValue(writer, value);
                writer.flush();
                st.setObject(index, writer.toString(), Types.OTHER);
            } catch (IOException e)
            {
                log.error("Failed to convert " + returnedClass()
                        + " to String " + e.getMessage(), e);
                throw new JSONBUserTypeException("Failed to convert " + returnedClass()
                        + " to String " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException
    {
        if (value != null)
        {
            try {
                Class modelClass = returnedClass();
                String ObjectAsString = MAPPER.writeValueAsString(value);
                return MAPPER.readValue(ObjectAsString, modelClass);
            } catch (IOException e) {
                log.error("Failed to deep copy object", e);
                throw new HibernateException("Failed to deep copy object", e);
            }
        }
        return null;
    }

    @Override
    public boolean isMutable()
    {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException
    {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (IOException e) {
            log.error("Failed to disassemble object", e);
            throw new HibernateException("Failed to disassemble object", e);
        }
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException
    {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return deepCopy(original);
    }
}
