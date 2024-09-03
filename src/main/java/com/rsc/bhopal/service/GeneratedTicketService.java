package com.rsc.bhopal.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rsc.bhopal.dtos.GeneratedTicketDTO;
import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.entity.BillSummary;
import com.rsc.bhopal.entity.GeneratedTicket;
import com.rsc.bhopal.entity.TicketsRatesMaster;
import com.rsc.bhopal.repos.GeneratedTicketRepository;
import com.rsc.bhopal.utills.CommonUtills;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class GeneratedTicketService {
	
	@Autowired
	GeneratedTicketRepository generatedTicketRepo;
	
	@Autowired
	private TicketsRatesService ticketsRatesService;
	
	@Autowired
	private RSCUserDetailsService userDetailsService;
	
	
	public List<GeneratedTicketDTO> getRecentTickets(int rows){
		List<GeneratedTicketDTO> ticketDTOs = new ArrayList();
		Pageable page=PageRequest.of(0, rows, Sort.by(Direction.DESC, "id"));		
		List<GeneratedTicket> tickets=generatedTicketRepo.recentRecords(page);		
		tickets.forEach(ticket->{
			log.debug(ticket.toString());
			GeneratedTicketDTO ticketDTO = new GeneratedTicketDTO();
			BeanUtils.copyProperties(ticket, ticketDTO);
			ticketDTO.setGeneratedBy(ticket.getGeneratedBy().getName());		
			ticketDTOs.add(ticketDTO);
		});		
		return ticketDTOs;
	}
	
	
	public void saveAndPrintTicket(TicketSelectorDTO dto,Principal user) throws JsonProcessingException {
	     //GeneratedTicket	
		 List<TicketsRatesMaster> rates = ticketsRatesService.getTicketRateByGroup(dto.getTickets(), dto.getGroup());
         List<BillSummary> billRows = new ArrayList<>();
         Double totalPrice = 0d;
         
         GeneratedTicket generatedTicket=new GeneratedTicket(); 
         
         generatedTicket.setGeneratedAt(new Date());
         generatedTicket.setGeneratedBy(userDetailsService.getUserByUsername(user.getName()));
         
         generatedTicket=generatedTicketRepo.save(generatedTicket);
         
         for(TicketsRatesMaster rate:rates) {
        	 BillSummary billRow = new BillSummary();			 
			 billRow.setPersons(dto.getPersons());
			 billRow.setPrice(dto.getPersons()*rate.getPrice());             
			 billRow.setRate(rate);
			 totalPrice+=billRow.getPrice();
			 billRow.setGeneratedTicket(generatedTicket);	
			 billRows.add(billRow);
         }
		
		 generatedTicket.setPrice(totalPrice);
		 generatedTicket.setBillSummary(billRows);
		 generatedTicketRepo.save(generatedTicket);
		 
		 log.debug("generatedTicket "+CommonUtills.convertToJSON(generatedTicket));
			
	}
	

}
