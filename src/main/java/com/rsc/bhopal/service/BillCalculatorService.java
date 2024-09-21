package com.rsc.bhopal.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rsc.bhopal.dtos.BillDescription;
import com.rsc.bhopal.dtos.BillSummarize;
import com.rsc.bhopal.dtos.ParkingBillDescription;
import com.rsc.bhopal.dtos.ParkingDetailsDTO;
import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;
import com.rsc.bhopal.dtos.VisitorsTypeDTO;
import com.rsc.bhopal.entity.TicketsRatesMaster;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BillCalculatorService {

	@Autowired
	private TicketsRatesService ticketsRatesService;

	@Autowired
	private VisitorTypeService visitorTypeService;

	@Autowired
	private ParkingService parkingService;

	public BillSummarize summarizeBill(final TicketSelectorDTO ticketSelectorDTO) {
		log.debug(ticketSelectorDTO.toString());
		BillSummarize billSummarize = new BillSummarize();
		if (ticketSelectorDTO.getFamilyGroup() == 0) {
			billSummarize.setBillDescription(summarizeTicketBill(ticketSelectorDTO));
			billSummarize.setComboCase(false);
		}
		else {
			billSummarize.setComboCase(true);
			billSummarize.setBillDescription(summarizeFamilyBill(ticketSelectorDTO.getFamilyGroup()));
		}
		// billSummarize.getParkingBillDescription();
		log.debug(""+ticketSelectorDTO);
		billSummarize.setParkingBillDescription(getParkingBillDescription(ticketSelectorDTO));
		return billSummarize;
	}

	public List<BillDescription> summarizeTicketBill(TicketSelectorDTO ticketSelectorDTO) {
		List<BillDescription> bills = new ArrayList<BillDescription>();

		for (final Long ticketId: ticketSelectorDTO.getTickets()) {
			final TicketsRatesMasterDTO ticketRate = ticketsRatesService.getTicketRateByGroup(ticketId, ticketSelectorDTO.getGroup());

			BillDescription bill = new BillDescription();

			bill.setTicket(ticketRate.getTicketType().getName());
			bill.setGroupName(ticketRate.getVisitorsType().getName());
			bill.setPerson(ticketSelectorDTO.getPersons());
			bill.setPerPersonPrice(ticketRate.getPrice());
			bill.setTotalSum(ticketSelectorDTO.getPersons() * ticketRate.getPrice());
			bills.add(bill);
		}
		return bills;
	}

	public List<BillDescription> summarizeFamilyBill(long familyGroupId){

		List<BillDescription> bills = new ArrayList<BillDescription>();

		final VisitorsTypeDTO visitorDTO = visitorTypeService.getGeneralVisitorId(familyGroupId);
		final List<Long> tickets = ticketsRatesService.getTicketsByGroup(familyGroupId);

		for (final Long ticketId: tickets) {
			TicketsRatesMasterDTO ticketRate = ticketsRatesService.getTicketRateByGroup(ticketId, familyGroupId);

			BillDescription bill = new BillDescription();

			bill.setTicket(ticketRate.getTicketType().getName());
			bill.setGroupName(ticketRate.getVisitorsType().getName());
			bill.setPerson(visitorDTO.getMinMembers());
			bill.setPerPersonPrice(ticketRate.getPrice());
			bill.setTotalSum(visitorDTO.getMinMembers()*ticketRate.getPrice());
			bills.add(bill);
		}
		return bills;
	}

	public List<ParkingBillDescription> getParkingBillDescription(TicketSelectorDTO ticketSelectorDTO) {
		List<ParkingBillDescription> billDescList = new ArrayList<ParkingBillDescription>();
		final List<TicketsRatesMasterDTO> ticketsRatesMasterDTOs = ticketsRatesService.getActiveParkingDetails();
		ticketSelectorDTO.getParkings().forEach(parking -> {
			if (parking.getCount() > 0){
				for (TicketsRatesMasterDTO ticketsRatesMasterDTO: ticketsRatesMasterDTOs) {
					if (ticketsRatesMasterDTO.getParkingDetails().getId() == parking.getId()) {
						ParkingBillDescription billDesc = new ParkingBillDescription();
						billDesc.setDesc(ticketsRatesMasterDTO.getParkingDetails().getName());
						billDesc.setCount(parking.getCount());
						billDesc.setPerCharge(ticketsRatesMasterDTO.getPrice());
						billDesc.setSum(ticketsRatesMasterDTO.getPrice() * parking.getCount());
						billDescList.add(billDesc);
					}
				}
			}
		});
		return billDescList;
	}

	public double calculateTotal(TicketSelectorDTO ticketSelectorDTO) {
		double totalAmount = 0;
		for (final Long ticketId: ticketSelectorDTO.getTickets()) {
			TicketsRatesMasterDTO ticketRate = ticketsRatesService.getTicketRateByGroup(ticketId, ticketSelectorDTO.getGroup());
			totalAmount += (ticketRate.getPrice() * ticketSelectorDTO.getPersons());
		}
		return totalAmount;
	}
}
