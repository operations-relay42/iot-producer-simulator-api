package com.relay.iot.consumer.simulator.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

/**
 * @author omidp
 *
 */
@Slf4j
public class JsonUtil {

	public static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

	private JsonUtil() {
	}

	public static String writeValueAsString(Object instance)
    {
        try
        {
            return MAPPER.writeValueAsString(instance);
        }
        catch (JsonProcessingException e)
        {
            log.info("JsonProcessingException {}", e);
            return "";
        }
    }
	
	public static <T> T toObject(String json, Class<T> clz)
    {
        try
        {
            return MAPPER.readValue(json.getBytes("UTF-8"), clz);
        }
        catch (IOException e)
        {
            log.info("JsonProcessingException {}", e);
            return null;
        }
    }
	
	public static boolean isJson(String str)
    {
        if(StringUtil.isEmpty(str))
            return false;
        if(str.startsWith("{") && str.endsWith("}"))
            return true;
        return str.startsWith("[") && str.endsWith("]");
    }
	
	public static <T> List<T> toList(String json, TypeReference<List<T>> typeRef)
    {
        try
        {
            return MAPPER.readValue(json, typeRef);
        }
        catch (IOException e)
        {
            log.info("JsonProcessingException {}", e);
            return null;
        }
    }

	
}
