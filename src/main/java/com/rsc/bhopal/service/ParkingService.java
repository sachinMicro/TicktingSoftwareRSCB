package com.rsc.bhopal.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsc.bhopal.dtos.ParkingDetailsDTO;
import com.rsc.bhopal.dtos.ParkingPriceDTO;
import com.rsc.bhopal.dtos.TicketDetailsDTO;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;
import com.rsc.bhopal.entity.ParkingDetails;
import com.rsc.bhopal.entity.RSCUser;
import com.rsc.bhopal.entity.TicketDetails;
import com.rsc.bhopal.entity.TicketsRatesMaster;
import com.rsc.bhopal.entity.VisitorsType;
import com.rsc.bhopal.enums.BillType;
import com.rsc.bhopal.repos.ParkingDetailsRepository;
import com.rsc.bhopal.repos.TicketsRatesMasterRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingService {

	@Autowired
	private ParkingDetailsRepository parkingRepo;

	@Autowired
	private TicketsRatesMasterRepository ticketRepo;

	@Autowired
	private RSCUserDetailsService userDetailsService;

	public void addNewParkingRate(ParkingPriceDTO parkingPriceDTO, Principal user) {
		TicketsRatesMaster ticketsRatesMaster = new TicketsRatesMaster();
		ticketsRatesMaster.setBillType(BillType.PARKING);
		ticketsRatesMaster.setRevisionNo(0);
		ticketsRatesMaster.setPrice(parkingPriceDTO.getPrice());
		ticketsRatesMaster.setRevisedAt(new Date());
		ticketsRatesMaster.setUser(userDetailsService.getUserByUsername(user.getName()));
		ticketsRatesMaster.setIsActive(true);
		ParkingDetails parkingDetails = new ParkingDetails();
		parkingDetails.setName(parkingPriceDTO.getName());
		parkingDetails.setAddedAt(new Date());
		parkingDetails.setIsActive(true);
		// parkingDetails.setRateMaster(ticketsRatesMaster);
		parkingDetails=parkingRepo.saveAndFlush(parkingDetails);
		ticketsRatesMaster.setParkingDetails(parkingDetails);
		ticketsRatesMaster=ticketRepo.saveAndFlush(ticketsRatesMaster);
	}

	public List<ParkingDetailsDTO> getParkingDetails() {

		List<ParkingDetailsDTO> parkingDetailsDTOs = new ArrayList<ParkingDetailsDTO>();

		List<ParkingDetails> parkingDetails = parkingRepo.findAll();

		parkingDetails.forEach(parkingDetail -> {
			System.out.println(parkingDetails);
			try {
				ParkingDetailsDTO parkingDetailsDTO = new ParkingDetailsDTO();

				TicketsRatesMasterDTO rateMasterDTO = new TicketsRatesMasterDTO();

				Optional<TicketsRatesMaster> rateMaster=parkingDetail.getRateMaster().stream()
														.filter(rate->rate.getIsActive()).findFirst();

				BeanUtils.copyProperties(rateMaster.get(), rateMasterDTO);

				BeanUtils.copyProperties(parkingDetail, parkingDetailsDTO);

				parkingDetailsDTO.setTicketsRatesMasterDTO(rateMasterDTO);

				parkingDetailsDTOs.add(parkingDetailsDTO);

				log.debug("PARKING DETAILS " + parkingDetailsDTOs);
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		return parkingDetailsDTOs;
	}

	public ParkingDetailsDTO getParkingDetailsById(final long parkingId) {
		ParkingDetailsDTO parkingDetailsDTO = new ParkingDetailsDTO();
		BeanUtils.copyProperties(parkingDetailsDTO, parkingRepo.findById(parkingId));
		return parkingDetailsDTO;
	}

	public void addNewParking(ParkingPriceDTO parkingPriceDTO) {
		ParkingDetailsDTO parkingDetailsDTO = new ParkingDetailsDTO();
		parkingDetailsDTO.setName(parkingPriceDTO.getName());
		parkingDetailsDTO.setAddedAt(new Date());
		parkingDetailsDTO.setIdDsec("");
		parkingDetailsDTO.setIsActive(true);
		ParkingDetails parkingDetails = new ParkingDetails();
		BeanUtils.copyProperties(parkingDetailsDTO, parkingDetails);
		parkingRepo.save(parkingDetails);
	}

}
