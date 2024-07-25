package com.rsc.bhopal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;

@Component
public class BillCalculatorService {

	@Autowired
	private TicketsRatesService ticketsRatesService;
	
	
	public double calculateTotal(TicketSelectorDTO ticketSelectorDTO) {
		double totalAmount = 0;
		
		
        for(Long ticketId : ticketSelectorDTO.getTickets()) {
        	TicketsRatesMasterDTO ticketRate =    ticketsRatesService.getTicketRateByGroup(ticketId,ticketSelectorDTO.getGroup());

        	totalAmount +=(ticketRate.getPrice()*ticketSelectorDTO.getPersons());
      
        }
		return totalAmount;
	}
	
	
	
	
}
