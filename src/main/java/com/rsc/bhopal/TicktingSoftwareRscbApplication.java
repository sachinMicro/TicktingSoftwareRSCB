package com.rsc.bhopal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.rsc.bhopal.aops.service.ActivityLogService;
import com.rsc.bhopal.dtos.ActivityLogDTO;
import com.rsc.bhopal.dtos.LogPayload;
import com.rsc.bhopal.repos.ParkingDetailsRepository;
import com.rsc.bhopal.repos.VisitorTypeRepository;
import com.rsc.bhopal.service.TicketDetailsService;
import com.rsc.bhopal.service.TicketsRatesService;
import com.rsc.bhopal.service.VisitorTypeService;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
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

	@Autowired
	private ActivityLogService logService;

	public static void main(String[] args) {
		SpringApplication.run(TicktingSoftwareRscbApplication.class, args);
	}

   @Bean
	CommandLineRunner runner() {
		return runner->{
		};
	}
}
