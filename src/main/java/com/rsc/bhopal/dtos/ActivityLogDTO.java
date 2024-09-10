package com.rsc.bhopal.dtos;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityLogDTO {	
	
	private Long id;

	private Boolean status;

	private String message;

	private Date actionAt;
	
	private String actionBy;
	
	private LogPayload payload;
	
	
}
