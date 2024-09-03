package com.rsc.bhopal.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class GeneratedTicketDTO {
	
	private Long id;	
	
	private Date generatedAt;
	
	private Double price;
	
    private String generatedBy;

   // List<BillSummary> billSummary;
	
}
