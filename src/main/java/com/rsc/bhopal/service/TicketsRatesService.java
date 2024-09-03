package com.rsc.bhopal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsc.bhopal.dtos.TicketDetailsDTO;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;
import com.rsc.bhopal.entity.TicketDetails;
import com.rsc.bhopal.entity.TicketsRatesMaster;
import com.rsc.bhopal.entity.VisitorsType;
import com.rsc.bhopal.repos.TicketsRatesMasterRepository;
import com.rsc.bhopal.repos.VisitorTypeRepository;

import com.rsc.bhopal.dtos.VisitorsTypeDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TicketsRatesService {

	@Autowired
	private TicketsRatesMasterRepository ticketRateRepo;
	
	@Autowired
	private VisitorTypeRepository vistorsTypeRepo;

	
	public List<TicketsRatesMaster> getTicketRateByGroup(List<Long> tickets, long groupId){
		 List<TicketsRatesMaster> rates = new ArrayList<>();
		 tickets.forEach(ticket->{
			 rates.add(ticketRateRepo.findByGroupAndTicketIds(ticket, groupId));
		 });		 
		 return rates;
    }
	
	
	public List<Long> getTicketsByGroup(long familyGroupId){
		return ticketRateRepo.getTicketsByGroup(familyGroupId);
	}
	
	public TicketsRatesMasterDTO getTicketRateByGroup(long ticketId, long groupId) {
		TicketsRatesMaster ticketRate = ticketRateRepo.findByGroupAndTicketIds(ticketId, groupId);
		TicketsRatesMasterDTO ticketRatesDTO = new TicketsRatesMasterDTO();

		TicketDetails ticketDetails = ticketRate.getTicketType();
		TicketDetailsDTO ticketDetailsDTO = new TicketDetailsDTO();
		log.debug("TICKET {} " + ticketRate);
		BeanUtils.copyProperties(ticketDetails, ticketDetailsDTO);
		ticketRatesDTO.setTicketType(ticketDetailsDTO);

		VisitorsType visitorsType = ticketRate.getVisitorsType();
		VisitorsTypeDTO visitorsTypeDTO = new VisitorsTypeDTO();
		BeanUtils.copyProperties(visitorsType, visitorsTypeDTO);
		ticketRatesDTO.setVisitorsType(visitorsTypeDTO);

		BeanUtils.copyProperties(ticketRate, ticketRatesDTO);
		return ticketRatesDTO;
	}

	public List<TicketsRatesMasterDTO> getAllTicketRates() {
		List<TicketsRatesMasterDTO> ticketRatesDTOs = new ArrayList<>();
		List<TicketsRatesMaster> ticketRates = ticketRateRepo.findAll();

		for (TicketsRatesMaster ticketRate : ticketRates) {
			TicketsRatesMasterDTO ticketRateDTO = new TicketsRatesMasterDTO();

			TicketDetails ticketDetails = ticketRate.getTicketType();
			TicketDetailsDTO ticketDetailsDTO = new TicketDetailsDTO();
			log.debug("TICKET {} " + ticketRate);
			BeanUtils.copyProperties(ticketDetails, ticketDetailsDTO);
			ticketRateDTO.setTicketType(ticketDetailsDTO);

			VisitorsType visitorsType = ticketRate.getVisitorsType();
			VisitorsTypeDTO visitorsTypeDTO = new VisitorsTypeDTO();
			BeanUtils.copyProperties(visitorsType, visitorsTypeDTO);
			ticketRateDTO.setVisitorsType(visitorsTypeDTO);

			BeanUtils.copyProperties(ticketRate, ticketRateDTO);
			ticketRatesDTOs.add(ticketRateDTO);
		}

		return ticketRatesDTOs;
	}

	public List<TicketsRatesMaster> getTicketsRatesMasters() {
		return ticketRateRepo.findAll();
	}
}
