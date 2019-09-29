package it.fmoon.installer.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OutputPrinter {

	@Autowired
	ObjectMapper objectMapper;
	
	public String print(Object... objects) {
		try {
			return objectMapper.writer().writeValueAsString(objects);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
