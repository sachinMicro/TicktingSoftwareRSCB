package com.rsc.bhopal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsc.bhopal.dtos.GeneratedTicketDTO;
import com.rsc.bhopal.service.GeneratedTicketService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/tickets")
public class TicketController {
	
	@Autowired
	GeneratedTicketService generatedTicketService;
	
	@GetMapping("/recent/{rows}")
	public String recentTickets(@PathVariable("rows") Integer rows,Map<String,Object> mapAttributes) {		
		log.debug("RECENT"+rows);				
		List<GeneratedTicketDTO> generatedTickets = generatedTicketService.getRecentTickets(rows);		
		mapAttributes.put("tickets", generatedTickets);			
		return "tickets/recent";
	}

}
