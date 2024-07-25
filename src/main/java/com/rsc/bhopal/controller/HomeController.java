package com.rsc.bhopal.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsc.bhopal.dtos.TicketDetailsDTO;
import com.rsc.bhopal.dtos.VisitorsTypeDTO;
import com.rsc.bhopal.enums.VisitorsTypeEnum;
import com.rsc.bhopal.service.TicketDetailsService;
import com.rsc.bhopal.service.VisitorTypeService;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private TicketDetailsService ticketDetails;
	
	@Autowired
	private VisitorTypeService visitorDetails;
	
	@GetMapping(path = {"","/{variable}"} )
	public String hello(Map<String, Object> attributes) {
		
		List<TicketDetailsDTO> tickets = ticketDetails.getAllTickets();
		List<VisitorsTypeDTO> visitors = visitorDetails.getAllVisitorTypes();
		
		attributes.put("tickets", tickets);
		attributes.put("groups", visitors.stream().filter(visitorType->
		VisitorsTypeEnum.GROUP.equals(visitorType.getType())||VisitorsTypeEnum.SCHOOL.equals(visitorType.getType())
		).collect(Collectors.toList()));
		attributes.put("familyGroups", visitors.stream().filter(visitorType->
		VisitorsTypeEnum.FAMILY.equals(visitorType.getType())
		).collect(Collectors.toList()));
		
		attributes.put("generalVistor", visitors.stream().filter(visitorType->
		VisitorsTypeEnum.GENERAL.equals(visitorType.getType())
		).findFirst().get());
		
		
		return "employee/home";
	}

}
