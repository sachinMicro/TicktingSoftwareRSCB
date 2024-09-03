package com.rsc.bhopal.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rsc.bhopal.dtos.NewTicketRate;
import com.rsc.bhopal.dtos.RSCUserDTO;
import com.rsc.bhopal.dtos.TicketDetailsDTO;
import com.rsc.bhopal.dtos.TicketRate;
import com.rsc.bhopal.dtos.TicketRateByGroup;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;
import com.rsc.bhopal.dtos.UserRoleDTO;
import com.rsc.bhopal.dtos.VisitorsTypeDTO;
import com.rsc.bhopal.service.RSCUserDetailsService;
import com.rsc.bhopal.service.TicketDetailsService;
import com.rsc.bhopal.service.TicketsRatesService;
import com.rsc.bhopal.service.VisitorTypeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	@Autowired
	private TicketsRatesService ticketsRatesMasterService;

	@Autowired
	private TicketDetailsService ticketDetailsService;

	@Autowired
	private VisitorTypeService visitorTypeService;

	@Autowired
	private TicketsRatesService ticketsRatesService;

	@Autowired
	private RSCUserDetailsService userDetailsService;
	
	
	@GetMapping("/rates")
	public String rates(Map<String, Object> attributes) {

		TicketRateByGroup ticketRateByGroup = new TicketRateByGroup();

		List<VisitorsTypeDTO> vistoryList = visitorTypeService.getAllVisitorTypes()
				.stream()
				.filter(dto ->
				// !dto.getType().equals(VisitorsTypeEnum.FAMILY)&&
				// !dto.getType().equals(VisitorsTypeEnum.SPONCERED)&&
				// !dto.getType().equals(VisitorsTypeEnum.SPECIAL)&&
				// !dto.getType().equals(VisitorsTypeEnum.OTHER)
				true
				).collect(Collectors.toList());

		List<TicketDetailsDTO> tickets = ticketDetailsService.getAllTickets();

		for(TicketDetailsDTO ticket: tickets ){
			Map<Long, Float> prices = new HashMap<Long, Float>();
			TicketRate rate = new TicketRate();
			rate.setTicketId(ticket.getId());
			rate.setTicketName(ticket.getName());


			vistoryList.forEach(visitor->{
				try{
					TicketsRatesMasterDTO ticketRateMaster = ticketsRatesMasterService.getTicketRateByGroup(ticket.getId(), visitor.getId());
					prices.put(visitor.getId(), ticketRateMaster.getPrice());
				}
				catch(NullPointerException ex) {
					log.debug(ex.getMessage());
					prices.put(visitor.getId(), 0f);
				}
			});

			rate.setPrices(prices);
			ticketRateByGroup.getTicketRates().add(rate);
		}
		ticketRateByGroup.setGroups(vistoryList.stream().map(VisitorsTypeDTO::getName).collect(Collectors.toList()));
		attributes.put("ticketRates", ticketRateByGroup);
		return "admin/rates";
	}

	
	
	@PostMapping("/rates/update")
	public @ResponseBody String updateNewRates(@ModelAttribute NewTicketRate newTicketRate, Principal user) {
		log.debug(user.getName());
		log.debug(newTicketRate.toString());		
		//return newTicketRate.toString();
		return "success";
	}
	
	@GetMapping("/users")
	public String showUsers(Map<String, Object> attributes) {
		List<UserRoleDTO> roles = userDetailsService.getAllRoles();		
		List<RSCUserDTO> users = userDetailsService.getAllUser();		
		attributes.put("users", users);
		attributes.put("roles", roles);
		return "admin/users";
	}
	
	@PostMapping("/users/add")
	public String addUsers(@ModelAttribute RSCUserDTO user) {
	    log.debug(user.toString());	    
	    userDetailsService.addUser(user);
		return "redirect:/admin/users";
	}
	
	@PostMapping("/users/status")
	public String changeStatus(@RequestParam("username") String username) {
	    userDetailsService.changeUserStatus(username);	    
		return "redirect:/admin/users";
	}
	
	@PostMapping("/users/password")
	public String changePassword(@RequestParam("username") String username,@RequestParam("password") String password) {
	    userDetailsService.changeUserPassword(username,password);	    
		return "redirect:/admin/users";
	}
}
