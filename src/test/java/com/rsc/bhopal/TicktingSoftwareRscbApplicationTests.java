package com.rsc.bhopal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.entity.TicketDetails;
import com.rsc.bhopal.repos.TicketDetailsRepository;
import com.rsc.bhopal.service.BillCalculatorService;
import com.rsc.bhopal.service.TicketDetailsService;



@SpringBootTest
class TicktingSoftwareRscbApplicationTests {

	@Autowired
	TicketDetailsService ticketDetails;
	
	@Autowired
	BillCalculatorService billCalculatorService;
	
	@MockBean
	TicketDetailsRepository ticketRepo;
	
	@Test
	public void getTicketDetails() {
		
	}
	
	
}
