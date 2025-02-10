package com.rsc.bhopal.utills;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rsc.bhopal.enums.GroupType;
import com.rsc.bhopal.projections.TicketDailyReport;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DailyReportExcel {
	public DailyReportExcel(List<TicketDailyReport> ticketDailyReports, Map<Long, String> ticketsMap, Map<Long, String> groupsSingleMap, Map<Long, String> groupsComboMap, HttpServletResponse httpServletResponse) throws IOException {
		double []grandTotal = new double[1];
		LinkedHashMap<Date, BillDate> billDates = arrange(ticketDailyReports, ticketsMap, groupsSingleMap, groupsComboMap, grandTotal);
		List<String> ticketsName = ticketsMap.values().stream().collect(Collectors.toList());
		List<String> groupsName = groupsSingleMap.values().stream().collect(Collectors.toList());
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Daily Report");
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// Header
		int columnCount = 0;
		Row row0 = sheet.createRow(0);
		Cell cell0 = row0.createCell(columnCount++);
		cell0.setCellValue("Serial");
		cell0.setCellStyle(cellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, columnCount - 1, columnCount - 1));
		Cell cell1 = row0.createCell(columnCount++);
		cell1.setCellValue("Date");
		cell1.setCellStyle(cellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, columnCount - 1, columnCount - 1));
		for (String ticketName: ticketsName) {
			// for (String groupName: groupsName) {
				Cell cell = row0.createCell(columnCount);
				cell.setCellValue(ticketName);
				cell.setCellStyle(cellStyle);
			// }
			// sheet.addMergedRegion(new CellRangeAddress(0, 0, columnCount - groupsName.size() - 1, columnCount - 1));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, columnCount, columnCount - 1 + groupsName.size()));
			columnCount += groupsName.size();
		}
		Cell cell_0 = row0.createCell(columnCount++);
		cell_0.setCellValue("Amount");
		cell_0.setCellStyle(cellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, columnCount - 1, columnCount - 2 + ticketsName.size()));
		columnCount += ticketsName.size();
		Cell cell_1_ = row0.createCell(columnCount - 1);
		cell_1_.setCellValue("Grand Total");
		cell_1_.setCellStyle(cellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, columnCount - 1, columnCount - 1));
		columnCount = 1;
		Row row1 = sheet.createRow(1);
		for (String ticketName: ticketsName) {
			for (String groupName: groupsName) {
				Cell cell = row1.createCell(++columnCount);
				cell.setCellValue(groupName);
				cell.setCellStyle(cellStyle);
			}
		}
		for (String ticketName: ticketsName) {
			Cell cell = row1.createCell(++columnCount);
			cell.setCellValue(ticketName);
			cell.setCellStyle(cellStyle);
		}
		// Data
		int rowCount = 2;
		for (HashMap.Entry<Date, BillDate> billDate: billDates.entrySet()) {
			columnCount = 0;
			Row row = sheet.createRow(rowCount);
			Cell cell00 = row.createCell(columnCount++);
			cell00.setCellValue(rowCount - 1);
			Cell cell01 = row.createCell(columnCount++);
			cell01.setCellValue(billDate.getKey().toString());
			for (HashMap.Entry<Long, Ticket> ticket: billDate.getValue().getTickets().entrySet()) {
				for (HashMap.Entry<Long, Group> group: ticket.getValue().getGroups().entrySet()) {
					Cell cell = row.createCell(columnCount++);
					cell.setCellValue(group.getValue().getQuantity());
				}
			}
			for (HashMap.Entry<Long, Ticket> ticket: billDate.getValue().getTickets().entrySet()) {
				Cell cell = row.createCell(columnCount++);
				cell.setCellValue(ticket.getValue().getTotalAmount());
				cell.setCellStyle(cellStyle);
			}
			Cell cell_00 = row.createCell(columnCount++);
			cell_00.setCellValue(billDate.getValue().getTotalAmount());
			++rowCount;
		}
		Row row_1 = sheet.createRow(rowCount);
		Cell cell_1 = row_1.createCell(0);
		cell_1.setCellValue(grandTotal[0]);
		sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 2 + (ticketsName.size()) * groupsName.size() + ticketsName.size()));
		CellStyle cellStyle_ = workbook.createCellStyle();
		cellStyle_.setAlignment(HorizontalAlignment.RIGHT);
		cell_1.setCellStyle(cellStyle_);

		try (OutputStream outputStream = httpServletResponse.getOutputStream()) {
			workbook.write(outputStream);
		}
		workbook.close();
	}

	public LinkedHashMap<Date, BillDate> arrange(List<TicketDailyReport> ticketDailyReports, Map<Long, String> ticketsMap, Map<Long, String> visitorsSingleMap, Map<Long, String> visitorsComboMap, double[] grandTotal) {
		int ticketCount = 0;
		LinkedHashMap<Long, Integer> ticketIdToIndex = new LinkedHashMap<Long, Integer>();
		for (Map.Entry<Long, String> ticketMap: ticketsMap.entrySet()) {
			ticketIdToIndex.put(ticketMap.getKey(), ticketCount++);
		}
		int visitorSingleCount = 0;
		LinkedHashMap<Long, Integer> groupSingleIdToIndex = new LinkedHashMap<Long, Integer>();
		for (Map.Entry<Long, String> visitorSingleMap: visitorsSingleMap.entrySet()) {
			groupSingleIdToIndex.put(visitorSingleMap.getKey(), visitorSingleCount++);
		}
		int visitorComboCount = 0;
		LinkedHashMap<Long, Integer> groupComboIdToIndex = new LinkedHashMap<Long, Integer>();
		for (Map.Entry<Long, String> visitorComboMap: visitorsComboMap.entrySet()) {
			groupComboIdToIndex.put(visitorComboMap.getKey(), visitorComboCount++);
		}

		LinkedHashMap<Date, BillDate> billDates = new LinkedHashMap<Date, BillDate>();
		for (TicketDailyReport ticketDailyReport: ticketDailyReports) {
			BillDate billDate = billDates.get(ticketDailyReport.getBillDate());
			if (billDate == null) {
				billDate = new BillDate();
				billDate.setDate(ticketDailyReport.getBillDate());
				billDate.setGroups(new HashMap<Long, Group>());
				billDate.setTickets(new HashMap<Long, Ticket>());

				if (ticketDailyReport.getGroupType() == GroupType.COMBO) {
					billDate.setTotalTickets(1);
					billDate.setTotalAmount(Double.valueOf(ticketDailyReport.getPrice()));

					Group group = new Group();
					group.setGroupId(ticketDailyReport.getVisitorId());
					group.setGroupName(ticketDailyReport.getVisitorName());
					group.setTicketSerial(ticketDailyReport.getTicketSerial());
					group.setQuantity(1);
					group.setAmount(ticketDailyReport.getPrice());
					billDate.getGroups().put(Long.valueOf(groupComboIdToIndex.get(ticketDailyReport.getVisitorId())), group);
				}
				else {
					billDate.setTotalTickets(ticketDailyReport.getPersons());
					billDate.setTotalAmount(Double.valueOf(ticketDailyReport.getPrice() * ticketDailyReport.getPersons()));

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
					ticket.getGroups().put(Long.valueOf(groupSingleIdToIndex.get(ticketDailyReport.getVisitorId())), group);

					billDate.getTickets().put(Long.valueOf(ticketIdToIndex.get(ticketDailyReport.getTicketId())), ticket);
				}

				billDates.put(ticketDailyReport.getBillDate(), billDate);
			}
			else {
				if (ticketDailyReport.getGroupType() == GroupType.COMBO) {
					Group group = billDate.getGroups().get(Long.valueOf(groupComboIdToIndex.get(ticketDailyReport.getVisitorId())));
					if (group == null) {
						group = new Group();
						group.setGroupId(ticketDailyReport.getVisitorId());
						group.setGroupName(ticketDailyReport.getVisitorName());
						group.setTicketSerial(ticketDailyReport.getTicketSerial());
						group.setQuantity(1);
						group.setAmount(ticketDailyReport.getPrice());
						billDate.getGroups().put(Long.valueOf(groupComboIdToIndex.get(ticketDailyReport.getVisitorId())), group);
					}
					else {
						group.setQuantity(group.getQuantity() + ticketDailyReport.getPersons());
						group.setAmount(group.getAmount() + ticketDailyReport.getPrice());
					}
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
						ticket.getGroups().put(Long.valueOf(groupSingleIdToIndex.get(ticketDailyReport.getVisitorId())), group);

						billDate.getTickets().put(Long.valueOf(ticketIdToIndex.get(ticketDailyReport.getTicketId())), ticket);
					}
					else {
						Group group = ticket.getGroups().get(Long.valueOf(groupSingleIdToIndex.get(ticketDailyReport.getVisitorId())));
						if (group == null) {
							group = new Group();
							group.setGroupId(ticketDailyReport.getVisitorId());
							group.setGroupName(ticketDailyReport.getVisitorName());
							group.setTicketSerial(ticketDailyReport.getTicketSerial());
							ticket.getGroups().put(Long.valueOf(groupSingleIdToIndex.get(ticketDailyReport.getVisitorId())), group);
						}
						else {
							group.setQuantity(group.getQuantity() + ticketDailyReport.getPersons());
							group.setAmount(group.getAmount() + (ticketDailyReport.getPrice() * ticketDailyReport.getPersons()));
						}
						ticket.setTotalQuantity(ticket.getTotalQuantity() + ticketDailyReport.getPersons());
						ticket.setTotalAmount(ticket.getTotalAmount() + (ticketDailyReport.getPrice() * ticketDailyReport.getPersons()));
					}
				}
				if (ticketDailyReport.getGroupType() == GroupType.COMBO) {
					billDate.setTotalTickets(billDate.getTotalTickets() + 1);
					billDate.setTotalAmount(billDate.getTotalAmount() + ticketDailyReport.getPrice());
				}
				else {
					billDate.setTotalTickets(billDate.getTotalTickets() + ticketDailyReport.getPersons());
					billDate.setTotalAmount(billDate.getTotalAmount() + (ticketDailyReport.getPrice() * ticketDailyReport.getPersons()));
				}
			}
			if (ticketDailyReport.getGroupType() == GroupType.COMBO) {
				grandTotal[0] += ticketDailyReport.getPrice();
			}
			else {
				grandTotal[0] += ticketDailyReport.getPrice() * ticketDailyReport.getPersons();
			}
		}

		// Make object with zero values that were not present
		for (HashMap.Entry<Date, BillDate> billDate: billDates.entrySet()) {
			for (HashMap.Entry<Long, Integer> groupComboMappedIndex: groupComboIdToIndex.entrySet()) {
				if (billDate.getValue().getGroups().get(Long.valueOf(groupComboMappedIndex.getValue())) == null) {
					Group group = new Group();
					group.setGroupName("");
					group.setQuantity(0);
					group.setAmount(0f);
					billDate.getValue().getGroups().put(Long.valueOf(groupComboMappedIndex.getValue()), group);
				}
			}
			for (HashMap.Entry<Long, Integer> ticketMappedIndex: ticketIdToIndex.entrySet()) {
				if (billDate.getValue().getTickets().get(Long.valueOf(ticketMappedIndex.getValue())) == null) {
					Ticket ticket = new Ticket();
					ticket.setTicketName("");
					ticket.setTotalQuantity(0);
					ticket.setTotalAmount(0d);
					ticket.setGroups(new LinkedHashMap<Long, Group>());
					for (HashMap.Entry<Long, Integer> groupSingleMappedIndex: groupSingleIdToIndex.entrySet()) {
						Group  group = new Group();
						group.setGroupName("");
						group.setQuantity(0);
						group.setAmount(0f);
						ticket.getGroups().put(Long.valueOf(groupSingleMappedIndex.getValue()), group);
					}
					billDate.getValue().getTickets().put(Long.valueOf(ticketMappedIndex.getValue()), ticket);
				}
				else {
					for (HashMap.Entry<Long, Integer> groupSingleMappedIndex: groupSingleIdToIndex.entrySet()) {
						if (billDate.getValue().getTickets().get(Long.valueOf(ticketMappedIndex.getValue())).getGroups().get(Long.valueOf(groupSingleMappedIndex.getValue())) == null) {
							Group group = new Group();
							group.setGroupName("");
							group.setQuantity(0);
							group.setAmount(0f);
							billDate.getValue().getTickets().get(Long.valueOf(ticketMappedIndex.getValue())).getGroups().put(Long.valueOf(groupSingleMappedIndex.getValue()), group);
						}
					}
				}
			}
		}
		return billDates;
	}

	/*
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
	*/

	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	private class BillDate {
		private Date date;
		private HashMap<Long, Ticket> tickets;
		private Integer totalTickets;
		private Double totalAmount;
		private HashMap<Long, Group> groups;
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
