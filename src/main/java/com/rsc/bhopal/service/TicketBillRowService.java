package com.rsc.bhopal.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsc.bhopal.dtos.BillSummarize;
import com.rsc.bhopal.dtos.RSCUserDTO;
import com.rsc.bhopal.dtos.TicketBillDTO;
import com.rsc.bhopal.dtos.TicketBillRowDTO;
import com.rsc.bhopal.dtos.TicketDetailsDTO;
import com.rsc.bhopal.dtos.TicketReportTableDTO;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;
import com.rsc.bhopal.dtos.VisitorsTypeDTO;
import com.rsc.bhopal.entity.TicketBillRow;
import com.rsc.bhopal.repos.TicketBillRowRepository;
import com.rsc.bhopal.utills.CommonUtills;

@Service
public class TicketBillRowService {
	@Autowired
	private TicketBillRowRepository ticketBillRowRepository;

	public List<TicketBillRowDTO> getTicketBillRows() {
		List<TicketBillRowDTO> ticketBillRowDTOs = new ArrayList<TicketBillRowDTO>();
		ticketBillRowRepository.getTicketBillRows().forEach(ticketBillRow -> {
			TicketBillRowDTO ticketBillRowDTO = new TicketBillRowDTO();
			BeanUtils.copyProperties(ticketBillRow, ticketBillRowDTO);

			TicketBillDTO ticketBillDTO = new TicketBillDTO();
			BeanUtils.copyProperties(ticketBillRow.getGeneratedTicket(), ticketBillDTO);
			ticketBillRowDTO.setTicketBillDTO(ticketBillDTO);

			RSCUserDTO rscUserDTO = new RSCUserDTO();
			BeanUtils.copyProperties(ticketBillRow.getGeneratedTicket().getGeneratedBy(), rscUserDTO);
			ticketBillRowDTO.getTicketBillDTO().setGeneratedBy(rscUserDTO.getUsername());

			BillSummarize billSummarize = CommonUtills.convertJSONToObject(ticketBillDTO.getTicketPayload(), BillSummarize.class);
			ticketBillDTO.setBillSummarize(billSummarize);

			TicketsRatesMasterDTO ticketsRatesMasterDTO = new TicketsRatesMasterDTO();
			BeanUtils.copyProperties(ticketBillRow.getRate(), ticketsRatesMasterDTO);
			ticketBillRowDTO.setTicketsRatesMasterDTO(ticketsRatesMasterDTO);

			if (ticketBillRow.getRate().getTicketType() != null) {
				TicketDetailsDTO ticketDetailsDTO = new TicketDetailsDTO();
				BeanUtils.copyProperties(ticketBillRow.getRate().getTicketType(), ticketDetailsDTO);
				ticketBillRowDTO.getTicketsRatesMasterDTO().setTicketType(ticketDetailsDTO);
			}

			if (ticketBillRow.getRate().getVisitorsType() != null) {
				VisitorsTypeDTO visitorsTypeDTO = new VisitorsTypeDTO();
				BeanUtils.copyProperties(ticketBillRow.getRate().getVisitorsType(), visitorsTypeDTO);
				ticketBillRowDTO.getTicketsRatesMasterDTO().setVisitorsType(visitorsTypeDTO);
			}

			ticketBillRowDTOs.add(ticketBillRowDTO);
		});
		return ticketBillRowDTOs;
	}

	public List<TicketBillRowDTO> getTicketBillRowsAtDateTime(Date startDateTime, Date endDateTime) {
		Timestamp timestampStartDateTime = new Timestamp(startDateTime.getTime());
		Timestamp timestampEndDateTime = new Timestamp(endDateTime.getTime());

		List<TicketBillRowDTO> ticketBillRowDTOs = new ArrayList<TicketBillRowDTO>();
		ticketBillRowRepository.getTicketBillRowsAtDateTime(timestampStartDateTime, timestampEndDateTime).forEach(ticketBillRow -> {
			TicketBillRowDTO ticketBillRowDTO = new TicketBillRowDTO();
			BeanUtils.copyProperties(ticketBillRow, ticketBillRowDTO);

			TicketBillDTO ticketBillDTO = new TicketBillDTO();
			BeanUtils.copyProperties(ticketBillRow.getGeneratedTicket(), ticketBillDTO);
			ticketBillRowDTO.setTicketBillDTO(ticketBillDTO);

			RSCUserDTO rscUserDTO = new RSCUserDTO();
			BeanUtils.copyProperties(ticketBillRow.getGeneratedTicket().getGeneratedBy(), rscUserDTO);
			ticketBillRowDTO.getTicketBillDTO().setGeneratedBy(rscUserDTO.getUsername());

			BillSummarize billSummarize = CommonUtills.convertJSONToObject(ticketBillDTO.getTicketPayload(), BillSummarize.class);
			ticketBillDTO.setBillSummarize(billSummarize);

			TicketsRatesMasterDTO ticketsRatesMasterDTO = new TicketsRatesMasterDTO();
			BeanUtils.copyProperties(ticketBillRow.getRate(), ticketsRatesMasterDTO);
			ticketBillRowDTO.setTicketsRatesMasterDTO(ticketsRatesMasterDTO);

			if (ticketBillRow.getRate().getTicketType() != null) {
				TicketDetailsDTO ticketDetailsDTO = new TicketDetailsDTO();
				BeanUtils.copyProperties(ticketBillRow.getRate().getTicketType(), ticketDetailsDTO);
				ticketBillRowDTO.getTicketsRatesMasterDTO().setTicketType(ticketDetailsDTO);
			}

			if (ticketBillRow.getRate().getVisitorsType() != null) {
				VisitorsTypeDTO visitorsTypeDTO = new VisitorsTypeDTO();
				BeanUtils.copyProperties(ticketBillRow.getRate().getVisitorsType(), visitorsTypeDTO);
				ticketBillRowDTO.getTicketsRatesMasterDTO().setVisitorsType(visitorsTypeDTO);
			}

			ticketBillRowDTOs.add(ticketBillRowDTO);
		});
		return ticketBillRowDTOs;
	}

