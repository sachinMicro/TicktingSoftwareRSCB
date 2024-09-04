package com.rsc.bhopal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rsc.bhopal.dtos.NewTicketRate;
import com.rsc.bhopal.dtos.ResponseMessage;
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

	public ResponseEntity<ResponseMessage> updatePrice(NewTicketRate newTicketRate){
		TicketsRatesMaster newRateMaster = new TicketsRatesMaster();
		TicketsRatesMaster rateMaster = ticketRateRepo.findByGroupAndTicketIds(newTicketRate.getTicketId(), newTicketRate.getGroupId());

		ResponseMessage responseMessage = new ResponseMessage();

		try {
			if (rateMaster.getIsActive() && rateMaster.getPrice() != newTicketRate.getPrice()) {
				rateMaster.setIsActive(false);
				rateMaster = ticketRateRepo.save(rateMaster);

				BeanUtils.copyProperties(rateMaster, newRateMaster);
				newRateMaster.setId(null);
				log.debug("Set new price: " + newTicketRate.getPrice() + " for Ticket ID: " + newTicketRate.getTicketId(), ", Visitor ID: " + newTicketRate.getGroupId() + "and Group ID");
				newRateMaster.setPrice(newTicketRate.getPrice());
				newRateMaster.setIsActive(true);
				newRateMaster.setRevisedAt(java.util.Date.from(java.time.Instant.now()));
				newRateMaster.setRevisionNo(rateMaster.getRevisionNo() == null ? 1 : rateMaster.getRevisionNo() + 1);
				// log.debug(rateMaster.getUser().getName());
				// newRateMaster.setUser(rateMaster.getUser());
				newRateMaster.setUser(rateMaster.getUser());
				ticketRateRepo.save(newRateMaster);

				// return "Price changed";
				responseMessage.setStatus(true);
				responseMessage.setMessage("Price successfully updated");
				return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
			}
		}
		catch (NullPointerException ex) {
			log.debug(ex.getMessage());

			// return "Tables inconsistant.";
			responseMessage.setStatus(false);
			responseMessage.setMessage("Table inconsistant");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		catch (Exception ex) {
			log.debug(ex.getMessage());

			// return ex.getMessage();
			responseMessage.setStatus(false);
			responseMessage.setMessage(ex.getMessage());
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}

		// return "";
		responseMessage.setStatus(false);
		responseMessage.setMessage("");
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}
}
