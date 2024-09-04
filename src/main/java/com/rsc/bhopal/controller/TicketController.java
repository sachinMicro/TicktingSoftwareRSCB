package com.rsc.bhopal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.rsc.bhopal.dtos.TicketBillDTO;
import com.rsc.bhopal.service.TicketBillService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/recent-tickets")
public class TicketController {
	
	@Autowired
	TicketBillService ticketBillService;
	
	@GetMapping(path = {"/{rows}","/"})
	public String recentTickets(@PathVariable(name =  "rows",required = false) Integer rows,Map<String,Object> mapAttributes) {		
		log.debug("RECENT"+rows);	
		rows=rows==null?10:rows;
		List<TicketBillDTO> generatedTickets = ticketBillService.getRecentTickets(rows);
		
		mapAttributes.put("tickets", generatedTickets);			
		return "tickets/recent";
	}

}