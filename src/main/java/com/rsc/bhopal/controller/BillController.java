package com.rsc.bhopal.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rsc.bhopal.dtos.BillSummarize;
import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.service.BillCalculatorService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/bill")
@Slf4j
public class BillController {
	
	@Autowired
	private BillCalculatorService billCalculator;
	
	@PostMapping("/calculate")	
	public @ResponseBody BillSummarize calculateBill(@ModelAttribute TicketSelectorDTO ticketSelector) {
		log.debug("Ticket Selector "+ticketSelector);		
		BillSummarize billSummarize = billCalculator.summarizeBill(ticketSelector);
		return billSummarize;		
	}
		
	@PostMapping("/print")	
	public String printBill(@ModelAttribute TicketSelectorDTO ticketSelector,Principal user) throws JsonProcessingException {
		log.debug("Ticket Selector "+ticketSelector);		
		billCalculator.saveAndPrintTicket(ticketSelector,user);	
		return "redirect:/home";
		
	}
}
