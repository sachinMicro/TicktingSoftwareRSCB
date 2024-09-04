package com.rsc.bhopal.dtos;

import java.util.Date;

import com.rsc.bhopal.enums.GroupType;
import com.rsc.bhopal.enums.VisitorsCategoryEnum;

import lombok.Data;

@Data
public class VisitorsTypeDTO {

	private long id; 
	
	private String name; 
	
	private VisitorsCategoryEnum category; 

	private GroupType  groupType; 
	
	private int minMembers; 

    private int fixedMembers;
   
    private Date addedAt;
    
    private String addedBy;
    
    private Boolean isActive;
	
}
