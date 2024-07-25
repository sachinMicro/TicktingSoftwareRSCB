package com.rsc.bhopal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.service.BillCalculatorService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/bill")
@Slf4j
public class BillController {
	
	@Autowired
	private BillCalculatorService billCalculator;
	
	@GetMapping("/calculate")	
	public String calculateBill(@ModelAttribute TicketSelectorDTO ticketSelector) {
		log.debug("Ticket Selector "+ticketSelector);
		
		return "redirect:/home/"+billCalculator.calculateTotal(ticketSelector);
	}
	
	
}
