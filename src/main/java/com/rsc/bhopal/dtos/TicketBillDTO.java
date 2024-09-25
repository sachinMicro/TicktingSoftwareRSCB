package com.rsc.bhopal.dtos;

import java.util.Date;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class TicketBillDTO {
	
	private Long id;	
	
	private Date generatedAt;
	
	private Double totalBill;
	
    private String generatedBy;
    
	private String institution;
		
	private String remark;
	
    private BillSummarize billSummarize;
   // List<BillSummary> billSummary;
	
}
