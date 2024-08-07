package com.rsc.bhopal;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rsc.bhopal.entity.ParkingDetails;
import com.rsc.bhopal.entity.TicketsRatesMaster;
import com.rsc.bhopal.entity.VisitorsType;
import com.rsc.bhopal.enums.BillType;
import com.rsc.bhopal.repos.ParkingDetailsRepository;
import com.rsc.bhopal.repos.VisitorTypeRepository;
import com.rsc.bhopal.service.TicketDetailsService;
import com.rsc.bhopal.service.TicketsRatesService;
import com.rsc.bhopal.service.VisitorTypeService;

@SpringBootApplication
public class TicktingSoftwareRscbApplication {

	@Autowired
	private TicketDetailsService ticketDetails;
	
	@Autowired
	private VisitorTypeService visitorDetails;
	
	@Autowired
	private TicketsRatesService ticketsRatesService;
	
	@Autowired
	private ParkingDetailsRepository parkingRepo;
	
	@Autowired
	private VisitorTypeRepository visitorRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(TicktingSoftwareRscbApplication.class, args);
	}
   @Bean
	CommandLineRunner runner() {
		return runner->{
			//ticketDetails.addTickets();
			///visitorDetails.addVisitorType();			
			//ticketsRatesService.getAllTicketRates();
			/*
			 * ParkingDetails parking= new ParkingDetails();
			 * 
			 * parking.setAddedAt(new Date()); parking.setIsActive(true);
			 * parking.setName("3 or 4 Wheeler");
			 * 
			 * TicketsRatesMaster rateMaster = new TicketsRatesMaster();
			 * rateMaster.setBillType(BillType.PARKING); rateMaster.setPrice(20f);
			 * rateMaster.setIsActive(true);
			 * 
			 * parking.setRateMaster(rateMaster);
			 * parking= parkingRepo.saveAndFlush(parking);
			 */
		};
	}

	
}
