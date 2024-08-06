package com.rsc.bhopal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rsc.bhopal.dtos.BillDescription;
import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;
import com.rsc.bhopal.service.BillCalculatorService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/bill")
@Slf4j
public class BillController {
	
	@Autowired
	private BillCalculatorService billCalculator;
	
	@PostMapping("/calculate")	
	public @ResponseBody List<BillDescription> calculateBill(@ModelAttribute TicketSelectorDTO ticketSelector) {
		log.debug("Ticket Selector "+ticketSelector);
		
		List<BillDescription> bills = billCalculator.summarizeBill(ticketSelector);

		//return "redirect:/home/"+billCalculator.calculateTotal(ticketSelector);

		return bills;
		
	}
	
	
}
