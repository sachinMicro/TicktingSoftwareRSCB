package com.rsc.bhopal.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsc.bhopal.dtos.TicketDetailsDTO;
import com.rsc.bhopal.dtos.VisitorsTypeDTO;
import com.rsc.bhopal.enums.GroupType;
import com.rsc.bhopal.projections.TicketDailyReport;
import com.rsc.bhopal.repos.TicketBillRowRepository;
import com.rsc.bhopal.service.TicketDetailsService;
import com.rsc.bhopal.service.VisitorTypeService;
import com.rsc.bhopal.utills.DailyReportExcel;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/report")
public class TicketDailyReportController {
	@Autowired
	private TicketBillRowRepository ticketBillRowRepository;

	@Autowired
	private TicketDetailsService ticketDetailsService;

	@Autowired
	private VisitorTypeService visitorTypeService;

/*
	@GetMapping(path = "/year-wise/{year}")
	public List<TicketDailyReport> TicketDailyReportController(@PathVariable("year") Integer year) {
		List<TicketDailyReport> ticketDailyReports = ticketBillRowRepository.getDailyReportDetails(year);
		arrange(ticketDailyReports);
		return ticketDailyReports;
	}
*/
/*
	@GetMapping(path = "/year-wise/{year}")
	public LinkedHashMap<Date, BillDate> TicketDailyReportController(@PathVariable("year") Integer year) {
		List<TicketDailyReport> ticketDailyReports = ticketBillRowRepository.getDailyReportDetails(year);
		ticketDetailsService.getAllTickets().stream().map(TicketDetailsDTO::getName).collect(Collectors.toList()).forEach(ticketDetailDTO -> {
			log.debug(ticketDetailDTO.toString());
		});
		visitorTypeService.getAllVisitorTypes().stream().filter(visitorsTypeDTO -> GroupType.SINGLE.equals(visitorsTypeDTO.getGroupType())).map(VisitorsTypeDTO::getName).collect(Collectors.toList()).forEach(visitorTypeDTO -> {
			log.debug(visitorTypeDTO.toString());
		});

		return arrange(ticketDailyReports);
	}
*/

	@GetMapping(path = "/year-wise/{year}")
	public String TicketDailyReportController(@PathVariable("year") Short year, Map<String, Object> attributes) {
		List<TicketDailyReport> ticketDailyReports = ticketBillRowRepository.getDailyReportDetails(year);
		attributes.put("startDateTime", "");
		attributes.put("endDateTime", "");
		// attributes.put("ticketsName", ticketDetailsService.getAllTickets().stream().map(TicketDetailsDTO::getName).collect(Collectors.toList()));
		// attributes.put("visitorsName", visitorTypeService.getAllVisitorTypes().stream().filter(visitorsTypeDTO -> GroupType.SINGLE.equals(visitorsTypeDTO.getGroupType())).map(VisitorsTypeDTO::getName).collect(Collectors.toList()));
		final Map<Long, String> ticketsMap = ticketDetailsService.getAllTickets().stream().collect(Collectors.toMap(TicketDetailsDTO::getId, TicketDetailsDTO::getName));
		final Map<Long, String> visitorsMap = visitorTypeService.getAllVisitorTypes().stream().filter(visitorsTypeDTO -> GroupType.SINGLE.equals(visitorsTypeDTO.getGroupType())).collect(Collectors.toMap(VisitorsTypeDTO::getId, VisitorsTypeDTO::getName));
		attributes.put("ticketsName", ticketsMap.values().stream().collect(Collectors.toList()));
		attributes.put("visitorsName", visitorsMap.values().stream().collect(Collectors.toList()));
		double []grandTotal = new double[1];
		attributes.put("bills", arrange(ticketDailyReports, ticketsMap, visitorsMap, grandTotal));
		attributes.put("ticketSerials", new int[2]);
		attributes.put("grandTotal", grandTotal[0]);
		return "reports/date";
	}