	public List<TicketBillRowDTO> getAllTicketsBillRow() {
		List<TicketBillRowDTO> ticketBillRowDTOs = new ArrayList<TicketBillRowDTO>();
		ticketBillRowRepository.findAll().forEach(ticketBillRow -> {
			TicketBillRowDTO ticketBillRowDTO = new TicketBillRowDTO();
			BeanUtils.copyProperties(ticketBillRow, ticketBillRowDTO);

			TicketBillDTO ticketBillDTO = new TicketBillDTO();
			BeanUtils.copyProperties(ticketBillRow.getGeneratedTicket(), ticketBillDTO);
			ticketBillRowDTO.setTicketBillDTO(ticketBillDTO);

			RSCUserDTO rscUserDTO = new RSCUserDTO();
			BeanUtils.copyProperties(ticketBillRow.getGeneratedTicket().getGeneratedBy(), rscUserDTO);
			ticketBillRowDTO.getTicketBillDTO().setGeneratedBy(rscUserDTO.getUsername());

			BillSummarize billSummarize = CommonUtills.convertJSONToObject(ticketBillDTO.getTicketPayload(), BillSummarize.class);
			ticketBillDTO.setBillSummarize(billSummarize);

			TicketsRatesMasterDTO ticketsRatesMasterDTO = new TicketsRatesMasterDTO();
			BeanUtils.copyProperties(ticketBillRow.getRate(), ticketsRatesMasterDTO);
			ticketBillRowDTO.setTicketsRatesMasterDTO(ticketsRatesMasterDTO);

			if (ticketBillRow.getRate().getTicketType() != null) {
				TicketDetailsDTO ticketDetailsDTO = new TicketDetailsDTO();
				BeanUtils.copyProperties(ticketBillRow.getRate().getTicketType(), ticketDetailsDTO);
				ticketBillRowDTO.getTicketsRatesMasterDTO().setTicketType(ticketDetailsDTO);
			}

			if (ticketBillRow.getRate().getVisitorsType() != null) {
				VisitorsTypeDTO visitorsTypeDTO = new VisitorsTypeDTO();
				BeanUtils.copyProperties(ticketBillRow.getRate().getVisitorsType(), visitorsTypeDTO);
				ticketBillRowDTO.getTicketsRatesMasterDTO().setVisitorsType(visitorsTypeDTO);
			}

			ticketBillRowDTOs.add(ticketBillRowDTO);
		});
		return ticketBillRowDTOs;
	}

	public List<BigInteger> getTicketsSerialDesc() {
		return ticketBillRowRepository.getTicketsSerialDesc();
	}

	public List<BigInteger> getTicketsSerialAtDateTimeDesc(Date startDateTime, Date endDateTime) {
		Timestamp timestampStartDateTime = new Timestamp(startDateTime.getTime());
		Timestamp timestampEndDateTime = new Timestamp(endDateTime.getTime());

		return ticketBillRowRepository.getTicketsSerialAtDateTimeDesc(timestampStartDateTime, timestampEndDateTime);
	}
	
