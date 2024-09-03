package com.rsc.bhopal.utills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtills {

	public static String convertToJSON( Object obj) throws JsonProcessingException {
		 ObjectMapper om = new ObjectMapper();
         return om.writeValueAsString(obj);
	}
	
}
