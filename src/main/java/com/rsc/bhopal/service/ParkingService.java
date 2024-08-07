package com.rsc.bhopal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsc.bhopal.dtos.ParkingDetailsDTO;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;
import com.rsc.bhopal.entity.ParkingDetails;
import com.rsc.bhopal.repos.ParkingDetailsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingService {

	@Autowired
	private ParkingDetailsRepository parkingRepo;
	

	public List<ParkingDetailsDTO> getParkingDetails() {
		
	  List<ParkingDetailsDTO> parkingDetailsDTOs = new ArrayList<>();
	  
	  
	  List<ParkingDetails> parkingDetails = parkingRepo.findAll();
		
	  parkingDetails.forEach(parkingDetail->{
		  
		  ParkingDetailsDTO parkingDetailsDTO = new ParkingDetailsDTO();
		  
		  TicketsRatesMasterDTO rateMaster = new TicketsRatesMasterDTO();
		  
		  BeanUtils.copyProperties(parkingDetail.getRateMaster(),rateMaster);		

		  BeanUtils.copyProperties(parkingDetail, parkingDetailsDTO);		
		  
		  parkingDetailsDTO.setTicketsRatesMasterDTO(rateMaster);
		  
		  parkingDetailsDTOs.add(parkingDetailsDTO);
		  
		  log.debug("PARKING DETAILS "+parkingDetailsDTOs);
		  
	  });
	  
		return parkingDetailsDTOs;
	}

}
