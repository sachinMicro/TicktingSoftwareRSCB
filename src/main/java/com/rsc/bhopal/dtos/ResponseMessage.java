package com.rsc.bhopal.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage {
	private boolean status;
	private String message;
}
