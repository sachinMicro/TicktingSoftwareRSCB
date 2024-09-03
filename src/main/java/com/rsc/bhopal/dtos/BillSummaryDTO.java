package com.rsc.bhopal.dtos;

import com.rsc.bhopal.entity.GeneratedTicket;
import com.rsc.bhopal.entity.TicketsRatesMaster;

import lombok.Data;

@Data
public class BillSummaryDTO {
	
	private Long id;
	
	private int persons;
	
	private Float price;
	
}
