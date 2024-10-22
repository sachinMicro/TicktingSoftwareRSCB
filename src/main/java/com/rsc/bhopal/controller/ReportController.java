package com.rsc.bhopal.controller;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rsc.bhopal.dtos.TicketBillRowDTO;
import com.rsc.bhopal.dtos.VisitorsTypeDTO;
import com.rsc.bhopal.enums.GroupType;
import com.rsc.bhopal.service.TicketBillRowService;
import com.rsc.bhopal.service.VisitorTypeService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequestMapping("/report")
public class ReportController {
	@Autowired
	private VisitorTypeService visitorTypeService;

	@Autowired
	private TicketBillRowService ticketBillRowService;

	private double grandTotal;

	@GetMapping(path = "/daily")
	public String getReport(Map<String, Object> attributes) {
		final List<String> visitorsColumn = visitorTypeService.getAllVisitorTypes().stream().filter(visitorsTypeDTO -> GroupType.SINGLE.equals(visitorsTypeDTO.getGroupType())).map(VisitorsTypeDTO::getName).collect(Collectors.toList());
		attributes.put("visitorsName", visitorsColumn);

		attributes.put("startDateTime", "");
		attributes.put("endDateTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

		grandTotal = 0;
		final List<Long> visitorsId = visitorTypeService.getAllVisitorTypes().stream().filter(visitorsTypeDTO -> GroupType.SINGLE.equals(visitorsTypeDTO.getGroupType())).map(VisitorsTypeDTO::getId).collect(Collectors.toList());
		final HashMap<Long, Ticket> reportTable = arrangeDataInTable(visitorsId, ticketBillRowService.getTicketBillRows());
		attributes.put("reportTable", reportTable);
		attributes.put("grandTotal", grandTotal);

		return "reports/daily";
	}

	@PostMapping(path = "/daily")
	public String postReport(@RequestParam String startDateTime, @RequestParam String endDateTime, Map<String, Object> attributes) {
		final List<String> visitorsColumn = visitorTypeService.getAllVisitorTypes().stream().filter(visitorsTypeDTO -> GroupType.SINGLE.equals(visitorsTypeDTO.getGroupType())).map(VisitorsTypeDTO::getName).collect(Collectors.toList());
		attributes.put("visitorsName", visitorsColumn);

		attributes.put("startDateTime", startDateTime);
		attributes.put("endDateTime", endDateTime);

		/*
		Date formattedStarDateTime = null;
		Date formattedEndDateTime = null;
		try {
			formattedStarDateTime = new SimpleDateFormat("dd-MM-yyyy").parse(startDateTime);
			formattedEndDateTime = new SimpleDateFormat("dd-MM--yyyy").parse(endDateTime);
		}
		catch(ParseException ex) {
			log.debug("Exception in parsing Date Time");
		}
		*/

		/*
		LocalDateTime formattedStarDateTime = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		LocalDateTime formattedEndDateTime = LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		ticketBillRowService.getTicketBillRowsAtDateTime(formattedStarDateTime, formattedStarDateTime);
		*/

		Date formattedStartDateTime = null;
		Date formattedEndDateTime = null;
		try {
			formattedStartDateTime = new SimpleDateFormat("yyyy-MM-dd").parse(startDateTime);
			formattedEndDateTime = new SimpleDateFormat("yyyy-MM-dd").parse(endDateTime);
		}
		catch(ParseException ex) {
			log.debug("Exception in parsing Date Time");
		}

		grandTotal = 0;
		final List<Long> visitorsId = visitorTypeService.getAllVisitorTypes().stream().filter(visitorsTypeDTO -> GroupType.SINGLE.equals(visitorsTypeDTO.getGroupType())).map(VisitorsTypeDTO::getId).collect(Collectors.toList());
		final HashMap<Long, Ticket> reportTable = arrangeDataInTable(visitorsId, ticketBillRowService.getTicketBillRowsAtDateTime(formattedStartDateTime, formattedEndDateTime));
		attributes.put("reportTable", reportTable);
		attributes.put("grandTotal", grandTotal);

		return "reports/daily";
	}

	/*
	@GetMapping(path = "/billrow")
	public @ResponseBody List<TicketBillRowDTO> getAllTicketsBillRow() {
		return ticketBillRowService.getAllTicketsBillRow();
	}
	*/

	/*
	@GetMapping(path = "/bill")
	public @ResponseBody List<TicketBillDTO> getAllTicketsBill() {
		return ticketBillService.getAllTicketsBill();
	}
	*/

	private HashMap<Long, Ticket> arrangeDataInTable(List<Long> visitorIds, List<TicketBillRowDTO> ticketBillRowDTOs) {
		HashMap<Long, Integer> visitorIdToIndex = new HashMap<Long, Integer>();
		for (int index = 0; index < visitorIds.size(); ++index) {
			visitorIdToIndex.put(visitorIds.get(index), index);
		}
		// log.debug("Mapping: " + visitorIdToIndex);

		HashMap<Long, Ticket> tickets = new HashMap<Long, Ticket>();
		for (final TicketBillRowDTO ticketBillRowDTO: ticketBillRowDTOs) {
			// if (ticketBillRowDTO.getTicketsRatesMasterDTO().getBillType() != BillType.TICKET)
				// continue;

			Ticket ticket = tickets.get(ticketBillRowDTO.getTicketsRatesMasterDTO().getTicketType().getId());
			if (ticket == null) {
				ticket = new Ticket();
				ticket.setTicketId(ticketBillRowDTO.getTicketsRatesMasterDTO().getTicketType().getId());
				ticket.setTicketName(ticketBillRowDTO.getTicketsRatesMasterDTO().getTicketType().getName());
				ticket.setGroup(new HashMap<Long, Group>());

				Group group = new Group();
				group.setGroupId(ticketBillRowDTO.getTicketsRatesMasterDTO().getVisitorsType().getId());
				group.setGroupName(ticketBillRowDTO.getTicketsRatesMasterDTO().getVisitorsType().getName());
				group.setCount(ticketBillRowDTO.getTicketBillDTO().getPersons());
				group.setPrice(ticketBillRowDTO.getTicketsRatesMasterDTO().getPrice());
				group.setPersonCount(ticketBillRowDTO.getTicketBillDTO().getPersons());
				group.setTicketSerial(ticketBillRowDTO.getTicketBillDTO().getBillSummarize().getTicketSerial());
				ticket.getGroup().put(Long.valueOf(visitorIdToIndex.get(ticketBillRowDTO.getTicketsRatesMasterDTO().getVisitorsType().getId())), group);

				ticket.setSubTotal(ticketBillRowDTO.getTicketBillDTO().getPersons() * ticketBillRowDTO.getTicketsRatesMasterDTO().getPrice());

				tickets.put(ticketBillRowDTO.getTicketsRatesMasterDTO().getTicketType().getId(), ticket);

				grandTotal += ticketBillRowDTO.getTicketBillDTO().getPersons() * group.getPrice();
			}
			else {
				Group groups = ticket.getGroup().get(Long.valueOf(visitorIdToIndex.get(ticketBillRowDTO.getTicketsRatesMasterDTO().getVisitorsType().getId())));
				if (groups == null) {
					Group group = new Group();
					group.setGroupId(ticketBillRowDTO.getTicketsRatesMasterDTO().getVisitorsType().getId());
					group.setGroupName(ticketBillRowDTO.getTicketsRatesMasterDTO().getVisitorsType().getName());
					group.setCount(ticketBillRowDTO.getTicketBillDTO().getPersons());
					group.setPrice(ticketBillRowDTO.getTicketsRatesMasterDTO().getPrice());
					group.setPersonCount(ticketBillRowDTO.getTicketBillDTO().getPersons());
					group.setTicketSerial(ticketBillRowDTO.getTicketBillDTO().getBillSummarize().getTicketSerial());

					ticket.getGroup().put(Long.valueOf(visitorIdToIndex.get(ticketBillRowDTO.getTicketsRatesMasterDTO().getVisitorsType().getId())), group);
					ticket.setSubTotal(ticket.getSubTotal() + (ticketBillRowDTO.getTicketBillDTO().getPersons() * ticketBillRowDTO.getTicketsRatesMasterDTO().getPrice()));

					grandTotal += ticketBillRowDTO.getTicketBillDTO().getPersons() * group.getPrice();
				}
				else {
					groups.setCount(groups.getCount() + ticketBillRowDTO.getTicketBillDTO().getPersons());
					ticket.setSubTotal(ticket.getSubTotal() + (ticketBillRowDTO.getTicketBillDTO().getPersons() * ticketBillRowDTO.getTicketsRatesMasterDTO().getPrice()));

					grandTotal += ticketBillRowDTO.getTicketBillDTO().getPersons() * ticketBillRowDTO.getTicketsRatesMasterDTO().getPrice();
				}
			}
		}

		// Make object with zero values that were not present
		for (HashMap.Entry<Long, Ticket> ticket: tickets.entrySet()) {
			for (HashMap.Entry<Long, Integer> mappedIndex: visitorIdToIndex.entrySet()) {
				if (ticket.getValue().getGroup().get(Long.valueOf(mappedIndex.getValue())) == null) {
					Group group = new Group();
					group.setGroupName("");
					group.setCount(0);
					group.setPrice(0f);
					group.setPersonCount(0);
					ticket.getValue().getGroup().put(Long.valueOf(mappedIndex.getValue()), group);
				}
			}
		}
		return tickets;
	}

	@Data
	private class Ticket {
		private Long ticketId;
		private String ticketName;

		private HashMap<Long, Group> group;
		private double subTotal;
	}

	@Data
	private class Group {
		private Long groupId;
		private String groupName;
		private Integer personCount;
		private Integer count;
		private Float price;
		private BigInteger ticketSerial;
	}
}
