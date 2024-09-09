package com.rsc.bhopal.dtos;

import lombok.Data;

@Data
public class TicketsRatesMasterDTO {

	private Long id; 
	
	private TicketDetailsDTO  ticketType;

	private VisitorsTypeDTO  visitorsType;

	private Float price;
}
