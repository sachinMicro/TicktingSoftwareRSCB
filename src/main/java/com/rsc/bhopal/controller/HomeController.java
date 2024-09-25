package com.rsc.bhopal.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsc.bhopal.dtos.TicketBillDTO;
import com.rsc.bhopal.dtos.TicketDetailsDTO;
import com.rsc.bhopal.dtos.VisitorsTypeDTO;
import com.rsc.bhopal.enums.GroupType;
import com.rsc.bhopal.enums.VisitorsCategoryEnum;
import com.rsc.bhopal.service.ParkingService;
import com.rsc.bhopal.service.TicketBillService;
import com.rsc.bhopal.service.TicketDetailsService;
import com.rsc.bhopal.service.VisitorTypeService;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private TicketDetailsService ticketDetails;

	@Autowired
	private VisitorTypeService visitorDetails;

	@Autowired
	private ParkingService parkingService;

	@GetMapping(path = {"","/{variable}"} )
	public String hello(Map<String, Object> attributes) {
		List<TicketDetailsDTO> tickets = ticketDetails.getAllActiveTickets();
		List<VisitorsTypeDTO> visitors = visitorDetails.getAllActiveVisitorTypes();
		String redirectString = "";
		if(tickets.size() == 0 || visitors.size() == 0) {
			if(tickets.size() == 0) {
				redirectString="redirect:/manage/tickets/add";
			}
			else if(visitors.size() == 0) {
				redirectString="redirect:/manage/groups/add";
			}
		}
		else {
			// attributes.put("tickets1", tickets.subList(0, 8));
			// attributes.put("tickets2", tickets.subList(8, tickets.size()));

			// float ticketGroups = (float) tickets.size() / 8f;
			// attributes.put("ticketGroups", ticketGroups);

			attributes.put("tickets", tickets);

			attributes.put("groups", visitors.stream().filter(visitorType->
				GroupType.SINGLE.equals(visitorType.getGroupType())
				).collect(Collectors.toList()));
			
			visitors.forEach(visitorType -> {
				if (GroupType.SINGLE.equals(visitorType.getGroupType()) && visitorType.getIsDefault()) {
					attributes.put("defaultpersonvalue", visitorType.getMinMembers());
				}
			});

			attributes.put("familyGroups", visitors.stream().filter(visitorType->
				GroupType.COMBO.equals(visitorType.getGroupType())
				).collect(Collectors.toList()));

			attributes.put("parkings", parkingService.getParkingDetails());
			redirectString = "employee/home";
		}
		return redirectString;
	}
}
