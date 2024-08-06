package com.rsc.bhopal.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TicketSelectorDTO {

	private List<Long> tickets;
	
	private long group;
	
	private long familyGroup;
	
	
	private int persons;
	
	private int bikes;
	
	private int threeFourWheeler;
		
	public TicketSelectorDTO(){
		this.tickets=new ArrayList<>();
	}
	
	
}