	@PostMapping(value = "/year-wise/{year}", produces = MediaType.APPLICATION_PDF_VALUE)
	public void generateSheet(@PathVariable("year") Short year, HttpServletResponse httpServletResponse) throws IOException {
		// httpServletResponse.setContentType("application/xlsx");
		httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		// httpServletResponse.setHeader("Content-Disposition", "inline; filename=daily_report.xlxs");
		httpServletResponse.setHeader("Content-Disposition", "attachment; filename=daily_report.xlsx");
		List<TicketDailyReport> ticketDailyReports = ticketBillRowRepository.getDailyReportDetails(year);
		final Map<Long, String> ticketsMap = ticketDetailsService.getAllTickets().stream().collect(Collectors.toMap(TicketDetailsDTO::getId, TicketDetailsDTO::getName));
		final Map<Long, String> visitorsMap = visitorTypeService.getAllVisitorTypes().stream().filter(visitorsTypeDTO -> GroupType.SINGLE.equals(visitorsTypeDTO.getGroupType())).collect(Collectors.toMap(VisitorsTypeDTO::getId, VisitorsTypeDTO::getName));
		final DailyReportExcel dailyReportExcel = new DailyReportExcel(ticketDailyReports, ticketsMap, visitorsMap, httpServletResponse);
	}

