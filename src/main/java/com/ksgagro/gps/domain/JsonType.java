package com.ksgagro.gps.domain;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;


public class JsonType implements UserType, ParameterizedType, Serializable{

	private static final long serialVersionUID = -2177091301179910519L;

	public static final String LIST_TYPE = "LIST";
	private static final int[] SQL_TYPES = new int[]{Types.LONGNVARCHAR};
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final TypeReference<List<?>> LIST_TYPE_REF = new TypeReference<List<?>>() {};
	
	private JavaType valueType = null;
	private Class<?> classType = null;
	
	public void setParameterValues(Properties parameters) {
		 String type = parameters.getProperty("type");
	        if (type.equals(LIST_TYPE)) {
	            if (parameters.getProperty("element") != null) {
	                try {
	                    valueType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, Class.forName(parameters.getProperty("element")));
	                } catch (ClassNotFoundException e) {
	                     throw new IllegalArgumentException("Type " + type + " is not a valid type.");
	                }
	            } else {
	                valueType = OBJECT_MAPPER.getTypeFactory().constructType(LIST_TYPE_REF);
	            }
	            classType = List.class;
	        } 
		
	}
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return deepCopy(cached);
	}
	@SuppressWarnings("rawtypes")
	public Object deepCopy(Object value) throws HibernateException {
		if(value==null){
			return null;
		}else if(valueType.isCollectionLikeType()){
			try{
				Object newValue = value.getClass().newInstance();
				Collection newValueCollection = (Collection)value;
				return newValueCollection;
			}catch (InstantiationException e) {
                throw new HibernateException("Failed to deep copy the collection-like value object.", e);
            } catch (IllegalAccessException e) {
                throw new HibernateException("Failed to deep copy the collection-like value object.", e);
            }
		}else return null;
		
	}
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable)deepCopy(value);
	}
	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		return Objects.equals(arg0, arg1);
	}
	public int hashCode(Object arg0) throws HibernateException {
		return Objects.hashCode(arg0);
	}
	public boolean isMutable() {
		return true;
	}
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		
		return nullSafeGet(rs,names, owner);
	}
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		
		nullSafeSet(st, value, index);	
	}
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        Object result = null;
        if (valueType == null) {
            throw new HibernateException("Value type not set.");
        }
        if (value != null && !value.equals("")) {
            try {
                result = OBJECT_MAPPER.readValue(value, valueType);
            } catch (IOException e) {
                throw new HibernateException("Exception deserializing value " + value, e);
            }
        }
        return result;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        StringWriter sw = new StringWriter();
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            try {
                OBJECT_MAPPER.writeValue(sw, value);
                
                st.setString(index, sw.toString());
            } catch (IOException e) {
                throw new HibernateException("Exception serializing value " + value, e);
            }
        }
    }
	public Object replace(Object arg0, Object arg1, Object arg2) throws HibernateException {
		
		return deepCopy(arg0);
	}
	public Class returnedClass() {
		return classType;
	}
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

}
