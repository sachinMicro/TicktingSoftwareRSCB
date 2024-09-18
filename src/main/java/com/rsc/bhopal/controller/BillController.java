package com.rsc.bhopal.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rsc.bhopal.dtos.BillSummarize;
import com.rsc.bhopal.dtos.ResponseMessage;
import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.exception.TicketRateNotMaintainedException;
import com.rsc.bhopal.service.BillCalculatorService;
import com.rsc.bhopal.service.TicketBillService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/bill")
@Slf4j
public class BillController {
	
	@Autowired
	private BillCalculatorService billCalculator;
	
	@Autowired	
	private TicketBillService ticketBillService;
	
	@PostMapping("/calculate")	
	public @ResponseBody ResponseEntity<?>  calculateBill(                  TicketSelectorDTO ticketSelector) {
		log.debug("Ticket Selector "+ticketSelector);		
		try {
			BillSummarize billSummarize = billCalculator.summarizeBill(ticketSelector);		
			return new ResponseEntity<>(ResponseMessage.builder()
                    .status(true)
                    .data(billSummarize)
                    .message("Bill Calculated")
                    .build(),HttpStatus.OK);
		}catch(TicketRateNotMaintainedException ex) {	
			ex.printStackTrace();
			return new ResponseEntity<>(ResponseMessage.builder()
                    .status(false)
                    .data(null)
                    .message(ex.getMessage())
                    .build(),HttpStatus.BAD_REQUEST);
		}catch(Exception ex) {	
			ex.printStackTrace();                                                                          
			return new ResponseEntity<>(ResponseMessage.builder()
                    .status(false)
                    .data(null)
                    .message("Some Internal Error occurred")
                    .build(),HttpStatus.BAD_REQUEST);
		}
		
		//user 9679,conf- 8051,approver 8044
		//BP Authorization 
		
	}
		
	@PostMapping("/print")	
	public String printBill(@ModelAttribute TicketSelectorDTO ticketSelector,Principal user) throws JsonProcessingException {
		log.debug("Ticket Selector "+ticketSelector);		
		ticketBillService.saveAndPrintTicket(ticketSelector,user);	
		return "redirect:/home";
		
	}
}