	public List<TicketBillRowDTO> getCancelledStatusDesc(boolean cancelledStatus) {
		List<TicketBillRowDTO> ticketBillRowDTOs = new ArrayList<TicketBillRowDTO>();
		ticketBillRowRepository.getCancelledStatusDesc(cancelledStatus).forEach(ticketBillRow -> {
			TicketBillRowDTO ticketBillRowDTO = new TicketBillRowDTO();
			BeanUtils.copyProperties(ticketBillRow, ticketBillRowDTO);

			TicketBillDTO ticketBillDTO = new TicketBillDTO();
			BeanUtils.copyProperties(ticketBillRow.getGeneratedTicket(), ticketBillDTO);
			ticketBillRowDTO.setTicketBillDTO(ticketBillDTO);

			RSCUserDTO rscUserDTO = new RSCUserDTO();
			BeanUtils.copyProperties(ticketBillRow.getGeneratedTicket().getGeneratedBy(), rscUserDTO);
			ticketBillRowDTO.getTicketBillDTO().setGeneratedBy(rscUserDTO.getUsername());

			BillSummarize billSummarize = CommonUtills.convertJSONToObject(ticketBillDTO.getTicketPayload(), BillSummarize.class);
			ticketBillDTO.setBillSummarize(billSummarize);

			TicketsRatesMasterDTO ticketsRatesMasterDTO = new TicketsRatesMasterDTO();
			BeanUtils.copyProperties(ticketBillRow.getRate(), ticketsRatesMasterDTO);
			ticketBillRowDTO.setTicketsRatesMasterDTO(ticketsRatesMasterDTO);

			if (ticketBillRow.getRate().getTicketType() != null) {
				TicketDetailsDTO ticketDetailsDTO = new TicketDetailsDTO();
				BeanUtils.copyProperties(ticketBillRow.getRate().getTicketType(), ticketDetailsDTO);
				ticketBillRowDTO.getTicketsRatesMasterDTO().setTicketType(ticketDetailsDTO);
			}

			if (ticketBillRow.getRate().getVisitorsType() != null) {
				VisitorsTypeDTO visitorsTypeDTO = new VisitorsTypeDTO();
				BeanUtils.copyProperties(ticketBillRow.getRate().getVisitorsType(), visitorsTypeDTO);
				ticketBillRowDTO.getTicketsRatesMasterDTO().setVisitorsType(visitorsTypeDTO);
			}

			ticketBillRowDTOs.add(ticketBillRowDTO);
		});
		return ticketBillRowDTOs;
	}

	public List<TicketBillRowDTO> getCancelledStatusAtDateTimeDesc(Date startDateTime, Date endDateTime, boolean cancelledStatus) {
		Timestamp timestampStartDateTime = new Timestamp(startDateTime.getTime());
		Timestamp timestampEndDateTime = new Timestamp(endDateTime.getTime());

		List<TicketBillRowDTO> ticketBillRowDTOs = new ArrayList<TicketBillRowDTO>();
		ticketBillRowRepository.getCancelledStatusAtDateTimeDesc(timestampStartDateTime, timestampEndDateTime, cancelledStatus).forEach(ticketBillRow -> {
			TicketBillRowDTO ticketBillRowDTO = new TicketBillRowDTO();
			BeanUtils.copyProperties(ticketBillRow, ticketBillRowDTO);

			TicketBillDTO ticketBillDTO = new TicketBillDTO();
			BeanUtils.copyProperties(ticketBillRow.getGeneratedTicket(), ticketBillDTO);
			ticketBillRowDTO.setTicketBillDTO(ticketBillDTO);

			RSCUserDTO rscUserDTO = new RSCUserDTO();
			BeanUtils.copyProperties(ticketBillRow.getGeneratedTicket().getGeneratedBy(), rscUserDTO);
			ticketBillRowDTO.getTicketBillDTO().setGeneratedBy(rscUserDTO.getUsername());

			BillSummarize billSummarize = CommonUtills.convertJSONToObject(ticketBillDTO.getTicketPayload(), BillSummarize.class);
			ticketBillDTO.setBillSummarize(billSummarize);

			TicketsRatesMasterDTO ticketsRatesMasterDTO = new TicketsRatesMasterDTO();
			BeanUtils.copyProperties(ticketBillRow.getRate(), ticketsRatesMasterDTO);
			ticketBillRowDTO.setTicketsRatesMasterDTO(ticketsRatesMasterDTO);

			if (ticketBillRow.getRate().getTicketType() != null) {
				TicketDetailsDTO ticketDetailsDTO = new TicketDetailsDTO();
				BeanUtils.copyProperties(ticketBillRow.getRate().getTicketType(), ticketDetailsDTO);
				ticketBillRowDTO.getTicketsRatesMasterDTO().setTicketType(ticketDetailsDTO);
			}

			if (ticketBillRow.getRate().getVisitorsType() != null) {
				VisitorsTypeDTO visitorsTypeDTO = new VisitorsTypeDTO();
				BeanUtils.copyProperties(ticketBillRow.getRate().getVisitorsType(), visitorsTypeDTO);
				ticketBillRowDTO.getTicketsRatesMasterDTO().setVisitorsType(visitorsTypeDTO);
			}

			ticketBillRowDTOs.add(ticketBillRowDTO);
		});
		return ticketBillRowDTOs;
	}

	public List<TicketReportTableDTO> getTicketsReportTable() {
		List<TicketReportTableDTO> ticketReportTableDTOs = new ArrayList<TicketReportTableDTO>();
		ticketBillRowRepository.getTicketsReportTable().forEach(ticketReportTable -> {
			TicketReportTableDTO ticketReportTableDTO = new TicketReportTableDTO();
			BeanUtils.copyProperties(ticketReportTable, ticketReportTableDTO);
			ticketReportTableDTOs.add(ticketReportTableDTO);
		});
		return ticketReportTableDTOs;
	}
}
