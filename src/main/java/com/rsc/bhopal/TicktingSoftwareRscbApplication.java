package com.rsc.bhopal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	
	public static void main(String[] args) {
		SpringApplication.run(TicktingSoftwareRscbApplication.class, args);
	}
   @Bean
	CommandLineRunner runner() {
		return runner->{
			//ticketDetails.addTickets();
			//visitorDetails.addVisitorType();			
			//ticketsRatesService.getAllTicketRates();
		};
	}
	
	
	
	
}
