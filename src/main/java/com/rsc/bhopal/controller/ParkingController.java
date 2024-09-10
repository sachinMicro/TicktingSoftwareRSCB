package com.rsc.bhopal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rsc.bhopal.dtos.ParkingDetailsDTO;
import com.rsc.bhopal.dtos.ParkingPriceDTO;
import com.rsc.bhopal.service.ParkingService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/manage")
@Slf4j
public class ParkingController {
	@Autowired
	private ParkingService parkingService;

	@GetMapping(path = "/parking/add")
	public String addParkingDetails(@ModelAttribute ParkingPriceDTO parkingPriceDTO) {
		log.debug(parkingPriceDTO.toString());
		final List<ParkingDetailsDTO> parkingDetailsDTO = parkingService.getParkingDetails();
		log.debug(parkingDetailsDTO.toString());
		parkingService.addNewParking(parkingPriceDTO);
		return "parking/add";
	}

	@GetMapping(path = "/parking/price/change")
	public String updateParkingDetails(@ModelAttribute List<ParkingPriceDTO> parkingPriceDTO) {
		final List<ParkingDetailsDTO> parkingDetailsDTO = parkingService.getParkingDetails();
		parkingDetailsDTO.forEach(parkingDetailDTO -> {});
		return "parking/add";
	}

	@GetMapping(path = "/parking/details")
	@ResponseBody
	public List<ParkingDetailsDTO> getDetails() {
		return parkingService.getParkingDetails();
	}
}
