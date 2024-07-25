package com.rsc.bhopal.dtos;

import com.rsc.bhopal.enums.VisitorsTypeEnum;

import lombok.Data;

@Data
public class VisitorsTypeDTO {

	private long id; 
	
	private String name; 
	
	private VisitorsTypeEnum type; 

	private int minMembers; 

    private int fixedMembers;
   
}
