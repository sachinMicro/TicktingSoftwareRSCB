package com.rsc.bhopal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsc.bhopal.dtos.TicketDetailsDTO;
import com.rsc.bhopal.entity.TicketDetails;
import com.rsc.bhopal.repos.TicketDetailsRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketDetailsService {
	 
  @Autowired
  private TicketDetailsRepository ticketRepo;
  
  
  @Transactional
  public void addTickets(){	  
	  List<TicketDetails> ticketDetails = Arrays.asList
			   (new TicketDetails("Museum"),
				new TicketDetails("Taramandal"),
				new TicketDetails("3D Ticket"),
				new TicketDetails("Science Centre + Taramandal"),
				new TicketDetails("Science Centre + 3D"),
				new TicketDetails("Science Centre + Taramandal  + 3D"),
				new TicketDetails("SDL/Film Show")); 
	  ticketDetails.forEach(System.out::println);
	  ticketRepo.saveAll(ticketDetails);		  
  }
  
  public List<TicketDetailsDTO> getAllTickets(){
	  List<TicketDetails> tickets = ticketRepo.findAll();
	  List<TicketDetailsDTO> ticketsDTOs = new ArrayList<TicketDetailsDTO>();
	  
	  for(TicketDetails ticket : tickets ) {
		  TicketDetailsDTO ticketDTO = new TicketDetailsDTO();
		  BeanUtils.copyProperties(ticket, ticketDTO);
		  ticketsDTOs.add(ticketDTO);
	  }	  
	  return ticketsDTOs;
  }

}