	public LinkedHashMap<Date, BillDate> arrange(List<TicketDailyReport> ticketDailyReports, Map<Long, String> ticketsMap, Map<Long, String> visitorsMap, double[] grandTotal) {
		int ticketCount = 0;
		LinkedHashMap<Long, Integer> ticketIdToIndex = new LinkedHashMap<Long, Integer>();
		for (Map.Entry<Long, String> ticketMap: ticketsMap.entrySet()) {
			ticketIdToIndex.put(ticketMap.getKey(), ++ticketCount);
		}
		int visitorCount = 0;
		LinkedHashMap<Long, Integer> groupIdToIndex = new LinkedHashMap<Long, Integer>();
		for (Map.Entry<Long, String> visitorMap: visitorsMap.entrySet()) {
			groupIdToIndex.put(visitorMap.getKey(), ++visitorCount);
		}
		// log.debug("" + ticketIdToIndex + ", " + groupIdToIndex);

		LinkedHashMap<Date, BillDate> billDates = new LinkedHashMap<Date, BillDate>();
		for (TicketDailyReport ticketDailyReport: ticketDailyReports) {
			BillDate billDate = billDates.get(ticketDailyReport.getBillDate());
			if (billDate == null) {
				billDate = new BillDate();
				billDate.setDate(ticketDailyReport.getBillDate());
				billDate.setTotalTickets(ticketDailyReport.getPersons());
				billDate.setTotalAmount(Double.valueOf(ticketDailyReport.getPrice() * ticketDailyReport.getPersons()));
				billDate.setTickets(new HashMap<Long, Ticket>());

				Ticket ticket = new Ticket();
				ticket.setTicketId(ticketDailyReport.getTicketId());
				ticket.setTicketName(ticketDailyReport.getTicketName());
				ticket.setTotalQuantity(ticketDailyReport.getPersons());
				ticket.setTotalAmount(Double.valueOf(ticketDailyReport.getPrice() * ticketDailyReport.getPersons()));
				ticket.setGroups(new HashMap<Long, Group>());

				Group group = new Group();
				group.setGroupId(ticketDailyReport.getVisitorId());
				group.setGroupName(ticketDailyReport.getVisitorName());
				group.setTicketSerial(ticketDailyReport.getTicketSerial());
				group.setQuantity(ticketDailyReport.getPersons());
				group.setAmount(ticketDailyReport.getPrice() * ticketDailyReport.getPersons());
				ticket.getGroups().put(Long.valueOf(ticketDailyReport.getVisitorId()), group);

				billDate.getTickets().put(Long.valueOf(ticketIdToIndex.get(ticketDailyReport.getTicketId())), ticket);

				billDates.put(ticketDailyReport.getBillDate(), billDate);
			}
			else {
				Ticket ticket = billDate.getTickets().get(Long.valueOf(ticketIdToIndex.get(ticketDailyReport.getTicketId())));
				if (ticket == null) {
					ticket = new Ticket();
					ticket.setTicketId(ticketDailyReport.getTicketId());
					ticket.setTicketName(ticketDailyReport.getTicketName());
					ticket.setTotalQuantity(ticketDailyReport.getPersons());
					ticket.setTotalAmount(Double.valueOf(ticketDailyReport.getPrice() * ticketDailyReport.getPersons()));
					ticket.setGroups(new HashMap<Long, Group>());

					Group group = new Group();
					group.setGroupId(ticketDailyReport.getVisitorId());
					group.setGroupName(ticketDailyReport.getVisitorName());
					group.setTicketSerial(ticketDailyReport.getTicketSerial());
					group.setQuantity(ticketDailyReport.getPersons());
					group.setAmount(ticketDailyReport.getPrice() * ticketDailyReport.getPersons());
					ticket.getGroups().put(Long.valueOf(ticketDailyReport.getVisitorId()), group);

					billDate.getTickets().put(Long.valueOf(ticketIdToIndex.get(ticketDailyReport.getTicketId())), ticket);
				}
				else {
					Group group = ticket.getGroups().get(Long.valueOf(groupIdToIndex.get(ticketDailyReport.getVisitorId())));
					if (group == null) {
						group = new Group();
						group.setGroupId(ticketDailyReport.getVisitorId());
						group.setGroupName(ticketDailyReport.getVisitorName());
						group.setTicketSerial(ticketDailyReport.getTicketSerial());
					}
					else {
						group.setQuantity(group.getQuantity() + ticketDailyReport.getPersons());
						group.setAmount(group.getAmount() + (ticketDailyReport.getPrice() * ticketDailyReport.getPersons()));
					}
					ticket.setTotalQuantity(ticket.getTotalQuantity() + ticketDailyReport.getPersons());
					ticket.setTotalAmount(ticket.getTotalAmount() + (ticketDailyReport.getPrice() * ticketDailyReport.getPersons()));
				}
				billDate.setTotalTickets(billDate.getTotalTickets() + ticketDailyReport.getPersons());
				billDate.setTotalAmount(billDate.getTotalAmount() + (ticketDailyReport.getPrice() * ticketDailyReport.getPersons()));
			}
			grandTotal[0] += ticketDailyReport.getPrice() * ticketDailyReport.getPersons();
		}

		// Make object with zero values that were not present
		for (HashMap.Entry<Date, BillDate> billDate: billDates.entrySet()) {
			for (HashMap.Entry<Long, Integer> ticketMappedIndex: ticketIdToIndex.entrySet()) {
				if (billDate.getValue().getTickets().get(Long.valueOf(ticketMappedIndex.getValue())) == null) {
					Ticket ticket = new Ticket();
					ticket.setTicketName("");
					ticket.setTotalQuantity(0);
					ticket.setTotalAmount(0d);
					ticket.setGroups(new LinkedHashMap<Long, Group>());
					for (HashMap.Entry<Long, Integer> groupMappedIndex: groupIdToIndex.entrySet()) {
						Group  group = new Group();
						group.setGroupName("");
						group.setQuantity(0);
						group.setAmount(0f);
						ticket.getGroups().put(Long.valueOf(groupMappedIndex.getValue()), group);
					}
					billDate.getValue().getTickets().put(Long.valueOf(ticketMappedIndex.getValue()), ticket);
				}
				else {
					for (HashMap.Entry<Long, Integer> groupMappedIndex: groupIdToIndex.entrySet()) {
						if (billDate.getValue().getTickets().get(Long.valueOf(ticketMappedIndex.getValue())).getGroups().get(Long.valueOf(groupMappedIndex.getValue())) == null) {
							Group group = new Group();
							group.setGroupName("");
							group.setQuantity(0);
							group.setAmount(0f);
							billDate.getValue().getTickets().get(Long.valueOf(ticketMappedIndex.getValue())).getGroups().put(Long.valueOf(groupMappedIndex.getValue()), group);
						}
					}
				}
			}
		}
		return billDates;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	private class BillDate {
		private Date date;
		private HashMap<Long, Ticket> tickets;
		private Integer totalTickets;
		private Double  totalAmount;
	}
	@Data
	private class Ticket {
		private Long ticketId;
		private String ticketName;
		private HashMap<Long, Group> groups;
		private Integer totalQuantity;
		private Double totalAmount;
	}
	@Data
	private class Group {
		private Long groupId;
		private String groupName;
		private BigInteger ticketSerial;
		private Integer quantity;
		private Float amount;
	}
}
