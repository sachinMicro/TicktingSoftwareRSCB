package com.rsc.bhopal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsc.bhopal.repos.TicketBillRowRepository;

@Service
public class TicketBillRowService {
	@Autowired
	TicketBillRowRepository billSummRepo;
}
