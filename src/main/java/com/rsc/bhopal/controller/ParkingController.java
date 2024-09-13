package com.rsc.bhopal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rsc.bhopal.dtos.ParkingDetailsDTO;
import com.rsc.bhopal.dtos.ParkingPriceDTO;
import com.rsc.bhopal.service.ParkingService;
import com.rsc.bhopal.service.TicketsRatesService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/manage")
@Slf4j
public class ParkingController {
	@Autowired
	private ParkingService parkingService;
	@Autowired
	private TicketsRatesService ticketsRatesService;

	@GetMapping(path = "/parking/add")
	public String addParkingDetails(@ModelAttribute ParkingPriceDTO parkingPriceDTO, Map<String, Object> attributes) {
		attributes.put("parkings", parkingService.getParkingDetails());
		return "parking/add";
	}

	@PostMapping(path = "/parking/add")
	public String addNewParkingDetails(@ModelAttribute ParkingPriceDTO parkingPriceDTO, Map<String, Object> attributes) {
		// parkingService.addNewParking(parkingPriceDTO);
		parkingService.addNewParkingRate(parkingPriceDTO);
		log.debug(parkingPriceDTO.toString());
		attributes.put("parkings", parkingService.getParkingDetails());
		return "redirect:/manage/parking/add";
	}
/*
	@PostMapping(path = "/parking/price/change")
	public String updateParkingDetails(@ModelAttribute List<ParkingPriceDTO> parkingPriceDTO, Map<String, Object> attributes) {
		log.debug(parkingPriceDTO.toString());

		final List<ParkingDetailsDTO> parkingDetailsDTO = parkingService.getParkingDetails();
		attributes.put("parkings", parkingDetailsDTO);
		log.debug(parkingDetailsDTO.toString());

		// parkingDetailsDTO.forEach(parkingDetailDTO -> {});
		return "parking/add";
	}
 */
	@GetMapping(path = "/parking/details")
	@ResponseBody
	public List<ParkingDetailsDTO> getDetails() {
		return parkingService.getParkingDetails();
	}
}
