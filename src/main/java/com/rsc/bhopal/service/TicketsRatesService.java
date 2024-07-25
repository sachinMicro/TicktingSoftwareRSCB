package com.rsc.bhopal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;
import com.rsc.bhopal.entity.TicketsRatesMaster;
import com.rsc.bhopal.repos.TicketsRatesMasterRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TicketsRatesService {

	
	@Autowired
	private TicketsRatesMasterRepository ticketRateRepo;
	
	public TicketsRatesMasterDTO getTicketRateByGroup(long ticketId,long groupId){
		TicketsRatesMaster ticketRate = ticketRateRepo.findByGroupAndTicketIds(ticketId,groupId);
		TicketsRatesMasterDTO ticketRatesDTO = new TicketsRatesMasterDTO();
		BeanUtils.copyProperties(ticketRate, ticketRatesDTO);
		return ticketRatesDTO;
	}
	
	
	public List<TicketsRatesMasterDTO> getAllTicketRates(){
		List<TicketsRatesMasterDTO> ticketRatesDTOs = new ArrayList<>(); 
		List<TicketsRatesMaster> ticketRates = ticketRateRepo.findAll();
		
		  for(TicketsRatesMaster ticketRate : ticketRates ) {
			  TicketsRatesMasterDTO ticketRateDTO = new TicketsRatesMasterDTO();
			  
			  log.debug("TICKET {} "+ticketRate);
			  
			  BeanUtils.copyProperties(ticketRate, ticketRateDTO);
			  ticketRatesDTOs.add(ticketRateDTO);
		  }	 
		  
		return ticketRatesDTOs;
	}
	
}
