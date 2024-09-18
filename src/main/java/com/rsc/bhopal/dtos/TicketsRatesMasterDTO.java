package com.rsc.bhopal.dtos;

import com.rsc.bhopal.entity.ParkingDetails;
import com.rsc.bhopal.enums.BillType;

import lombok.Data;

@Data
public class TicketsRatesMasterDTO {

	private Long id; 
	
	private TicketDetailsDTO ticketType;

	private VisitorsTypeDTO visitorsType;

	private ParkingDetails  parkingDetails;

	private Float price;

	private BillType billType;
}
