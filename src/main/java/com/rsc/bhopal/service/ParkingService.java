package com.rsc.bhopal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsc.bhopal.dtos.ParkingDetailsDTO;
import com.rsc.bhopal.dtos.ParkingPriceDTO;
import com.rsc.bhopal.dtos.TicketDetailsDTO;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;
import com.rsc.bhopal.entity.ParkingDetails;
import com.rsc.bhopal.enums.BillType;
import com.rsc.bhopal.repos.ParkingDetailsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingService {

	@Autowired
	private ParkingDetailsRepository parkingRepo;


	public List<ParkingDetailsDTO> getParkingDetails() {

		List<ParkingDetailsDTO> parkingDetailsDTOs = new ArrayList<ParkingDetailsDTO>();

		List<ParkingDetails> parkingDetails = parkingRepo.findAll();

		parkingDetails.forEach(parkingDetail -> {

			try {
				ParkingDetailsDTO parkingDetailsDTO = new ParkingDetailsDTO();

				TicketsRatesMasterDTO rateMaster = new TicketsRatesMasterDTO();

				BeanUtils.copyProperties(parkingDetail.getRateMaster(), rateMaster);

				BeanUtils.copyProperties(parkingDetail, parkingDetailsDTO);

				parkingDetailsDTO.setTicketsRatesMasterDTO(rateMaster);

				parkingDetailsDTOs.add(parkingDetailsDTO);

				log.debug("PARKING DETAILS " + parkingDetailsDTOs);
			}
			catch(NullPointerException ex) {
				log.debug(ex.getMessage());
			}
			catch(IllegalArgumentException ex) {
				log.debug(ex.getMessage());
			}

		});

		return parkingDetailsDTOs;
	}

	public void addNewParking(ParkingPriceDTO parkingPriceDTO) {
		ParkingDetailsDTO parkingDetailsDTO = new ParkingDetailsDTO();
		parkingDetailsDTO.setName(parkingPriceDTO.getName());
		parkingDetailsDTO.setAddedAt(new Date());
		parkingDetailsDTO.setIdDsec("");
		parkingDetailsDTO.setIsActive(true);

		/*
		TicketsRatesMasterDTO ticketsRatesMasterDTO = new TicketsRatesMasterDTO();
		ticketsRatesMasterDTO.setId(null);
		ticketsRatesMasterDTO.setBillType(BillType.PARKING);
		ticketsRatesMasterDTO.setTicketType(null);
		ticketsRatesMasterDTO.setVisitorsType(null);
		parkingDetailsDTO.setTicketsRatesMasterDTO(ticketsRatesMasterDTO);
		*/

		ParkingDetails parkingDetails = new ParkingDetails();
		BeanUtils.copyProperties(parkingDetailsDTO, parkingDetails);
		parkingRepo.save(parkingDetails);
	}

}
