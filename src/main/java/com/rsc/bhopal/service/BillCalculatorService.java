package com.rsc.bhopal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rsc.bhopal.dtos.BillDescription;
import com.rsc.bhopal.dtos.BillSummarize;
import com.rsc.bhopal.dtos.ParkingBillDescription;
import com.rsc.bhopal.dtos.ParkingDetailsDTO;
import com.rsc.bhopal.dtos.TicketSelectorDTO;
import com.rsc.bhopal.dtos.TicketsRatesMasterDTO;
import com.rsc.bhopal.dtos.VisitorsTypeDTO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BillCalculatorService {

	@Autowired
	private TicketsRatesService ticketsRatesService;
	
	@Autowired
	private VisitorTypeService visitorTypeService;
	
	@Autowired
	private ParkingService parkingService;
	
	public  List<ParkingBillDescription> getParkingBillDescription(int bikes , int threeFourWheeler){
		
		List<ParkingDetailsDTO> parkingDetails = parkingService.getParkingDetails();	
		
		List<ParkingBillDescription> billDescList = new ArrayList<>();		
		
		parkingDetails.forEach(parking->{		
			if(parking.getIdDsec().equals("bikes") && bikes>0 ) {
				ParkingBillDescription billDesc = new ParkingBillDescription();			
				billDesc.setDesc(parking.getName());
				billDesc.setGroupName("PARKING");
				billDesc.setCount(bikes);			
				billDesc.setPerCharge(parking.getTicketsRatesMasterDTO().getPrice());
				billDesc.setSum(bikes*parking.getTicketsRatesMasterDTO().getPrice());
				billDescList.add(billDesc);	
			}else if(parking.getIdDsec().equals("threeFourWheeler") && threeFourWheeler>0 ) {
				ParkingBillDescription billDesc = new ParkingBillDescription();			
				billDesc.setDesc(parking.getName());
				billDesc.setGroupName("PARKING");
				billDesc.setCount(threeFourWheeler);			
				billDesc.setPerCharge(parking.getTicketsRatesMasterDTO().getPrice());
				billDesc.setSum(threeFourWheeler*parking.getTicketsRatesMasterDTO().getPrice());				
				billDescList.add(billDesc);	
			}				
		});		
	  return billDescList;	
	}
	
	
	
	public BillSummarize summarizeBill(TicketSelectorDTO ticketSelectorDTO) {
		
		BillSummarize billSummarize = new BillSummarize();
		
		if(ticketSelectorDTO.getFamilyGroup()==0) {
			billSummarize.setBillDescription(summarizeTicketBill(ticketSelectorDTO));
		}else {
			billSummarize.setBillDescription(summarizeFamilyBill(ticketSelectorDTO.getFamilyGroup()));
		}
		if(ticketSelectorDTO.getBikes()>0 ||ticketSelectorDTO.getThreeFourWheeler()>0) {
			billSummarize.setParkingBillDescription(getParkingBillDescription(ticketSelectorDTO.getBikes()
                    ,ticketSelectorDTO.getThreeFourWheeler()));
		}
		return billSummarize;
	}
	
	public List<BillDescription> summarizeFamilyBill(long familyGroupId){
		
		List<BillDescription> bills = new ArrayList<>();
		
		VisitorsTypeDTO visitorDTO = visitorTypeService.getGeneralVisitorId(familyGroupId);
		List<Long> tickets = ticketsRatesService.getTicketsByGroup(familyGroupId);
		
		for(Long ticketId : tickets) {
        	TicketsRatesMasterDTO ticketRate =    ticketsRatesService.getTicketRateByGroup(ticketId,familyGroupId);	

			BillDescription bill = new BillDescription();

			bill.setTicket(ticketRate.getTicketType().getName());
			bill.setGroupName(ticketRate.getVisitorsType().getName());
			bill.setPerson(visitorDTO.getMinMembers());
			bill.setPerPersonPrice(ticketRate.getPrice());
			bill.setTotalSum(visitorDTO.getMinMembers()*ticketRate.getPrice());			
			bills.add(bill);
        }        
		
		
		return bills;
	}
	
	

	public List<BillDescription> summarizeTicketBill(TicketSelectorDTO ticketSelectorDTO) {		
		List<BillDescription> bills = new ArrayList<>();		
		List<String> tickets = new ArrayList<>();
        
		for(Long ticketId : ticketSelectorDTO.getTickets()) {
        	TicketsRatesMasterDTO ticketRate =    ticketsRatesService.getTicketRateByGroup(ticketId,ticketSelectorDTO.getGroup());	

			BillDescription bill = new BillDescription();

			bill.setTicket(ticketRate.getTicketType().getName());
			bill.setGroupName(ticketRate.getVisitorsType().getName());
			bill.setPerson(ticketSelectorDTO.getPersons());
			bill.setPerPersonPrice(ticketRate.getPrice());
			bill.setTotalSum(ticketSelectorDTO.getPersons()*ticketRate.getPrice());			
			bills.add(bill);
        }        
		return bills;
		
	}
	
	
	public double calculateTotal(TicketSelectorDTO ticketSelectorDTO) {
		double totalAmount = 0;
		
		
        for(Long ticketId : ticketSelectorDTO.getTickets()) {
        	TicketsRatesMasterDTO ticketRate =    ticketsRatesService.getTicketRateByGroup(ticketId,ticketSelectorDTO.getGroup());

        	totalAmount +=(ticketRate.getPrice()*ticketSelectorDTO.getPersons());
      
        }



		return totalAmount;
	}
	
	
	
	
}
