package com.rsc.bhopal.dtos;

import lombok.Data;

@Data
public class TicketsRatesMasterDTO {

	private long id; 
	
	private TicketDetailsDTO  ticketType;

	private VisitorsTypeDTO  visitorsType;

	private float price;
}
