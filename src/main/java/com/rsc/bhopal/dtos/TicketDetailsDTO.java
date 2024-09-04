package com.rsc.bhopal.dtos;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketDetailsDTO {
	
	private long id;	
	private String name;
	  
    private Date addedAt;
    
    private String addedBy;
    
    private Boolean isActive;
    
}
