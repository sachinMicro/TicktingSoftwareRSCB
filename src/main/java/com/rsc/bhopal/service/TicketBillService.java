package com.rsc.bhopal.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.rsc.bhopal.dtos.BillSummarize;
import com.rsc.bhopal.dtos.TicketBillDTO;
import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.entity.TicketBill;
import com.rsc.bhopal.entity.TicketBillRow;
import com.rsc.bhopal.entity.TicketsRatesMaster;
import com.rsc.bhopal.repos.TicketBillRepository;
import com.rsc.bhopal.utills.CommonUtills;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class TicketBillService {
	
	@Autowired
	TicketBillRepository generatedTicketRepo;
	
	@Autowired
	private TicketsRatesService ticketsRatesService;
	
	@Autowired
	private RSCUserDetailsService userDetailsService;
	
	@Autowired
	private BillCalculatorService billCalculator;

	public List<TicketBillDTO> getRecentTickets(int rows){
		List<TicketBillDTO> ticketDTOs = new ArrayList();
		Pageable page=PageRequest.of(0, rows, Sort.by(Direction.DESC, "id"));		
		List<TicketBill> tickets=generatedTicketRepo.recentRecords(page);	
		
		tickets.forEach(ticket->{
			TicketBillDTO ticketDTO = new TicketBillDTO();
			BeanUtils.copyProperties(ticket, ticketDTO);
			ticketDTO.setGeneratedBy(ticket.getGeneratedBy().getName());
			ticketDTO.setBillSummarize(ticket.getTicketPayload()!=null?CommonUtills.convertJSONToObject(ticket.getTicketPayload(),BillSummarize.class):null);
			log.debug(ticket.toString());
			ticketDTOs.add(ticketDTO);
		});	
		return ticketDTOs;
	}
	
	
	public void saveAndPrintTicket(TicketSelectorDTO dto,Principal user) throws JsonProcessingException {
	     //GeneratedTicket	
		 List<TicketsRatesMaster> rates = ticketsRatesService.getTicketRateByGroup(dto.getTickets(), dto.getGroup());
         List<TicketBillRow> billRows = new ArrayList<>();
         Double totalPrice = 0d;
         
         TicketBill generatedTicket=new TicketBill(); 
         
         generatedTicket.setGeneratedAt(new Date());
         generatedTicket.setGeneratedBy(userDetailsService.getUserByUsername(user.getName()));
         generatedTicket.setPersons(dto.getPersons());
         generatedTicket=generatedTicketRepo.save(generatedTicket);
         
         for(TicketsRatesMaster rate:rates) {
        	 TicketBillRow billRow = new TicketBillRow();		
			 billRow.setPrice(dto.getPersons()*rate.getPrice());             
			 billRow.setRate(rate);
			 totalPrice+=billRow.getPrice();
			 billRow.setGeneratedTicket(generatedTicket);	
			 billRows.add(billRow);
         }
         BillSummarize billSummarize = billCalculator.summarizeBill(dto);         
         
         generatedTicket.setTicketPayload(CommonUtills.convertToJSON(billSummarize));         
		 generatedTicket.setPrice(totalPrice);
		 generatedTicket.setBillSummary(billRows);
		 generatedTicketRepo.save(generatedTicket);
		 
		 log.debug("generatedTicket "+CommonUtills.convertToJSON(generatedTicket));
			
	}
	

}
