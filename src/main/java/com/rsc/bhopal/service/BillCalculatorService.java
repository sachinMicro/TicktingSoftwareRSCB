package com.rsc.bhopal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rsc.bhopal.dtos.BillDescription;
import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;

@Component
public class BillCalculatorService {

	@Autowired
	private TicketsRatesService ticketsRatesService;
	

	public List<BillDescription> summarizeBill(TicketSelectorDTO ticketSelectorDTO) {
		List<BillDescription> bills = new ArrayList<>();
		
		List<String> tickets = new ArrayList<>();


        for(Long ticketId : ticketSelectorDTO.getTickets()) {
        	TicketsRatesMasterDTO ticketRate =    ticketsRatesService.getTicketRateByGroup(ticketId,ticketSelectorDTO.getGroup());	

			BillDescription bill = new BillDescription();

			bill.setTicket(ticketRate.getTicketType().getName());
			bill.setGroupName(ticketRate.getVisitorsType().getName());
			bill.setPerson(ticketSelectorDTO.getPersons());
			bill.setPerPersonPrice(ticketRate.getPrice());
			bill.setTotalSum(ticketSelectorDTO.getPersons()*ticketRate.getPrice());			
			bills.add(bill);
        }

		return bills;
	}
	
	
	public double calculateTotal(TicketSelectorDTO ticketSelectorDTO) {
		double totalAmount = 0;
		
		
        for(Long ticketId : ticketSelectorDTO.getTickets()) {
        	TicketsRatesMasterDTO ticketRate =    ticketsRatesService.getTicketRateByGroup(ticketId,ticketSelectorDTO.getGroup());

        	totalAmount +=(ticketRate.getPrice()*ticketSelectorDTO.getPersons());
      
        }



		return totalAmount;
	}
	
	
	
	
}
