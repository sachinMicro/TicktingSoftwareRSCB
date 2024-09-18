package com.rsc.bhopal.utills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtills {

	public static String convertToJSON( Object obj) throws JsonProcessingException {
		 ObjectMapper om = new ObjectMapper();
         return om.writeValueAsString(obj);
	}	
	public static <T> T convertJSONToObject(String parload, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();		
		try {
			return mapper.readValue(parload,clazz);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
