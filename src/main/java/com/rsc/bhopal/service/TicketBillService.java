package com.rsc.bhopal.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rsc.bhopal.dtos.BillSummarize;
import com.rsc.bhopal.dtos.ParkingCalDTO;
import com.rsc.bhopal.dtos.TicketBillDTO;
import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.dtos.TicketSummaryDTO;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;
import com.rsc.bhopal.entity.TicketBill;
import com.rsc.bhopal.entity.TicketBillRow;
import com.rsc.bhopal.entity.TicketsRatesMaster;
import com.rsc.bhopal.entity.VisitorsType;
import com.rsc.bhopal.exception.TicketRateNotMaintainedException;
import com.rsc.bhopal.projections.TicketSummary;
import com.rsc.bhopal.repos.TicketBillRepository;
import com.rsc.bhopal.utills.CommonUtills;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class TicketBillService {

	@Autowired
	TicketBillRepository generatedTicketRepo;

	@Autowired
	private TicketsRatesService ticketsRatesService;

	@Autowired
	private RSCUserDetailsService userDetailsService;

	@Autowired
	private VisitorTypeService visitorTypeService;

	@Autowired
	private BillCalculatorService billCalculator;

	public List<TicketBillDTO> getRecentTickets(int rows){
		List<TicketBillDTO> ticketDTOs = new ArrayList<TicketBillDTO>();
		Pageable page=PageRequest.of(0, rows, Sort.by(Direction.DESC, "id"));
		List<TicketBill> tickets=generatedTicketRepo.recentRecords(page);
		tickets.forEach(ticket->{
			TicketBillDTO ticketDTO = new TicketBillDTO();
			BeanUtils.copyProperties(ticket, ticketDTO);
			ticketDTO.setGeneratedBy(ticket.getGeneratedBy().getName());
			ticketDTO.setBillSummarize(ticket.getTicketPayload()!=null?CommonUtills.convertJSONToObject(ticket.getTicketPayload(),BillSummarize.class):null);
			log.debug(ticket.toString());
			ticketDTOs.add(ticketDTO);
		});
		return ticketDTOs;
	}

	@Transactional
	public void saveAndPrintTicket(TicketSelectorDTO dto,Principal user) throws JsonProcessingException {

		Optional<VisitorsType> comboGroup = null;
		List<TicketBillRow> billRows = new ArrayList<TicketBillRow>();
		Double totalPrice = 0d;
		final boolean IS_COMBO_CASE=dto.getFamilyGroup() !=0 ? true : false;

		if (!IS_COMBO_CASE && dto.getTickets().size() < 1) {
			throw new TicketRateNotMaintainedException("No selected tickets received by server.");
		}
		TicketBill generatedTicket=new TicketBill();
		generatedTicket.setGeneratedAt(new Date());
		generatedTicket.setInstitution(dto.getInstitution());
		generatedTicket.setRemark(dto.getRemark());
		generatedTicket.setGeneratedBy(userDetailsService.getUserByUsername(user.getName()));
		// Generated Ticket
		List<TicketsRatesMaster> rates = null;
		if (!IS_COMBO_CASE) {
			rates=ticketsRatesService.getTicketRateByGroup(dto.getTickets(), dto.getGroup());			
			generatedTicket.setPersons(dto.getPersons());
		}
		else {
			log.debug("=======COMOBO CASE===================");
			comboGroup=visitorTypeService.getVisitorById(dto.getFamilyGroup());
			rates=ticketsRatesService.getRatesOfCombo(dto.getFamilyGroup());
			if(rates==null) {
				throw new TicketRateNotMaintainedException("Ticket Rate is not maintained for Combo Group.");
			}
			generatedTicket.setPersons(comboGroup.get().getFixedMembers());
			dto.setPersons(generatedTicket.getPersons());
		}
		
		
		generatedTicket=generatedTicketRepo.save(generatedTicket);

		for(TicketsRatesMaster rate: rates) {
			TicketBillRow billRow = new TicketBillRow();
			if (!IS_COMBO_CASE){
				billRow.setTotalSum(dto.getPersons()*rate.getPrice());
				totalPrice+=billRow.getTotalSum();
			}
			else {
				billRow.setTotalSum(rate.getPrice());
				totalPrice+=billRow.getTotalSum();
			}
			billRow.setRate(rate);
			billRow.setGeneratedTicket(generatedTicket);
			billRows.add(billRow);
		}
		
		if(dto.getParkings()!=null) {
			for( ParkingCalDTO parking :  dto.getParkings()){
				TicketBillRow billRow = new TicketBillRow();
				final TicketsRatesMaster rate = ticketsRatesService.getActiveParkingRateFloat(parking.getId());
				log.debug("Parking Rate: " + rate.getPrice());
				billRow.setTotalSum(parking.getCount() * rate.getPrice());
				totalPrice += billRow.getTotalSum();
				billRow.setGeneratedTicket(generatedTicket);
				billRow.setRate(rate);
				billRows.add(billRow);
			}
		}

		BillSummarize billSummarize = billCalculator.summarizeBill(dto);
		generatedTicket.setTicketPayload(CommonUtills.convertToJSON(billSummarize));
		generatedTicket.setTotalBill(totalPrice);
		generatedTicket.setBillSummary(billRows);
		generatedTicketRepo.save(generatedTicket);
		log.debug("generatedTicket "+CommonUtills.convertToJSON(generatedTicket));
	}

/*
	public List<TicketSummary> getTicketSummary(String startDate, String endDate){
		return generatedTicketRepo.getTicketSummary(startDate, endDate);
	}
	public List<TicketSummaryDTO> getTicketSummaryDTOs(String startDate, String endDate) {
		List<TicketSummaryDTO> ticketSummaryDTOs = new ArrayList<TicketSummaryDTO>();
		generatedTicketRepo.getTicketSummary(startDate, endDate).forEach(ticketSummary -> {
			TicketSummaryDTO ticketSummaryDTO = new TicketSummaryDTO();
			BeanUtils.copyProperties(ticketSummary, ticketSummaryDTO);
			ticketSummaryDTOs.add(ticketSummaryDTO);
		});
		return ticketSummaryDTOs;
	}
*/
}
